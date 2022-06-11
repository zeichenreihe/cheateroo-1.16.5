package local.pixy.cheateroo.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import local.pixy.cheateroo.config.FeatureToggle;

/**
 * @author pixy
 * @param <T> The type passed to the mixin.
 *
 */
@Mixin(EntityRenderer.class)
public class MixinDisplayPlayerHealth_EntityRenderer<T extends Entity> {
	/**
	 * A mixin to render the player health next to the player name.
	 * 
	 * @param entity          Object passed to the mixin.
	 * @param text            Object passed to the mixin.
	 * @param matrices        Object passed to the mixin.
	 * @param vertexConsumers Object passed to the mixin.
	 * @param light           Object passed to the mixin.
	 * @param ci              Object passed to the mixin.
	 */
	@Inject(at = @At("HEAD"), method = "renderLabelIfPresent")
	private void renderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
			int light, CallbackInfo ci) {
		if (entity instanceof PlayerEntity && FeatureToggle.DISPLAY_PLAYER_HEALTH.getBooleanValue()) {
			text = this.generateNameText((LivingEntity) entity, text);
		}
	}

	private Text generateNameText(LivingEntity entity, Text original) {
		int health = (int) entity.getHealth();
		MutableText text = new LiteralText(" ");
		text.append(Integer.toString(health));
		text.formatted(this.getFormatForHealth(health));
		return ((MutableText) original).append(text);
	}

	private Formatting getFormatForHealth(int health) {
		if (health <= 5)
			return Formatting.DARK_RED;
		if (health <= 10)
			return Formatting.GOLD;
		if (health <= 15)
			return Formatting.YELLOW;
		return Formatting.GREEN;
	}
}
