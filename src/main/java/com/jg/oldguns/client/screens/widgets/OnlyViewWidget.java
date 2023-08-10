package com.jg.oldguns.client.screens.widgets;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class OnlyViewWidget<T> extends AbstractWidget {

	protected T item;
	protected Screen s;
	protected List<Component> components;
	
	public OnlyViewWidget(Screen s, int x, int y, int w, int h) {
		this(s, null, x, y, w, h);
	}
	
	public OnlyViewWidget(Screen s, T item, int x, int y, int w, int h) {
		super(x, y, w, h, new TranslatableComponent(""));
		this.item = item;
		this.s = s;
	}

	@Override
	public void render(PoseStack p_93657_, int p_93658_, int p_93659_, float p_93660_) {
		if (this.visible) {
	         this.isHovered = p_93658_ >= this.x && p_93659_ >= this.y && p_93658_ < this.x + this.width && p_93659_ < this.y + this.height;
	         this.renderButton(p_93657_, p_93658_, p_93659_, p_93660_);
	         if(isHovered) {
	        	 renderHovered(p_93657_, p_93658_, p_93659_);
	         }
	    }
	}
	
	@Override
	public void renderButton(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_) {
		renderSlot(p_93676_, p_93677_, p_93678_);
	}
	
	public void shadow(PoseStack matrix, int x, int y) {
		RenderSystem.disableDepthTest();
	    RenderSystem.colorMask(true, true, true, false);
	    // Llamada a fillGradient con los colores personalizados
	    fillGradient(matrix, x, y, x + 16, y + 16, 0x90000000, 0x90000000);
	    RenderSystem.colorMask(true, true, true, true);
	    RenderSystem.enableDepthTest();
	}
	
	public void highlight(PoseStack matrix, int x, int y) {
		RenderSystem.disableDepthTest();
	    RenderSystem.colorMask(true, true, true, false);
	    fillGradient(matrix, x, y, x + 16, y + 16, 
	    		-2130706433, -2130706433, getBlitOffset());
	    RenderSystem.colorMask(true, true, true, true);
	    RenderSystem.enableDepthTest();
	}
	
	public void setItem(T item) {
		this.item = item;
	}
	
	public T getItem() {
		return item;
	}
	
	public abstract void renderSlot(PoseStack matrix, int x, int y);
	
	public abstract void renderHovered(PoseStack matrix, int x, int y);
	
	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {
		
	}
	
}
