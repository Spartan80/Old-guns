package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.animations.RepetitiveAnimation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.modmodels.Kar98kModModel;
import com.jg.oldguns.debug.AnimWriter;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.MeleeHelper;
import com.jg.oldguns.utils.Paths;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class Kar98kGunModel extends GunModel {

	public static final Animation SA = Animation.create(24);
	public static final RepetitiveAnimation R = Animation.createRep(43);
	public static final Animation R1 = Animation.create(43);
	public static final Animation R2 = Animation.create(51);
	public static final Animation R3 = Animation.create(59);
	public static final Animation R4 = Animation.create(67);
	public static final Animation R5 = Animation.create(75);
	public static final Animation KB = Animation.create(32);
	public static final Animation IA = Animation.create(32);

	public Kar98kGunModel() {
		super(ItemRegistries.Kar98k.get());
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
		return getAnimation() == EMPTY;
	}

	@Override
	public void doGetOutMag(ItemStack arg0) {

	}

	@Override
	public String getHammerPath() {
		return Paths.KARKHAM;
	}

	@Override
	public BakedModel getModifiedModel(BakedModel origin) {
		return new Kar98kModModel(origin);
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, scope };
	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.2999f), Mth.lerp(p, 0, 0.055f), 0);
		matrix.mulPose(Vector3f.YN.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, 6))));
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -24))));
	}

	@Override
	public void initReloadAnimation(int gun, int mag) {
		int bf = 5 - gun;
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
			}
		}

	}

	@Override
	public void onAnimTick(float tick) {
		Animation c = getAnimation();
		ItemStack stack = ClientEventHandler.getPlayer().getMainHandItem();

		if (c == SA) {
			if (tick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKBACK.get(), SoundSource.NEUTRAL);
			} else if (tick == 12) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKBACK.get(), SoundSource.NEUTRAL);
			}
		} else if (c == R1) {
			if (tick == 12) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKBACK.get(), SoundSource.NEUTRAL);
			} else if (tick == 24) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKPUSH.get(), SoundSource.NEUTRAL);
				ReloadHandler.growOneBullet(stack);
				ReloadHandler.removeItem(ammoindex, 1);

			} else if (tick == 36) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKFORWARD.get(), SoundSource.NEUTRAL);
			}
		} else if (c == R2) {
			if (tick == 12) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKBACK.get(), SoundSource.NEUTRAL);
			} else if (tick == 24 || tick == 32) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKPUSH.get(), SoundSource.NEUTRAL);
				ReloadHandler.growOneBullet(stack);
				ReloadHandler.removeItem(ammoindex, 1);

			} else if (tick == 44) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKFORWARD.get(), SoundSource.NEUTRAL);
			}
		} else if (c == R3) {
			if (tick == 12) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKBACK.get(), SoundSource.NEUTRAL);
			} else if (tick == 24 || tick == 32 || tick == 40) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKPUSH.get(), SoundSource.NEUTRAL);
				ReloadHandler.growOneBullet(stack);
				ReloadHandler.removeItem(ammoindex, 1);
			} else if (tick == 52) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKFORWARD.get(), SoundSource.NEUTRAL);
			}
		} else if (c == R4) {
			if (tick == 12) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKBACK.get(), SoundSource.NEUTRAL);
			} else if (tick == 24 || tick == 32 || tick == 40 || tick == 48) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKPUSH.get(), SoundSource.NEUTRAL);
				ReloadHandler.growOneBullet(stack);
				ReloadHandler.removeItem(ammoindex, 1);
			} else if (tick == 60) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKFORWARD.get(), SoundSource.NEUTRAL);
			}
		} else if (c == R5) {
			if (tick == 12) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKBACK.get(), SoundSource.NEUTRAL);
			} else if (tick == 24 || tick == 32 || tick == 40 || tick == 48 || tick == 56) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKPUSH.get(), SoundSource.NEUTRAL);
				ReloadHandler.growOneBullet(stack);
				ReloadHandler.removeItem(ammoindex, 1);
			} else if (tick == 68) {
				SoundHandler.playSoundOnServer(SoundRegistries.KARKFORWARD.get(), SoundSource.NEUTRAL);
				ClientEventHandler.getPlayer().playSound(SoundRegistries.KARKFORWARD.get(), 1.0f, 1.0f);
			}
		} else if(c == KB) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(tick == 4) {
				MeleeHelper.hit(7);
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
		animator.startKeyframe(4);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.11f, 0.23f, -0.14f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(hammer, 0.02f, -0.01f, 0.23f);
		animator.move(rightarm, 0.15f, 0.23f, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(hammer, 0.02f, -0.01f, 0.23f);
		animator.move(rightarm, 0.15f, 0.23f, 0);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, 0.11f, 0.23f, -0.14f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, 0.11f, 0.23f, -0.14f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		// One bullet reload animation
		animator.setAnimation(R1);
		animator.startKeyframe(4);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.11f, 0.23f, -0.14f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(hammer, 0.02f, -0.01f, 0.23f);
		animator.move(rightarm, 0.15f, 0.23f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		animator.startKeyframe(4);
		animator.move(rightarm, 0.1f, 0.27f, 0.05f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.27f, -0.14f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(5);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(6);
		animator.endKeyframe();

		// Two bullet reload animation
		animator.setAnimation(R2);
		animator.startKeyframe(4);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.11f, 0.23f, -0.14f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(hammer, 0.02f, -0.01f, 0.23f);
		animator.move(rightarm, 0.15f, 0.23f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		animator.startKeyframe(4);
		animator.move(rightarm, 0.1f, 0.27f, 0.05f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.27f, -0.14f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(5);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(6);
		animator.endKeyframe();

		// Three bullet reload animation
		animator.setAnimation(R3);
		animator.startKeyframe(4);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.11f, 0.23f, -0.14f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(hammer, 0.02f, -0.01f, 0.23f);
		animator.move(rightarm, 0.15f, 0.23f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		animator.startKeyframe(4);
		animator.move(rightarm, 0.1f, 0.27f, 0.05f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.27f, -0.14f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(5);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(6);
		animator.endKeyframe();

		// Four bullet reload animation
		animator.setAnimation(R4);
		animator.startKeyframe(4);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.11f, 0.23f, -0.14f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(hammer, 0.02f, -0.01f, 0.23f);
		animator.move(rightarm, 0.15f, 0.23f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		// Push bullet cycle
		animator.startKeyframe(4);
		animator.move(rightarm, 0.04f, 0.19f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		// Push bullet cycle end
		animator.startKeyframe(4);
		animator.move(rightarm, 0.1f, 0.27f, 0.05f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.27f, -0.14f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(5);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(6);
		animator.endKeyframe();

		// Five bullet reload animation
		animator.setAnimation(R5);
		animator.startKeyframe(4);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.11f, 0.23f, -0.14f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(hammer, 0.02f, -0.01f, 0.23f);
		animator.move(rightarm, 0.15f, 0.23f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.06f, 0.23f, -0.14f);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		for(int i = 0; i < 5; i++) {
			animator.startKeyframe(4);
			animator.move(rightarm, 0.04f, 0.19f, -0.14f);
			animator.move(leftarm, -0.05f, -0.02f, 0);
			animator.move(gun, 0.18f, -0.1f, 0f);
			animator.rotate(gun, 0, -0.279252f, 0);
			animator.move(hammer, 0.2f, -0.1f, 0.18f);
			animator.rotate(hammer, 0, -0.279252f, 0);
			animator.endKeyframe();
			animator.startKeyframe(4);
			animator.move(rightarm, 0.06f, 0.23f, -0.14f);
			animator.move(leftarm, -0.05f, -0.02f, 0);
			animator.move(gun, 0.18f, -0.1f, 0f);
			animator.rotate(gun, 0, -0.279252f, 0);
			animator.move(hammer, 0.2f, -0.1f, 0.18f);
			animator.rotate(hammer, 0, -0.279252f, 0);
			animator.endKeyframe();
		}
		animator.startKeyframe(4);
		animator.move(rightarm, 0.1f, 0.27f, 0.05f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.2f, -0.1f, 0.18f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.05f, 0.27f, -0.14f);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.startKeyframe(5);
		animator.move(rightarm, 0.15f, 0, 0);
		animator.rotate(rightarm, 0, -0.06981f, 0);
		animator.move(leftarm, -0.05f, -0.02f, 0);
		animator.move(gun, 0.18f, -0.1f, 0f);
		animator.rotate(gun, 0, -0.279252f, 0);
		animator.move(hammer, 0.18f, -0.1f, -0.02f);
		animator.rotate(hammer, 0, -0.279252f, 0);
		animator.endKeyframe();
		animator.resetKeyframe(6);
		animator.endKeyframe();
		
		animator.setAnimation(KB);
		animator.startKeyframe(2);
		animator.move(leftarm, -0.13300012f, 0.0f, 0.1310002f);
		animator.move(gun, 0.59f, 0.0155000003f, -0.100022f);
		animator.rotate(gun, 0.0f, 0.0f, 0.74351515f);
		animator.move(hammer, 0.59f, 0.0155000003f, -0.100022f);
		animator.rotate(hammer, 0.0f, 0.0f, 0.74351515f);
		animator.rotate(rightarm, 0.282744525f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(1);
		animator.move(leftarm, -0.26600012f, 0.0f, 0.2610002f);
		animator.move(gun, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(gun, 0.0f, 0.0f, 1.4870303f);
		animator.rotate(rightarm, 0.56548905f, 0.0f, 0.0f);
		animator.move(hammer, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.4870303f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(leftarm, -0.26600012f, 0.0f, 0.2610002f);
		animator.move(gun, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(rightarm, 0.56548905f, 0.0f, 0.0f);
		animator.rotate(gun, 0.0f, 0.0f, 1.4870303f);
		animator.move(hammer, 1.1889995f, 0.031000003f, -0.19900022f);
		animator.rotate(hammer, 0.0f, 0.0f, 1.4870303f);
		animator.endKeyframe();
		animator.resetKeyframe(8);
		animator.endKeyframe();
		
		animator.setAnimation(IA);
		animator.startKeyframe(4);
		animator.move(gun, 0.0f, 0.30999997f, 0.0f);
		animator.move(leftarm, 0.22000001f, 0.0f, 0.0f);
		animator.move(rightarm, 0.2f, -0.02f, -0.06999999f);
		animator.rotate(gun, 0.0f, 0.43633226f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.52359873f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.43633226f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(gun, 0.0f, 0.30999997f, 0.0f);
		animator.move(leftarm, 0.22000001f, 0.0f, 0.0f);
		animator.move(rightarm, 0.2f, -0.02f, -0.06999999f);
		animator.rotate(gun, 0.0f, 0.43633226f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.52359873f, 0.0f);
		animator.rotate(rightarm, 0.0f, 0.43633226f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(gun, 0.25000003f, -0.040000014f, 0.0f);
		animator.move(leftarm, -0.14f, 0.0f, 0.0f);
		animator.move(rightarm, -0.050000012f, 0.23000003f, -0.06999999f);
		animator.rotate(gun, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.52359873f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(gun, 0.25000003f, -0.040000014f, 0.0f);
		animator.move(leftarm, -0.14f, 0.0f, 0.0f);
		animator.move(rightarm, -0.050000012f, 0.23000003f, -0.06999999f);
		animator.rotate(gun, 0.0f, -0.43633223f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.52359873f, 0.0f);
		animator.rotate(rightarm, 0.0f, -0.43633223f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, 0.0400f, 0.3710f, 0.7298f, -0.5060f, -0.5410f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.3397f, 0.8498f, 0.6989f, 0.3666f, 0.8202f, 0.2619f);
		setPartDisplayTransform(gun, -0.24f, -0.84f, -0.08f, -1.5533f, 0, 0.1047f);
		setPartDisplayTransform(scope, -0.24f, -0.84f, -0.08f, -1.5533f, 0, 0.1047f);
		setPartDisplayTransform(hammer, -0.24f, -0.84f, -0.08f, -1.5533f, 0, 0.1047f);
		
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
		return new Vector3f(0.071f, -0.13f, -2.14f);
	}

}
