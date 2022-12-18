package com.jg.oldguns.client.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jg.oldguns.client.animations.parts.GunModel;
import com.mojang.logging.LogUtils;

public class GunModelsHandler {

	private static final Map<String, GunModel> gunModels = new HashMap<>();
	private static boolean init;
	
	public static void register(String gun, GunModel gunModel) {
		gunModels.put(gun, gunModel);
		LogUtils.getLogger().info("registering gun: " + gun);
	}

	public static GunModel get(String gun) {
		if(gunModels.containsKey(gun)) {
			return gunModels.get(gun);
		}else {
			for(Entry<String, GunModel> entry : gunModels.entrySet()) {
				LogUtils.getLogger().error(entry.getKey());
			}
			//LogUtils.getLogger().error("No gunmodel linked with item: " + gun.getRegistryName().toString());
			return null;
		}
	}
	
	public static Map<String, GunModel> getMap(){
		return gunModels;
	}
	
	public static boolean getInit() {
		return init;
	}
	
	public static void setInit(boolean init) {
		GunModelsHandler.init = init;
	}
	
}
