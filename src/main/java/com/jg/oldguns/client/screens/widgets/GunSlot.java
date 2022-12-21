package com.jg.oldguns.client.screens.widgets;

import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.render.RenderHelper;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GunSlot extends Button {

	public ItemStack gun;
	protected BakedModel model;
	final int tab;
	int currenttab;

	public GunSlot(Screen s, int tab, int x, int y, int ox, int oy, int w, int h, int step, ResourceLocation image,
			Item item) {
		super(s, x, y, ox, oy, w, h, step, image);
		this.gun = new ItemStack(item);
		this.model = Utils.getModel(item);
		this.tab = tab;
		this.currenttab = 0;
	}

	@Override
	public void render(PoseStack mat, int x, int y, float p_230430_4_) {
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

	public void renderItem(PoseStack stack) {
		RenderHelper.renderGuiItem(gun, this.x, this.y, model);
	}

	public void updateTab(int tab) {
		this.currenttab = tab;
	}
}
