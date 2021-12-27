package local.pixy.cheateroo.render;

import org.jetbrains.annotations.Nullable;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class OverlayRenderer {
	private static final OverlayRenderer INSTANCE = new OverlayRenderer();

	private final MinecraftClient mc;

	private OverlayRenderer() {
		this.mc = MinecraftClient.getInstance();
	}
	public static OverlayRenderer getInstance() {
		return INSTANCE;
	}

	public void renderHighlightBlock(MatrixStack matrices, float partialTicks, BlockPos pos, @Nullable BlockPos lookPos) {
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.disableLighting();
		RenderSystem.disableTexture();
		matrices.push();

		RenderSystem.lineWidth(2f);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_LINES, VertexFormats.POSITION_COLOR);

		
		Color4f color = new Color4f(1,0,0,1);
		RenderUtils.drawBlockBoundingBoxOutlinesBatchedLines(pos, color, 0.002, buffer, this.mc);

		tessellator.draw();
		
		matrices.pop();
		RenderSystem.enableTexture();
		RenderSystem.enableCull();
		RenderSystem.enableLighting();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();	
	}
}
