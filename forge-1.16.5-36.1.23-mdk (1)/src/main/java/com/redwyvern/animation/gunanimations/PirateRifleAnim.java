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

public class PirateRifleAnim extends GunAnimation {

	public PirateRifleAnim() {

	}

	@Override
	public void animGun(MatrixStack stack, int stage, float ticks) {
		super.animGun(stack, stage, ticks);
		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, -0.14f), MathHelper.lerp(ticks, 0, -0.08f), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -16), MathHelper.rotLerp(ticks, 0, 24), 0, true));
			break;
		case 1:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 2:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 3:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 4:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 5:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 6:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 7:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 8:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 9:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 10:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		case 11:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, -16, 2), MathHelper.rotLerp(ticks, 24, 56), 0, true));
			break;
		case 12:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(2, 56, 0, true));
			break;
		case 13:
			stack.translate(-0.14f, -0.08f, 0);
			stack.mulPose(new Quaternion(2, 56, 0, true));
			break;
		case 14:
			stack.translate(MathHelper.lerp(ticks, -0.14f, 0), MathHelper.lerp(ticks, -0.08f, 0), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 2, 0), MathHelper.rotLerp(ticks, 56, 0), 0, true));
			break;
		}

	}

	@Override
	public void animLeftArm(MatrixStack stack, int stage, float ticks) {
		super.animLeftArm(stack, stage, ticks);
		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, -1.1f), MathHelper.lerp(ticks, 0, -0.68f), 0);
			break;
		case 1:
			stack.translate(MathHelper.lerp(ticks, -1.1f, -1.36f), -0.68f, MathHelper.lerp(ticks, 0, 0.34f));
			break;
		case 2:
			stack.translate(MathHelper.lerp(ticks, -1.36f, -0.22f), MathHelper.lerp(ticks, -0.68f, 0.62f),
					MathHelper.lerp(ticks, 0.34f, 1.42f));
			break;
		case 3:
			if (ticks == 0.5f) {
				PlayerEntity player = Minecraft.getInstance().player;
				player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
						SoundRegistries.FlintlockHammerSound.get(), SoundCategory.NEUTRAL, 10.0f, 1.0f);
				OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(SoundRegistries.FlintlockHammerSound.get(),
						player.getX(), player.getY(), player.getZ()));
			}
			stack.translate(MathHelper.lerp(ticks, -0.22f, -0.1f), 0.62f, 1.42f);
			break;
		case 4:
			stack.translate(MathHelper.lerp(ticks, -0.1f, -0.76f), MathHelper.lerp(ticks, 0.62f, -0.58f),
					MathHelper.lerp(ticks, 1.42f, 2.88f));
			break;
		case 5:
			stack.translate(MathHelper.lerp(ticks, -0.76f, -0.22f), MathHelper.lerp(ticks, -0.58f, 0.82f),
					MathHelper.lerp(ticks, 2.88f, 1.42f));
			break;
		case 6:
			stack.translate(-0.22f, MathHelper.lerp(ticks, 0.82f, 0.62f), 1.42f);
			break;
		case 7:
			stack.translate(-0.22f, MathHelper.lerp(ticks, 0.62f, 0.82f), 1.42f);
			break;
		case 8:
			stack.translate(-0.22f, MathHelper.lerp(ticks, 0.82f, 0.62f), 1.42f);
			break;
		case 9:
			stack.translate(-0.22f, MathHelper.lerp(ticks, 0.62f, 0.82f), 1.42f);
			break;
		case 10:
			stack.translate(MathHelper.lerp(ticks, -0.22f, -0.76f), MathHelper.lerp(ticks, 0.82f, -0.58f),
					MathHelper.lerp(ticks, 1.42f, 2.88f));
			break;
		case 11:
			stack.translate(-0.76f, -0.58f, 2.88f);
			break;
		case 12:
			stack.translate(MathHelper.lerp(ticks, -0.76f, -1.34f), MathHelper.lerp(ticks, -0.58f, 0.28f),
					MathHelper.lerp(ticks, 2.88f, 0.40f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, 32), 0, true));
			break;
		case 13:
			stack.translate(MathHelper.lerp(ticks, -1.34f, -1.18f), 0.28f, 0.40f);
			stack.mulPose(new Quaternion(0, 32, 0, true));
			break;
		case 14:
			stack.translate(MathHelper.lerp(ticks, -1.18f, 0), MathHelper.lerp(ticks, 0.28f, 0),
					MathHelper.lerp(ticks, 0.40f, 0));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 32, 0), 0, true));
			break;
		}

	}

	@Override
	public void animRightArm(MatrixStack stack, int stage, float ticks) {
		super.animRightArm(stack, stage, ticks);
		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, 1.28f), MathHelper.lerp(ticks, 0, 0.74f), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -18), MathHelper.rotLerp(ticks, 0, 30), 0, true));
			break;
		case 1:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 2:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 3:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 4:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 5:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 6:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 7:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 8:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 9:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 10:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 11:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 12:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 13:
			stack.translate(1.28f, 0.74f, 0);
			stack.mulPose(new Quaternion(-18, 30, 0, true));
			break;
		case 14:
			stack.translate(MathHelper.lerp(ticks, 1.28f, 0), MathHelper.lerp(ticks, 0.74f, 0), 0);
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, -18f, 0), MathHelper.rotLerp(ticks, 30, 0), 0, true));
			break;

		}

	}

	@Override
	public void animHammer(MatrixStack stack, int stage, float ticks) {
		super.animHammer(stack, stage, ticks);
		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, -0.15f), MathHelper.lerp(ticks, 0, 0.01f),
					MathHelper.lerp(ticks, 0, 0.41f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -16), MathHelper.rotLerp(ticks, 0, 24), 0, true));
			break;
		case 11:
			stack.translate(MathHelper.lerp(ticks, -0.15f, 0.14f), MathHelper.lerp(ticks, 0.01f, -0.13f),
					MathHelper.lerp(ticks, 0.41f, 0.49f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, -16, 2), MathHelper.rotLerp(ticks, 24, 56), 0, true));
			break;
		case 12:
			stack.translate(0.14f, -0.14f, 0.49f);
			stack.mulPose(new Quaternion(2, 56, 0, true));
			break;
		case 13:
			stack.translate(0.14f, -0.14f, 0.49f);
			stack.mulPose(new Quaternion(2, 56, 0, true));
			break;
		case 14:
			stack.translate(MathHelper.lerp(ticks, 0.14f, 0), MathHelper.lerp(ticks, -0.14f, 0),
					MathHelper.lerp(ticks, 0.49f, 0));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 2, 0), MathHelper.rotLerp(ticks, 56, 0), 0, true));
			break;
		default:
			stack.translate(-0.15f, 0.01f, 0.41f);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		}
	}

	@Override
	public void animScope(MatrixStack stack, int stage, float ticks) {
		super.animScope(stack, stage, ticks);
		switch (stage) {
		case 0:
			stack.translate(MathHelper.lerp(ticks, 0, -0.4f), MathHelper.lerp(ticks, 0, -0.227f),
					MathHelper.lerp(ticks, 0, 0.172f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -16), MathHelper.rotLerp(ticks, 0, 24), 0, true));
			break;
		case 11:
			stack.translate(MathHelper.lerp(ticks, -0.4f, -0.824f), MathHelper.lerp(ticks, -0.227f, -0.052f),
					MathHelper.lerp(ticks, 0.172f, 0.116f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, -16, 2), MathHelper.rotLerp(ticks, 24, 56), 0, true));
			break;
		case 12:
			stack.translate(-0.824f, -0.052f, 0.116f);
			stack.mulPose(new Quaternion(2, 56, 0, true));
			break;
		case 13:
			stack.translate(-0.824f, -0.052f, 0.116f);
			stack.mulPose(new Quaternion(2, 56, 0, true));
			break;
		case 14:
			stack.translate(MathHelper.lerp(ticks, -0.824f, 0), MathHelper.lerp(ticks, -0.052f, 0),
					MathHelper.lerp(ticks, 0.116f, 0));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 2, 0), MathHelper.rotLerp(ticks, 56, 0), 0, true));
			break;
		default:
			stack.translate(-0.4f, -0.227f, 0.172f);
			stack.mulPose(new Quaternion(-16, 24, 0, true));
			break;
		}
	}

	@Override
	public int getAnimSize() {
		return 14;
	}

	@Override
	public void setBullets(int bullets, int bulletsleftn, int maxbullets) {

	}

	@Override
	public boolean hasCustomReload() {
		return false;
	}

	@Override
	public void animLeftHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animLeftHandAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, 0.44f), MathHelper.lerp(aimprogress, 0, 1.02f), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -16),
					MathHelper.rotLerp(aimprogress, 0, 18), 0, true));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.17f), 0, 0);
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(aimprogress, 0, 10), 0, true));
		}
	}

	@Override
	public void animRightHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animRightHandAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.18f), MathHelper.lerp(aimprogress, 0, 0.38f),
					MathHelper.lerp(aimprogress, 0, 1.06f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(aimprogress, 0, 14), 0, true));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.88f), 0, 0);
		}
	}

	@Override
	public void animGunAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animGunAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.4f), MathHelper.lerp(aimprogress, 0, 0.1f),
					MathHelper.lerp(aimprogress, 0, 0.24f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -4), 0, 0, true));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.4f), 0, 0);
		}
	}

	@Override
	public void animHammerAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animHammerAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.356f), MathHelper.lerp(aimprogress, 0, 0.027f),
					MathHelper.lerp(aimprogress, 0, 0.024f));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.396f), MathHelper.lerp(aimprogress, 0, -0.024f),
					MathHelper.lerp(aimprogress, 0, -0.044f));
		}
	}

	@Override
	public void animScopeAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animScopeAim(stack, aimprogress, scope);
		stack.translate(MathHelper.lerp(aimprogress, 0, -0.4f), 0, 0);

	}

	@Override
	public void animLeftHandSprint(MatrixStack stack, float sprintprogress) {
		super.animLeftHandSprint(stack, sprintprogress);
		stack.translate(MathHelper.lerp(sprintprogress, 0, -0.14f), MathHelper.lerp(sprintprogress, 0, -0.56f),
				MathHelper.lerp(sprintprogress, 0, 0.86f));
		stack.mulPose(new Quaternion(0, MathHelper.rotLerp(sprintprogress, 0, 24), 0, true));

	}

	@Override
	public void animRightHandSprint(MatrixStack stack, float sprintprogress) {
		super.animRightHandSprint(stack, sprintprogress);
		stack.translate(MathHelper.lerp(sprintprogress, 0, 0.28f), MathHelper.lerp(sprintprogress, 0, 0.74f),
				MathHelper.lerp(sprintprogress, 0, 1.12f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprintprogress, 0, -10),
				MathHelper.rotLerp(sprintprogress, 0, 12), 0, true));

	}

	@Override
	public void animGunSprint(MatrixStack stack, float sprintprogress) {
		super.animGunSprint(stack, sprintprogress);
		stack.translate(MathHelper.lerp(sprintprogress, 0, -0.28f), MathHelper.lerp(sprintprogress, 0, -0.02f),
				MathHelper.lerp(sprintprogress, 0, 0.24f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprintprogress, 0, -14),
				MathHelper.rotLerp(sprintprogress, 0, 44), 0, true));

	}

	@Override
	public void animHammerSprint(MatrixStack stack, float sprintprogress) {
		super.animHammerSprint(stack, sprintprogress);
		stack.translate(MathHelper.lerp(sprintprogress, 0, -0.112f), MathHelper.lerp(sprintprogress, 0, 0.124f),
				MathHelper.lerp(sprintprogress, 0, 0.86f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprintprogress, 0, -16),
				MathHelper.rotLerp(sprintprogress, 0, 44), 0, true));
	}

	@Override
	public void animScopeSprint(MatrixStack stack, float sprintprogress) {
		super.animScopeSprint(stack, sprintprogress);
		stack.translate(MathHelper.lerp(sprintprogress, 0, -0.83f), MathHelper.lerp(sprintprogress, 0, -0.06f),
				MathHelper.lerp(sprintprogress, 0, 0.28f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprintprogress, 0, -6),
				MathHelper.rotLerp(sprintprogress, 0, 44), 0, true));
	}

	@Override
	public void specialReload(int stage) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getReloadTimeForStage(int stage) {
		if (stage >= 6 && stage <= 9) {
			return 4;
		}
		return 8;
	}

}
