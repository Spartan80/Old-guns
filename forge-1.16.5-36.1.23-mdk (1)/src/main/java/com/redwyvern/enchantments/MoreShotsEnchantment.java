package com.redwyvern.enchantments;

public class MoreShotsEnchantment extends GunEnchantment {

	public MoreShotsEnchantment() {
		super(Rarity.VERY_RARE, EnchantmentTypes.GUN);

	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public int getMinCost(int level) {
		return 1 + 10 * (level - 1);
	}

	@Override
	public int getMaxCost(int level) {
		return super.getMinCost(level) + 50;
	}
}
