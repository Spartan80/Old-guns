package com.jg.oldguns.guns.properties;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;

public class GunProperties {
	
	private Map<String, BarrelProperties> barrels;
	private Map<String, BodyProperties> bodys;
	private Map<String, StockProperties> stocks;
	
	public GunProperties() {
		barrels = new HashMap<String, BarrelProperties>();
		bodys = new HashMap<String, BodyProperties>();
		stocks = new HashMap<String, StockProperties>();
		
	}
	
	public void addBarrel(String itempath, BarrelProperties prop) {
		barrels.putIfAbsent(itempath, prop);
	}
	
	public void addBody(String itempath, BodyProperties prop) {
		bodys.putIfAbsent(itempath, prop);
	}
	
	public void addStock(String itempath, StockProperties prop) {
		stocks.putIfAbsent(itempath, prop);
	}
	
	public void addBarrel(Item item, BarrelProperties prop) {
		barrels.putIfAbsent(item.getRegistryName().toString(), prop);
	}
	
	public void addBody(Item item, BodyProperties prop) {
		bodys.putIfAbsent(item.getRegistryName().toString(), prop);
	}
	
	public void addStock(Item item, StockProperties prop) {
		stocks.putIfAbsent(item.getRegistryName().toString(), prop);
	}
	
}
