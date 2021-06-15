package com.redwyvern.animation.gunanimations;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.redwyvern.animation.AnimationManager;
import com.redwyvern.animation.GunAnimation;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.network.BulletsMessage;
import com.redwyvern.network.PlaySoundOnServerMessage;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.registries.SoundRegistries;
import com.redwyvern.util.InventoryHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;

public class Kar98KAnim extends GunAnimation {

	public Kar98KAnim() {

	}

	@Override
	public int getReloadTimeForStage(int stage) {
		return 8;
	}

	@Override
	public int getAnimSize() {
		return animsize;
	}

	@Override
	public void animGun(MatrixStack stack, int stage, float ticks) {
		super.animGun(stack, stage, ticks);
		if (stage == 0) {
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, 10), MathHelper.rotLerp(ticks, 0, 6), true));
		} else if (stage == animsize) {
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 10, 0), MathHelper.rotLerp(ticks, 6, 0), true));
		} else {
			stack.mulPose(new Quaternion(0, 10, 6, true));
		}
	}

	@Override
	public void animHammer(MatrixStack stack, int stage, float ticks) {
		super.animHammer(stack, stage, ticks);
		if (bulletsleft == 4) {
			switch (stage) {
			case 0:
				stack.translate(MathHelper.lerp(ticks, 0, 0.1f), MathHelper.lerp(ticks, 0, -0.068f),
						MathHelper.lerp(ticks, 0, 0.108f));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 0, 10), MathHelper.rotLerp(ticks, 0, 6), true));
				break;
			case 1:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 2:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 3:

				stack.translate(MathHelper.lerp(ticks, 0.1f, 0.124f), -0.068f, MathHelper.lerp(ticks, 0.108f, 0.284f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 4:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 5:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 6:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 7:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 8:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 9:
				stack.translate(MathHelper.lerp(ticks, 0.124f, 0.1f), -0.068f, MathHelper.lerp(ticks, 0.284f, 0.108f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 10:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 11:
				stack.translate(MathHelper.lerp(ticks, 0.1f, 0), MathHelper.lerp(ticks, -0.068f, 0),
						MathHelper.lerp(ticks, 0.108f, 0));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 10, 0), MathHelper.rotLerp(ticks, 6, 0), true));
				break;
			}
		} else if (bulletsleft == 3) {
			switch (stage) {
			case 0:
				stack.translate(MathHelper.lerp(ticks, 0, 0.1f), MathHelper.lerp(ticks, 0, -0.068f),
						MathHelper.lerp(ticks, 0, 0.108f));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 0, 10), MathHelper.rotLerp(ticks, 0, 6), true));
				break;
			case 1:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 2:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, 0.1f, 0.124f), -0.068f, MathHelper.lerp(ticks, 0.108f, 0.284f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 4:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 5:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 6:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 7:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 8:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 9:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 10:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 11:
				stack.translate(MathHelper.lerp(ticks, 0.124f, 0.1f), -0.068f, MathHelper.lerp(ticks, 0.284f, 0.108f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 12:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 13:
				stack.translate(MathHelper.lerp(ticks, 0.1f, 0), MathHelper.lerp(ticks, -0.068f, 0),
						MathHelper.lerp(ticks, 0.108f, 0));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 10, 0), MathHelper.rotLerp(ticks, 6, 0), true));
				break;
			}
		} else if (bulletsleft == 2) {
			switch (stage) {
			case 0:
				stack.translate(MathHelper.lerp(ticks, 0, 0.1f), MathHelper.lerp(ticks, 0, -0.068f),
						MathHelper.lerp(ticks, 0, 0.108f));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 0, 10), MathHelper.rotLerp(ticks, 0, 6), true));
				break;
			case 1:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 2:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, 0.1f, 0.124f), -0.068f, MathHelper.lerp(ticks, 0.108f, 0.284f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 4:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 5:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 6:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 7:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 8:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 9:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 10:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 11:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 12:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 13:
				stack.translate(MathHelper.lerp(ticks, 0.124f, 0.1f), -0.068f, MathHelper.lerp(ticks, 0.284f, 0.108f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 14:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 15:
				stack.translate(MathHelper.lerp(ticks, 0.1f, 0), MathHelper.lerp(ticks, -0.068f, 0),
						MathHelper.lerp(ticks, 0.108f, 0));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 10, 0), MathHelper.rotLerp(ticks, 6, 0), true));
				break;
			}
		} else if (bulletsleft == 1) {
			switch (stage) {
			case 0:
				stack.translate(MathHelper.lerp(ticks, 0, 0.1f), MathHelper.lerp(ticks, 0, -0.068f),
						MathHelper.lerp(ticks, 0, 0.108f));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 0, 10), MathHelper.rotLerp(ticks, 0, 6), true));
				break;
			case 1:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 2:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, 0.1f, 0.124f), -0.068f, MathHelper.lerp(ticks, 0.108f, 0.284f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 4:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 5:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 6:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 7:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 8:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 9:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 10:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 11:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 12:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 13:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 14:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 15:
				stack.translate(MathHelper.lerp(ticks, 0.124f, 0.1f), -0.068f, MathHelper.lerp(ticks, 0.284f, 0.108f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 16:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 17:
				stack.translate(MathHelper.lerp(ticks, 0.1f, 0), MathHelper.lerp(ticks, -0.068f, 0),
						MathHelper.lerp(ticks, 0.108f, 0));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 10, 0), MathHelper.rotLerp(ticks, 6, 0), true));
				break;
			}
		} else if (bulletsleft == 0) {

			switch (stage) {
			case 0:
				stack.translate(MathHelper.lerp(ticks, 0, 0.1f), MathHelper.lerp(ticks, 0, -0.068f),
						MathHelper.lerp(ticks, 0, 0.108f));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 0, 10), MathHelper.rotLerp(ticks, 0, 6), true));
				break;
			case 1:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 2:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, 0.1f, 0.124f), -0.068f, MathHelper.lerp(ticks, 0.108f, 0.284f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 4:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 5:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 6:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 7:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 8:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 9:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 10:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 11:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 12:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 13:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 14:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 15:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 16:
				stack.translate(0.124f, -0.068f, 0.284f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 17:
				stack.translate(MathHelper.lerp(ticks, 0.124f, 0.1f), -0.068f, MathHelper.lerp(ticks, 0.284f, 0.108f));
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 18:
				stack.translate(0.1f, -0.068f, 0.108f);
				stack.mulPose(new Quaternion(0, 10, 6, true));
				break;
			case 19:
				stack.translate(MathHelper.lerp(ticks, 0.1f, 0), MathHelper.lerp(ticks, -0.068f, 0),
						MathHelper.lerp(ticks, 0.108f, 0));
				stack.mulPose(
						new Quaternion(0, MathHelper.rotLerp(ticks, 10, 0), MathHelper.rotLerp(ticks, 6, 0), true));
				break;
			}
		}
	}

	@Override
	public void animLeftArm(MatrixStack stack, int stage, float ticks) {
		super.animLeftArm(stack, stage, ticks);
		if (stage == 0) {
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -2), MathHelper.rotLerp(ticks, 0, 6), 0, true));
		} else if (stage == animsize) {
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -2, 0), MathHelper.rotLerp(ticks, 6, 0), 0, true));
		} else {
			stack.mulPose(new Quaternion(-2, 6, 0, true));
		}
	}

	@Override
	public void animRightArm(MatrixStack stack, int stage, float ticks) {
		super.animRightArm(stack, stage, ticks);
		if (bulletsleft == 4) {
			switch (stage) {
			case 1:
				stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, -8), 0, true));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, 0, 0.672f), MathHelper.lerp(ticks, 0, 0.112f),
						MathHelper.lerp(ticks, 0, 0.588f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, 12), MathHelper.rotLerp(ticks, -8, 10), 0, true));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0.708f), 0.112f, MathHelper.lerp(ticks, 0.588f, 1.036f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 4:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.948f), MathHelper.lerp(ticks, 0.112f, -0.208f),
						MathHelper.lerp(ticks, 1.036f, 1.74f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 5:
				stack.translate(MathHelper.lerp(ticks, 0.948f, 1.168f), MathHelper.lerp(ticks, -0.208f, 0.476f),
						MathHelper.lerp(ticks, 1.74f, 0.184f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 8), MathHelper.rotLerp(ticks, 10, 22), 0, true));
				break;
			case 6:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 7:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 8:
				stack.translate(MathHelper.lerp(ticks, 1.168f, 0.708f), MathHelper.lerp(ticks, 0.476f, 0.112f),
						MathHelper.lerp(ticks, 0.184f, 1.036f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 8, 12), MathHelper.rotLerp(ticks, 22, 10), 0, true));
				break;
			case 9:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.672f), 0.112f, MathHelper.lerp(ticks, 1.036f, 0.588f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 10:
				stack.translate(0.672f, 0.112f, 0.588f);
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 2), MathHelper.rotLerp(ticks, 10, 6), 0, true));
				break;
			case 11:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0), MathHelper.lerp(ticks, 0.112f, 0),
						MathHelper.lerp(ticks, 0.588f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 2, 0), MathHelper.rotLerp(ticks, 6, 0), 0, true));
				break;
			}
		} else if (bulletsleft == 3) {
			switch (stage) {
			case 1:
				stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, -8), 0, true));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, 0, 0.672f), MathHelper.lerp(ticks, 0, 0.112f),
						MathHelper.lerp(ticks, 0, 0.588f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, 12), MathHelper.rotLerp(ticks, -8, 10), 0, true));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0.708f), 0.112f, MathHelper.lerp(ticks, 0.588f, 1.036f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 4:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.948f), MathHelper.lerp(ticks, 0.112f, -0.208f),
						MathHelper.lerp(ticks, 1.036f, 1.74f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 5:
				stack.translate(MathHelper.lerp(ticks, 0.948f, 1.168f), MathHelper.lerp(ticks, -0.208f, 0.476f),
						MathHelper.lerp(ticks, 1.74f, 0.184f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 8), MathHelper.rotLerp(ticks, 10, 22), 0, true));
				break;
			case 6:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 7:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 8:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 9:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 10:
				stack.translate(MathHelper.lerp(ticks, 1.168f, 0.708f), MathHelper.lerp(ticks, 0.476f, 0.112f),
						MathHelper.lerp(ticks, 0.184f, 1.036f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 8, 12), MathHelper.rotLerp(ticks, 22, 10), 0, true));
				break;
			case 11:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.672f), 0.112f, MathHelper.lerp(ticks, 1.036f, 0.588f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 12:
				stack.translate(0.672f, 0.112f, 0.588f);
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 2), MathHelper.rotLerp(ticks, 10, 6), 0, true));
				break;
			case 13:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0), MathHelper.lerp(ticks, 0.112f, 0),
						MathHelper.lerp(ticks, 0.588f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 2, 0), MathHelper.rotLerp(ticks, 6, 0), 0, true));
				break;
			}
		} else if (bulletsleft == 2) {
			switch (stage) {
			case 1:
				stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, -8), 0, true));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, 0, 0.672f), MathHelper.lerp(ticks, 0, 0.112f),
						MathHelper.lerp(ticks, 0, 0.588f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, 12), MathHelper.rotLerp(ticks, -8, 10), 0, true));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0.708f), 0.112f, MathHelper.lerp(ticks, 0.588f, 1.036f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 4:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.948f), MathHelper.lerp(ticks, 0.112f, -0.208f),
						MathHelper.lerp(ticks, 1.036f, 1.74f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 5:
				stack.translate(MathHelper.lerp(ticks, 0.948f, 1.168f), MathHelper.lerp(ticks, -0.208f, 0.476f),
						MathHelper.lerp(ticks, 1.74f, 0.184f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 8), MathHelper.rotLerp(ticks, 10, 22), 0, true));
				break;
			case 6:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 7:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 8:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 9:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 10:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 11:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 12:
				stack.translate(MathHelper.lerp(ticks, 1.168f, 0.708f), MathHelper.lerp(ticks, 0.476f, 0.112f),
						MathHelper.lerp(ticks, 0.184f, 1.036f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 8, 12), MathHelper.rotLerp(ticks, 22, 10), 0, true));
				break;
			case 13:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.672f), 0.112f, MathHelper.lerp(ticks, 1.036f, 0.588f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 14:
				stack.translate(0.672f, 0.112f, 0.588f);
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 2), MathHelper.rotLerp(ticks, 10, 6), 0, true));
				break;
			case 15:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0), MathHelper.lerp(ticks, 0.112f, 0),
						MathHelper.lerp(ticks, 0.588f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 2, 0), MathHelper.rotLerp(ticks, 6, 0), 0, true));
				break;
			}
		} else if (bulletsleft == 1) {
			switch (stage) {
			case 1:
				stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, -8), 0, true));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, 0, 0.672f), MathHelper.lerp(ticks, 0, 0.112f),
						MathHelper.lerp(ticks, 0, 0.588f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, 12), MathHelper.rotLerp(ticks, -8, 10), 0, true));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0.708f), 0.112f, MathHelper.lerp(ticks, 0.588f, 1.036f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 4:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.948f), MathHelper.lerp(ticks, 0.112f, -0.208f),
						MathHelper.lerp(ticks, 1.036f, 1.74f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 5:
				stack.translate(MathHelper.lerp(ticks, 0.948f, 1.168f), MathHelper.lerp(ticks, -0.208f, 0.476f),
						MathHelper.lerp(ticks, 1.74f, 0.184f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 8), MathHelper.rotLerp(ticks, 10, 22), 0, true));
				break;
			case 6:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 7:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 8:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 9:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 10:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 11:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 12:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 13:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 14:
				stack.translate(MathHelper.lerp(ticks, 1.168f, 0.708f), MathHelper.lerp(ticks, 0.476f, 0.112f),
						MathHelper.lerp(ticks, 0.184f, 1.036f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 8, 12), MathHelper.rotLerp(ticks, 22, 10), 0, true));
				break;
			case 15:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.672f), 0.112f, MathHelper.lerp(ticks, 1.036f, 0.588f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 16:
				stack.translate(0.672f, 0.112f, 0.588f);
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 2), MathHelper.rotLerp(ticks, 10, 6), 0, true));
				break;
			case 17:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0), MathHelper.lerp(ticks, 0.112f, 0),
						MathHelper.lerp(ticks, 0.588f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 2, 0), MathHelper.rotLerp(ticks, 6, 0), 0, true));
				break;
			}
		} else if (bulletsleft == 0) {

			switch (stage) {
			case 1:
				stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, -8), 0, true));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, 0, 0.672f), MathHelper.lerp(ticks, 0, 0.112f),
						MathHelper.lerp(ticks, 0, 0.588f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, 12), MathHelper.rotLerp(ticks, -8, 10), 0, true));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0.708f), 0.112f, MathHelper.lerp(ticks, 0.588f, 1.036f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 4:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.948f), MathHelper.lerp(ticks, 0.112f, -0.208f),
						MathHelper.lerp(ticks, 1.036f, 1.74f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 5:
				stack.translate(MathHelper.lerp(ticks, 0.948f, 1.168f), MathHelper.lerp(ticks, -0.208f, 0.476f),
						MathHelper.lerp(ticks, 1.74f, 0.184f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 8), MathHelper.rotLerp(ticks, 10, 22), 0, true));
				break;
			case 6:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 7:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 8:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 9:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 10:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 11:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 12:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 13:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 14:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 8, 4), 22, 0, true));
				break;
			case 15:
				stack.translate(1.168f, 0.476f, 0.184f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 4, 8), 22, 0, true));
				break;
			case 16:
				stack.translate(MathHelper.lerp(ticks, 1.168f, 0.708f), MathHelper.lerp(ticks, 0.476f, 0.112f),
						MathHelper.lerp(ticks, 0.184f, 1.036f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 8, 12), MathHelper.rotLerp(ticks, 22, 10), 0, true));
				break;
			case 17:
				stack.translate(MathHelper.lerp(ticks, 0.708f, 0.672f), 0.112f, MathHelper.lerp(ticks, 1.036f, 0.588f));
				stack.mulPose(new Quaternion(12, 10, 0, true));
				break;
			case 18:
				stack.translate(0.672f, 0.112f, 0.588f);
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 12, 2), MathHelper.rotLerp(ticks, 10, 6), 0, true));
				break;
			case 19:
				stack.translate(MathHelper.lerp(ticks, 0.672f, 0), MathHelper.lerp(ticks, 0.112f, 0),
						MathHelper.lerp(ticks, 0.588f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 2, 0), MathHelper.rotLerp(ticks, 6, 0), 0, true));
				break;
			}
		}
	}

	@Override
	public void animScope(MatrixStack stack, int stage, float ticks) {
		super.animScope(stack, stage, ticks);
		if (stage == 0) {
			stack.translate(MathHelper.lerp(ticks, 0, -0.02f), MathHelper.lerp(ticks, 0, 0.072f),
					MathHelper.lerp(ticks, 0, -0.14f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 0, 10), MathHelper.rotLerp(ticks, 0, 6), true));
		} else if (stage == animsize) {
			stack.translate(MathHelper.lerp(ticks, 0, -0.02f), MathHelper.lerp(ticks, 0.072f, 0),
					MathHelper.lerp(ticks, -0.14f, 0));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(ticks, 10, 0), MathHelper.rotLerp(ticks, 6, 0), true));
		} else {
			stack.translate(-0.02f, 0.072f, -0.14f);
			stack.mulPose(new Quaternion(0, 10, 6, true));
		}
	}

	/////////// aiming

	@Override
	public void animGunAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animGunAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.42f), MathHelper.lerp(aimprogress, 0, 0.016f),
					MathHelper.lerp(aimprogress, 0, 0.192f));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.42f), MathHelper.lerp(aimprogress, 0, 0.016f),
					MathHelper.lerp(aimprogress, 0, 0.192f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -4), 0, 0, true));
		}
	}

	@Override
	public void animHammerAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animHammerAim(stack, aimprogress, scope);
		stack.translate(MathHelper.lerp(aimprogress, 0, -0.42f), MathHelper.lerp(aimprogress, 0, 0.032f),
				MathHelper.lerp(aimprogress, 0, 0.18f));
	}

	@Override
	public void animLeftHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animLeftHandAim(stack, aimprogress, scope);
		stack.translate(MathHelper.lerp(aimprogress, 0, -0.644f), MathHelper.lerp(aimprogress, 0, -0.048f), 0);
		stack.mulPose(new Quaternion(0, MathHelper.rotLerp(aimprogress, 0, 4), 0, true));
	}

	@Override
	public void animRightHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animRightHandAim(stack, aimprogress, scope);
		stack.translate(MathHelper.lerp(aimprogress, 0, -0.264f), MathHelper.lerp(aimprogress, 0, -0.024f), 0);
		stack.mulPose(
				new Quaternion(MathHelper.rotLerp(aimprogress, 0, 4), MathHelper.rotLerp(aimprogress, 0, 12), 0, true));
	}

	@Override
	public void animScopeAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animScopeAim(stack, aimprogress, scope);
		stack.translate(MathHelper.lerp(aimprogress, 0, -0.408f), MathHelper.lerp(aimprogress, 0, 0.004f),
				MathHelper.lerp(aimprogress, 0, 0.116f));
	}

	@Override
	public void animGunSprint(MatrixStack stack, float sprfloatprogress) {
		super.animGunSprint(stack, sprfloatprogress);
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprfloatprogress, 0, 8),
				MathHelper.rotLerp(sprfloatprogress, 0, 52), 0, true));
	}

	@Override
	public void animLeftHandSprint(MatrixStack stack, float sprfloatprogress) {
		super.animLeftHandSprint(stack, sprfloatprogress);
		stack.translate(0, MathHelper.lerp(sprfloatprogress, 0, -1.084f), MathHelper.lerp(sprfloatprogress, 0, 1.116f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprfloatprogress, 0, 14),
				MathHelper.rotLerp(sprfloatprogress, 0, 16), 0, true));
	}

	@Override
	public void animRightHandSprint(MatrixStack stack, float sprfloatprogress) {
		super.animRightHandSprint(stack, sprfloatprogress);
		stack.translate(MathHelper.lerp(sprfloatprogress, 0, 1.944f), MathHelper.lerp(sprfloatprogress, 0, -1.655f),
				MathHelper.lerp(sprfloatprogress, 0, 0.784f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprfloatprogress, 0, 24),
				MathHelper.rotLerp(sprfloatprogress, 0, 40), 0, true));
	}

	@Override
	public void animHammerSprint(MatrixStack stack, float sprfloatprogress) {
		super.animHammerSprint(stack, sprfloatprogress);
		stack.translate(MathHelper.lerp(sprfloatprogress, 0, 0.324f), MathHelper.lerp(sprfloatprogress, 0, -0.1f),
				MathHelper.lerp(sprfloatprogress, 0, 0.46f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprfloatprogress, 0, 8),
				MathHelper.rotLerp(sprfloatprogress, 0, 52), 0, true));
	}

	@Override
	public void animScopeSprint(MatrixStack stack, float sprfloatprogress) {
		super.animScopeSprint(stack, sprfloatprogress);
		stack.translate(MathHelper.lerp(sprfloatprogress, 0, 0.096f), 0, MathHelper.lerp(sprfloatprogress, 0, -0.1f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprfloatprogress, 0, 8),
				MathHelper.rotLerp(sprfloatprogress, 0, 52), 0, true));
	}

	@Override
	public void animShootHammer(MatrixStack stack, int shootticks) {
		super.animShootHammer(stack, shootticks);

		float part = 30 / 6.0f;

		if (shootticks >= part * 5) {

		} else if (shootticks >= part * 4) {// 3

		} else if (shootticks >= part * 3) {// 3
			float stc = (shootticks - (part * 3)) / part;
			stack.translate(0, 0, MathHelper.lerp(stc, 0.172f, 0));

		} else if (shootticks >= part * 2) {// 2
			float stc = (shootticks - (part * 2)) / part;
			stack.translate(0, 0, MathHelper.lerp(stc, 0, 0.172f));
		} else if (shootticks >= part) {// 3

		} else {

		}
	}

	@Override
	public void animShootRightArm(MatrixStack stack, int shootticks) {
		super.animShootRightArm(stack, shootticks);
		float part = 30 / 6.0f;

		if (shootticks >= part * 5) {// 6
			float stc = (shootticks - (part * 5)) / part;
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(stc, -6, 0), 0, true));

		} else if (shootticks >= part * 4) {// 5
			float stc = (shootticks - (part * 4)) / part;
			if (AnimationManager.aimprogress > 0) {
				stack.translate(MathHelper.lerp(stc, 0.024f, 0), 0, MathHelper.lerp(stc, 0.796f, 0));
			}
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 14, 0), -6, 0, true));

		} else if (shootticks >= part * 3) {// 4
			float stc = (shootticks - (part * 3)) / part;
			if (AnimationManager.aimprogress > 0) {
				stack.translate(0.024f, 0, 0.796f);
			}
			stack.translate(0, 0, MathHelper.lerp(stc, 0.656f, 0));
			stack.mulPose(new Quaternion(14, -6, 0, true));

		} else if (shootticks >= part * 2) {// 3
			float stc = (shootticks - (part * 2)) / part;
			if (AnimationManager.aimprogress > 0) {
				stack.translate(0.024f, 0, 0.796f);
			}
			stack.translate(0, 0, MathHelper.lerp(stc, 0, 0.656f));
			stack.mulPose(new Quaternion(14, -6, 0, true));

		} else if (shootticks >= part) {// 2
			float stc = (shootticks - part) / part;
			if (AnimationManager.aimprogress > 0) {
				stack.translate(MathHelper.lerp(stc, 0, 0.024f), 0, MathHelper.lerp(stc, 0, 0.796f));
			}
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 0, 14), -6, 0, true));

		} else {// 1
			float stc = shootticks / part;
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(stc, 0, -6), 0, true));

		}
	}

	@Override
	public void setBullets(int bullets, int bulletsleftn, int maxbullets) {

		if (bullets == maxbullets) {
			return;
		}
		if (bullets + bulletsleftn <= maxbullets) {
			animsize = 19 - ((maxbullets - bulletsleftn) * 2);
			bulletsleft = maxbullets - bulletsleftn;
			System.out.println(animsize);
		} else {
			int dif = maxbullets - bullets;
			bulletsleft = maxbullets - dif;
			animsize = 19 - ((maxbullets - dif) * 2);
			System.out.println(animsize);
		}
	}

	@Override
	public boolean hasCustomReload() {
		return true;
	}

	@Override
	public void specialReload(int stage) {
		PlayerEntity player = Minecraft.getInstance().player;
		PlayerInventory inv = player.inventory;
		ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
		int index = InventoryHelper.hasItem(inv, ItemRegistries.big_bullet.get());

		if (stage == 3) {
			player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
					SoundRegistries.KarkHammerBackSound.get(), SoundCategory.NEUTRAL, 10.0f, 1.0f);
			OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(SoundRegistries.KarkHammerBackSound.get(),
					player.getX(), player.getY(), player.getZ()));
		} else if (stage == animsize - 1) {
			player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
					SoundRegistries.KarkHammerFrontSound.get(), SoundCategory.NEUTRAL, 10.0f, 1.0f);
			OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(SoundRegistries.KarkHammerFrontSound.get(),
					player.getX(), player.getY(), player.getZ()));
		}
		switch (bulletsleft) {
		case 4:
			if (stage == 7) {
				if (stack.getItem() instanceof ItemGun) {
					if (index != -1) {
						player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
								SoundRegistries.LoadingBulletSound.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
						OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(
								SoundRegistries.LoadingBulletSound.get(), player.getX(), player.getY(), player.getZ()));
						stack.getOrCreateTag().putInt("bullets", stack.getOrCreateTag().getInt("bullets") + 1);
						OldGuns.channel.sendToServer(new BulletsMessage(stack.getOrCreateTag().getInt("bullets")));
						consumeifCreative(player, index);
					} else {
						bulletsleft = 5;
					}
				}
			}
			break;
		case 3:
			if (stage == 7 || stage == 9) {
				if (stack.getItem() instanceof ItemGun) {
					if (index != -1) {
						player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
								SoundRegistries.LoadingBulletSound.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
						OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(
								SoundRegistries.LoadingBulletSound.get(), player.getX(), player.getY(), player.getZ()));
						stack.getOrCreateTag().putInt("bullets", stack.getOrCreateTag().getInt("bullets") + 1);
						OldGuns.channel.sendToServer(new BulletsMessage(stack.getOrCreateTag().getInt("bullets")));
						consumeifCreative(player, index);
					} else {
						bulletsleft = 5;
					}
				}
			}
			break;
		case 2:
			if (stage == 7 || stage == 9 || stage == 10) {
				if (stack.getItem() instanceof ItemGun) {
					if (index != -1) {
						player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
								SoundRegistries.LoadingBulletSound.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
						OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(
								SoundRegistries.LoadingBulletSound.get(), player.getX(), player.getY(), player.getZ()));
						stack.getOrCreateTag().putInt("bullets", stack.getOrCreateTag().getInt("bullets") + 1);
						OldGuns.channel.sendToServer(new BulletsMessage(stack.getOrCreateTag().getInt("bullets")));
						consumeifCreative(player, index);
					} else {
						bulletsleft = 5;
					}
				}
			}
			break;
		case 1:
			if (stage == 7 || stage == 9 || stage == 11 || stage == 13) {
				if (stack.getItem() instanceof ItemGun) {
					if (index != -1) {
						player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
								SoundRegistries.LoadingBulletSound.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
						OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(
								SoundRegistries.LoadingBulletSound.get(), player.getX(), player.getY(), player.getZ()));
						stack.getOrCreateTag().putInt("bullets", stack.getOrCreateTag().getInt("bullets") + 1);
						OldGuns.channel.sendToServer(new BulletsMessage(stack.getOrCreateTag().getInt("bullets")));
						consumeifCreative(player, index);
					} else {
						bulletsleft = 5;
					}
				}
			}
			break;
		case 0:
			if (stage == 7 || stage == 9 || stage == 11 || stage == 13 || stage == 15) {
				if (stack.getItem() instanceof ItemGun) {
					if (index != -1) {
						player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
								SoundRegistries.LoadingBulletSound.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
						OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(
								SoundRegistries.LoadingBulletSound.get(), player.getX(), player.getY(), player.getZ()));
						stack.getOrCreateTag().putInt("bullets", stack.getOrCreateTag().getInt("bullets") + 1);
						OldGuns.channel.sendToServer(new BulletsMessage(stack.getOrCreateTag().getInt("bullets")));
						consumeifCreative(player, index);
					} else {
						bulletsleft = 5;
					}
				}
			}
			break;
		}
	}

}

