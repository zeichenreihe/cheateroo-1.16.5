package local.pixy.cheateroo.config;

import java.io.File;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import local.pixy.cheateroo.Reference;

public class Configs implements IConfigHandler {
	private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

	public static void loadFromFile() {
		File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

		if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
			JsonElement element = JsonUtils.parseJsonFile(configFile);

			if (element != null && element.isJsonObject()) {
				JsonObject root = element.getAsJsonObject();

				ConfigUtils.readConfigBase(root, "GenericHotkeys", Hotkeys.HOTKEY_LIST);
				ConfigUtils.readHotkeyToggleOptions(root, "CheatHotkeys", "CheatToggles",
						ImmutableList.copyOf(FeatureToggle.values()));
				ConfigUtils.readHotkeyToggleOptions(root, "CSHotkeys", "CSToggles",
						ImmutableList.copyOf(CSToggle.values()));
			}
		}
	}

	public static void saveToFile() {
		File dir = FileUtils.getConfigDirectory();

		if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
			JsonObject root = new JsonObject();

			ConfigUtils.writeConfigBase(root, "GenericHotkeys", Hotkeys.HOTKEY_LIST);
			ConfigUtils.writeHotkeyToggleOptions(root, "CheatHotkeys", "CheatToggles",
					ImmutableList.copyOf(FeatureToggle.values()));
			ConfigUtils.writeHotkeyToggleOptions(root, "CSHotkeys", "CSToggles",
					ImmutableList.copyOf(CSToggle.values()));

			JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
		}
	}

	@Override
	public void load() {
		loadFromFile();
	}

	@Override
	public void save() {
		saveToFile();
	}
}
