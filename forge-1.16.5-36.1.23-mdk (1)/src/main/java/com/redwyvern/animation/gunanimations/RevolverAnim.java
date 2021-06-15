package com.redwyvern.animation.gunanimations;

import com.mojang.blaze3d.matrix.MatrixStack;
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

public class RevolverAnim extends GunAnimation {

	public RevolverAnim() {

	}

	@Override
	public void animGun(MatrixStack stack, int stage, float ticks) {
		super.animGun(stack, stage, ticks);
		if (stage == 1 && ticks == 0) {
			PlayerEntity player = Minecraft.getInstance().player;
			player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
					SoundRegistries.RevolverSpinSound.get(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
			OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(SoundRegistries.RevolverSpinSound.get(),
					player.getX(), player.getY(), player.getZ()));
		}
		if (stage == 0) {
			stack.translate(0, MathHelper.lerp(ticks, 0, -0.38f), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -48), 0, 0, true));
		} else if (stage == animsize) {
			stack.translate(0, MathHelper.lerp(ticks, -0.38f, 0), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -48, 0), 0, 0, true));
		} else {
			stack.translate(0, -0.38f, 0);
			stack.mulPose(new Quaternion(-48, 0, 0, true));
		}

	}

	@Override
	public int getAnimSize() {
		// TODO Auto-generated method stub
		return animsize;
	}

	@Override
	public void setBullets(int gunbullets, int bulletsleftn, int maxbullets) {

		if (gunbullets == maxbullets) {
			return;
		}
		if (gunbullets + bulletsleftn <= maxbullets) {
			animsize = maxbullets + ((((maxbullets - (maxbullets - bulletsleftn)) - 1) * 2) - 2);
			bulletsleft = maxbullets - bulletsleftn;
			System.out.println(animsize);
		} else {
			int dif = maxbullets - gunbullets;
			bulletsleft = maxbullets - dif;
			animsize = maxbullets + ((((maxbullets - (maxbullets - dif)) - 1) * 2) - 2);
			System.out.println(animsize);
		}

	}

	@Override
	public void animLeftArm(MatrixStack stack, int stage, float ticks) {
		super.animLeftArm(stack, stage, ticks);
		if (bulletsleft == 5) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, 0.968f), MathHelper.lerp(ticks, 0, -0.512f),
						MathHelper.lerp(ticks, 0, -2.484f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, -32), MathHelper.lerp(ticks, 0, -14), 0, true));
				break;
			case 2:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 3:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 4:
				stack.translate(MathHelper.lerp(ticks, 0.968f, 0), MathHelper.lerp(ticks, -0.512f, 0),
						MathHelper.lerp(ticks, -2.484f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, -32, 0), MathHelper.lerp(ticks, -14, 0), 0, true));
				break;
			}

		} else if (bulletsleft == 4) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, 0.968f), MathHelper.lerp(ticks, 0, -0.512f),
						MathHelper.lerp(ticks, 0, -2.484f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, -32), MathHelper.lerp(ticks, 0, -14), 0, true));
				break;
			case 2:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 3:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 4:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 5:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 6:
				stack.translate(MathHelper.lerp(ticks, 0.968f, 0), MathHelper.lerp(ticks, -0.512f, 0),
						MathHelper.lerp(ticks, -2.484f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, -32, 0), MathHelper.lerp(ticks, -14, 0), 0, true));
				break;
			}

		} else if (bulletsleft == 3) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, 0.968f), MathHelper.lerp(ticks, 0, -0.512f),
						MathHelper.lerp(ticks, 0, -2.484f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, -32), MathHelper.lerp(ticks, 0, -14), 0, true));
				break;
			case 2:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 3:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 4:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 5:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 6:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 7:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 8:
				stack.translate(MathHelper.lerp(ticks, 0.968f, 0), MathHelper.lerp(ticks, -0.512f, 0),
						MathHelper.lerp(ticks, -2.484f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, -32, 0), MathHelper.lerp(ticks, -14, 0), 0, true));
				break;
			}

		} else if (bulletsleft == 2) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, 0.968f), MathHelper.lerp(ticks, 0, -0.512f),
						MathHelper.lerp(ticks, 0, -2.484f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, -32), MathHelper.lerp(ticks, 0, -14), 0, true));
				break;
			case 2:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 3:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 4:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 5:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 6:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 7:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 8:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 9:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 10:
				stack.translate(MathHelper.lerp(ticks, 0.968f, 0), MathHelper.lerp(ticks, -0.512f, 0),
						MathHelper.lerp(ticks, -2.484f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, -32, 0), MathHelper.lerp(ticks, -14, 0), 0, true));
				break;

			}

		} else if (bulletsleft == 1) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, 0.968f), MathHelper.lerp(ticks, 0, -0.512f),
						MathHelper.lerp(ticks, 0, -2.484f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, -32), MathHelper.lerp(ticks, 0, -14), 0, true));
				break;
			case 2:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 3:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 4:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 5:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 6:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 7:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 8:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 9:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 10:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 11:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 12:
				stack.translate(MathHelper.lerp(ticks, 0.968f, 0), MathHelper.lerp(ticks, -0.512f, 0),
						MathHelper.lerp(ticks, -2.484f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, -32, 0), MathHelper.lerp(ticks, -14, 0), 0, true));
				break;
			}
		}

		else if (bulletsleft == 0) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, 0.968f), MathHelper.lerp(ticks, 0, -0.512f),
						MathHelper.lerp(ticks, 0, -2.484f));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, 0, -32), MathHelper.lerp(ticks, 0, -14), 0, true));
				break;
			case 2:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 3:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 4:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 5:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 6:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 7:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 8:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 9:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 10:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 11:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 12:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -32, -44), -14, 0, true));
				break;
			case 13:
				stack.translate(0.968f, -0.512f, -2.484f);
				stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -44, -32), -14, 0, true));
				break;
			case 14:
				stack.translate(MathHelper.lerp(ticks, 0.968f, 0), MathHelper.lerp(ticks, -0.512f, 0),
						MathHelper.lerp(ticks, -2.484f, 0));
				stack.mulPose(
						new Quaternion(MathHelper.rotLerp(ticks, -32, 0), MathHelper.lerp(ticks, -14, 0), 0, true));
				break;
			}
		}

	}

	@Override
	public void animRightArm(MatrixStack stack, int stage, float ticks) {
		super.animRightArm(stack, stage, ticks);
		if (stage == 0) {
			stack.translate(MathHelper.lerp(ticks, 0, -0.66f), MathHelper.lerp(ticks, 0, 0.788f),
					MathHelper.lerp(ticks, 0, -0.944f));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, 0, -36), MathHelper.rotLerp(ticks, 0, -10), 0, true));
		} else if (stage == animsize) {
			stack.translate(MathHelper.lerp(ticks, -0.66f, 0), MathHelper.lerp(ticks, 0.788f, 0),
					MathHelper.lerp(ticks, -0.944f, 0));
			stack.mulPose(
					new Quaternion(MathHelper.rotLerp(ticks, -36, 0), MathHelper.rotLerp(ticks, -10, 0), 0, true));
		} else {
			stack.translate(-0.66f, 0.788f, -0.944f);
			stack.mulPose(new Quaternion(-36, -10, 0, true));
		}

	}

	@Override
	public void animHammer(MatrixStack stack, int stage, float ticks) {
		super.animHammer(stack, stage, ticks);
		if (stage == 0) {
			stack.translate(MathHelper.lerp(ticks, 0, 0.08f), MathHelper.lerp(ticks, 0, -0.48f), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -48), 0, 0, true));
		} else if (stage == animsize) {
			stack.translate(MathHelper.lerp(ticks, 0.08f, 00), MathHelper.lerp(ticks, -0.48f, 0), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -48, 0), 0, 0, true));
		} else {
			stack.translate(0.08f, -0.48f, 0);
			stack.mulPose(new Quaternion(-48, 0, 0, true));
		}

	}

	@Override
	public void animScope(MatrixStack stack, int stage, float ticks) {
		super.animScope(stack, stage, ticks);
		if (stage == 0) {
			stack.translate(0, MathHelper.lerp(ticks, 0, -1.272f), MathHelper.lerp(ticks, 0, 0.08f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, 0, -48), 0, 0, true));
		} else if (stage == animsize) {
			stack.translate(0, MathHelper.lerp(ticks, -1.272f, 0), MathHelper.lerp(ticks, 0.08f, 0));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(ticks, -48, 0), 0, 0, true));
		} else {
			stack.translate(0, -1.272f, 0.08f);
			stack.mulPose(new Quaternion(-48, 0, 0, true));
		}
	}

	@Override
	public void animShootHammer(MatrixStack stack, int shootticks) {
		super.animShootHammer(stack, shootticks);

		if (shootticks >= 9) {
			float stc = (shootticks - 9) / 9;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, -2, 0), 0, 0, true));
		} else {
			float stc = shootticks / 9;
			stack.mulPose(new Quaternion(MathHelper.rotLerp(stc, 0, -2), 0, 0, true));
		}
	}

	@Override
	public void animRightHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animRightHandAim(stack, aimprogress, scope);
		stack.translate(MathHelper.lerp(aimprogress, 0, -1.036f), MathHelper.lerp(aimprogress, 0, 0.844f),
				MathHelper.lerp(aimprogress, 0, 0.04f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -12), 0, 0, true));
	}

	@Override
	public void animGunAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animGunAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.5f), MathHelper.lerp(aimprogress, 0, 0.18f),
					MathHelper.lerp(aimprogress, 0, 0.08f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -8), 0, 0, true));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.5f), MathHelper.lerp(aimprogress, 0, 0.064f),
					MathHelper.lerp(aimprogress, 0, 0.064f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -8), 0, 0, true));
		}
	}

	@Override
	public void animHammerAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animHammerAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.504f), MathHelper.lerp(aimprogress, 0, 0.172f),
					MathHelper.lerp(aimprogress, 0, 0.096f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -4), 0, 0, true));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.506f), MathHelper.lerp(aimprogress, 0, 0.048f),
					MathHelper.lerp(aimprogress, 0, 0.096f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -4), 0, 0, true));
		}
	}

	@Override
	public void animScopeAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animScopeAim(stack, aimprogress, scope);
		stack.translate(MathHelper.lerp(aimprogress, 0, -0.5f), MathHelper.lerp(aimprogress, 0, -0.024f),
				MathHelper.lerp(aimprogress, 0, 0.012f));
		stack.mulPose(new Quaternion(MathHelper.lerp(aimprogress, 0, -4), 0, 0, true));
	}

	@Override
	public void animRightHandSprint(MatrixStack stack, float sprfloatprogress) {
		super.animRightHandSprint(stack, sprfloatprogress);
		stack.translate(MathHelper.lerp(sprfloatprogress, 0, -0.66f), MathHelper.lerp(sprfloatprogress, 0, 0.788f),
				MathHelper.lerp(sprfloatprogress, 0, -0.944f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprfloatprogress, 0, -36),
				MathHelper.rotLerp(sprfloatprogress, 0, -10), 0, true));

	}

	@Override
	public void animGunSprint(MatrixStack stack, float sprfloatprogress) {
		super.animGunSprint(stack, sprfloatprogress);
		stack.translate(0, MathHelper.lerp(sprfloatprogress, 0, -0.38f), 0);
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprfloatprogress, 0, -48), 0, 0, true));

	}

	@Override
	public void animHammerSprint(MatrixStack stack, float sprfloatprogress) {
		super.animHammerSprint(stack, sprfloatprogress);
		stack.translate(MathHelper.lerp(sprfloatprogress, 0, 0.08f), MathHelper.lerp(sprfloatprogress, 0, -0.48f), 0);
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprfloatprogress, 0, -48), 0, 0, true));

	}

	@Override
	public void animScopeSprint(MatrixStack stack, float sprfloatprogress) {
		super.animScopeSprint(stack, sprfloatprogress);
		stack.translate(0, MathHelper.lerp(sprfloatprogress, 0, -1.268f), 0);
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprfloatprogress, 0, -44), 0, 0, true));
	}

	@Override
	public void specialReload(int stage) {
		PlayerEntity player = Minecraft.getInstance().player;
		PlayerInventory inv = player.inventory;
		ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
		int index = InventoryHelper.hasItem(inv, ItemRegistries.big_bullet.get());
		if (stage == 1) {

			player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
					SoundRegistries.RevolverBulletsOutSound.get(), SoundCategory.NEUTRAL, 10.0f, 1.0f);
			OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(SoundRegistries.RevolverBulletsOutSound.get(),
					player.getX(), player.getY(), player.getZ()));
		}
		if (stage != animsize) {
			if (stage % 2 == 0) {
				if (stack.getItem() instanceof ItemGun) {
					if (index != -1) {
						player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
								SoundRegistries.RevolverBulletsInsideSound.get(), SoundCategory.NEUTRAL, 10.0f, 1.0f);
						OldGuns.channel.sendToServer(
								new PlaySoundOnServerMessage(SoundRegistries.RevolverBulletsInsideSound.get(),
										player.getX(), player.getY(), player.getZ()));
						stack.getOrCreateTag().putInt("bullets", stack.getOrCreateTag().getInt("bullets") + 1);
						OldGuns.channel.sendToServer(new BulletsMessage(stack.getOrCreateTag().getInt("bullets")));
						inv.removeItem(index, 1);
						consumeifCreative(player, index);
					} else {
						bulletsleft = 6;
					}
				}
			}
		}

	}

	@Override
	public boolean hasCustomReload() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getReloadTimeForStage(int stage) {
		// TODO Auto-generated method stub
		return 8;
	}

}
