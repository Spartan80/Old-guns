package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.RecoilHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.modmodels.ColtModModel;
import com.jg.oldguns.client.modmodels.PirateRifleModModel;
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
import net.minecraft.world.item.Items;

public class PirateRifleGunModel extends GunModel {

	public static final Animation R = Animation.create(76);
	public static final Animation KB = Animation.create(12);

	public PirateRifleGunModel() {
		super(ItemRegistries.PirateRifle.get());
	}

	public void render(PoseStack matrix, ItemStack stack, MultiBufferSource itembuffer, BufferSource armbuffer, int light,
			int overlay, float partialTicks, float aimProgress, float sprintProgress, RecoilHandler handler) {
		if (!(ServerUtils.hasScope(stack) && ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID())
				.getAimingHandler().isFullAimingProgress())) {
			matrix.pushPose();

			handleAim(matrix, aimProgress);
			renderMuzzleFlash(matrix, armbuffer, partialTicks, stack);
			handleSprint(matrix, sprintProgress);

			matrix.translate(0, 0, this.gunitem.handleZRecoil(handler.getRecoil(stack), stack));
			matrix.mulPose(new Quaternion(0,
					0, (float)Math.sin(this.gunitem.handleRHorRecoil(handler.getRecoil(stack), stack)), true));
			renderRightArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			if (both) {
				renderLeftArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			}
			armbuffer.endBatch();
			if (hasMultipleParts()) {
				renderBarrel(matrix, stack, itembuffer, light, overlay);
				renderBody(matrix, stack, itembuffer, light, overlay);
				renderStock(matrix, stack, itembuffer, light, overlay);
			} else {
				renderGun(matrix, stack, itembuffer, light, overlay);
			}
			renderHammer(matrix, stack, itembuffer, light, overlay);
			if (canRenderMag(stack)) {
				renderMag(matrix, stack, itembuffer, light, overlay);
			}
			if(hasScope) {
				if(ServerUtils.getScopePath(stack) != "") {
					renderScope(matrix, stack, itembuffer, light, overlay);
				}
			}
			matrix.popPose();
		}
	}
	
	@Override
	public void doGetOutMag(ItemStack stack) {

	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.317f), Mth.lerp(p, 0, 0.192f), Mth.lerp(p, 0, 0.318f));
		matrix.mulPose(new Quaternion(Mth.lerp(p, 0, -0.0698f), Mth.lerp(p, 0, -0.1099f), 0, false));
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -24))));

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
		return getAnimation() == EMPTY;
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, 0.0400f, 0.381f, 0.7298f, -0.506f, -0.541f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.2897f, 0.7898f, 0.7288f, 0.3666f, 0.8202f, 0.2619f);
		setPartDisplayTransform(gun, -0.19f, -0.76f, -0.36f, -1.5009f, 0, 0.1047f);
		setPartDisplayTransform(scope, -0.189f, -0.962f, 0.258f, -1.5009f, 0, 0.1047f);
		if (ClientEventHandler.getPlayer() != null) {
			if (ServerUtils.getHammer(Util.getStack())) {
				setPartDisplayTransform(hammer, -0.215f, -0.73f, -0.293f, -1.5724f, 0.0017f, 0.0453f);
			} else {
				setPartDisplayTransform(hammer, -0.188f, 0.21f, -0.171f, -3.1153f, -0.0506f, 0.0453f);
			}
		}

	}

	@Override
	public void setupAnimations() {

		animator.setAnimation(R);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.15f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.01f, 0.15f, 0.27f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.11f, 0.3f, 0.15f);
		animator.rotate(leftarm, 0.2792f, -0.5410f, 0.1919f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.11f, 0.3f, 0.15f);
		animator.rotate(leftarm, 0.2792f, -0.5410f, 0.2617f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.11f, 0.3f, 0.15f);
		animator.rotate(leftarm, 0.2792f, -0.5410f, 0.1919f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.11f, 0.3f, 0.15f);
		animator.rotate(leftarm, 0.2094f, -0.5410f, 0.1919f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.11f, 0.3f, 0.15f);
		animator.rotate(leftarm, 0.2792f, -0.5410f, 0.1919f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.11f, 0.3f, 0.15f);
		animator.rotate(leftarm, 0.2094f, -0.5410f, 0.1919f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.11f, 0.3f, 0.15f);
		animator.rotate(leftarm, 0.2792f, -0.5410f, 0.1919f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.15f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, 0, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0, 0, 0.6283f);
		animator.move(leftarm, 0.04f, 0.02f, 0.14f);
		animator.rotate(leftarm, -0.4712f, 0, 0.2443f);
		animator.move(gun, 0, 0, -0.78f);
		animator.rotate(gun, 1.5707f, 0, 0);
		//tx: -0.11999997 ty: -0.49999982 tz: -0.9399994
		animator.move(scope, -0.11999997f, -0.49999982f, -0.9399994f);
		animator.rotate(scope, 1.5707f, 0, 0);
		animator.move(hammer, 0, 0, -0.78f);
		animator.rotate(hammer, 1.5707f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.rotate(rightarm, 0, 0, 0.6283f);
		animator.move(leftarm, 0.04f, 0.02f, 0.14f);
		animator.rotate(leftarm, -0.4712f, 0, 0.2443f);
		animator.move(gun, 0, 0, -0.78f);
		animator.rotate(gun, 1.5707f, 0, 0);
		animator.move(scope, 0, 0, -0.78f);
		animator.rotate(scope, 1.5707f, 0, 0);
		animator.move(hammer, 0, 0, -0.78f);
		animator.rotate(hammer, 1.5707f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, -0.75f, 0);
		animator.rotate(rightarm, 0, 0, 0.6283f);
		animator.move(leftarm, -0.05f, 0.2f, 0.14f);
		animator.rotate(leftarm, -0.4712f, 0, 0.2443f);
		animator.move(gun, 0, -0.4f, -0.78f);
		animator.rotate(gun, 1.5707f, 0, 0);
		animator.move(scope, 0, -0.4f, -0.78f);
		animator.rotate(scope, 1.5707f, 0, 0);
		animator.move(hammer, 0, -0.4f, -0.78f);
		animator.rotate(hammer, 1.5707f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, -0.75f, 0);
		animator.rotate(rightarm, 0, 0, 0.6283f);
		animator.move(leftarm, -0.05f, 0.2f, 0.14f);
		animator.rotate(leftarm, -0.4712f, 0, 0.3141f);
		animator.move(gun, 0, -0.4f, -0.78f);
		animator.rotate(gun, 1.5707f, 0, 0);
		animator.move(scope, 0, -0.4f, -0.78f);
		animator.rotate(scope, 1.5707f, 0, 0);
		animator.move(hammer, 0, -0.4f, -0.78f);
		animator.rotate(hammer, 1.5707f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, -0.75f, 0);
		animator.rotate(rightarm, 0, 0, 0.6283f);
		animator.move(leftarm, -0.05f, 0.17f, 0.14f);
		animator.rotate(leftarm, -0.4712f, 0, 0.3141f);
		animator.move(gun, 0, -0.4f, -0.78f);
		animator.rotate(gun, 1.5707f, 0, 0);
		animator.move(scope, 0, -0.4f, -0.78f);
		animator.rotate(scope, 1.5707f, 0, 0);
		animator.move(hammer, 0, -0.4f, -0.78f);
		animator.rotate(hammer, 1.5707f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, -0.75f, 0);
		animator.rotate(rightarm, 0, 0, 0.6283f);
		animator.move(leftarm, -0.05f, 0.2f, 0.14f);
		animator.rotate(leftarm, -0.4712f, 0, 0.3141f);
		animator.move(gun, 0, -0.4f, -0.78f);
		animator.rotate(gun, 1.5707f, 0, 0);
		animator.move(scope, 0, -0.4f, -0.78f);
		animator.rotate(scope, 1.5707f, 0, 0);
		animator.move(hammer, 0, -0.4f, -0.78f);
		animator.rotate(hammer, 1.5707f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(8);
		animator.endKeyframe();
		
		animator.setAnimation(KB);
		animator.startKeyframe(2);
		animator.move(leftarm, -0.26600012f, 0.0f, 0.2610002f);
		animator.move(gun, 0.7769936f, 0.0f, -0.04999997f);
		animator.rotate(gun, 0.0f, 0.0f, 1.4870303f);
		animator.move(hammer, 0.9809996f, 0.01f, -0.22000004f);
		animator.rotate(hammer, 0.0f, -1.551608f, 0);
		animator.rotate(rightarm, 0.56548905f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(leftarm, -0.26600012f, 0.0f, 0.2610002f);
		animator.move(gun, 0.7769936f, 0.0f, -0.04999997f);
		animator.rotate(gun, 0.0f, 0.0f, 1.4870303f);
		animator.move(hammer, 0.9809996f, 0.01f, -0.22000004f);
		animator.rotate(hammer, 0.0f, -1.551608f, 0);
		animator.rotate(rightarm, 0.56548905f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(8);
		animator.endKeyframe();
	}

	@Override
	public void initReloadAnimation(int currentgunbullets, int magbullets, boolean hasMag) {

		int totalGunPowder = ServerUtils.getTotalItemAmout(Minecraft.getInstance().player.getInventory(), Items.GUNPOWDER);

		if (gunbullets == 0 && magbullets != 0 && totalGunPowder >= 2) {
			setAnimation(R);
		}

	}

	@Override
	public void onAnimTick(float animTick) {
		if (getAnimation() == R) {
			if (animTick == 13) {
				ReloadHandler.setHammer(true);
				SoundHandler.playSoundOnServer(SoundRegistries.FLINTLOCK_HAMMER.get());
			} else if (animTick == 44) {
				if (ServerUtils.getTotalItemAmout(Minecraft.getInstance().player.getInventory(), Items.GUNPOWDER) >= 2
						&& ServerUtils.getIndexForItem(Minecraft.getInstance().player.getInventory(),
								ItemRegistries.MusketBullet.get()) != -1) {
					ServerUtils.removeItemInDifIndexes(Minecraft.getInstance().player.getInventory(), Items.GUNPOWDER, 2);
					ReloadHandler.removeItem(ammoindex, 1);
					ReloadHandler.growOneBullet(Util.getStack());
				} else {
					ReloadHandler.setHammer(false);
					ReloadHandler.setBullets(0);
					setAnimation(EMPTY);
				}
			}
		}else if(getAnimation() == KB){
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 4) {
				MeleeHelper.hit(5.5f);
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
	public void onAnimationEnd() {

	}

	@Override
	public void initExtraParts() {

	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		return new PirateRifleModModel(origin);
	}

	@Override
	public String getHammerPath() {
		return Paths.PRH;
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
		
	}

	@Override
	public Vector3f getMuzzleFlashPos(ItemStack stack) {
		// TODO Auto-generated method stub
		return new Vector3f();
	}

}
