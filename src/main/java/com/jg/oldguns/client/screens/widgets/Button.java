package com.jg.oldguns.client.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class Button extends AbstractSlot {

	ResourceLocation img;
	final int ox, oy, step;
	boolean clicked;

	public Button(Screen s, int x, int y, int ox, int oy, int w, int h, int step, ResourceLocation image) {
		super(s, x, y, w, h);
		this.img = image;
		this.ox = ox;
		this.oy = oy;
		this.step = step;
		this.clicked = false;
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
	public void renderButton(PoseStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, img);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		int oxt = 0;
		if (isHovered) {
			oxt += step;
			if (clicked) {
				oxt += step;
			}
		}
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		this.blit(p_230431_1_, this.x, this.y, ox + oxt, oy, this.width, this.height);
	}

	@Override
	public void onPress() {
		clicked = true;

	}

	@Override
	public void onRelease(double p_231000_1_, double p_231000_3_) {
		clicked = false;
	}

	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {

	}

}
