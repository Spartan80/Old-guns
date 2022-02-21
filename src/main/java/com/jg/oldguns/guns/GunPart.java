package com.jg.oldguns.guns;

import net.minecraft.item.Item;

public class GunPart extends Item {

	private int gunSlot;
	private String gunId;

	private int wood;
	private int metal;
	private boolean steel;
	
	public GunPart(Properties p_i48487_1_, int gunSlot, String gunid, int wood, int metal, boolean steel) {
		super(p_i48487_1_);
		this.gunSlot = gunSlot;
		this.gunId = gunid;
		this.wood = wood;
		this.metal = metal;
		this.steel = steel;
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

}
