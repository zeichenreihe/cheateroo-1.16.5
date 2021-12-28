package local.pixy.cheateroo.event;

import fi.dy.masa.malilib.interfaces.IClientTickHandler;

import net.minecraft.client.MinecraftClient;

import local.pixy.cheateroo.util.InventoryUtils;

public class ClientTickHandler implements IClientTickHandler {

	@Override
	public void onClientTick(MinecraftClient mc) {
		if (mc.player != null) {
			// mc.player.getHungerManager().setFoodLevel(20);
			// mc.player.getHungerManager().setSaturationLevelClient(5);

			InventoryUtils.tryRestockTotem(mc.player, false);
		}

	}

}
