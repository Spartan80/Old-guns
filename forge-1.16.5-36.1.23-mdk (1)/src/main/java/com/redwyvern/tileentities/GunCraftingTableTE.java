package com.redwyvern.tileentities;

import com.redwyvern.container.GunCraftingTableContainer;
import com.redwyvern.registries.EntityRegistries;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GunCraftingTableTE extends TileEntity implements INamedContainerProvider {

	public GunCraftingTableTE() {
		super(EntityRegistries.GCTTE.get());
	}

	public GunCraftingTableTE(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);

	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		// TODO Auto-generated method stub
		return this.level == null ? null
				: new GunCraftingTableContainer(p_createMenu_1_, p_createMenu_2_,
						IWorldPosCallable.create(this.level, this.worldPosition));
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TranslationTextComponent("oldguns.container.gun_crafting_table");
	}

}
