package local.pixy.cheateroo.render;

import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RenderUtils {
	/**
	 * @author masa
	 * @param pos
	 * @param color
	 * @param expand
	 * @param buffer
	 * @param mc
	 */
	public static void drawBlockBoundingBoxOutlinesBatchedLines(BlockPos pos, Color4f color,
			double expand, BufferBuilder buffer, MinecraftClient mc)
	{
		Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();
		final double dx = cameraPos.x;
		final double dy = cameraPos.y;
		final double dz = cameraPos.z;

		double minX = pos.getX() - dx - expand;
		double minY = pos.getY() - dy - expand;
		double minZ = pos.getZ() - dz - expand;
		double maxX = pos.getX() - dx + expand + 1;
		double maxY = pos.getY() - dy + expand + 1;
		double maxZ = pos.getZ() - dz + expand + 1;

		fi.dy.masa.malilib.render.RenderUtils.drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, color, buffer);
	}
}
