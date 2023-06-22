package com.jg.oldguns.client.animations.parts;

import com.jg.oldguns.guns.GunStuff;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class GunModelStuff {
	
	protected ItemStack stock;
	protected ItemStack body;
	protected ItemStack barrel;
	protected ItemStack mag;
	protected String[] hammers;
	
	public GunModelStuff() {
		
	}
	
	public GunModelStuff(RegistryObject<Item> stock, RegistryObject<Item> body
			, RegistryObject<Item> barrel, RegistryObject<Item> mag, String[] hammers) {
		this.stock = stock == null ? null : new ItemStack(stock.get());
		this.body = body == null ? null : new ItemStack(body.get());
		this.barrel = barrel == null ? null : new ItemStack(barrel.get());
		this.mag = mag == null ? null : new ItemStack(mag.get());
		this.hammers = hammers;
	}
	
	public GunModelStuff(GunStuff stuff) {
		this.stock = new ItemStack(stuff.getStock());
		this.body = new ItemStack(stuff.getBody());
		this.barrel = new ItemStack(stuff.getBarrel());
		this.mag = new ItemStack(stuff.getMag());
		this.hammers = stuff.getHammers();
	}
	
	public GunModelStuff(GunModelStuff stuff) {
		this.stock = stuff.getStock();
		this.body = stuff.getBody();
		this.barrel = stuff.getBarrel();
		this.mag = stuff.getMag();
		this.hammers = stuff.getHammers();
	}

	public ItemStack getStock() {
		return stock;
	}

	public ItemStack getBody() {
		return body;
	}

	public ItemStack getBarrel() {
		return barrel;
	}

	public ItemStack getMag() {
		return mag;
	}

	public String[] getHammers() {
		return hammers;
	}

	public void setStock(ItemStack stock) {
		this.stock = stock;
	}

	public void setBody(ItemStack body) {
		this.body = body;
	}

	public void setBarrel(ItemStack barrel) {
		this.barrel = barrel;
	}

	public void setMag(ItemStack mag) {
		this.mag = mag;
	}
	
	public void setStock(Item stock) {
		this.stock = new ItemStack(stock);
	}

	public void setBody(Item body) {
		this.body = new ItemStack(body);
	}

	public void setBarrel(Item barrel) {
		this.barrel = new ItemStack(barrel);
	}

	public void setMag(Item mag) {
		this.mag = new ItemStack(mag);
	}

	public void setHammers(String[] hammers) {
		this.hammers = hammers;
	}
}
