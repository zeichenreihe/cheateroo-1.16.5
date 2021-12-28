package local.pixy.cheateroo.config;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;

import net.minecraft.client.MinecraftClient;

import local.pixy.cheateroo.gui.GuiConfigs;

public class Callbacks {

	public static void init(MinecraftClient mc) {

		IHotkeyCallback callbackGeneric = new KeyCallbackHotkeysGeneric(mc);
		IHotkeyCallback callbackMessage = new KeyCallbackHotkeyWithMessage(mc);

		Hotkeys.OPEN_CONFIG_GUI.getKeybind().setCallback(callbackGeneric);
		Hotkeys.SET_HIGHLIGHT_BLOCK.getKeybind().setCallback(callbackGeneric);

	}

	public static class KeyCallbackHotkeyWithMessage implements IHotkeyCallback {
		private final MinecraftClient mc;

		public KeyCallbackHotkeyWithMessage(MinecraftClient mc) {
			this.mc = mc;
		}

		@Override
		public boolean onKeyAction(KeyAction action, IKeybind key) {
			return false;
		}
	}

	private static class KeyCallbackHotkeysGeneric implements IHotkeyCallback {
		private final MinecraftClient mc;

		public KeyCallbackHotkeysGeneric(MinecraftClient mc) {
			this.mc = mc;
		}

		@Override
		public boolean onKeyAction(KeyAction action, IKeybind key) {
			if (key == Hotkeys.OPEN_CONFIG_GUI.getKeybind()) {
				GuiBase.openGui(new GuiConfigs());
				return true;
			}
			return false;
		}
	}
}