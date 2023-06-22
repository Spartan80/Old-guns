package com.jg.oldguns.client.models.modmodels;

import java.util.Random;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;

public class Aks74uModModel extends WrapperModel {

	public Aks74uModModel(BakedModel origin) {
		super(origin, ItemRegistries.AKS74U.get());
	}

	@Override
	public void addExtraStuff(BlockState state, Random random) {
		
	}

}
