package com.jg.oldguns.client.screens;

import com.jg.oldguns.containers.MagContainer;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MagGui extends AbstractContainerScreen<MagContainer> {

	private static final ResourceLocation MAG_GUI = Utils.loc("textures/gui/container/mag_gui.png");

	public MagGui(MagContainer p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
		super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
	}

	@Override
	protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, MAG_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(p_230450_1_, i, j, 0, 0, this.imageWidth, this.imageHeight);

	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int x, int y) {
		this.font.draw(matrixStack, this.title, (float) this.titleLabelX + 56, (float) this.titleLabelY + 33, 4210752);
		// this.font.draw(matrixStack, this.t, (float) this.inventoryLabelX,
		// (float) this.inventoryLabelY, 4210752);
		this.renderTooltip(matrixStack, x - leftPos, y - topPos);
	}

}
