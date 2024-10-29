package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.content.blocktype.BlockShapes;
import com.CompactMekanismMachines.common.tile.CompressedWindGenerator.TileEntityCompressedWindGenerator_x2;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import mekanism.api.Upgrade;
import mekanism.api.math.FloatingLong;
import mekanism.common.block.attribute.AttributeCustomSelectionBox;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.content.blocktype.Generator;
import mekanism.generators.common.registries.GeneratorsContainerTypes;
import mekanism.generators.common.registries.GeneratorsSounds;
import mekanism.generators.common.registries.GeneratorsTileEntityTypes;
import mekanism.generators.common.tile.TileEntityGasGenerator;
import mekanism.generators.common.tile.TileEntityWindGenerator;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CompactBlockTypes {

    private CompactBlockTypes(){}

    public static final Generator<TileEntityCompactFissionReactor> COMPACT_FISSION_REACTOR = Generator.GeneratorBuilder
            .createGenerator(() -> CompactTileEntityTypes.COMPACT_FISSION_REACTOR, GeneratorsLang.DESCRIPTION_GAS_BURNING_GENERATOR)
            .withGui(() -> CompactContainerTypes.COMPACT_FISSION_REACTOR)
            .withEnergyConfig(()-> FloatingLong.parseFloatingLong(String.valueOf(CompactMekanismMachinesConfig.machines.cfrEnergyCapacity.get())))
            .withCustomShape(BlockShapes.COMPACT_FISSION_REACTOR)
            .with(AttributeCustomSelectionBox.JSON)
            .withSound(GeneratorsSounds.FISSION_REACTOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
            .withComputerSupport("compactfissionreactor")
            .replace(Attributes.ACTIVE_MELT_LIGHT)
            .build();

    public static final Generator<TileEntityCompactIndustrialTurbine> COMPACT_INDUSTRIAL_TURBINE = Generator.GeneratorBuilder
            .createGenerator(() -> CompactTileEntityTypes.COMPACT_INDUSTRIAL_TURBINE, GeneratorsLang.DESCRIPTION_GAS_BURNING_GENERATOR)
            .withGui(() -> CompactContainerTypes.COMPACT_INDUSTRIAL_TURBINE)
            .withEnergyConfig(()-> FloatingLong.parseFloatingLong(String.valueOf(CompactMekanismMachinesConfig.machines.cfrEnergyCapacity.get())))
            .withCustomShape(BlockShapes.COMPACT_FISSION_REACTOR)
            .with(AttributeCustomSelectionBox.JSON)
            .withSound(GeneratorsSounds.FISSION_REACTOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
            .withComputerSupport("compactindustrialturbine")
            .replace(Attributes.ACTIVE_MELT_LIGHT)
            .build();
    public static final Generator<TileEntityCompressedWindGenerator_x2> WIND_GENERATOR_X2 = Generator.GeneratorBuilder
            .createGenerator(() -> CompactTileEntityTypes.WIND_GENERATOR_X2, GeneratorsLang.DESCRIPTION_WIND_GENERATOR)
            .withGui(() -> GeneratorsContainerTypes.WIND_GENERATOR)
            .withEnergyConfig(MekanismGeneratorsConfig.storageConfig.windGenerator)
            .withCustomShape(mekanism.generators.common.content.blocktype.BlockShapes.WIND_GENERATOR)
            .with(AttributeCustomSelectionBox.JAVA)
            .withSound(GeneratorsSounds.WIND_GENERATOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
            .withBounding((pos, state, builder) -> {
                for (int i = 0; i < 4; i++) {
                    builder.add(pos.above(i + 1));
                }
            })
            .withComputerSupport("compressed_windGenerator_x2")
            .build();
}
