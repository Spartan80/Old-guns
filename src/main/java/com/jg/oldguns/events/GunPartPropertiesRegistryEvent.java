package com.jg.oldguns.events;

import java.util.function.Supplier;

import com.jg.oldguns.guns.properties.BarrelProperties;
import com.jg.oldguns.guns.properties.BodyProperties;
import com.jg.oldguns.guns.properties.GunPropertiesHandler;
import com.jg.oldguns.guns.properties.StockProperties;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class GunPartPropertiesRegistryEvent extends Event{
	
	GunPropertiesHandler handler;
	
	public GunPartPropertiesRegistryEvent() {
		this.handler = GunPropertiesHandler.INSTANCE;
	}
	
	public void addBarrel(Supplier<Item> item, BarrelProperties prop) {
		handler.getBarrels().put(item.get().getRegistryName().toString(), prop);
	}
	
	public void addBarrel(String item, BarrelProperties prop) {
		handler.getBarrels().put(item, prop);
	}
	
	public void addBody(Supplier<Item> item, BodyProperties prop) {
		handler.getBodys().put(item.get().getRegistryName().toString(), prop);
	}
	
	public void addBody(String item, BodyProperties prop) {
		handler.getBodys().put(item, prop);
	}
	
	public void addStock(Supplier<Item> item, StockProperties prop) {
		handler.getStocks().put(item.get().getRegistryName().toString(), prop);
	}
	
	public void addStock(String item, StockProperties prop) {
		//prop.gunid = ((ItemGun)Registry.ITEM.get(new ResourceLocation(item))).getGunId();
		handler.getStocks().put(item, prop);
	}

}
