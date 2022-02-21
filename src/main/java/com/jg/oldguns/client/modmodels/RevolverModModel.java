package com.jg.oldguns.client.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.Item;

public class RevolverModModel extends WrapperModel{

	public RevolverModModel(IBakedModel origin) {
		super(origin, ItemRegistries.Revolver.get());
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.REVOLVERHAMMER;
	}

	@Override
	public Item getScope(int scopetype) {
		// TODO Auto-generated method stub
		return null;
	}

}
