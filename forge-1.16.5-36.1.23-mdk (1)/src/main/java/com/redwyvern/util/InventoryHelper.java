package com.redwyvern.util;

import com.redwyvern.registries.ItemRegistries;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class InventoryHelper {
	public static int hasItem(PlayerInventory inv, Item itemToSearch) {
		for (int index = 0; index < inv.getContainerSize(); ++index) {
			Item item = inv.getItem(index).getItem();
			if (item == itemToSearch) {
				return index;
			}
		}
		return -1;
	}

	public static int SearchItemStack(PlayerInventory inv, ItemStack stack) {
		for (int index = 0; index < inv.getContainerSize(); ++index) {
			ItemStack stackv = inv.getItem(index);
			if (stackv.isSame(stackv, stack)) {
				return index;
			}
		}
		return -1;
	}

	public static void addStackOrDrop(ItemStack stack, PlayerEntity player) {
		if (player.inventory.getFreeSlot() != -1) {
			player.addItem(stack);
		} else {
			player.drop(stack, false);
		}
	}

	public static void addStack(ItemStack stack, PlayerEntity player) {
		if (player.inventory.getFreeSlot() != -1) {
			player.addItem(stack);
		}
	}

	public static void addStack(ItemStack stack, PlayerInventory inv) {
		if (inv.getFreeSlot() != -1) {
			inv.player.addItem(stack);
		}
	}

	public static int findBullets(PlayerInventory inv) {
		for (int index = 0; index < inv.getContainerSize(); ++index) {
			Item item = inv.getItem(index).getItem();
			if (item == ItemRegistries.piratebullet.get()) {
				return inv.getItem(index).getCount();
			}
		}
		return -1;
	}

	public static int findTotalBullets(PlayerInventory inv, boolean prim) {
		int bullets = 0;
		for (int index = 0; index < inv.getContainerSize(); ++index) {
			Item item = inv.getItem(index).getItem();
			if (item == (prim ? ItemRegistries.piratebullet.get() : ItemRegistries.big_bullet.get())) {
				bullets += inv.getItem(index).getCount();
			}
		}
		return bullets;
	}

	public static int findTotalGunPowder(PlayerInventory inv) {
		int bullets = 0;
		for (int index = 0; index < inv.getContainerSize(); ++index) {
			Item item = inv.getItem(index).getItem();
			if (item == Items.GUNPOWDER) {
				bullets += inv.getItem(index).getCount();
			}
		}
		return bullets;
	}

}
