package com.CompactMekanismMachines.common.config;

import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedFloatingLongValue;
import mekanism.common.config.value.CachedIntValue;
import mekanism.common.config.value.CachedLongValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class MachinesConfig extends BaseMekanismConfig {
    private final ForgeConfigSpec configSpec;
    public final CachedLongValue cfrTankCapacity;

    MachinesConfig(){
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("CompactFissionReactor Settings").push("compactfissionreactor");
        this.cfrTankCapacity = CachedLongValue.wrap(this, builder.comment("The capacity in mB of the gas tank in the Compact Fission Reactor").defineInRange("tankCapacity", 18000000L, 1L, Long.MAX_VALUE));
        builder.pop();
        this.configSpec = builder.build();
    }
    public String getFileName() {
        return "compactmachines";
    }

    public ForgeConfigSpec getConfigSpec() {
        return this.configSpec;
    }

    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }
}
