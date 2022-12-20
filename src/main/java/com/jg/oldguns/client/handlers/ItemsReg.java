package com.jg.oldguns.client.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;

public enum ItemsReg {
	INSTANCE;

	private ArrayList<Supplier<? extends Item>> guns;
	private ArrayList<Supplier<? extends Item>> extraItems;
	private Map<String, ArrayList<Supplier<? extends Item>>> gunparts;

	private ItemsReg() {
		guns = new ArrayList<Supplier<? extends Item>>();
		extraItems = new ArrayList<Supplier<? extends Item>>();
		gunparts = new HashMap<String, ArrayList<Supplier<? extends Item>>>();
	}

	public void registerGun(Supplier<? extends Item> sup) {
		if (!guns.contains(sup)) {
			guns.add(sup);
		}
	}

	public void registerExtraItem(Supplier<? extends Item> sup) {
		if (!extraItems.contains(sup)) {
			extraItems.add(sup);
		}
	}

	public void registerPartForGun(Supplier<? extends Item> sup, String name) {
		if (!gunparts.containsKey(name)) {
			gunparts.put(name, new ArrayList<Supplier<? extends Item>>());
		}
		gunparts.get(name).add(sup);
	}

	public Supplier<? extends Item> getGunSup(int index) {
		return guns.get(index);
	}

	public Supplier<? extends Item> getExtraItemSup(int index) {
		return extraItems.get(index);
	}

	public Item getGun(int index) {
		return guns.get(index).get();
	}

	public Item getExtraItem(int index) {
		return extraItems.get(index).get();
	}

	public Supplier<? extends Item> getGunPartSup(String name, int index) {
		return gunparts.get(name).get(index);
	}

	public Item getItem(String name, int index) {
		return gunparts.get(name).get(index).get();
	}

	public ArrayList<Supplier<? extends Item>> getGuns() {
		return guns;
	}

	public Map<String, ArrayList<Supplier<? extends Item>>> getGunParts() {
		return gunparts;
	}

	public ArrayList<Supplier<? extends Item>> getExtraItems() {
		return extraItems;
	}
}
