package local.pixy.cheateroo.mixins.callback;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.screen.Screen;

import local.pixy.cheateroo.event.ClientCommandHandler;

@Mixin(Screen.class)
public abstract class OnClientChatMessage extends AbstractParentElement {
	@Shadow
	protected MinecraftClient client;

	@Inject(method = "sendMessage(Ljava/lang/String;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendChatMessage(Ljava/lang/String;)V"), cancellable = true)
	private void onSendMessage(String message, boolean addToChat, CallbackInfo ci) {
		if (ClientCommandHandler.getInstance().onClientChatMessage(client, message)) {
			ci.cancel();
		}
	}
}
