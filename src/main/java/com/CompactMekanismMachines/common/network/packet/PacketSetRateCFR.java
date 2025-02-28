package com.CompactMekanismMachines.common.network.packet;

import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import mekanism.api.math.FloatingLong;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class PacketSetRateCFR implements IMekanismPacket {
    private final BlockPos tilePos;
    private final long value;

    public PacketSetRateCFR(BlockPos tilePos, long value) {
        this.tilePos = tilePos;
        this.value = value;
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        Player pl = context.getSender();
        if (pl != null) {
            TileEntityCompactFissionReactor tile = WorldUtils.getTileEntity(TileEntityCompactFissionReactor.class, pl.level(), tilePos);
            if (tile instanceof TileEntityCompactFissionReactor) {
                tile.setRateLimit(value);
            }
        }
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(tilePos);
        buffer.writeVarLong(value);
    }

    public static PacketSetRateCFR decode(FriendlyByteBuf buffer) {
        return new PacketSetRateCFR(buffer.readBlockPos(), buffer.readVarLong());
    }
}
