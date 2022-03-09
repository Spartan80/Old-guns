package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.modmodels.WinchesterModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.MeleeHelper;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class WinchesterGunModel extends GunModel {

	public static Animation R1 = Animation.create(22);
	public static Animation R2 = Animation.create(32);
	public static Animation R3 = Animation.create(42);
	public static Animation R4 = Animation.create(52);
	public static Animation R5 = Animation.create(62);
	public static Animation R6 = Animation.create(72);
	public static final Animation KB = Animation.create(12);
	public static final Animation IA = Animation.create(32);
	public static Animation SHOOT = Animation.create(10);

	public WinchesterGunModel() {
		super(ItemRegistries.Winchester.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.2770f), Mth.lerp(p, 0, 0.047f), 0);
		matrix.mulPose(new Quaternion(Mth.lerp(p, 0, -0.0174f), Mth.lerp(p, 0, -0.0523f), 0, false));
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -24))));

	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, scope };
	}

	@Override
	public boolean canRenderMag(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canShoot(ItemStack stack) {
		return getAnimation() == EMPTY;
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, 0.04f, 0.371f, 0.7298f, -0.506f, -0.541f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.2797f, 0.8198f, 0.6989f, 0.3666f, 0.8202f, 0.2619f);
		setPartDisplayTransform(gun, -0.19f, -0.93f, 0, -1.5533f, 0, 0.0523f);
		setPartDisplayTransform(hammer, -0.19f, -0.93f, 0, -1.5533f, 0, 0.0523f);
		setPartDisplayTransform(scope, -0.243f, -0.815f, 0.341f, -1.5533f, 0, 0.0523f);
	}

	@Override
	public void setupAnimations() {
		animator.setAnimation(R1);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, -0.07f, 0, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.11f, 0.06f, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.08f, 0.06f, -0.14f);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.08f, 0.06f, -0.18f);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.11f, 0.06f, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		// End of the cycle
		animator.resetKeyframe(4);// 12
		animator.endKeyframe();

		animator.setAnimation(R2);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, -0.07f, 0, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.11f, 0.06f, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		for(int i = 0; i < 2; i++) {
			// Push bullet cycle
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.14f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(2);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.18f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.11f, 0.06f, 0);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			// End of the cycle
		}
		animator.resetKeyframe(4);// 12
		animator.endKeyframe();

		animator.setAnimation(R3);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, -0.07f, 0, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.11f, 0.06f, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		for(int i = 0; i < 3; i++) {
			// Push bullet cycle
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.14f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(2);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.18f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.11f, 0.06f, 0);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			// End of the cycle
		}
		animator.resetKeyframe(4);// 12
		animator.endKeyframe();

		animator.setAnimation(R4);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, -0.07f, 0, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.11f, 0.06f, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		for(int i = 0; i < 4; i++) {
			// Push bullet cycle
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.14f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(2);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.18f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.11f, 0.06f, 0);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			// End of the cycle
		}
		animator.resetKeyframe(4);// 12
		animator.endKeyframe();

		animator.setAnimation(R5);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, -0.07f, 0, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.11f, 0.06f, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		for(int i = 0; i < 5; i++) {
			// Push bullet cycle
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.14f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(2);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.18f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.11f, 0.06f, 0);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			// End of the cycle
		}
		animator.resetKeyframe(4);// 12
		animator.endKeyframe();

		animator.setAnimation(R6);
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, -0.07f, 0, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(leftarm, -0.107f, -0.019f, 0);
		animator.move(rightarm, 0.11f, 0.06f, 0);
		animator.move(gun, 0.106f, -0.108f, 0);
		animator.rotate(gun, 0, -0.2268f, 0);
		animator.move(hammer, 0.106f, -0.108f, 0);
		animator.rotate(hammer, 0, -0.2268f, 0);
		animator.endKeyframe();
		for(int i = 0; i < 6; i++) {
			// Push bullet cycle
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.14f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(2);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.08f, 0.06f, -0.18f);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(leftarm, -0.107f, -0.019f, 0);
			animator.move(rightarm, 0.11f, 0.06f, 0);
			animator.move(gun, 0.106f, -0.108f, 0);
			animator.rotate(gun, 0, -0.2268f, 0);
			animator.move(hammer, 0.106f, -0.108f, 0);
			animator.rotate(hammer, 0, -0.2268f, 0);
			animator.endKeyframe();
			// End of the cycle
		}
		animator.resetKeyframe(4);// 12
		animator.endKeyframe();
		
		animator.setAnimation(SHOOT);
		animator.setStaticKeyframe(2);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0, 0, -0.065f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(KB);
		animator.startKeyframe(2);
		animator.move(leftarm, -0.26600012f, 0.0f, 0.2610002f);
		animator.move(gun, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(gun, 0.0f, 0.0f, 1.4870303f);
		animator.move(hammer, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.4870303f);
		animator.rotate(rightarm, 0.56548905f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(leftarm, -0.26600012f, 0.0f, 0.2610002f);
		animator.move(gun, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(gun, 0.0f, 0.0f, 1.4870303f);
		animator.move(hammer, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.4870303f);
		animator.rotate(rightarm, 0.56548905f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(8);
		animator.endKeyframe();
		
	}

	@Override
	public void initReloadAnimation(int gun, int mag) {
		int bf = 6 - gun;
		if (mag > bf && bf != 0) {
			mag = bf;

			if (mag == 1) {
				setAnimation(R1);
			} else if (mag == 2) {
				setAnimation(R2);
			} else if (mag == 3) {
				setAnimation(R3);
			} else if (mag == 4) {
				setAnimation(R4);
			} else if (mag == 5) {
				setAnimation(R5);
			} else if (mag == 6) {
				setAnimation(R6);
			}
		} else {
			if (mag == 1) {
				setAnimation(R1);
			} else if (mag == 2) {
				setAnimation(R2);
			} else if (mag == 3) {
				setAnimation(R3);
			} else if (mag == 4) {
				setAnimation(R4);
			} else if (mag == 5) {
				setAnimation(R5);
			} else if (mag == 6) {
				setAnimation(R6);
			}
		}

	}

	@Override
	public void onAnimTick(float animTick) {
		if (getAnimation() != SHOOT && getAnimation() != KB) {
			if (animTick % 10 == 3 && animTick > 10) {
				System.out.println(animTick % 10);
				SoundHandler.playSoundOnServer(SoundRegistries.KARKPUSH.get(), SoundSource.NEUTRAL);
				ReloadHandler.growOneBullet(Util.getStack());
				ReloadHandler.removeOneItemFrom(ammoindex);
			}
		}else if(getAnimation() == SHOOT){
			if(animTick == 2) {
				SoundHandler.playSoundOnServer(SoundRegistries.WINCHESTERBOLTFORWARD.get(), SoundSource.NEUTRAL);
			}else if(animTick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.WINCHESTERBOLTFORWARD.get(), SoundSource.NEUTRAL);
				ReloadHandler.decOneBullet(Util.getStack());
			}
		}else if(getAnimation() == KB) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 4) {
				MeleeHelper.hit(6.5f);
			}
		}

	}

	@Override
	public void onShoot(ItemStack stack) {
		setAnimation(SHOOT);
	}

	@Override
	public void onAnimationEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initExtraParts() {
		// TODO Auto-generated method stub

	}

	@Override
	public BakedModel getModifiedModel(BakedModel origin) {
		// TODO Auto-generated method stub
		return new WinchesterModModel(origin);
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.WINCHESTERHAMMER;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector3f getMuzzleFlashPos(ItemStack stack) {
		return new Vector3f(0.16f, -0.12f, -2.04f);
	}

}
