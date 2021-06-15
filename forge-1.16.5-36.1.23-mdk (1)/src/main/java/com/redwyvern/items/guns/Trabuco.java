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

public class Trabuco extends ItemGun {

	public Trabuco() {
		super(GunTiers.MEDIUM);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getRecoil() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public float getDamage() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public int getReloadTime() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getShootTime() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public float getPower() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public float getInaccuracy() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public boolean isBoth() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Item getBodyWood() {
		// TODO Auto-generated method stub
		return ItemRegistries.tbw.get();
	}

	@Override
	public Item getBodyMetal() {
		// TODO Auto-generated method stub
		return ItemRegistries.tbm.get();
	}

	@Override
	public Item getBarrel() {
		// TODO Auto-generated method stub
		return ItemRegistries.tb.get();
	}

	@Override
	public void configMatrixGun(MatrixStack matrix) {
		matrix.translate(-0.01f, 0.27f, -0.53f);
		matrix.mulPose(new Quaternion(-2, 0, 0, true));

	}

	@Override
	public void configMatrixGunWhileAiming(MatrixStack matrix, float aimticks) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configMatrixLeftHand(MatrixStack matrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configMatrixRightHand(MatrixStack matrix) {
		matrix.translate(0.04f, 0.176f, -0.476f);
		matrix.mulPose(new Quaternion(-14, 0, 0, true));
	}

	@Override
	public void configMatrixScope(MatrixStack matrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configMatrixHammer(MatrixStack matrix, ItemStack stack) {
		if (getNBTBullets(stack) > 0) {
			matrix.translate(-0.11f, 1.12f, -0.48f);
		} else {
			matrix.translate(-0.01f, 1.29f, 1.43f);
			matrix.mulPose(new Quaternion(-90, 0, 0, true));
			matrix.translate(-0.11f, 1.12f, -0.48f);
		}

	}

	@Override
	public boolean hasScope() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMaxAmmo() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isShotgun() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public SoundEvent getShootSound() {
		// TODO Auto-generated method stub
		return SoundRegistries.TrabucoShootSound.get();
	}

	@Override
	public float getShootVolume() {
		// TODO Auto-generated method stub
		return 1.0f;
	}

}
