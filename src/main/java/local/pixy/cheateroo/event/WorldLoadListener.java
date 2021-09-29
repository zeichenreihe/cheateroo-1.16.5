package local.pixy.cheateroo.event;

import org.jetbrains.annotations.Nullable;

import fi.dy.masa.malilib.interfaces.IWorldLoadListener;
import local.pixy.cheateroo.util.DataStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;

public class WorldLoadListener implements IWorldLoadListener {
	@Override
	public void onWorldLoadPost(@Nullable ClientWorld worldBefore, @Nullable ClientWorld worldAfter, MinecraftClient mc) {
		DataStorage.getInstance().reset();
	}
}
