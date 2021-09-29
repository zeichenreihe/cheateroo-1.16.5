package local.pixy.cheateroo.gui;

import java.util.Collections;
import java.util.List;
import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import local.pixy.cheateroo.Reference;
import local.pixy.cheateroo.config.FeatureToggle;
import local.pixy.cheateroo.config.CSToggle;
import local.pixy.cheateroo.config.Hotkeys;

public class GuiConfigs extends GuiConfigsBase {
	private static ConfigGuiTab tab = ConfigGuiTab.CHEAT_TOGGLES;

	public GuiConfigs() {
		super(10, 50, Reference.MOD_ID, null, "cheateroo.gui.title.configs");
	}

	@Override
	public void initGui() {
		super.initGui();
		this.clearOptions();

		int x = 10;
		int y = 26;

		for (ConfigGuiTab tab : ConfigGuiTab.values()) {
			x += this.createButton(x, y, -1, tab);
		}
	}

	private int createButton(int x, int y, int width, ConfigGuiTab tab) {
		ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
		button.setEnabled(GuiConfigs.tab != tab);
		this.addButton(button, new ButtonListener(tab, this));

		return button.getWidth() + 2;
	}

	@Override
	protected int getConfigWidth() {
		ConfigGuiTab tab = GuiConfigs.tab;

		if (tab == ConfigGuiTab.CHEAT_TOGGLES) {
			return 80;
		}

		return super.getConfigWidth();
	}

	@Override
	protected boolean useKeybindSearch() {
		return GuiConfigs.tab == ConfigGuiTab.CHEAT_HOTKEYS || GuiConfigs.tab == ConfigGuiTab.GENERIC_HOTKEYS;
	}

	@Override
	public List<ConfigOptionWrapper> getConfigs() {
		List<? extends IConfigBase> configs;
		ConfigGuiTab tab = GuiConfigs.tab;

		if (tab == ConfigGuiTab.CHEAT_TOGGLES) {
			configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN,
					ImmutableList.copyOf(FeatureToggle.values()));
		} else if (tab == ConfigGuiTab.CHEAT_HOTKEYS) {
			configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY,
					ImmutableList.copyOf(FeatureToggle.values()));
		} else if (tab == ConfigGuiTab.GENERIC_HOTKEYS) {
			configs = Hotkeys.HOTKEY_LIST;
		} else if (tab == ConfigGuiTab.CS_TOGGLES) {
			configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN,
					ImmutableList.copyOf(CSToggle.values()));
		} else if (tab == ConfigGuiTab.CS_HOTKEYS) {
			configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY,
					ImmutableList.copyOf(CSToggle.values()));
		} else {
			return Collections.emptyList();
		}

		return ConfigOptionWrapper.createFor(configs);
	}

	private static class ButtonListener implements IButtonActionListener {
		private final GuiConfigs parent;
		private final ConfigGuiTab tab;

		public ButtonListener(ConfigGuiTab tab, GuiConfigs parent) {
			this.tab = tab;
			this.parent = parent;
		}

		@Override
		public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
			GuiConfigs.tab = this.tab;

			this.parent.reCreateListWidget(); // apply the new config width
			this.parent.getListWidget().resetScrollbarPosition();
			this.parent.initGui();
		}
	}

	public enum ConfigGuiTab {
		CHEAT_TOGGLES("cheateroo.gui.button.config_gui.cheat_toggles"),
		CHEAT_HOTKEYS("cheateroo.gui.button.config_gui.cheat_hotkeys"),
		CS_TOGGLES("cheateroo.gui.button.config_gui.cs_toggles"),
		CS_HOTKEYS("cheateroo.gui.button.config_gui.cs_hotkeys"),
		GENERIC_HOTKEYS("cheateroo.gui.button.config_gui.generic_hotkeys");

		private final String translationKey;

		private ConfigGuiTab(String translationKey) {
			this.translationKey = translationKey;
		}

		public String getDisplayName() {
			return StringUtils.translate(this.translationKey);
		}
	}
}
