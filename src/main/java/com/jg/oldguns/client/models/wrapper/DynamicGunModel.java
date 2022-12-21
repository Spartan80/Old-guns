package com.jg.oldguns.client.models.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DynamicGunModel extends BaseModel {

	ItemStack stack;
	List<BakedQuad> quads;
	boolean init = false;
	boolean firstTime;
	
	public DynamicGunModel(BakedModel origin, GunItem gunitem, ItemStack stack) {
		super(origin, gunitem);
		this.stack = stack;
		this.quads = new ArrayList<BakedQuad>();
		this.firstTime = true;
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction dir, RandomSource random) {
		if (!init || firstTime) {
			Minecraft mc = Minecraft.getInstance();
			if (!firstTime) {
				this.stack = mc.player.getMainHandItem();
			}
			quads.clear();

			String barrel = NBTUtils.getBarrel(stack);
			LogUtils.getLogger().info("Barrel: " + barrel);
			if (barrel.equals("")) {
				addNonSpecialModel(quads, gunitem.getStuff().getBarrel(), state, random);
			} else {
				addNonSpecialModel(quads, barrel, state, random);
			}
			String body = NBTUtils.getBody(stack);
			if (body.equals("")) {
				addNonSpecialModel(quads, gunitem.getStuff().getBody(), state, random);
			} else {
				addNonSpecialModel(quads, body, state, random);
			}

			String stock = NBTUtils.getStock(stack);
			if (stock.equals("")) {
				addNonSpecialModel(quads, gunitem.getStuff().getStock(), state, random);
			} else {
				addNonSpecialModel(quads, stock, state, random);
			}

			if (!NBTUtils.getMag(stack).equals("") && (firstTime ? true
					: NBTUtils.getId(stack).equals(NBTUtils
							.getId(Minecraft.getInstance().player.getMainHandItem())))/* && !gunitem.isPistol() */) {
				String magpath = NBTUtils.getMag(stack);
				if (!magpath.equals("")) {
					addNonSpecialModel(quads, magpath, state, random);
				}
			}

			if (firstTime) {
				firstTime = false;
			}

			init = true;
		}
		
		if (dir == null) {
			return quads;
		} else {
			return Utils.EMPTY;
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
