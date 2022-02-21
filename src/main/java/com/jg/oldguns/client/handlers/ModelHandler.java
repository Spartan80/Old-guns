package com.jg.oldguns.client.handlers;

import java.util.HashMap;
import java.util.Map;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.wrapper.DynamicGunModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;

@OnlyIn(Dist.CLIENT)
public enum ModelHandler {
	INSTANCE;

	public Map<String, DynamicGunModel> cache;
	private Map<String, ModelResourceLocation> modelres;
	public Map<Item, GunModel> gunmodels;
	public Map<String, Map<String, ModelResourceLocation>> modsmodels;
	public boolean init = false;
	public Map<String, ModelResourceLocation> currentmod;
	private Minecraft mc;

	private ModelHandler() {
		cache = new HashMap<String, DynamicGunModel>();
		modelres = new HashMap<String, ModelResourceLocation>();
		gunmodels = new HashMap<Item, GunModel>();
		modsmodels = new HashMap<String, Map<String, ModelResourceLocation>>();
		mc = Minecraft.getInstance();
	}

	public void pushMod(String key, Map<String, ModelResourceLocation> map) {
		modsmodels.putIfAbsent(key, map);
		currentmod = map;
	}

	public void popMod() {
		currentmod = null;
	}

	public void registerSpecialModel(String name) {
		if (currentmod != null) {
			currentmod.putIfAbsent(name, new ModelResourceLocation(name, "inventory"));
			modelres.putIfAbsent(name, new ModelResourceLocation(name, "inventory"));
		} else {
			OldGuns.LOGGER.error("Error not current map found");
		}
	}

	public void registerGunModel(Item item, GunModel model) {
		System.out.println("Item path: " + item.getRegistryName().toString());
		gunmodels.putIfAbsent(item, model);
	}

	public IBakedModel getModel(String path) {
		return mc.getModelManager().getModel(new ModelResourceLocation(path, "inventory"));
	}

	public IBakedModel getModel(ResourceLocation path) {
		return mc.getModelManager().getModel(new ModelResourceLocation(path, "inventory"));
	}

	public GunModel getGunModel(Item item) {
		return gunmodels.get(item);
	}

	public void initSpecialModels(Map<String, ModelResourceLocation> map) {
		for (String n : map.keySet()) {
			ModelLoader.addSpecialModel(map.get(n));
		}
	}

	public void initModels() {
		/*
		 * for(String n : INSTANCE.modelres.keySet()) { IBakedModel model =
		 * Minecraft.getInstance().getModelManager().getModel(INSTANCE.modelres.get(n));
		 * if(model == null) { System.out.println("model with name: " + n + " is null");
		 * } //specmodels.putIfAbsent(n, model); }
		 */
		INSTANCE.modelres.clear();
		INSTANCE.modsmodels.clear();
		/*
		 * for(Supplier<? extends Item> i : ItemsReg.INSTANCE.getGuns()) {
		 * registerNonSpecialModel(i.get()); String id =
		 * ((ItemGun)(i.get())).getGunId();
		 * if(ItemsReg.INSTANCE.getGunParts().containsKey(id)) { for(Supplier<? extends
		 * Item> gp : ItemsReg.INSTANCE.getGunParts().get(id)) {
		 * registerNonSpecialModel(gp.get()); } } } for(Map.Entry<String,
		 * ArrayList<Supplier<? extends Item>>> entry :
		 * ItemsReg.INSTANCE.getGunParts().entrySet()) {
		 * System.out.println(entry.getKey()); }
		 * 
		 * for(Supplier<? extends Item> i : ItemsReg.INSTANCE.getExtraItems()) {
		 * registerNonSpecialModel(i.get()); }
		 */
	}

	public void setInit() {
		System.out.println("Initializing models");
		init = true;
	}

	public Map<String, ModelResourceLocation> getModelResMap() {
		return modelres;
	}

	/*
	 * public Map<String, IBakedModel> getSpecialModelMap(){ return specmodels; }
	 * 
	 * public Map<String, IBakedModel> getNonSpecialModelMap(){ return
	 * nonspecmodels; }
	 */

	public Map<Item, GunModel> getGunModelMap() {
		return gunmodels;
	}
}
