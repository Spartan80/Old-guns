package com.jg.oldguns.client.handlers;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.parse.ANTLRParser.throwsSpec_return;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.wrapper.DynamicGunModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ForgeModelBakery;

@OnlyIn(Dist.CLIENT)
public enum ModelHandler {
	INSTANCE;

	public Map<String, DynamicGunModel> cache;
	public Map<Item, GunModel> gunmodels;
	public boolean init = false;
	public Map<String, ModelResourceLocation> currentmod;
	private Minecraft mc;

	private ModelHandler() {
		cache = new HashMap<String, DynamicGunModel>();
		gunmodels = new HashMap<Item, GunModel>();
		mc = Minecraft.getInstance();
	}
	
	public void registerGunModel(Item item, GunModel model) {
		System.out.println("Item path: " + item.getRegistryName().toString());
		if(gunmodels.containsKey(item)) {
			System.out.println("The key " + item.getRegistryName().toString() + " already exists");
			throw new IllegalStateException();
		}
		if(gunmodels.containsValue(model)) {
			System.out.println("The model for " + item.getRegistryName().toString() + " already exists");
			throw new IllegalStateException();
		}
		gunmodels.putIfAbsent(item, model);
	}

	public BakedModel getModel(String path) {
		return mc.getModelManager().getModel(new ModelResourceLocation(path, "inventory"));
	}

	public BakedModel getModel(ResourceLocation path) {
		return mc.getModelManager().getModel(new ModelResourceLocation(path, "inventory"));
	}

	public GunModel getGunModel(Item item) {
		return gunmodels.get(item);
	}

	public void initSpecialModels(Map<String, ModelResourceLocation> map) {
		for (String n : map.keySet()) {
			ForgeModelBakery.addSpecialModel(map.get(n));
		}
	}

	public void setInit() {
		System.out.println("Initializing models");
		init = true;
	}

	public Map<Item, GunModel> getGunModelMap() {
		return gunmodels;
	}
}
