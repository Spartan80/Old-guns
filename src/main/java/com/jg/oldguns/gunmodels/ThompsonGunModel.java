package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.client.handlers.RecoilHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.modmodels.ThompsonModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;
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

public class ThompsonGunModel extends GunModel {

	public static final Animation SA = Animation.create(24);
	public static final Animation R1 = Animation.create(28);
	public static final Animation R2 = Animation.create(48);
	public static final Animation R3 = Animation.create(24);
	public static final Animation R4 = Animation.create(40);
	public static final Animation R5 = Animation.create(40);

	public ThompsonGunModel() {
		super(ItemRegistries.Thompson.get());
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
			matrix.mulPose(new Quaternion(0,
					0, this.gunitem.handleRHorRecoil(handler.getRecoil(stack), stack), false));
			matrix.translate(0.09f, -0.12f, -0.01f);
			matrix.mulPose(new Quaternion(0, -0.0698f, 0, false));
			renderRightArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			if (both) {
				renderLeftArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			}
			armbuffer.endBatch();
			renderBarrel(matrix, stack, itembuffer, light, overlay);
			renderBody(matrix, stack, itembuffer, light, overlay);
			renderStock(matrix, stack, itembuffer, light, overlay);
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
	public boolean canRenderMag(ItemStack arg0) {
		return ServerUtils.hasMag(arg0);
	}

	@Override
	public boolean canShoot(ItemStack arg0) {
		return true;
	}

	@Override
	public void doGetOutMag(ItemStack arg0) {
		setAnimation(SA);

	}

	@Override
	public String getHammerPath() {
		return Paths.THOMPSONH;
	}

	@Override
	public IBakedModel getModifiedModel(IBakedModel arg0) {
		// TODO Auto-generated method stub
		return new ThompsonModModel(arg0);
	}

	@Override
	public GunModelPart[] getParts() {
		// TODO Auto-generated method stub
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, scope, aim };
	}

	@Override
	public void handleAim(MatrixStack matrix, float p) {
		matrix.translate(MathHelper.lerp(p, 0, -0.352f), MathHelper.lerp(p, 0, 0.14f), MathHelper.lerp(p, 0, 0.165f));
		matrix.mulPose(new Quaternion(MathHelper.lerp(p, 0, -0.03490f), MathHelper.lerp(p, 0, -0.0383f), 0, false));
	}

