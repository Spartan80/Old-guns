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

public class Thompson extends ItemGun {

	public Thompson(Properties prop, RegistryObject<Item> mag) {
		super(prop, mag);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Item getBarrel() {
		// TODO Auto-generated method stub
		return ItemRegistries.ThompsonBarrel.get();
	}

	@Override
	public Item getBarrelMouth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getBaseSpeedModifier() {
		return 0;
	}

	@Override
	public Item getBody() {
		// TODO Auto-generated method stub
		return ItemRegistries.ThompsonBody.get();
	}

	@Override
	public String getGunId() {
		// TODO Auto-generated method stub
		return "thompson";
	}

	@Override
	public ResourceLocation getNullPartsResLoc() {
		// TODO Auto-generated method stub
		return RenderUtil.SCOPEEMPTYPART;
	}

	@Override
	public ResourceLocation getScopeResLoc() {
		// TODO Auto-generated method stub
		return RenderUtil.SCOPE;
	}

	@Override
	public Item getStock() {
		// TODO Auto-generated method stub
		return ItemRegistries.ThompsonStock.get();
	}

	@Override
	public boolean hasScope() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isBoth() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onHit(ItemStack arg0, LivingEntity arg1) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean shouldRenderCrosshair() {
		return false;
	}

	@Override
	public boolean canChangeParts() {
		// TODO Auto-generated method stub
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
		return 2;
	}

	@Override
	public double getPower(ItemStack stack) {
		return 14;
	}

	@Override
	public float getInaccuracy(ItemStack stack) {
		return 0.18f;
	}

	@Override
	public float getRHorRecoil(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.ThompsonBarrel)) {
			return 0.02f;
		}else {
			return 0.05f;
		}
	}

	@Override
	public float getRVertRecoil(ItemStack stack) {
		return 0;
	}

	@Override
	public float getZRecoil(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.ThompsonBarrel)) {
			return 0.04f;
		}else {
			return 0.07f;
		}
	}

	@Override
	public float getBurstTargetTime(ItemStack stack) {
		return 0;
	}

	@Override
	public int getRecoilTime(ItemStack stack) {
		return 2;
	}

	@Override
	public int getShootTime(ItemStack stack) {
		return 2;
	}

	@Override
	public int getRange(ItemStack stack) {
		return 7;
	}

	/*@Override
	public int getMaxAmmo(ItemStack stack) {
		switch(getNBTMagType(stack)) {
		case 0:
			return 20;
		case 1:
			return 45;
		default:
			return 20;
		}
	}*/

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
		return 0.55f;
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
		return SoundRegistries.THOMPSONSHOOT.get();
	}

	@Override
	public Item getBulletItem(ItemStack stack) {
		return null;
	}
	
}
