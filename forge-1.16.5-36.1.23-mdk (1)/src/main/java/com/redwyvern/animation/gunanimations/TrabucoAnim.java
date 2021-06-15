package com.redwyvern.animation.gunanimations;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.redwyvern.animation.GunAnimation;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.network.PlaySoundOnServerMessage;
import com.redwyvern.registries.SoundRegistries;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;

public class TrabucoAnim extends GunAnimation {

	public TrabucoAnim() {

	}

	@Override
	public void animGun(MatrixStack stack, int stage, float ticks) {
		super.animGun(stack, stage, ticks);
		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, -0.14f), MathHelper.lerp(ticks, 0, -0.08f), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -16), MathHelper.rotLerp(ticks, 0, 24), 0, true));
			break;
		case 10:
			stack.translate(MathHelper.lerp(ticks, -0.14f, -0.08f), MathHelper.lerp(ticks, -0.08f, -0.3f),
					MathHelper.lerp(ticks, 0, -0.51f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, -16, 90), MathHelper.rotLerp(ticks, 24, 0), 0, true));
			break;
		case 11:
			stack.translate(-0.08f, -0.3f, -0.51f);
			stack.mulPose(new Quaternion(90, 0, 0, true));
			break;
		case 12:
			stack.translate(-0.08f, -0.3f, -0.51f);
			stack.mulPose(new Quaternion(90, 0, 0, true));
			break;
		case 13:
			stack.translate(-0.08f, -0.3f, -0.51f);
			stack.mulPose(new Quaternion(90, 0, 0, true));
			break;
		case 14:
			stack.translate(-0.08f, -0.3f, -0.51f);
			stack.mulPose(new Quaternion(90, 0, 0, true));
			break;
		case 15:
			stack.translate(MathHelper.lerp(ticks, -0.08f, 0f), MathHelper.lerp(ticks, -0.3f, 0f),
					MathHelper.lerp(ticks, -0.51f, 0f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 90, 0), 0, 0, true));
			break;
		default:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		/*
		 * case 1: stack.translate(-0.14f, -0.08f, 0); stack.mulPose(new Quaternion(-16,
		 * 24, 0, true)); break; case 2: stack.translate(-0.14f, -0.08f, 0);
		 * stack.mulPose(new Quaternion(-16, 24, 0, true)); break; case 3:
		 * stack.translate(-0.14f, -0.08f, 0); stack.mulPose(new Quaternion(-16, 24, 0,
		 * true)); break; case 4: stack.translate(-0.14f, -0.08f, 0); stack.mulPose(new
		 * Quaternion(-16, 24, 0, true)); break; case 5: stack.translate(-0.14f, -0.08f,
		 * 0); stack.mulPose(new Quaternion(-16, 24, 0, true)); break; case 6:
		 * stack.translate(-0.14f, -0.08f, 0); stack.mulPose(new Quaternion(-16, 24, 0,
		 * true)); break;
		 */
		}

	}

	@Override
	public void animHammer(MatrixStack stack, int stage, float ticks) {
		super.animHammer(stack, stage, ticks);

		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, -0.08f), MathHelper.lerp(ticks, 0, 0.01f),
					MathHelper.lerp(ticks, 0, 0.45f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -16), MathHelper.rotLerp(ticks, 0, 24), 0, true));
			break;
		case 10:
			stack.translate(MathHelper.lerp(ticks, -0.08f, 0.04f), MathHelper.lerp(ticks, 0.01f, -0.49f), 0.45f);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 11:
			stack.translate(0.04f, -0.49f, 0.45f);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 12:
			stack.translate(0.04f, -0.49f, 0.45f);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 13:
			stack.translate(0.04f, -0.49f, 0.45f);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 14:
			stack.translate(0.04f, -0.49f, 0.45f);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 15:
			stack.translate(MathHelper.lerp(ticks, 0.04f, 0f), MathHelper.lerp(ticks, -0.49f, 0f),
					MathHelper.lerp(ticks, 0.45f, 0f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -16, 0), MathHelper.rotLerp(ticks, 24, 0), 0, true));
		default:
			stack.translate(-0.08f, 0.01f, 0.45f);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		/*
		 * case 1: stack.translate(-0.06f, 0.14f, 0); break; case 2:
		 * stack.translate(-0.06f, 0.14f, 0); break; case 3: stack.translate(-0.06f,
		 * 0.14f, 0); break; case 4: stack.translate(-0.06f, 0.14f, 0); break; case 5:
		 * stack.translate(-0.06f, 0.14f, 0); break; case 6: stack.translate(-0.06f,
		 * 0.14f, 0); break; case 7: stack.translate(-0.06f, 0.14f, 0); break;
		 */
		}
	}

	@Override
	public void animLeftArm(MatrixStack stack, int stage, float ticks) {
		super.animLeftArm(stack, stage, ticks);

		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, -0.94f), MathHelper.lerp(ticks, 0, -0.65f),
					MathHelper.lerp(ticks, 0, 0.35f));
			break;
		case 1:
			stack.translate(-0.94f, -0.65f, MathHelper.lerp(ticks, 0.35f, 1.03f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -16), 0, 0, true));
			break;
		case 2:
			if (ticks == 0.5f) {
				PlayerEntity player = Minecraft.getInstance().player;
				player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
						SoundRegistries.FlintlockHammerSound.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
				OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(SoundRegistries.FlintlockHammerSound.get(),
						player.getX(), player.getY(), player.getZ()));
			}
			stack.translate(MathHelper.lerp(ticks, -0.94f, -0.99f), MathHelper.lerp(ticks, -0.65f, 1.20f),
					MathHelper.lerp(ticks, 1.03f, 1.47f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, -16, -4), MathHelper.rotLerp(ticks, 0, -12), 0, true));
			break;
		case 3:
			stack.translate(-0.99f, 1.20f, 1.47f);
			stack.mulPose(new Quaternion(-4, MathHelper.rotLerp(ticks, -12, -14), 0, true));
			break;
		case 4:
			stack.translate(-0.99f, 1.20f, 1.47f);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -4, -6), -14, 0, true));
			break;
		case 5:
			stack.translate(-0.99f, 1.20f, 1.47f);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -6, -4), -14, 0, true));
			break;
		case 6:
			stack.translate(-0.99f, 1.20f, 1.47f);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -4, -6), -14, 0, true));
			break;
		case 7:
			stack.translate(-0.99f, 1.20f, 1.47f);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -6, -4), -14, 0, true));
			break;
		case 8:
			stack.translate(MathHelper.lerp(ticks, -0.99f, -0.94f), MathHelper.lerp(ticks, 1.20f, 0.46f), 1.47f);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -4, 0), MathHelper.rotLerp(ticks, -14, 0), 0, true));
			break;
		case 9:
			stack.translate(-0.94f, MathHelper.lerp(ticks, 0.46f, -0.65f), MathHelper.lerp(ticks, 1.47f, 0.65f));
			break;
		case 10:
			stack.translate(MathHelper.lerp(ticks, -0.94f, -1.71f), MathHelper.lerp(ticks, -0.65f, -0.12f),
					MathHelper.lerp(ticks, 0.65f, 1.03f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, -22), 0, true));
			break;
		case 11:
			stack.translate(-1.71f, -0.12f, 1.03f);
			stack.mulPose(new Quaternion(0, -22, 0, true));
			break;
		case 12:
			stack.translate(-1.71f, -0.12f, 1.03f);
			stack.mulPose(new Quaternion(0, -22, 0, true));
			break;
		case 13:
			stack.translate(-1.71f, -0.12f, 1.03f);
			stack.mulPose(new Quaternion(0, -22, 0, true));
			break;
		case 14:
			stack.translate(-1.71f, -0.12f, 1.03f);
			stack.mulPose(new Quaternion(0, -22, 0, true));
			break;
		case 15:
			stack.translate(MathHelper.lerp(ticks, -1.71f, 0f), MathHelper.lerp(ticks, -0.12f, 0f),
					MathHelper.lerp(ticks, 1.03f, 0f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, -22, 0), 0, true));
			break;
		}
	}

	@Override
	public void animRightArm(MatrixStack stack, int stage, float ticks) {
		super.animRightArm(stack, stage, ticks);

		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, 1.19f), MathHelper.lerp(ticks, 0, 0.70f),
					MathHelper.lerp(ticks, 0, -0.39f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -18), MathHelper.rotLerp(ticks, 0, 30), 0, true));
			break;
		case 10:
			stack.translate(MathHelper.lerp(ticks, 1.19f, 0.72f), MathHelper.lerp(ticks, 0.70f, -0.04f),
					MathHelper.lerp(ticks, -0.39f, -0.22f));
			stack.mulPose(new Quaternion(-18, MathHelper.rotLerp(ticks, 30, 18), 0, true));
			break;
		case 11:
			stack.translate(MathHelper.lerp(ticks, 0.72f, 2.14f), MathHelper.lerp(ticks, -0.04f, 0.05f),
					MathHelper.lerp(ticks, -0.22f, 1.21f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, -18, 26), MathHelper.rotLerp(ticks, 18, 16), 0, true));
			break;
		case 12:
			stack.translate(MathHelper.lerp(ticks, 2.14f, 0.78f), MathHelper.lerp(ticks, 0.05f, 0f),
					MathHelper.lerp(ticks, 1.21f, 1.11f));
			stack.mulPose(new Quaternion(26, 16, 0, true));
			break;
		case 13:
			stack.translate(MathHelper.lerp(ticks, 0.78f, 0.74f), MathHelper.lerp(ticks, 0f, 2.97f),
					MathHelper.lerp(ticks, 1.11f, -0.51f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 26, -36), 16, 0, true));
			break;
		case 14:
			stack.translate(MathHelper.lerp(ticks, 0.74f, 2.14f), MathHelper.lerp(ticks, 2.97f, 0.05f),
					MathHelper.lerp(ticks, -0.51f, 1.21f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -36, 26), 16, 0, true));
			break;
		case 15:
			stack.translate(MathHelper.lerp(ticks, 2.14f, 0f), MathHelper.lerp(ticks, 0.05f, 0f),
					MathHelper.lerp(ticks, 1.21f, 0f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, 26, -14), MathHelper.rotLerp(ticks, 16, 2), 0, true));
			break;
		case 16:
			stack.translate(0f, 0f, 0f);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -14, 0), MathHelper.rotLerp(ticks, 2, 0), 0, true));
			break;
		default:
			stack.translate(1.19f, 0.70f, -0.39f);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		/*
		 * case 1: stack.translate(1.19f, 0.70f, -0.39f); stack.mulPose(new
		 * Quaternion(-18, 30, 0, true)); break; case 2: stack.translate(1.19f, 0.70f,
		 * -0.39f); stack.mulPose(new Quaternion(-18, 30, 0, true)); break; case 3:
		 * stack.translate(1.19f, 0.70f, -0.39f); stack.mulPose(new Quaternion(-18, 30,
		 * 0, true)); break; case 4: stack.translate(1.19f, 0.70f, -0.39f);
		 * stack.mulPose(new Quaternion(-18, 30, 0, true)); break; case 5:
		 * stack.translate(1.19f, 0.70f, -0.39f); stack.mulPose(new Quaternion(-18, 30,
		 * 0, true)); break; case 6: stack.translate(1.19f, 0.70f, -0.39f);
		 * stack.mulPose(new Quaternion(-18, 30, 0, true)); break;
		 */
		}
	}

	@Override
	public void animGunAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animGunAim(stack, aimprogress, scope);

		stack.translate(MathHelper.lerp(aimprogress, 0, -0.39f), MathHelper.lerp(aimprogress, 0, 0.076f),
				MathHelper.lerp(aimprogress, 0, 0.16f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -4), 0, 0, true));
	}

	@Override
	public void animLeftHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animLeftHandAim(stack, aimprogress, scope);

		stack.translate(MathHelper.lerp(aimprogress, 0, -0.22f), 0, 0);
		stack.mulPose(new Quaternion(0, MathHelper.rotLerp(aimprogress, 0, 10), 0, true));
	}

	@Override
	public void animRightHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animRightHandAim(stack, aimprogress, scope);

		stack.translate(MathHelper.lerp(aimprogress, 0, -0.376f), MathHelper.lerp(aimprogress, 0, 0.43f),
				MathHelper.lerp(aimprogress, 0, 0.98f));
		stack.mulPose(new Quaternion(0, MathHelper.rotLerp(aimprogress, 0, 10), 0, true));
	}

	@Override
	public void animHammerAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animHammerAim(stack, aimprogress, scope);

		stack.translate(MathHelper.lerp(aimprogress, 0, -0.36f), MathHelper.lerp(aimprogress, 0, 0.044f),
				MathHelper.lerp(aimprogress, 0, -0.044f));
	}

	@Override
	public void animGunSprint(MatrixStack stack, float sprfloatprogress) {
		super.animGunSprint(stack, sprfloatprogress);

		stack.translate(MathHelper.lerp(sprfloatprogress, 0, -0.13f), 0, 0);
		stack.mulPose(new Quaternion(0, MathHelper.rotLerp(sprfloatprogress, 0, 60), 0, true));
	}

	@Override
	public void animLeftHandSprint(MatrixStack stack, float sprfloatprogress) {
		super.animLeftHandSprint(stack, sprfloatprogress);

		stack.translate(MathHelper.lerp(sprfloatprogress, 0, -0.98f), MathHelper.lerp(sprfloatprogress, 0, -0.18f),
				MathHelper.lerp(sprfloatprogress, 0, 2.07f));
	}

	@Override
	public void animRightHandSprint(MatrixStack stack, float sprfloatprogress) {
		super.animRightHandSprint(stack, sprfloatprogress);

		stack.translate(MathHelper.lerp(sprfloatprogress, 0, 1.56f), 0, MathHelper.lerp(sprfloatprogress, 0, 0.78f));
		stack.mulPose(new Quaternion(0, MathHelper.rotLerp(sprfloatprogress, 0, 30), 0, true));
	}

	@Override
	public void animHammerSprint(MatrixStack stack, float sprfloatprogress) {
		super.animHammerSprint(stack, sprfloatprogress);

		stack.translate(MathHelper.lerp(sprfloatprogress, 0, 0.112f), MathHelper.lerp(sprfloatprogress, 0, -0.004f),
				MathHelper.lerp(sprfloatprogress, 0, 0.492f));
		stack.mulPose(new Quaternion(0, MathHelper.rotLerp(sprfloatprogress, 0, 60), 0, true));
	}

	@Override
	public void animRecoilGun(MatrixStack stack, float recoilticks) {
		super.animRecoilGun(stack, recoilticks);

	}

	@Override
	public int getReloadTimeForStage(int stage) {
		if (stage >= 4 && stage <= 7) {
			return 4;
		}

		return 8;
	}

	@Override
	public void specialReload(int stage) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getAnimSize() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public void setBullets(int bullets, int bulletsleftn, int maxbullets) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasCustomReload() {
		// TODO Auto-generated method stub
		return false;
	}

}
