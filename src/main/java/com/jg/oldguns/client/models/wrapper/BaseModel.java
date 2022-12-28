package com.jg.oldguns.client.models.wrapper;

import java.util.List;

import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class BaseModel implements BakedModel {

	BakedModel origin;
	GunItem gunitem;

	public BaseModel(BakedModel origin, GunItem gunitem) {
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

	protected void addSpecialModel(List<BakedQuad> quads, String name, BlockState p_200117_1_, RandomSource p_200117_3_) {
		BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper()
				.getModelManager().getModel(new ModelResourceLocation(name, "inventory"));
		if (model != null) {
			for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
				quads.add(copyQuad(quad));
			}
		}
	}

	protected void addNonSpecialModel(List<BakedQuad> quads, Item item, BlockState p_200117_1_, RandomSource p_200117_3_) {
		BakedModel model = Utils.getModel(item);//ModelHandler.INSTANCE.getModel(item.getDescriptionId());
		if (model != null && item != Items.AIR) {
			for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
				quads.add(copyQuad(quad));
			}
		}
	}

	protected void addNonSpecialModel(List<BakedQuad> quads, String itempath, BlockState p_200117_1_,
			RandomSource p_200117_3_) {
		BakedModel model = Utils.getModel(ForgeRegistries.ITEMS
				.getValue(new ResourceLocation(itempath)));// ModelHandler.INSTANCE.getModel(itempath);
		if (model != null) {
			for (BakedQuad quad : model.getQuads(p_200117_1_, null, p_200117_3_)) {
				quads.add(copyQuad(quad));
			}
		}
	}

	protected void addGunQuads(List<BakedQuad> quads, BlockState p_200117_1_, RandomSource source) {
		addQuadsFromItem(quads, gunitem.getStuff().getBarrel(), p_200117_1_, source);
		addQuadsFromItem(quads, gunitem.getStuff().getBody(), p_200117_1_, source);
		addQuadsFromItem(quads, gunitem.getStuff().getStock(), p_200117_1_, source);
	}

	protected void addQuadsFromSpecial(List<BakedQuad> quads, String path, BlockState p_200117_1_, RandomSource p_200117_3_) {
		for (BakedQuad quad : Minecraft.getInstance().getItemRenderer().getItemModelShaper()
				.getModelManager().getModel(new ModelResourceLocation(path, "inventory"))
				.getQuads(p_200117_1_, null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}

	protected void addQuadsFromItem(List<BakedQuad> quads, Item item, BlockState p_200117_1_, RandomSource p_200117_3_) {
		for (BakedQuad quad : Minecraft.getInstance().getItemRenderer().getItemModelShaper()
				.getItemModel(item).getQuads(p_200117_1_, null,
				p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}

	protected void addQuadsFromItem(List<BakedQuad> quads, String item, BlockState p_200117_1_, RandomSource p_200117_3_) {
		for (BakedQuad quad : Minecraft.getInstance().getItemRenderer().getItemModelShaper()
				.getModelManager().getModel(new ModelResourceLocation(item, "inventory")).getQuads(p_200117_1_, null, p_200117_3_)) {
			quads.add(copyQuad(quad));
		}
	}

	protected BakedQuad copyQuad(BakedQuad quad) {
		return new BakedQuad(quad.getVertices().clone(), quad.getTintIndex(), quad.getDirection(), quad.getSprite(),
				quad.isShade());
	}
}