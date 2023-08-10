package com.jg.oldguns.client.screens.widgets;

import com.jg.oldguns.client.screens.GunCraftingGui2;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GunPartCheckSlot extends AbstractSlot {

	private ItemStack gunPart;
	private boolean hasPart;
	private int ox, oy, slot;

	public GunPartCheckSlot(Screen s, int x, int y, int ox, int oy, int w, int h) {
		super(s, x, y, w, h);
		this.ox = ox;
		this.oy = oy;
	}

	@Override
	public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		if (this.visible) {
			this.isHovered = p_230430_2_ >= this.x && p_230430_3_ >= this.y && p_230430_2_ < this.x + this.width
					&& p_230430_3_ < this.y + this.height;

			if (this.visible) {
				this.renderButton(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);

			}

		}
	}

	@Override
	public void renderButton(PoseStack matrix, int x, int y, float p_230431_4_) {
		if (gunPart != null) {
			Minecraft.getInstance().getItemRenderer().renderGuiItem(gunPart, this.x + 4, this.y - 1);
			if (isHovered) {
				matrix.pushPose();
				renderToolTip(matrix, gunPart, x, y, this.x + this.x, this.y + 200);
				matrix.popPose();
			}
		}
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GunCraftingGui2.GUN_GUI);
		if (hasPart) {
			this.blit(matrix, this.x, this.y, this.ox, this.oy, this.width, this.height);
		} else {
			this.blit(matrix, this.x, this.y, this.ox + 26, this.oy, this.width, this.height);
		}
	}

	public void check(Inventory pi) {
		hasPart = false;
		for (int i = 0; i < pi.items.size(); i++) {
			if (pi.getItem(i).getItem() == gunPart.getItem()) {
				this.slot = i;
				hasPart = true;
			}
		}
		if (!hasPart) {
			this.slot = -1;
		}
	}

	public void setPart(Item item, Inventory pi) {
		this.gunPart = new ItemStack(item);
		check(pi);
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

	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {
		// TODO Auto-generated method stub

	}

}
