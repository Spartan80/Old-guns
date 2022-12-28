package com.jg.oldguns.client.models.modmodels;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class GalilModModel extends WrapperModel {

	public GalilModModel(BakedModel origin) {
		super(origin, ItemRegistries.GALIL.get());
	}

	@Override
	public void addExtraStuff(BlockState state, RandomSource random) {
		
	}

}