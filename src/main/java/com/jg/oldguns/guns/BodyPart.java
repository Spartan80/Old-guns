package com.jg.oldguns.guns;

import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.ItemGun.ShootMode;

import net.minecraft.world.item.Item;

public class BodyPart extends GunPart{

	private float dmg;
	private float power;
	private float rangeDamageReduction;
	private float shootTime;
	private float range;
	private float shotgunBullets;
	private float burstShots;
	private float burstTargetTime;
	
	private boolean isShotgun;
	private boolean requiresMag;
	private boolean hasScope;
	private boolean canChageParts;
	
	private String bulletType;
	
	private Item bullet;
	
	String bulletProvider;
	
	ShootMode shootMode;
	
	public BodyPart(Properties p_i48487_1_, int gunSlot, String gunid, int wood, int metal, boolean steel, float weight,
			BodyPartConfig config) {
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

	public float getShotgunBullets() {
		return shotgunBullets;
	}

	public void setShotgunBullets(float shotgunBullets) {
		this.shotgunBullets = shotgunBullets;
	}

	public float getBurstShots() {
		return burstShots;
	}

	public void setBurstShots(float burstShots) {
		this.burstShots = burstShots;
	}

	public float getBurstTargetTime() {
		return burstTargetTime;
	}

	public void setBurstTargetTime(float burstTargetTime) {
		this.burstTargetTime = burstTargetTime;
	}

	public boolean isShotgun() {
		return isShotgun;
	}

	public void setShotgun(boolean isShotgun) {
		this.isShotgun = isShotgun;
	}

	public boolean isRequiresMag() {
		return requiresMag;
	}

	public void setRequiresMag(boolean requiresMag) {
		this.requiresMag = requiresMag;
	}

	public boolean isHasScope() {
		return hasScope;
	}

	public void setHasScope(boolean hasScope) {
		this.hasScope = hasScope;
	}

	public boolean isCanChageParts() {
		return canChageParts;
	}

	public void setCanChageParts(boolean canChageParts) {
		this.canChageParts = canChageParts;
	}

	public String getBulletType() {
		return bulletType;
	}

	public void setBulletType(String bulletType) {
		this.bulletType = bulletType;
	}

	public Item getBullet() {
		return bullet;
	}

	public void setBullet(Item bullet) {
		this.bullet = bullet;
	}

	public String getBulletProvider() {
		return bulletProvider;
	}

	public void setBulletProvider(String bulletProvider) {
		this.bulletProvider = bulletProvider;
	}

	public ShootMode getShootMode() {
		return shootMode;
	}

	public void setShootMode(ShootMode shootMode) {
		this.shootMode = shootMode;
	}
	
	public interface BodyPartConfig{
		public void config(BodyPart p);
	}
	
}
