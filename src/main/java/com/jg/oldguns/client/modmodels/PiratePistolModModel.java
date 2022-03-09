package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;

public class PiratePistolModModel extends WrapperModel {

	public PiratePistolModModel(BakedModel origin) {
		super(origin, ItemRegistries.PiratePistol.get());
	}

	@Override
	public Item getScope(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.PPHAM;
	}

}
