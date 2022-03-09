package com.jg.oldguns.registries;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.containers.AmmoCraftingTableContainer;
import com.jg.oldguns.containers.GunContainer;
import com.jg.oldguns.containers.GunCraftingTableContainer;
import com.jg.oldguns.containers.GunPartsContainer;
import com.jg.oldguns.containers.MagContainer;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerRegistries {
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, OldGuns.MODID);

	public static final RegistryObject<MenuType<GunContainer>> GUN_CONTAINER = CONTAINERS.register("gun_container",
			() -> new MenuType<>(GunContainer::new));

	public static final RegistryObject<MenuType<MagContainer>> MAG_CONTAINER = CONTAINERS.register("mag_container",
			() -> new MenuType<>(MagContainer::new));

	public static final RegistryObject<MenuType<GunCraftingTableContainer>> GUN_CRAFTING_CONTAINER = CONTAINERS
			.register("gun_crafting_container", () -> new MenuType<>(GunCraftingTableContainer::new));

	public static final RegistryObject<MenuType<GunPartsContainer>> GUN_PARTS_CONTAINER = CONTAINERS
			.register("gun_parts_container", () -> new MenuType<>(GunPartsContainer::new));
	
	public static final RegistryObject<MenuType<AmmoCraftingTableContainer>> GUN_AMMO_CONTAINER = CONTAINERS
			.register("gun_ammo_container", () -> new MenuType<>(AmmoCraftingTableContainer::new));
}
