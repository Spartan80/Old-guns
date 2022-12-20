package com.jg.oldguns.utils;

import com.jg.oldguns.guns.FireMode;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTUtils {

	public static final String EMPTY = "";
	public static final String AMMO = "ammo";
	public static final String MAG = "mag";
	public static final String MAXAMMO = "maxammo";
	public static final String HASMAG = "hasmag";
	public static final String BULLETS = "bullets";
	public static final String SAFE = "safe";
	public static final String MODE = "mode";
	public static final String ID = "id";
	public static final String MAGBULLET = "magbullet";
	public static final String BARREL = "barrel";
	public static final String BODY = "body";
	public static final String STOCK = "stock";
	public static final String HAMMERS = "hammers";
	public static final String MODIFIED = "modified";
	
	public static CompoundTag get(ItemStack stack) {
		return stack.getOrCreateTag();
	}
	
	// Int
	
	public static void setAmmo(ItemStack stack, int ammo) {
		get(stack).putInt(AMMO, ammo);
	}
	
	public static int getAmmo(ItemStack stack) {
		return get(stack).getInt(AMMO);
	}
	
	public static void setMaxAmmo(ItemStack stack, int maxAmmo) {
		get(stack).putInt(MAXAMMO, maxAmmo);
	}
	
	public static int getMaxAmmo(ItemStack stack) {
		return get(stack).getInt(MAXAMMO);
	}
	
	// Boolean
	
	public static void setSafe(ItemStack stack, boolean safe) {
		get(stack).putBoolean(SAFE, safe);
	}
	
	public static boolean getSafe(ItemStack stack) {
		return get(stack).getBoolean(SAFE);
	}
	
	public static void setModified(ItemStack stack, boolean modified) {
		get(stack).putBoolean(MODIFIED, modified);
	}
	
	public static boolean getModified(ItemStack stack) {
		return get(stack).getBoolean(MODIFIED);
	}
	
	// String
	
	public static void setHammers(ItemStack stack, String[] hammers) {
		String all = "";
		for(String s : hammers) {
			all += s+ ",";
		}
		all = all.substring(0, all.length()-1);
		get(stack).putString(MAG, all);
	}
	
	public static String[] getHammers(ItemStack stack) {
		return get(stack).getString(MAG).split(",");
	}
	
	public static void setMag(ItemStack stack, String mag) {
		get(stack).putString(MAG, mag);
	}
	
	public static String getMag(ItemStack stack) {
		return get(stack).getString(MAG);
	}
	
	public static void setId(ItemStack stack, String id) {
		get(stack).putString(ID, id);
	}
	
	public static String getId(ItemStack stack) {
		return get(stack).getString(ID);
	}
	
	public static void setMagBullet(ItemStack stack, String bullet) {
		get(stack).putString(MAGBULLET, bullet);
	}
	
	public static String getMagBullet(ItemStack stack) {
		return get(stack).getString(MAGBULLET);
	}
	
	public static void setStock(ItemStack stack, String stock) {
		get(stack).putString(STOCK, stock);
	}
	
	public static String getStock(ItemStack stack) {
		return get(stack).getString(STOCK);
	}
	
	public static void setBody(ItemStack stack, String body) {
		get(stack).putString(BODY, body);
	}
	
	public static String getBody(ItemStack stack) {
		return get(stack).getString(BODY);
	}
	
	public static void setBarrel(ItemStack stack, String barrel) {
		get(stack).putString(MAG, barrel);
	}
	
	public static String getBarrel(ItemStack stack) {
		return get(stack).getString(BARREL);
	}
	
	// Misc
	
	public static void setMode(ItemStack stack, FireMode mode) {
		get(stack).putInt(AMMO, mode.ordinal());
	}
	
	public static FireMode getMode(ItemStack stack) {
		return FireMode.values()[(get(stack).getInt(AMMO))];
	}
	
}
