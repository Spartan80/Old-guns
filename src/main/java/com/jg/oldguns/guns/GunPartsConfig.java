package com.jg.oldguns.guns;

import com.jg.oldguns.guns.ItemGun.ShootMode;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.world.item.Item;

public class GunPartsConfig {
	
	String[] names;
	
	float[] dmg;
	float[] power;
	float[] inn;
	float[] hRec;
	float[] vRec;
	float[] zRec;
	float[] recTime;
	float[] rdr;
	float[] st;
	float[] r;
	
	float shotGunB;
	float burstShots;
	float burstTargetTime;
	
	boolean isShotgun;
	boolean requiresMag;
	boolean hasScope;
	boolean canChageParts;
	
	String bulletType;
	String gunId;
	String bulletId;
	
	Sound sound;
	
	Item bullet;
	
	ShootMode shootMode;
	
	public GunPartsConfig(String[] names, RunConfig c) {
		this.names = names;
		dmg = new float[4];
		power = new float[4];
		inn = new float[4];
		hRec = new float[4];
		vRec = new float[4];
		zRec = new float[4];
		recTime = new float[4];
		rdr = new float[3];
		st = new float[3];
		r = new float[3];
		c.run(this);
	}
	
	public void setNames(String... names) {
		this.names = names;
	}
	
	public void setDmg(float... dmg) {
		this.dmg = dmg;
	}
	
	public void setPower(float... power) {
		this.power = power;
	}
	
	public void setInn(float... inn) {
		this.inn = inn;
	}
	
	public void setZRecoil(float... zRec) {
		this.zRec = zRec;
	}
	
	public void setHRecoil(float... hRec) {
		this.hRec = hRec;
	}
	
	public void setVRecoil(float... vRec) {
		this.vRec = vRec;
	}
	
	public void setRangeDamageReduct(float... rdr) {
		this.rdr = rdr;
	}
	
	public void setRecoilTime(float... recTime) {
		this.recTime = recTime;
	}
	
	public void setShootTime(float... shootTime) {
		this.st = shootTime;
	}
	
	public void setRange(float... range) {
		this.r = range;
	}
	
	public void setShotgunBullets(float sb) {
		this.shotGunB = sb;
	}
	
	public void setBurstShots(float shots) {
		this.burstShots = shots;
	}
	
	public void setShootMode(ShootMode mode) {
		this.shootMode = mode;
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

	public String getGunId() {
		return gunId;
	}

	public void setGunId(String gunId) {
		this.gunId = gunId;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public Item getBullet() {
		return bullet;
	}

	public void setBullet(Item bullet) {
		this.bullet = bullet;
	}

	public String getBulletProvider() {
		return bulletId;
	}

	public void setBulletProvider(String bulletId) {
		this.bulletId = bulletId;
	}

	public String[] getNames() {
		return names;
	}

	public float[] getDmg() {
		return dmg;
	}

	public float[] getPower() {
		return power;
	}

	public float[] getInn() {
		return inn;
	}

	public float[] gethRec() {
		return hRec;
	}

	public float[] getvRec() {
		return vRec;
	}

	public float[] getzRec() {
		return zRec;
	}

	public float[] getRecTime() {
		return recTime;
	}

	public float[] getRdr() {
		return rdr;
	}

	public float[] getSt() {
		return st;
	}

	public float[] getR() {
		return r;
	}

	public float getShotGunB() {
		return shotGunB;
	}

	public float getBurstShots() {
		return burstShots;
	}

	public float getBurstTargetTime() {
		return burstTargetTime;
	}

	public ShootMode getShootMode() {
		return shootMode;
	}

	public static interface RunConfig {
		public void run(GunPartsConfig config);
	}
	
}
