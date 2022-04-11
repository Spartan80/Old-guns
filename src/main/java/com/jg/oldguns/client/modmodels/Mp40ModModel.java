package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;

public class Mp40ModModel extends WrapperModel {

	public Mp40ModModel(BakedModel origin) {
		super(origin, ItemRegistries.Mp40.get());

	}

	@Override
	public Item getScope(int scopetype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.MP40H;
	}

	@Override
	public String[] getExtraStuff() {
		// TODO Auto-generated method stub
		return null;
	}

}
