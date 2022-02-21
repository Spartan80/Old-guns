package com.jg.oldguns.client.modmodels;

import java.util.Random;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.Item;

public class ColtModModel extends WrapperModel {

	public ColtModModel(IBakedModel origin) {
		super(origin, ItemRegistries.Colt1911.get());

	}

	@Override
	protected void initQuads(BlockState p_200117_1_, Random p_200117_3_) {
		addGunQuads(quads, p_200117_1_, p_200117_3_);
		addQuadsFromSpecial(quads, getHammerPath(), p_200117_1_, p_200117_3_);
		addQuadsFromSpecial(quads, Paths.COLTSLIDER, p_200117_1_, p_200117_3_);
	}

	@Override
	public Item getScope(int arg0) {
		return null;
	}

	@Override
	public String getHammerPath() {
		return Paths.COLTHAM;
	}

}
