package local.pixy.cheateroo.server;

import java.util.Comparator;

import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import local.pixy.cheateroo.Cheateroo;
import local.pixy.cheateroo.config.CSToggle;

public class ChunkTicket {
	// Lasts for 1gt exactly, one per chunk
	// The ticket time is 2gt here because the ticket isn't processed until one tick
	// after it's created - it will last for exactly 1gt
	public static ChunkTicketType<ChunkPos> TICKET;

	static {
		ChunkTicket.update(20);
	}

	public static void update(int ticks) {
		ChunkTicket.TICKET = ChunkTicketType.create("cheateroo", Comparator.comparingLong(ChunkPos::toLong), ticks + 1);
	}

	public static void loadTicking(ServerWorld world, ChunkPos pos) {
		if (!CSToggle.BLOCKUPDATES_LOAD_CHUNKS.getBooleanValue())
			return;
		ServerChunkManager manager = world.getChunkManager();
		manager.addTicket(ChunkTicket.TICKET, pos, 1, pos);
	}

	public static void loadEntityTicking(ServerWorld world, ChunkPos pos) {
		if (!CSToggle.BLOCKUPDATES_LOAD_CHUNKS.getBooleanValue())
			return;
		ServerChunkManager manager = world.getChunkManager();
		manager.addTicket(ChunkTicket.TICKET, pos, 2, pos);
	}

	public static class TicketCallback implements IValueChangeCallback<ConfigInteger> {
		@Override
		public void onValueChanged(ConfigInteger config) {
			ChunkTicket.update(config.getIntegerValue());
			Cheateroo.LOGGER.info(String.format("Updated cheateroo ChunkTicket type to %d ticks!", config.getIntegerValue()));
		}

	}
}