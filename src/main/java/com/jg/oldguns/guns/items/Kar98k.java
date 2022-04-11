package com.jg.oldguns.guns.items;

import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.ItemBullet;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.RenderUtil;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class Kar98k extends ItemGun {

	public Kar98k(Properties prop, RegistryObject<Item> mag) {
		super(prop, mag);

	}
	
	@Override
	public Item getBarrel() {
		return ItemRegistries.Kar98kBarrel.get();
	}

	@Override
	public Item getBarrelMouth() {
		return null;
	}

	@Override
	public float getBaseSpeedModifier() {
		return 0f;
	}

	@Override
	public Item getBody() {
		return ItemRegistries.Kar98kBody.get();
	}

	@Override
	public String getGunId() {
		return "kark";
	}

	@Override
	public ResourceLocation getNullPartsResLoc() {
		return RenderUtil.SCOPEEMPTYPART;
	}

	@Override
	public ResourceLocation getScopeResLoc() {
		return RenderUtil.SCOPE;
	}

	@Override
	public Item getStock() {
		return ItemRegistries.Kar98kStock.get();
	}

	@Override
	public boolean hasScope() {
		return true;
	}

	@Override
	public boolean isBoth() {
		return true;
	}

	@Override
	public void onHit(ItemStack arg0, LivingEntity arg1) {

	}

	@Override
	public boolean shouldRenderCrosshair() {
		return false;
	}	
	
	@Override
	public boolean canChangeParts() {
		return true;
	}

	@Override
	public ShootMode getShootMode(ItemStack stack) {
		return ShootMode.SEMI;
	}

	@Override
	public double getGunDamage(ItemStack stack) {
		return 10;
	}

	@Override
	public double getPower(ItemStack stack) {
		return 16;
	}

	@Override
	public float getInaccuracy(ItemStack stack) {
		return 0.01f;
	}

	@Override
	public float getRHorRecoil(ItemStack stack) {
		return 0.3f;
	}

	@Override
	public float getRVertRecoil(ItemStack stack) {
		return 0;
	}

	@Override
	public float getZRecoil(ItemStack stack) {
		return 0.8f;
	}

	@Override
	public float getBurstTargetTime(ItemStack stack) {
		return 0;
	}

	@Override
	public int getRecoilTime(ItemStack stack) {
		return 10;
	}

	@Override
	public int getShootTime(ItemStack stack) {
		return 20;
	}

	@Override
	public int getRange(ItemStack stack) {
		return 10;
	}

	@Override
	public int getShotgunBullets(ItemStack stack) {
		return 0;
	}

	@Override
	public int getBurstShots(ItemStack stack) {
		return 0;
	}

	@Override
	public float getRangeDamageReduction(ItemStack stack) {
		return 0.9f;
	}

	@Override
	public int getMaxAmmo(ItemStack stack) {
		return 5;
	}
	
	@Override
	public boolean isShotgun(ItemStack stack) {
		return false;
	}

	@Override
	public boolean requiresMag(ItemStack stack) {
		return false;
	}

	@Override
	public String getAcceptedBulletType(ItemStack stack) {
		return ItemBullet.BIG;
	}

	@Override
	public SoundEvent getShootSound(ItemStack stack) {
		return SoundRegistries.KARKSHOOT.get();
	}

	@Override
	public Item getBulletItem(ItemStack stack) {
		return ItemRegistries.BigBullet.get();
	}

	@Override
	public String getProjectile(ItemStack stack) {
		return "oldguns:bullet";
	}
	
}
