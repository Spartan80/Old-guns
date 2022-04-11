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
import com.jg.oldguns.client.modmodels.ThompsonModModel;
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

public class ThompsonGunModel extends GunModel {

	public static final Animation SA = Animation.create(24);
	public static final Animation R1 = Animation.create(28);
	public static final Animation R2 = Animation.create(48);
	public static final Animation R3 = Animation.create(24);
	public static final Animation R4 = Animation.create(40);
	public static final Animation R5 = Animation.create(40);
	public static final Animation KB = Animation.create(13);
	public static final Animation IA = Animation.create(32);

	public ThompsonGunModel() {
		super(ItemRegistries.Thompson.get());
	}

	@Override
	public void render(PoseStack matrix, ItemStack stack, MultiBufferSource itembuffer, BufferSource armbuffer, int light,
			int overlay, float partialTicks, float aimProgress, float sprintProgress, RecoilHandler handler) {
		if (!(ServerUtils.hasScope(stack) && ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID())
				.getAimingHandler().isFullAimingProgress())) {
			matrix.pushPose();

			handleAim(matrix, aimProgress);
			
			/*RenderUtil.renderMuzzleFlash(matrix, 
					armbuffer, partialTicks, 
					getPart().transform.offsetX, 
				    getPart().transform.offsetY, 
				    getPart().transform.offsetZ);
			*/
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
	public WrapperModel getModifiedModel(BakedModel arg0) {
		// TODO Auto-generated method stub
		return new ThompsonModModel(arg0);
	}

	@Override
	public GunModelPart[] getParts() {
		// TODO Auto-generated method stub
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, scope, aim };
	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.352f), Mth.lerp(p, 0, 0.14f), Mth.lerp(p, 0, 0.165f));
		matrix.mulPose(new Quaternion(Mth.lerp(p, 0, -0.03490f), Mth.lerp(p, 0, -0.0383f), 0, false));
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		// ReloadHandler.setMag(false, getMBPath());
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -24))));

	}

	@Override
	public void initExtraParts() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initReloadAnimation(int gun, int mag, boolean hasMag) {
		if (mag > 0 && hasMag) {
			setAnimation(R2);
			System.out.println("R2");
		} else if (mag == 0 && hasMag) {
			setAnimation(R1);
			System.out.println("R1");
		} else if (!hasMag && mag == 0) {
			setAnimation(R3);
			System.out.println("R3");
		} else if (!hasMag && mag > 0) {
			setAnimation(R4);
			System.out.println("R4");
		}

	}

	@Override
	public void onAnimTick(float animTick) {
		if (getAnimation() == R1) {
			if (animTick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGOUT.get(), SoundSource.NEUTRAL);
			} else if (animTick == 12) {
				ReloadHandler.restoreMag(getMPath(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
			} else if (animTick == 22) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGIN.get(), SoundSource.NEUTRAL);
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R2) {
			if (animTick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGOUT.get(), SoundSource.NEUTRAL);
			} else if (animTick == 22) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGIN.get(), SoundSource.NEUTRAL);
			} else if (animTick == 33) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONCOCKING.get(), SoundSource.NEUTRAL);
				ReloadHandler.restoreMag(getMPath(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.setBullets(magbullets);
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R3) {
			if(animTick == 8) {
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
			}else if (animTick == 16) {
				SoundHandler.playSoundOnServer(SoundRegistries.THOMPSONMAGIN.get());
				ReloadHandler.removeItem(ammoindex, 1);
			}
		} else if (getAnimation() == R4) {
			if (animTick == 8) {
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
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
				ReloadHandler.restoreMag(getMPath(),
						ServerUtils.getBullets(Util.getStack()));
				ReloadHandler.setMag(this, 0, false, "", Constants.EMPTY);
				ReloadHandler.setBullets(0);
			}
		} else if(getAnimation() == KB) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 3) {
				MeleeHelper.hit(5f);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, 0.45f, -0.15f, 0f);
		animator.rotate(scope, 0, -0.5235f, 0);
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
		animator.move(scope, 0.45f, -0.15f, 0f);
		animator.rotate(scope, 0, -0.5235f, 0);
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
		animator.move(scope, 0.45f, -0.15f, 0f);
		animator.rotate(scope, 0, -0.5235f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, -0.05f, 0.14f, 0f);
		animator.rotate(scope, 0, 0.2617f, 0);
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
		animator.move(scope, 0.45f, -0.15f, 0f);
		animator.rotate(scope, 0, -0.5235f, 0);
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
		animator.move(scope, 0.45f, -0.15f, 0f);
		animator.rotate(scope, 0, -0.5235f, 0);
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
		animator.move(scope, 0.45f, -0.15f, 0f);
		animator.rotate(scope, 0, -0.5235f, 0);
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
		animator.move(scope, 0.45f, -0.15f, 0f);
		animator.rotate(scope, 0, -0.5235f, 0);
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
		animator.move(scope, 0.45f, -0.15f, 0f);
		animator.rotate(scope, 0, -0.5235f, 0);
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
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(KB);
		animator.startKeyframe(2);
		animator.move(rightarm, 0.11999998f, 0.07999999f, -0.06999999f);
		animator.move(gun, 0.8599995f, 0.0f, 0.03f);
		animator.move(mag, 0.8599995f, 0.0f, 0.03f);
		animator.move(leftarm, -0.36999992f, 0.0f, 0.22000001f);
		animator.rotate(rightarm, -0.2792527f, -0.13962634f, -0.017453292f);
		animator.rotate(gun, 0.0f, 0.0f, 1.047198f);
		animator.rotate(mag, 0.0f, 0.0f, 1.047198f);
		animator.endKeyframe();
		animator.startKeyframe(3);
		animator.move(rightarm, 0.11999998f, 0.07999999f, -0.06999999f);
		animator.move(gun, 0.8599995f, 0.0f, 0.03f);
		animator.move(mag, 0.8599995f, 0.0f, 0.03f);
		animator.move(leftarm, -0.36999992f, 0.0f, 0.22000001f);
		animator.rotate(rightarm, -0.2792527f, -0.13962634f, -0.017453292f);
		animator.rotate(gun, 0.0f, 0.0f, 1.047198f);
		animator.rotate(mag, 0.0f, 0.0f, 1.047198f);
		animator.endKeyframe();
		animator.resetKeyframe(8);
		animator.endKeyframe();
		
		animator.setAnimation(IA);
		animator.startKeyframe(4);
		animator.move(rightarm, 0.109999985f, 0.01f, -0.01f);
		animator.move(gun, 0.0f, 0.23000003f, 0.0f);
		animator.move(hammer, 0.0f, 0.23000003f, 0.0f);
		animator.move(mag, 0.0f, 0.23000003f, 0.0f);
		animator.move(leftarm, -0.23000003f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.2792527f, 0.0f);
		animator.rotate(gun, 0.0f, 0.2792527f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.2792527f, 0.0f);
		animator.rotate(mag, 0.0f, 0.2792527f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(10);
		animator.move(rightarm, 0.109999985f, 0.01f, -0.01f);
		animator.move(gun, 0.0f, 0.23000003f, 0.0f);
		animator.move(hammer, 0.0f, 0.23000003f, 0.0f);
		animator.move(mag, 0.0f, 0.23000003f, 0.0f);
		animator.move(leftarm, -0.23000003f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.2792527f, 0.0f);
		animator.rotate(gun, 0.0f, 0.2792527f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.2792527f, 0.0f);
		animator.rotate(mag, 0.0f, 0.2792527f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, -0.5999997f, 0.13999999f, -0.01f);
		animator.move(gun, -0.18000002f, -0.02f, 0.0f);
		animator.move(hammer, -0.18000002f, -0.02f, 0.0f);
		animator.move(mag, -0.18000002f, -0.02f, 0.0f);
		animator.move(leftarm, -0.5199998f, 0.03f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.29670596f, 0.0f);
		animator.rotate(gun, 0.0f, -0.29670596f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.29670596f, 0.0f);
		animator.rotate(mag, 0.0f, -0.29670596f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(10);
		animator.move(rightarm, -0.5999997f, 0.13999999f, -0.01f);
		animator.move(gun, -0.18000002f, -0.02f, 0.0f);
		animator.move(hammer, -0.18000002f, -0.02f, 0.0f);
		animator.move(mag, -0.18000002f, -0.02f, 0.0f);
		animator.move(leftarm, -0.5199998f, 0.03f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.29670596f, 0.0f);
		animator.rotate(gun, 0.0f, -0.29670596f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.29670596f, 0.0f);
		animator.rotate(mag, 0.0f, -0.29670596f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, 0.03f, 0.441f, 0.7098f, -0.5409f, -0.4886f, -0.4009f);
		if (ClientEventHandler.getPlayer() != null) {
			if(ServerUtils.isMagEqualTo(Util.getStack(), ItemRegistries.ThompsonMag.get()) || !ServerUtils.hasMag(Util.getStack())) {
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
		return new Vector3f(0.29f, -0.16f, -1.63f);
	}

}
