package com.jg.oldguns.client.models.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;

public abstract class WrapperModel extends BaseModel {

	protected List<BakedQuad> quads;
	protected GunWithMagModel gunwithmagmodel;
	protected boolean constructed;

	public WrapperModel(IBakedModel origin, Item gunitem) {
		super(origin, (ItemGun) gunitem);
		quads = new ArrayList<BakedQuad>();
		gunwithmagmodel = new GunWithMagModel(origin, this.gunitem, getHammerPath());
		constructed = false;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState p_200117_1_, Direction p_200117_2_, Random p_200117_3_) {

		// System.out.println("on WrapperModel quads");
		// System.out.println("on WrapperModel quads");

		if (!constructed) {
			quads.clear();
			initQuads(p_200117_1_, p_200117_3_);
			constructed = true;
			// System.out.println("on WrapperModel quads");
		}

		// constructed = false;

		// System.out.println(origin.getQuads(p_200117_1_, p_200117_2_,
		// p_200117_3_).isEmpty() + " Direction: " + p_200117_2_);

		if (p_200117_2_ == null) {
			return quads;
		} else {
			return Util.empty;
		}
	}

	@Override
	public boolean useAmbientOcclusion() {
		return origin.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return origin.isGui3d();
	}

	@Override
	public boolean usesBlockLight() {
		return origin.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer() {
		return origin.isCustomRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return origin.getParticleIcon();
	}

	@Override
	public ItemCameraTransforms getTransforms() {
		return origin.getTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return new ItemOverrideList() {
			@Override
			public IBakedModel resolve(IBakedModel origin, ItemStack stack, ClientWorld world, LivingEntity entity) {

				if (ServerUtils.hasModParts(stack)) {
					// System.out.println("Has modparts");
					String id = stack.getOrCreateTag().getString(Constants.ID);
					if (!id.equals("")) {
						// System.out.println("id not empty");
						if (!ModelHandler.INSTANCE.cache.containsKey(id)) {
							// System.out.println("Contains key");
							System.out.println("Override Barrel: " + ServerUtils.getBarrel(stack) + " Body: "
									+ ServerUtils.getBody(stack) + " Stock: " + ServerUtils.getStock(stack));
							ModelHandler.INSTANCE.cache.put(id, new DynamicGunModel(origin, gunitem, stack));
						}
					}
					if (ModelHandler.INSTANCE.cache.containsKey(id)) {
						// System.out.println("Custom model");
						return ModelHandler.INSTANCE.cache.get(id);
					} else {
						System.out.println("Origin with modparts");
						return origin;
					}
				} else {
					// System.out.println("Origin");
					return origin;
				}

				/*
				 * if(ServerUtils.hasMag(stack) && WrapperModel.this.gunitem.requiresMag()) {
				 * //System.out.println(ServerUtils.hasMag(stack)); return gunwithmagmodel;
				 * }else { //System.out.println(ServerUtils.hasMag(stack)); return
				 * WrapperModel.this; }
				 */
			}
		};
	}

	protected void initQuads(BlockState p_200117_1_, Random p_200117_3_) {
		addGunQuads(quads, p_200117_1_, p_200117_3_);
		if (getHammerPath() != null) {
			addQuadsFromSpecial(quads, getHammerPath(), p_200117_1_, p_200117_3_);
		}
		System.out.println(ServerUtils.getScope(Util.getStack()));
	}

	public abstract String getHammerPath();

	public abstract Item getScope(int scopetype);

}
