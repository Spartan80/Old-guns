package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;

public class Model37ModModel extends WrapperModel{

	public Model37ModModel(BakedModel origin) {
		super(origin, ItemRegistries.Model37.get());
	}

	@Override
	public String getHammerPath() {
		return Paths.MODEL37LOADER;
	}

	@Override
	public Item getScope(int scopetype) {
		return null;
	}

	@Override
	public String[] getExtraStuff() {
		// TODO Auto-generated method stub
		return null;
	}

}
