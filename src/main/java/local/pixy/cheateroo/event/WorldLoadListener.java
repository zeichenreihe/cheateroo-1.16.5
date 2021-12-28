package local.pixy.cheateroo.event;

import org.jetbrains.annotations.Nullable;

import fi.dy.masa.malilib.interfaces.IWorldLoadListener;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

import local.pixy.cheateroo.util.DataStorage;

public class WorldLoadListener implements IWorldLoadListener {
	@Override
	public void onWorldLoadPost(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter,
			MinecraftClient mc) {
		DataStorage.getInstance().reset();
	}
}
