package com.redwyvern.items;

import java.util.List;

import com.redwyvern.mod.OldGuns;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class GunPartMold extends Item {

	public GunPartMold() {
		super(new Item.Properties().tab(OldGuns.modtab).stacksTo(1));
	}

	public CompoundNBT getNBT(ItemStack stack) {
		return stack.getOrCreateTag();
	}

	public String getNBTMoldName(ItemStack stack) {
		return getNBT(stack).getString("mold_name");
	}

	public void setNBTMoldName(ItemStack stack, String moldname) {
		getNBT(stack).putString("mold_name", moldname);
	}

	public String getNBTPart(ItemStack stack) {
		return getNBT(stack).getString("res_loc");
	}

	public void setNBTPart(ItemStack stack, String res) {
		getNBT(stack).putString("res_loc", res);
	}

	@Override
	public ITextComponent getName(ItemStack stack) {
		// TODO Auto-generated method stub
		return new TranslationTextComponent(this.getNBTMoldName(stack));
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("Name: " + this.getNBTMoldName(stack)));
		tooltip.add(new TranslationTextComponent("Item: "));
	}
}
