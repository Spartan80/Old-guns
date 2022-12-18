package com.jg.oldguns.guns;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public abstract class GunItem extends Item {
	
	GunStuff stuff;
	
	public GunItem(Item.Properties prop, GunStuff stuff) {
		super(prop);
		this.stuff = stuff;
	}
	
	// Getters
	
	public GunStuff getStuff() {
		return stuff;
	}
	
	// Abstract methods
	
	public abstract float getDamage();
	
	public abstract float getPower();
	
	public abstract int getBulletsPerShoot();
	
	public abstract int getRange();
	
	public abstract float getRangeDamageReduction();
	
	public abstract float getInnacuracy();
	
	public abstract boolean hasScope();
	
	public abstract SoundEvent getShootSound();
	
}
