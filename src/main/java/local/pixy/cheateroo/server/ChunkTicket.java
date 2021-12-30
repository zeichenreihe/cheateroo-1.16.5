package local.pixy.cheateroo.server;

import java.util.Comparator;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import local.pixy.cheateroo.config.CSToggle;

public class ChunkTicket {
	// Lasts for 1gt exactly, one per chunk
	// The ticket time is 2gt here because the ticket isn't processed until one tick
	// after it's created - it will last for exactly 1gt
	public static final ChunkTicketType<ChunkPos> TICKET = ChunkTicketType.create("cheateroo",
		Comparator.comparingLong(ChunkPos::toLong), 21);

	public static void loadTicking(ServerWorld world, ChunkPos pos) {
		if(!CSToggle.BLOCKUPDATES_LOAD_CHUNKS.getBooleanValue())
			return;
		ServerChunkManager manager = world.getChunkManager();
		manager.addTicket(ChunkTicket.TICKET, pos, 1, pos);
	}

	public static void loadEntityTicking(ServerWorld world, ChunkPos pos) {
		if(!CSToggle.BLOCKUPDATES_LOAD_CHUNKS.getBooleanValue())
			return;
		ServerChunkManager manager = world.getChunkManager();
		manager.addTicket(ChunkTicket.TICKET, pos, 2, pos);
	}
}
