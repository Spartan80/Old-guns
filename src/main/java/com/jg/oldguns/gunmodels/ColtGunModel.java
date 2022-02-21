package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.RecoilHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.modmodels.ColtModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class ColtGunModel extends GunModel {

	public static final Animation SA = Animation.create(4);
	public static final Animation R1 = Animation.create(46);
	public static final Animation R2 = Animation.create(46);
	public static final Animation R3 = Animation.create(20);
	public static final Animation R4 = Animation.create(36);
	public static final Animation R5 = Animation.create(20);
	public static final Animation R6 = Animation.create(32);
	private GunModelPart slider;

	public ColtGunModel() {
		super(ItemRegistries.Colt1911.get());

	}

	@Override
	public void initExtraParts() {
		slider = new GunModelPart("slider");

	}

	@Override
	public void render(MatrixStack matrix, ItemStack stack, IRenderTypeBuffer itembuffer, Impl armbuffer, int light,
			int overlay, float aimProgress, float sprintProgress, RecoilHandler handler) {
		if (!(ServerUtils.hasScope(stack) && ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID())
				.getAimingHandler().isFullAimingProgress())) {
			matrix.pushPose();

			handleAim(matrix, aimProgress);
			handleSprint(matrix, sprintProgress);

			matrix.translate(0, 0, handler.getRecoil(stack) * 0.1f * gunitem.getZRecoil(stack));
			matrix.mulPose(new Quaternion(handler.getRecoil(stack) * 0.1f * getGunItem().getRVertRecoil(stack),
					handler.getRecoil(stack) * 0.1f * getGunItem().getRHorRecoil(stack), 0, false));
			renderRightArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			if (both) {
				renderLeftArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			}
			armbuffer.endBatch();
			renderBarrel(matrix, stack, itembuffer, light, overlay);
			renderBody(matrix, stack, itembuffer, light, overlay);
			renderStock(matrix, stack, itembuffer, light, overlay);
			renderHammer(matrix, stack, itembuffer, light, overlay);
			renderSlider(matrix, stack, itembuffer, light, overlay);
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

	public void renderSlider(MatrixStack matrix, ItemStack stack, IRenderTypeBuffer buffer, int light, int overlay) {
		// if (ModelHandler.INSTANCE.getSpecialModel(Paths.COLTSLIDER) != null) {
		if (ModelHandler.INSTANCE.getModel(Paths.COLTSLIDER) != null) {
			matrix.pushPose();
			translateRotateForPart(slider, matrix);
			// RenderUtil.renderModel(ModelHandler.INSTANCE.getSpecialModel(Paths.COLTSLIDER),
			// matrix, stack, buffer,
			RenderUtil.renderModel(ModelHandler.INSTANCE.getModel(Paths.COLTSLIDER), matrix, stack, buffer, light,
					overlay);
			matrix.popPose();
		}
	}

	@Override
	public boolean canRenderMag(ItemStack arg0) {
		return false;
	}

	@Override
	public boolean canShoot(ItemStack arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void doGetOutMag(ItemStack arg0) {
		if (ServerUtils.getBullets(arg0) == 0) {
			setAnimation(R5);
		} else {
			setAnimation(R6);
		}
	}

	@Override
	public String getHammerPath() {
		return Paths.COLTHAM;
	}

	@Override
	public IBakedModel getModifiedModel(IBakedModel arg0) {
		return new ColtModModel(arg0);
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, slider, scope};
	}

	@Override
	public void handleAim(MatrixStack matrix, float p) {
		matrix.translate(MathHelper.lerp(p, 0, -0.19f), MathHelper.lerp(p, 0, 0.14f), MathHelper.lerp(p, 0, 0.25f));
		matrix.mulPose(new Quaternion(MathHelper.lerp(p, 0, -0.0349f), 0, 0, false));

	}

	@Override
	public void handleSprint(MatrixStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(MathHelper.rotLerp(p, 0, -18))));
	}

	@Override
	public void initReloadAnimation(int gunbullets, int magbullets) {
		if (magbullets > 0 && gunbullets == 0 && !ServerUtils.hasMag(Util.getStack())) {
			setAnimation(R4);
			System.out.println("R4");
		} else if (magbullets > 0 && gunbullets == 0 && ServerUtils.hasMag(Util.getStack())) {
			setAnimation(R2);
			System.out.println("R2");
		} else if (magbullets == 0 && ServerUtils.hasMag(Util.getStack())) {
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
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get(), SoundCategory.NEUTRAL);
			} else if (tick == 24) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get(), SoundCategory.NEUTRAL);
			} else if (tick == 40) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundCategory.NEUTRAL);
				ReloadHandler.restoreMag(getGunItem().getMagPath(Util.getStack()).getRegistryName().toString(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.removeItem(ammoindex, 1);
				ReloadHandler.setBullets(0);
			}
		} else if (getAnimation() == R2) {
			if (tick == 14) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get(), SoundCategory.NEUTRAL);
			} else if (tick == 24) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get(), SoundCategory.NEUTRAL);
			} else if (tick == 37) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundCategory.NEUTRAL);
			} else if (tick == 41) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundCategory.NEUTRAL);
				ReloadHandler.restoreMag(getGunItem().getMagPath(Util.getStack()).getRegistryName().toString(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.setMag(true, getMagItem().getMagType(), getMBPath());
				ReloadHandler.removeItem(ammoindex, 1);
			}
		}
		if (getAnimation() == R3) {
			if (tick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get(), SoundCategory.NEUTRAL);
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.setMag(true, getMagItem().getMagType(), getMBPath());
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R4) {
			if (tick == 10) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get(), SoundCategory.NEUTRAL);
			} else if (tick == 27) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundCategory.NEUTRAL);
			} else if (tick == 30) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundCategory.NEUTRAL);
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.setMag(true, getMagItem().getMagType(),
						getMagStack().getOrCreateTag().getString(Constants.MAGBULLETPATH));
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R5) {
			if (tick == 14) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get(), SoundCategory.NEUTRAL);
				ReloadHandler.restoreMag(getGunItem().getMagPath(Util.getStack()).getRegistryName().toString(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setBullets(0);
				ReloadHandler.setMag(false, -1, "");
			}
		} else if (getAnimation() == R6) {
			if (tick == 14) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get(), SoundCategory.NEUTRAL);
			} else if (tick == 26) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get(), SoundCategory.NEUTRAL);
				ReloadHandler.restoreMag(getGunItem().getMagPath(Util.getStack()).getRegistryName().toString(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setBullets(0);
				ReloadHandler.setMag(false, -1, "");
			}
		}

	}

	@Override
	public void onShoot(ItemStack arg0) {
		setAnimation(SA);

	}

	@Override
	public void setupAnimations() {

		// Shoot animation
		animator.setAnimation(SA);
		animator.startKeyframe(2);
		animator.move(hammer, 0, 0.09f, -0.4076f);
		animator.rotate(hammer, 0.6442f, 0, 0);
		animator.move(slider, 0, 0, 0.09f);
		animator.endKeyframe();
		animator.resetKeyframe(2);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(slider, 0, 0, 0);
		animator.endKeyframe();

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
		animator.move(slider, 0.05f, 0.07f, -0.03f);
		animator.rotate(slider, 0.2094f, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0.2268f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0, 0, 0);
		animator.rotate(gun, 0, 0, 0);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(slider, 0, 0, 0);
		animator.rotate(slider, 0, 0, 0);
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
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
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
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0.10f);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0.10f);
		animator.rotate(slider, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.13f);
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
		animator.move(slider, 0.05f, 0.07f, -0.03f);
		animator.rotate(slider, 0.2094f, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0.2268f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0, 0, 0);
		animator.rotate(gun, 0, 0, 0);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(slider, 0, 0, 0);
		animator.rotate(slider, 0, 0, 0);
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
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
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
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0.10f);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, -0.09f);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
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
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
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
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.17f, -0.01f, 0);
		animator.rotate(rightarm, 0, 0.2792f, 0);
		animator.move(gun, 0, 0.21f, 0);
		animator.rotate(gun, 0, 0.2792f, 0);
		animator.move(hammer, 0, 0.21f, 0);
		animator.rotate(hammer, 0, 0.2792f, 0);
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
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
		animator.move(slider, 0, 0.21f, 0);
		animator.rotate(slider, 0, 0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0.10f);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, -0.09f);
		animator.rotate(slider, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.03f);
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
		animator.move(slider, 0.05f, 0.07f, -0.03f);
		animator.rotate(slider, 0.2094f, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0.2268f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0, 0, 0);
		animator.rotate(gun, 0, 0, 0);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(slider, 0, 0, 0);
		animator.rotate(slider, 0, 0, 0);
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
		animator.move(slider, 0.05f, 0.07f, -0.03f);
		animator.rotate(slider, 0.2094f, 0, 0);
		animator.rotate(leftarm, 0, 0, -0.9948f);
		animator.rotate(rightarm, 0, 0, 0.2268f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0, 0, 0);
		animator.rotate(gun, 0, 0, 0);
		animator.move(hammer, 0, 0, 0);
		animator.rotate(hammer, 0, 0, 0);
		animator.move(slider, 0, 0, 0);
		animator.rotate(slider, 0, 0, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0.10f);
		animator.rotate(slider, 0, -0.2792f, 0);
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
		animator.move(slider, 0.18f, -0.08f, 0.10f);
		animator.rotate(slider, 0, -0.2792f, 0);
		animator.move(leftarm, -0.15f, 0.24f, 0.03f);
		animator.rotate(leftarm, -0.0349f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
	}

	@Override
	public void setupParts() {
		/*
		 * gun.transform.setOffset( mag.transform.offsetX, mag.transform.offsetY,
		 * mag.transform.offsetZ); hammer.transform.setOffset( mag.transform.offsetX,
		 * mag.transform.offsetY, mag.transform.offsetZ); slider.transform.setOffset(
		 * mag.transform.offsetX, mag.transform.offsetY, mag.transform.offsetZ);
		 * gun.transform.setRotation( mag.transform.rotationX, mag.transform.rotationY,
		 * mag.transform.rotationZ); hammer.transform.setRotation(
		 * mag.transform.rotationX, mag.transform.rotationY, mag.transform.rotationZ);
		 * slider.transform.setRotation( mag.transform.rotationX,
		 * mag.transform.rotationY, mag.transform.rotationZ);
		 */
		setPartDisplayTransform(rightarm, -0.13f, 0.37f, 0.64f, -0.2967f, -0.5410f, -0.4014f);
		setPartDisplayTransform(leftarm, -0.31f, 0.86f, 0.8789f, 0.2967f, -0.5410f, -0.4014f);
		setPartDisplayTransform(gun, -0.34f, -0.89f, -0.77f, -1.5533f, 0, 0);
		if (ClientEventHandler.getPlayer() != null) {
			if (Util.canWork(ClientEventHandler.getPlayer())) {
				if (ServerUtils.getBullets(ClientEventHandler.getPlayer().getMainHandItem()) != 0
						|| ServerUtils.hasBulletOnRoom(Util.getStack())) {
					setPartDisplayTransform(hammer, -0.34f, -0.89f, -0.77f, -1.5533f, 0, 0);
					setPartDisplayTransform(slider, -0.34f, -0.89f, -0.77f, -1.5533f, 0, 0);
				} else {
					setPartDisplayTransform(hammer, -0.34f, -0.89f, -0.77f, -1.5533f, 0, 0);
					setPartDisplayTransform(slider, -0.34f, -0.89f, -0.69f, -1.5533f, 0, 0);
				}
			}
		}

	}

	@Override
	public void onAnimationEnd() {

	}

	@Override
	public boolean hasMultipleParts() {
		// TODO Auto-generated method stub
		return true;
	}

}
