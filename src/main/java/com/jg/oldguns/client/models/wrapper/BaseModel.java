package com.jg.oldguns.client.models.wrapper;

import java.util.List;
import java.util.Random;

import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.utils.Constants;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ForgeModelBakery;

public abstract class BaseModel implements BakedModel {

	BakedModel origin;
	ItemGun gunitem;

	public BaseModel(BakedModel origin, ItemGun gunitem) {
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
	public ItemTransforms getTransforms() {
		return origin.getTransforms();
	}

	protected void addSpecialModel(List<BakedQuad> quads, String name, BlockState p_200117_1_, Random p_200117_3_) {
		BakedModel model = ModelHandler.INSTANCE.getModel(name);
		if (model != null && name != Constants.EMPTY) {
			for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
				if (ForgeModelBakery.instance() == null)
					break;

				quads.add(copyQuad(quad));
			}
		}
	}

	protected void addNonSpecialModel(List<BakedQuad> quads, Item item, BlockState p_200117_1_, Random p_200117_3_) {
		BakedModel model = ModelHandler.INSTANCE.getModel(item.getRegistryName());
		if (model != null && item != Items.AIR) {
			for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
				if (ForgeModelBakery.instance() == null)
					break;

				quads.add(copyQuad(quad));
			}
		}
	}

	protected void addNonSpecialModel(List<BakedQuad> quads, String itempath, BlockState p_200117_1_,
			Random p_200117_3_) {
		BakedModel model = ModelHandler.INSTANCE.getModel(itempath);
		if (model != null && itempath != Constants.EMPTY) {
			for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
				if (ForgeModelBakery.instance() == null)
					break;

				quads.add(copyQuad(quad));
			}
		}
	}

	protected void addGunQuads(List<BakedQuad> quads, BlockState p_200117_1_, Random p_200117_3_) {
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(gunitem.getBarrel().getRegistryName())
				.getQuads(p_200117_1_, null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(gunitem.getBody().getRegistryName()).getQuads(p_200117_1_,
				null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(gunitem.getStock().getRegistryName()).getQuads(p_200117_1_,
				null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}

	protected void addQuadsFromSpecial(List<BakedQuad> quads, String path, BlockState p_200117_1_, Random p_200117_3_) {
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(path).getQuads(p_200117_1_, null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}

	protected void addQuadsFromItem(List<BakedQuad> quads, Item item, BlockState p_200117_1_, Random p_200117_3_) {
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(item.getRegistryName()).getQuads(p_200117_1_, null,
				p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}
	
	protected void addQuadsFromItem(List<BakedQuad> quads, String item, BlockState p_200117_1_, Random p_200117_3_) {
		for (BakedQuad quad : ModelHandler.INSTANCE.getModel(item).getQuads(p_200117_1_, null,
				p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}

	protected BakedQuad copyQuad(BakedQuad quad) {
		return new BakedQuad(quad.getVertices().clone(), quad.getTintIndex(), quad.getDirection(), quad.getSprite(),
				quad.isShade());
	}
}
