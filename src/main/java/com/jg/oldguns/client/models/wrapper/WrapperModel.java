package com.jg.oldguns.client.models.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Level;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class WrapperModel extends BaseModel {

	protected List<BakedQuad> quads;
	protected GunWithMagModel gunwithmagmodel;
	protected boolean constructed;
	protected boolean reconstruct;

	public WrapperModel(BakedModel origin, Item gunitem) {
		super(origin, (ItemGun) gunitem);
		quads = new ArrayList<BakedQuad>();
		gunwithmagmodel = new GunWithMagModel(origin, this.gunitem, getHammerPath());
		constructed = false;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState p_200117_1_, Direction p_200117_2_, Random p_200117_3_) {
		if (!constructed) {
			quads.clear();
			initQuads(p_200117_1_, p_200117_3_);
			constructed = true;
		}
		
		if (p_200117_2_ == null) {
			return quads;
		} else {
			return Util.empty;
		}
	}
	
	public void setReconstruct() {
		this.reconstruct = true;
	}
	
	public void reconstruct(ItemStack stack) {
		String id = ServerUtils.getId(stack);
		if(!id.equals("")) {
			DynamicGunModel model = ModelHandler.INSTANCE.cache.get(id);
			if(model != null) {
				model.reconstruct();
				System.out.println("Reconstructing");
			}else {
				System.out.println("Model is null");
			}
		}else {
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
			public BakedModel resolve(BakedModel origin, ItemStack stack, ClientLevel p_173467_,
					LivingEntity p_173468_, int p_173469_) {
				if (ServerUtils.hasModParts(stack) || !ServerUtils.getMagPath(stack).equals("")) {
					String id = stack.getOrCreateTag().getString(Constants.ID);
					if (!id.equals("")) {
						if (!ModelHandler.INSTANCE.cache.containsKey(id)) {
							System.out.println("Override Barrel: " + ServerUtils.getBarrel(stack) + " Body: "
									+ ServerUtils.getBody(stack) + " Stock: " + ServerUtils.getStock(stack));
							ModelHandler.INSTANCE.cache.put(id, new DynamicGunModel(origin, gunitem, stack, getExtraStuff()));
							System.out.println("lol1 method stack item: " + stack.getItem().getRegistryName().toString() + " util stack item " + Util.getStack().getItem().getRegistryName().toString());
						}
					}
					if (ModelHandler.INSTANCE.cache.containsKey(id)) {
						if(reconstruct) {
							reconstruct(stack);
							System.out.println("lol2 method stack item: " + stack.getItem().getRegistryName().toString() + " util stack item " + Util.getStack().getItem().getRegistryName().toString());
							reconstruct = false;
						}
						//System.out.println("lol3 method stack item: " + stack.getItem().getRegistryName().toString() + " util stack item " + Util.getStack().getItem().getRegistryName().toString());
						return ModelHandler.INSTANCE.cache.get(id);
					} else {
						System.out.println("lol4 method stack item: " + stack.getItem().getRegistryName().toString() + " util stack item " + Util.getStack().getItem().getRegistryName().toString());
						return origin;
					}
				} else {
					//System.out.println("lol5 method stack item: " + stack.getItem().getRegistryName().toString() + " util stack item " + Util.getStack().getItem().getRegistryName().toString());
					return origin;
				}
			}
		};
	}

	protected void initQuads(BlockState p_200117_1_, Random p_200117_3_) {
		addGunQuads(quads, p_200117_1_, p_200117_3_);
		if (getHammerPath() != null) {
			addQuadsFromSpecial(quads, getHammerPath(), p_200117_1_, p_200117_3_);
		}
	}

	public abstract String[] getExtraStuff();
	
	public abstract String getHammerPath();

	public abstract Item getScope(int scopetype);

}
