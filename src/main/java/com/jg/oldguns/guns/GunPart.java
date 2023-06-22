package com.jg.oldguns.guns;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public class GunPart extends Item {

	protected int type;
	protected GunPartProperties prop;
	protected String gunId;
	protected int wood;
	protected int metal;
	protected boolean steel;
	
	public GunPart(String gunId, Properties p_41383_, GunPartProperties prop, int type, int wood, int metal, boolean steel) {
		super(p_41383_);
		this.gunId = gunId;
		this.prop = prop;
		this.type = type;
		this.wood = wood;
		this.metal = metal;
		this.steel = steel;
	}

	public GunPartProperties getGunPartProperties() {
		return prop;
	}
	
	public int getGunSlot() {
		return type;
	}
	
	public void setGunId(String gunId) {
		this.gunId = gunId;
	}
	
	public String getGunId() {
		return gunId;
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

	public static class GunPartProperties {
		
		protected float damage;
		protected float power;
		protected float innacuracy;
		protected float dmgRed;
		protected float weight;
		protected int bulletsPerShoot;
		protected int range;
		protected String validSize;
		protected SoundEvent sound;
		
		public GunPartProperties() {
			this.validSize = BulletItem.SMALL;
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
		
		public GunPartProperties weight(float weight) {
			this.weight = weight;
			return this;
		}
		
		public GunPartProperties bulletsPerShoot(int bullets) {
			this.bulletsPerShoot = bullets;
			return this;
		}
		
		public GunPartProperties range(int range) {
			this.range = range;
			return this;
		}
		
		public GunPartProperties validSize(String size) {
			this.validSize = size;
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
		
		public String getValidSize() {
			return validSize;
		}

		public SoundEvent getSound() {
			return sound;
		}
		
	}
	
}
