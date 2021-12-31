package local.pixy.cheateroo.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import local.pixy.cheateroo.gui.GuiConfigs;

/**
 * @author pixy
 *
 */
public class ModMenuImpl implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (screen) -> {
			GuiConfigs configGui = new GuiConfigs();
			configGui.setParent(screen);
			return configGui;
		};
	}
}
