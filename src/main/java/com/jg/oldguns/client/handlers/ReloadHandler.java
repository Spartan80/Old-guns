package com.jg.oldguns.client.handlers;

import java.util.ArrayList;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.guns.ItemBullet;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.network.MagBulletPathMessage;
import com.jg.oldguns.network.MakeMagMessage;
import com.jg.oldguns.network.RemoveItemMessage;
import com.jg.oldguns.network.RestoreMagMessage;
import com.jg.oldguns.network.SetBulletsMessage;
import com.jg.oldguns.network.SetHammerMessage;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ReloadHandler {
	// Reload ServerUtilss

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

	public static void setMag(int maxAmmo, boolean mag, String bpath, ItemMag magItem) {
		if(mag) {
			OldGuns.channel.sendToServer(new MakeMagMessage(maxAmmo, mag, bpath, magItem.getRegistryName().toString()));
		}else {
			OldGuns.channel.sendToServer(new MakeMagMessage(0, mag, bpath, ""));
		}
	}

	public static void setMag(int maxAmmo, boolean mag, String bpath, String magPath) {
		OldGuns.channel.sendToServer(new MakeMagMessage(maxAmmo, mag, bpath, magPath));
	}
	
	public static void removeOneItemFrom(int index) {
		OldGuns.channel.sendToServer(new RemoveItemMessage(index, 1));
	}

	public static void removeItem(int index, int count) {
		OldGuns.channel.sendToServer(new RemoveItemMessage(index, count));
	}

	public static void addBullet(String path, int count) {
		OldGuns.channel.sendToServer(new AddItemMessage(path, count));
	}

	public static void restoreMag(String mag, int bullets) {
		OldGuns.channel.sendToServer(new RestoreMagMessage(mag, bullets));
	}

	public static void setHammer(boolean hammer) {
		OldGuns.channel.sendToServer(new SetHammerMessage(hammer));
	}

	public static void doGetOutMag(String mag, int bullets) {
		restoreMag(mag, ServerUtils.getBullets(Util.getStack()));
		setBullets(0);
		setMag(0, false, Constants.EMPTY, Constants.EMPTY);
	}

	public static void doReloadMag(int maxAmmo, int magbullets, int ammoindex) {
		ItemStack magstack = (ClientEventHandler.getPlayer()).getInventory().getItem(ammoindex);
		setBullets(magbullets);
		setMag(maxAmmo, true,
				ServerUtils.getMagBulletPath(magstack), magstack.getItem().getRegistryName().toString());
		removeItem(ammoindex, 1);
	}

	public static void setMagBulletPath(String bpath) {
		OldGuns.channel.sendToServer(new MagBulletPathMessage(bpath));
	}

	public static void doMag(int maxAmmo, int ammoindex) {
		ItemStack magstack = (ClientEventHandler.getPlayer()).getInventory().getItem(ammoindex);
		setMag(maxAmmo, true,
				ServerUtils.getMagBulletPath(magstack), magstack.getItem().getRegistryName().toString());
		ReloadHandler.removeItem(ammoindex, 1);
	}

	public static void doGetOutMag(GunModel model) {
		ReloadHandler.restoreMag(((ItemGun) ClientEventHandler.getPlayer().getMainHandItem().getItem())
				.getMagPath(Util.getStack()).getRegistryName().toString(), model.getGunBullets());
		ReloadHandler.setBullets(0);
		ReloadHandler.setMag(0, false, Constants.EMPTY, Constants.EMPTY);
	}

	public static int findCorrectBullet(Inventory pi, ItemStack stack) {
		ItemMag mag = (ItemMag) stack.getItem();

		int index = -1;
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		int count = 0;
		int bulletsable = 0;
		for (int i = 0; i < pi.getContainerSize(); i++) {
			ItemStack istack = pi.getItem(i);
			if (istack.getItem() instanceof ItemBullet) {
				if (mag.getAcceptedSize().equals(((ItemBullet) istack.getItem()).getSize())) {
					bulletsable = mag.getMaxAmmo() - ServerUtils.getBullets(stack);
					if (count + istack.getCount() < bulletsable) {
						indexs.add(i);
						index = i;
						count += istack.getCount();
						amounts.add(istack.getCount());
						OldGuns.channel
								.sendToServer(new MagBulletPathMessage(istack.getItem().getRegistryName().toString()));
					} else if (count < bulletsable) {
						indexs.add(i);
						index = i;
						amounts.add(bulletsable - count);
						count += bulletsable - count;
						OldGuns.channel
								.sendToServer(new MagBulletPathMessage(istack.getItem().getRegistryName().toString()));
					}
				}
			}
		}
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
