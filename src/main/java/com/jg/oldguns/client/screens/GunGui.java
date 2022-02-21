package com.jg.oldguns.client.screens;

import com.jg.oldguns.containers.GunContainer;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GunGui extends ContainerScreen<GunContainer> {

	public static ResourceLocation GUN_GUI = new ResourceLocation(Util.loc("textures/gui/container/gun_gui.png"));

	public GunGui(GunContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
		super(p_i51105_1_, p_i51105_2_, p_i51105_3_);

	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float p_230450_2_, int x, int y) {
		Minecraft.getInstance().textureManager.bind(GUN_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		this.font.draw(matrixStack, this.title, (float) this.titleLabelX + 56, (float) this.titleLabelY + 33, 4210752);
		this.font.draw(matrixStack, this.inventory.getDisplayName(), (float) this.inventoryLabelX,
				(float) this.inventoryLabelY, 4210752);
		this.renderTooltip(matrixStack, x - leftPos, y - topPos);
	}

}
