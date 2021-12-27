package local.pixy.cheateroo.command;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import local.pixy.cheateroo.interfaces.IClientCommandHandler;
import local.pixy.cheateroo.util.ChatUtils;
import local.pixy.cheateroo.util.DataStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class HighlightBlock implements IClientCommandHandler {
	public static final List<String> COMMAND_LIST = Arrays.asList("highlight-block");

	@Override
	public void onClientCommandCall(MinecraftClient mc, String message, String[] parts, String testedCommand) {
		if(parts.length != 4)
			return;
		BlockPos pos = new BlockPos(
				NumberUtils.toInt(parts[1]),
				NumberUtils.toInt(parts[2]),
				NumberUtils.toInt(parts[3])
		);
		DataStorage.getInstance().setHighlightBlockPos(pos);
		ChatUtils.addChatMessage(mc, "Set Location to " + pos.toString());
	}

	@Override
	public List<String> getCommandNames() {
		return COMMAND_LIST;
	}

}
