package com.redwyvern.tileentities;

import com.redwyvern.container.MoldingPartsContainer;
import com.redwyvern.registries.EntityRegistries;
import com.redwyvern.tileentities.data.MoldingPartsData;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MoldingPartsTE extends TileEntity implements INamedContainerProvider {

	private MoldingPartsData datam;

	public MoldingPartsTE() {
		super(EntityRegistries.MPTE.get());
		datam = new MoldingPartsData();
	}

	public MoldingPartsTE(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		datam = new MoldingPartsData();
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		// TODO Auto-generated method stub
		return this.level == null ? null
				: new MoldingPartsContainer(p_createMenu_1_, p_createMenu_2_,
						IWorldPosCallable.create(this.level, this.worldPosition), datam);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TranslationTextComponent("oldguns.container.molding_parts_table");
	}

}
