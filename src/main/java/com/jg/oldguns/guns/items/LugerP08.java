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

public class LugerP08 extends ItemGun {
	
	public LugerP08(Properties prop, RegistryObject<Item> mag) {
		super(prop, mag);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldRenderCrosshair() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getGunId() {
		// TODO Auto-generated method stub
		return "lugerp08";
	}

	@Override
	public Item getBarrel() {
		// TODO Auto-generated method stub
		return ItemRegistries.LugerBarrel.get();
	}

	@Override
	public Item getBody() {
		// TODO Auto-generated method stub
		return ItemRegistries.LugerBody.get();
	}

	@Override
	public Item getStock() {
		// TODO Auto-generated method stub
		return ItemRegistries.LugerHandle.get();
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
	public ShootMode getShootMode(ItemStack stack) {
		return ShootMode.SEMI;
	}

	@Override
	public double getGunDamage(ItemStack stack) {
		return 3;
	}

	@Override
	public double getPower(ItemStack stack) {
		return 10;
	}

	@Override
	public float getInaccuracy(ItemStack stack) {
		return 0.1f;
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
		return 4;
	}

	@Override
	public int getShootTime(ItemStack stack) {
		return 4;
	}

	@Override
	public int getRange(ItemStack stack) {
		return 6;
	}

	@Override
	public int getMaxAmmo(ItemStack stack) {
		return 8;
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
		return 0.6f;
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
		return SoundRegistries.LUGERSHOOT.get();
	}

	@Override
	public Item getBulletItem(ItemStack stack) {
		return null;
	}

	@Override
	public String getProjectile(ItemStack stack) {
		return "oldguns:bullet";
	}

}
