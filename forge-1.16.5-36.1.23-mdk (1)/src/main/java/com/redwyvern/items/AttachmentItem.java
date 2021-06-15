package com.redwyvern.items;

import com.redwyvern.mod.OldGuns;

import net.minecraft.item.Item;

public class AttachmentItem extends Item {

	public AttachmentItem() {
		super(new Item.Properties().tab(OldGuns.modtab).stacksTo(64));
	}

}
