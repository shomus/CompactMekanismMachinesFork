package com.CompactMekanismMachines.common.tile;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.math.FloatingLong;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableBoolean;
import mekanism.common.inventory.container.sync.SyncableFloatingLong;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.util.MekanismUtils;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.content.blocktype.Generator;
import mekanism.generators.common.item.generator.ItemBlockWindGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public abstract class TileEntityCompressedWindGenerator<TILE extends TileEntityCompressedWindGenerator<TILE>> extends TileEntityGenerator implements IBoundingBlock {


    public static final float SPEED = 32F;
    public static final float SPEED_SCALED = 256F / SPEED;
    private final Long multiply;

    private double angle;
    private FloatingLong currentMultiplier = FloatingLong.ZERO;
    private boolean isBlacklistDimension;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "energy item slot")
    EnergyInventorySlot energySlot;

    public TileEntityCompressedWindGenerator(BlockPos pos, BlockState state, Long multiply_, BlockRegistryObject<BlockTile.BlockTileModel<TILE, Generator<TILE>>, ItemBlockWindGenerator> block) {
        super(block, pos, state, () -> MekanismGeneratorsConfig.generators.windGenerationMax.get().multiply(multiply_));
        this.multiply = multiply_;
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSide(this::getDirection);
        builder.addSlot(energySlot = EnergyInventorySlot.drain(getEnergyContainer(), listener, 143, 35));
        return builder.build();
    }

    @Override
    protected RelativeSide[] getEnergySides() {
        return new RelativeSide[]{RelativeSide.FRONT, RelativeSide.BOTTOM};
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        energySlot.drainContainer();
        // If we're in a blacklisted dimension, there's nothing more to do
        if (isBlacklistDimension) {
            return;
        }
        if (ticker % 20 == 0) {
            // Recalculate the current multiplier once a second
            currentMultiplier = getMultiplier();
            setActive(MekanismUtils.canFunction(this) && !currentMultiplier.isZero());
        }
        if (!currentMultiplier.isZero() && MekanismUtils.canFunction(this) && !getEnergyContainer().getNeeded().isZero()) {
            getEnergyContainer().insert(MekanismGeneratorsConfig.generators.windGenerationMin.get().multiply(currentMultiplier), Action.EXECUTE, AutomationType.INTERNAL);
        }
    }

    @Override
    protected void onUpdateClient() {
        super.onUpdateClient();
        if (getActive()) {
            angle = (angle + (getBlockPos().getY() + 4F) / SPEED_SCALED) % 360;
        }
    }

    /**
     * Determines the current output multiplier, taking sky visibility and height into account.
     **/
    private FloatingLong getMultiplier() {
        if (level != null) {
            BlockPos top = getBlockPos().above(4);
            if (level.getFluidState(top).isEmpty() && level.canSeeSky(top)) {
                //Validate it isn't fluid logged to help try and prevent https://github.com/mekanism/Mekanism/issues/7344
                //Clamp the height limits as the logical bounds of the world
                int minY = Math.max(MekanismGeneratorsConfig.generators.windGenerationMinY.get(), level.getMinBuildHeight());
                int maxY = Math.min(MekanismGeneratorsConfig.generators.windGenerationMaxY.get(), level.dimensionType().logicalHeight());
                float clampedY = Math.min(maxY, Math.max(minY, top.getY()));
                FloatingLong minG = MekanismGeneratorsConfig.generators.windGenerationMin.get();
                FloatingLong maxG = MekanismGeneratorsConfig.generators.windGenerationMax.get();
                FloatingLong slope = maxG.subtract(minG).divide(maxY - minY);
                FloatingLong toGen = minG.add(slope.multiply(clampedY - minY));
                return toGen.divide(minG).multiply(multiply);
            }
        }
        return FloatingLong.ZERO;
    }

    @Override
    public void setLevel(@NotNull Level world) {
        super.setLevel(world);
        // Check the blacklist and force an update if we're in the blacklist. Otherwise, we'll never send
        // an initial activity status and the client (in MP) will show the windmills turning while not
        // generating any power
        isBlacklistDimension = MekanismGeneratorsConfig.generators.windGenerationDimBlacklist.get().contains(world.dimension().location());
        if (isBlacklistDimension) {
            setActive(false);
        }
    }

    public FloatingLong getCurrentMultiplier() {
        return currentMultiplier;
    }

    public double getAngle() {
        return angle;
    }

    @ComputerMethod(nameOverride = "isBlacklistedDimension")
    public boolean isBlacklistDimension() {
        return isBlacklistDimension;
    }

    @Override
    public SoundSource getSoundCategory() {
        return SoundSource.WEATHER;
    }

    @Override
    public BlockPos getSoundPos() {
        return super.getSoundPos().above(4);
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableFloatingLong.create(this::getCurrentMultiplier, value -> currentMultiplier = value));
        container.track(SyncableBoolean.create(this::isBlacklistDimension, value -> isBlacklistDimension = value));
    }

    @NotNull
    @Override
    public AABB getRenderBoundingBox() {
        //Note: we just extend it to the max size it could be ignoring what direction it is actually facing
        return new AABB(worldPosition.offset(-2, 0, -2), worldPosition.offset(3, 7, 3));
    }

    //Methods relating to IComputerTile
    @Override
    FloatingLong getProductionRate() {
        return getActive() ? MekanismGeneratorsConfig.generators.windGenerationMin.get().multiply(getCurrentMultiplier()) : FloatingLong.ZERO;
    }

    //End methods IComputerTile
    public long getMultiply() {
        return this.multiply;
    }
}
