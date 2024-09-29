package com.CompactMekanismMachines.common.config;

import com.google.common.cache.Cache;
import mekanism.api.math.FloatingLong;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.*;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.security.DrbgParameters;

public class MachinesConfig extends BaseMekanismConfig {
    private final ForgeConfigSpec configSpec;
    public final CachedLongValue cfrFuelTankCapacity;
    public final CachedLongValue cfrCoolantGasTankCapacity;
    public final CachedIntValue  cfrCoolantFluidTankCapacity;
    public final CachedLongValue cfrHeatedCoolantTankCapacity;
    public final CachedDoubleValue cfrHeatTankCpacity;
    public final CachedLongValue cfrWasteTankCapacity;
    public final CachedLongValue cfrBurnRate;
    public final CachedLongValue cfrEnergyCapacity;

    MachinesConfig(){
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("CompactFissionReactor Settings").push("compactfissionreactor");
        this.cfrFuelTankCapacity = CachedLongValue.wrap(this,         builder.comment("The capacity in mB of the gas tank of fuel in the Compact Fission Reactor").defineInRange("tankCapacity", 18000000L, 1L, Long.MAX_VALUE));
        this.cfrCoolantGasTankCapacity = CachedLongValue.wrap(this,      builder.comment("The capacity in mB of the gas coolant tank of fuel in the Compact Fission Reactor").defineInRange("tankCapacity", 18000000L, 1L, Long.MAX_VALUE));
        this.cfrCoolantFluidTankCapacity = CachedIntValue.wrap(this,      builder.comment("The capacity in mB of the fluid coolant tank of fuel in the Compact Fission Reactor").defineInRange("tankCapacity", 18000000, 1, Integer.MAX_VALUE));
        this.cfrHeatedCoolantTankCapacity = CachedLongValue.wrap(this,builder.comment("The capacity in mB of the heated coolant tank of fuel in the Compact Fission Reactor").defineInRange("tankCapacity", 18000000L, 1L, Long.MAX_VALUE));
        this.cfrWasteTankCapacity = CachedLongValue.wrap(this,builder.comment("The capacity in mB of the waste tank of fuel in the Compact Fission Reactor").defineInRange("tankCapacity", 18000000L, 1L, Long.MAX_VALUE));
        this.cfrBurnRate = CachedLongValue.wrap(this,                 builder.comment("Max fuel cosume per tick of Compact Fission Reactor").defineInRange("burnrate",1920,1,Long.MAX_VALUE));
        this.cfrEnergyCapacity = CachedLongValue.wrap(this,           builder.comment("Energy Capacity of Compact Fission Reactor").defineInRange("energycapacity",2500000000L,1L,Long.MAX_VALUE));
        this.cfrHeatTankCpacity = MekanismGeneratorsConfig.generators.fissionCasingHeatCapacity;
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
