package com.jg.oldguns.client.models.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jg.oldguns.guns.ItemGun;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class GunWithMagModel extends BaseModel {

	List<BakedQuad> quads;

	private boolean constructed;

	private String hp;

	private ItemGun gunitem;

	public GunWithMagModel(BakedModel origin, ItemGun gunitem, String hp) {
		super(origin, gunitem);
		this.hp = hp;
		this.gunitem = gunitem;
		quads = new ArrayList<BakedQuad>();
		constructed = false;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState p_200117_1_, Direction p_200117_2_, Random p_200117_3_) {

		// System.out.println("on GunWithMagModel quads");

		if (!constructed) {
			quads.clear();
			initQuads(p_200117_1_, p_200117_3_);
			constructed = true;
			// System.out.println("constructing");
		}

		// constructed = false;

		if (p_200117_2_ == null) {
			return quads;
		} else {
			return new ArrayList<BakedQuad>();
		}
	}

	protected void initQuads(BlockState p_200117_1_, Random p_200117_3_) {
		addGunQuads(quads, p_200117_1_, p_200117_3_);
		if (hp != null) {
			addQuadsFromSpecial(quads, hp, p_200117_1_, p_200117_3_);
		}
		if (gunitem.requiresMag(Minecraft.getInstance().player.getMainHandItem())) {
			addQuadsFromItem(quads, gunitem.getDefaultMag(), p_200117_1_, p_200117_3_);
		}
	}

	@Override
	public ItemOverrides getOverrides() {
		// TODO Auto-generated method stub
		return null;
	}

}
