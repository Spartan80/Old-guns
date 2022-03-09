package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;

public class ThompsonModModel extends WrapperModel {

	public ThompsonModModel(BakedModel origin) {
		super(origin, ItemRegistries.Thompson.get());
	}

	@Override
	public Item getScope(int arg0) {
		return null;
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.THOMPSONH;
	}

}
