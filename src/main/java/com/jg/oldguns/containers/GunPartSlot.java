package com.jg.oldguns.containers;

import com.jg.oldguns.guns.GunPart;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GunPartSlot extends Slot {

	public GunPartSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}

	@Override
	public boolean mayPlace(ItemStack p_75214_1_) {
		Item i = p_75214_1_.getItem();
		if (i instanceof GunPart) {
			GunPart part = (GunPart) i;
			return part.getGunSlot() == this.index;
		}
		return false;
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	@Override
	public int getMaxStackSize(ItemStack p_178170_1_) {
		return 1;
	}

}
