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

public class PiratePistol extends ItemGun {

	public PiratePistol() {
		super(GunTiers.SMALL);

	}

	@Override
	public float getRecoil() {
		return 3;
	}

	@Override
	public float getDamage() {
		return 10;
	}

	@Override
	public float getPower() {
		return 10;
	}

	@Override
	public float getInaccuracy() {
		return 0.3f;
	}

	@Override
	public int getReloadTime() {

		return 10;
	}

	@Override
	public boolean isBoth() {
		return false;
	}

	@Override
	public Item getBodyWood() {

		return ItemRegistries.ppby.get();
	}

	@Override
	public Item getBodyMetal() {

		return ItemRegistries.ppbm.get();
	}

	@Override
	public Item getBarrel() {

		return ItemRegistries.ppb.get();
	}

	@Override
	public void configMatrixGun(MatrixStack matrix) {

	}

	@Override
	public void configMatrixLeftHand(MatrixStack matrix) {

	}

	@Override
	public void configMatrixRightHand(MatrixStack matrix) {
		matrix.translate(0.084f, 0.536f, -0.744f);
		matrix.mulPose(new Quaternion(-24, 0, 0, true));
	}

	@Override
	public void configMatrixHammer(MatrixStack matrix, ItemStack stack) {
		if (getNBTBullets(stack) > 0) {
			matrix.translate(-0.01f, 0.73f, -0.81f);
		} else {
			matrix.translate(0, 1.23f, 0.76f);
			matrix.mulPose(new Quaternion(-90, 0, 0, true));
			matrix.translate(-0.01f, 0.73f, -0.81f);
		}

	}

	@Override
	public boolean hasScope() {
		return true;
	}

	@Override
	public void configMatrixGunWhileAiming(MatrixStack matrix, float aimticks) {

	}

	@Override
	public int getMaxAmmo() {
		return 1;
	}

	@Override
	public int getShootTime() {
		return 0;
	}

	@Override
	public void configMatrixScope(MatrixStack matrix) {
		matrix.translate(-0.57f, -0.16f, -0.94f);
	}

	@Override
	public SoundEvent getShootSound() {
		// TODO Auto-generated method stub
		return SoundRegistries.PiratePistolShootSound.get();
	}

	@Override
	public float getShootVolume() {
		return 10.0f;
	}
}
