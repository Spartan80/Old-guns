package com.jg.oldguns.client.handlers;

import java.util.ArrayList;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.guns.MagItem;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.network.ConsumeItemMessage;
import com.jg.oldguns.network.MagBulletPathMessage;
import com.jg.oldguns.network.MakeMagMessage;
import com.jg.oldguns.network.RestoreMagMessage;
import com.jg.oldguns.network.SetBulletsMessage;
import com.jg.oldguns.network.SetHammerMessage;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.logging.LogUtils;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ReloadHandler {
	// Reload ServerUtils

	public static void growOneBullet(ItemStack stack) {
		OldGuns.channel.sendToServer(new SetBulletsMessage(ServerUtils.getBullets(stack) + 1));
	}

	public static void decOneBullet(ItemStack stack) {
		int bullets = (ServerUtils.getBullets(stack) - 1);
		OldGuns.channel.sendToServer(new SetBulletsMessage(bullets > 0 ? bullets : 0));
	}

	public static void growBullets(ItemStack stack, int bullets) {
		OldGuns.channel.sendToServer(new SetBulletsMessage(ServerUtils.growBullets(stack, bullets, true)));
	}

	public static void decBullets(ItemStack stack, int bullets) {
		OldGuns.channel.sendToServer(new SetBulletsMessage(ServerUtils.growBullets(stack, bullets, false)));
	}

	public static void setBullets(int bullets) {
		OldGuns.channel.sendToServer(new SetBulletsMessage(bullets));
	}

	public static void setMag(GunModel model, int maxAmmo, boolean mag, String bpath, MagItem magItem) {
		ItemStack stack = Utils.getStack();
		if (mag) {
			OldGuns.channel.sendToServer(new MakeMagMessage(maxAmmo, mag, bpath, 
					Utils.getR(magItem).toString()));
			ServerUtils.setHasMag(stack, mag);
		} else {
			OldGuns.channel.sendToServer(new MakeMagMessage(0, mag, bpath, ""));
			ServerUtils.setHasMag(stack, mag);
		}
		model.getModel().setReconstruct();
	}

	public static void setMag(GunModel model, int maxAmmo, boolean mag, String bpath, String magPath) {
		ItemStack stack = Utils.getStack();
		OldGuns.channel.sendToServer(new MakeMagMessage(maxAmmo, mag, bpath, magPath));
		ServerUtils.setHasMag(stack, mag);
		model.getModel().setReconstruct();
	}

	public static void removeOneItemFrom(int index) {
		OldGuns.channel.sendToServer(new ConsumeItemMessage(index, 1));
	}

	public static void removeItem(int index, int count) {
		OldGuns.channel.sendToServer(new ConsumeItemMessage(index, count));
	}

	public static void addBullet(String path, int count) {
		OldGuns.channel.sendToServer(new AddItemMessage(path, ServerUtils
				.distributeItem(path, count)));
	}

	public static void restoreMag(String mag, int bullets) {
		OldGuns.channel.sendToServer(new RestoreMagMessage(mag, bullets));
	}

	public static void setHammer(boolean hammer) {
		OldGuns.channel.sendToServer(new SetHammerMessage(hammer));
	}

	public static void doGetOutMag(GunModel model, String mag, int bullets) {
		restoreMag(mag, ServerUtils.getBullets(Utils.getStack()));
		setBullets(0);
		setMag(model, 0, false, NBTUtils.EMPTY, NBTUtils.EMPTY);
	}

	public static void doReloadMag(GunModel model, int maxAmmo, int magbullets, int ammoindex) {
		ItemStack magstack = (Utils.getPlayer()).getInventory().getItem(ammoindex);
		setBullets(magbullets);
		setMag(model, maxAmmo, true, ServerUtils.getMagBulletPath(magstack),
				Utils.getR(magstack).toString());
		removeItem(ammoindex, 1);
	}

	public static void setMagBulletPath(String bpath) {
		OldGuns.channel.sendToServer(new MagBulletPathMessage(bpath));
	}

	public static void doMag(GunModel model, int maxAmmo, int ammoindex) {
		ItemStack magstack = (Utils.getPlayer()).getInventory().getItem(ammoindex);
		setMag(model, maxAmmo, true, ServerUtils.getMagBulletPath(magstack),
				Utils.getR(magstack).toString());
		ReloadHandler.removeItem(ammoindex, 1);
	}

	public static void doGetOutMag(GunModel model) {
		ReloadHandler.restoreMag(NBTUtils.getMag(Utils.getStack()), model.gun
				.getBulletsPerShoot());
		ReloadHandler.setBullets(0);
		ReloadHandler.setMag(model, 0, false, NBTUtils.EMPTY, NBTUtils.EMPTY);
	}

	public static int findCorrectBullet(Inventory pi, ItemStack stack) {
		MagItem mag = (MagItem) stack.getItem();
		
		if(stack.getOrCreateTag().getInt(NBTUtils.BULLETS) >= mag.getMaxAmmo()) {
			return -1;
		}		
		
		int index = -1;
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		int count = 0;
		int bulletsable = 0;
		for (int i = 0; i < pi.getContainerSize(); i++) {
			ItemStack istack = pi.getItem(i);
			if (istack.getItem() instanceof BulletItem) {
				if (mag.getAcceptedSize().equals(((BulletItem) istack.getItem()).getSize())) {
					bulletsable = mag.getMaxAmmo() - ServerUtils.getBullets(stack);
					if (count + istack.getCount() < bulletsable) {
						indexs.add(i);
						index = i;
						count += istack.getCount();
						amounts.add(istack.getCount());
						OldGuns.channel
								.sendToServer(new MagBulletPathMessage(Utils.getR(istack)
										.toString()));
					} else if (count < bulletsable) {
						indexs.add(i);
						index = i;
						amounts.add(bulletsable - count);
						count += bulletsable - count;
						OldGuns.channel
								.sendToServer(new MagBulletPathMessage(Utils.getR(istack)
										.toString()));
					}
				}
			}
		}
		LogUtils.getLogger().info("bulletsable: " + bulletsable);
		if (index == -1 && bulletsable == 0) {
			return -1;
		}
		int amount = count >= bulletsable ? bulletsable : count;
		for (int i = 0; i < indexs.size(); i++) {
			ReloadHandler.removeItem(indexs.get(i), amounts.get(i));
		}
		ReloadHandler.growBullets(stack, amount);
		return index;
	}
}
