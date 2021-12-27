package local.pixy.cheateroo.command;

import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import local.pixy.cheateroo.interfaces.IClientCommandHandler;
import local.pixy.cheateroo.util.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class Test implements IClientCommandHandler{
	public static final List<String> COMMAND_LIST = Arrays.asList("test");

	@Override
	public void onClientCommandCall(MinecraftClient mc, String message, String[] parts, String testedCommand) {
		try {
			//InputStream is = mc.getResourcePackDownloader().getPack().open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_16x16.png"));
			//mc.getResourcePackDownloader().getPack();
			Identifier id = new Identifier("icons/icon_16x16.png");
			
			URL url = DefaultResourcePack.class.getResource("/" + ResourceType.CLIENT_RESOURCES.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath());
			
			//Path path = DefaultResourcePack.resourcePath.resolve(ResourceType.CLIENT_RESOURCES.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath());
			ChatUtils.addChatMessage(mc, "Icon Path: " + url);
		} catch (Exception e) {
			ChatUtils.addChatMessage(mc, "something went wrong." + e.getMessage());
		}
		
	}

	public List<String> getCommandNames() {
		return COMMAND_LIST;
	}
}
