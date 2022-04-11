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

public class Sten extends ItemGun{

	public Sten(Properties prop, RegistryObject<Item> mag) {
		super(prop, mag);
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
		return true;
	}

	@Override
	public boolean hasScope() {
		return false;
	}

	@Override
	public boolean shouldRenderCrosshair() {
		return false;
	}

	@Override
	public String getGunId() {
		// TODO Auto-generated method stub
		return "sten";
	}

	@Override
	public Item getBarrel() {
		return ItemRegistries.StenBarrel.get();
	}

	@Override
	public Item getBody() {
		return ItemRegistries.StenBody.get();
	}

	@Override
	public Item getStock() {
		// TODO Auto-generated method stub
		return ItemRegistries.StenStock.get();
	}

	@Override
	public Item getBarrelMouth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLocation getScopeResLoc() {
		// TODO Auto-generated method stub
		return RenderUtil.SCOPE;
	}

	@Override
	public ResourceLocation getNullPartsResLoc() {
		// TODO Auto-generated method stub
		return RenderUtil.SCOPEEMPTYPART;
	}

	@Override
	public boolean canChangeParts() {
		return true;
	}

	@Override
	public String getProjectile(ItemStack stack) {
		return "oldguns:bullet";
	}

	@Override
	public ShootMode getShootMode(ItemStack stack) {
		return ShootMode.AUTO;
	}

	@Override
	public double getGunDamage(ItemStack stack) {
		return 4.3d;
	}

	@Override
	public double getPower(ItemStack stack) {
		return 10;
	}

	@Override
	public float getInaccuracy(ItemStack stack) {
		return 0.06f;
	}

	@Override
	public float getRHorRecoil(ItemStack stack) {
		return 0.05f;
	}

	@Override
	public float getRVertRecoil(ItemStack stack) {
		return 0;
	}

	@Override
	public float getZRecoil(ItemStack stack) {
		return 0.2f;
	}

	@Override
	public float getBurstTargetTime(ItemStack stack) {
		return 0;
	}

	@Override
	public int getRecoilTime(ItemStack stack) {
		return 3;
	}

	@Override
	public int getShootTime(ItemStack stack) {
		return 3;
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
		return 0.85f;
	}

	@Override
	public boolean isShotgun(ItemStack stack) {
		return false;
	}

	@Override
	public boolean requiresMag(ItemStack stack) {
		return true;
	}

	@Override
	public String getAcceptedBulletType(ItemStack stack) {
		return ItemBullet.SMALL;
	}
	
	@Override
	public SoundEvent getShootSound(ItemStack stack) {
		return SoundRegistries.STENSHOOT.get();
	}

	@Override
	public Item getBulletItem(ItemStack stack) {
		return null;
	}

}
