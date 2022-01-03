package local.pixy.cheateroo.mixins.csfeature;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.Gson;

import net.minecraft.util.snooper.Snooper;

import local.pixy.cheateroo.Cheateroo;
import local.pixy.cheateroo.Reference;
import local.pixy.cheateroo.config.CSToggle;
import local.pixy.cheateroo.util.NetworkUtils;

import net.fabricmc.fabric.mixin.event.lifecycle.WorldMixin;

/**
 * @author pixy
 *
 */
@Mixin(Snooper.class)
public abstract class MixinSnooperFeature_Snooper {
	@Shadow
	private Map<String, Object> initialInfo;
	@Shadow
	private Map<String, Object> info;
	@Shadow
	private boolean active;

	@Shadow
	public abstract void update();

	@Shadow
	private Timer timer;
	@Shadow
	private Object syncObject;
	@Shadow
	private String token;
	@Shadow
	private URL snooperUrl;

	private int counter;

	@ModifyConstant(method = "<init>", constant = @Constant(stringValue = "http://snoop.minecraft.net/"))
	public String replaceMojangSnooperWithCustomAddress(String oldAddress) {
		String newAddress = "http://localhost/mcsnoop/";
		try {
			@SuppressWarnings("unused")
			URL url = new URL(newAddress);
		} catch (MalformedURLException e) {
			newAddress = oldAddress;
		}

		Cheateroo.LOGGER.debug("old snooper address was: " + oldAddress + "; new address is " + newAddress);
		return newAddress;
	}
	
	@ModifyConstant(method = "<init>", constant = @Constant(stringValue = "?version="))
	public String replaceFileEnding(String oldFileending) {
		return ".php" + oldFileending;
	}

	@Inject(method = "method_5482", at = @At("HEAD"))
	public void setupTimer(CallbackInfo ci) {
		if (!this.active) {
			this.active = true;
			this.update();
			this.timer.schedule(new MixinSnooperFeature_Snooper.Task(), 0, 90000);
		}
	}

	/**
	 * Sends the data in the hash map to the server
	 * 
	 * @param data
	 */
	protected void sendData(HashMap<String, Object> data) {
		// We don't want to send packets to minecraft.net.
		if (this.snooperUrl.getHost() == "snoop.minecraft.net")
			return;

		Map<String, String> sendData = new LinkedHashMap<String, String>();
		Entry<String, Object> entry;
		Iterator<Entry<String, Object>> i = data.entrySet().iterator();
		while (i.hasNext()) {
			entry = i.next();
			sendData.put(entry.getKey(), entry.getValue().toString());
		}

		// Cheateroo.LOGGER.debug(sendData.toString());
		Gson gson = new Gson();
		String jsonString = gson.toJson(sendData);

		NetworkUtils.doTask(() -> {
			NetworkUtils.sendData(this.snooperUrl, jsonString, "application/json",
					NetworkUtils.getUserAgent(Reference.MOD_ID), "GET");
		});
	}

	private class Task extends TimerTask {
		@Override
		public void run() {
			if (CSToggle.CUSTOM_SNOOPER.getBooleanValue()) {
				HashMap<String, Object> data;
				synchronized (MixinSnooperFeature_Snooper.this.syncObject) {
					data = new HashMap<String, Object>(MixinSnooperFeature_Snooper.this.info);
					if (MixinSnooperFeature_Snooper.this.counter == 0)
						data.putAll(MixinSnooperFeature_Snooper.this.initialInfo);
					data.put("snooper_count", MixinSnooperFeature_Snooper.this.counter++);
					data.put("snooper_token", MixinSnooperFeature_Snooper.this.token);
				}
				MixinSnooperFeature_Snooper.this.sendData(data);
			}
		}
	}
}
