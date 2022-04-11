package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;

public class PirateRifleModModel extends WrapperModel {

	public PirateRifleModModel(BakedModel origin) {
		super(origin, ItemRegistries.PirateRifle.get());

	}

	@Override
	public Item getScope(int scopetype) {
		return null;
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.PRH;
	}

	@Override
	public String[] getExtraStuff() {
		// TODO Auto-generated method stub
		return null;
	}

}
