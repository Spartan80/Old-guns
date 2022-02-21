package com.jg.oldguns.registries;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.containers.AmmoCraftingTableContainer;
import com.jg.oldguns.containers.GunContainer;
import com.jg.oldguns.containers.GunCraftingTableContainer;
import com.jg.oldguns.containers.GunPartsContainer;
import com.jg.oldguns.containers.MagContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerRegistries {
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, OldGuns.MODID);

	public static final RegistryObject<ContainerType<GunContainer>> GUN_CONTAINER = CONTAINERS.register("gun_container",
			() -> new ContainerType<>(GunContainer::new));

	public static final RegistryObject<ContainerType<MagContainer>> MAG_CONTAINER = CONTAINERS.register("mag_container",
			() -> new ContainerType<>(MagContainer::new));

	public static final RegistryObject<ContainerType<GunCraftingTableContainer>> GUN_CRAFTING_CONTAINER = CONTAINERS
			.register("gun_crafting_container", () -> new ContainerType<>(GunCraftingTableContainer::new));

	public static final RegistryObject<ContainerType<GunPartsContainer>> GUN_PARTS_CONTAINER = CONTAINERS
			.register("gun_parts_container", () -> new ContainerType<>(GunPartsContainer::new));
	
	public static final RegistryObject<ContainerType<AmmoCraftingTableContainer>> GUN_AMMO_CONTAINER = CONTAINERS
			.register("gun_ammo_container", () -> new ContainerType<>(AmmoCraftingTableContainer::new));
}
