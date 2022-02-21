package com.jg.oldguns.client.screens.widgets;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class AbstractSlot extends AbstractButton {

	public AbstractSlot(int x, int y, int w, int h) {
		super(x, y, w, h, new TranslationTextComponent(""));
		// TODO Auto-generated constructor stub
	}

	public void renderToolTip(MatrixStack mat, ItemStack stack, int mx, int my, int width, int height) {
		FontRenderer font = stack.getItem().getFontRenderer(stack);
		net.minecraftforge.fml.client.gui.GuiUtils.preItemToolTip(stack);
		this.renderWrappedToolTip(mat, this.getTooltipFromItem(stack), mx, my, width, height,
				(font == null ? Minecraft.getInstance().font : font));
		net.minecraftforge.fml.client.gui.GuiUtils.postItemToolTip();
	}

	public void renderWrappedToolTip(MatrixStack matrixStack,
			List<? extends net.minecraft.util.text.ITextProperties> tooltips, int mouseX, int mouseY, int width,
			int height, FontRenderer font) {
		net.minecraftforge.fml.client.gui.GuiUtils.drawHoveringText(matrixStack, tooltips, mouseX, mouseY, width,
				height, -1, font);
	}

	public List<ITextComponent> getTooltipFromItem(ItemStack p_231151_1_) {
		return p_231151_1_.getTooltipLines(Minecraft.getInstance().player,
				Minecraft.getInstance().options.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED
						: ITooltipFlag.TooltipFlags.NORMAL);
	}
}
