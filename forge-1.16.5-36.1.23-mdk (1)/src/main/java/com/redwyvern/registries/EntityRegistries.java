package com.redwyvern.registries;

import com.redwyvern.entities.projectiles.Bullet;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.tileentities.GunCraftingTableTE;
import com.redwyvern.tileentities.MoldingPartsTE;
import com.redwyvern.tileentities.SmeltingPartsTE;
import com.redwyvern.util.Util;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistries {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			OldGuns.MODID);

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, OldGuns.MODID);

	public static final RegistryObject<EntityType<Bullet>> BULLET = ENTITIES.register("bullet", () -> EntityType.Builder
			.<Bullet>of(Bullet::new, EntityClassification.MISC).sized(0.9f, 0.9f).build(Util.loc("bullet").toString()));

	public static final RegistryObject<TileEntityType<GunCraftingTableTE>> GCTTE = TILE_ENTITIES.register(
			"gun_crafting_te",
			() -> TileEntityType.Builder
					.<GunCraftingTableTE>of(GunCraftingTableTE::new, BlockRegistries.gun_crafting_block.get())
					.build(null));

	public static final RegistryObject<TileEntityType<SmeltingPartsTE>> SPTE = TILE_ENTITIES
			.register("smelting_parts_te", () -> TileEntityType.Builder
					.<SmeltingPartsTE>of(SmeltingPartsTE::new, BlockRegistries.smelting_parts_block.get()).build(null));

	public static final RegistryObject<TileEntityType<MoldingPartsTE>> MPTE = TILE_ENTITIES.register("molding_parts_te",
			() -> TileEntityType.Builder
					.<MoldingPartsTE>of(MoldingPartsTE::new, BlockRegistries.molding_parts_block.get()).build(null));
}
