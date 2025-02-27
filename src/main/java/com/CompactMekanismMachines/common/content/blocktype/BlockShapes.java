package com.CompactMekanismMachines.common.content.blocktype;

import mekanism.common.util.EnumUtils;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BlockShapes {

    private BlockShapes() {
    }

    private static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return Block.box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static final VoxelShape[] COMPACT_FISSION_REACTOR = new VoxelShape[EnumUtils.HORIZONTAL_DIRECTIONS.length];

    static {
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
                box(4, 12, 13, 12, 13, 14), // port_connector_south1
                box(4, 11.3536, 12.6464, 12, 12.8536, 13.6464), // port_connector_south2
                box(0.5, 12, 4, 2, 13, 12), // port_connector_west1
                box(2, 12, 4, 3, 13, 12), // port_connector_west2
                box(1, 5, 4, 2, 5, 12), // bottom_connector1
                box(4, 5, 14, 12, 5, 15), // bottom_connector2
                box(14, 5, 4, 15, 5, 12), // bottom_connector3
                box(4, 5, 1, 12, 5, 2), // bottom_connector4
                box(4, 12, 2, 12, 13, 3), // port_connector_north1
                box(4, 10.6464, 2.0607, 12, 12.1464, 3.0607), // port_connector_north2
                box(12.8431, 17.0858, 4, 14.3431, 18.0858, 12), // port_connector_east1
                box(13, 12, 4, 14, 13, 12), // port_connector_east2
                box(3, 6, 3, 13, 16, 13), // chamber
                box(12, 5, 1, 15, 14, 4), // tank1
                box(1, 5, 1, 4, 14, 4), // tank4
                box(1, 5, 12, 4, 14, 15), // tank3
                box(12, 5, 12, 15, 14, 15), // tank2
                box(0, 0, 0, 16, 4, 16), // base
                box(2, 4, 2, 14, 5, 14), // base_platform
                box(4, 4, 0, 12, 12, 1), // port_north
                box(15, 4, 4, 16, 12, 12), // port_east
                box(4, 4, 15, 12, 12, 16), // port_south
                box(0, 4, 4, 1, 12, 12) // port_west
        ), COMPACT_FISSION_REACTOR);
    }
}
