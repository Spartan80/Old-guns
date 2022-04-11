package com.jg.oldguns.recipes.gunrecipes;

import com.jg.oldguns.guns.ItemGun;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public abstract class GunRecipe {

	public ItemGun gun;
	public String id;

	public GunRecipe(ItemGun gun) {
		this.gun = gun;
		this.id = gun.getGunId();
	}

	public abstract boolean isAble(Inventory inv);

	public abstract boolean areEquals(Inventory inv);

	public abstract ItemStack getResult(Inventory cinv);

}
