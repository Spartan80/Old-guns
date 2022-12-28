package com.jg.oldguns.client.models.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.logging.LogUtils;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class WrapperModel extends BaseModel {
	protected List<BakedQuad> quads;
	protected boolean constructed;
	protected boolean reconstruct;

	public WrapperModel(BakedModel origin, Item gunitem) {
		super(origin, (GunItem) gunitem);
		quads = new ArrayList<BakedQuad>();
		constructed = false;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState p_200117_1_, Direction p_200117_2_, RandomSource p_200117_3_) {
		if (!constructed) {
			quads.clear();
			initQuads(p_200117_1_, p_200117_3_);
			constructed = true;
		}

		if (p_200117_2_ == null) {
			return quads;
		} else {
			return Utils.EMPTY;
		}
	}

	public void setReconstruct() {
		this.reconstruct = true;
	}

	public void reconstruct(ItemStack stack) {
		String id = NBTUtils.getId(stack);
		if (!id.equals("")) {
			DynamicGunModel model = ModelHandler.INSTANCE.getModel(id);
			if (model != null) {
				model.reconstruct();
				System.out.println("Reconstructing");
			} else {
				System.out.println("Model is null");
			}
		} else {
			OldGuns.LOGGER.log(Level.ERROR, "This stack doesnt have an id");
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
	public ItemTransforms getTransforms() {
		return origin.getTransforms();
	}

	@Override
	public ItemOverrides getOverrides() {
		return new ItemOverrides() {
			@Override
			public BakedModel resolve(BakedModel origin, ItemStack stack, ClientLevel p_173467_, LivingEntity p_173468_,
					int p_173469_) {
				if (NBTUtils.getModified(stack)) {
					String id = stack.getOrCreateTag().getString(NBTUtils.ID);
					if (!id.equals("")) {
						if (!ModelHandler.INSTANCE.getModels().containsKey(id)) {
							ModelHandler.INSTANCE.getModels().put(id,
									new DynamicGunModel(origin, gunitem, stack));
							LogUtils.getLogger().info("Set new model");
						}
					}
					if (ModelHandler.INSTANCE.getModels().containsKey(id)) {
						if (reconstruct) {
							reconstruct(stack);
							LogUtils.getLogger().info("Reconstructing");
							reconstruct = false;
						}
						//LogUtils.getLogger().info("Already registered");
						return ModelHandler.INSTANCE.getModel(id);
					} else {
						//LogUtils.getLogger().info("Id not registered -> Origin");
						return origin;
					}
				} else {
					//LogUtils.getLogger().info("Origin");
					return origin;
				}
			}
		};
	}

	protected void initQuads(BlockState p_200117_1_, RandomSource p_200117_3_) {
		addGunQuads(quads, p_200117_1_, p_200117_3_);
		if (gunitem.getStuff().getHammers().length != 0) {
			for(String hammer : gunitem.getStuff().getHammers()) {
				addQuadsFromSpecial(quads, hammer, p_200117_1_, p_200117_3_);
			}
		}
	}

	public abstract void addExtraStuff(BlockState state, RandomSource random);
}
