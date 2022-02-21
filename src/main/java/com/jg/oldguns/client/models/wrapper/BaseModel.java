package com.jg.oldguns.client.models.wrapper;

import java.util.List;
import java.util.Random;

import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.utils.Constants;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.client.model.ModelLoader;

public abstract class BaseModel implements IBakedModel {

	IBakedModel origin;
	ItemGun gunitem;

	public BaseModel(IBakedModel origin, ItemGun gunitem) {
		this.origin = origin;
		this.gunitem = gunitem;
	}

	@Override
	public boolean useAmbientOcclusion() {
		return origin.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return false;
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

	protected void addSpecialModel(List<BakedQuad> quads, String name, BlockState p_200117_1_, Random p_200117_3_) {
		// IBakedModel model = ModelHandler.INSTANCE.getSpecialModel(name);
		IBakedModel model = ModelHandler.INSTANCE.getModel(name);
		if (model != null && name != Constants.EMPTY) {
			for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
				if (ModelLoader.instance() == null)
					break;

				quads.add(copyQuad(quad));
			}
		}
	}

	protected void addNonSpecialModel(List<BakedQuad> quads, Item item, BlockState p_200117_1_, Random p_200117_3_) {
		// IBakedModel model = ModelHandler.INSTANCE.getNonSpecialModel(item);
		IBakedModel model = ModelHandler.INSTANCE.getModel(item.getRegistryName());
		if (model != null && item != Items.AIR) {
			for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
				if (ModelLoader.instance() == null)
					break;

				quads.add(copyQuad(quad));
			}
		}
	}

	protected void addNonSpecialModel(List<BakedQuad> quads, String itempath, BlockState p_200117_1_,
			Random p_200117_3_) {
		// IBakedModel model = ModelHandler.INSTANCE.getNonSpecialModel(itempath);
		IBakedModel model = ModelHandler.INSTANCE.getModel(itempath);
		if (model != null && itempath != Constants.EMPTY) {
			for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
				if (ModelLoader.instance() == null)
					break;

				quads.add(copyQuad(quad));
			}
		}
	}

	protected void addGunQuads(List<BakedQuad> quads, BlockState p_200117_1_, Random p_200117_3_) {
		// for(BakedQuad quad :
		// ModelHandler.INSTANCE.getNonSpecialModel(gunitem.getBarrel()).getQuads(p_200117_1_,
		// null, p_200117_3_)) {
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(gunitem.getBarrel().getRegistryName())
				.getQuads(p_200117_1_, null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
		// for(BakedQuad quad :
		// ModelHandler.INSTANCE.getNonSpecialModel(gunitem.getBody()).getQuads(p_200117_1_,
		// null, p_200117_3_)) {
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(gunitem.getBody().getRegistryName()).getQuads(p_200117_1_,
				null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
		// for(BakedQuad quad :
		// ModelHandler.INSTANCE.getNonSpecialModel(gunitem.getStock()).getQuads(p_200117_1_,
		// null, p_200117_3_)) {
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(gunitem.getStock().getRegistryName()).getQuads(p_200117_1_,
				null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}

	protected void addQuadsFromSpecial(List<BakedQuad> quads, String path, BlockState p_200117_1_, Random p_200117_3_) {
		// for(BakedQuad quad :
		// ModelHandler.INSTANCE.getSpecialModel(path).getQuads(p_200117_1_, null,
		// p_200117_3_)) {
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(path).getQuads(p_200117_1_, null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}

	protected void addQuadsFromItem(List<BakedQuad> quads, Item item, BlockState p_200117_1_, Random p_200117_3_) {
		// for(BakedQuad quad :
		// ModelHandler.INSTANCE.getNonSpecialModel(item).getQuads(p_200117_1_, null,
		// p_200117_3_)) {
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(item.getRegistryName()).getQuads(p_200117_1_, null,
				p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}

	protected BakedQuad copyQuad(BakedQuad quad) {
		return new BakedQuad(quad.getVertices().clone(), quad.getTintIndex(), quad.getDirection(), quad.getSprite(),
				quad.isShade());
	}
}
