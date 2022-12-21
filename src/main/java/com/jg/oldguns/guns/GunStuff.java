package com.jg.oldguns.guns;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class GunStuff {

	protected Item stock;
	protected Item body;
	protected Item barrel;
	protected Item mag;
	protected boolean canModStock;
	protected boolean canModBody;
	protected boolean canModBarrel;
	protected String[] hammers;
	
	public GunStuff() {
		
	}
	
	public GunStuff(RegistryObject<Item> stock, RegistryObject<Item> body
			, RegistryObject<Item> barrel, RegistryObject<Item> mag, String[] hammers, 
			boolean modStock, boolean modBody, boolean modBarrel) {
		this.stock = stock == null ? null : stock.get();
		this.body = body == null ? null : body.get();
		this.barrel = barrel == null ? null : barrel.get();
		this.mag = mag == null ? null : mag.get();
		this.canModStock = modStock;
		this.canModBody = modBody;
		this.canModBarrel = modBarrel;
		this.hammers = hammers;
	}
	
	public GunStuff(GunStuff stuff) {
		this.stock = stuff.getStock();
		this.body = stuff.getBody();
		this.barrel = stuff.getBarrel();
		this.mag = stuff.getMag();
		this.hammers = stuff.getHammers();
	}

	public Item getStock() {
		return stock;
	}

	public Item getBody() {
		return body;
	}

	public Item getBarrel() {
		return barrel;
	}

	public Item getMag() {
		return mag;
	}

	public String[] getHammers() {
		return hammers;
	}

	public boolean canModifyStock() {
		return canModStock;
	}

	public boolean canModifyBody() {
		return canModBody;
	}

	public boolean canModifyBarrel() {
		return canModBarrel;
	}

	public void setStock(Item stock) {
		this.stock = stock;
	}

	public void setBody(Item body) {
		this.body = body;
	}

	public void setBarrel(Item barrel) {
		this.barrel = barrel;
	}

	public void setMag(Item mag) {
		this.mag = mag;
	}

	public void setHammers(String[] hammers) {
		this.hammers = hammers;
	}
	
}
