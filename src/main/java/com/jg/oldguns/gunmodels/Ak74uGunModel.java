package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.modmodels.Ak74uModModel;
import com.jg.oldguns.debug.AnimWriter;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.MeleeHelper;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.ServerUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class Ak74uGunModel extends GunModel{

	public static final Animation SA = Animation.create(3);
	public static final Animation R1 = Animation.create(24);
	public static final Animation R2 = Animation.create(48);
	public static final Animation R3 = Animation.create(28);
	public static final Animation R4 = Animation.create(52);
	public static final Animation GOM = Animation.create(24);
	public static final Animation KB = Animation.create(16);
	public static final Animation IA = Animation.create(28);
	
	public Ak74uGunModel() {
		super(ItemRegistries.Ak74u.get());
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		setAnimation(GOM);
	}

	@Override
	public void startKickBackAnim() {
		setAnimation(KB);
		
	}

	@Override
	public void startInspectAnim() {
		setAnimation(IA);
		
	}

	@Override
	public Vector3f getMuzzleFlashPos(ItemStack stack) {
		return new Vector3f(0.159f, -0.16f, -0.992f);
	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.272f), Mth.lerp(p, 0, 0.099f), Mth.lerp(p, 0, 0.306f));
		matrix.mulPose(new Quaternion(Mth.lerp(p, 0, -0.0139f), Mth.lerp(p, 0, -0.1082f), 0, false));
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -18))));
		
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] {rightarm, leftarm, gun, mag, hammer, aim, scope};
	}

	@Override
	public boolean canRenderMag(ItemStack stack) {
		return ServerUtils.hasMag(stack);
	}

	@Override
	public boolean canShoot(ItemStack stack) {
		return getAnimation() == EMPTY;
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, 0.03f, 0.441f, 0.8598f, -0.5409f, -0.541f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.3337f, 0.8048f, 0.9489f, 0.3666f, 0.6142f, 0.2619f);
		setPartDisplayTransform(gun, -0.282f, -0.721f, -0.202f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(hammer, -0.282f, -0.721f, -0.202f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(mag, -0.282f, -0.721f, -0.202f, -1.5358f, 0, 0.1047f);
		
	}

	@Override
	public void setupAnimations() {
		
		animator.setAnimation(R1);
		animator.startKeyframe(4);
		animator.move(leftarm, 0.0f, 0.0f, 0.02f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.move(rightarm, 0.0f, -0.003f, 0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		animator.move(rightarm, 0.0f, 0.001f, 0f);
		animator.move(gun, 0.0f, 0.001f, 0f);
		animator.move(hammer, 0.0f, 0.001f, 0f);
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R2);
		animator.startKeyframe(4);
		animator.move(leftarm, 0.0f, 0.0f, 0.02f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.move(rightarm, 0.0f, -0.003f, 0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		animator.move(rightarm, 0.0f, 0.001f, 0f);
		animator.move(gun, 0.0f, 0.001f, 0f);
		animator.move(hammer, 0.0f, 0.001f, 0f);
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.06999999f, -0.14999999f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.06999999f, -0.09999999f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, 0.003f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.06999999f, -0.14999999f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, -0.003f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R3);
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.move(rightarm, 0.0f, -0.003f, 0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.move(rightarm, 0.0f, -0.003f, 0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		animator.move(rightarm, 0.0f, 0.001f, 0f);
		animator.move(gun, 0.0f, 0.001f, 0f);
		animator.move(hammer, 0.0f, 0.001f, 0f);
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R4);
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.move(rightarm, 0.0f, -0.003f, 0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.move(rightarm, 0.0f, -0.003f, 0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		animator.move(rightarm, 0.0f, 0.001f, 0f);
		animator.move(gun, 0.0f, 0.001f, 0f);
		animator.move(hammer, 0.0f, 0.001f, 0f);
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.06999999f, -0.14999999f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.06999999f, -0.09999999f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, 0.003f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.06999999f, -0.14999999f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, -0.003f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.16f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(GOM);
		animator.startKeyframe(4);
		animator.move(leftarm, 0.0f, 0.0f, 0.02f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.move(rightarm, 0.0f, -0.003f, 0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, 0.0f, -0.5299998f, 0.03f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		animator.move(rightarm, 0.0f, 0.001f, 0f);
		animator.move(gun, 0.0f, 0.001f, 0f);
		animator.move(hammer, 0.0f, 0.001f, 0f);
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(KB);
		animator.startKeyframe(1);
		animator.move(leftarm, -0.17f, 0.0f, 0.0f);
		animator.move(gun, 0.43999985f, 0.0f, 0.0f);
		animator.move(hammer, 0.43999985f, 0.0f, 0.0f);
		animator.move(mag, 0.43999985f, 0.0f, 0.0f);
		animator.move(rightarm, 0.08999999f, 0.0f, -0.109999985f);
		animator.rotate(gun, 0.0f, 0.0f, 0.9250249f);
		animator.rotate(hammer, 0.0f, 0.0f, 0.9250249f);
		animator.rotate(mag, 0.0f, 0.0f, 0.9250249f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(leftarm, -0.17f, 0.0f, 0.0f);
		animator.move(gun, 0.43999985f, 0.0f, 0.0f);
		animator.move(hammer, 0.43999985f, 0.0f, 0.0f);
		animator.move(mag, 0.43999985f, 0.0f, 0.0f);
		animator.move(rightarm, 0.08999999f, 0.0f, -0.109999985f);
		animator.rotate(gun, 0.0f, 0.0f, 0.9250249f);
		animator.rotate(hammer, 0.0f, 0.0f, 0.9250249f);
		animator.rotate(mag, 0.0f, 0.0f, 0.9250249f);
		animator.endKeyframe();
		animator.resetKeyframe(6);
		animator.endKeyframe();
		
		animator.setAnimation(IA);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.28f, 0.0f, 0.0f);
		animator.move(gun, 0.19000001f, -0.12999998f, 0.059999995f);
		animator.move(hammer, 0.19000001f, -0.12999998f, 0.059999995f);
		animator.move(mag, 0.19000001f, -0.12999998f, 0.059999995f);
		animator.move(rightarm, -0.01f, 0.07999999f, 0.0f);
		animator.rotate(gun, 0.0f, -0.34906584f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.34906584f, 0.0f);
		animator.rotate(mag, 0.0f, -0.34906584f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.24434613f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(leftarm, -0.28f, 0.0f, 0.0f);
		animator.move(gun, 0.19000001f, -0.12999998f, 0.059999995f);
		animator.move(hammer, 0.19000001f, -0.12999998f, 0.059999995f);
		animator.move(mag, 0.19000001f, -0.12999998f, 0.059999995f);
		animator.move(rightarm, -0.01f, 0.07999999f, 0.0f);
		animator.rotate(gun, 0.0f, -0.34906584f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.34906584f, 0.0f);
		animator.rotate(mag, 0.0f, -0.34906584f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.24434613f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.28f, -0.01f, 0.0f);
		animator.move(gun, -0.14999999f, 0.27f, 0.11999998f);
		animator.move(hammer, -0.14999999f, 0.27f, 0.11999998f);
		animator.move(mag, -0.14999999f, 0.27f, 0.11999998f);
		animator.move(rightarm, -0.01f, -0.01f, 0.0f);
		animator.rotate(gun, 0.0f, 0.3490658f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.3490658f, 0.0f);
		animator.rotate(mag, 0.0f, 0.3490658f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.2617994f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(leftarm, -0.28f, -0.01f, 0.0f);
		animator.move(gun, -0.14999999f, 0.27f, 0.11999998f);
		animator.move(hammer, -0.14999999f, 0.27f, 0.11999998f);
		animator.move(mag, -0.14999999f, 0.27f, 0.11999998f);
		animator.move(rightarm, -0.01f, -0.01f, 0.0f);
		animator.rotate(gun, 0.0f, 0.3490658f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.3490658f, 0.0f);
		animator.rotate(mag, 0.0f, 0.3490658f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.2617994f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(SA);
		animator.startKeyframe(1);
		animator.move(hammer, 0.008f, -0.004f, 0.105f);
		animator.endKeyframe();
		animator.resetKeyframe(2);
		animator.endKeyframe();
		//AnimWriter.startAnimation(this, animator, IA);
	}

	@Override
	public void initReloadAnimation(int currentgunbullets, int magbullets, boolean hasMag) {
		if(hasMag) {
			if(magbullets == 0) {
				setAnimation(R1);
			}else {
				setAnimation(R2);
			}
		}else {
			if(magbullets == 0) {
				setAnimation(R3);
			}else {
				setAnimation(R4);
			}
		}
	}

	@Override
	public void onAnimTick(float animTick) {
		if(getAnimation() == R1) {
			if(animTick == 4) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGOUT.get());
				ReloadHandler.restoreMag(getMPath(), gunbullets);
			}else if(animTick == 19) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGIN.get());
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
				ReloadHandler.setBullets(magbullets);
			}
		}else if(getAnimation() == R2){
			if(animTick == 4) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGOUT.get());
				ReloadHandler.restoreMag(getMPath(), gunbullets);
			}else if(animTick == 19) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGIN.get());
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			}else if(animTick == 33) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UHAMMERBACK.get());
			}else if(animTick == 37) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UHAMMERFORWARD.get());
				ReloadHandler.setBullets(magbullets);
			}
		}else if(getAnimation() == R3) {
			if(animTick == 22) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGIN.get());
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			}
		}else if(getAnimation() == R4) {
			if(animTick == 22) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGIN.get());
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			}else if(animTick == 37) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UHAMMERBACK.get());
			}else if(animTick == 41) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UHAMMERFORWARD.get());
				ReloadHandler.setBullets(magbullets);
			}
		}else if(getAnimation() == GOM) {
			if(animTick == 3) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGOUT.get());
				ReloadHandler.restoreMag(getMPath(), gunbullets);
				ReloadHandler.setMag(this, 0, false, "", Constants.EMPTY);
				ReloadHandler.setBullets(0);
			}
		}else if(getAnimation() == KB) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 4) {
				MeleeHelper.hit(5f);
			}
		}
	}

	@Override
	public void onShoot(ItemStack stack) {
		setAnimation(SA);
	}

	@Override
	public void onAnimationEnd() {
		AnimWriter.onEndAnimation(this, IA);
	}

	@Override
	public void initExtraParts() {
		
	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		return new Ak74uModModel(origin);
	}

	@Override
	public String getHammerPath() {
		return Paths.AK74UHAMMER;
	}

	@Override
	public boolean hasMultipleParts() {
		return true;
	}

}
