package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.modmodels.RevolverModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class RevolverGunModel extends GunModel{

	public static final Animation SA = Animation.create(2);
	public static final Animation R1 = Animation.create(26);
	public static final Animation R2 = Animation.create(36);
	public static final Animation R3 = Animation.create(46);
	public static final Animation R4 = Animation.create(56);
	public static final Animation R5 = Animation.create(66);
	public static final Animation R6 = Animation.create(76);
	
	public RevolverGunModel() {
		super(ItemRegistries.Revolver.get());
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		
	}

	@Override
	public void handleAim(MatrixStack matrix, float p) {
		matrix.translate(MathHelper.lerp(p, 0, -0.245f), MathHelper.lerp(p, 0, 0.145f), MathHelper.lerp(p, 0, 0.25f));
		matrix.mulPose(new Quaternion(MathHelper.lerp(p, 0, -0.0174f), MathHelper.lerp(p, 0, -0.0523f), 0, false));
	}

	@Override
	public void handleSprint(MatrixStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(MathHelper.rotLerp(p, 0, -18))));
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, scope, aim };
	}

	@Override
	public boolean canRenderMag(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canShoot(ItemStack stack) {
		return true;
	}

	@Override
	public void setupParts() {
		
		setPartDisplayTransform(rightarm, -0.13f, 0.37f, 0.64f, -0.2967f, -0.5410f, -0.4014f);
		setPartDisplayTransform(leftarm, -0.31f, 0.86f, 0.8789f, 0.2967f, -0.5410f, -0.4014f);
		setPartDisplayTransform(gun, -0.26f, -0.86f, -0.71f, -1.5533f, 0, 0.0524f);
		if (ClientEventHandler.getPlayer() != null) {
			if (ServerUtils.getBullets(Util.getStack()) > 0) {
				setPartDisplayTransform(hammer, -0.26f, -0.86f, -0.69f, -1.5533f, 0, 0.0524f);
			} else {
				setPartDisplayTransform(hammer, -0.26f, -0.86f, -0.71f, -1.5533f, 0, 0.0524f);
			}
		}
		
	}

	@Override
	public void setupAnimations() {
		
		animator.setAnimation(SA);
		animator.startKeyframe(1);
		animator.rotate(hammer, -0.0523f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(1);
		animator.endKeyframe();
		
		animator.setAnimation(R1);
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0.0f, -4.0978193E-8f, -0.6108653f);
		animator.move(leftarm, 0.0f, 0.01f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.move(gun, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(gun, -0.4363323f, 0.0f, 0.0f);
		animator.move(hammer, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(hammer, -0.4363323f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(6);
		animator.move(gun, 0.02f, -0.19000001f, 0.18f);
		animator.move(hammer, 0.02f, -0.19000001f, 0.18f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(aim, -0.19200021f, 0.1380001f, 0.25000033f);
		animator.move(leftarm, -0.09999999f, 0.109999985f, 0.03f);
		animator.rotate(gun, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(hammer, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -4.0978193E-8f, -0.6108653f);
		animator.rotate(aim, -0.013962633f, -0.006981316f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0.07999999f, -0.29f, 0.18f);
		animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
		animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
		animator.move(leftarm, -0.04f, 0.16f, 0.049999997f);
		animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		
		for(int i = 0; i < 1; i++) {
			animator.startKeyframe(6);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.15f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.17f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
		}
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R2);
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0.0f, -4.0978193E-8f, -0.6108653f);
		animator.move(leftarm, 0.0f, 0.01f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.move(gun, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(gun, -0.4363323f, 0.0f, 0.0f);
		animator.move(hammer, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(hammer, -0.4363323f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(6);
		animator.move(gun, 0.02f, -0.19000001f, 0.18f);
		animator.move(hammer, 0.02f, -0.19000001f, 0.18f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(aim, -0.19200021f, 0.1380001f, 0.25000033f);
		animator.move(leftarm, -0.09999999f, 0.109999985f, 0.03f);
		animator.rotate(gun, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(hammer, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -4.0978193E-8f, -0.6108653f);
		animator.rotate(aim, -0.013962633f, -0.006981316f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0.07999999f, -0.29f, 0.18f);
		animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
		animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
		animator.move(leftarm, -0.04f, 0.16f, 0.049999997f);
		animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		
		for(int i = 0; i < 2; i++) {
			animator.startKeyframe(6);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.15f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.17f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
		}
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R3);
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0.0f, -4.0978193E-8f, -0.6108653f);
		animator.move(leftarm, 0.0f, 0.01f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.move(gun, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(gun, -0.4363323f, 0.0f, 0.0f);
		animator.move(hammer, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(hammer, -0.4363323f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(6);
		animator.move(gun, 0.02f, -0.19000001f, 0.18f);
		animator.move(hammer, 0.02f, -0.19000001f, 0.18f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(aim, -0.19200021f, 0.1380001f, 0.25000033f);
		animator.move(leftarm, -0.09999999f, 0.109999985f, 0.03f);
		animator.rotate(gun, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(hammer, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -4.0978193E-8f, -0.6108653f);
		animator.rotate(aim, -0.013962633f, -0.006981316f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0.07999999f, -0.29f, 0.18f);
		animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
		animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
		animator.move(leftarm, -0.04f, 0.16f, 0.049999997f);
		animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		
		for(int i = 0; i < 3; i++) {
			animator.startKeyframe(6);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.15f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.17f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
		}
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R4);
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0.0f, -4.0978193E-8f, -0.6108653f);
		animator.move(leftarm, 0.0f, 0.01f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.move(gun, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(gun, -0.4363323f, 0.0f, 0.0f);
		animator.move(hammer, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(hammer, -0.4363323f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(6);
		animator.move(gun, 0.02f, -0.19000001f, 0.18f);
		animator.move(hammer, 0.02f, -0.19000001f, 0.18f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(aim, -0.19200021f, 0.1380001f, 0.25000033f);
		animator.move(leftarm, -0.09999999f, 0.109999985f, 0.03f);
		animator.rotate(gun, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(hammer, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -4.0978193E-8f, -0.6108653f);
		animator.rotate(aim, -0.013962633f, -0.006981316f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0.07999999f, -0.29f, 0.18f);
		animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
		animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
		animator.move(leftarm, -0.04f, 0.16f, 0.049999997f);
		animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		
		for(int i = 0; i < 4; i++) {
			animator.startKeyframe(6);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.15f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.17f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
		}
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R5);
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0.0f, -4.0978193E-8f, -0.6108653f);
		animator.move(leftarm, 0.0f, 0.01f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.move(gun, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(gun, -0.4363323f, 0.0f, 0.0f);
		animator.move(hammer, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(hammer, -0.4363323f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(6);
		animator.move(gun, 0.02f, -0.19000001f, 0.18f);
		animator.move(hammer, 0.02f, -0.19000001f, 0.18f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(aim, -0.19200021f, 0.1380001f, 0.25000033f);
		animator.move(leftarm, -0.09999999f, 0.109999985f, 0.03f);
		animator.rotate(gun, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(hammer, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -4.0978193E-8f, -0.6108653f);
		animator.rotate(aim, -0.013962633f, -0.006981316f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0.07999999f, -0.29f, 0.18f);
		animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
		animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
		animator.move(leftarm, -0.04f, 0.16f, 0.049999997f);
		animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		
		for(int i = 0; i < 5; i++) {
			animator.startKeyframe(6);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.15f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.17f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
		}
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R6);
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0.0f, -4.0978193E-8f, -0.6108653f);
		animator.move(leftarm, 0.0f, 0.01f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.move(gun, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(gun, -0.4363323f, 0.0f, 0.0f);
		animator.move(hammer, -0.08999999f, -0.23000003f, 0.08999999f);
		animator.rotate(hammer, -0.4363323f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(6);
		animator.move(gun, 0.02f, -0.19000001f, 0.18f);
		animator.move(hammer, 0.02f, -0.19000001f, 0.18f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(aim, -0.19200021f, 0.1380001f, 0.25000033f);
		animator.move(leftarm, -0.09999999f, 0.109999985f, 0.03f);
		animator.rotate(gun, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(hammer, -0.3316126f, 0.017453292f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -4.0978193E-8f, -0.6108653f);
		animator.rotate(aim, -0.013962633f, -0.006981316f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0.07999999f, -0.29f, 0.18f);
		animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
		animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
		animator.move(leftarm, -0.04f, 0.16f, 0.049999997f);
		animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
		animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
		animator.endKeyframe();
		
		for(int i = 0; i < 6; i++) {
			animator.startKeyframe(6);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.15f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(gun, 0.07999999f, -0.29f, 0.18f);
			animator.move(hammer, 0.07999999f, -0.29f, 0.18f);
			animator.move(rightarm, -0.09999999f, 0.0f, 0.0f);
			animator.move(leftarm, -0.04f, 0.17f, 0.049999997f);
			animator.rotate(gun, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(hammer, -0.3316126f, -0.22689283f, 0.0f);
			animator.rotate(rightarm, 0.36651912f, -0.22689286f, -0.6108653f);
			animator.rotate(leftarm, 0.0f, 0.0f, -0.6981318f);
			animator.endKeyframe();
		}
		animator.resetKeyframe(4);
		animator.endKeyframe();
	}

	@Override
	public void initReloadAnimation(int gun, int mag) {
		if(getAnimation() == EMPTY) {
			int bf = 6 - gun;
			if (mag > bf && bf != 0) {
				mag = bf;
	
				if (mag == 1) {
					setAnimation(R1);
				} else if (mag == 2) {
					setAnimation(R2);
				} else if (mag == 3) {
					setAnimation(R3);
				} else if (mag == 4) {
					setAnimation(R4);
				} else if (mag == 5) {
					setAnimation(R5);
				} else if (mag == 6) {
					setAnimation(R6);
				}
			} else {
				if (mag == 1) {
					setAnimation(R1);
				} else if (mag == 2) {
					setAnimation(R2);
				} else if (mag == 3) {
					setAnimation(R3);
				} else if (mag == 4) {
					setAnimation(R4);
				} else if (mag == 5) {
					setAnimation(R5);
				} else if (mag == 6) {
					setAnimation(R6);
				}
			}
		}
	}

	@Override
	public void onAnimTick(float animTick) {
		if(animTick == 12) {
			SoundHandler.playSoundOnServer(SoundRegistries.REVOLVERSPIN.get());
		}else if(animTick == 14){
			SoundHandler.playSoundOnServer(SoundRegistries.REVOLVER_BULLETS_OUT.get());
		}
		if(animTick > 10 && animTick%10 == 0) {
			ReloadHandler.removeItem(ammoindex, 1);
			ReloadHandler.growOneBullet(Util.getStack());
			SoundHandler.playSoundOnServer(SoundRegistries.REVOLVER_BULLETS_INSIDE.get());
		}
	}

	@Override
	public void onShoot(ItemStack stack) {
		if(getAnimation() == EMPTY) {
			setAnimation(SA);
		}
	}

	@Override
	public void onAnimationEnd() {
		
	}

	@Override
	public void initExtraParts() {
		
	}

	@Override
	public IBakedModel getModifiedModel(IBakedModel origin) {
		return new RevolverModModel(origin);
	}

	@Override
	public String getHammerPath() {
		return Paths.REVOLVERHAMMER;
	}

	@Override
	public boolean hasMultipleParts() {
		return true;
	}

}
