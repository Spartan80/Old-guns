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

public class Revolver extends ItemGun{

	public Revolver(Properties prop, RegistryObject<Item> mag) {
		super(prop, mag);
	}

	@Override
	public void onHit(ItemStack stack, LivingEntity target) {
		
	}

	@Override
	public float getBaseSpeedModifier() {
		return 0;
	}

	@Override
	public boolean isBoth() {
		return true;
	}

	@Override
	public boolean hasScope() {
		return true;
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
	public String getGunId() {
		return "revolver";
	}

	@Override
	public Item getBarrel() {
		return ItemRegistries.RevolverBarrel.get();
	}

	@Override
	public Item getBody() {
		return ItemRegistries.RevolverBody.get();
	}

	@Override
	public Item getStock() {
		return ItemRegistries.RevolverStock.get();
	}

	@Override
	public Item getBarrelMouth() {
		return null;
	}

	@Override
	public ResourceLocation getScopeResLoc() {
		return RenderUtil.SCOPE;
	}

	@Override
	public ResourceLocation getNullPartsResLoc() {
		return RenderUtil.SCOPEEMPTYPART;
	}

	@Override
	public BulletProvider<? extends Bullet> getProjectile(ItemStack stack) {
		return Bullet::new;
	}

	@Override
	public ShootMode getShootMode(ItemStack stack) {
		return ShootMode.SEMI;
	}

	@Override
	public double getGunDamage(ItemStack stack) {
		return 7;
	}

	@Override
	public double getPower(ItemStack stack) {
		return 15;
	}

	@Override
	public float getInaccuracy(ItemStack stack) {
		return 0.08f;
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
		return 0.4f;
	}

	@Override
	public float getBurstTargetTime(ItemStack stack) {
		return 0;
	}

	@Override
	public int getRecoilTime(ItemStack stack) {
		return 6;
	}

	@Override
	public int getShootTime(ItemStack stack) {
		return 6;
	}

	@Override
	public int getRange(ItemStack stack) {
		return 9;
	}

	@Override
	public int getMaxAmmo(ItemStack stack) {
		return 6;
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
		return false;
	}

	@Override
	public String getAcceptedBulletType(ItemStack stack) {
		return ItemBullet.MEDIUM;
	}
	
	@Override
	public SoundEvent getShootSound(ItemStack stack) {
		return SoundRegistries.REVOLVERSHOT.get();
	}

	@Override
	public Item getBulletItem(ItemStack stack) {
		return ItemRegistries.MediumBullet.get();
	}

}
