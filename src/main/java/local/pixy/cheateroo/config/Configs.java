package local.pixy.cheateroo.config;

import java.io.File;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.config.options.ConfigString;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

import local.pixy.cheateroo.Reference;

public class Configs implements IConfigHandler {
	public static class Values {
		public static final ConfigInteger CHUNK_LOADING_TIME = new ConfigInteger("chunkLoadingTime", 20, 1, 9000, false,
				"The amount of ticks a chunk keeps loaded by the chunk load on blockupdate feature.");
		public static final ConfigString SNOOPER_URL = new ConfigString("snooperUrl", "http://localhost/mcsnoop/%s.php",
				"The address to post the snooper data to. %%%%s will be replaced with either \"client\" or \"server\".");
		public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(CHUNK_LOADING_TIME, SNOOPER_URL);
	}

	private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

	public static void loadFromFile() {
		File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

		if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
			JsonElement element = JsonUtils.parseJsonFile(configFile);

			if (element != null && element.isJsonObject()) {
				JsonObject root = element.getAsJsonObject();

				ConfigUtils.readConfigBase(root, "Values", Configs.Values.OPTIONS);
				ConfigUtils.readConfigBase(root, "GenericHotkeys", Hotkeys.HOTKEY_LIST);
				ConfigUtils.readHotkeyToggleOptions(root, "CheatHotkeys", "CheatToggles",
						ImmutableList.copyOf(FeatureToggle.values()));
				ConfigUtils.readHotkeyToggleOptions(root, "CSHotkeys", "CSToggles",
						ImmutableList.copyOf(CSToggle.values()));

				Configs.Values.CHUNK_LOADING_TIME.onValueChanged();
			}
		}
	}

	public static void saveToFile() {
		File dir = FileUtils.getConfigDirectory();

		if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
			JsonObject root = new JsonObject();

			ConfigUtils.writeConfigBase(root, "Values", Configs.Values.OPTIONS);
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
