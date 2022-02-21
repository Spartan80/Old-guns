package com.jg.oldguns.client.models.wrapper;

import java.util.List;
import java.util.Random;

import com.jg.oldguns.client.models.ScopeTransforms;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

public class ScopeModel implements IBakedModel{

	String gunid;
	IBakedModel origin;

	public ScopeModel(IBakedModel origin, String gunid) {
		this.origin = origin;
		this.gunid = gunid;
	}
	
	@Override
	public List<BakedQuad> getQuads(BlockState p_200117_1_, Direction p_200117_2_, Random p_200117_3_) {
		return origin.getQuads(p_200117_1_, p_200117_2_, p_200117_3_);
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
	public ItemOverrideList getOverrides() {
		return origin.getOverrides();
	}
	
	@Override
	public ItemCameraTransforms getTransforms() {
		return ScopeTransforms.get(gunid);
	}
	
}
