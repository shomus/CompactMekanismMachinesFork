package com.CompactMekanismMachines.client.gui;

import com.CompactMekanismMachines.common.tile.TileEntityCompactFissionReactor;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiNumberGauge;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.tab.GuiHeatTab;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.tile.TileEntityGasGenerator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GuiCompactFissionReactor extends GuiConfigurableTile<TileEntityCompactFissionReactor, MekanismTileContainer<TileEntityCompactFissionReactor>> {

    public GuiCompactFissionReactor(MekanismTileContainer<TileEntityCompactFissionReactor> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        titleLabelY = 5;
        inventoryLabelY += 3;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiGasGauge(() -> tile.heatedCoolantTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 122, 13));
        addRenderableWidget(new GuiGasGauge(() -> tile.wasteTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 100, 13));
        addRenderableWidget(new GuiGasGauge(() -> tile.fuelTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 45, 13));
        addRenderableWidget(new GuiFluidGauge(() -> tile.coolantFluidTank, () -> tile.getFluidTanks(null), GaugeType.STANDARD, this, 23, 13));
        addRenderableWidget(new GuiGasGauge(() -> tile.coolantGasTank, () -> tile.getGasTanks(null), GaugeType.STANDARD, this, 5, 13));
        addRenderableWidget(new GuiHeatTab(this, () -> {
            Component enviroment = MekanismUtils.getTemperatureDisplay(tile.heatCapacitor.getTemperature(), UnitDisplayUtils.TemperatureUnit.KELVIN, false);
            return Collections.singletonList(MekanismLang.TEMPERATURE.translate(enviroment));
        }));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        drawString(guiGraphics, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        Component component = GeneratorsLang.GAS_BURN_RATE.translate(tile.getUsed());
        int left = inventoryLabelX + getStringWidth(playerInventoryTitle) + 4;
        int end = imageWidth - 8;
        left = Math.max(left, end - getStringWidth(component));
        drawTextScaledBound(guiGraphics, component, left, inventoryLabelY, titleTextColor(), end - left);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}
