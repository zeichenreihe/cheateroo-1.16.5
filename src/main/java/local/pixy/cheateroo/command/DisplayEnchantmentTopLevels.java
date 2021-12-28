package local.pixy.cheateroo.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

import local.pixy.cheateroo.interfaces.IClientCommandHandler;
import local.pixy.cheateroo.util.ChatUtils;

public class DisplayEnchantmentTopLevels implements IClientCommandHandler {

	public static final List<String> COMMAND_LIST = Arrays.asList("display-enchantments");

	@Override
	public void onClientCommandCall(MinecraftClient mc, String message, String[] parts, String testedCommand) {
		ChatUtils.addChatMessage(mc, "List of all Enchantments:");

		List<Enchantment> enchantments = new ArrayList<>();
		Registry.ENCHANTMENT.forEach(enchantments::add);

		for (Enchantment e : enchantments) {
			ChatUtils.addChatMessage(mc, e.getName(e.getMaxLevel()).getString());
		}
	}

	public List<String> getCommandNames() {
		return COMMAND_LIST;
	}
}
