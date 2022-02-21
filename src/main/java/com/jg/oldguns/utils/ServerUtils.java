package com.jg.oldguns.utils;

import java.util.ArrayList;
import java.util.List;

import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.guns.ItemMag;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ServerUtils {
	
	// Utils for itemstack
	
	public static void addToNBT(ItemStack stack, String path, float val) {
		CompoundNBT nbt = stack.getOrCreateTag();
		nbt.putFloat(path, nbt.getFloat(path)+val);
	}
	
	public static void addToNBT(ItemStack stack, String path, int val) {
		CompoundNBT nbt = stack.getOrCreateTag();
		nbt.putInt(path, nbt.getInt(path)+val);
	}
	
	public static void growBullets(ServerPlayerEntity player) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putInt(Constants.BULLETS, stack.getOrCreateTag().getInt(Constants.BULLETS) + 1);
	}

	public static void growBulletsBy(ServerPlayerEntity player, int bullets) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putInt(Constants.BULLETS, stack.getOrCreateTag().getInt(Constants.BULLETS) + bullets);
	}

	public static void setGunBullets(ServerPlayerEntity player, int bullets) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putInt(Constants.BULLETS, bullets);
	}

	public static void setStock(ServerPlayerEntity player, String stock) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putString(Constants.STOCK, stock);
	}

	public static void setBody(ServerPlayerEntity player, String body) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putString(Constants.BODY, body);
	}

	public static void setBarrel(ServerPlayerEntity player, String barrel) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putString(Constants.BARREL, barrel);
	}

	public static void setBarrelMouth(ServerPlayerEntity player, String barrelmouth) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putString(Constants.BARRELMOUTH, barrelmouth);
	}

	public static void setScope(ServerPlayerEntity player, int scope) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putInt(Constants.SCOPE, scope);
	}

	public static void setHammer(ServerPlayerEntity player, boolean hammer) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putBoolean(Constants.HAMMER, hammer);
	}

	public static boolean hasMag(ServerPlayerEntity player) {
		return player.getMainHandItem().getOrCreateTag().getBoolean(Constants.HASMAG);
	}

	public static boolean hasMag(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean(Constants.HASMAG);
	}

	public static boolean hasScope(ServerPlayerEntity player) {
		return player.getMainHandItem().getOrCreateTag().getInt(Constants.SCOPE) != 0;
	}

	public static boolean hasScope(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.SCOPEPATH) != "";
	}

	public static boolean hasBulletOnRoom(ServerPlayerEntity player) {
		return player.getMainHandItem().getOrCreateTag().getBoolean(Constants.HASBULLETONROOM);
	}

	public static boolean hasBulletOnRoom(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean(Constants.HASBULLETONROOM);
	}

	public static ItemStack getStack(ServerPlayerEntity player) {
		return player.getMainHandItem();
	}

	public static ItemStack getStack(PlayerEntity player) {
		return player.getMainHandItem();
	}

	public static int growBullets(ItemStack stack, int bullets, boolean sum) {
		return getBullets(stack) + (sum ? bullets : -bullets);
	}

	public static void setBullets(ItemStack stack, int bullets) {
		stack.getOrCreateTag().putInt(Constants.BULLETS, bullets);
	}

	public static int getBullets(ItemStack stack) {
		return stack.getOrCreateTag().getInt(Constants.BULLETS);
	}

	public static String getBarrel(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.BARREL);
	}

	public static void setBarrel(ItemStack stack, String barrel) {
		stack.getOrCreateTag().putString(Constants.BARREL, barrel);
	}

	public static String getBody(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.BODY);
	}

	public static void setBody(ItemStack stack, String body) {
		stack.getOrCreateTag().putString(Constants.BODY, body);
	}

	public static String getStock(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.STOCK);
	}

	public static void setStock(ItemStack stack, String stock) {
		stack.getOrCreateTag().putString(Constants.STOCK, stock);
	}

	public static String getBarrelMouth(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.BARRELMOUTH);
	}

	public static void setBarrelMouth(ItemStack stack, String barrelm) {
		stack.getOrCreateTag().putString(Constants.BARRELMOUTH, barrelm);
	}

	public static void setScope(ItemStack stack, int scope) {
		stack.getOrCreateTag().putInt(Constants.SCOPE, scope);
	}

	public static int getScope(ItemStack stack) {
		return stack.getOrCreateTag().getInt(Constants.SCOPE);
	}
	
	public static String getScopePath(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.SCOPEPATH);
	}
	
	public static void setScopePath(ItemStack stack, String path) {
		stack.getOrCreateTag().putString(Constants.SCOPEPATH, path);
	}
	
	public static void setScopeGunId(ItemStack stack, String scope) {
		stack.getOrCreateTag().putString(Constants.SCOPEGUNID, scope);
	}

	public static String getScopeGunId(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.SCOPEGUNID);
	}

	public static void setMagType(ItemStack stack, int magtype) {
		stack.getOrCreateTag().putInt(Constants.MAGTYPE, magtype);
	}

	public static int getMagType(ItemStack stack) {
		return stack.getOrCreateTag().getInt(Constants.MAGTYPE);
	}

	public static void setHammer(ItemStack stack, boolean magtype) {
		stack.getOrCreateTag().putBoolean(Constants.HAMMER, magtype);
	}

	public static boolean getHammer(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean(Constants.HAMMER);
	}

	public static void setInit(ItemStack stack, boolean init) {
		stack.getOrCreateTag().putBoolean(Constants.INIT, init);
	}

	public static boolean getInit(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean(Constants.INIT);
	}

	public static boolean isBarrelEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.BARREL).equals(Constants.EMPTY)
				|| stack.getOrCreateTag().getString(Constants.BARREL).equals("");
	}

	public static boolean isBodyEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.BODY).equals(Constants.EMPTY)
				|| stack.getOrCreateTag().getString(Constants.BODY).equals("");
	}

	public static boolean isStockEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.STOCK).equals(Constants.EMPTY)
				|| stack.getOrCreateTag().getString(Constants.STOCK).equals("");
	}

	public static boolean isBarrelMouthEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getString(Constants.BARRELMOUTH).equals(Constants.EMPTY)
				|| stack.getOrCreateTag().getString(Constants.BARRELMOUTH).equals("");
	}

	public static boolean isScopeEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getInt(Constants.SCOPE) == 0;//stack.getOrCreateTag().getString(Constants.SCOPE).equals(Constants.EMPTY)
				//|| stack.getOrCreateTag().getString(Constants.SCOPE).equals("");
	}

	public static boolean hasModParts(ItemStack stack) {
		return !stack.getOrCreateTag().getString(Constants.BARREL).equals("")
				|| !stack.getOrCreateTag().getString(Constants.BODY).equals("")
				|| !stack.getOrCreateTag().getString(Constants.STOCK).equals("")
				|| !stack.getOrCreateTag().getString(Constants.BARRELMOUTH).equals("");
	}

	// Utils for itemstack

	// Utils for inventory

	public static int getIndexForItem(PlayerInventory pi, Item item) {
		for (int index = 0; index < pi.getContainerSize(); ++index) {
			if (pi.getItem(index).getItem() == item) {
				return index;
			}
		}
		return -1;
	}

	public static int getIndexForItem(PlayerInventory pi, List<Item> items) {
		for (Item item : items) {
			for (int index = 0; index < pi.getContainerSize(); ++index) {
				if (pi.getItem(index).getItem() == item) {
					return index;
				}
			}
		}
		return -1;
	}

	public static ItemMag getMagForGun(PlayerInventory pi, String gunid, String gunAcceptedType) {
		for (int index = 0; index < pi.getContainerSize(); ++index) {
			ItemStack stack = pi.getItem(index);
			if (stack.getItem() instanceof ItemMag) {
				ItemMag mag = (ItemMag) stack.getItem();
				if (mag.getGunId().equals(gunid) && mag.getAcceptedSize().equals(gunAcceptedType)) {
					return mag;
				}
			}
		}
		return null;
	}

	public static int getIndexForCorrectMag(PlayerInventory pi, String gunid, String gunAcceptedType) {
		for (int index = 0; index < pi.getContainerSize(); ++index) {
			ItemStack stack = pi.getItem(index);
			if (stack.getItem() instanceof ItemMag) {
				ItemMag mag = (ItemMag) stack.getItem();
				if (mag.getGunId().equals(gunid) && mag.getAcceptedSize().equals(gunAcceptedType)) {
					return index;
				}
			}
		}
		return -1;
	}

	public static int getItemCount(PlayerInventory pi, Item item) {
		for (int index = 0; index < pi.getContainerSize(); ++index) {
			ItemStack stack = pi.getItem(index);
			if (stack.getItem() == item) {
				return stack.getCount();
			}
		}
		return -1;
	}

	public static int getTotalItemAmout(PlayerInventory pi, Item item) {
		int amount = 0;
		for (int index = 0; index < pi.getContainerSize(); ++index) {
			ItemStack stack = pi.getItem(index);
			if (stack.getItem() == item) {
				amount += stack.getCount();
			}
		}
		return amount;
	}

	public static int getTotalItemAmout(PlayerInventory pi, List<Item> items) {
		int amount = 0;
		for (Item item : items) {
			for (int index = 0; index < pi.getContainerSize(); ++index) {
				ItemStack stack = pi.getItem(index);
				if (stack.getItem() == item) {
					amount += stack.getCount();
				}
			}
		}
		return amount;
	}

	public static void removeItemInDifIndexes(PlayerInventory pi, Item item, int amount) {
		int remAmount = amount;
		List<Integer> mat = new ArrayList<Integer>();

		for (int i = 0; i < pi.items.size(); i++) {
			ItemStack stack = pi.items.get(i);

			if (stack.getItem() == item) {
				if (remAmount > 0) {
					mat.add(i);
					if (stack.getCount() < remAmount) {
						mat.add(stack.getCount());
						remAmount -= stack.getCount();
					} else {
						mat.add(remAmount);
						remAmount = 0;
					}
				}
			}
		}

		for (int i = 0; i < mat.size(); i += 2) {
			ReloadHandler.removeItem(mat.get(i), mat.get(i + 1));
		}

	}

	public static void removeItemInDifIndexes(PlayerInventory pi, List<Item> items, int amount) {
		int remAmount = amount;
		List<Integer> mat = new ArrayList<Integer>();

		for (int i = 0; i < pi.items.size(); i++) {
			ItemStack stack = pi.items.get(i);

			if (items.contains(stack.getItem())) {
				if (remAmount > 0) {
					mat.add(i);
					if (stack.getCount() < remAmount) {
						mat.add(stack.getCount());
						remAmount -= stack.getCount();
					} else {
						mat.add(remAmount);
						remAmount = 0;
					}
				}
			}
		}

		for (int i = 0; i < mat.size(); i += 2) {
			ReloadHandler.removeItem(mat.get(i), mat.get(i + 1));
		}

	}

	public static String getRegForIndex(int index, PlayerInventory pi) {
		return pi.getItem(index).getItem().getRegistryName().toString();
	}

	// Utils for inventory
}
