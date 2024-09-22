package com.CompactMekanismMachines.common.registries;


import com.CompactMekanismMachines.common.CompactMekanismMachines;

import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.resource.BlockResourceInfo;
import mekanism.generators.common.content.blocktype.Generator;
import mekanism.generators.common.tile.TileEntityGasGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class CompactBlocks  {
    private CompactBlocks(){
    }
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(CompactMekanismMachines.MODID);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompactFissionReactor, Generator<TileEntityCompactFissionReactor>>, BlockItem> COMPACT_FISSION_REACTOR = BLOCKS.register("compact_fission_reactor", () -> new BlockTileModel<>(CompactBlockTypes.COMPACT_FISSION_REACTOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())));
    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return BLOCKS.registerDefaultProperties(name, blockCreator, ItemBlockTooltip::new);
    }
}
