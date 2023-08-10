package com.jg.oldguns.utils;

import java.util.ArrayList;
import java.util.List;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.network.ConsumeItemMessage;
import com.mojang.logging.LogUtils;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class InventoryUtils {

	public static int getIndexForItem(Player player, Item item) {
		Inventory inv = player.getInventory();
		for(int i = 0; i < inv.items.size(); i++) {
			if(inv.items.get(i).getItem() == item) {
				return i;
			}
		}
		return -1;
	}
	
	public static int getCountForItem(Player player, Item item) {
		Inventory inv = player.getInventory();
		for(int i = 0; i < inv.items.size(); i++) {
			if(inv.items.get(i).getItem() == item) {
				return inv.items.get(i).getCount();
			}
		}
		return -1;
	}
	
	public static int[] getCountAndIndexForItem(Player player, Item item) {
		int[] data = new int[] { -1, -1 };
		Inventory inv = player.getInventory();
		for(int i = 0; i < inv.items.size(); i++) {
			if(inv.items.get(i).getItem() == item) {
				data[0] = i;
				data[1] = inv.items.get(i).getCount();
				return data;
			}
		}
		return data;
	}
	
	public static InvData getTotalCountAndIndexForItem(Player player, Item item, int amount) {
		if(amount <= 0) {
			return new InvData(0, new int[0]);
		}
		List<Integer> list = new ArrayList<>();
		Inventory inv = player.getInventory();
		int total = 0;
		for(int i = 0; i < inv.items.size(); i++) {
			if(inv.items.get(i).getItem() == item) {
				int count = inv.items.get(i).getCount();
				if(count > amount) {
					list.add(i);
					list.add(amount);
					total += amount;
					break;
				} else {
					list.add(i);
					list.add(count);
					total += count;
					amount -= count;
				}
			}
		}
		int[] finalData = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			finalData[i] = list.get(i);
		}
		return new InvData(total, finalData);
	}
	
	public static InvData getTotalCountAndIndexForItem(Player player, TagKey<Item> tag, int amount) {
		List<Integer> list = new ArrayList<>();
		Inventory inv = player.getInventory();
		List<Item> items = Utils.getItemsFromTag(tag);
		int total = 0;
		String all = "";
		for(Item item : items) {
			all += item.getDescriptionId() + " ,";
		}
		LogUtils.getLogger().info("Tag items: " + all);
		for(int i = 0; i < inv.items.size(); i++) {
			LogUtils.getLogger().info("Item i: " + inv.items.get(i).getItem().getDescriptionId());
			if(items.contains(inv.items.get(i).getItem())) {
				int count = inv.items.get(i).getCount();
				if(count > amount) {
					list.add(i);
					list.add(amount);
					total += amount;
					LogUtils.getLogger().info("1 Added " + inv.items.get(i).getItem().getDescriptionId());
					break;
				} else {
					list.add(i);
					list.add(count);
					total += count;
					amount -= count;
					LogUtils.getLogger().info("2 Added " + inv.items.get(i).getItem().getDescriptionId());
				}
			}
		}
		LogUtils.getLogger().info("Final count: " + total);
		int[] finalData = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			finalData[i] = list.get(i);
		}
		return new InvData(total, finalData);
	}
	
	public static int getTotalCountForTag(Player player, TagKey<Item> tag) {
		int count = 0;
		for(int i = 0; i < player.getInventory().items.size(); i++) {
			ItemStack stack = player.getInventory().items.get(i);
			if(ForgeRegistries.ITEMS.tags().getTag(tag).contains(stack.getItem())) {
				count += stack.getCount();
			}
		}
		return count;
	}
	
	public static void addItem(String modid, Item item, int[] data) {
		OldGuns.channel.sendToServer(new AddItemMessage(
				modid + ":" + item.toString(), data));
	}
	
	public static void addItem(String modid, ItemStack stack) {
		OldGuns.channel.sendToServer(new AddItemMessage(
				modid + ":" + stack.getItem().toString(), new int[] { -1, stack.getCount() }));
	}
	
	public static int[] getCountAndIndexForItems(Player player, List<ItemStack> items) {
		int[] data = new int[] { -1, -1 };
		Inventory inv = player.getInventory();
		for(int i = 0; i < inv.items.size(); i++) {
			if(items.contains(inv.items.get(i))) {
				data[0] = i;
				data[1] = inv.items.get(i).getCount();
				return data;
			}
		}
		return data;
	}
	
	public static void consumeItem(Player player, int index, int amount) {
		OldGuns.channel.sendToServer(new ConsumeItemMessage(index, amount));
	}
	
	public static void consumeItems(Player player, int[] data) {
		OldGuns.channel.sendToServer(new ConsumeItemMessage(data));
	}
	
	public static void consumeItem(Player player, Item item, int amount) {
		OldGuns.channel.sendToServer(new ConsumeItemMessage(
				getIndexForItem(player, item), amount));
	}

	public static class InvData {
		
		protected int total;
		protected int[] data;
		
		public InvData(int total, int[] data) {
			this.total = total;
			this.data = data;
		}

		public int getTotal() {
			return total;
		}

		public int[] getData() {
			return data;
		}
		
	}
	
	public static class Pair<T, R> {
		
		protected T t;
		protected R r;
		
		public Pair(T t, R r) {
			this.t = t;
			this.r = r;
		}

		public T getLeft() {
			return t;
		}

		public R getRight() {
			return r;
		}
		
	}
	
}
