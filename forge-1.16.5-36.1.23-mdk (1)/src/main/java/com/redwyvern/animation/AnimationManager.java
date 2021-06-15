package com.redwyvern.animation;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.redwyvern.animation.gunanimations.Kar98KAnim;
import com.redwyvern.animation.gunanimations.PiratePistolAnim;
import com.redwyvern.animation.gunanimations.PirateRifleAnim;
import com.redwyvern.animation.gunanimations.RevolverAnim;
import com.redwyvern.animation.gunanimations.TrabucoAnim;
import com.redwyvern.animation.gunanimations.WinchesterAnim;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.network.BulletsMessage;
import com.redwyvern.network.ConsumeStackMessage;
import com.redwyvern.network.PlaySoundOnServerMessage;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.util.InventoryHelper;
import com.redwyvern.util.MathUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class AnimationManager {

	public static int MAXANIMTICKS;
	private float gunticks, gunticksconv, recoilticks;
	public int shootticks, MAXSHOOTTICKS;
	public float MAXRECOILTICKS;
	public boolean fired, shoot = true, aiming = false;
	public static float aimprogress, sprintprogress;
	public float aimprogressconv, sprintprogressconv;
	private int stage, level;
	public GunAnimation anim;
	public boolean start;
	public boolean renderCrosshair = false;
	private Map<String, GunAnimation> anims = new HashMap<String, GunAnimation>();

	public AnimationManager() {
		anims.put(ItemRegistries.pirate_pistol.get().getRegistryName().toString(), new PiratePistolAnim());
		anims.put(ItemRegistries.pirate_rifle.get().getRegistryName().toString(), new PirateRifleAnim());
		anims.put(ItemRegistries.winchester.get().getRegistryName().toString(), new WinchesterAnim());
		anims.put(ItemRegistries.revolver.get().getRegistryName().toString(), new RevolverAnim());
		anims.put(ItemRegistries.trabuco.get().getRegistryName().toString(), new TrabucoAnim());
		anims.put(ItemRegistries.kar98k.get().getRegistryName().toString(), new Kar98KAnim());
	}

	public void setStart(boolean start, int bullets, int count, int max, int level) {
		this.start = start;
		this.level = level;
		PlayerEntity player = Minecraft.getInstance().player;
		if (anim != null) {
			MAXANIMTICKS = (int) (level > 0 ? MathUtils.getPorcentageFrom(anim.getReloadTimeForStage(stage), level * 20)
					: anim.getReloadTimeForStage(stage));

			if (anim.hasCustomReload()) {
				anim.setBullets(bullets, count, max);
			} else {
				ItemStack stack = player.getMainHandItem();
				PlayerInventory inv = player.inventory;
				IItemHandler handler = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
						.orElseThrow(() -> new NullPointerException("getting capability"));
				int index = InventoryHelper.hasItem(inv, ItemRegistries.piratebullet.get());
				int gunpowder = InventoryHelper.hasItem(inv, Items.GUNPOWDER);
				if (stack.getItem() instanceof ItemGun) {
					if (index != -1 && gunpowder != -1) {
						int count2 = inv.getItem(index).getCount() > max + 1 ? max : inv.getItem(index).getCount();
						stack.getOrCreateTag().putInt("bullets", count2);
						OldGuns.channel.sendToServer(new BulletsMessage(count2));

						if (!player.isCreative()) {
							inv.removeItem(index, count2);
							OldGuns.channel.sendToServer(new ConsumeStackMessage(index, count2));
							inv.removeItem(gunpowder, ((ItemGun) stack.getItem()).isShotgun() ? 2 : 1);
							OldGuns.channel.sendToServer(new ConsumeStackMessage(gunpowder,
									((ItemGun) stack.getItem()).isShotgun() ? 2 : 1));
						}
					}
				}
			}
		}

	}

	public void startShootingTicks(int shootticks) {
		this.MAXSHOOTTICKS = shootticks;
		shoot = true;
	}

	public void startRecoilTicks(float recoil, String gun) {
		MAXRECOILTICKS = recoil;
		anims.get(gun).MAXRECOILTICKS = recoil;
		fired = true;
	}

	public void tryShoot(PlayerEntity player, ItemStack stack) {

	}

	public void tick() {

		// shoot = true;
		// MAXSHOOTTICKS = 30;
		if (start) {
			if (gunticks < MAXANIMTICKS) {
				gunticks++;

				gunticksconv = gunticks / MAXANIMTICKS;

			} else {
				gunticks = 0;
				gunticksconv = gunticks / MAXANIMTICKS;
				if (anim != null) {

					if (stage < anim.getAnimSize()) {
						stage++;
						anim.specialReload(stage);
						MAXANIMTICKS = (int) (level > 0
								? MathUtils.getPorcentageFrom(anim.getReloadTimeForStage(stage), level * 20)
								: anim.getReloadTimeForStage(stage));

					} else {
						start = false;
					}
				}
			}
		}
		if (fired) {
			if (recoilticks < MAXRECOILTICKS) {
				recoilticks++;

			} else {
				recoilticks = 0;
				fired = false;

			}
		}
		// if(shoot) {
		PlayerEntity player = Minecraft.getInstance().player;
		ItemGun gun = ((ItemGun) player.getMainHandItem().getItem());
		if (shootticks < gun.getShootTime()) {
			shootticks++;
			if (shootticks == 4) {

				if (gun.getShootReloadSound() != null) {
					System.out.println("playing sound with clientSide: " + player.level.isClientSide);
					player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
							gun.getShootReloadSound(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
					OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(gun.getShootReloadSound(), player.getX(),
							player.getY(), player.getZ()));
				}
			}
		} /*
			 * else { shoot = false; shootticks = 0; }
			 */
		// }
	}

	public void reset() {
		gunticks = 0;
		stage = 0;
		aimprogress = 0;
		sprintprogress = 0;
		recoilticks = 0;
		level = 0;
		MAXANIMTICKS = 0;
		start = false;
	}

	public void resetShoot() {
		shootticks = 0;
	}

	public void getAnimForGun(String gun) {
		anim = anims.get(gun);
	}

	public void animShootLeftArm(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animShootLeftArm(stack, shootticks);
	}

	public void animShootRightArm(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animShootRightArm(stack, shootticks);
	}

	public void animShootGun(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animShootGun(stack, shootticks);
	}

	public void animShootHammer(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animShootHammer(stack, shootticks);
	}

	public void animRecoilRightArm(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animRecoilRightArm(stack, recoilticks);
	}

	public void animRecoilGun(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animRecoilGun(stack, recoilticks);
	}

	public void animRecoilHammer(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animRecoilHammer(stack, recoilticks);
	}

	public void animRecoilLeftArm(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animRecoilLeftArm(stack, recoilticks);
	}

	public void animRecoilScope(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animRecoilScope(stack, recoilticks);
	}

	public void animGun(MatrixStack stack) {
		if (!start || anim == null)
			return;
		anim.animGun(stack, stage, gunticksconv);
	}

	public void animRightArm(MatrixStack stack) {
		if (!start || anim == null)
			return;
		anim.animRightArm(stack, stage, gunticksconv);

	}

	public void animHammer(MatrixStack stack) {
		if (!start || anim == null)
			return;
		anim.animHammer(stack, stage, gunticksconv);

	}

	public void animAditional(MatrixStack matrix) {
		if (!start || anim == null)
			return;
		anim.animAditional(matrix, stage, gunticksconv);
	}

	public void animScope(MatrixStack stack) {
		if (!start || anim == null)
			return;
		anim.animScope(stack, stage, gunticksconv);

	}

	public void animLeftArm(MatrixStack stack) {
		if (!start || anim == null)
			return;
		anim.animLeftArm(stack, stage, gunticksconv);
	}

	/////////// Aim

	public void animLeftHandAim(MatrixStack stack, boolean scope) {
		if (anim == null)
			return;
		anim.animLeftHandAim(stack, aimprogressconv, scope);
	}

	public void animRightHandAim(MatrixStack stack, boolean scope) {
		if (anim == null)
			return;
		anim.animRightHandAim(stack, aimprogressconv, scope);
	}

	public void animGunAim(MatrixStack stack, boolean scope) {
		if (anim == null)
			return;
		anim.animGunAim(stack, aimprogressconv, scope);
	}

	public void animHammerAim(MatrixStack stack, boolean scope) {
		if (anim == null)
			return;
		anim.animHammerAim(stack, aimprogressconv, scope);
	}

	public void animScopeAim(MatrixStack stack, boolean scope) {
		if (anim == null)
			return;
		anim.animScopeAim(stack, aimprogressconv, scope);
	}

	/////////// Sprinting

	public void animLeftHandSprint(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animLeftHandSprint(stack, sprintprogressconv);
	}

	public void animRightHandSprint(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animRightHandSprint(stack, sprintprogressconv);
	}

	public void animGunSprint(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animGunSprint(stack, sprintprogressconv);
	}

	public void animHammerSprint(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animHammerSprint(stack, sprintprogressconv);
	}

	public void animScopeSprint(MatrixStack stack) {
		if (anim == null)
			return;
		anim.animScopeSprint(stack, sprintprogressconv);
	}
}
