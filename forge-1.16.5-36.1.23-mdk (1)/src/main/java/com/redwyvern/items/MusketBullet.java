package com.redwyvern.items;

import com.redwyvern.mod.OldGuns;

import net.minecraft.item.Item;

public class MusketBullet extends Item {

	public MusketBullet() {
		super(new Item.Properties().tab(OldGuns.modtab).stacksTo(64));
	}

}
