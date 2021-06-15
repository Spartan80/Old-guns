package com.redwyvern.enchantments;

public class FastReloadEnchantment extends GunEnchantment {

	public FastReloadEnchantment() {
		super(Rarity.UNCOMMON, EnchantmentTypes.GUN);

	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public int getMinCost(int level) {
		// TODO Auto-generated method stub
		return 1 + 10 * (level - 1);
	}

	@Override
	public int getMaxCost(int level) {
		return super.getMaxCost(level) + 50;
	}
}
