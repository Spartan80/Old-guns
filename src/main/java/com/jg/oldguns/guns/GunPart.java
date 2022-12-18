package com.jg.oldguns.guns;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public class GunPart extends Item {

	protected int type;
	protected GunPartProperties prop;
	
	public GunPart(Properties p_41383_, GunPartProperties prop, int type) {
		super(p_41383_);
		this.prop = prop;
		this.type = type;
	}

	public GunPartProperties getGunPartProperties() {
		return prop;
	}
	
	public int getType() {
		return type;
	}
	
	public static class GunPartProperties {
		
		protected float damage;
		protected float power;
		protected float innacuracy;
		protected float dmgRed;
		protected int bulletsPerShoot;
		protected int range;
		protected SoundEvent sound;
		
		public GunPartProperties() {
			
		}
		
		public GunPartProperties damage(float dmg) {
			this.damage = dmg;
			return this;
		}
		
		public GunPartProperties power(float power) {
			this.power = power;
			return this;
		}
		
		public GunPartProperties innacuracy(float inna) {
			this.innacuracy = inna;
			return this;
		}
		
		public GunPartProperties damageRed(float dmgRed) {
			this.dmgRed = dmgRed;
			return this;
		}
		
		public GunPartProperties bulletsPerShoot(int bullets) {
			this.bulletsPerShoot = bullets;
			return this;
		}
		
		public GunPartProperties dmgRed(int range) {
			this.range = range;
			return this;
		}
		
		public GunPartProperties shootSound(SoundEvent sound) {
			this.sound = sound;
			return this;
		}

		public float getDamage() {
			return damage;
		}

		public float getPower() {
			return power;
		}

		public float getInnacuracy() {
			return innacuracy;
		}

		public float getDmgRed() {
			return dmgRed;
		}

		public int getBulletsPerShoot() {
			return bulletsPerShoot;
		}

		public int getRange() {
			return range;
		}

		public SoundEvent getSound() {
			return sound;
		}
		
	}
	
}
