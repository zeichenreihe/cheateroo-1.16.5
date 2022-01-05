package local.pixy.cheateroo.mixins;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import local.pixy.cheateroo.config.FeatureToggle;
import local.pixy.cheateroo.util.InventoryUtils;

/**
 * @author pixy
 *
 */
@Mixin(MinecraftClient.class)
public abstract class MixinBucketPickup_MinecraftClient {
	private BlockPos currentCheckPos;
	@Shadow
	@Nullable
	private ClientWorld world;
	@Shadow
	@Nullable
	private ClientPlayerInteractionManager interactionManager;

	@Shadow
	@Nullable
	private ClientPlayerEntity player;

	@Shadow
	public abstract Entity getCameraEntity();

	@Shadow
	public abstract void doItemUse();

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;handleInputEvents()V", shift = Shift.AFTER))
	private void tick(CallbackInfo ci) {
		if (!FeatureToggle.BUCKET_FILLER.getBooleanValue())
			return;
		if (this.world == null || this.interactionManager == null || this.player == null)
			return;
		Entity entity = this.getCameraEntity();
		HitResult result = entity.raycast(6, 1, true);

		if (result.getType() != HitResult.Type.BLOCK)
			return;

//		boolean realSneaking = this.player.isSneaking();
//		this.player.setSneaking(true);
//		this.player.networkHandler
//				.sendPacket(new ClientCommandC2SPacket(this.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
//		this.player.networkHandler
//				.sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, (BlockHitResult) result));
//		this.player.networkHandler
//				.sendPacket(new ClientCommandC2SPacket(this.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
//		this.player.setSneaking(realSneaking);

		this.currentCheckPos = new BlockPos(result.getPos());
		FluidState state = this.world.getBlockState(this.currentCheckPos).getFluidState();
		if (!state.isStill())
			return;
		Fluid fluid = state.getFluid();
		if (Fluids.LAVA.matchesType(fluid) || Fluids.WATER.matchesType(fluid)) {
			InventoryUtils.swapItemToHandExecuteSwapBack(this.player, Hand.MAIN_HAND, Items.BUCKET, () -> {
				this.doItemUse();
			});
		}
	}
}
