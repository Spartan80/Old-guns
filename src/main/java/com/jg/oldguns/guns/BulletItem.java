package com.jg.oldguns.guns;

import net.minecraft.world.item.Item;

public class BulletItem extends Item {

	public static final String BIG = "BigBullet";
	public static final String MEDIUM = "MediumBullet";
	public static final String SMALL = "SmallBullet";
	public static final String SHOTGUN = "ShotgunBullet";

	boolean requiresIngots;
	String size;
	int metal;
	int gunPowder;

	public BulletItem(Properties p_i48487_1_, String size, int metal, int gunPowder, boolean requiresIngots) {
		super(p_i48487_1_);
		this.size = size;
		this.gunPowder = gunPowder;
		this.metal = metal;
		this.requiresIngots = requiresIngots;
	}

	public String getSize() {
		return size;
	}

	public int getMetal() {
		return metal;
	}

	public int getGunPowder() {
		return gunPowder;
	}

	public boolean requiresIngots() {
		return requiresIngots;
	}
}
