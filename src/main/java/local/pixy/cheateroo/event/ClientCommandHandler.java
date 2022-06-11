package local.pixy.cheateroo.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;

import local.pixy.cheateroo.config.Configs;
import local.pixy.cheateroo.interfaces.IClientCommandHandler;

/**
 * A class that manages all client commands.
 * 
 * @author pixy
 *
 */
public class ClientCommandHandler {
	private static List<IClientCommandHandler> clientCommandHandlers = new ArrayList<>();

	/**
	 * A function to register a new client command handler to call on specific
	 * commands.
	 * 
	 * @param handler An valid client command hander that implements
	 *                {@linkplain IClientCommandHandler}.
	 */
	public static void registerClientCommandHandler(IClientCommandHandler handler) {
		if (!ClientCommandHandler.clientCommandHandlers.contains(handler))
			ClientCommandHandler.clientCommandHandlers.add(handler);
	}

	/**
	 * The function that gets called when a chat message arrives.
	 * 
	 * @apiNote DONT USE THIS, THIS IS A CALLBACK FROM A MIXIN!
	 * @param mc      The {@link MinecraftClient}.
	 * @param message The message that the client got.
	 * @return False if the chat message didn't start with the
	 *         {@linkplain local.pixy.cheateroo.config.Configs.Values#COMMAND_PREFIX}.
	 */
	public static boolean onClientChatMessage(MinecraftClient mc, String message) {
		if (!message.startsWith(Configs.Values.COMMAND_PREFIX.getStringValue()))
			return false;
		String[] parts = message.substring(1).split(" ");
		if (ClientCommandHandler.clientCommandHandlers.isEmpty())
			return true;

		for (IClientCommandHandler handler : ClientCommandHandler.clientCommandHandlers) {
			for (String testedCommand : handler.getCommandNames()) {
				if (parts[0].equals(testedCommand)) {
					handler.onClientCommandCall(mc, message, parts, testedCommand);
				}
			}
		}
		return true;
	}
}
