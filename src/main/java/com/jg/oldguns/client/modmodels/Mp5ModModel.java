package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;

public class Mp5ModModel extends WrapperModel {

	public Mp5ModModel(BakedModel origin, Item gunitem) {
		super(origin, gunitem);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] getExtraStuff() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.MP5HAMMER;
	}

	@Override
	public Item getScope(int scopetype) {
		// TODO Auto-generated method stub
		return null;
	}

}
