package com.redwyvern.items;

import com.redwyvern.mod.OldGuns;

import net.minecraft.item.Item;

public class BigBullet extends Item {
	public BigBullet() {
		super(new Item.Properties().tab(OldGuns.modtab).stacksTo(64));
	}
}
