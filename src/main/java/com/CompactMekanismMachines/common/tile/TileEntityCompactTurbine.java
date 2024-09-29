package com.CompactMekanismMachines.common.tile;

import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.registries.CompactBlocks;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.chemical.gas.attribute.GasAttributes;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.heat.HeatAPI;
import mekanism.api.math.FloatingLong;
import mekanism.api.math.FloatingLongSupplier;
import mekanism.api.math.MathUtils;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.capabilities.chemical.variable.VariableCapacityChemicalTankBuilder;
import mekanism.common.capabilities.fluid.VariableCapacityFluidTank;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.capabilities.heat.VariableHeatCapacitor;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.heat.HeatCapacitorHelper;
import mekanism.common.capabilities.holder.heat.IHeatCapacitorHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.config.MekanismConfig;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableDouble;
import mekanism.common.inventory.container.sync.SyncableFloatingLong;
import mekanism.common.inventory.container.sync.SyncableInt;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.chemical.GasInventorySlot;
import mekanism.common.registries.MekanismGases;
import mekanism.common.tile.base.SubstanceType;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityCompactTurbine extends TileEntityGenerator  {

    /**
     * The tank this block is storing fuel in.
     */
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerChemicalTankWrapper.class, methodNames = {"getFuel", "getFuelCapacity", "getFuelNeeded", "getFuelFilledPercentage"}, docPlaceholder = "fuel tank")
    public FuelTank fuelTank;
    public CoolantGasTank coolantGasTank;
    public CoolantFluidTank coolantFluidTank;
    public HeatedCoolantTank heatedCoolantTank;
    public WasteTank wasteTank;
    public HeatTank heatCapacitor;
    private long burnTicks;
    private int maxBurnTicks;
    private FloatingLong generationRate = FloatingLong.ZERO;
    private double biomeAmbientTemp;
    private double gasUsedLastTick;
    public  double partialWaste;
    public long lastBoilRate = 0;
    private static final double INVERSE_INSULATION_COEFFICIENT = 10_000;
    private static final double INVERSE_CONDUCTION_COEFFICIENT = 10;
    private static final double waterConductivity = 0.5;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getFuelItem", docPlaceholder = "fuel item slot")
    GasInventorySlot fuelSlot;
    @WrappingComputerMethod(wrapper = SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper.class, methodNames = "getEnergyItem", docPlaceholder = "energy item slot")
    EnergyInventorySlot energySlot;

    public TileEntityCompactTurbine(BlockPos pos, BlockState state) {
        super(CompactBlocks.COMPACT_FISSION_REACTOR, pos, state, MekanismConfig.general.FROM_H2);
        biomeAmbientTemp = HeatAPI.getAmbientTemp(this.getLevel(), this.getTilePos());
    }

    /**
     * Generator -- a block that produces energy. It has a certain amount of fuel it can store as well as an output rate.
     *
     * @param blockProvider
     * @param pos
     * @param state
     * @param maxOutput
     */
    public TileEntityCompactTurbine(IBlockProvider blockProvider, BlockPos pos, BlockState state, @NotNull FloatingLongSupplier maxOutput) {
        super(blockProvider, pos, state, maxOutput);
    }

    @NotNull
    @Override
    public IChemicalTankHolder<Gas, GasStack, IGasTank> getInitialGasTanks(IContentsListener listener) {
        ChemicalTankHelper<Gas, GasStack, IGasTank> builder = ChemicalTankHelper.forSide(this::getDirection);
        builder.addTank(fuelTank = new FuelTank(listener), RelativeSide.LEFT, RelativeSide.RIGHT, RelativeSide.BACK);
        builder.addTank(coolantGasTank = new CoolantGasTank(listener),RelativeSide.TOP);
        builder.addTank(wasteTank = new WasteTank(listener),RelativeSide.BOTTOM);
        builder.addTank(heatedCoolantTank = new HeatedCoolantTank(listener),RelativeSide.BACK);
        return builder.build();
    }

    @NotNull
    @Override
    public IFluidTankHolder getInitialFluidTanks(IContentsListener listener){
        FluidTankHelper builder = FluidTankHelper.forSide(this::getDirection);
        builder.addTank(coolantFluidTank = new CoolantFluidTank(listener),RelativeSide.TOP);
        return builder.build();
    }

    @NotNull
    @Override
    public IHeatCapacitorHolder getInitialHeatCapacitors(IContentsListener listener, CachedAmbientTemperature ambientTemperature){
        HeatCapacitorHelper builder = HeatCapacitorHelper.forSide(this::getDirection);
        builder.addCapacitor(heatCapacitor = new HeatTank(listener),RelativeSide.TOP,RelativeSide.BOTTOM,RelativeSide.LEFT, RelativeSide.RIGHT, RelativeSide.BACK,RelativeSide.FRONT);
        return builder.build();
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSide(this::getDirection);
        builder.addSlot(energySlot = EnergyInventorySlot.drain(getEnergyContainer(), listener, 143, 35), RelativeSide.RIGHT);
        return builder.build();
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        energySlot.drainContainer();

        if (!fuelTank.isEmpty() && MekanismUtils.canFunction(this) && getEnergyContainer().insert(generationRate, Action.SIMULATE, AutomationType.INTERNAL).isZero()) {
            setActive(true);
            if (!fuelTank.isEmpty()) {
                maxBurnTicks = 1;
                generationRate = FloatingLong.create(50000);

            }

            long toUse = getToUse();
            FloatingLong toUseGeneration = generationRate.multiply(toUse);
            updateMaxOutputRaw(MekanismConfig.general.FROM_H2.get().max(toUseGeneration));

            long total = burnTicks + fuelTank.getStored() * maxBurnTicks;
            total -= toUse;
            getEnergyContainer().insert(toUseGeneration, Action.EXECUTE, AutomationType.INTERNAL);
            if (!fuelTank.isEmpty()) {
                //TODO: Improve this as it is sort of hacky
                fuelTank.setStack(new GasStack(fuelTank.getStack(), total / maxBurnTicks));
            }

            heatCapacitor.handleHeat(toUse * MekanismGeneratorsConfig.generators.energyPerFissionFuel.get().doubleValue());
            if (heatCapacitor.getTemperature()>1600){
                heatCapacitor.setHeat(heatCapacitor.getHeatCapacity()*1600);
            }
            double temp = heatCapacitor.getTemperature();
            double heat = toUse * (temp - HeatUtils.BASE_BOIL_TEMP) * heatCapacitor.getHeatCapacity();
            if (!coolantFluidTank.isEmpty()){
                double caseCoolantHeat = heat * waterConductivity;
                lastBoilRate = clampCoolantHeated(HeatUtils.getSteamEnergyEfficiency() * caseCoolantHeat / HeatUtils.getWaterThermalEnthalpy(),
                        coolantFluidTank.getFluidAmount());
                if (lastBoilRate > 0) {
                    MekanismUtils.logMismatchedStackSize(coolantFluidTank.shrinkStack((int) lastBoilRate, Action.EXECUTE), lastBoilRate);
                    // extra steam is dumped
                    heatedCoolantTank.insert(MekanismGases.STEAM.getStack(lastBoilRate), Action.EXECUTE, AutomationType.INTERNAL);
                    caseCoolantHeat = lastBoilRate * HeatUtils.getWaterThermalEnthalpy() / HeatUtils.getSteamEnergyEfficiency();
                    heatCapacitor.handleHeat(-caseCoolantHeat);
                }
            }   else if (!coolantGasTank.isEmpty()) {
                coolantGasTank.getStack().ifAttributePresent(GasAttributes.CooledCoolant.class, coolantType -> {
                    double caseCoolantHeat = heat * coolantType.getConductivity();
                    lastBoilRate = clampCoolantHeated(caseCoolantHeat / coolantType.getThermalEnthalpy(), coolantGasTank.getStored());
                    if (lastBoilRate > 0) {
                        MekanismUtils.logMismatchedStackSize(coolantGasTank.shrinkStack(lastBoilRate, Action.EXECUTE), lastBoilRate);
                        heatedCoolantTank.insert(coolantType.getHeatedGas().getStack(lastBoilRate), Action.EXECUTE, AutomationType.INTERNAL);
                        caseCoolantHeat = lastBoilRate * coolantType.getThermalEnthalpy();
                        heatCapacitor.handleHeat(-caseCoolantHeat);
                    }
                });
            }
            partialWaste += toUse;
            long newWaste = (long) Math.floor(partialWaste);
            if (newWaste>0){
                partialWaste %= 1;
                GasStack wasteToAdd = MekanismGases.NUCLEAR_WASTE.getStack(newWaste);

                wasteTank.insert(wasteToAdd, Action.EXECUTE, AutomationType.INTERNAL);
            }
            burnTicks = total % maxBurnTicks;
            gasUsedLastTick = toUse / (double) maxBurnTicks;
        } else {
            if (fuelTank.isEmpty() && burnTicks == 0) {
                reset();
            }
            gasUsedLastTick = 0;
            setActive(false);
        }
    }

    private void reset() {
        burnTicks = 0;
        maxBurnTicks = 0;
        generationRate = FloatingLong.ZERO;
        updateMaxOutputRaw(MekanismConfig.general.FROM_H2.get());
    }

    private long getToUse() {
        if (generationRate.isZero() || fuelTank.isEmpty()) {
            return 0;
        }
        long max = (long) Math.ceil(CompactMekanismMachinesConfig.machines.cfrBurnRate.get() * (fuelTank.getStored() / (double) fuelTank.getCapacity()));
        max = Math.min(maxBurnTicks * fuelTank.getStored() + burnTicks, max);
        max = Math.min(getEnergyContainer().getNeeded().divide(generationRate).intValue(), max);
        return max;
    }

    private long clampCoolantHeated(double heated, long stored) {
        long heatedLong = MathUtils.clampToLong(heated);
        if (heatedLong < 0) {
            return 0;
        } else if (heatedLong > stored) {
            return stored;
        }
        return heatedLong;
    }
    public FloatingLong getGenerationRate() {
        return generationRate;
    }

    @ComputerMethod(nameOverride = "getBurnRate")
    public double getUsed() {
        return Math.round(gasUsedLastTick * 100) / 100D;
    }

    public int getMaxBurnTicks() {
        return maxBurnTicks;
    }

    @Override
    public int getRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(fuelTank.getStored(), fuelTank.getCapacity());
    }

    @Override
    protected boolean makesComparatorDirty(@Nullable SubstanceType type) {
        return type == SubstanceType.GAS;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableFloatingLong.create(this::getGenerationRate, value -> generationRate = value));
        container.track(syncableMaxOutput());
        container.track(SyncableDouble.create(this::getUsed, value -> gasUsedLastTick = value));
        container.track(SyncableInt.create(this::getMaxBurnTicks, value -> maxBurnTicks = value));
    }

    //Methods relating to IComputerTile
    @Override
    FloatingLong getProductionRate() {
        return getGenerationRate().multiply(getUsed()).multiply(getMaxBurnTicks());
    }
    //End methods IComputerTile

    //Implementation of gas tank that on no longer being empty updates the output rate of this generator
    private class FuelTank extends VariableCapacityChemicalTankBuilder.VariableCapacityGasTank {

        protected FuelTank(@Nullable IContentsListener listener) {
            super(CompactMekanismMachinesConfig.machines.cfrFuelTankCapacity, ChemicalTankBuilder.GAS.notExternal, ChemicalTankBuilder.GAS.alwaysTrueBi,
                    gas -> gas.equals(MekanismGases.FISSILE_FUEL.getChemical()), null, listener);
        }

        @Override
        public void setStack(@NotNull GasStack stack) {
            boolean wasEmpty = isEmpty();
            super.setStack(stack);
            recheckOutput(stack, wasEmpty);
        }

        @Override
        public void setStackUnchecked(@NotNull GasStack stack) {
            boolean wasEmpty = isEmpty();
            super.setStackUnchecked(stack);
            recheckOutput(stack, wasEmpty);
        }

        private void recheckOutput(@NotNull GasStack stack, boolean wasEmpty) {
            if (wasEmpty && !stack.isEmpty()) {
                getType().ifAttributePresent(GasAttributes.Fuel.class, fuel -> updateMaxOutputRaw(fuel.getEnergyPerTick()));
            }
        }
    }
    private class CoolantGasTank extends VariableCapacityChemicalTankBuilder.VariableCapacityGasTank {
        protected  CoolantGasTank(@Nullable IContentsListener listener){
            super(CompactMekanismMachinesConfig.machines.cfrCoolantGasTankCapacity,ChemicalTankBuilder.GAS.notExternal,ChemicalTankBuilder.GAS.alwaysTrueBi,
                    gas -> gas.has(GasAttributes.CooledCoolant.class),null,listener);
        }
        @Override
        public void setStack(@NotNull GasStack stack) {
            boolean wasEmpty = isEmpty();
            super.setStack(stack);
            recheckOutput(stack, wasEmpty);
        }

        @Override
        public void setStackUnchecked(@NotNull GasStack stack) {
            boolean wasEmpty = isEmpty();
            super.setStackUnchecked(stack);
            recheckOutput(stack, wasEmpty);
        }

        private void recheckOutput(@NotNull GasStack stack, boolean wasEmpty) {
        }
    }

    private class CoolantFluidTank extends VariableCapacityFluidTank {
        protected  CoolantFluidTank(@Nullable IContentsListener listener){
            super(CompactMekanismMachinesConfig.machines.cfrCoolantFluidTankCapacity, ConstantPredicates.notExternal(), ConstantPredicates.alwaysTrueBi(),
                    fluid -> fluid.isFluidEqual(new FluidStack(Fluids.WATER,1)),listener);
        }
    }

    private  class HeatedCoolantTank extends VariableCapacityChemicalTankBuilder.VariableCapacityGasTank {
        protected HeatedCoolantTank(@Nullable IContentsListener listener){
            super(CompactMekanismMachinesConfig.machines.cfrHeatedCoolantTankCapacity,ConstantPredicates.alwaysTrueBi(),ConstantPredicates.internalOnly(),
                    gas -> (gas.has(GasAttributes.HeatedCoolant.class)||gas.equals(MekanismGases.STEAM.get())), ChemicalAttributeValidator.ALWAYS_ALLOW,listener);
        }
    }

    private  class  WasteTank extends VariableCapacityChemicalTankBuilder.VariableCapacityGasTank {
        protected WasteTank(@Nullable IContentsListener listener){
            super(CompactMekanismMachinesConfig.machines.cfrWasteTankCapacity,ChemicalTankBuilder.GAS.alwaysTrueBi,ChemicalTankBuilder.GAS.internalOnly,
                    gas -> gas.equals(MekanismGases.NUCLEAR_WASTE.getChemical()), ChemicalAttributeValidator.ALWAYS_ALLOW,listener);
        }
    }

    public class HeatTank extends VariableHeatCapacitor {
        protected HeatTank(@Nullable IContentsListener listener){
            super(CompactMekanismMachinesConfig.machines.cfrHeatTankCpacity.get(),() -> INVERSE_CONDUCTION_COEFFICIENT, () -> INVERSE_INSULATION_COEFFICIENT, () -> biomeAmbientTemp, null);
        }
    }

}
