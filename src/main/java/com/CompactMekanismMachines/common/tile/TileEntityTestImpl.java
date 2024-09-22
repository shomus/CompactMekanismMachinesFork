package com.CompactMekanismMachines.common.tile;

import mekanism.api.math.FloatingLong;
import mekanism.generators.common.tile.TileEntityGenerator;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TileEntityTestImpl extends TileEntityGenerator {
    @Override
    public FloatingLong getProductionRate(){
        return  FloatingLong.MAX_VALUE;
    }

}
