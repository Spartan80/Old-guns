package com.jg.oldguns.client.models.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DynamicGunModel extends BaseModel {

	ItemStack stack;
	List<BakedQuad> quads;
	String[] extraStuff;
	boolean init = false;
	boolean firstTime;

	public DynamicGunModel(BakedModel origin, ItemGun gunitem, ItemStack stack, String[] extraStuff) {
		super(origin, gunitem);
		this.stack = stack;
		this.quads = new ArrayList<BakedQuad>();
		this.extraStuff = extraStuff;
		this.firstTime = true;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState p_200117_1_, Direction p_200117_2_, Random p_200117_3_) {
		//System.out.println(stack.getItem().getRegistryName().toString());
		if (!init || firstTime) {
			if(!firstTime) {
				this.stack = Util.getStack();
			}
			quads.clear();
			
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
			
			if(ServerUtils.hasMag(stack) && (firstTime ? true : ServerUtils.getId(stack) == ServerUtils.getId(Util.getStack()))/* && !gunitem.isPistol()*/) {
				System.out.println("Original stack id " + ServerUtils.getId(stack) + " actual stack id " + ServerUtils.getId(Util.getStack()) + " original stack item: " + stack.getItem().getRegistryName().toString() + " actual stack item: " + Util.getStack().getItem().getRegistryName().toString());
				String magpath = ServerUtils.getMagPath(stack);
				if(!magpath.equals("")) {
					addNonSpecialModel(quads, magpath, p_200117_1_, p_200117_3_);
				}
			}
			
			if(firstTime) {
				System.out.println("Reconstructing for first time item " + stack.getItem().getRegistryName().toString());
				firstTime = false;
			}
			
			if(extraStuff != null) {
				for(String s : extraStuff) {
					for (BakedQuad quad : ModelHandler.INSTANCE.getModel(s).getQuads(p_200117_1_, null, p_200117_3_)) {
						quads.add(copyQuad(quad));
					}
				}
			}
			
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
	public ItemOverrides getOverrides() {
		return null;
	}

}
