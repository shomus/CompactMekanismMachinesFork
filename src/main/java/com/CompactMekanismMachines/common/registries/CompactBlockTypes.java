package com.CompactMekanismMachines.common.registries;

import mekanism.api.Upgrade;
import mekanism.api.math.FloatingLong;
import mekanism.common.block.attribute.AttributeCustomSelectionBox;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.block.attribute.Attributes;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.content.blocktype.BlockShapes;
import mekanism.generators.common.content.blocktype.Generator;
import mekanism.generators.common.registries.GeneratorsContainerTypes;
import mekanism.generators.common.registries.GeneratorsSounds;
import mekanism.generators.common.registries.GeneratorsTileEntityTypes;
import mekanism.generators.common.tile.TileEntityGasGenerator;

import java.util.EnumSet;

public class CompactBlockTypes {
    public static final Generator<TileEntityGasGenerator> GAS_BURNING_GENERATOR = Generator.GeneratorBuilder
            .createGenerator(() -> GeneratorsTileEntityTypes.GAS_BURNING_GENERATOR, GeneratorsLang.DESCRIPTION_GAS_BURNING_GENERATOR)
            .withGui(() -> GeneratorsContainerTypes.GAS_BURNING_GENERATOR)
            .withEnergyConfig(()-> FloatingLong.parseFloatingLong("1000"))
            .withCustomShape(BlockShapes.GAS_BURNING_GENERATOR)
            .with(AttributeCustomSelectionBox.JSON)
            .withSound(GeneratorsSounds.GAS_BURNING_GENERATOR)
            .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
            .withComputerSupport("compactfissionreactor")
            .replace(Attributes.ACTIVE_MELT_LIGHT)
            .build();
}
