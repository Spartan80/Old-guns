package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;

public class Kar98kModModel extends WrapperModel {

	public Kar98kModModel(BakedModel origin) {
		super(origin, ItemRegistries.Kar98k.get());
	}

	@Override
	public Item getScope(int arg0) {
		return null;
	}

	@Override
	public String getHammerPath() {
		return Paths.KARKHAM;
	}

	@Override
	public String[] getExtraStuff() {
		// TODO Auto-generated method stub
		return null;
	}

}
