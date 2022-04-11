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
import com.jg.oldguns.client.modmodels.WinchesterModModel;
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

public class WinchesterGunModel extends GunModel {

	public static RepetitiveAnimation R = Animation.createRep(22);
	public static final Animation KB = Animation.create(12);
	public static final Animation IA = Animation.create(36);
	public static Animation SHOOT = Animation.create(10);

	public WinchesterGunModel() {
		super(ItemRegistries.Winchester.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.2770f), Mth.lerp(p, 0, 0.047f), 0);
		matrix.mulPose(new Quaternion(Mth.lerp(p, 0, -0.0174f), Mth.lerp(p, 0, -0.0523f), 0, false));
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -24))));

	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, scope };
	}

	@Override
	public boolean canRenderMag(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canShoot(ItemStack stack) {
		return getAnimation() == EMPTY;
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, 0.04f, 0.371f, 0.7298f, -0.506f, -0.541f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.2797f, 0.8198f, 0.6989f, 0.3666f, 0.8202f, 0.2619f);
		setPartDisplayTransform(gun, -0.19f, -0.93f, 0, -1.5533f, 0, 0.0523f);
		setPartDisplayTransform(hammer, -0.19f, -0.93f, 0, -1.5533f, 0, 0.0523f);
		setPartDisplayTransform(scope, -0.243f, -0.815f, 0.341f, -1.5533f, 0, 0.0523f);
	}

	@Override
	public void setupAnimations() {

		animator.setAnimation(R);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, -0.07f, 0, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(scope, 0.106f, -0.108f, 0);
		animator.rotate(scope, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.11f, 0.06f, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(scope, 0.106f, -0.108f, 0);
		animator.rotate(scope, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		R.anim(() -> {
			// Push bullet cycle
						animator.startKeyframe(4);
						animator.move(leftarm, -0.107f, -0.019f, 0);
						animator.move(rightarm, 0.08f, 0.06f, -0.14f);
						animator.move(gun, 0.106f, -0.108f, 0);
						animator.rotate(gun, 0, -0.2268f, 0);
						animator.move(scope, 0.076f, -0.108f, 0);
						animator.rotate(scope, 0, -0.2268f, 0);
						animator.move(hammer, 0.106f, -0.108f, 0);
						animator.rotate(hammer, 0, -0.2268f, 0);
						animator.endKeyframe();
						animator.startKeyframe(2);
						animator.move(leftarm, -0.107f, -0.019f, 0);
						animator.move(rightarm, 0.08f, 0.06f, -0.18f);
						animator.move(gun, 0.106f, -0.108f, 0);
						animator.rotate(gun, 0, -0.2268f, 0);
						animator.move(scope, 0.076f, -0.108f, 0);
						animator.rotate(scope, 0, -0.2268f, 0);
						animator.move(hammer, 0.106f, -0.108f, 0);
						animator.rotate(hammer, 0, -0.2268f, 0);
						animator.endKeyframe();
						animator.startKeyframe(4);
						animator.move(leftarm, -0.107f, -0.019f, 0);
						animator.move(rightarm, 0.11f, 0.06f, 0);
						animator.move(gun, 0.106f, -0.108f, 0);
						animator.rotate(gun, 0, -0.2268f, 0);
						animator.move(scope, 0.076f, -0.108f, 0);
						animator.rotate(scope, 0, -0.2268f, 0);
						animator.move(hammer, 0.106f, -0.108f, 0);
						animator.rotate(hammer, 0, -0.2268f, 0);
						animator.endKeyframe();
						// End of the cycle
		});
		animator.resetKeyframe(4);// 12
		animator.endKeyframe();
		
		animator.setAnimation(SHOOT);
		animator.setStaticKeyframe(2);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0, -0.065f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(KB);
		animator.startKeyframe(2);
		animator.move(leftarm, -0.26600012f, 0.0f, 0.2610002f);
		animator.move(gun, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(gun, 0.0f, 0.0f, 1.4870303f);
		animator.move(scope, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(scope, 0.0f, 0.0f, 1.4870303f);
		animator.move(hammer, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.4870303f);
		animator.rotate(rightarm, 0.56548905f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(leftarm, -0.26600012f, 0.0f, 0.2610002f);
		animator.move(gun, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(gun, 0.0f, 0.0f, 1.4870303f);
		animator.move(scope, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(scope, 0.0f, 0.0f, 1.4870303f);
		animator.move(hammer, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.4870303f);
		animator.rotate(rightarm, 0.56548905f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(8);
		animator.endKeyframe();
		
		animator.setAnimation(IA);
		animator.startKeyframe(4);
		animator.move(gun, -0.16f, 0.28f, 0.0f);
		animator.move(scope, -0.16f, 0.28f, 0.0f);
		animator.move(hammer, -0.16f, 0.28f, 0.0f);
		animator.move(leftarm, -0.33999994f, 0.0f, 0.0f);
		animator.move(rightarm, -0.03f, -0.08999999f, 0.0f);
		animator.rotate(gun, 0.0f, 0.45378554f, 0.0f);
		animator.rotate(scope, 0.0f, 0.45378554f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.45378554f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.45378554f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(gun, -0.16f, 0.28f, 0.0f);
		animator.move(scope, -0.16f, 0.28f, 0.0f);
		animator.move(hammer, -0.16f, 0.28f, 0.0f);
		animator.move(leftarm, -0.33999994f, 0.0f, 0.0f);
		animator.move(rightarm, -0.03f, -0.08999999f, 0.0f);
		animator.rotate(gun, 0.0f, 0.45378554f, 0.0f);
		animator.rotate(scope, 0.0f, 0.45378554f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.45378554f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.45378554f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(gun, 0.07999999f, -0.080000006f, 0.0f);
		animator.move(hammer, 0.07999999f, -0.080000006f, 0.0f);
		animator.move(leftarm, -0.6299997f, -0.16f, 0.0f);
		animator.move(rightarm, -0.36999992f, 0.13999999f, 0.0f);
		animator.rotate(gun, 0.0f, -0.4537855f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.4537855f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.45378548f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(gun, 0.07999999f, -0.080000006f, 0.0f);
		animator.move(scope, 0.07999999f, -0.080000006f, 0.0f);
		animator.move(hammer, 0.07999999f, -0.080000006f, 0.0f);
		animator.move(leftarm, -0.6299997f, -0.16f, 0.0f);
		animator.move(rightarm, -0.36999992f, 0.13999999f, 0.0f);
		animator.rotate(gun, 0.0f, -0.4537855f, 0.0f);
		animator.rotate(scope, 0.0f, -0.4537855f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.4537855f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.45378548f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
	}

	@Override
	public void initReloadAnimation(int gun, int mag, boolean hasMag) {
		int bf = 6 - gun;
		if (mag > bf && bf != 0) {
			mag = bf;
			R.setDuration(12 + (mag*10));
			R.setTimes(mag);
		} else {
			R.setDuration(12 + (mag*10));
			R.setTimes(mag);
		}
		setAnimation(R);
	}

	@Override
	public void onAnimTick(float animTick) {
		if (getAnimation() == R) {
			if (R.isRepTime(10, 10, animTick)) {//animTick % 10 == 3 && animTick > 10) {
				System.out.println(animTick % 10);
				SoundHandler.playSoundOnServer(SoundRegistries.KARKPUSH.get(), SoundSource.NEUTRAL);
				ReloadHandler.growOneBullet(Util.getStack());
				ReloadHandler.removeOneItemFrom(ammoindex);
			}
		}else if(getAnimation() == SHOOT){
			if(animTick == 2) {
				SoundHandler.playSoundOnServer(SoundRegistries.WINCHESTERBOLTFORWARD.get(), SoundSource.NEUTRAL);
			}else if(animTick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.WINCHESTERBOLTFORWARD.get(), SoundSource.NEUTRAL);
			}
		}else if(getAnimation() == KB) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 4) {
				MeleeHelper.hit(6.5f);
			}
		}

	}

	@Override
	public void onShoot(ItemStack stack) {
		setAnimation(SHOOT);
	}

	@Override
	public void onAnimationEnd() {

	}

	@Override
	public void initExtraParts() {
		// TODO Auto-generated method stub

	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		// TODO Auto-generated method stub
		return new WinchesterModModel(origin);
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.WINCHESTERHAMMER;
	}

	@Override
	public boolean hasMultipleParts() {
		// TODO Auto-generated method stub
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
		return new Vector3f(0.16f, -0.12f, -2.04f);
	}

}
