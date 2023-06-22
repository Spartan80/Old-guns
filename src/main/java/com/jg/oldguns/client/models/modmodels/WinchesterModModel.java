package com.jg.oldguns.client.models.modmodels;

import java.util.Random;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.RandomSource;

public class WinchesterModModel extends WrapperModel {

	public WinchesterModModel(BakedModel origin) {
		super(origin, ItemRegistries.WINCHESTER.get());
	}

	@Override
	public void addExtraStuff(BlockState state, Random random) {
		
	}

}
