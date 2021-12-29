package local.pixy.cheateroo.mixins.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import local.pixy.cheateroo.server.ChunkTicket;

@Mixin(ServerWorld.class)
public abstract class MixinChunkTicket_ServerWorld {
	@Inject(method = "addEntity", at = @At("HEAD"))
	private void loadChunkOnEntityCreation(Entity entity, CallbackInfoReturnable ci) {
		ChunkTicket.loadEntityTicking((ServerWorld) (Object) this, new ChunkPos(entity.getBlockPos()));
	}
}
