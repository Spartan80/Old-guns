package com.jg.oldguns.client.screens.widgets;

import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.utils.RenderUtil;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GunSlot extends Button {

	public ItemStack gun;
	protected IBakedModel model;
	final int tab;
	int currenttab;

	public GunSlot(int tab, int x, int y, int ox, int oy, int w, int h, int step, ResourceLocation image, Item item) {
		super(x, y, ox, oy, w, h, step, image);
		this.gun = new ItemStack(item);
		model = ModelHandler.INSTANCE.getModel(item.getRegistryName());
		this.tab = tab;
		this.currenttab = 0;
	}

	@Override
	public void render(MatrixStack mat, int x, int y, float p_230430_4_) {
		if (this.visible && this.currenttab == tab) {
			this.isHovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;

			if (this.visible) {
				this.renderButton(mat, x, y, p_230430_4_);
				this.renderItem(mat);
				if (gun != null) {
					if (isHovered) {
						renderToolTip(mat, gun, x, y, this.x + this.x, this.y + 200);
					}
				}
			}
		}
	}

	public void renderItem(MatrixStack stack) {
		RenderUtil.renderGuiItem(gun, this.x, this.y, model);
	}

	public void updateTab(int tab) {
		this.currenttab = tab;
	}
}
