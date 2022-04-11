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
import com.jg.oldguns.client.modmodels.RevolverModModel;
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

public class RevolverGunModel extends GunModel{

	public static final Animation SA = Animation.create(2);
	public static final RepetitiveAnimation R = Animation.createRep(26);
	public static final Animation R1 = Animation.create(26);
	public static final Animation R2 = Animation.create(36);
	public static final Animation R3 = Animation.create(46);
	public static final Animation R4 = Animation.create(56);
	public static final Animation R5 = Animation.create(66);
	public static final Animation R6 = Animation.create(76);
	public static final Animation KB = Animation.create(16);
	public static final Animation IA = Animation.create(28);
	
	public RevolverGunModel() {
		super(ItemRegistries.Revolver.get());
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		
	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.245f), Mth.lerp(p, 0, 0.145f), Mth.lerp(p, 0, 0.25f));
		matrix.mulPose(new Quaternion(Mth.lerp(p, 0, -0.0174f), Mth.lerp(p, 0, -0.0523f), 0, false));
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -18))));
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
		
		animator.setAnimation(R);
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
		R.anim(() -> {
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
		});
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(KB);
		animator.startKeyframe(8);
		animator.move(gun, 0.11400005f, 0.28199992f, -0.027000003f);
		animator.rotate(gun, 0.70686185f, 0.0f, 6.519258E-9f);
		animator.move(hammer, 0.11400005f, 0.28199992f, -0.027000003f);
		animator.rotate(hammer, 0.70686185f, 0.0f, 6.519258E-9f);
		animator.move(scope, 0.11400005f, 0.28199992f, -0.027000003f);
		animator.rotate(scope, 0.70686185f, 0.0f, 6.519258E-9f);
		animator.rotate(leftarm, 0.0f, 0.0f, -0.4049175f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.70686185f);
		animator.endKeyframe();
		animator.setStaticKeyframe(2);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(gun, 0.017f, 0.0f, 0.0f);
		animator.move(hammer, 0.017f, 0.0f, 0.0f);
		animator.move(scope, 6.0574636f, 14.984234f, -1.4346615f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.1466076f, 0.0f, 0.0f);
		animator.rotate(hammer, 0.1466076f, 0.0f, 0.0f);
		animator.rotate(scope, 37.55953f, 0.0f, 3.4640482E-7f);
		animator.rotate(rightarm, -9.313226E-10f, 0.0f, 0.08203044f);
		animator.endKeyframe();
		animator.resetKeyframe(2);
		animator.endKeyframe();
		
		animator.setAnimation(IA);
		animator.startKeyframe(4);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.23000002f, 0.07999999f, 0.0f);
		animator.move(gun, 0.0f, 0.45999983f, 0.0f);
		animator.move(hammer, 0.0f, 0.45999983f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.43633226f, 0.0f);
		animator.rotate(gun, 0.0f, 0.43633223f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.23000002f, 0.07999999f, 0.0f);
		animator.move(gun, 0.0f, 0.45999983f, 0.0f);
		animator.move(hammer, 0.0f, 0.45999983f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.43633226f, 0.0f);
		animator.rotate(gun, 0.0f, 0.43633223f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(hammer, 0.29f, -0.20000003f, 0.0f);
		animator.move(gun, 0.29f, -0.20000003f, 0.0f);
		animator.rotate(leftarm, -0.80285174f, -0.12217303f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(gun, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.29f, -0.20000003f, 0.0f);
		animator.move(hammer, 0.29f, -0.20000003f, 0.0f);
		animator.rotate(leftarm, -0.80285174f, -0.12217303f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(gun, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
	}

	@Override
	public void initReloadAnimation(int gun, int mag, boolean hasMag) {
		if(getAnimation() == EMPTY) {
			int bf = 6 - gun;
			if (mag > bf && bf != 0) {
				mag = bf;
				R.setDuration(16 + (mag*10));
				R.setTimes(mag);
			} else {
				R.setDuration(16 + (mag*10));
				R.setTimes(mag);
			}
			setAnimation(R);
		}
	}

	@Override
	public void onAnimTick(float animTick) {
		if(getAnimation() != KB && getAnimation() != IA) {
			if(animTick == 12) {
				SoundHandler.playSoundOnServer(SoundRegistries.REVOLVERSPIN.get());
			}else if(animTick == 14){
				SoundHandler.playSoundOnServer(SoundRegistries.REVOLVER_BULLETS_OUT.get());
			}
			if(R.isRepTime(14, 10, animTick)) {
				ReloadHandler.removeItem(ammoindex, 1);
				ReloadHandler.growOneBullet(Util.getStack());
				SoundHandler.playSoundOnServer(SoundRegistries.REVOLVER_BULLETS_INSIDE.get());
			}
		}else if(getAnimation() == KB){
			if(animTick == 3) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 10) {
				MeleeHelper.hit(4.5f);
			}
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
	public WrapperModel getModifiedModel(BakedModel origin) {
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
		// TODO Auto-generated method stub
		return new Vector3f(0.17f, -0.18f, -1.41f);
	}

}
