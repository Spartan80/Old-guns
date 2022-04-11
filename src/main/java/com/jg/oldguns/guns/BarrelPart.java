package com.jg.oldguns.guns;

import net.minecraft.client.resources.sounds.Sound;

public class BarrelPart extends GunPart {

	private float dmg;
	private float power;
	private float rangeDamageReduction;
	private float shootTime;
	private float range;
	private Sound sound;
	
	public BarrelPart(Properties p_i48487_1_, int gunSlot, String gunid, int wood, int metal, boolean steel,
			float weight, BarrelPartConfig config) {
		super(p_i48487_1_, gunSlot, gunid, wood, metal, steel, weight);
		config.config(this);
	}

	public float getDmg() {
		return dmg;
	}

	public void setDmg(float dmg) {
		this.dmg = dmg;
	}

	public float getPower() {
		return power;
	}

	public void setPower(float power) {
		this.power = power;
	}

	public float getRangeDamageReduction() {
		return rangeDamageReduction;
	}

	public void setRangeDamageReduction(float rangeDamageReduction) {
		this.rangeDamageReduction = rangeDamageReduction;
	}

	public float getShootTime() {
		return shootTime;
	}

	public void setShootTime(float shootTime) {
		this.shootTime = shootTime;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}
	
	public interface BarrelPartConfig{
		public void config(BarrelPart p);
	}
	
}
