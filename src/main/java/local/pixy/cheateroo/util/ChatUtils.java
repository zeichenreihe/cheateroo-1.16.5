package local.pixy.cheateroo.util;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtils {
	public static void sendChatMessage(MinecraftClient mc, String message) {
		mc.player.sendChatMessage(message);
	}
	public static void sendChatMessages(MinecraftClient mc, String[] messages) {
		for(String message : messages) {
			mc.player.sendChatMessage(message);
		}
	}
	
	public static void addChatMessage(MinecraftClient mc, String message) {
		mc.inGameHud.getChatHud().addMessage(Text.of(message));
	}
	public static void addChatMessages(MinecraftClient mc, String[] messages) {
		for(String message : messages) {
			mc.inGameHud.getChatHud().addMessage(Text.of(message));
		}
	}
	
	public static void addChatMessages(MinecraftClient mc, List<String> list) {
		String[] array = list.toArray(new String[list.size()]);
		ChatUtils.addChatMessages(mc, array);
	}
}
