package com.redwyvern.items.guns;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.redwyvern.gun.GunTiers;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.registries.SoundRegistries;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Quaternion;

public class Kar98K extends ItemGun {

	IBakedModel bullet;

	public Kar98K() {
		super(GunTiers.LARGE);

	}

	@Override
	public float getRecoil() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public float getDamage() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public int getReloadTime() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getShootTime() {
		// TODO Auto-generated method stub
		return 30;
	}

	@Override
	public float getPower() {
		// TODO Auto-generated method stub
		return 18;
	}

	@Override
	public float getInaccuracy() {
		// TODO Auto-generated method stub
		return 0.1f;
	}

	@Override
	public boolean isBoth() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Item getBodyWood() {
		// TODO Auto-generated method stub
		return ItemRegistries.kbw.get();
	}

	@Override
	public Item getBodyMetal() {
		// TODO Auto-generated method stub
		return ItemRegistries.kbm.get();
	}

	@Override
	public Item getBarrel() {
		// TODO Auto-generated method stub
		return ItemRegistries.kb.get();
	}

	@Override
	public void configMatrixGun(MatrixStack matrix) {
		matrix.translate(-0.236f, 0.54f, 0);
		matrix.mulPose(new Quaternion(-4, 0, 0, true));

	}

	@Override
	public void configMatrixHammer(MatrixStack matrix, ItemStack stack) {
		matrix.translate(-0.872f, 0.44f, 0.716f);
		matrix.mulPose(new Quaternion(-90, 0, 0, true));
		matrix.scale(2.112f, 1f, 1.588f);
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
		matrix.translate(0.044f, 0.17f, -0.476f);
		matrix.mulPose(new Quaternion(-16, 0, 0, true));
	}

	@Override
	public void configMatrixScope(MatrixStack matrix) {
		matrix.translate(0.672f, 0.128f, 0);

	}

	@Override
	public boolean hasScope() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getMaxAmmo() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void setupModel(IBakedModel model) {
		this.model = model;
		this.hammer = Minecraft.getInstance().getModelManager()
				.getModel(new ModelResourceLocation("oldguns:guns/kark/kark_hammer", "inventory"));
	}

	@Override
	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SoundEvent getShootSound() {
		return SoundRegistries.Kar98kShootSound.get();
	}

	@Override
	public SoundEvent getShootReloadSound() {
		return SoundRegistries.Kar98kHammerShootSound.get();
	}

	@Override
	public float getShootVolume() {
		// TODO Auto-generated method stub
		return 1.0f;
	}

}