	@Override
	public void handleSprint(MatrixStack matrix, float p) {
		// ReloadHandler.setMag(false, getMagItem().getMagType(), getMBPath());
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(MathHelper.rotLerp(p, 0, -24))));

	}

	@Override
	public void initExtraParts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initReloadAnimation(int gun, int mag) {
		ClientHandler.debug = false;
		if (mag > 0 && ServerUtils.hasMag(Util.getStack())) {
			setAnimation(R2);
			System.out.println("R2");
		} else if (mag == 0 && ServerUtils.hasMag(Util.getStack())) {
			setAnimation(R1);
			System.out.println("R1");
		} else if (!ServerUtils.hasMag(Util.getStack()) && mag == 0) {
			setAnimation(R3);
			System.out.println("R3");
		} else if (!ServerUtils.hasMag(Util.getStack()) && mag > 0) {
			setAnimation(R4);
			System.out.println("R4");
		}

	}

	@Override
	public void onAnimTick(float animTick) {
		if (getAnimation() == R1) {
			if (animTick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGOUT.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 12) {
				ReloadHandler.restoreMag(getGunItem().getMagPath(Util.getStack()).getRegistryName().toString(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setMag(true, getMagItem().getMagType(), getMBPath());
			} else if (animTick == 22) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGIN.get(), SoundCategory.NEUTRAL);
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R2) {
			if (animTick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGOUT.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 22) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGIN.get(), SoundCategory.NEUTRAL);
			} else if (animTick == 33) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONCOCKING.get(), SoundCategory.NEUTRAL);
				ReloadHandler.restoreMag(getGunItem().getMagPath(Util.getStack()).getRegistryName().toString(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R3) {
			if (animTick == 16) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGIN.get());
				ReloadHandler.setMag(true, getMagItem().getMagType(), getMBPath());
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R4) {
			if (animTick == 8) {
				ReloadHandler.setMag(true, getMagItem().getMagType(), getMBPath());
			} else if (animTick == 16) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGIN.get());
			} else if (animTick == 28) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONCOCKING.get());
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == SA) {
			if (animTick == 9) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGOUT.get());
			} else if (animTick == 13) {
				ReloadHandler.restoreMag(getGunItem().getMagPath(Util.getStack()).getRegistryName().toString(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setMag(false, 0, "");
				ReloadHandler.setBullets(0);
			}
		}

	}

	@Override
	public void onAnimationEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onShoot(ItemStack arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupAnimations() {

		animator.setAnimation(R1);// 28
		animator.startKeyframe(8);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.05f, 0.14f, 0f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.05f, 0.14f, 0f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(R2);// 48
		animator.startKeyframe(8);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.05f, 0.14f, 0f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.05f, 0.14f, 0f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.22f, 0.13f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(hammer, 0.45f, -0.15f, 0f);
		animator.rotate(hammer, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.17f, 0.28f, 0.15f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(hammer, 0.45f, -0.15f, 0f);
		animator.rotate(hammer, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.17f, 0.28f, 0.19f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(hammer, 0.45f, -0.15f, 0f);
		animator.rotate(hammer, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(8);
		animator.endKeyframe();

		animator.setAnimation(R3);// 28
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.05f, 0.14f, 0f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(R4);// 48
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.05f, 0.14f, 0f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.22f, 0.13f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(hammer, 0.45f, -0.15f, 0f);
		animator.rotate(hammer, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.17f, 0.28f, 0.15f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(hammer, 0.45f, -0.15f, 0f);
		animator.rotate(hammer, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.17f, 0.28f, 0.19f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(hammer, 0.45f, -0.15f, 0f);
		animator.rotate(hammer, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.17f, 0.28f, 0.15f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(hammer, 0.45f, -0.15f, 0f);
		animator.rotate(hammer, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.17f, 0.28f, 0.15f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(hammer, 0.45f, -0.15f, 0f);
		animator.rotate(hammer, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(SA);// 28
		animator.startKeyframe(8);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.05f, 0.14f, 0f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(hammer, -0.05f, 0.14f, 0f);
		animator.rotate(hammer, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		/*
		 * animator.startKeyframe(4); animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		 * animator.rotate(rightarm, 0, 0.2617f, 0); animator.move(leftarm, -0.01f,
		 * -0.01f, 0.08f); animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		 * animator.move(gun, -0.05f, 0.14f, 0f); animator.rotate(gun, 0, 0.2617f, 0);
		 * animator.move(mag, -0.05f, 0.14f, 0f); animator.rotate(mag, 0, 0.2617f, 0);
		 * animator.endKeyframe();
		 */
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(R5);// 28
		animator.startKeyframe(8);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(mag, -0.05f, 0.14f, 0f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		animator.rotate(rightarm, 0, 0.2617f, 0);
		animator.move(leftarm, -0.01f, -0.01f, 0.08f);
		animator.rotate(leftarm, 0.1221f, -0.5585f, -1.5533f);
		animator.move(gun, -0.05f, 0.14f, 0f);
		animator.rotate(gun, 0, 0.2617f, 0);
		animator.move(mag, -0.37f, -0.62f, -0.18f);
		animator.rotate(mag, 0, 0.2617f, 0);
		animator.endKeyframe();
		/*
		 * animator.startKeyframe(4); animator.move(rightarm, 0.05f, -0.06f, -0.02f);
		 * animator.rotate(rightarm, 0, 0.2617f, 0); animator.move(leftarm, -0.01f,
		 * -0.01f, 0.08f); animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		 * animator.move(gun, -0.05f, 0.14f, 0f); animator.rotate(gun, 0, 0.2617f, 0);
		 * animator.move(mag, -0.05f, 0.14f, 0f); animator.rotate(mag, 0, 0.2617f, 0);
		 * animator.endKeyframe();
		 */
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.22f, 0.13f, 0.08f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.17f, 0.28f, 0.15f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		animator.rotate(rightarm, 0, -0.5235f, 0);
		animator.move(leftarm, -0.17f, 0.28f, 0.19f);
		animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		animator.move(gun, 0.45f, -0.15f, 0f);
		animator.rotate(gun, 0, -0.5235f, 0);
		animator.move(mag, 0.45f, -0.15f, 0f);
		animator.rotate(mag, 0, -0.5235f, 0);
		animator.endKeyframe();
		/*
		 * animator.startKeyframe(4); animator.move(rightarm, 0.05f, 0.12f, -0.02f);
		 * animator.rotate(rightarm, 0, -0.5235f, 0); animator.move(leftarm, -0.17f,
		 * 0.28f, 0.15f); animator.rotate(leftarm, 0.1570f, -0.5235f, 0);
		 * animator.move(gun, 0.45f, -0.15f, 0f); animator.rotate(gun, 0, -0.5235f, 0);
		 * animator.move(mag, 0.45f, -0.15f, 0f); animator.rotate(mag, 0, -0.5235f, 0);
		 * animator.endKeyframe(); animator.startKeyframe(4); animator.move(rightarm,
		 * 0.05f, 0.12f, -0.02f); animator.rotate(rightarm, 0, -0.5235f, 0);
		 * animator.move(leftarm, -0.17f, 0.28f, 0.15f); animator.rotate(leftarm,
		 * 0.1570f, -0.5235f, 0); animator.move(gun, 0.45f, -0.15f, 0f);
		 * animator.rotate(gun, 0, -0.5235f, 0); animator.move(mag, 0.45f, -0.15f, 0f);
		 * animator.rotate(mag, 0, -0.5235f, 0); animator.endKeyframe();
		 */
		animator.resetKeyframe(4);
		animator.endKeyframe();
	}

	@Override
	public void setupParts() {
		// debugGun();
		/*
		 * rightarm.transform.setOffset(0.0400f, 0.3710f, 0.7298f);
		 * rightarm.transform.setRotation(-0.5060f, -0.5410f, -0.4009f);
		 * leftarm.transform.setOffset(-0.3397f, 0.8498f, 0.6989f);
		 * leftarm.transform.setRotation(0.3666f, 0.8202f, 0.2619f);
		 * gun.transform.setOffset(-0.24f, -0.84f, -0.08f);
		 * gun.transform.setRotation(-1.5533f, 0, 0.1047f);
		 * hammer.transform.setOffset(-0.24f, -0.84f, -0.08f);
		 * hammer.transform.setRotation(-1.5533f, 0, 0.1047f);
		 */

		//leftarm.transform.setDisplaytoNonD();
		//leftarm.transform.resetDisplay();
		//gun.transform.resetDisplay();
		//rightarm.transform.resetDisplay();
		setPartDisplayTransform(rightarm, 0.03f, 0.441f, 0.7098f, -0.5409f, -0.4886f, -0.4009f);
		if (ClientEventHandler.getPlayer() != null) {
			if(ServerUtils.getMagType(Util.getStack()) == 0 || !ServerUtils.hasMag(Util.getStack())) {
				setPartDisplayTransform(leftarm, -0.3397f, 0.8498f, 0.7089f, 0.3666f, 0.8202f, 0.2619f);
			}else {
				setPartDisplayTransform(leftarm, -0.3696f, 0.7598f, 0.8688f, 0.3666f, 0.8202f, 0.2619f);
			}
		}
		setPartDisplayTransform(gun, -0.22f, -0.83f, -0.07f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(scope, -0.235f, -0.793f, 0.3709f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(hammer, -0.24f, -0.84f, -0.08f, -1.5533f, 0, 0.1047f);
		setPartDisplayTransform(mag, -0.22f, -0.84f, -0.07f, -1.5533f, 0, 0.1047f);
		
	}

	@Override
	public boolean hasMultipleParts() {
		// TODO Auto-generated method stub
		return true;
	}

}
