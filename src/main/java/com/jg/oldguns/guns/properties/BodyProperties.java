package com.jg.oldguns.guns.properties;

import java.util.function.Supplier;

import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.ItemGun.ShootMode;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class BodyProperties extends GunPartProperties<BodyProperties>{
	
	public static final BodyProperties EMPTY = new BodyProperties().setBullet(() -> Items.AIR).setBulletProvider(Bullet::new).setMagItem(null).setShootMode(ShootMode.NONE);
	
	ShootMode mode;
	
	float rangeRed;
	float burstTargeTime;

	double dmg;
	double power;
	
	int shootTime;
	int range;
	int shotgunBullets;
	int maxAmmo;
	int burstShots;
	
	boolean isShotgun;
	
	boolean reqMag;
	
	Supplier<Item> magPath;
	Supplier<Item> bullet;
	
	Bullet proj;
	
	BulletProvider<? extends Bullet> bulletProvider;
	
	public BodyProperties() {
		
	}

	public void initBullet(PlayerEntity player, World world) {
		this.proj = bulletProvider.create(player, world);
	}
	
	public BodyProperties setShootMode(ShootMode mode) {
		this.mode = mode;
		return this;
	}
	
	public BodyProperties setDamage(double damage) {
		this.dmg = damage;
		return this;
	}
	
	public BodyProperties setPower(double power) {
		this.power = power;
		return this;
	}
	
	public BodyProperties setRangeReduction(float rangeRed) {
		this.rangeRed = rangeRed;
		return this;
	}

	public BodyProperties setBurstTargeTime(float burstTargeTime) {
		this.burstTargeTime = burstTargeTime;
		return this;
	}
	
	public BodyProperties setShootTime(int time) {
		this.shootTime = time;
		return this;
	}
	
	public BodyProperties setRange(int range) {
		this.range = range;
		return this;
	}
	
	public BodyProperties setShotgunBullets(int bullets) {
		this.shotgunBullets = bullets;
		return this;
	}
	
	public BodyProperties setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
		return this;
	}
	
	public BodyProperties setBurstShots(int burstShots) {
		this.burstShots = burstShots;
		return this;
	}
	
	public BodyProperties setIsShotgun(boolean isShotgun) {
		this.isShotgun = isShotgun;
		return this;
	}
	
	public BodyProperties setRequiresMag(boolean requiresMag) {
		this.reqMag = requiresMag;
		return this;
	}
	
	public BodyProperties setMagItem(Supplier<Item> mag) {
		this.magPath = mag;
		return this;
	}
	
	public BodyProperties setBullet(Supplier<Item> bullet) {
		this.bullet = bullet;
		return this;
	}
	
	public BodyProperties setBulletProvider(BulletProvider<? extends Bullet> bulletProvider) {
		this.bulletProvider = bulletProvider;
		return this;
	}

	public ShootMode getMode() {
		return mode;
	}

	public float getRangeRed() {
		return rangeRed;
	}

	public float getBurstTargeTime() {
		return burstTargeTime;
	}
	
	public double getDamage() {
		return dmg;
	}

	public double getPower() {
		return power;
	}

	public int getShootTime() {
		return shootTime;
	}

	public int getRange() {
		return range;
	}

	public int getShotgunBullets() {
		return shotgunBullets;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}
	
	public int getBurstShots() {
		return burstShots;
	}


	public boolean isShotgun() {
		return isShotgun;
	}

	public boolean isReqMag() {
		return reqMag;
	}

	public Supplier<Item> getMagPath() {
		return magPath;
	}

	public Supplier<Item> getBullet() {
		return bullet;
	}

	public Bullet getProj() {
		return this.proj;
	}

	public BulletProvider<? extends Bullet> getBulletProvider() {
		return bulletProvider;
	}
	
	public interface BulletProvider<T extends Bullet>{
		T create(PlayerEntity player, World world);
	}
	
}
