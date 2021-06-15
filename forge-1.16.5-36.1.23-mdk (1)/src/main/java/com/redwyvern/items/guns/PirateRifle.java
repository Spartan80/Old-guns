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

public class PirateRifle extends ItemGun {

	public PirateRifle() {
		super(GunTiers.LARGE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getRecoil() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public float getDamage() {
		// TODO Auto-generated method stub
		return 14;
	}

	@Override
	public int getReloadTime() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public float getPower() {
		// TODO Auto-generated method stub
		return 14;
	}

	@Override
	public float getInaccuracy() {
		// TODO Auto-generated method stub
		return 0.1f;
	}

	@Override
	public boolean isBoth() {
		return true;
	}

	@Override
	public Item getBodyWood() {
		// TODO Auto-generated method stub
		return ItemRegistries.prby.get();
	}

	@Override
	public Item getBodyMetal() {
		// TODO Auto-generated method stub
		return ItemRegistries.prbm.get();
	}

	@Override
	public Item getBarrel() {
		// TODO Auto-generated method stub
		return ItemRegistries.prb.get();
	}

	@Override
	public void configMatrixGun(MatrixStack matrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configMatrixLeftHand(MatrixStack matrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configMatrixRightHand(MatrixStack matrix) {
		matrix.translate(0.084f, 0.716f, -0.876f);
		matrix.mulPose(new Quaternion(-28, 0, 0, true));

	}

	@Override
	public void configMatrixHammer(MatrixStack matrix, ItemStack stack) {
		if (getNBTBullets(stack) > 0) {
			matrix.translate(0, 0.83f, -0.51f);
		} else {
			matrix.translate(-0.01f, 1.05f, 1.09f);
			matrix.mulPose(new Quaternion(-90, 0, 0, true));
			matrix.translate(0, 0.83f, -0.51f);
		}
	}

	@Override
	public boolean hasScope() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void configMatrixGunWhileAiming(MatrixStack matrix, float aimticks) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMaxAmmo() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getShootTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void configMatrixScope(MatrixStack matrix) {
		matrix.translate(0.1f, -0.56f, -0.64f);

	}

	@Override
	public SoundEvent getShootSound() {
		// TODO Auto-generated method stub
		return SoundRegistries.PirateRifleShootSound.get();
	}

	@Override
	public float getShootVolume() {
		return 10.0f;
	}

}
