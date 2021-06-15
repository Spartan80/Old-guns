package com.redwyvern.registries;

import com.redwyvern.blocks.GunCraftingTableBlock;
import com.redwyvern.blocks.MoldingPartsTableBlock;
import com.redwyvern.blocks.SmeltingPartsTableBlock;
import com.redwyvern.mod.OldGuns;

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

	public static RegistryObject<Block> gun_crafting_block = BLOCKS.register("gun_crafting_block",
			() -> new GunCraftingTableBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F)
					.harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));

	public static RegistryObject<Block> smelting_parts_block = BLOCKS.register("smelting_parts_block",
			() -> new SmeltingPartsTableBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F)
					.harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)));

	public static RegistryObject<Block> molding_parts_block = BLOCKS.register("molding_parts_block",
			() -> new MoldingPartsTableBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F)
					.harvestLevel(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));

	public static RegistryObject<Block> steel_block = BLOCKS.register("steel_block",
			() -> new Block(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(2.0F, 3.0F).harvestLevel(2)
					.harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));
}
