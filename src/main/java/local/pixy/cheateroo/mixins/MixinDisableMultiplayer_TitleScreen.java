package local.pixy.cheateroo.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;

import local.pixy.cheateroo.Cheateroo;

/**
 * @author pixy
 *
 */
@Mixin(TitleScreen.class)
public class MixinDisableMultiplayer_TitleScreen {
	/**
	 * Mixin to disable multiplayer, so nobody can use this mod for playing at
	 * anarchy servers and stuff.
	 * @param mc 
	 * 
	 * @return the modified isMultiplayerEnabled variable
	 */
	@Redirect(method = "initWidgetsNormal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isMultiplayerEnabled()Z"))
	public boolean isMultiplayerEnabled(MinecraftClient mc) {
		return Cheateroo.allowMultiplayer();
	}
}
