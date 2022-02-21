package com.jg.oldguns.client.models.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;

public class DynamicGunModel extends BaseModel {

	ItemStack stack;
	List<BakedQuad> quads;
	List<BakedQuad> extraquads;
	boolean init = false;

	public DynamicGunModel(IBakedModel origin, ItemGun gunitem, ItemStack stack) {
		super(origin, gunitem);
		this.stack = stack;
		quads = new ArrayList<BakedQuad>();
		extraquads = new ArrayList<BakedQuad>();
	}

	@Override
	public List<BakedQuad> getQuads(BlockState p_200117_1_, Direction p_200117_2_, Random p_200117_3_) {

		if (!init) {
			this.stack = Util.getStack();
			quads.clear();
			for (BakedQuad quad : extraquads) {
				quads.add(copyQuad(quad));
			}
			if (!ServerUtils.getBarrelMouth(stack).equals("")) {
				addNonSpecialModel(quads, ServerUtils.getBarrelMouth(stack), p_200117_1_, p_200117_3_);
			}
			if (ServerUtils.isBarrelEmpty(stack)) {
				addNonSpecialModel(quads, gunitem.getBarrel(), p_200117_1_, p_200117_3_);
			} else {
				addNonSpecialModel(quads, ServerUtils.getBarrel(stack), p_200117_1_, p_200117_3_);
			}

			if (ServerUtils.isBodyEmpty(stack)) {
				addNonSpecialModel(quads, gunitem.getBody(), p_200117_1_, p_200117_3_);
			} else {
				addNonSpecialModel(quads, ServerUtils.getBody(stack), p_200117_1_, p_200117_3_);
			}

			if (ServerUtils.isStockEmpty(stack)) {
				addNonSpecialModel(quads, gunitem.getStock(), p_200117_1_, p_200117_3_);
			} else {
				addNonSpecialModel(quads, ServerUtils.getStock(stack), p_200117_1_, p_200117_3_);
			}
			
			/*if (!ServerUtils.isScopeEmpty(stack)) {
				IBakedModel model = new ScopeModel(ModelHandler.INSTANCE.getModel(ServerUtils.getScopePath(stack)), gunitem.getGunId());//ModelHandler.INSTANCE.getModel(ServerUtils.getScopePath(stack));
				if (model != null) {
					for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
						if (ModelLoader.instance() == null)
							break;

						quads.add(copyQuad(quad));
					}
				}
			}*/
			
			init = true;
		}

		if (p_200117_2_ == null) {
			return quads;
		} else {
			return Util.empty;
		}
	}

	public void reconstruct() {
		init = false;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}

}
