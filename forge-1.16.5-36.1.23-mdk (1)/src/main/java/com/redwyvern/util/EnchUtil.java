package com.redwyvern.util;

import com.redwyvern.registries.EnchantmentRegistries;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class EnchUtil {

	public static int getFastReload(ItemStack stack, int reloadtime) {
		int level = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistries.QUICK_HANDS.get(), stack);
		if (level > 0) {
			return reloadtime - ((reloadtime * (level * 20)) / 100);
		} else {
			return reloadtime;
		}

	}

	public static int getFastReloadLevel(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistries.QUICK_HANDS.get(), stack);
	}

	public static int getMoreShots(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistries.MORE_SHOTS.get(), stack);

	}

}
