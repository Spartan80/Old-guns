package com.jg.oldguns.utils;

import java.util.ArrayList;
import java.util.List;

import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.guns.ItemMag;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ServerUtils {

	// Utils for itemstack

	public static void addToNBT(ItemStack stack, String path, float val) {
		CompoundTag nbt = stack.getOrCreateTag();
		nbt.putFloat(path, nbt.getFloat(path) + val);
	}

	public static void addToNBT(ItemStack stack, String path, int val) {
		CompoundTag nbt = stack.getOrCreateTag();
		nbt.putInt(path, nbt.getInt(path) + val);
	}

	public static void growBullets(ServerPlayer player) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putInt(NBTUtils.AMMO, stack.getOrCreateTag().getInt(NBTUtils.AMMO) + 1);
	}

	public static void growBulletsBy(ServerPlayer player, int bullets) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putInt(NBTUtils.AMMO, stack.getOrCreateTag().getInt(NBTUtils.AMMO) + bullets);
	}

	public static void setHasMag(ItemStack stack, boolean hasMag) {
		stack.getOrCreateTag().putBoolean(NBTUtils.HASMAG, hasMag);
	}

	public static void setGunBullets(ServerPlayer player, int bullets) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putInt(NBTUtils.AMMO, bullets);
	}

	public static void setStock(ServerPlayer player, String stock) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putString(NBTUtils.STOCK, stock);
	}

	public static void setBody(ServerPlayer player, String body) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putString(NBTUtils.BODY, body);
	}

	public static void setBarrel(ServerPlayer player, String barrel) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putString(NBTUtils.BARREL, barrel);
	}

	/*public static void setScope(ServerPlayer player, int scope) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putInt(NBTUtils.SCOPE, scope);
	}

	public static void setHammer(ServerPlayer player, boolean hammer) {
		ItemStack stack = player.getMainHandItem();
		stack.getOrCreateTag().putBoolean(NBTUtils.HAMMER, hammer);
	}*/

	public static boolean hasMag(ServerPlayer player) {
		return player.getMainHandItem().getOrCreateTag().getBoolean(NBTUtils.HASMAG);
	}

	public static boolean hasMag(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean(NBTUtils.HASMAG);
	}

	/*public static boolean hasScope(ServerPlayer player) {
		return player.getMainHandItem().getOrCreateTag().getInt(NBTUtils.SCOPE) != 0;
	}

	public static boolean hasScope(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.SCOPEPATH) != "";
	}

	public static boolean hasBulletOnRoom(ServerPlayer player) {
		return player.getMainHandItem().getOrCreateTag().getBoolean(NBTUtils.HASBULLETONROOM);
	}

	public static boolean hasBulletOnRoom(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean(NBTUtils.HASBULLETONROOM);
	}*/

	public static String getId(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.ID);
	}

	public static void setMagPath(ItemStack stack, String magp) {
		stack.getOrCreateTag().putString(NBTUtils.MAG, magp);
	}

	public static String getMagPath(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.MAG);
	}

	public static ItemStack getStack(ServerPlayer player) {
		return player.getMainHandItem();
	}

	public static ItemStack getStack(Player player) {
		return player.getMainHandItem();
	}

	public static int growBullets(ItemStack stack, int bullets, boolean sum) {
		return getBullets(stack) + (sum ? bullets : -bullets);
	}

	public static void setBullets(ItemStack stack, int bullets) {
		stack.getOrCreateTag().putInt(NBTUtils.BULLETS, bullets);
	}

	public static int getBullets(ItemStack stack) {
		return stack.getOrCreateTag().getInt(NBTUtils.AMMO);
	}

	public static String getBarrel(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.BARREL);
	}

	public static void setBarrel(ItemStack stack, String barrel) {
		stack.getOrCreateTag().putString(NBTUtils.BARREL, barrel);
	}

	public static String getBody(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.BODY);
	}

	public static void setBody(ItemStack stack, String body) {
		stack.getOrCreateTag().putString(NBTUtils.BODY, body);
	}

	public static String getStock(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.STOCK);
	}

	public static void setStock(ItemStack stack, String stock) {
		stack.getOrCreateTag().putString(NBTUtils.STOCK, stock);
	}

	/*public static String getBarrelMouth(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.BARRELMOUTH);
	}

	public static void setBarrelMouth(ItemStack stack, String barrelm) {
		stack.getOrCreateTag().putString(NBTUtils.BARRELMOUTH, barrelm);
	}*/

	/*public static void setScope(ItemStack stack, int scope) {
		stack.getOrCreateTag().putInt(NBTUtils.SCOPE, scope);
	}

	public static int getScope(ItemStack stack) {
		return stack.getOrCreateTag().getInt(NBTUtils.SCOPE);
	}

	public static String getScopePath(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.SCOPEPATH);
	}

	public static void setScopePath(ItemStack stack, String path) {
		stack.getOrCreateTag().putString(NBTUtils.SCOPEPATH, path);
	}

	public static void setScopeGunId(ItemStack stack, String scope) {
		stack.getOrCreateTag().putString(NBTUtils.SCOPEGUNID, scope);
	}

	public static String getScopeGunId(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.SCOPEGUNID);
	}*/

	public static void setMagBulletPath(ItemStack stack, String magbp) {
		stack.getOrCreateTag().putString(NBTUtils.MAGBULLET, magbp);
	}

	public static String getMagBulletPath(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.MAGBULLET);
	}

	/*public static void setHammer(ItemStack stack, boolean magtype) {
		stack.getOrCreateTag().putBoolean(NBTUtils.HAMMER, magtype);
	}

	public static boolean getHammer(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean(NBTUtils.HAMMER);
	}

	public static void setInit(ItemStack stack, boolean init) {
		stack.getOrCreateTag().putBoolean(NBTUtils.INIT, init);
	}

	public static boolean getInit(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean(NBTUtils.INIT);
	}*/

	public static boolean isBarrelEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.BARREL).equals(NBTUtils.EMPTY)
				|| stack.getOrCreateTag().getString(NBTUtils.BARREL).equals("");
	}

	public static boolean isBodyEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.BODY).equals(NBTUtils.EMPTY)
				|| stack.getOrCreateTag().getString(NBTUtils.BODY).equals("");
	}

	public static boolean isStockEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getString(NBTUtils.STOCK).equals(NBTUtils.EMPTY)
				|| stack.getOrCreateTag().getString(NBTUtils.STOCK).equals("");
	}

	/*public static boolean isScopeEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getInt(NBTUtils.SCOPE) == 0;// stack.getOrCreateTag().getString(NBTUtils.SCOPE).equals(NBTUtils.EMPTY)
		// || stack.getOrCreateTag().getString(NBTUtils.SCOPE).equals("");
	}*/

	public static boolean hasModParts(ItemStack stack) {
		return !stack.getOrCreateTag().getString(NBTUtils.BARREL).equals("")
				|| !stack.getOrCreateTag().getString(NBTUtils.BODY).equals("")
				|| !stack.getOrCreateTag().getString(NBTUtils.STOCK).equals("");
	}

	public static boolean isMagEqualTo(ItemStack stack, Item item) {
		return stack.getOrCreateTag().getString(NBTUtils.MAG)
				.equals(Utils.getR(item).toString());
	}

	// Utils for itemstack

	// Utils for inventory

	public static int getIndexForItem(Inventory pi, Item item) {
		for (int index = 0; index < pi.getContainerSize(); ++index) {
			if (pi.getItem(index).getItem() == item) {
				return index;
			}
		}
		return -1;
	}

	public static int getIndexForItem(Inventory pi, List<Item> items) {
		for (Item item : items) {
			for (int index = 0; index < pi.getContainerSize(); ++index) {
				if (pi.getItem(index).getItem() == item) {
					return index;
				}
			}
		}
		return -1;
	}

	public static ItemMag getMagForGun(Inventory pi, String gunid, String gunAcceptedType) {
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

	public static int getIndexForCorrectMag(Inventory pi, String gunid, String gunAcceptedType) {
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

	public static int getItemCount(Inventory pi, Item item) {
		for (int index = 0; index < pi.getContainerSize(); ++index) {
			ItemStack stack = pi.getItem(index);
			if (stack.getItem() == item) {
				return stack.getCount();
			}
		}
		return -1;
	}

	public static int getTotalItemAmout(Inventory pi, Item item) {
		int amount = 0;
		for (int index = 0; index < pi.getContainerSize(); ++index) {
			ItemStack stack = pi.getItem(index);
			if (stack.getItem() == item) {
				amount += stack.getCount();
			}
		}
		return amount;
	}

	public static int getTotalItemAmout(Inventory pi, List<Item> items) {
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

	public static void removeItemInDifIndexes(Inventory pi, Item item, int amount) {
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

	public static void removeItemInDifIndexes(Inventory pi, List<Item> items, int amount) {
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

	public static String getRegForIndex(int index, Inventory pi) {
		return Utils.getR(pi.getItem(index).getItem()).toString();
	}

	// Utils for inventory
}
