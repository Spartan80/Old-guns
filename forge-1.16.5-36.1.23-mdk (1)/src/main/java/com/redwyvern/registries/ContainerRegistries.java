package com.redwyvern.registries;

import com.redwyvern.container.GunCraftingTableContainer;
import com.redwyvern.container.MoldingPartsContainer;
import com.redwyvern.container.SmeltingPartsContainer;
import com.redwyvern.mod.OldGuns;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerRegistries {
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, OldGuns.MODID);

	public static final RegistryObject<ContainerType<GunCraftingTableContainer>> gun_crafting_container = CONTAINERS
			.register("gun_crafting_table_container", () -> new ContainerType<>(GunCraftingTableContainer::new));

	public static final RegistryObject<ContainerType<SmeltingPartsContainer>> smelting_container = CONTAINERS
			.register("smelting_parts_container", () -> new ContainerType<>(SmeltingPartsContainer::new));

	public static final RegistryObject<ContainerType<MoldingPartsContainer>> molding_container = CONTAINERS
			.register("molding_parts_container", () -> new ContainerType<>(MoldingPartsContainer::new));

}
