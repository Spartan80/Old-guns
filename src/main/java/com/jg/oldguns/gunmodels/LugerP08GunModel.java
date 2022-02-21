package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.RecoilHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.modmodels.LugerP08ModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.ServerUtils;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class LugerP08GunModel extends GunModel {

	public static final Animation SA = Animation.create(4);
	public static final Animation R1 = Animation.create(46);
	public static final Animation R2 = Animation.create(46);
	public static final Animation R3 = Animation.create(20);
	public static final Animation R4 = Animation.create(36);
	public static final Animation R5 = Animation.create(20);
	public static final Animation R6 = Animation.create(32);

	private GunModelPart backhammer;

	public LugerP08GunModel() {
		super(ItemRegistries.LugerP08.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(MatrixStack matrix, ItemStack stack, IRenderTypeBuffer itembuffer, Impl armbuffer, int light,
			int overlay, float aimProgress, float sprintProgress, RecoilHandler handler) {
		if (!(ServerUtils.hasScope(stack) && ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID())
				.getAimingHandler().isFullAimingProgress())) {
			matrix.pushPose();

			handleAim(matrix, aimProgress);
			handleSprint(matrix, sprintProgress);

			matrix.translate(0, 0, this.gunitem.handleZRecoil(handler.getRecoil(stack), stack));
			matrix.mulPose(new Quaternion(this.gunitem.handleRVertRecoil(handler.getRecoil(stack), stack),
					this.gunitem.handleRHorRecoil(handler.getRecoil(stack), stack), 0.0F, false));
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

	public void renderBackHammer(MatrixStack matrix, ItemStack stack, IRenderTypeBuffer buffer, int light,
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
		// TODO Auto-generated method stub

	}

	@Override
	public void handleAim(MatrixStack matrix, float aimProgress) {
		//debugAim(matrix, aimProgress);
		matrix.translate(MathHelper.lerp(aimProgress, 0, -0.192f), MathHelper.lerp(aimProgress, 0, 0.138f), MathHelper.lerp(aimProgress, 0, 0.25f));
		matrix.mulPose(new Quaternion(MathHelper.lerp(aimProgress, 0, -0.0139f),
				MathHelper.lerp(aimProgress, 0, -0.0069f), 0, false));
		//debugAim(matrix, aimProgress);
	}

	@Override
	public void handleSprint(MatrixStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(MathHelper.rotLerp(p, 0, -18))));

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
		/*
		 * for(GunModelPart part : parts) { part.transform.resetDisplay(); }
		 */
		/*
		 * rightarm.transform.setDisplaytoNonD(); leftarm.transform.setDisplaytoNonD();
		 * gun.transform.setDisplaytoNonD(); hammer.transform.setDisplaytoNonD();
		 * backhammer.transform.setDisplaytoNonD();
		 */

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
		animator.move(backhammer, 0.18f, -0.08f, 0);
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
		animator.move(backhammer, 0.18f, -0.08f, 0.10f);
		animator.rotate(backhammer, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.03f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

	}

	@Override
	public void initReloadAnimation(int currentgunbullets, int magbullets) {
		setAnimation(R3);

	}

	@Override
	public void onAnimTick(float animTick) {
		// TODO Auto-generated method stub

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

	}

	@Override
	public IBakedModel getModifiedModel(IBakedModel origin) {
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

}
