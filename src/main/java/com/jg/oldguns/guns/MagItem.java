package com.jg.oldguns.guns;

import net.minecraft.world.item.Item;

public class MagItem extends Item {

	protected int maxAmmo;
	protected String[] acceptedBullets;
	
	public MagItem(Properties p_41383_, int maxAmmo, String[] acceptedBullets) {
		super(p_41383_);
		this.maxAmmo = maxAmmo;
		this.acceptedBullets = acceptedBullets;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public String[] getAcceptedBullets() {
		return acceptedBullets;
	}
	
}
