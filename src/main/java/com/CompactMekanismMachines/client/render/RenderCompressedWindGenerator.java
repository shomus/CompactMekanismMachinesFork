package com.CompactMekanismMachines.client.render;

import com.CompactMekanismMachines.common.tile.TileEntityCompressedWindGenerator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.tileentity.IWireFrameRenderer;
import mekanism.client.render.tileentity.ModelTileEntityRenderer;
import mekanism.generators.client.model.ModelWindGenerator;
import mekanism.generators.common.GeneratorsProfilerConstants;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public abstract class RenderCompressedWindGenerator<TILE extends TileEntityCompressedWindGenerator> extends ModelTileEntityRenderer<TILE, ModelWindGenerator> implements IWireFrameRenderer {
    public RenderCompressedWindGenerator(BlockEntityRendererProvider.Context context) {
        super(context, ModelWindGenerator::new);
    }


    @Override
    protected void render(@NotNull TILE tile, float partialTick, @NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light, int overlayLight, ProfilerFiller profiler) {
        renderTranslated(tile, partialTick, matrix, (poseStack, angle) -> model.render(poseStack, renderer, angle, light, overlayLight, false));
    }

    @Override
    protected @NotNull String getProfilerSection() {
        return GeneratorsProfilerConstants.WIND_GENERATOR+TILE.getMultiply();
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull TILE tile) {
        return true;
    }

    @Override
    public void renderWireFrame(BlockEntity tile, float partialTick, PoseStack matrix, VertexConsumer buffer, int red, int green, int blue, int alpha) {
        if (tile instanceof TileEntityCompressedWindGenerator windGenerator) {
            renderTranslated((TILE) windGenerator, partialTick, matrix, (poseStack, angle) -> model.renderWireFrame(poseStack, buffer, angle, red, green, blue, alpha));

        }
    }

    private void renderTranslated(TILE tile, float partialTick, PoseStack matrix, CompressedWindGeneratorRender renderer) {
        matrix.pushPose();
        matrix.translate(0.5, 1.5, 0.5);
        MekanismRenderer.rotate(matrix, tile.getDirection(), 0, 180, 90, 270);
        matrix.mulPose(Axis.ZP.rotationDegrees(180));
        double angle = tile.getAngle();
        if (tile.getActive()) {
            angle = (tile.getAngle() + ((tile.getBlockPos().getY() + 4F) / TILE.SPEED_SCALED) * partialTick) % 360;
        }
        renderer.render(matrix, angle);
        matrix.popPose();
    }

    private interface CompressedWindGeneratorRender {

        void render(PoseStack poseStack, double angle);
    }
}
