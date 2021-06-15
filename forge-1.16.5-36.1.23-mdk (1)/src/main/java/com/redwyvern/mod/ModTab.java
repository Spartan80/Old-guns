package com.redwyvern.mod;

import com.redwyvern.registries.ItemRegistries;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModTab extends ItemGroup {

	public ModTab() {
		super("oldguns");

	}

	@Override
	public ItemStack makeIcon() {
		// TODO Auto-generated method stub
		return new ItemStack(ItemRegistries.pirate_pistol.get());
	}

}
