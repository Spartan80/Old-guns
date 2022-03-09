package com.jg.oldguns.guns;

import net.minecraft.world.item.Item;

public class GunPart extends Item {

	public static final float LIGHT = 0.66f;
	public static final float MEDIUM = 1.16f;
	public static final float HEAVY = 1.66f;
	public static final float TOOHEAVY = 2.33f;
	
	private float weight;
	private int gunSlot;
	private String gunId;

	private int wood;
	private int metal;
	private boolean steel;
	
	public GunPart(Properties p_i48487_1_, int gunSlot, String gunid, int wood, int metal, boolean steel, float weight) {
		super(p_i48487_1_);
		this.gunSlot = gunSlot;
		this.gunId = gunid;
		this.wood = wood;
		this.metal = metal;
		this.steel = steel;
		this.weight = weight;
	}

	public int getGunSlot() {
		return this.gunSlot;
	};

	public String getGunId() {
		return this.gunId;
	}

	public int getWood() {
		return wood;
	}

	public int getMetal() {
		return metal;
	}

	public boolean isSteel() {
		return steel;
	}
	
	public float getWeight() {
		return weight;
	}

}
