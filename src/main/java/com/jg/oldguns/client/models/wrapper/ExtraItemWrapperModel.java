package com.jg.oldguns.client.models.wrapper;

import java.util.List;

import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class ExtraItemWrapperModel implements BakedModel {

	BakedModel origin;
	
	public ExtraItemWrapperModel(BakedModel origin) {
		this.origin = origin;
	}
	
	@Override
	public List<BakedQuad> getQuads(BlockState p_235039_, Direction p_235040_, RandomSource p_235041_) {
		return origin.getQuads(p_235039_, p_235040_, p_235041_);
	}
	
	@Override
	public BakedModel applyTransform(TransformType transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
		if(transformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
			if(Utils.getStack().getItem() instanceof GunItem) {
				transformType = TransformType.HEAD;
				//LogUtils.getLogger().info("HEAD");
			}
		}
		return origin.applyTransform(transformType, poseStack, applyLeftHandTransform);
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
	public ItemOverrides getOverrides() {
		return origin.getOverrides();
	}

}
