package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.modmodels.Mp40ModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class Mp40GunModel extends GunModel {

	public static final Animation SA = Animation.create(2);
	public static final Animation R1 = Animation.create(36);
	public static final Animation R3 = Animation.create(32);
	public static final Animation R4 = Animation.create(32);

	public Mp40GunModel() {
		super(ItemRegistries.Mp40.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		ClientHandler.debug = false;
		setAnimation(R4);

	}

	@Override
	public void handleAim(MatrixStack matrix, float aimProgress) {
		matrix.translate(MathHelper.lerp(aimProgress, 0, -0.267f), MathHelper.lerp(aimProgress, 0, 0.035f), 0);
		matrix.mulPose(new Quaternion(MathHelper.lerp(aimProgress, 0, -0.0349f),
				MathHelper.lerp(aimProgress, 0, -0.1047f), 0, false));

		// debugAim(matrix, aimProgress);
	}

	@Override
	public void handleSprint(MatrixStack matrix, float aimProgress) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(MathHelper.rotLerp(aimProgress, 0, -24))));

	}

	@Override
	public GunModelPart[] getParts() {
		// TODO Auto-generated method stub
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, scope, aim };
	}

	@Override
	public boolean canRenderMag(ItemStack stack) {
		// TODO Auto-generated method stub
		return ServerUtils.hasMag(stack);
	}

	@Override
	public boolean canShoot(ItemStack stack) {
		// TODO Auto-generated method stub
		return getAnimation() == EMPTY;
	}

	@Override
	public void setupParts() {

		setPartDisplayTransform(rightarm, 0.03f, 0.441f, 0.8598f, -0.5409f, -0.541f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.3397f, 0.8698f, 0.7889f, 0.3666f, 0.8202f, 0.2619f);
		setPartDisplayTransform(gun, -0.28f, -0.68f, -0.14f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(hammer, -0.28f, -0.68f, -0.14f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(mag, -0.28f, -0.68f, -0.14f, -1.5358f, 0, 0.1047f);

	}

	@Override
	public void setupAnimations() {

		animator.setAnimation(SA);
		animator.startKeyframe(1);
		animator.move(hammer, 0.03f, -0.01f, 0.21f);
		animator.endKeyframe();
		animator.resetKeyframe(1);
		animator.endKeyframe();

		animator.setAnimation(R1);
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.13f, -0.01f, 0.19f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.21f, -0.16f, 0);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, -0.01f, 0.33f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.23f, -0.16f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.01f, 0.33f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.23f, -0.15f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, -0.02f, 0);
		animator.rotate(rightarm, 0, 0.2443f, 0);
		animator.move(leftarm, 0.0f, -0.09f, 0.04f);
		animator.rotate(leftarm, 0.1919f, 0, 0.2792f);
		animator.move(gun, -0.04f, 0.18f, 0);
		animator.rotate(gun, 0, 0.2443f, 0);
		animator.move(mag, -0.04f, 0.18f, 0);
		animator.rotate(mag, 0, 0.2443f, 0);
		animator.move(hammer, -0.02f, 0.18f, 0.2f);
		animator.rotate(hammer, 0, 0.2443f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.5999997f, -0.55999976f, 0.04f);
		animator.move(hammer, -0.02f, 0.18f, 0.20000002f);
		animator.move(mag, -0.41999987f, -0.43999985f, -0.09999999f);
		animator.move(gun, -0.04f, 0.18f, 0.0f);
		animator.move(rightarm, 0.01f, -0.02f, 0.0f);
		animator.rotate(leftarm, 0.52359873f, 0.0f, 0.2792527f);
		animator.rotate(hammer, 0.0f, 0.24434613f, 0.0f);
		animator.rotate(mag, 0.0f, 0.24434613f, 0.0f);
		animator.rotate(gun, 0.0f, 0.24434613f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.24434613f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, -0.02f, 0);
		animator.rotate(rightarm, 0, 0.2443f, 0);
		animator.move(leftarm, 0.0f, -0.09f, 0.04f);
		animator.rotate(leftarm, 0.1919f, 0, 0.2792f);
		animator.move(gun, -0.04f, 0.18f, 0);
		animator.rotate(gun, 0, 0.2443f, 0);
		animator.move(mag, -0.04f, 0.18f, 0);
		animator.rotate(mag, 0, 0.2443f, 0);
		animator.move(hammer, -0.02f, 0.18f, 0.2f);
		animator.rotate(hammer, 0, 0.2443f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.08f, 0.33f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.23f, -0.15f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.08f, 0.33f);
		animator.rotate(leftarm, 0.1919f, 0, 0);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.24f, -0.16f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.08f, 0.33f);
		animator.rotate(leftarm, 0.1919f, 0, 0);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.21f, -0.16f, 0);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(R3);// 28
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.13f, -0.01f, 0.19f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.21f, -0.16f, 0);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, -0.01f, 0.33f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.23f, -0.16f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.01f, 0.33f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.23f, -0.15f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.5999997f, -0.55999976f, 0.04f);
		animator.move(hammer, -0.02f, 0.18f, 0.20000002f);
		animator.move(mag, -0.41999987f, -0.43999985f, -0.09999999f);
		animator.move(gun, -0.04f, 0.18f, 0.0f);
		animator.move(rightarm, 0.01f, -0.02f, 0.0f);
		animator.rotate(leftarm, 0.52359873f, 0.0f, 0.2792527f);
		animator.rotate(hammer, 0.0f, 0.24434613f, 0.0f);
		animator.rotate(mag, 0.0f, 0.24434613f, 0.0f);
		animator.rotate(gun, 0.0f, 0.24434613f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.24434613f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, -0.02f, 0);
		animator.rotate(rightarm, 0, 0.2443f, 0);
		animator.move(leftarm, 0.0f, -0.09f, 0.04f);
		animator.rotate(leftarm, 0.1919f, 0, 0.2792f);
		animator.move(gun, -0.04f, 0.18f, 0);
		animator.rotate(gun, 0, 0.2443f, 0);
		animator.move(mag, -0.04f, 0.18f, 0);
		animator.rotate(mag, 0, 0.2443f, 0);
		animator.move(hammer, -0.02f, 0.18f, 0.2f);
		animator.rotate(hammer, 0, 0.2443f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.08f, 0.33f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.23f, -0.15f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.08f, 0.33f);
		animator.rotate(leftarm, 0.1919f, 0, 0);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.24f, -0.16f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.08f, 0.33f);
		animator.rotate(leftarm, 0.1919f, 0, 0);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.21f, -0.16f, 0);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(R4);// 48
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.13f, -0.01f, 0.19f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.21f, -0.16f, 0);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, -0.01f, 0.33f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.23f, -0.16f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.01f, 0.33f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.23f, -0.15f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, -0.02f, 0);
		animator.rotate(rightarm, 0, 0.2443f, 0);
		animator.move(leftarm, 0.0f, -0.09f, 0.04f);
		animator.rotate(leftarm, 0.1919f, 0, 0.2792f);
		animator.move(gun, -0.04f, 0.18f, 0);
		animator.rotate(gun, 0, 0.2443f, 0);
		animator.move(mag, -0.04f, 0.18f, 0);
		animator.rotate(mag, 0, 0.2443f, 0);
		animator.move(hammer, -0.02f, 0.18f, 0.2f);
		animator.rotate(hammer, 0, 0.2443f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.5999997f, -0.55999976f, 0.04f);
		animator.move(hammer, -0.02f, 0.18f, 0.20000002f);
		animator.move(mag, -0.41999987f, -0.43999985f, -0.09999999f);
		animator.move(gun, -0.04f, 0.18f, 0.0f);
		animator.move(rightarm, 0.01f, -0.02f, 0.0f);
		animator.rotate(leftarm, 0.52359873f, 0.0f, 0.2792527f);
		animator.rotate(hammer, 0.0f, 0.24434613f, 0.0f);
		animator.rotate(mag, 0.0f, 0.24434613f, 0.0f);
		animator.rotate(gun, 0.0f, 0.24434613f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.24434613f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.08f, 0.33f);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.23f, -0.15f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.08f, 0.33f);
		animator.rotate(leftarm, 0.1919f, 0, 0);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.24f, -0.16f, 0.2f);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, 0.01f, 0.11f, 0);
		animator.rotate(rightarm, 0, -0.3839f, 0);
		animator.move(leftarm, -0.12f, 0.08f, 0.33f);
		animator.rotate(leftarm, 0.1919f, 0, 0);
		animator.move(gun, 0.21f, -0.16f, 0);
		animator.rotate(gun, 0, -0.3839f, 0);
		animator.move(mag, 0.21f, -0.16f, 0);
		animator.rotate(mag, 0, -0.3839f, 0);
		animator.move(hammer, 0.21f, -0.16f, 0);
		animator.rotate(hammer, 0, -0.3839f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
	}

	@Override
	public void initReloadAnimation(int currentgunbullets, int magbullets) {
		ClientHandler.debug = false;
		if (magbullets >= 0 && ServerUtils.hasMag(Util.getStack())) {
			setAnimation(R1);
			System.out.println("R1");
		} else {
			setAnimation(R3);
			System.out.println("R3");
		}

	}

	@Override
	public void onAnimTick(float animTick) {
		if (getAnimation() == R1) {
			if (animTick == 10) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40COKING_FIRST.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 16) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40_CLIPOUT.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 24) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40_CLIPIN.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 28) {
				ReloadHandler.restoreMag(getGunItem().getMagPath(Util.getStack()).getRegistryName().toString(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.removeItem(ammoindex, 1);
			} else if (animTick == 29) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40COKING_LAST.get(), SoundCategory.NEUTRAL);
			}
		} else if (getAnimation() == R3) {
			if (animTick == 10) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40COKING_FIRST.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 16) {
				ReloadHandler.setMag(true, getMagItem().getMagType(), getMBPath());
				ReloadHandler.removeItem(ammoindex, 1);
			} else if (animTick == 20) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40_CLIPIN.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 26) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40COKING_LAST.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 28) {
				ReloadHandler.setBullets(magbullets);
			}
		} else if (getAnimation() == R4) {
			if (animTick == 10) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40COKING_FIRST.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 16) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40_CLIPOUT.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 19) {
				ReloadHandler.restoreMag(getGunItem().getMagPath(Util.getStack()).getRegistryName().toString(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setMag(false, 0, "");
				ReloadHandler.setBullets(0);
			} else if (animTick == 25) {
				SoundHandler.playSoundOnServer(SoundRegistries.MP40COKING_LAST.get(), SoundCategory.NEUTRAL);
			}
		}

	}

	@Override
	public void onShoot(ItemStack stack) {
		setAnimation(SA);

	}

	@Override
	public void onAnimationEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initExtraParts() {
		// TODO Auto-generated method stub

	}

	@Override
	public IBakedModel getModifiedModel(IBakedModel origin) {
		// TODO Auto-generated method stub
		return new Mp40ModModel(origin);
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.MP40H;
	}

	@Override
	public boolean hasMultipleParts() {
		// TODO Auto-generated method stub
		return true;
	}

}
