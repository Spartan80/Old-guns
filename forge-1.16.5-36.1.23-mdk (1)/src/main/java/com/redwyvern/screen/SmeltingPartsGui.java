package com.redwyvern.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.redwyvern.container.SmeltingPartsContainer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SmeltingPartsGui extends ContainerScreen<SmeltingPartsContainer> {

	public SmeltingPartsContainer container;
	public static final ResourceLocation SMELTING_PARTS_GUI = new ResourceLocation(
			"oldguns:textures/gui/container/smelting_parts_gui.png");

	public SmeltingPartsGui(SmeltingPartsContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		container = screenContainer;
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().textureManager.bind(SMELTING_PARTS_GUI);
		this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.blit(matrixStack, this.leftPos + 61, this.topPos + 41, 177, 41, container.data.get(1), 5);
		this.blit(matrixStack, this.leftPos + 43, this.topPos + 45 - this.menu.data.get(4), 202,
				45 - this.menu.data.get(4), 5, 23);
		RenderSystem.pushMatrix();
		RenderSystem.translatef(0, 0, 1000f);

		this.blit(matrixStack, this.leftPos + 12, this.topPos + 18, 177, 47, 20, 20);
		RenderSystem.popMatrix();

	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		super.renderLabels(matrixStack, x, y);
		RenderSystem.pushMatrix();
		RenderSystem.translatef(0, 0, 307f);
		this.renderTooltip(matrixStack, x - leftPos, y - topPos);
		RenderSystem.popMatrix();
	}

}
