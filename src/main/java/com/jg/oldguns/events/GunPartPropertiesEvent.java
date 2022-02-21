package com.jg.oldguns.events;

import java.util.Map;

import com.jg.oldguns.guns.properties.GunProperties;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class GunPartPropertiesEvent extends Event{

	private Map<Item, GunProperties> gunProperties;
	
	public GunPartPropertiesEvent(Map<Item, GunProperties> gunProperties) {
		this.gunProperties = gunProperties;
	}
	
	public GunProperties getGunProperties(Item item) {
		return gunProperties.get(item);
	}
	
	public Map<Item, GunProperties> getGunPartProperties(){
		return gunProperties;
	}
	
}
