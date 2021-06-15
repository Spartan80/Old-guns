package com.redwyvern.registries;

import com.redwyvern.mod.OldGuns;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootFunctionRegistries {

	public static LootFunctionType ENCHANT = null;

	public static void init() {
		ENCHANT = register("enchant_random_with_level", new SetCount.Serializer());
	}

	private static LootFunctionType register(String p_237451_0_, ILootSerializer<? extends ILootFunction> p_237451_1_) {
		return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(OldGuns.MODID, p_237451_0_),
				new LootFunctionType(p_237451_1_));
	}
}
