package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.animations.RepetitiveAnimation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.RecoilHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.modmodels.ColtModModel;
import com.jg.oldguns.client.modmodels.Model37ModModel;
import com.jg.oldguns.debug.AnimWriter;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.MeleeHelper;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class Model37GunModel extends GunModel{

	public static final Animation SA = Animation.create(20);
	public static final RepetitiveAnimation R = Animation.createRep(26);
	public static final Animation R1 = Animation.create(46);
	public static final Animation R2 = Animation.create(46);
	public static final Animation R3 = Animation.create(20);
	public static final Animation R4 = Animation.create(36);
	public static final Animation KB = Animation.create(13);
	public static final Animation IA = Animation.create(32);
	
	public Model37GunModel() {
		super(ItemRegistries.Model37.get());
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		
	}

	@Override
	public void handleAim(PoseStack matrix, float aimProgress) {
		matrix.translate(Mth.lerp(aimProgress, 0, -0.279f), Mth.lerp(aimProgress, 0, 0.117f), Mth.lerp(aimProgress, 0, 0.25f));
		matrix.mulPose(new Quaternion(Mth.lerp(aimProgress, 0, -0.0523f), Mth.lerp(aimProgress, 0, -0.1082f), 0, false));
		
		//debugAim(matrix, aimProgress);
	}

	@Override
	public void handleSprint(PoseStack matrix, float aimProgress) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(aimProgress, 0, -24))));
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, aim };
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
		setPartDisplayTransform(rightarm, 0.0400f, 0.3901f, 0.7298f, -0.5060f, -0.5410f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.3897f, 0.8298f, 0.6389f, 0.3666f, 0.8202f, 0.2619f);
		setPartDisplayTransform(gun, -0.17f, -0.97f, 0.13f, -1.5183f, 0, 0.1047f);
		setPartDisplayTransform(hammer, -0.17f, -0.97f, 0.13f, -1.5183f, 0, 0.1047f);
	}

	@Override
	public void setupAnimations() {
		animator.setAnimation(SA);
		animator.setStaticKeyframe(10);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(hammer, 0.0f, 0.0f, 0.116000056f);
		animator.move(leftarm, 0.0f, 0.0f, 0.10200003f);
		animator.endKeyframe();
		animator.resetKeyframe(6);
		animator.endKeyframe();
		
		animator.setAnimation(R);
		animator.startKeyframe(4);
		animator.move(gun, -0.11999998f, 0.13999999f, 0.0f);
		animator.move(hammer, -0.11999998f, 0.13999999f, 0.0f);
		animator.move(rightarm, 0.0f, -0.049999997f, 0.0f);
		animator.move(leftarm, -0.18f, -0.11999998f, 0.7599996f);
		animator.rotate(gun, 0.0f, 0.26179942f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.26179942f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.26179942f, 0.0f);
		animator.endKeyframe();
		R.anim(() -> {
		animator.startKeyframe(4);
		animator.move(gun, -0.11999998f, 0.13999999f, 0.0f);
		animator.move(hammer, -0.11999998f, 0.13999999f, 0.0f);
		animator.move(leftarm, 0.09999999f, -0.04f, 0.19000003f);
		animator.move(rightarm, 0.0f, -0.049999997f, 0.0f);
		animator.rotate(gun, 0.0f, 0.26179942f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.26179942f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.26179942f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(leftarm, 0.09999999f, -0.04f, 0.15f);
		animator.move(gun, -0.11999998f, 0.13999999f, 0.0f);
		animator.move(hammer, -0.11999998f, 0.13999999f, 0.0f);
		animator.move(rightarm, 0.0f, -0.049999997f, 0.0f);
		animator.rotate(gun, 0.0f, 0.26179942f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.26179942f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.26179942f, 0.0f);
		animator.endKeyframe();
		});
		animator.resetKeyframe(4);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(hammer, 0.0f, 0.0f, 0.116000056f);
		animator.move(leftarm, 0.0f, 0.0f, 0.10200003f);
		animator.endKeyframe();
		animator.resetKeyframe(6);
		animator.endKeyframe();
		
		animator.setAnimation(KB);
		animator.startKeyframe(2);
		animator.move(rightarm, 0.23000002f, 0.0f, 0.0f);
		animator.move(gun, 0.9899994f, 0.0f, -0.17f);
		animator.move(hammer, 0.9899994f, 0.0f, -0.17f);
		animator.move(leftarm, -0.32999995f, 0.0f, 0.28f);
		animator.rotate(gun, 0.0f, 0.0f, 1.099558f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.099558f);
		animator.endKeyframe();
		animator.startKeyframe(3);
		animator.move(rightarm, 0.23000002f, 0.0f, 0.0f);
		animator.move(gun, 0.9899994f, 0.0f, -0.17f);
		animator.move(hammer, 0.9899994f, 0.0f, -0.17f);
		animator.move(leftarm, -0.32999995f, 0.0f, 0.28f);
		animator.rotate(gun, 0.0f, 0.0f, 1.099558f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.099558f);
		animator.endKeyframe();
		animator.resetKeyframe(8);
		animator.endKeyframe();
		
		animator.setAnimation(IA);
		animator.startKeyframe(4);
		animator.move(gun, 0.25000003f, -0.07999999f, 0.0f);
		animator.move(hammer, 0.25000003f, -0.07999999f, 0.0f);
		animator.move(rightarm, -0.17f, 0.18f, 0.0f);
		animator.move(leftarm, -0.3899999f, 0.0f, 0.03f);
		animator.rotate(gun, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.45378554f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(gun, 0.25000003f, -0.07999999f, 0.0f);
		animator.move(hammer, 0.25000003f, -0.07999999f, 0.0f);
		animator.move(rightarm, -0.17f, 0.18f, 0.0f);
		animator.move(leftarm, -0.3899999f, 0.0f, 0.03f);
		animator.rotate(gun, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.45378554f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(gun, -0.11f, 0.29f, 0.0f);
		animator.move(hammer, -0.11f, 0.29f, 0.0f);
		animator.move(rightarm, 0.09999997f, -0.059999995f, -0.03f);
		animator.move(leftarm, 0.22000004f, 0.0f, 0.03f);
		animator.rotate(gun, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.4537855f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(gun, -0.11f, 0.29f, 0.0f);
		animator.move(hammer, -0.11f, 0.29f, 0.0f);
		animator.move(rightarm, 0.09999997f, -0.059999995f, -0.03f);
		animator.move(leftarm, 0.22000004f, 0.0f, 0.03f);
		animator.rotate(gun, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.4537855f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

	}

	@Override
	public void initReloadAnimation(int gun, int mag, boolean hasMag) {
		int maxAmmo = getGunItem().getMaxAmmo(Util.getStack());
		System.out.println(maxAmmo-gun);
		int bf = maxAmmo-gun;
		if (mag > bf && bf != 0) {
			mag = bf;
			R.setDuration(18 + (12*mag));
			R.setTimes(mag);
		} else {
			R.setDuration(18 + (12*mag));
			R.setTimes(mag);
		}
		setAnimation(R);
	}

	@Override
	public void onAnimTick(float animTick) {
		if(getAnimation() == SA) {
			if(animTick == 14) {
				SoundHandler.playSoundOnServer(SoundRegistries.MODEL37_SLIDE_BACK.get());
			}else if(animTick == 20) {
				SoundHandler.playSoundOnServer(SoundRegistries.MODEL37_SLIDE_FORWARD.get());
			}
		}else if(getAnimation() == R){
			System.out.println((animTick-4)%12);
			if(R.isRepTime(4, 12, animTick)) {
				SoundHandler.playSoundOnServer(SoundRegistries.MODEL37_SHELL_INSERT.get());
				ReloadHandler.growOneBullet(Util.getStack());
				ReloadHandler.removeItem(ammoindex, 1);
			}else if(animTick >= R.getDuration()-14) {
				if(animTick == R.getDuration()-8) {
					SoundHandler.playSoundOnServer(SoundRegistries.MODEL37_SLIDE_BACK.get());
				}else if(animTick == R.getDuration()-13) {
					SoundHandler.playSoundOnServer(SoundRegistries.MODEL37_SLIDE_FORWARD.get());
				}
			}
		}else if(getAnimation() == KB) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 3) {
				MeleeHelper.hit(7);
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
		return new Model37ModModel(origin);
	}

	@Override
	public String getHammerPath() {
		return Paths.MODEL37LOADER;
	}

	@Override
	public boolean hasMultipleParts() {
		return true;
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
		return new Vector3f(0.08f, -0.1f, -1.84f);
	}

}
