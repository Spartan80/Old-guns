package com.jg.oldguns.guns.items;

import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.ItemBullet;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.properties.BodyProperties.BulletProvider;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.RenderUtil;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class Winchester extends ItemGun {

	public Winchester(Properties prop, RegistryObject<Item> mag) {
		super(prop, mag);
	}

	@Override
	public void tickServer(ItemStack stack, World world, PlayerEntity player) {
		// TODO Auto-generated method stub

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
		return true;
	}

	@Override
	public boolean shouldRenderCrosshair() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getGunId() {
		// TODO Auto-generated method stub
		return "winchester";
	}

	@Override
	public Item getBarrel() {
		// TODO Auto-generated method stub
		return ItemRegistries.WinchesterBarrel.get();
	}

	@Override
	public Item getBody() {
		// TODO Auto-generated method stub
		return ItemRegistries.WinchesterBody.get();
	}

	@Override
	public Item getStock() {
		// TODO Auto-generated method stub
		return ItemRegistries.WinchesterStock.get();
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ShootMode getShootMode(ItemStack stack) {
		return ShootMode.SEMI;
	}

	@Override
	public double getGunDamage(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return 3;
		}
		return 8;
	}

	@Override
	public double getPower(ItemStack stack) {
		return 14;
	}

	@Override
	public float getInaccuracy(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return 1f;
		}
		return 0.07f;
	}

	@Override
	public float getRHorRecoil(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return 0.18f;
		}
		return 0.1f;
	}

	@Override
	public float getRVertRecoil(ItemStack stack) {
		return 0;
	}

	@Override
	public float getZRecoil(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return 0.38f;
		}
		return 0.22f;
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
		return 10;
	}

	@Override
	public int getRange(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return 5;
		}
		return 9;
	}

	@Override
	public int getMaxAmmo(ItemStack stack) {
		return 6;
	}

	@Override
	public int getShotgunBullets(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return 5;
		}
		return 0;
	}

	@Override
	public int getBurstShots(ItemStack stack) {
		return 0;
	}

	@Override
	public float getSpeedModifier(ItemStack stack) {
		return 0;
	}

	@Override
	public float getRangeDamageReduction(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return 0.55f;
		}
		return 0.85f;
	}

	@Override
	public boolean isShotgun(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean requiresMag(ItemStack stack) {
		return false;
	}

	@Override
	public String getAcceptedBulletType(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return ItemBullet.SHOTGUN;
		}
		return ItemBullet.MEDIUM;
	}

	@Override
	public SoundEvent getShootSound(ItemStack stack) {
		return SoundRegistries.WINCHESTERSHOOT.get();
	}

	@Override
	public Item getMagPath(ItemStack stack) {
		return null;
	}

	@Override
	public Item getBulletItem(ItemStack stack) {
		if(barrelEqTo(stack, ItemRegistries.WinchesterShotgunBarrel)) {
			return ItemRegistries.ShotgunBullet.get();
		}
		return ItemRegistries.MediumBullet.get();
	}

	@Override
	public BulletProvider<? extends Bullet> getProjectile(ItemStack stack) {
		return Bullet::new;
	}

}
