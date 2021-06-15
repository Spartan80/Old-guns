package com.redwyvern.gun;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

public enum GunTiers implements IItemTier {
	SMALL(100, null), MEDIUM(300, null), LARGE(500, null);

	private int maxUses;
	private Item repairMaterial;

	GunTiers(int maxUses, Item ingredient) {
		this.maxUses = maxUses;
		this.repairMaterial = ingredient;
	}

	@Override
	public int getUses() {
		// TODO Auto-generated method stub
		return maxUses;
	}

	@Override
	public float getAttackDamageBonus() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Ingredient getRepairIngredient() {
		// TODO Auto-generated method stub
		return Ingredient.of(repairMaterial);// Ingredient.fromItems(Item);
	}

	@Override
	public float getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEnchantmentValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
