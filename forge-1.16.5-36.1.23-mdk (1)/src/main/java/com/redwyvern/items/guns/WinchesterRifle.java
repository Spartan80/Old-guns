package com.redwyvern.items.guns;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.redwyvern.gun.GunTiers;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.registries.SoundRegistries;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Quaternion;

public class WinchesterRifle extends ItemGun {

	public float SCOPETY, SCOPETZ;

	public WinchesterRifle() {
		super(GunTiers.LARGE);

	}

	@Override
	public float getRecoil() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public float getDamage() {
		// TODO Auto-generated method stub
		return 11;
	}

	@Override
	public int getReloadTime() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public float getPower() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public float getInaccuracy() {
		// TODO Auto-generated method stub
		return 0.3f;
	}

	@Override
	public boolean isBoth() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Item getBodyWood() {
		// TODO Auto-generated method stub
		return ItemRegistries.wbw.get();
	}

	@Override
	public Item getBodyMetal() {
		// TODO Auto-generated method stub
		return ItemRegistries.wbm.get();
	}

	@Override
	public Item getBarrel() {
		// TODO Auto-generated method stub
		return ItemRegistries.wb.get();
	}

	@Override
	public void configMatrixGun(MatrixStack matrix) {
		matrix.translate(-0.16f, 0.28f, 0);
		matrix.mulPose(new Quaternion(-4, 2, 0, true));
	}

	@Override
	public void configMatrixLeftHand(MatrixStack matrix) {
		matrix.translate(0, -0.4f, -0.48f);

	}

	@Override
	public void configMatrixRightHand(MatrixStack matrix) {
		matrix.translate(0.084f, -0.388f, -0.308f);
		matrix.mulPose(new Quaternion(-10, 0, 0, true));
	}

	@Override
	public void configMatrixHammer(MatrixStack matrix, ItemStack stack) {

	}

	@Override
	public boolean hasScope() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void configMatrixGunWhileAiming(MatrixStack matrix, float aimticks) {
		matrix.translate(0, SCOPETY * aimticks, SCOPETZ * aimticks);

	}

	@Override
	public int getMaxAmmo() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public int getShootTime() {
		// TODO Auto-generated method stub
		return 16;
	}

	@Override
	public void configMatrixScope(MatrixStack matrix) {
		matrix.translate(0.41f, -0.06f, -0.19f);
		// matrix.translate(0.8f, -0.1f, -0.44f);
	}

	@Override
	public boolean hasHammer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SoundEvent getShootSound() {
		// TODO Auto-generated method stub
		return SoundRegistries.WinchesterShootSound.get();
	}

	@Override
	public SoundEvent getShootReloadSound() {
		return SoundRegistries.WinchesterHammerReloadSound.get();
	}

	@Override
	public float getShootVolume() {
		return 10.0f;
	}

}
