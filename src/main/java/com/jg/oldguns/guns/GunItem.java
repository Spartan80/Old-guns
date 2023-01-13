package com.jg.oldguns.guns;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public abstract class GunItem extends Item {
	
	GunStuff stuff;
	String gunId;
	
	public GunItem(String gunId, Item.Properties prop, GunStuff stuff) {
		super(prop);
		this.stuff = stuff;
		this.gunId = gunId;
	}
	
	// Getters
	
	public GunStuff getStuff() {
		return stuff;
	}
	
	public String getGunId() {
		return gunId;
	}
	
	public Item getBarrel() {
		return stuff.getBarrel();
	}
	
	public Item getBody() {
		return stuff.getBody();
	}
	
	public Item getStock() {
		return stuff.getStock();
	}
	
	// Setters
	
	public void setGunId(String gunId) {
		this.gunId = gunId;
	}
	
	// Abstract methods
	
	public abstract float getDamage();
	
	public abstract float getShootTime();
	
	public abstract float getPower();
	
	public abstract int getBulletsPerShoot();
	
	public abstract int getRange();
	
	public abstract float getRangeDamageReduction();
	
	public abstract float getInnacuracy();
	
	public abstract boolean hasScope();
	
	public abstract FireMode getFireMode();
	
	public abstract SoundEvent getShootSound();
	
}
