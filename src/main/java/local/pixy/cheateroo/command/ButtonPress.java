package local.pixy.cheateroo.command;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import local.pixy.cheateroo.interfaces.IClientCommandHandler;
import local.pixy.cheateroo.util.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ButtonPress implements IClientCommandHandler{
	
	public static final List<String> COMMAND_LIST = Arrays.asList("button-press");
	
	@Override
	public void onClientCommandCall(MinecraftClient mc, String message, String[] parts, String testedCommand) {
		if(parts.length != 4)
			return;
		BlockPos pos = new BlockPos(
				NumberUtils.toInt(parts[1]),
				NumberUtils.toInt(parts[2]),
				NumberUtils.toInt(parts[3])
		);
		BlockHitResult hitResult = new BlockHitResult(mc.player.getPos(), Direction.UP, pos, false);
		mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, hitResult);
		ChatUtils.addChatMessage(mc, "pressed the button at " + pos.toString() + " feet pos is " + mc.player.getBlockPos().toString());
	}
	public List<String> getCommandNames(){
		return COMMAND_LIST;
	}
}
