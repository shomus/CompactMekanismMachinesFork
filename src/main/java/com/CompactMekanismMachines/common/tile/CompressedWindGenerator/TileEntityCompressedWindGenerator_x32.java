package com.CompactMekanismMachines.common.tile.CompressedWindGenerator;

import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCompressedWindGenerator_x32 extends TileEntityCompressedWindGenerator<TileEntityCompressedWindGenerator_x32> {
    public TileEntityCompressedWindGenerator_x32(BlockPos pos, BlockState state) {
        super(pos, state, 32L, CompactBlocks.WIND_GENERATOR_X32);
    }
}
