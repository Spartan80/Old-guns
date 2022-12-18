package com.jg.oldguns.client.handlers;

import java.util.HashMap;
import java.util.Map;

import com.jg.oldguns.client.models.wrapper.DynamicGunModel;

public enum ModelHandler {
	INSTANCE;
	
	protected Map<String, DynamicGunModel> models;
	
	ModelHandler() {
		models = new HashMap<>();
	}
	
	public void put(String id, DynamicGunModel model) {
		models.put(id, model);
	}
	
	public DynamicGunModel getModel(String id) {
		return models.get(id);
	}
	
	public Map<String, DynamicGunModel> getModels(){
		return models;
	}
	
}
