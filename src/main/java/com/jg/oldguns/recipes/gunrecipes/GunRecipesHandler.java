package com.jg.oldguns.recipes.gunrecipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GunRecipesHandler {
	INSTANCE;

	Map<String, List<GunRecipe>> recipes;

	private GunRecipesHandler() {
		recipes = new HashMap<String, List<GunRecipe>>();
	}

	public void addRecipe(String id, GunRecipe recipe) {
		if (!recipes.containsKey(id)) {
			System.out.println("Putted new list");

			recipes.put(id, new ArrayList<GunRecipe>());
		}
		System.out.println("Recipe added");
		recipes.get(id).add(recipe);
	}

	public Map<String, List<GunRecipe>> getRecipes() {
		return recipes;
	}

	public List<GunRecipe> getRecipesFor(String id) {
		return recipes.getOrDefault(id, null);
	}

}
