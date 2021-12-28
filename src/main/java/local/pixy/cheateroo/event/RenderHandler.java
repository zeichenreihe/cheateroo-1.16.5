package local.pixy.cheateroo.event;

import fi.dy.masa.malilib.interfaces.IRenderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import local.pixy.cheateroo.config.FeatureToggle;
import local.pixy.cheateroo.render.OverlayRenderer;
import local.pixy.cheateroo.util.DataStorage;

public class RenderHandler implements IRenderer {
	@Override
	public void onRenderWorldLast(float partialTicks, MatrixStack matrices) {
		MinecraftClient mc = MinecraftClient.getInstance();

		if (FeatureToggle.ENABLE_RENDERING.getBooleanValue()) {
			if (FeatureToggle.HIGHLIGHT_BLOCK.getBooleanValue())
				OverlayRenderer.getInstance().renderHighlightBlock(matrices, partialTicks,
						DataStorage.getInstance().getHighlightBlockPos(), null);
		}
	}
}
