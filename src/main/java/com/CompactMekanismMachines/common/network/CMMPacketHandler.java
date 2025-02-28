package com.CompactMekanismMachines.common.network;

import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.network.packet.PacketSetRateCFR;
import mekanism.common.network.BasePacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.simple.SimpleChannel;

public class CMMPacketHandler extends BasePacketHandler {
    private final SimpleChannel netHandler = createChannel(new ResourceLocation(CompactMekanismMachines.MODID, CompactMekanismMachines.MODID), CompactMekanismMachines.instance.version);

    @Override
    protected SimpleChannel getChannel() {
        return netHandler;
    }

    @Override
    public void initialize() {
        // Client -> Server
        registerClientToServer(PacketSetRateCFR.class, PacketSetRateCFR::decode);
    }
}
