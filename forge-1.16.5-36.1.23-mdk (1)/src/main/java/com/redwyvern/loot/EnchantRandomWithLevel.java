package com.redwyvern.loot;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.registries.EnchantmentRegistries;
import com.redwyvern.registries.LootFunctionRegistries;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;

public class EnchantRandomWithLevel extends LootFunction {

	private List<Enchantment> enc;

	protected EnchantRandomWithLevel(ILootCondition[] p_i51231_1_) {
		super(p_i51231_1_);
		enc = new ArrayList<Enchantment>();
		enc.add(EnchantmentRegistries.MORE_SHOTS.get());
		enc.add(EnchantmentRegistries.QUICK_HANDS.get());

	}

	@Override
	public LootFunctionType getType() {
		return LootFunctionRegistries.ENCHANT;
	}

	@Override
	protected ItemStack run(ItemStack stack, LootContext p_215859_2_) {
		int re = (int) Math.floor(Math.random() * 2);
		ItemStack s = stack;
		int l = getLevelFor(enc.get(re));
		if (stack.getItem() != Items.BOOK) {
			s.enchant(enc.get(re), l);
			System.out.println("enchating item");
			return s;
		} else {
			if (enc.get(re) == null) {
				System.out.println("null enchantment");
			}
			EnchantedBookItem.addEnchantment(s, new EnchantmentData(enc.get(re), l));
			System.out.println("enchating book with: " + enc.get(re).getDescriptionId() + " with level: " + l);
			return s;
		}
	}

	public int getLevelFor(Enchantment e) {
		for (int l = e.getMaxLevel(); l > 0; l--) {
			boolean p = ItemGun.prob(((e.getMaxLevel() + 1) - l) * 20);
			if (p) {
				return l;
			}
		}
		return 1;
	}

	public static EnchantRandomWithLevel.Builder enchantRandomWithLevels() {
		return new EnchantRandomWithLevel.Builder();
	}

	public static class Builder extends LootFunction.Builder<EnchantRandomWithLevel.Builder> {

		public Builder() {

		}

		protected EnchantRandomWithLevel.Builder getThis() {
			return this;
		}

		public ILootFunction build() {
			return new EnchantRandomWithLevel(this.getConditions());
		}
	}

	public static class Serializer extends LootFunction.Serializer<EnchantRandomWithLevel> {
		public void serialize(JsonObject p_230424_1_, EnchantRandomWithLevel p_230424_2_,
				JsonSerializationContext p_230424_3_) {
			super.serialize(p_230424_1_, p_230424_2_, p_230424_3_);
		}

		public EnchantRandomWithLevel deserialize(JsonObject p_186530_1_, JsonDeserializationContext p_186530_2_,
				ILootCondition[] p_186530_3_) {
			return new EnchantRandomWithLevel(p_186530_3_);
		}
	}

}
