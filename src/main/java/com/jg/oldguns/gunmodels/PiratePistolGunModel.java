package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.modmodels.PiratePistolModModel;
import com.jg.oldguns.debug.AnimWriter;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.MeleeHelper;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PiratePistolGunModel extends GunModel {

	public static final Animation R = Animation.create(48);
	public static final Animation KB = Animation.create(16);
	public static final Animation IA = Animation.create(28);

	public PiratePistolGunModel() {
		super(ItemRegistries.PiratePistol.get());
	}

	@Override
	public void initExtraParts() {

	}

	@Override
	public void onAnimationEnd() {
		AnimWriter.onEndAnimation(this, IA);
	}

	@Override
	public boolean canRenderMag(ItemStack arg0) {
		return false;
	}

	@Override
	public boolean canShoot(ItemStack arg0) {
		return getAnimation() == GunModel.EMPTY;
	}

	@Override
	public void doGetOutMag(ItemStack arg0) {

	}

	@Override
	public String getHammerPath() {
		return Paths.PPHAM;
	}

	@Override
	public BakedModel getModifiedModel(BakedModel origin) {
		return new PiratePistolModModel(origin);
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, scope };
	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.24f), Mth.lerp(p, 0, 0.15f), 0);
		matrix.mulPose(new Quaternion(0, Mth.lerp(p, 0, -0.0523f), 0, false));
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -24))));
	}

	@Override
	public void initReloadAnimation(int gunbullets, int magbullets) {

		int totalGunPowder = ServerUtils.getTotalItemAmout(Minecraft.getInstance().player.getInventory(), Items.GUNPOWDER);

		if (gunbullets == 0 && magbullets != 0 && totalGunPowder >= 1) {
			setAnimation(R);
		}

	}

	@Override
	public void onAnimTick(float arg0) {

		if (animation == R) {
			if (animTick == 12) {
				ReloadHandler.setHammer(true);
				SoundHandler.playSoundOnServer(SoundRegistries.FLINTLOCK_HAMMER.get());
			} else if (animTick == 44) {
				if (ServerUtils.getTotalItemAmout(Minecraft.getInstance().player.getInventory(), Items.GUNPOWDER) >= 1) {
					ReloadHandler.removeItem(
							ServerUtils.getIndexForItem(Minecraft.getInstance().player.getInventory(), Items.GUNPOWDER), 1);
					ReloadHandler.removeItem(ammoindex, 1);
					ReloadHandler.growOneBullet(Util.getStack());
				} else {
					ReloadHandler.setHammer(false);
					ReloadHandler.setBullets(0);
					setAnimation(EMPTY);
				}
			}
		}else if(getAnimation() == KB) {
			if(animTick == 3) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 10) {
				MeleeHelper.hit(3.5f);
			}
		}
	}

	@Override
	public void onShoot(ItemStack stack) {
		if (ServerUtils.getHammer(stack)) {
			ReloadHandler.setHammer(false);
			ReloadHandler.setBullets(0);
		}
	}

	@Override
	public void setupAnimations() {

		animator.setAnimation(R);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.3f, 0, 0);
		animator.rotate(rightarm, 0, -0.5061f, 0);
		animator.move(gun, 0.3f, -0.25f, 0);
		animator.rotate(gun, 0, -0.5061f, 0);
		animator.move(hammer, 0.3f, -0.26f, 0.02f);
		animator.rotate(hammer, 0, -0.5061f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0, -0.5061f, 0);
		animator.move(gun, 0.3f, -0.25f, 0);
		animator.rotate(gun, 0, -0.5061f, 0);
		animator.move(hammer, 0.3f, -0.26f, 0.02f);
		animator.rotate(hammer, 0, -0.5061f, 0);
		animator.move(leftarm, -0.17f, 0.2f, 0);
		animator.rotate(leftarm, 0.2443f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0, -0.5061f, 0);
		animator.move(gun, 0.3f, -0.25f, 0);
		animator.rotate(gun, 0, -0.5061f, 0);
		animator.move(hammer, 0.3f, -0.26f, 0.02f);
		animator.rotate(hammer, 0, -0.5061f, 0);
		animator.move(leftarm, -0.17f, 0.2f, 0.06f);
		animator.rotate(leftarm, 0.2443f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0, -0.5061f, 0);
		animator.move(gun, 0.3f, -0.25f, 0);
		animator.rotate(gun, 0, -0.5061f, 0);
		animator.move(hammer, 0.3f, -0.26f, 0.02f);
		animator.rotate(hammer, 0, -0.5061f, 0);
		animator.move(leftarm, -0.17f, 0.2f, 0);
		animator.rotate(leftarm, 0.2443f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0, -0.5061f, 0);
		animator.move(gun, 0.3f, -0.25f, 0);
		animator.rotate(gun, 0, -0.5061f, 0);
		animator.move(hammer, 0.3f, -0.26f, 0.02f);
		animator.rotate(hammer, 0, -0.5061f, 0);
		animator.move(leftarm, -0.17f, 0.2f, 0);
		animator.rotate(leftarm, 0.2443f, 0, 0.0523f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0, -0.5061f, 0);
		animator.move(gun, 0.3f, -0.25f, 0);
		animator.rotate(gun, 0, -0.5061f, 0);
		animator.move(hammer, 0.3f, -0.26f, 0.02f);
		animator.rotate(hammer, 0, -0.5061f, 0);
		animator.move(leftarm, -0.17f, 0.2f, 0);
		animator.rotate(leftarm, 0.2443f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0, -0.5061f, 0);
		animator.move(gun, 0.3f, -0.25f, 0);
		animator.rotate(gun, 0, -0.5061f, 0);
		animator.move(hammer, 0.3f, -0.26f, 0.02f);
		animator.rotate(hammer, 0, -0.5061f, 0);
		animator.move(leftarm, -0.17f, 0.2f, 0);
		animator.rotate(leftarm, 0.2443f, 0, 0.0523f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0, -0.5061f, 0);
		animator.move(gun, 0.3f, -0.25f, 0);
		animator.rotate(gun, 0, -0.5061f, 0);
		animator.move(hammer, 0.3f, -0.26f, 0.02f);
		animator.rotate(hammer, 0, -0.5061f, 0);
		animator.move(leftarm, -0.17f, 0.2f, 0);
		animator.rotate(leftarm, 0.2443f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.25f, -0.21f, -0.26f);
		animator.rotate(rightarm, -0.9250f, 0.3141f, 0.1919f);
		animator.move(gun, 0.14f, -0.21f, 0.17f);
		animator.rotate(gun, 0.4886f, 0.3141f, 0.7679f);
		animator.move(hammer, 0.14f, -0.21f, 0.17f);
		animator.rotate(hammer, 0.4886f, 0.3141f, 0.7679f);
		animator.move(leftarm, -0.52f, 0.2f, 0);
		animator.rotate(leftarm, -0.4188f, 0, 0.3141f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.25f, -0.21f, -0.26f);
		animator.rotate(rightarm, -0.9250f, 0.3141f, 0.1919f);
		animator.move(gun, 0.14f, -0.21f, 0.17f);
		animator.rotate(gun, 0.4886f, 0.3141f, 0.7679f);
		animator.move(hammer, 0.14f, -0.21f, 0.17f);
		animator.rotate(hammer, 0.4886f, 0.3141f, 0.7679f);
		animator.move(leftarm, -0.52f, 0.2f, 0);
		animator.rotate(leftarm, -0.3665f, 0, 0.3141f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.25f, -0.21f, -0.26f);
		animator.rotate(rightarm, -0.9250f, 0.3141f, 0.1919f);
		animator.move(gun, 0.14f, -0.21f, 0.17f);
		animator.rotate(gun, 0.4886f, 0.3141f, 0.7679f);
		animator.move(hammer, 0.14f, -0.21f, 0.17f);
		animator.rotate(hammer, 0.4886f, 0.3141f, 0.7679f);
		animator.move(leftarm, -0.52f, 0.2f, 0);
		animator.rotate(leftarm, -0.4188f, 0, 0.3141f);
		animator.endKeyframe();
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
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.14999999f, 0.03f, 0.0f);
		animator.move(hammer, 0.35999992f, 0.27f, -0.03f);
		animator.move(gun, 0.0f, 0.33999994f, 0.0f);
		animator.rotate(rightarm, 3.7252903E-9f, 0.4363323f, 0.0f);
		animator.rotate(hammer, 4.0978193E-8f, 4.0978193E-8f, 0.43633226f);
		animator.rotate(gun, 0.0f, 0.43633226f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.14999999f, 0.03f, 0.0f);
		animator.move(hammer, 0.35999992f, 0.27f, -0.03f);
		animator.move(gun, 0.0f, 0.33999994f, 0.0f);
		animator.rotate(rightarm, 3.7252903E-9f, 0.4363323f, 0.0f);
		animator.rotate(hammer, 4.0978193E-8f, 4.0978193E-8f, 0.43633226f);
		animator.rotate(gun, 0.0f, 0.43633226f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, -0.02f, 0.21000002f, 0.0f);
		animator.move(hammer, -0.14f, -0.11f, 0.02f);
		animator.move(gun, 0.21000002f, -0.030000014f, 0.0f);
		animator.rotate(rightarm, 3.7252903E-9f, -0.4363322f, 0.0f);
		animator.rotate(hammer, 4.0978193E-8f, 4.0978193E-8f, -0.43633223f);
		animator.rotate(leftarm, -0.66322523f, 3.7252903E-9f, 0.0f);
		animator.rotate(gun, 0.0f, -0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, -0.02f, 0.21000002f, 0.0f);
		animator.move(hammer, -0.14f, -0.11f, 0.02f);
		animator.move(gun, 0.21000002f, -0.030000014f, 0.0f);
		animator.rotate(rightarm, 3.7252903E-9f, -0.4363322f, 0.0f);
		animator.rotate(hammer, 4.0978193E-8f, 4.0978193E-8f, -0.43633223f);
		animator.rotate(leftarm, -0.66322523f, 3.7252903E-9f, 0.0f);
		animator.rotate(gun, 0.0f, -0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, -0.13f, 0.37f, 0.64f, -0.2967f, -0.5410f, -0.4014f);
		setPartDisplayTransform(leftarm, -0.31f, 0.86f, 0.8789f, 0.2967f, -0.5410f, -0.4014f);
		setPartDisplayTransform(gun, -0.26f, -0.74f, -0.66f, -1.5533f, 0, 0.0524f);
		if (ClientEventHandler.getPlayer() != null) {
			if (ServerUtils.getHammer(Util.getStack())) {
				setPartDisplayTransform(hammer, -0.26f, -0.74f, -0.66f, -1.5533f, 0, 0.0524f);
			} else {
				setPartDisplayTransform(hammer, -0.27f, 0.11f, -0.38f, -3.1589f, 0, 0.0524f);
			}
		}
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
		return new Vector3f();
	}

}
