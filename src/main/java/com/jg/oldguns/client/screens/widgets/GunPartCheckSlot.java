package com.jg.oldguns.client.screens.widgets;

import com.jg.oldguns.client.screens.GunCraftingGui;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GunPartCheckSlot extends AbstractSlot {

	private ItemStack gunPart;
	private boolean hasPart;
	private int ox, oy, slot;

	public GunPartCheckSlot(int x, int y, int ox, int oy, int w, int h) {
		super(x, y, w, h);
		this.ox = ox;
		this.oy = oy;
	}

	@Override
	public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		if (this.visible) {
			this.isHovered = p_230430_2_ >= this.x && p_230430_3_ >= this.y && p_230430_2_ < this.x + this.width
					&& p_230430_3_ < this.y + this.height;

			if (this.visible) {
				this.renderButton(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);

			}

		}
	}

	@Override
	public void renderButton(MatrixStack matrix, int x, int y, float p_230431_4_) {
		if (gunPart != null) {
			Minecraft.getInstance().getItemRenderer().renderGuiItem(gunPart, this.x + 4, this.y - 1);
			if (isHovered) {
				matrix.pushPose();
				renderToolTip(matrix, gunPart, x, y, this.x + this.x, this.y + 200);
				matrix.popPose();
			}
		}
		Minecraft.getInstance().getTextureManager().bind(GunCraftingGui.GUN_GUI);
		if (hasPart) {
			this.blit(matrix, this.x, this.y, this.ox, this.oy, this.width, this.height);
		} else {
			this.blit(matrix, this.x, this.y, this.ox + 26, this.oy, this.width, this.height);
		}
	}

	public void update(PlayerInventory pi) {
		if (gunPart != null) {
			this.slot = pi.findSlotMatchingItem(gunPart);

			if (this.slot != -1) {
				hasPart = true;
			} else {
				hasPart = false;
			}
		}
	}

	public void setPart(Item item) {
		this.gunPart = new ItemStack(item);
	}

	public boolean hasPart() {
		return hasPart;
	}

	public int getSlot() {
		return slot;
	}

	@Override
	public void onPress() {

	}

}