/*
 * @Override public void animGunAim(MatrixStack stack, float aimprogress,
 * boolean scope) { super.animGunAim(stack, aimprogress, scope);
 * 
 * }
 * 
 * @Override public void animHammerAim(MatrixStack stack, float aimprogress,
 * boolean scope) { // TODO Auto-generated method stub
 * super.animHammerAim(stack, aimprogress, scope); }
 * 
 * @Override public void animLeftHandAim(MatrixStack stack, float aimprogress,
 * boolean scope) { // TODO Auto-generated method stub
 * super.animLeftHandAim(stack, aimprogress, scope); }
 * 
 * @Override public void animRightHandAim(MatrixStack stack, float aimprogress,
 * boolean scope) { // TODO Auto-generated method stub
 * super.animRightHandAim(stack, aimprogress, scope); }
 * 
 * @Override public void animScopeAim(MatrixStack stack, float aimprogress,
 * boolean scope) { // TODO Auto-generated method stub super.animScopeAim(stack,
 * aimprogress, scope); }
 * 
 * @Override public void animGun(MatrixStack stack, int stage, float ticks) { //
 * TODO Auto-generated method stub super.animGun(stack, stage, ticks); }
 * 
 * @Override public void animHammer(MatrixStack stack, int stage, float ticks) {
 * // TODO Auto-generated method stub super.animHammer(stack, stage, ticks); }
 * 
 * @Override public void animLeftArm(MatrixStack stack, int stage, float ticks)
 * { // TODO Auto-generated method stub super.animLeftArm(stack, stage, ticks);
 * }
 * 
 * @Override public void animRightArm(MatrixStack stack, int stage, float ticks)
 * { // TODO Auto-generated method stub super.animRightArm(stack, stage, ticks);
 * }
 * 
 * @Override public void animScope(MatrixStack stack, int stage, float ticks) {
 * // TODO Auto-generated method stub super.animScope(stack, stage, ticks); }
 * 
 * @Override public void animGunSprint(MatrixStack stack, float
 * sprfloatprogress) { // TODO Auto-generated method stub
 * super.animGunSprint(stack, sprfloatprogress); }
 * 
 * @Override public void animLeftHandSprint(MatrixStack stack, float
 * sprfloatprogress) { // TODO Auto-generated method stub
 * super.animLeftHandSprint(stack, sprfloatprogress); }
 * 
 * @Override public void animRightHandSprint(MatrixStack stack, float
 * sprfloatprogress) { // TODO Auto-generated method stub
 * super.animRightHandSprint(stack, sprfloatprogress); }
 * 
 * @Override public void animHammerSprint(MatrixStack stack, float
 * sprfloatprogress) { // TODO Auto-generated method stub
 * super.animHammerSprint(stack, sprfloatprogress); }
 * 
 * @Override public void animScopeSprint(MatrixStack stack, float
 * sprfloatprogress) { // TODO Auto-generated method stub
 * super.animScopeSprint(stack, sprfloatprogress); }
 */
