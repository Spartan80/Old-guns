package com.jg.oldguns.guns.properties;

import java.util.HashMap;
import java.util.Map;

public enum GunPropertiesHandler {
	INSTANCE;
	
	Map<String, BarrelProperties> barrels;
	Map<String, BodyProperties> bodys;
	Map<String, StockProperties> stocks;
	
	private GunPropertiesHandler() {
		barrels = new HashMap<String, BarrelProperties>();
		bodys = new HashMap<String, BodyProperties>();
		stocks = new HashMap<String, StockProperties>();
	}
	
	public boolean isAble() {
		return !(barrels.isEmpty() && bodys.isEmpty() && stocks.isEmpty());
	}
	
	public BarrelProperties getBarrel(String barrelPath) {
		return barrels.get(barrelPath);
	}
	
	public BodyProperties getBody(String bodyPath) {
		return bodys.get(bodyPath);
	}
	
	public StockProperties getStock(String stockPath) {
		return stocks.get(stockPath);
	}
	
	public Map<String, BarrelProperties> getBarrels() {
		return barrels;
	}
	
	public Map<String, BodyProperties> getBodys() {
		return bodys;
	}
	
	public Map<String, StockProperties> getStocks() {
		return stocks;
	}
	
}
