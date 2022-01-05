package local.pixy.cheateroo.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.screen.Screen;

import local.pixy.cheateroo.config.FeatureToggle;

/**
 * @author pixy
 *
 */
@Mixin(Keyboard.class)
public class MixinDisableNarratorButton_Keyboard {
	@Redirect(method = "onKey", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;hasControlDown()Z", ordinal = 1))
	private boolean redirect() {
		if (FeatureToggle.DISABLE_NARRATOR_BUTTON.getBooleanValue())
			return false;
		return Screen.hasControlDown();
	}
}
