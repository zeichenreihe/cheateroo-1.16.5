package local.pixy.cheateroo.event;

import java.util.ArrayList;
import java.util.List;

import local.pixy.cheateroo.interfaces.IClientCommandHandler;
import net.minecraft.client.MinecraftClient;

public class ClientCommandHandler {
	private static final ClientCommandHandler INSTANCE = new ClientCommandHandler();

	private static List<IClientCommandHandler> clientCommandHandlers = new ArrayList<>();

	public static ClientCommandHandler getInstance() {
		return INSTANCE;
	}

	public static void registerClientCommandHandler(IClientCommandHandler handler) {
		if(!ClientCommandHandler.clientCommandHandlers.contains(handler))
			ClientCommandHandler.clientCommandHandlers.add(handler);
	}

	public boolean onClientChatMessage(MinecraftClient mc, String message) {
		if(!message.startsWith("."))
			return false;
		String[] parts = message.substring(1).split(" ");
		if(ClientCommandHandler.clientCommandHandlers.isEmpty())
			return true;

		for(IClientCommandHandler handler : ClientCommandHandler.clientCommandHandlers) {
			for(String testedCommand : handler.getCommandNames()) {
				//local.pixy.cheateroo.util.ChatUtils.addChatMessage(mc, testedCommand);
				if(parts[0].equals(testedCommand)) {
					handler.onClientCommandCall(mc, message, parts, testedCommand);
				}
			}
		}
		return true;
	}
}
