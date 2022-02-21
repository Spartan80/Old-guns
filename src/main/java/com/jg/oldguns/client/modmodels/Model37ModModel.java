package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.Item;

public class Model37ModModel extends WrapperModel{

	public Model37ModModel(IBakedModel origin) {
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

}
