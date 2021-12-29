package local.pixy.cheateroo.mixins.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import local.pixy.cheateroo.server.ChunkTicket;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MixinChunkTicket_AbstractBlockState {
	@Inject(method = "neighborUpdate", at = @At("HEAD"))
	private void loadChunkOnUpdate(World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify,
			CallbackInfo ci) {
		// Only on the server
		if (world.isClient())
			return;

		// Only across chunk borders
		ChunkPos src = new ChunkPos(fromPos);
		ChunkPos dest = new ChunkPos(pos);
		if (src.equals(dest))
			return;

		ChunkTicket.loadTicking((ServerWorld) world, dest);
	}
}
