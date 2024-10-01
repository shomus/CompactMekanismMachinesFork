package com.CompactMekanismMachines.common.registries;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import com.CompactMekanismMachines.common.tile.TileEntityCompactIndustrialTurbine;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.generators.common.registries.GeneratorsContainerTypes;

public class CompactTileEntityTypes {
    private CompactTileEntityTypes(){

    }
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(CompactMekanismMachines.MODID);

    public static final TileEntityTypeRegistryObject<TileEntityCompactFissionReactor> COMPACT_FISSION_REACTOR = TILE_ENTITY_TYPES.register(CompactBlocks.COMPACT_FISSION_REACTOR,TileEntityCompactFissionReactor::new, TileEntityMekanism::tickServer,TileEntityMekanism::tickClient);
    public static final TileEntityTypeRegistryObject<TileEntityCompactIndustrialTurbine> COMPACT_INDUSTRIAL_TURBINE = TILE_ENTITY_TYPES.register(CompactBlocks.COMPACT_INDUSTRIAL_TURBINE, TileEntityCompactIndustrialTurbine::new, TileEntityMekanism::tickServer,TileEntityMekanism::tickClient);
}
