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

public class PiratePistolAnim extends GunAnimation {

	public PiratePistolAnim() {

	}

	@Override
	public void animGun(MatrixStack stack, int stage, float ticks) {
		super.animGun(stack, stage, ticks);
		switch (stage) {
		case 0:
			stack.translate(0, MathHelper.lerp(ticks, 0, 0.02f), MathHelper.lerp(ticks, 0, 0.12f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, 28), 0, true));
			break;
		case 1:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 2:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 3:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 4:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 5:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 6:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 7:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 8:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 9:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 10:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 11:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 12:
			stack.translate(0, 0.02f, 0.12f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 13:
			stack.translate(0, MathHelper.lerp(ticks, 0.02f, 0f), MathHelper.lerp(ticks, 0.12f, 0));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 28, 0), 0, true));
			break;
		}
	}

	@Override
	public void animLeftArm(MatrixStack stack, int stage, float ticks) {
		super.animLeftArm(stack, stage, ticks);
		switch (stage) {
		case 1:
			stack.translate(MathHelper.lerp(ticks, 0, 1.09f), MathHelper.lerp(ticks, 0, 0.68f),
					MathHelper.lerp(ticks, 0, -1.66f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, 8), MathHelper.rotLerp(ticks, 0, -20), 0, true));
			break;
		case 2:
			if (ticks == 0.5) {
				PlayerEntity player = Minecraft.getInstance().player;
				player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
						SoundRegistries.FlintlockHammerSound.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
				OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(SoundRegistries.FlintlockHammerSound.get(),
						player.getX(), player.getY(), player.getZ()));
			}
			stack.translate(MathHelper.lerp(ticks, 1.09f, 1.24f), 0.68f, -1.66f);
			stack.mulPose(new Quaternion(8, -20, 0, true));
			break;
		case 3:
			stack.translate(MathHelper.lerp(ticks, 1.24f, 0.02f), MathHelper.lerp(ticks, 0.68f, 0.08f),
					MathHelper.lerp(ticks, -1.66, -0.46f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, 8, -12), MathHelper.rotLerp(ticks, -20, -6), 0, true));
			break;
		case 4:
			stack.translate(MathHelper.lerp(ticks, 0, 1.09f), MathHelper.lerp(ticks, 0, 0.68f),
					MathHelper.lerp(ticks, 0, -1.66f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, 8), MathHelper.rotLerp(ticks, 0, -20), 0, true));
			break;
		case 5:
			stack.translate(1.09f, MathHelper.lerp(ticks, 0.68f, 0.56f), -1.66f);
			stack.mulPose(new Quaternion(8, -20, 0, true));
			break;
		case 6:
			stack.translate(1.09f, MathHelper.lerp(ticks, 0.56f, 0.68f), -1.66f);
			stack.mulPose(new Quaternion(8, -20, 0, true));
			break;
		case 7:
			stack.translate(1.09f, MathHelper.lerp(ticks, 0.68f, 0.56f), -1.66f);
			stack.mulPose(new Quaternion(8, -20, 0, true));
			break;
		case 8:
			stack.translate(1.09f, MathHelper.lerp(ticks, 0.56f, 0.68f), -1.66f);
			stack.mulPose(new Quaternion(8, -20, 0, true));
			break;
		case 9:
			stack.translate(MathHelper.lerp(ticks, 1.09f, 0.02f), MathHelper.lerp(ticks, 0.68f, 0.08f),
					MathHelper.lerp(ticks, -1.66, -0.46f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, 8, -12), MathHelper.rotLerp(ticks, -20, -6), 0, true));
			break;
		case 10:
			stack.translate(MathHelper.lerp(ticks, 0, -0.17f), MathHelper.lerp(ticks, 0, 0.1f),
					MathHelper.lerp(ticks, 0, -3.48f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, 8), MathHelper.rotLerp(ticks, 0, -4), 0, true));
			break;
		case 11:
			stack.translate(-0.17f, 0.1f, -3.48f);
			stack.mulPose(new Quaternion(8, MathHelper.rotLerp(ticks, -4, -16), 0, true));
			break;
		case 12:
			stack.translate(MathHelper.lerp(ticks, -0.17f, 0.02f), MathHelper.lerp(ticks, 0.1f, 0.08f),
					MathHelper.lerp(ticks, -3.48, -0.46f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, 8, -12), MathHelper.rotLerp(ticks, -16, -6), 0, true));
			break;
		}

	}

	@Override
	public void animRightArm(MatrixStack stack, int stage, float ticks) {
		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, 1.40f), MathHelper.lerp(ticks, 0, 0.07f),
					MathHelper.lerp(ticks, 0, 0.63f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, 30), MathHelper.rotLerp(ticks, 0, -4), true));
			break;
		case 1:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 2:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 3:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 4:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 5:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 6:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 7:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 8:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 9:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 10:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 11:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 12:
			stack.translate(1.40f, 0.07f, 0.63f);
			stack.mulPose(new Quaternion(0, 30, -4, true));
			break;
		case 13:
			stack.translate(MathHelper.lerp(ticks, 1.40f, 0), MathHelper.lerp(ticks, 0.07f, 0),
					MathHelper.lerp(ticks, 0.63f, 0));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 30, 0), MathHelper.rotLerp(ticks, -4, 0), true));
			break;
		}

	}

	@Override
	public void animHammer(MatrixStack stack, int stage, float ticks) {
		super.animHammer(stack, stage, ticks);
		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, 0.1f), MathHelper.lerp(ticks, 0, 0.01f),
					MathHelper.lerp(ticks, 0, 0.42f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, 28), 0, true));
			break;
		case 1:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 2:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 3:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 4:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 5:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 6:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 7:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 8:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 9:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 10:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 11:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 12:
			stack.translate(0.1f, 0.01f, 0.42f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 13:
			stack.translate(MathHelper.lerp(ticks, 0.1f, 0), MathHelper.lerp(ticks, 0.01f, 0),
					MathHelper.lerp(ticks, 0.42f, 0));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 28, 0), 0, true));
			break;
		}

	}

	@Override
	public void animScope(MatrixStack stack, int stage, float ticks) {
		super.animScope(stack, stage, ticks);
		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, -0.396f), MathHelper.lerp(ticks, 0, 0.02f),
					MathHelper.lerp(ticks, 0, 0.46f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, 28), 0, true));
			break;
		case 1:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 2:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 3:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 4:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 5:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 6:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 7:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 8:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 9:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 10:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 11:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 12:
			stack.translate(-0.396f, 0.02f, 0.46f);
			stack.mulPose(new Quaternion(0, 28, 0, true));
			break;
		case 13:
			stack.translate(MathHelper.lerp(ticks, -0.396f, 0), MathHelper.lerp(ticks, 0.02f, 0),
					MathHelper.lerp(ticks, 0.46f, 0));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 28, 0), 0, true));
			break;
		}

	}

	@Override
	public int getAnimSize() {
		// TODO Auto-generated method stub
		return 14;
	}

	@Override
	public boolean hasCustomReload() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBullets(int bullets, int bulletsleftn, int maxbullets) {
		// TODO Auto-generated method stub

	}

	@Override
	public void animRightHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animRightHandAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -1f), MathHelper.lerp(aimprogress, 0, 0.32f), 0);
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.97f), MathHelper.lerp(aimprogress, 0, 0.22f), 0);
		}
	}

	@Override
	public void animGunAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animGunAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.51f), MathHelper.lerp(aimprogress, 0, 0.08f), 0);
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.51f), MathHelper.lerp(aimprogress, 0, 0.04f), 0);
		}
	}

	@Override
	public void animHammerAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animHammerAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.5f), MathHelper.lerp(aimprogress, 0, 0.068f),
					MathHelper.lerp(aimprogress, 0, -0.052f));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.5f), MathHelper.lerp(aimprogress, 0, 0.028f),
					MathHelper.lerp(aimprogress, 0, -0.052f));
		}
	}

	@Override
	public void animScopeAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animScopeAim(stack, aimprogress, scope);
		stack.translate(MathHelper.lerp(aimprogress, 0, -0.51f), MathHelper.lerp(aimprogress, 0, 0.04f), 0);
	}

	@Override
	public void animRightHandSprint(MatrixStack stack, float sprintprogress) {
		super.animRightHandSprint(stack, sprintprogress);
		stack.translate(MathHelper.lerp(sprintprogress, 0, -0.66f), MathHelper.lerp(sprintprogress, 0, 1.24),
				MathHelper.lerp(sprintprogress, 0, -0.54f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprintprogress, 0, -38),
				MathHelper.rotLerp(sprintprogress, 0, -10), 0, true));
	}

	@Override
	public void animGunSprint(MatrixStack stack, float sprintprogress) {
		super.animGunSprint(stack, sprintprogress);
		stack.translate(0, MathHelper.lerp(sprintprogress, 0, -0.236f), 0);
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprintprogress, 0, -48), 0, 0, true));
	}

	@Override
	public void animHammerSprint(MatrixStack stack, float sprintprogress) {
		super.animHammerSprint(stack, sprintprogress);
		stack.translate(MathHelper.lerp(sprintprogress, 0, 0.148f), MathHelper.lerp(sprintprogress, 0, -0.576f), 0);
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprintprogress, 0, -48), 0, 0, true));
	}

	@Override
	public void animScopeSprint(MatrixStack stack, float sprintprogress) {
		super.animScopeSprint(stack, sprintprogress);
		stack.translate(0, MathHelper.lerp(sprintprogress, 0, -1.03f), MathHelper.lerp(sprintprogress, 0, 0.18f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprintprogress, 0, -34), 0, 0, true));
	}

	@Override
	public void specialReload(int stage) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getReloadTimeForStage(int stage) {
		if (stage >= 5 && stage <= 8) {
			return 4;
		}
		return 8;
	}

}
