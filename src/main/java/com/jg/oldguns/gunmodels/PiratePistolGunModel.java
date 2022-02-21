package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.modmodels.PiratePistolModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class PiratePistolGunModel extends GunModel {

	public static final Animation R = Animation.create(48);

	public PiratePistolGunModel() {
		super(ItemRegistries.PiratePistol.get());
	}

	@Override
	public void initExtraParts() {

	}

	@Override
	public void onAnimationEnd() {

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
	public IBakedModel getModifiedModel(IBakedModel origin) {
		return new PiratePistolModModel(origin);
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, scope };
	}

	@Override
	public void handleAim(MatrixStack matrix, float p) {
		matrix.translate(MathHelper.lerp(p, 0, -0.24f), MathHelper.lerp(p, 0, 0.15f), 0);
		matrix.mulPose(new Quaternion(0, MathHelper.lerp(p, 0, -0.0523f), 0, false));
	}

	@Override
	public void handleSprint(MatrixStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(MathHelper.rotLerp(p, 0, -24))));
	}

	@Override
	public void initReloadAnimation(int gunbullets, int magbullets) {

		int totalGunPowder = ServerUtils.getTotalItemAmout(Minecraft.getInstance().player.inventory, Items.GUNPOWDER);

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
				if (ServerUtils.getTotalItemAmout(Minecraft.getInstance().player.inventory, Items.GUNPOWDER) >= 1) {
					ReloadHandler.removeItem(
							ServerUtils.getIndexForItem(Minecraft.getInstance().player.inventory, Items.GUNPOWDER), 1);
					ReloadHandler.removeItem(ammoindex, 1);
					ReloadHandler.growOneBullet(Util.getStack());
				} else {
					ReloadHandler.setHammer(false);
					ReloadHandler.setBullets(0);
					setAnimation(EMPTY);
				}
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
		// TODO Auto-generated method stub
		return true;
	}

}
