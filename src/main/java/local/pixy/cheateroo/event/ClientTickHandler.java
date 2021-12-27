package local.pixy.cheateroo.event;

import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import local.pixy.cheateroo.config.FeatureToggle;
import local.pixy.cheateroo.util.InventoryUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class ClientTickHandler implements IClientTickHandler {

	@Override
	public void onClientTick(MinecraftClient mc) {
		if(mc.player != null) {
			//mc.player.getHungerManager().setFoodLevel(20);
			//mc.player.getHungerManager().setSaturationLevelClient(5);
			
			InventoryUtils.tryRestockTotem(mc.player, false);
		}

	}

}
