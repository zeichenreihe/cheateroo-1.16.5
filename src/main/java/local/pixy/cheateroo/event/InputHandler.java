package local.pixy.cheateroo.event;

import com.google.common.collect.ImmutableList;

import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.IKeyboardInputHandler;
import fi.dy.masa.malilib.hotkeys.IMouseInputHandler;
import local.pixy.cheateroo.Reference;
import local.pixy.cheateroo.config.Configs;
import local.pixy.cheateroo.config.FeatureToggle;
import local.pixy.cheateroo.config.Hotkeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.options.GameOptions;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler {
	private static final InputHandler INSTANCE = new InputHandler();
	private LeftRight lastSidewaysInput = LeftRight.NONE;
	private ForwardBack lastForwardInput = ForwardBack.NONE;

	private InputHandler() {
		super();
	}

	public static InputHandler getInstance() {
		return INSTANCE;
	}

	@Override
	public void addKeysToMap(IKeybindManager manager) {
		for (FeatureToggle toggle : FeatureToggle.values()) {
			manager.addKeybindToMap(toggle.getKeybind());
		}

		for (IHotkey hotkey : Hotkeys.HOTKEY_LIST) {
			manager.addKeybindToMap(hotkey.getKeybind());
		}
	}

	@Override
	public void addHotkeys(IKeybindManager manager) {

		manager.addHotkeysForCategory(Reference.MOD_NAME, "cheateroo.hotkeys.category.generic_hotkeys",
				Hotkeys.HOTKEY_LIST);
		manager.addHotkeysForCategory(Reference.MOD_NAME, "cheateroo.hotkeys.category.cheat_toggle_hotkeys",
				ImmutableList.copyOf(FeatureToggle.values()));
	}

	/*
	 * @Override public boolean onKeyInput(int keyCode, int scanCode, int modifiers,
	 * boolean eventKeyState) { MinecraftClient mc = MinecraftClient.getInstance();
	 * 
	 * return true; }
	 */

	public void handleMovementKeys(Input movement) {
		GameOptions settings = MinecraftClient.getInstance().options;

		if (settings.keyLeft.isPressed() && settings.keyRight.isPressed()) {
			if (this.lastSidewaysInput == LeftRight.LEFT) {
				movement.movementSideways = 1;
				movement.pressingLeft = true;
				movement.pressingRight = false;
			} else if (this.lastSidewaysInput == LeftRight.RIGHT) {
				movement.movementSideways = -1;
				movement.pressingLeft = false;
				movement.pressingRight = true;
			}
		}

		if (settings.keyBack.isPressed() && settings.keyForward.isPressed()) {
			if (this.lastForwardInput == ForwardBack.FORWARD) {
				movement.movementForward = 1;
				movement.pressingForward = true;
				movement.pressingBack = false;
			} else if (this.lastForwardInput == ForwardBack.BACK) {
				movement.movementForward = -1;
				movement.pressingForward = false;
				movement.pressingBack = true;
			}
		}
	}

	public enum LeftRight {
		NONE, LEFT, RIGHT
	}

	public enum ForwardBack {
		NONE, FORWARD, BACK
	}
}
