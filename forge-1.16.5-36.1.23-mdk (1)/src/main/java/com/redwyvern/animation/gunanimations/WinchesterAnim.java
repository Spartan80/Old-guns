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

public class WinchesterAnim extends GunAnimation {

	public WinchesterAnim() {

	}

	@Override
	public void animGun(MatrixStack stack, int stage, float gunticks) {
		super.animGun(stack, stage, gunticks);
		if (stage == 0) {
			stack.mulPose(new Quaternion(0, 0, MathHelper.rotLerp(gunticks, 0, -16), true));
		} else if (stage == animsize) {
			stack.mulPose(new Quaternion(0, 0, MathHelper.rotLerp(gunticks, -16, 0), true));
		} else {
			stack.mulPose(new Quaternion(0, 0, -16, true));
		}
	}

	@Override
	public void animLeftArm(MatrixStack stack, int stage, float ticks) {
		super.animLeftArm(stack, stage, ticks);
		if (bulletsleft == 5) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, -0.836f), 0, MathHelper.lerp(ticks, 0, 3.124f));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, -0.836f, -0.292f), MathHelper.lerp(ticks, 0, -0.36f),
						MathHelper.lerp(ticks, 3.124f, 0.836f));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, -0.292f, 0.004f), -0.36f, 0.836f);
				break;
			case 4:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 5:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 6:
				stack.translate(MathHelper.lerp(ticks, 0.004f, 0), MathHelper.lerp(ticks, -0.36f, 0),
						MathHelper.lerp(ticks, 0.836f, 0));
				break;
			}

		} else if (bulletsleft == 4) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, -0.836f), 0, MathHelper.lerp(ticks, 0, 3.124f));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, -0.836f, -0.292f), MathHelper.lerp(ticks, 0, -0.36f),
						MathHelper.lerp(ticks, 3.124f, 0.836f));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, -0.292f, 0.004f), -0.36f, 0.836f);
				break;
			case 4:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 5:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 6:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 7:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 8:
				stack.translate(MathHelper.lerp(ticks, 0.004f, 0), MathHelper.lerp(ticks, -0.36f, 0),
						MathHelper.lerp(ticks, 0.836f, 0));
				break;
			}

		} else if (bulletsleft == 3) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, -0.836f), 0, MathHelper.lerp(ticks, 0, 3.124f));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, -0.836f, -0.292f), MathHelper.lerp(ticks, 0, -0.36f),
						MathHelper.lerp(ticks, 3.124f, 0.836f));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, -0.292f, 0.004f), -0.36f, 0.836f);
				break;
			case 4:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 5:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 6:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 7:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 8:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 9:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 10:
				stack.translate(MathHelper.lerp(ticks, 0.004f, 0), MathHelper.lerp(ticks, -0.36f, 0),
						MathHelper.lerp(ticks, 0.836f, 0));
				break;
			}

		} else if (bulletsleft == 2) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, -0.836f), 0, MathHelper.lerp(ticks, 0, 3.124f));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, -0.836f, -0.292f), MathHelper.lerp(ticks, 0, -0.36f),
						MathHelper.lerp(ticks, 3.124f, 0.836f));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, -0.292f, 0.004f), -0.36f, 0.836f);
				break;
			case 4:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 5:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 6:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 7:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 8:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 9:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 10:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 11:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 12:
				stack.translate(MathHelper.lerp(ticks, 0.004f, 0), MathHelper.lerp(ticks, -0.36f, 0),
						MathHelper.lerp(ticks, 0.836f, 0));
				break;
			}

		} else if (bulletsleft == 1) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, -0.836f), 0, MathHelper.lerp(ticks, 0, 3.124f));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, -0.836f, -0.292f), MathHelper.lerp(ticks, 0, -0.36f),
						MathHelper.lerp(ticks, 3.124f, 0.836f));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, -0.292f, 0.004f), -0.36f, 0.836f);
				break;
			case 4:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 5:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 6:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 7:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 8:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 9:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 10:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 11:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 12:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 13:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 14:
				stack.translate(MathHelper.lerp(ticks, 0.004f, 0), MathHelper.lerp(ticks, -0.36f, 0),
						MathHelper.lerp(ticks, 0.836f, 0));
				break;
			}
		}

		else if (bulletsleft == 0) {
			switch (stage) {
			case 1:
				stack.translate(MathHelper.lerp(ticks, 0, -0.836f), 0, MathHelper.lerp(ticks, 0, 3.124f));
				break;
			case 2:
				stack.translate(MathHelper.lerp(ticks, -0.836f, -0.292f), MathHelper.lerp(ticks, 0, -0.36f),
						MathHelper.lerp(ticks, 3.124f, 0.836f));
				break;
			case 3:
				stack.translate(MathHelper.lerp(ticks, -0.292f, 0.004f), -0.36f, 0.836f);
				break;
			case 4:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 5:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 6:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 7:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 8:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 9:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 10:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 11:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 12:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 13:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 14:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.36f, -0.276f),
						MathHelper.lerp(ticks, 0.836f, 0.572f));
				break;
			case 15:
				stack.translate(0.004f, MathHelper.lerp(ticks, -0.276f, -0.36f),
						MathHelper.lerp(ticks, 0.572f, 0.836f));
				break;
			case 16:
				stack.translate(MathHelper.lerp(ticks, 0.004f, 0), MathHelper.lerp(ticks, -0.36f, 0),
						MathHelper.lerp(ticks, 0.836f, 0));
				break;
			}
		}
	}

	@Override
	public void animScope(MatrixStack stack, int stage, float ticks) {
		super.animScope(stack, stage, ticks);
		if (stage == 0) {
			stack.translate(MathHelper.lerp(ticks, 0, -0.036f), MathHelper.lerp(ticks, 0, -0.104f), 0);
			stack.mulPose(new Quaternion(0, 0, MathHelper.rotLerp(ticks, 0, -16), true));
		} else if (stage == animsize) {
			stack.translate(MathHelper.lerp(ticks, -0.036f, 0), MathHelper.lerp(ticks, -0.104f, 0), 0);
			stack.mulPose(new Quaternion(0, 0, MathHelper.rotLerp(ticks, -16, 0), true));
		} else {
			stack.translate(-0.036f, -0.104f, 0);
			stack.mulPose(new Quaternion(0, 0, -16, true));
		}

	}

	@Override
	public int getAnimSize() {
		return animsize;
	}

	@Override
	public boolean hasCustomReload() {
		return true;
	}

	@Override
	public void setBullets(int bullets, int bulletsleftn, int maxbullets) {
		System.out.println(maxbullets - bulletsleftn);

		if (bullets == maxbullets) {
			return;
		}
		if (bullets + bulletsleftn <= maxbullets) {
			bulletsleft = maxbullets - bulletsleftn;
			animsize = maxbullets + (((maxbullets - (maxbullets - bulletsleftn)) - 1) * 2);
			System.out.println(animsize);
		} else {
			int dif = maxbullets - bullets;
			bulletsleft = maxbullets - dif;
			animsize = maxbullets + (((maxbullets - (maxbullets - dif)) - 1) * 2);
			System.out.println(animsize);

		}
	}

	@Override
	public void animShootRightArm(MatrixStack stack, int shootticks) {
		super.animShootRightArm(stack, shootticks);
		float part = 8;

		if (shootticks >= part) {
			float stc = (shootticks - part) / part;
			stack.translate(0, MathHelper.lerp(stc, -0.16f, 0), MathHelper.lerp(stc, -0.78f, 0));

		} else {
			float stc = shootticks / part;
			stack.translate(0, MathHelper.lerp(stc, 0, -0.16f), MathHelper.lerp(stc, 0, -0.78f));
		}

	}

	@Override
	public void animLeftHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animLeftHandAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.9f), 0, 0);
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.81f), 0, 0);
		}
	}

	@Override
	public void animRightHandAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animRightHandAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, 0.14f), MathHelper.lerp(aimprogress, 0, 0.476f),
					MathHelper.lerp(aimprogress, 0, -0.216f));
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(aimprogress, 0, 22), 0, true));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.21f), MathHelper.lerp(aimprogress, 0, 0.21f), 0);
			stack.mulPose(new Quaternion(0, MathHelper.rotLerp(aimprogress, 0, 10), 0, true));
		}

	}

	@Override
	public void animGunAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animGunAim(stack, aimprogress, scope);
		if (!scope) {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.4f), MathHelper.lerp(aimprogress, 0, 0.12f), 0);
			stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -2), MathHelper.rotLerp(aimprogress, 0, -2),
					0, true));
		} else {
			stack.translate(MathHelper.lerp(aimprogress, 0, -0.4f), MathHelper.lerp(aimprogress, 0, 0.08f),
					MathHelper.lerp(aimprogress, 0, 0.39f));
			stack.mulPose(new Quaternion(MathHelper.rotLerp(aimprogress, 0, -4), MathHelper.rotLerp(aimprogress, 0, -2),
					0, true));
		}
	}

	@Override
	public void animScopeAim(MatrixStack stack, float aimprogress, boolean scope) {
		super.animScopeAim(stack, aimprogress, scope);

		stack.translate(MathHelper.lerp(aimprogress, 0, -0.41f), MathHelper.lerp(aimprogress, 0, 0.07f),
				MathHelper.lerp(aimprogress, 0, 0.25f));
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
	public void animScopeSprint(MatrixStack stack, float sprintprogress) {
		super.animScopeSprint(stack, sprintprogress);
		stack.translate(MathHelper.lerp(sprintprogress, 0, -0.57f), MathHelper.lerp(sprintprogress, 0, 0.03f),
				MathHelper.lerp(sprintprogress, 0, -0.18f));
		stack.mulPose(new Quaternion(MathHelper.rotLerp(sprintprogress, 0, -2),
				MathHelper.rotLerp(sprintprogress, 0, 46), 0, true));
	}

	@Override
	public void specialReload(int stage) {
		PlayerEntity player = Minecraft.getInstance().player;
		PlayerInventory inv = player.inventory;
		ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
		int index = InventoryHelper.hasItem(inv, ItemRegistries.big_bullet.get());
		if (bulletsleft == 5) {
			if (stage == 4) {
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
						bulletsleft = 6;
					}
				}
			}

		} else if (bulletsleft == 4) {
			if (stage == 4 || stage == 6) {
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
						bulletsleft = 6;
					}
				}
			}

		} else if (bulletsleft == 3) {
			if (stage == 4 || stage == 6 || stage == 8) {
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
						bulletsleft = 6;
					}
				}
			}
		} else if (bulletsleft == 2) {
			if (stage == 4 || stage == 6 || stage == 8 || stage == 10) {
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
						bulletsleft = 6;
					}
				}
			}

		} else if (bulletsleft == 1) {
			if (stage == 4 || stage == 6 || stage == 8 || stage == 10 || stage == 12) {
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
						bulletsleft = 6;
					}
				}
			}

		} else if (bulletsleft == 0) {
			if (stage == 4 || stage == 6 || stage == 8 || stage == 10 || stage == 12 || stage == 14) {
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
						bulletsleft = 6;
					}
				}
			}
		}

	}

	@Override
	public int getReloadTimeForStage(int stage) {
		return 8;
	}

}
