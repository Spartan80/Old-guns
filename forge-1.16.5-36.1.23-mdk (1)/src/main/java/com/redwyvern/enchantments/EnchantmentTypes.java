package com.redwyvern.enchantments;

import com.redwyvern.gun.ItemGun;
import com.redwyvern.mod.OldGuns;

import net.minecraft.enchantment.EnchantmentType;

public class EnchantmentTypes {
	public static final EnchantmentType GUN = EnchantmentType.create(OldGuns.MODID + ":gun",
			item -> item.getItem() instanceof ItemGun);
}
