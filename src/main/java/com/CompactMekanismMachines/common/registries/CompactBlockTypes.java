package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.config.CompactMekanismMachinesConfig;
import com.CompactMekanismMachines.common.content.blocktype.BlockShapes;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import mekanism.api.Upgrade;
import mekanism.api.math.FloatingLong;
import mekanism.common.block.attribute.AttributeCustomSelectionBox;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.content.blocktype.Generator;
import mekanism.generators.common.registries.GeneratorsContainerTypes;
import mekanism.generators.common.registries.GeneratorsSounds;
import mekanism.generators.common.registries.GeneratorsTileEntityTypes;
import mekanism.generators.common.tile.TileEntityGasGenerator;

import java.util.EnumSet;

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
}
