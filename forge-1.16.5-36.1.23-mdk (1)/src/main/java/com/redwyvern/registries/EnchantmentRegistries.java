package com.redwyvern.registries;

import com.redwyvern.enchantments.FastReloadEnchantment;
import com.redwyvern.enchantments.MoreShotsEnchantment;
import com.redwyvern.mod.OldGuns;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentRegistries {
	public static final DeferredRegister<Enchantment> REGISTER = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS,
			OldGuns.MODID);

	public static final RegistryObject<Enchantment> QUICK_HANDS = REGISTER.register("fast_reload",
			FastReloadEnchantment::new);

	public static final RegistryObject<Enchantment> MORE_SHOTS = REGISTER.register("more_shots",
			MoreShotsEnchantment::new);
}
