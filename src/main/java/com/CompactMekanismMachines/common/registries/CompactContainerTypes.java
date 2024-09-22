package com.CompactMekanismMachines.common.registries;

import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;
import mekanism.generators.common.MekanismGenerators;
import mekanism.generators.common.registries.GeneratorsBlocks;
import mekanism.generators.common.registries.GeneratorsContainerTypes;
import mekanism.generators.common.tile.TileEntityGasGenerator;

public class CompactContainerTypes {
    private CompactContainerTypes(){

    }

    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(MekanismGenerators.MODID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityGasGenerator>> GAS_BURNING_GENERATOR = CONTAINER_TYPES.custom(GeneratorsBlocks.GAS_BURNING_GENERATOR, TileEntityGasGenerator.class).armorSideBar(-20, 11, 0).build();
}
