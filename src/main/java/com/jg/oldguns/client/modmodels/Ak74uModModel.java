package com.jg.oldguns.client.modmodels;

import java.util.List;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;

public class Ak74uModModel extends WrapperModel{

	public Ak74uModModel(BakedModel origin) {
		super(origin, ItemRegistries.Ak74u.get());
	}

	@Override
	public String getHammerPath() {
		return Paths.AK74UHAMMER;
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
