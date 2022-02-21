package com.jg.oldguns.guns.properties;

import com.jg.oldguns.registries.SoundRegistries;

import net.minecraft.util.SoundEvent;

public class BarrelProperties extends GunPartProperties<BarrelProperties>{
	
	public static final BarrelProperties EMPTY = new BarrelProperties().setShootSound(SoundRegistries.KARKSHOOT.get());
	
	int range;
	
	float rangeRed;
	
	double power;
	double damage;
	
	SoundEvent shootSound;
	
	public BarrelProperties() {
		
	}
	
	public BarrelProperties setRange(int range) {
		this.range = range;
		return this;
	}
	
	public BarrelProperties setRangeReduction(float rangeRed) {
		this.rangeRed = rangeRed;
		return this;
	}
	
	public BarrelProperties setPower(double power) {
		this.power = power;
		return this;
	}
	
	public BarrelProperties setDamage(double damage) {
		this.damage = damage;
		return this;
	}
	
	public BarrelProperties setShootSound(SoundEvent sound) {
		this.shootSound = sound;
		return this;
	}

	public int getRange() {
		return range;
	}

	public float getRangeRed() {
		return rangeRed;
	}

	public double getPower() {
		return power;
	}

	public double getDamage() {
		return damage;
	}

	public SoundEvent getShootSound() {
		return shootSound;
	}
	
}
