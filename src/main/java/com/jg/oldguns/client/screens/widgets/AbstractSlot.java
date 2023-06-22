package com.jg.oldguns.client.screens.widgets;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.client.RenderProperties;

public abstract class AbstractSlot extends AbstractButton {

	Screen s;

	public AbstractSlot(Screen s, int x, int y, int w, int h) {
		super(x, y, w, h, new TranslatableComponent(""));
		this.s = s;
	}

	public void renderToolTip(PoseStack mat, ItemStack stack, int mx, int my, int width, int height) {
		Font font = RenderProperties.get(stack.getItem()).getFont(stack);
		this.renderWrappedToolTip(mat, this.getTooltipFromItem(stack), mx, my, width, height,
				(font == null ? Minecraft.getInstance().font : font));
	}

	public void renderWrappedToolTip(PoseStack matrixStack, List<Component> tooltips, int mouseX, int mouseY, int width,
			int height, Font font) {
		this.s.renderComponentTooltip(matrixStack, tooltips, width, height, font);
	}

	public List<Component> getTooltipFromItem(ItemStack p_231151_1_) {
		return p_231151_1_.getTooltipLines(Minecraft.getInstance().player,
				Minecraft.getInstance().options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED
						: TooltipFlag.Default.NORMAL);
	}
}
