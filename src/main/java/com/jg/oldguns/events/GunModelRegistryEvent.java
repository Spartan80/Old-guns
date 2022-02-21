package com.jg.oldguns.events;

import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.models.GunModel;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class GunModelRegistryEvent extends Event{

	public GunModelRegistryEvent() {
		
	}
	
	public void registerGunModel(Item gun, GunModel gunModel) {
		System.out.println("registering gundmodels");
		ModelHandler.INSTANCE.registerGunModel(gun, gunModel);
	}
	
}
