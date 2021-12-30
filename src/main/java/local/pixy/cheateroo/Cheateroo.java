package local.pixy.cheateroo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fi.dy.masa.malilib.event.InitializationHandler;

import net.fabricmc.api.ModInitializer;

public class Cheateroo implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

	@Override
	public void onInitialize() {
		InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
	}
}
