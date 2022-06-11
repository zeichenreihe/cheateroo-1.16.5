package local.pixy.cheateroo.interfaces;

import java.util.List;

import net.minecraft.client.MinecraftClient;

import local.pixy.cheateroo.event.ClientCommandHandler;

/**
 * An interface to implement, if you want a hander that can be called by the {@linkplain ClientCommandHandler}.
 * @author pixy
 *
 */
public interface IClientCommandHandler {
	/**
	 * The callback that is called when a message arrives.
	 * @param mc The {@linkplain MinecraftClient} instance.
	 * @param message The message as a string.
	 * @param parts The message as an array, split at spaces.
	 * @param testedCommand The command that triggered the callback.
	 */
	public void onClientCommandCall(MinecraftClient mc, String message, String[] parts, String testedCommand);

	/**
	 * @return A {@linkplain List} that contains all the commands to trigger on.
	 */
	public List<String> getCommandNames();
}
