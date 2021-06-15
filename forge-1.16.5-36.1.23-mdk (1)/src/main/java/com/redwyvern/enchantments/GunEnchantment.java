package com.redwyvern.enchantments;

import com.redwyvern.gun.ItemGun;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class GunEnchantment extends Enchantment {

	protected GunEnchantment(Rarity rarityIn, EnchantmentType typeIn) {
		super(rarityIn, typeIn, new EquipmentSlotType[] { EquipmentSlotType.MAINHAND });

	}

	@Override
	protected boolean checkCompatibility(Enchantment p_77326_1_) {
		return p_77326_1_ instanceof GunEnchantment && p_77326_1_ != this;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() == Items.BOOK || stack.getItem() instanceof ItemGun
				|| stack.getItem() == Items.ENCHANTED_BOOK;
	}

	public boolean canApply(ItemStack stack) {
		System.out.println("trying to apply: " + stack.getItem().getRegistryName().toString());
		return (stack.getItem() instanceof ItemGun || stack.getItem() instanceof EnchantedBookItem
				|| stack.getItem() == Items.ENCHANTED_BOOK) ? true : super.canEnchant(stack);
	}

}
