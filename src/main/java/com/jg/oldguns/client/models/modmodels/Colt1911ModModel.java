package com.jg.oldguns.client.models.modmodels;

import java.util.Random;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;

public class Colt1911ModModel extends WrapperModel {

	public Colt1911ModModel(BakedModel origin) {
		super(origin, ItemRegistries.COLT1911.get());
	}

	@Override
	public void addExtraStuff(BlockState state, Random random) {
		addSpecialModel(quads, Paths.COLT1911SLIDER, state, random);
	}

}
