package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;

public class GalilModModel extends WrapperModel {

	public GalilModModel(BakedModel origin, Item gunitem) {
		super(origin, gunitem);
	}

	@Override
	public String[] getExtraStuff() {
		return null;
	}

	@Override
	public String getHammerPath() {
		return Paths.GALILHAMMER;
	}

	@Override
	public Item getScope(int scopetype) {
		return null;
	}

}
