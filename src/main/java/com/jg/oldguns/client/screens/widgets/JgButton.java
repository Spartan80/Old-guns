package com.jg.oldguns.client.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class JgButton extends AbstractSlot {

	private ResourceLocation img;
	private final int ox, oy, stepX, stepY;
	private boolean clicked;
	private boolean simple;
	private Runnable toRun;
	
	public JgButton(Screen s, int x, int y, int ox, int oy, int w, int h, 
			ResourceLocation image, Runnable toRun) {
		this(s, x, y, ox, oy, w, h, 0, 0, image, true, toRun);
	}
	
	public JgButton(Screen s, int x, int y, int ox, int oy, int w, int h, int stepX, 
			int stepY, ResourceLocation image, boolean simple, Runnable toRun) {
		super(s, x, y, w, h);
		this.img = image;
		this.ox = ox;
		this.oy = oy;
		this.stepX = stepX;
		this.stepY = stepY;
		this.clicked = false;
		this.simple = simple;
		this.toRun = toRun;
	}

	@Override
	public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		if (this.visible) {
			this.isHovered = p_230430_2_ >= this.x && p_230430_3_ >= this.y && p_230430_2_ < this.x + this.width
					&& p_230430_3_ < this.y + this.height;
			this.renderButton(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
		}
	}

	@Override
	public void renderButton(PoseStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
		if(!simple) {
			RenderSystem.enableBlend();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, img);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
			int oxt = 0;
			int oyt = 0;
			if (isHovered) {
				oxt += stepX;
				oyt += stepY;
				if (clicked) {
					oxt += stepX;
					oyt += stepY;
				}
			}
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			this.blit(p_230431_1_, this.x, this.y, ox + oxt, oy + oyt, this.width, this.height);
			//LogUtils.getLogger().info("oy: " + (oy + oyt));
		} else {
			RenderSystem.enableBlend();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, img);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
			this.blit(p_230431_1_, this.x, this.y, ox, oy, this.width, this.height);
			if(clicked) {
				shadow(p_230431_1_, this.x, this.y, this.width, this.height);
			} else if(isHovered) {
				highlight(p_230431_1_, this.x, this.y, this.width, this.height);
			}
		}
	}

	@Override
	public void onPress() {
		clicked = true;
		toRun.run();
	}

	@Override
	public void onRelease(double p_231000_1_, double p_231000_3_) {
		clicked = false;
	}
	
	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {
		
	}
	
	public void shadow(PoseStack matrix, int x, int y, int w, int h) {
		RenderSystem.disableDepthTest();
	    RenderSystem.colorMask(true, true, true, false);
	    // Llamada a fillGradient con los colores personalizados
	    fillGradient(matrix, x, y, x + w, y + h, 0x90000000, 0x90000000);
	    RenderSystem.colorMask(true, true, true, true);
	    RenderSystem.enableDepthTest();
	}
	
	public void highlight(PoseStack matrix, int x, int y, int w, int h) {
		RenderSystem.disableDepthTest();
	    RenderSystem.colorMask(true, true, true, false);
	    fillGradient(matrix, x, y, x + w, y + h, 
	    		-2130706433, -2130706433, getBlitOffset());
	    RenderSystem.colorMask(true, true, true, true);
	    RenderSystem.enableDepthTest();
	}

}
