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

public class Revolver extends ItemGun {

	public Revolver() {
		super(GunTiers.MEDIUM);
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
		return 10;
	}

	@Override
	public int getReloadTime() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getShootTime() {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public float getPower() {
		// TODO Auto-generated method stub
		return 14;
	}

	@Override
	public float getInaccuracy() {
		// TODO Auto-generated method stub
		return 0.2f;
	}

	@Override
	public boolean isBoth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Item getBodyWood() {

		return ItemRegistries.rbw.get();
	}

	@Override
	public Item getBodyMetal() {

		return ItemRegistries.rbm.get();
	}

	@Override
	public Item getBarrel() {

		return ItemRegistries.rb.get();
	}

	@Override
	public void configMatrixGun(MatrixStack matrix) {
		matrix.translate(-0.06f, -0.02f, -0.14f);

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
		matrix.translate(0.084f, 0.38f, -0.584f);
		matrix.mulPose(new Quaternion(-20, 0, 0, true));

	}

	@Override
	public void configMatrixHammer(MatrixStack matrix, ItemStack stack) {
		if (getNBTBullets(stack) > 0) {
			matrix.translate(-0.098f, 0.71f, -0.7f);
		} else {
			matrix.translate(0.01f, 0.14f, 0.41f);
			matrix.mulPose(new Quaternion(-26, 0, 0, true));
			matrix.translate(-0.109f, 0.71f, -0.7f);
		}

	}

	@Override
	public void configMatrixScope(MatrixStack matrix) {
		matrix.translate(-0.42f, 0.26f, -0.95f);

	}

	@Override
	public boolean hasScope() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getMaxAmmo() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SoundEvent getShootSound() {
		// TODO Auto-generated method stub
		return SoundRegistries.RevolverShootSound.get();
	}

}
