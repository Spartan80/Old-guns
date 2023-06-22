package com.jg.oldguns.client.screens.widgets;

import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.screens.widgets.JGSelectionList.Key;

import net.minecraft.client.gui.Font;

public class GunPartKey extends Key {

	protected GunModelPart part;
	
	public GunPartKey(Font font, GunModelPart part) {
		super(font, part.getName());
		this.part = part;
	}
	
	public GunModelPart getPart() {
		return part;
	}
	
}
