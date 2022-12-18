package com.jg.oldguns.events;

import java.util.HashMap;
import java.util.Map;

import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.handlers.GunModelsHandler;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class RegisterGunModelEvent extends Event {
	
	public RegisterGunModelEvent() {
		
	}
	
	public void register(Item item, GunModel model) {
		GunModelsHandler.register(item.getDescriptionId(), model);
	}
	
	public void get(Item item) {
		GunModelsHandler.get(item.getDescriptionId());
	}
	
	public Map<String, GunModel> getModels() {
		return GunModelsHandler.getMap();
	}
	
}
