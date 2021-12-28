package local.pixy.cheateroo.config;

import java.util.List;

import com.google.common.collect.ImmutableList;

import fi.dy.masa.malilib.config.options.ConfigHotkey;

public class Hotkeys {
	public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGui", "P,C",
			"The key open the in-game config GUI");
	public static final ConfigHotkey SET_HIGHLIGHT_BLOCK = new ConfigHotkey("setHighlightBlock", "P,O",
			"The key to set the hightlighted block");

	public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(OPEN_CONFIG_GUI, SET_HIGHLIGHT_BLOCK);
}
