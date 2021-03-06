package com.jg.oldguns.client.modmodels;

import java.util.Random;

import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public class LugerP08ModModel extends WrapperModel {
	
	public LugerP08ModModel(BakedModel origin) {
		super(origin, ItemRegistries.LugerP08.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initQuads(BlockState p_200117_1_, Random p_200117_3_) {
		addGunQuads(quads, p_200117_1_, p_200117_3_);
		addQuadsFromSpecial(quads, getHammerPath(), p_200117_1_, p_200117_3_);
		addQuadsFromSpecial(quads, Paths.LUGERP08HF, p_200117_1_, p_200117_3_);
	}

	@Override
	public Item getScope(int scopetype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.LUGERP08HB;
	}

	@Override
	public String[] getExtraStuff() {
		// TODO Auto-generated method stub
		return new String[] { Paths.LUGERP08HF };
	}

}
