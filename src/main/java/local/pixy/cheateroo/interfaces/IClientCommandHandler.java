package local.pixy.cheateroo.interfaces;

import java.util.List;

import net.minecraft.client.MinecraftClient;

public interface IClientCommandHandler {
	public void onClientCommandCall(MinecraftClient mc, String message, String[] parts, String testedCommand);

	public List<String> getCommandNames();
}
