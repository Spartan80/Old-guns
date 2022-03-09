package com.jg.oldguns.client.modmodels;

import java.util.Random;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public class ColtModModel extends WrapperModel {

	public ColtModModel(BakedModel origin) {
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
