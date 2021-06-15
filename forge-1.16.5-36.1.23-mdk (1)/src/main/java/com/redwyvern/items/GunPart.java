package com.redwyvern.items;

import com.redwyvern.mod.OldGuns;

import net.minecraft.item.Item;

public class GunPart extends Item {

	public GunPart() {
		super(new Item.Properties().tab(OldGuns.modtab).stacksTo(16));

	}

}
