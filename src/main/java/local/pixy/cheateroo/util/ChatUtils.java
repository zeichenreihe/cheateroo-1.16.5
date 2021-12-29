package local.pixy.cheateroo.util;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

/**
 * Helper Class for sending Chat messages and appending information on the InGameHud
 * @author pixy
 *
 */
public class ChatUtils {
	/**
	 * Sends a chat message to the server
	 * @param mc
	 * @param message
	 */
	public static void sendChatMessage(MinecraftClient mc, String message) {
		mc.player.sendChatMessage(message);
	}

	/**
	 * Sends multiple chat messages to the server
	 * @param mc
	 * @param messages
	 */
	public static void sendChatMessages(MinecraftClient mc, String[] messages) {
		for (String message : messages) {
			mc.player.sendChatMessage(message);
		}
	}

	/**
	 * Adds a chat message to the InGameHud
	 * @param mc
	 * @param message
	 */
	public static void addChatMessage(MinecraftClient mc, String message) {
		mc.inGameHud.getChatHud().addMessage(Text.of(message));
	}

	/**
	 * Adds multiple chat messages to the InGameHud
	 * @param mc
	 * @param messages
	 */
	public static void addChatMessages(MinecraftClient mc, String[] messages) {
		for (String message : messages) {
			mc.inGameHud.getChatHud().addMessage(Text.of(message));
		}
	}

	public static void addChatMessages(MinecraftClient mc, List<String> list) {
		String[] array = list.toArray(new String[list.size()]);
		ChatUtils.addChatMessages(mc, array);
	}
}
