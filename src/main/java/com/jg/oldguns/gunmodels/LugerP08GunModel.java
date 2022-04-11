package com.jg.oldguns.gunmodels;

import java.util.Random;

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
import com.jg.oldguns.client.modmodels.LugerP08ModModel;
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

public class LugerP08GunModel extends GunModel {

	public static final Animation SA = Animation.create(4);
	public static final Animation R1 = Animation.create(46);
	public static final Animation R2 = Animation.create(46);
	public static final Animation R3 = Animation.create(20);
	public static final Animation R4 = Animation.create(36);
	public static final Animation R5 = Animation.create(20);
	public static final Animation R6 = Animation.create(32);
	public static final Animation KB = Animation.create(16);
	public static final Animation IA = Animation.create(32);

	private GunModelPart backhammer;

	public LugerP08GunModel() {
		super(ItemRegistries.LugerP08.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(PoseStack matrix, ItemStack stack, MultiBufferSource itembuffer, BufferSource armbuffer, int light,
			int overlay, float partialTicks, float aimProgress, float sprintProgress, RecoilHandler handler) {
		if (!(ServerUtils.hasScope(stack) && ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID())
				.getAimingHandler().isFullAimingProgress())) {
			matrix.pushPose();

			handleAim(matrix, aimProgress);
			renderMuzzleFlash(matrix, armbuffer, partialTicks, stack);
			handleSprint(matrix, sprintProgress);

			float rec = handler.getRecoil(stack)*0.2f;
			float x = (Mth.cos(rec)-Mth.sin(rec))*0.1f;
			System.out.println("sd");
			float y = 0.0008f;
			float z = 0.0008f;
			matrix.translate(0, Mth.lerp(rec, 0, -x*0.25f), Mth.lerp(rec, 0, x*0.6f));//0.05f
			matrix.mulPose(Vector3f.XP.rotation(Mth.lerp(rec, 0, x)));
			matrix.mulPose(Vector3f.YP.rotation(Mth.lerp(rec, 0, Mth.randomBetween(new Random(), -y, y))));
			matrix.mulPose(Vector3f.ZP.rotation(Mth.lerp(rec, 0, Mth.randomBetween(new Random(), -z, z))));
			
			renderRightArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			if (both) {
				renderLeftArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			}
			armbuffer.endBatch();
			renderBarrel(matrix, stack, itembuffer, light, overlay);
			renderBody(matrix, stack, itembuffer, light, overlay);
			renderStock(matrix, stack, itembuffer, light, overlay);
			renderHammer(matrix, stack, itembuffer, light, overlay);
			renderBackHammer(matrix, stack, itembuffer, light, overlay);
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

	public void renderBackHammer(PoseStack matrix, ItemStack stack, MultiBufferSource buffer, int light,
			int overlay) {
		if (getHammerPath() != null) {
			// if(ModelHandler.INSTANCE.getSpecialModel(Paths.LUGERP08HB) != null) {
			if (ModelHandler.INSTANCE.getModel(Paths.LUGERP08HB) != null) {
				matrix.pushPose();
				translateRotateForPart(backhammer, matrix);
				// RenderUtil.renderModel(ModelHandler.INSTANCE.getSpecialModel(Paths.LUGERP08HB),
				// matrix, stack, buffer, light, overlay);
				RenderUtil.renderModel(ModelHandler.INSTANCE.getModel(Paths.LUGERP08HB), matrix, stack, buffer, light,
						overlay);
				matrix.popPose();
			}
		}
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		if (ServerUtils.getBullets(stack) == 0) {
			setAnimation(R5);
		} else {
			setAnimation(R6);
		}

	}

	@Override
	public void handleAim(PoseStack matrix, float aimProgress) {
		//debugAim(matrix, aimProgress);
		matrix.translate(Mth.lerp(aimProgress, 0, -0.192f), Mth.lerp(aimProgress, 0, 0.138f), Mth.lerp(aimProgress, 0, 0.25f));
		matrix.mulPose(new Quaternion(Mth.lerp(aimProgress, 0, -0.0139f),
				Mth.lerp(aimProgress, 0, -0.0069f), 0, false));
		//debugAim(matrix, aimProgress);
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -18))));

	}

	@Override
	public GunModelPart[] getParts() {
		// TODO Auto-generated method stub
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, backhammer, scope, aim };
	}

	@Override
	public boolean canRenderMag(ItemStack stack) {
		return ServerUtils.hasMag(stack);
	}

	@Override
	public boolean canShoot(ItemStack stack) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, -0.13f, 0.37f, 0.64f, -0.2967f, -0.5410f, -0.4014f);
		setPartDisplayTransform(leftarm, -0.31f, 0.86f, 0.8789f, 0.2967f, -0.5410f, -0.4014f);
		setPartDisplayTransform(gun, -0.34f, -0.87f, -0.77f, -1.5533f, 0, 0);
		setPartDisplayTransform(hammer, -0.34f, -0.87f, -0.77f, -1.5533f, 0, 0);
		setPartDisplayTransform(backhammer, -0.34f, -0.87f, -0.77f, -1.5533f, 0, 0);
		setPartDisplayTransform(scope, -0.346f, -0.883f, 0.173f, -1.5533f, 0, 0);
	}

	@Override
	public void setupAnimations() {
		// Shoot animation
		animator.setAnimation(SA);
		animator.setStaticKeyframe(1);
		animator.move(hammer, -0.04f, 0.26f, 0.36f);
		animator.rotate(hammer, -0.5585f, 0.0523f, 0);
		animator.move(backhammer, 0, 0.26f, -0.63f);
		animator.rotate(backhammer, 0.9721f, 0, 0);
		animator.endStationaryKeyframe();
		animator.setStaticKeyframe(1);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(backhammer, 0, 0, 0);
		animator.rotate(backhammer, 0, 0, 0);
		animator.endStationaryKeyframe();

		// No bullets on gun no bullets on mag
		animator.setAnimation(R1);
		animator.startKeyframe(4);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(gun, 0.05f, 0.07f, -0.03f);
		animator.rotate(gun, 0.2094f, 0, 0);
		animator.move(hammer, 0.05f, 0.07f, -0.03f);
		animator.rotate(hammer, 0.2094f, 0, 0);
		animator.move(backhammer, 0.05f, 0.07f, -0.03f);
		animator.rotate(backhammer, 0.2094f, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0.2268f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0, 0, 0);
		animator.rotate(gun, 0, 0, 0);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(backhammer, 0, 0, 0);
		animator.rotate(backhammer, 0, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.move(leftarm, 0, -0.2f, 0);
		animator.rotate(leftarm, 0.2967f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.move(leftarm, 0, -0.11f, 0);
		animator.rotate(leftarm, 0.2967f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);// Aqui
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.08f, 0.26f, 0.13f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);// Aqui
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.08f, 0.26f, 0.17f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);// Aqui
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.08f, 0.26f, 0.17f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		// 46
		// No bullets on room mag inside with bullets
		animator.setAnimation(R2);
		animator.startKeyframe(4);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(gun, 0.05f, 0.07f, -0.03f);
		animator.rotate(gun, 0.2094f, 0, 0);
		animator.move(hammer, 0.05f, 0.07f, -0.03f);
		animator.rotate(hammer, 0.2094f, 0, 0);
		animator.move(backhammer, 0.05f, 0.07f, -0.03f);
		animator.rotate(backhammer, 0.2094f, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0.2268f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0, 0, 0);
		animator.rotate(gun, 0, 0, 0);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(backhammer, 0, 0, 0);
		animator.rotate(backhammer, 0, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.move(leftarm, 0, -0.2f, 0);
		animator.rotate(leftarm, 0.2967f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);// 1
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.move(leftarm, 0, -0.11f, 0);
		animator.rotate(leftarm, 0.2967f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.34f, 0.24f, 0.03f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.03f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0.10f);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.13f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, -0.09f);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.03f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		// 20
		// No bullets on room bullets or no on mag
		animator.setAnimation(R3);
		animator.startKeyframe(4);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.move(leftarm, 0, -0.2f, 0);
		animator.rotate(leftarm, 0.2967f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.move(leftarm, 0, -0.11f, 0);
		animator.rotate(leftarm, 0.2967f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		// 36
		// No bullet on room no mag bullets on mag
		animator.setAnimation(R4);
		animator.startKeyframe(4);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(backhammer, 0, 0.21f, 0);
		animator.rotate(backhammer, 0, 0.2792f, 0);
		animator.move(leftarm, 0, -0.2f, 0);
		animator.rotate(leftarm, 0.2967f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);// Aqui
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.08f, 0.26f, 0.13f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);// Aqui
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.08f, 0.26f, 0.17f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);// Aqui
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.08f, 0.26f, 0.17f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		// 20
		// Do get out animation
		animator.setAnimation(R5);
		animator.startKeyframe(4);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(gun, 0.05f, 0.07f, -0.03f);
		animator.rotate(gun, 0.2094f, 0, 0);
		animator.move(hammer, 0.05f, 0.07f, -0.03f);
		animator.rotate(hammer, 0.2094f, 0, 0);
		animator.move(backhammer, 0.05f, 0.07f, -0.03f);
		animator.rotate(backhammer, 0.2094f, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0.2268f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0, 0, 0);
		animator.rotate(gun, 0, 0, 0);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(backhammer, 0, 0, 0);
		animator.rotate(backhammer, 0, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(6);
		animator.endKeyframe();

		// Do get out animation
		animator.setAnimation(R6);
		animator.startKeyframe(4);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(gun, 0.05f, 0.07f, -0.03f);
		animator.rotate(gun, 0.2094f, 0, 0);
		animator.move(hammer, 0.05f, 0.07f, -0.03f);
		animator.rotate(hammer, 0.2094f, 0, 0);
		animator.move(backhammer, 0.05f, 0.07f, -0.03f);
		animator.rotate(backhammer, 0.2094f, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0.2268f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0, 0, 0);
		animator.rotate(gun, 0, 0, 0);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(backhammer, 0, 0, 0);
		animator.rotate(backhammer, 0, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0f);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.13f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0f);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.03f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0f);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.13f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0.08f, 0);
		animator.rotate(rightarm, 0, -0.2792f, 0);
		animator.move(gun, 0.18f, -0.08f, 0);
		animator.rotate(gun, 0, -0.2792f, 0);
		animator.move(hammer, 0.18f, -0.08f, 0);
		animator.rotate(hammer, 0, -0.2792f, 0);
		animator.move(backhammer, 0.18f, -0.08f, 0f);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.03f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(KB);
		animator.startKeyframe(8);
		animator.move(gun, 0.11400005f, 0.28199992f, -0.027000003f);
		animator.rotate(gun, 0.70686185f, 0.0f, 6.519258E-9f);
		animator.move(hammer, 0.11400005f, 0.28199992f, -0.027000003f);
		animator.rotate(hammer, 0.70686185f, 0.0f, 6.519258E-9f);
		animator.move(backhammer, 0.11400005f, 0.28199992f, -0.027000003f);
		animator.rotate(backhammer, 0.70686185f, 0.0f, 6.519258E-9f);
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
		animator.move(backhammer, 0.017f, 0.0f, 0.0f);
		animator.move(scope, 6.0574636f, 14.984234f, -1.4346615f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, 0.1466076f, 0.0f, 0.0f);
		animator.rotate(hammer, 0.1466076f, 0.0f, 0.0f);
		animator.rotate(backhammer, 0.1466076f, 0.0f, 0.0f);
		animator.rotate(scope, 37.55953f, 0.0f, 3.4640482E-7f);
		animator.rotate(rightarm, -9.313226E-10f, 0.0f, 0.08203044f);
		animator.endKeyframe();
		animator.resetKeyframe(2);
		animator.endKeyframe();

		animator.setAnimation(IA);
		animator.startKeyframe(4);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.21000002f, 0.07999999f, 0.0f);
		animator.move(gun, 0.0f, 0.45999983f, 0.0f);
		animator.move(backhammer, 0.0f, 0.45999983f, 0.0f);
		animator.move(hammer, 0.0f, 0.45999983f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.43633226f, 0.0f);
		animator.rotate(gun, 0.0f, 0.43633223f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.43633223f, 0.0f);
		animator.rotate(backhammer, 0.0f, 0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.21000002f, 0.07999999f, 0.0f);
		animator.move(gun, 0.0f, 0.45999983f, 0.0f);
		animator.move(hammer, 0.0f, 0.45999983f, 0.0f);
		animator.move(backhammer, 0.0f, 0.45999983f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.43633226f, 0.0f);
		animator.rotate(gun, 0.0f, 0.43633223f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.43633223f, 0.0f);
		animator.rotate(backhammer, 0.0f, 0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(hammer, 0.29f, -0.20000003f, 0.0f);
		animator.move(backhammer, 0.29f, -0.20000003f, 0.0f);
		animator.move(gun, 0.29f, -0.20000003f, 0.0f);
		animator.rotate(leftarm, -0.80285174f, -0.12217303f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(gun, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(backhammer, 0.0f, -0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.29f, -0.20000003f, 0.0f);
		animator.move(hammer, 0.29f, -0.20000003f, 0.0f);
		animator.move(backhammer, 0.29f, -0.20000003f, 0.0f);
		animator.rotate(leftarm, -0.80285174f, -0.12217303f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(gun, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(backhammer, 0.0f, -0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
	}

	@Override
	public void initReloadAnimation(int currentgunbullets, int magbullets, boolean hasMag) {
		if (magbullets > 0 && gunbullets == 0 && !hasMag) {
			setAnimation(R4);
			System.out.println("R4");
		} else if (magbullets > 0 && gunbullets == 0 && hasMag) {
			setAnimation(R2);
			System.out.println("R2");
		} else if (magbullets == 0 && hasMag) {
			setAnimation(R1);
			System.out.println("R1");
		} else if (magbullets == 0) {
			setAnimation(R3);
			System.out.println("R3");
		}

	}

	@Override
	public void onAnimTick(float tick) {
		if (getAnimation() == R1) {
			if (tick == 12) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get(), SoundSource.NEUTRAL);
			} else if (tick == 24) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get(), SoundSource.NEUTRAL);
			} else if (tick == 40) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundSource.NEUTRAL);
				ReloadHandler.restoreMag(getMPath(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.removeItem(ammoindex, 1);
				ReloadHandler.setBullets(0);
			}
		} else if (getAnimation() == R2) {
			if (tick == 14) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get(), SoundSource.NEUTRAL);
			} else if (tick == 24) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get(), SoundSource.NEUTRAL);
			} else if (tick == 37) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundSource.NEUTRAL);
			} else if (tick == 41) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundSource.NEUTRAL);
				ReloadHandler.restoreMag(getMPath(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			}
		}
		if (getAnimation() == R3) {
			if (tick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get(), SoundSource.NEUTRAL);
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R4) {
			if (tick == 10) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get(), SoundSource.NEUTRAL);
			} else if (tick == 27) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundSource.NEUTRAL);
			} else if (tick == 30) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundSource.NEUTRAL);
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true,
						getMagStack().getOrCreateTag().getString(Constants.MAGBULLETPATH), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R5) {
			if (tick == 14) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get(), SoundSource.NEUTRAL);
				ReloadHandler.restoreMag(getMPath(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setBullets(0);
				ReloadHandler.setMag(this, 0, false, "", Constants.EMPTY);
			}
		} else if (getAnimation() == R6) {
			if (tick == 14) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get(), SoundSource.NEUTRAL);
			} else if (tick == 26) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundSource.NEUTRAL);
				ReloadHandler.restoreMag(getMPath(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setBullets(0);
				ReloadHandler.setMag(this, 0, false, "", Constants.EMPTY);
			}
		} else if(getAnimation() == KB) {
			if(animTick == 3) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(tick == 10) {
				MeleeHelper.hit(4.5f);
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
		backhammer = new GunModelPart("backhammer");
		initExtraParts(backhammer);
	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		// TODO Auto-generated method stub
		return new LugerP08ModModel(origin);
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.LUGERP08HF;
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
		// TODO Auto-generated method stub
		return new Vector3f(0.18f, -0.2f, -1.38f);
	}

}
