package local.pixy.cheateroo.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import local.pixy.cheateroo.Cheateroo;
import local.pixy.cheateroo.config.FeatureToggle;

/**
 * @author pixy
 *
 */
@Mixin(LivingEntity.class)
public abstract class MixinCheatFly_LivingEntity extends Entity {
	public MixinCheatFly_LivingEntity(EntityType<?> type, World world) {
		super(type, world);
		// TODO Auto-generated constructor stub
	}

	/**
	 * A mixin to stop the elytra movement sideways.
	 * @param entity
	 * @param movementInput
	 */
	@Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/Vec3d/Vec3d;)V"))
	public void travelMixin(LivingEntity entity, Vec3d movementInput) {
		/*if(FeatureToggle.CHEAT_FLY.getBooleanValue() && entity instanceof PlayerEntity && entity.world.isClient() && entity.isFallFlying()) {
			Vec3d movement = movementInput.multiply(50);
			this.setVelocity(movement.x, 0, movement.z);
			this.move(MovementType.SELF, this.getVelocity());
		}
		else {*/
			entity.travel(movementInput);
		//}
	}

	/**
	 * is might not be required!
	 * @param entity
	 * @return
	 */
	/*@Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isFallFlying()Z"))
	private boolean isFallFlyingMixin(LivingEntity entity) {
			//entity.LOGGER.info("yay");
			//entity.LOGGER.info((!FeatureToggle.CHEAT_FLY.getBooleanValue()));
			return entity.isFallFlying() && (!FeatureToggle.CHEAT_FLY.getBooleanValue());
	}*/
}
