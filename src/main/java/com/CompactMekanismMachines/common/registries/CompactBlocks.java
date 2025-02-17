package com.CompactMekanismMachines.common.registries;


import com.CompactMekanismMachines.common.CompactMekanismMachines;

import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.*;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import com.CompactMekanismMachines.common.tile.TileEntityCompactThermalEvaporation;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.item.block.machine.ItemBlockMachine;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.resource.BlockResourceInfo;
import mekanism.generators.common.content.blocktype.Generator;
import mekanism.generators.common.item.generator.ItemBlockWindGenerator;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class CompactBlocks  {
    private CompactBlocks(){
    }
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(CompactMekanismMachines.MODID);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompactFissionReactor, Generator<TileEntityCompactFissionReactor>>, ItemBlockMachine> COMPACT_FISSION_REACTOR = BLOCKS.register("compact_fission_reactor", () -> new BlockTileModel<>(CompactBlockTypes.COMPACT_FISSION_REACTOR, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompactIndustrialTurbine, Generator<TileEntityCompactIndustrialTurbine>>, ItemBlockMachine> COMPACT_INDUSTRIAL_TURBINE = BLOCKS.register("compact_industrial_turbine", () -> new BlockTileModel<>(CompactBlockTypes.COMPACT_INDUSTRIAL_TURBINE, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompactThermalEvaporation, Machine<TileEntityCompactThermalEvaporation>>,ItemBlockMachine> COMPACT_THERMAL_EVAPORATION = BLOCKS.register("compact_thermal_evaporation", () -> new BlockTileModel<>(CompactBlockTypes.COMPACT_THERMAL_EVAPORATION, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)),ItemBlockMachine::new);

    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x2, Generator<TileEntityCompressedWindGenerator_x2>>, ItemBlockWindGenerator> WIND_GENERATOR_X2 = BLOCKS.register("compressed_wind_generator_x2", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X2, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x8, Generator<TileEntityCompressedWindGenerator_x8>>, ItemBlockWindGenerator> WIND_GENERATOR_X8 = BLOCKS.register("compressed_wind_generator_x8", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X8, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x32, Generator<TileEntityCompressedWindGenerator_x32>>, ItemBlockWindGenerator> WIND_GENERATOR_X32 = BLOCKS.register("compressed_wind_generator_x32", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X32, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x128, Generator<TileEntityCompressedWindGenerator_x128>>, ItemBlockWindGenerator> WIND_GENERATOR_X128 = BLOCKS.register("compressed_wind_generator_x128", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X128, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x512, Generator<TileEntityCompressedWindGenerator_x512>>, ItemBlockWindGenerator> WIND_GENERATOR_X512 = BLOCKS.register("compressed_wind_generator_x512", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X512, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x2048, Generator<TileEntityCompressedWindGenerator_x2048>>, ItemBlockWindGenerator> WIND_GENERATOR_X2048 = BLOCKS.register("compressed_wind_generator_x2048", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X2048, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x8192, Generator<TileEntityCompressedWindGenerator_x8192>>, ItemBlockWindGenerator> WIND_GENERATOR_X8192 = BLOCKS.register("compressed_wind_generator_x8192", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X8192, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x32768, Generator<TileEntityCompressedWindGenerator_x32768>>, ItemBlockWindGenerator> WIND_GENERATOR_X32768 = BLOCKS.register("compressed_wind_generator_x32768", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X32768, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x131072, Generator<TileEntityCompressedWindGenerator_x131072>>, ItemBlockWindGenerator> WIND_GENERATOR_X131072 = BLOCKS.register("compressed_wind_generator_x131072", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X131072, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityCompressedWindGenerator_x532480, Generator<TileEntityCompressedWindGenerator_x532480>>, ItemBlockWindGenerator> WIND_GENERATOR_X532480 = BLOCKS.register("compressed_wind_generator_x532480", () -> new BlockTileModel<>(CompactBlockTypes.WIND_GENERATOR_X532480, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor()).strength(0.2F)), ItemBlockWindGenerator::new);



    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return BLOCKS.registerDefaultProperties(name, blockCreator, ItemBlockTooltip::new);
    }
}
