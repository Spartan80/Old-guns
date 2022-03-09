package com.jg.oldguns.registries;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.blocks.GunAmmoBlock;
import com.jg.oldguns.blocks.GunCraftingTableBlock;
import com.jg.oldguns.blocks.GunPartsBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistries {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OldGuns.MODID);

	public static RegistryObject<Block> Gun_crafting_block = BLOCKS.register("gun_crafting_block",
			() -> new GunCraftingTableBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F)
					.strength(2).sound(SoundType.METAL)));

	public static RegistryObject<Block> Gun_parts_block = BLOCKS.register("gun_parts_block",
			() -> new GunPartsBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F)
					.strength(2).sound(SoundType.METAL)));
	
	public static RegistryObject<Block> Gun_ammo_block = BLOCKS.register("gun_ammo_block",
			() -> new GunAmmoBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F)
					.sound(SoundType.METAL)));

	public static RegistryObject<Block> Steel_block = BLOCKS.register("steel_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F).strength(2)
					.sound(SoundType.METAL)));
}
