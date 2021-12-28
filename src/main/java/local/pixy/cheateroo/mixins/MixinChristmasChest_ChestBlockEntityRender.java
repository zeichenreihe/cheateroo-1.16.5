package local.pixy.cheateroo.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;

import local.pixy.cheateroo.config.FeatureToggle;

/**
 * @author pixy
 *
 */
@Mixin(ChestBlockEntityRenderer.class)
public class MixinChristmasChest_ChestBlockEntityRender {
	/**
	 * A Mixin to change the texture of a chest
	 * 
	 * @param blockEntity
	 * @param type
	 * @param christmas
	 * @return the modified texture of a chest
	 */
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getChestTexture(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;"))
	private SpriteIdentifier redirect(BlockEntity blockEntity, ChestType type, boolean christmas) {
		if (FeatureToggle.OVERRIDE_CHRISTMAS_CHEST.getBooleanValue())
			christmas = FeatureToggle.OVERRIDE_CHRISTMAS_CHEST_VALUE.getBooleanValue();
		return TexturedRenderLayers.getChestTexture(blockEntity, type, christmas);
	}
}
