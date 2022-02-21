package com.jg.oldguns.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.client.handlers.RecoilHandler;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.registries.ItemRegistries;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class Util {

	public static final List<BakedQuad> empty = new ArrayList<BakedQuad>();

	public static final ItemStack WOOD = new ItemStack(Items.OAK_PLANKS);

	public static final ItemStack IRON_INGOT = new ItemStack(Items.IRON_INGOT);

	public static final ItemStack STEEL_INGOT = new ItemStack(ItemRegistries.SteelIngot.get());

	// JSON Test

	public static JsonElement load(ResourceLocation jsonLocation) {
		String filename = "assets/" + jsonLocation.getNamespace() + "/" + jsonLocation.getPath();
		InputStream in = new RecoilHandler().getClass().getClassLoader().getResourceAsStream(filename);
		if (in == null) {
			Logger.getLogger(OldGuns.MODID).log(Level.SEVERE, "Could not open resource " + filename);
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return new Gson().fromJson(reader, JsonElement.class);
	}

	// Utils for locating

	public static String loc(String loc) {
		return "oldguns:" + loc;
	}

	public static String locS(String loc) {
		return "oldguns:special/" + loc;
	}

	public static ResourceLocation locR(String loc) {
		return new ResourceLocation(OldGuns.MODID, loc);
	}

	public static ModelResourceLocation locG(String loc) {
		return new ModelResourceLocation("oldguns:" + "guns/" + loc + "/" + loc, "inventory");
	}

	// Utils for locating

	// Misc

	public static float getFrameTime() {
		return Minecraft.getInstance().getFrameTime();
	}

	public static double numInRange(double min, double max) {
		return Math.random() * (max - min) + min;
	}

	public static boolean isAvailable(PlayerEntity player) {
		if (player != null) {
			return !player.isSpectator() && player.isAlive();
		}
		return false;
	}

	public static boolean canWork(PlayerEntity player) {
		return player.getMainHandItem().getItem() instanceof ItemGun;
	}

	public static boolean canWork(ItemStack stack) {
		return stack.getItem() instanceof ItemGun;
	}

	public static boolean isThirdPerson() {
		return Minecraft.getInstance().options.getCameraType() == PointOfView.THIRD_PERSON_BACK
				|| Minecraft.getInstance().options.getCameraType() == PointOfView.THIRD_PERSON_FRONT;
	}

	public static boolean isFirstPerson() {
		return Minecraft.getInstance().options.getCameraType() == PointOfView.FIRST_PERSON;
	}

	public static boolean canEquip(ItemStack carried, int index) {
		boolean temp = false;
		System.out.println(carried.getItem().getRegistryName().toString());
		if (carried.getItem() instanceof GunPart) {
			System.out.println("part gun slot: " + ((GunPart) carried.getItem()).getGunSlot() + " index: " + index);
			if (((GunPart) carried.getItem()).getGunSlot() == index) {
				temp = true;
			}
		}
		return temp;
	}

	public static ClientHandler getHandler() {
		return ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
	}

	/*
	 * Returns the registry name of the item
	 */
	public String getReg(Item item) {
		return item.getRegistryName().toString();
	}

	// Misc

	// Utils for itemstack

	public static ItemStack getStack() {
		return ClientEventHandler.getPlayer().getMainHandItem();
	}

	// Utils for itemstack

}
