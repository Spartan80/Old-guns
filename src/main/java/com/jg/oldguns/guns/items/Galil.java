package com.jg.oldguns.guns.items;

import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.registries.ItemRegistries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class Galil extends ItemGun {

	public Galil(Properties prop, RegistryObject<Item> mag) {
		super(prop, mag);
	}

	@Override
	public ShootMode getShootMode(ItemStack stack) {
		// TODO Auto-generated method stub
		return ShootMode.AUTO;
	}

	@Override
	public double getGunDamage(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPower(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getInaccuracy(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRHorRecoil(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRVertRecoil(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getZRecoil(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getBurstTargetTime(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRangeDamageReduction(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRecoilTime(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getShootTime(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRange(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getShotgunBullets(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBurstShots(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isShotgun(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requiresMag(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAcceptedBulletType(ItemStack stack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SoundEvent getShootSound(ItemStack stack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getBulletItem(ItemStack stack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProjectile(ItemStack stack) {
		return "oldguns:bullet";
	}

	@Override
	public void onHit(ItemStack stack, LivingEntity target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getBaseSpeedModifier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isBoth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasScope() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldRenderCrosshair() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canChangeParts() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getGunId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item getBarrel() {
		// TODO Auto-generated method stub
		return ItemRegistries.GalilBarrel.get();
	}

	@Override
	public Item getBody() {
		// TODO Auto-generated method stub
		return ItemRegistries.GalilBody.get();
	}

	@Override
	public Item getStock() {
		// TODO Auto-generated method stub
		return ItemRegistries.GalilStock.get();
	}

	@Override
	public Item getBarrelMouth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getScopeResLoc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getNullPartsResLoc() {
		// TODO Auto-generated method stub
		return null;
	}

}
