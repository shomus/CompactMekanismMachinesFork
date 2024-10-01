package com.CompactMekanismMachines.client;

import com.CompactMekanismMachines.client.gui.GuiCompactFissionReactor;
import com.CompactMekanismMachines.client.gui.GuiCompactIndustrialTurbine;
import com.CompactMekanismMachines.common.CompactMekanismMachines;
import com.CompactMekanismMachines.common.registries.CompactContainerTypes;
import com.CompactMekanismMachines.common.registries.CompactTileEntityTypes;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.gui.robit.GuiRobitRepair;
import mekanism.common.registries.MekanismContainerTypes;
import mekanism.generators.client.gui.GuiGasGenerator;
import mekanism.generators.common.MekanismGenerators;
import mekanism.generators.common.registries.GeneratorsContainerTypes;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = CompactMekanismMachines.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CompactClientRegistration {
    private CompactClientRegistration(){}
    ;
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registries.MENU, helper -> {
            ClientRegistrationUtil.registerScreen(CompactContainerTypes.COMPACT_FISSION_REACTOR, GuiCompactFissionReactor::new);
            ClientRegistrationUtil.registerScreen(CompactContainerTypes.COMPACT_INDUSTRIAL_TURBINE, GuiCompactIndustrialTurbine::new);
        });
    }
}
