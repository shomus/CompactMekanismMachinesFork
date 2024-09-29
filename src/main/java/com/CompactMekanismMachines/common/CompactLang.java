package com.CompactMekanismMachines.common;

import mekanism.api.text.ILangEntry;
import mekanism.generators.common.MekanismGenerators;
import net.minecraft.Util;

public enum CompactLang implements ILangEntry {
    DESCRIPTION_COMPACT_FISSION_REACTOR("description","compact_fission_reactor");
    private final String key;
    CompactLang(String type, String path) {
        this(Util.makeDescriptionId(type, MekanismGenerators.rl(path)));
    }

    CompactLang(String key) {
        this.key = key;
    }


    @Override
    public String getTranslationKey() {
        return key;
    }
}
