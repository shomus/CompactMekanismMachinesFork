package com.CompactMekanismMachines.client.gui;

import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import mekanism.api.text.EnumColor;
import mekanism.api.text.ILangEntry;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.generators.client.gui.element.GuiStateTexture;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.MekanismGenerators;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiCompressedWindGenerator<TILE extends TileEntityCompressedWindGenerator, CONTAINER extends MekanismTileContainer<TILE>> extends GuiMekanismTile<TILE, MekanismTileContainer<TILE>> {

    public GuiCompressedWindGenerator(CONTAINER container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiInnerScreen(this, 48, 21, 80, 44, () -> {
            List<Component> list = new ArrayList<>();
            list.add(EnergyDisplay.of(tile.getEnergyContainer()).getTextComponent());
            list.add(GeneratorsLang.POWER.translate(MekanismUtils.convertToDisplay(MekanismGeneratorsConfig.generators.windGenerationMin.get()
                    .multiply(tile.getCurrentMultiplier())).toString(2)));
            list.add(GeneratorsLang.OUTPUT_RATE_SHORT.translate(EnergyDisplay.of(tile.getMaxOutput())));
            if (!tile.getActive()) {
                ILangEntry reason = tile.isBlacklistDimension() ? GeneratorsLang.NO_WIND : GeneratorsLang.SKY_BLOCKED;
                list.add(reason.translateColored(EnumColor.DARK_RED));
            }
            return list;
        }));
        addRenderableWidget(new GuiEnergyTab(this, () -> List.of(GeneratorsLang.PRODUCING_AMOUNT.translate(
                        tile.getActive() ? EnergyDisplay.of(MekanismGeneratorsConfig.generators.windGenerationMin.get().multiply(tile.getCurrentMultiplier())) : EnergyDisplay.ZERO),
                MekanismLang.MAX_OUTPUT.translate(EnergyDisplay.of(tile.getMaxOutput())))));
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 164, 15));
        addRenderableWidget(new GuiStateTexture(this, 18, 35, tile::getActive, MekanismGenerators.rl(ResourceType.GUI.getPrefix() + "wind_on.png"),
                MekanismGenerators.rl(ResourceType.GUI.getPrefix() + "wind_off.png")));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        drawString(guiGraphics, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
