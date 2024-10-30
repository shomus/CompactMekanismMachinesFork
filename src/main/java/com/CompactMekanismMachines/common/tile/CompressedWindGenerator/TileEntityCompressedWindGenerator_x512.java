package com.CompactMekanismMachines.common.tile.CompressedWindGenerator;

import com.CompactMekanismMachines.common.registries.CompactBlockTypes;
import com.CompactMekanismMachines.common.registries.CompactBlocks;
import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityCompressedWindGenerator_x512 extends TileEntityCompressedWindGenerator<TileEntityCompressedWindGenerator_x512> {
    public TileEntityCompressedWindGenerator_x512(BlockPos pos, BlockState state) {
        super(pos, state, 512L, CompactBlocks.WIND_GENERATOR_X512);
    }
}
