package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.Item;

public class PirateRifleModModel extends WrapperModel {

	public PirateRifleModModel(IBakedModel origin) {
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

}
