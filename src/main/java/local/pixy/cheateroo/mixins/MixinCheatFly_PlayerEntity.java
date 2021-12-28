package local.pixy.cheateroo.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import local.pixy.cheateroo.config.FeatureToggle;

/**
 * @author pixy
 *
 */
@Mixin(PlayerEntity.class)
public abstract class MixinCheatFly_PlayerEntity extends LivingEntity {
	public MixinCheatFly_PlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	/**
	 * A mixin to stop the elytra movement sideways.
	 * 
	 * @param player
	 * @param movementInput
	 */
	@Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;travel(Lnet/minecraft/util/math/Vec3d/Vec3d;)V"))
	public void travelMixin(PlayerEntity player, Vec3d movementInput) {
		if (FeatureToggle.CHEAT_FLY.getBooleanValue() && player.world.isClient() && player.isFallFlying()) {
			Vec3d movement = movementInput.multiply(50);
			this.setVelocity(movement.x, 0, movement.z);
			this.move(MovementType.SELF, this.getVelocity());
		} else {
			super.travel(movementInput);
		}
	}
}
