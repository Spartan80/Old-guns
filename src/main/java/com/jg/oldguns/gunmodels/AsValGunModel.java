package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.modmodels.AsValModModel;
import com.jg.oldguns.debug.AnimWriter;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.MeleeHelper;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class AsValGunModel extends GunModel{

	public static final Animation SA = Animation.create(2);
	public static final Animation R1 = Animation.create(30);
	public static final Animation R2 = Animation.create(54);
	public static final Animation R3 = Animation.create(26);
	public static final Animation R4 = Animation.create(50);
	public static final Animation GOM = Animation.create(26);
	public static final Animation KB = Animation.create(16);
	public static final Animation IA = Animation.create(28);
	
	public AsValGunModel() {
		super(ItemRegistries.AsVal.get());
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		setAnimation(GOM);
		
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
		return new Vector3f(0.17f, -0.10f, -1.18f);
	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.315f), Mth.lerp(p, 0, 0.159f), Mth.lerp(p, 0, 0.184f));
		matrix.mulPose(new Quaternion(Mth.lerp(p, 0, -0.1012f), Mth.lerp(p, 0, -0.1221f), 0, false));
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -24))));
		
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] {rightarm, leftarm, gun, mag, hammer, aim, scope};
	}

	@Override
	public boolean canRenderMag(ItemStack stack) {
		return ServerUtils.hasMag(stack);
	}

	@Override
	public boolean canShoot(ItemStack stack) {
		return getAnimation() == EMPTY;
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, 0.046f, 0.407f, 0.8447f, -0.5409f, -0.4886f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.2437f, 0.8528f, 0.8148f, 0.3666f, 0.8202f, 0.2619f);
		setPartDisplayTransform(gun, -0.22f, -0.83f, -0.07f, -1.4729f, 0, 0.1204f);
		setPartDisplayTransform(hammer, -0.22f, -0.83f, -0.07f, -1.4729f, 0, 0.1204f);
		setPartDisplayTransform(mag, -0.22f, -0.83f, -0.07f, -1.4729f, 0, 0.1204f);
		setPartDisplayTransform(scope, -0.235f, -0.793f, 0.3709f, -1.5358f, 0, 0.1047f);
	}

	@Override
	public void setupAnimations() {
		
		animator.setAnimation(R1);
		animator.startKeyframe(6);
		animator.move(leftarm, -0.12999998f, -0.08999999f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.12999998f, -0.08999999f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.move(gun, 0.0f, 0.001f, 0f);
		animator.move(hammer, 0.0f, 0.001f, 0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R2);
		animator.startKeyframe(6);
		animator.move(leftarm, -0.12999998f, -0.08999999f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.12999998f, -0.08999999f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.move(gun, 0.0f, 0.001f, 0f);
		animator.move(hammer, 0.0f, 0.001f, 0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.20000002f, 0.0f, -0.03f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.20000002f, 0.049999997f, -0.22000003f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.20000002f, 0.049999997f, -0.13999999f);
		animator.move(gun, 0.0f, 0.0f, 0.003f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.20000002f, 0.049999997f, -0.24000004f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.20000002f, -0.03f, -0.049999997f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R3);
		animator.startKeyframe(6);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.12999998f, -0.08999999f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.move(gun, 0.0f, 0.001f, 0f);
		animator.move(hammer, 0.0f, 0.001f, 0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R4);
		animator.startKeyframe(6);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.12999998f, -0.08999999f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.move(gun, 0.0f, 0.001f, 0f);
		animator.move(hammer, 0.0f, 0.001f, 0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.20000002f, 0.0f, -0.03f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.20000002f, 0.049999997f, -0.22000003f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.20000002f, 0.049999997f, -0.13999999f);
		animator.move(gun, 0.0f, 0.0f, 0.003f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.20000002f, 0.049999997f, -0.24000004f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(rightarm, 0.20000002f, -0.03f, -0.049999997f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(GOM);
		animator.startKeyframe(6);
		animator.move(leftarm, -0.12999998f, -0.08999999f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.0f, -0.003f, 0f);
		animator.move(hammer, 0.0f, -0.003f, 0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(mag, 0.0f, -0.35999992f, 0.0f);
		animator.move(leftarm, -0.12999998f, -0.5199998f, 0.12999998f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.593412f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(KB);
		animator.startKeyframe(1);
		animator.move(rightarm, 0.25000003f, 0.0f, -0.03f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.8099995f, 0.01f, -0.03f);
		animator.move(mag, 0.8099995f, 0.01f, -0.03f);
		animator.move(hammer, 0.8099995f, 0.01f, -0.03f);
		animator.move(leftarm, -0.28f, 0.0f, 0.18f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 1.1175871E-8f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 1.2042779f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.2042779f);
		animator.rotate(mag, 0.0f, 0.0f, 1.2042779f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, 0.25000003f, 0.0f, -0.03f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.8099995f, 0.01f, -0.03f);
		animator.move(hammer, 0.8099995f, 0.01f, -0.03f);
		animator.move(mag, 0.8099995f, 0.01f, -0.03f);
		animator.move(leftarm, -0.28f, 0.0f, 0.18f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 1.1175871E-8f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 1.2042779f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.2042779f);
		animator.rotate(mag, 0.0f, 0.0f, 1.2042779f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(IA);
		animator.startKeyframe(4);
		animator.move(rightarm, -0.049999997f, 0.07999999f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.23000003f, -0.11999998f, 0.0f);
		animator.move(hammer, 0.23000003f, -0.11999998f, 0.0f);
		animator.move(mag, 0.23000003f, -0.11999998f, 0.0f);
		animator.move(leftarm, -0.35999992f, 0.0f, 0.0f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.17453294f, 0.0f);
		animator.rotate(gun, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(mag, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, -0.049999997f, 0.07999999f, 0.0f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.23000003f, -0.11999998f, 0.0f);
		animator.move(hammer, 0.23000003f, -0.11999998f, 0.0f);
		animator.move(mag, 0.23000003f, -0.11999998f, 0.0f);
		animator.move(leftarm, -0.35999992f, 0.0f, 0.0f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.17453294f, 0.0f);
		animator.rotate(gun, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(hammer, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(mag, 0.0f, -0.45378554f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.18f, 0.01f, -0.01f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, -0.049999997f, 0.30999997f, 0.0f);
		animator.move(hammer, -0.049999997f, 0.30999997f, 0.0f);
		animator.move(mag, -0.049999997f, 0.30999997f, 0.0f);
		animator.move(leftarm, 0.09f, 0.0f, 0.0f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.19198622f, 0.0f);
		animator.rotate(gun, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(mag, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.57595867f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.18f, 0.01f, -0.01f);
		animator.move(leftarm, 0.0f, 0.0f, 0.0f);
		animator.move(gun, -0.049999997f, 0.30999997f, 0.0f);
		animator.move(hammer, -0.049999997f, 0.30999997f, 0.0f);
		animator.move(mag, -0.049999997f, 0.30999997f, 0.0f);
		animator.move(leftarm, 0.09f, 0.0f, 0.0f);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.19198622f, 0.0f);
		animator.rotate(gun, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(hammer, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(mag, 0.0f, 0.4537855f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.57595867f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(SA);
		animator.startKeyframe(1);
		animator.move(hammer, 0.008f, -0.004f, 0.105f);
		animator.endKeyframe();
		animator.resetKeyframe(1);
		animator.endKeyframe();
	}

	@Override
	public void initReloadAnimation(int currentgunbullets, int magbullets, boolean hasMag) {
		if(hasMag) {
			if(magbullets == 0) {
				System.out.println("R1");
				setAnimation(R1);
			}else {
				System.out.println("R2");
				setAnimation(R2);
			}
		}else {
			if(magbullets == 0) {
				System.out.println("R3");
				setAnimation(R3);
			}else {
				System.out.println("R4");
				setAnimation(R4);
			}
		}
	}

	@Override
	public void onAnimTick(float animTick) {
		if(getAnimation() == R1) {
			if(animTick == 6) {
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALMAGOUT.get());
			}else if(animTick == 21) {
				ReloadHandler.restoreMag(getMPath(), gunbullets);
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			}else if(animTick == 26) {
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALMAGIN.get());
				ReloadHandler.setBullets(magbullets);
			}
		} else if(getAnimation() == R2) {
			if(animTick == 6) {
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALMAGOUT.get());
			} else if(animTick == 21) {
				ReloadHandler.restoreMag(getMPath(), gunbullets);
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			} else if(animTick == 26) {
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALMAGIN.get());
			} else if(animTick == 39) {
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALSLIDEBACK.get());
				ReloadHandler.setBullets(magbullets);
			} else if(animTick == 43) {
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALSLIDEFORWARD.get());
			}
		} else if(getAnimation() == R3) {
			if(animTick == 7) {
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			}else if(animTick == 21){
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALMAGIN.get());
			}
		} if(getAnimation() == R4) {
			if(animTick == 7) {
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			} else if(animTick == 21){
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALMAGIN.get());
			} else if(animTick == 35) {
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALSLIDEBACK.get());
			} else if(animTick == 39) {
				ReloadHandler.setBullets(magbullets);
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALSLIDEFORWARD.get());
			}
		} else if(getAnimation() == GOM) {
			if(animTick == 6) {
				SoundHandler.playSoundOnServer(SoundRegistries.ASVALMAGOUT.get());
			}else if(animTick == 20) {
				ReloadHandler.restoreMag(getMPath(), gunbullets);
				ReloadHandler.setMag(this, 0, false, "", Constants.EMPTY);
				ReloadHandler.setBullets(0);
			}
		} else if(getAnimation() == KB) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 4) {
				MeleeHelper.hit(7);
			}
		}
	}

	@Override
	public void onShoot(ItemStack stack) {
		setAnimation(SA);
	}

	@Override
	public void onAnimationEnd() {
		AnimWriter.onEndAnimation(this, R1);
	}

	@Override
	public void initExtraParts() {
		
	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		return new AsValModModel(origin);
	}

	@Override
	public String getHammerPath() {
		return Paths.ASVALHAMMER;
	}

	@Override
	public boolean hasMultipleParts() {
		return true;
	}

}
