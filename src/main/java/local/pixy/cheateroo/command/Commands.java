package local.pixy.cheateroo.command;

import local.pixy.cheateroo.event.ClientCommandHandler;

public class Commands {
	private static ButtonPress BUTTON_PRESS;
	private static DisplayEnchantmentTopLevels DISPLAY_ENCHANTMENT_TOP_LEVELS;
	private static HighlightBlock HIGHLIGHT_BLOCK;
	private static Test TEST;

	public static void register() {
		ClientCommandHandler.registerClientCommandHandler(BUTTON_PRESS);
		ClientCommandHandler.registerClientCommandHandler(DISPLAY_ENCHANTMENT_TOP_LEVELS);
		ClientCommandHandler.registerClientCommandHandler(HIGHLIGHT_BLOCK);
		ClientCommandHandler.registerClientCommandHandler(TEST);
	}

	static {
		BUTTON_PRESS = new ButtonPress();
		DISPLAY_ENCHANTMENT_TOP_LEVELS = new DisplayEnchantmentTopLevels();
		HIGHLIGHT_BLOCK = new HighlightBlock();
		TEST = new Test();
	}
}
