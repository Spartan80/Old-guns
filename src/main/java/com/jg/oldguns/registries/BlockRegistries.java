package com.jg.oldguns.registries;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.blocks.GunAmmoBlock;
import com.jg.oldguns.blocks.GunCraftingTableBlock;
import com.jg.oldguns.blocks.GunPartsBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistries {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OldGuns.MODID);

	public static RegistryObject<Block> Gun_crafting_block = BLOCKS.register("gun_crafting_block",
			() -> new GunCraftingTableBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F)
					.harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));

	public static RegistryObject<Block> Gun_parts_block = BLOCKS.register("gun_parts_block",
			() -> new GunPartsBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F)
					.harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));
	
	public static RegistryObject<Block> Gun_ammo_block = BLOCKS.register("gun_ammo_block",
			() -> new GunAmmoBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F)
					.harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));

	public static RegistryObject<Block> Steel_block = BLOCKS.register("steel_block",
			() -> new Block(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F).harvestLevel(2)
					.harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));
}
