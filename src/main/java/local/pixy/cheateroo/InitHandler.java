package local.pixy.cheateroo;

import net.minecraft.client.MinecraftClient;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.event.RenderEventHandler;
import fi.dy.masa.malilib.event.TickHandler;
import fi.dy.masa.malilib.event.WorldLoadHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import fi.dy.masa.malilib.interfaces.IRenderer;
import local.pixy.cheateroo.command.Commands;
import local.pixy.cheateroo.config.Callbacks;
import local.pixy.cheateroo.config.Configs;
import local.pixy.cheateroo.event.ClientTickHandler;
import local.pixy.cheateroo.event.InputHandler;
import local.pixy.cheateroo.event.RenderHandler;
import local.pixy.cheateroo.event.WorldLoadListener;

public class InitHandler implements IInitializationHandler {
	@Override
	public void registerModHandlers() {
		ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Configs());

		InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
		InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.getInstance());
		InputEventHandler.getInputManager().registerMouseInputHandler(InputHandler.getInstance());

		IRenderer renderer = new RenderHandler();
		RenderEventHandler.getInstance().registerGameOverlayRenderer(renderer);
		RenderEventHandler.getInstance().registerTooltipLastRenderer(renderer);
		RenderEventHandler.getInstance().registerWorldLastRenderer(renderer);

		TickHandler.getInstance().registerClientTickHandler(new ClientTickHandler());
		WorldLoadHandler.getInstance().registerWorldLoadPreHandler(new WorldLoadListener());
		
		Commands.register();

		Callbacks.init(MinecraftClient.getInstance());
	}
}
