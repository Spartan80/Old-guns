package com.redwyvern.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redwyvern.gun.ItemGun;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.registries.ItemRegistries;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

public class Util {
	public static final List<BakedQuad> emptyList = new ArrayList<>();

	public static final ItemStack SNOWBALL = new ItemStack(Items.SNOWBALL);

	public static Map<String, Integer> ingots = new HashMap<String, Integer>();

	public static Map<String, Integer> woods = new HashMap<String, Integer>();

	public static final ArrayList<Item> guns = new ArrayList<Item>();

	public static final ArrayList<Item> gunmetalparts = new ArrayList<Item>();

	public static final ArrayList<Item> gunwoodparts = new ArrayList<Item>();

	public static Map<String, Boolean> isIron = new HashMap<String, Boolean>();

	public static boolean isAble(ItemStack stack) {
		return (stack.getOrCreateTag().getInt("bullets") > 0
				&& stack.getOrCreateTag().getInt("bullets") < ((ItemGun) stack.getItem()).getMaxAmmo());
	}

	public static void init() {
		ingots.put(ItemRegistries.ppb.get().getRegistryName().toString(), 4);
		ingots.put(ItemRegistries.ppbm.get().getRegistryName().toString(), 2);
		ingots.put(ItemRegistries.prb.get().getRegistryName().toString(), 10);
		ingots.put(ItemRegistries.prbm.get().getRegistryName().toString(), 4);
		ingots.put(ItemRegistries.wb.get().getRegistryName().toString(), 12);
		ingots.put(ItemRegistries.wbm.get().getRegistryName().toString(), 14);
		ingots.put(ItemRegistries.tb.get().getRegistryName().toString(), 8);
		ingots.put(ItemRegistries.tbm.get().getRegistryName().toString(), 4);
		ingots.put(ItemRegistries.rb.get().getRegistryName().toString(), 5);
		ingots.put(ItemRegistries.rbm.get().getRegistryName().toString(), 8);
		ingots.put(ItemRegistries.kb.get().getRegistryName().toString(), 14);
		ingots.put(ItemRegistries.kbm.get().getRegistryName().toString(), 14);

		isIron.put(ItemRegistries.ppb.get().getRegistryName().toString(), true);
		isIron.put(ItemRegistries.ppbm.get().getRegistryName().toString(), true);
		isIron.put(ItemRegistries.prb.get().getRegistryName().toString(), true);
		isIron.put(ItemRegistries.prbm.get().getRegistryName().toString(), true);
		isIron.put(ItemRegistries.wb.get().getRegistryName().toString(), false);
		isIron.put(ItemRegistries.wbm.get().getRegistryName().toString(), false);
		isIron.put(ItemRegistries.tb.get().getRegistryName().toString(), true);
		isIron.put(ItemRegistries.tbm.get().getRegistryName().toString(), true);
		isIron.put(ItemRegistries.rb.get().getRegistryName().toString(), false);
		isIron.put(ItemRegistries.rbm.get().getRegistryName().toString(), false);
		isIron.put(ItemRegistries.kb.get().getRegistryName().toString(), false);
		isIron.put(ItemRegistries.kbm.get().getRegistryName().toString(), false);

		woods.put(ItemRegistries.ppby.get().getRegistryName().toString(), 6);
		woods.put(ItemRegistries.prby.get().getRegistryName().toString(), 12);
		woods.put(ItemRegistries.wbw.get().getRegistryName().toString(), 16);
		woods.put(ItemRegistries.rbw.get().getRegistryName().toString(), 6);
		woods.put(ItemRegistries.kbw.get().getRegistryName().toString(), 18);
		woods.put(ItemRegistries.tbw.get().getRegistryName().toString(), 12);

		guns.add(ItemRegistries.pirate_pistol.get());
		guns.add(ItemRegistries.pirate_rifle.get());
		guns.add(ItemRegistries.winchester.get());
		guns.add(ItemRegistries.trabuco.get());
		guns.add(ItemRegistries.revolver.get());
		guns.add(ItemRegistries.kar98k.get());

		gunmetalparts.add(ItemRegistries.ppb.get());
		gunmetalparts.add(ItemRegistries.ppbm.get());
		gunmetalparts.add(ItemRegistries.prb.get());
		gunmetalparts.add(ItemRegistries.prbm.get());
		gunmetalparts.add(ItemRegistries.wb.get());
		gunmetalparts.add(ItemRegistries.wbm.get());
		gunmetalparts.add(ItemRegistries.rb.get());
		gunmetalparts.add(ItemRegistries.rbm.get());
		gunmetalparts.add(ItemRegistries.kb.get());
		gunmetalparts.add(ItemRegistries.kbm.get());
		gunmetalparts.add(ItemRegistries.tb.get());
		gunmetalparts.add(ItemRegistries.tbm.get());

		gunwoodparts.add(ItemRegistries.ppby.get());
		gunwoodparts.add(ItemRegistries.prby.get());
		gunwoodparts.add(ItemRegistries.wbw.get());
		gunwoodparts.add(ItemRegistries.rbw.get());
		gunwoodparts.add(ItemRegistries.kbw.get());
		gunwoodparts.add(ItemRegistries.tbw.get());
	}

	public static boolean isMoldValid(ItemStack stackp) {
		ItemStack stack = stackp;
		if (!stack.isEmpty()) {
			if (stack.getOrCreateTag().getString("res_loc") != null
					&& stack.getOrCreateTag().getString("res_loc") != "") {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidWood(Item item) {
		if (item == Items.ACACIA_PLANKS || item == Items.BIRCH_PLANKS || item == Items.CRIMSON_PLANKS
				|| item == Items.DARK_OAK_PLANKS || item == Items.JUNGLE_PLANKS || item == Items.OAK_PLANKS
				|| item == Items.SPRUCE_PLANKS || item == Items.WARPED_PLANKS) {
			return true;
		}
		return false;
	}

	public static boolean isIron(String item) {
		if (!isIron.isEmpty()) {
			return isIron.get(item);
		}
		return false;
	}

	public static int getIngotsFor(String item) {
		if (!ingots.isEmpty()) {
			return ingots.get(item);
		}
		return 0;
	}

	public static int getWoodsFor(String item) {
		if (!woods.isEmpty()) {
			return woods.get(item);
		}
		return 0;
	}

	public static ResourceLocation loc(String name) {
		return new ResourceLocation(OldGuns.MODID, name);
	}

	public static float numInRange(float min, float max) {
		return (float) (Math.random() * (max + 1 - min)) + min;
	}

	public static void translateQuad(BakedQuad quad, Direction direction, float amount) {
		int index;
		int[] vertex = quad.getVertices();
		amount /= 16.0F;
		switch (direction) {
		case NORTH: // arriba
			index = 2;
			break;
		case SOUTH:
		default:
			index = 2;
			if (direction == Direction.SOUTH)
				amount = -amount;
			break;
		case UP:
			index = 1;
			break;
		case DOWN: // back
			index = 1;
			if (direction == Direction.DOWN)
				amount = -amount;
			break;
		case EAST:
			index = 0;
			break;
		case WEST: // izq
			index = 0;
			if (direction == Direction.WEST)
				amount = -amount;
			break;
		}
		for (; index < vertex.length; index += 8)
			vertex[index] = Float.floatToRawIntBits(Float.intBitsToFloat(vertex[index]) + amount);
	}

}
