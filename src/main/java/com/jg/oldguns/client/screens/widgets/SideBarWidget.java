package com.jg.oldguns.client.screens.widgets;

import com.jg.oldguns.client.screens.widgets.JgListWidget.RenderableInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SideBarWidget extends GuiComponent implements Widget, GuiEventListener{

	protected ResourceLocation res;
	
	protected int x;
	protected int y;
	protected int sx;
	protected int sy;
	protected int w;
	protected int h;
	protected int sh;
	
	protected RenderableInfo info;
	
	public SideBarWidget(int x, int y, int w, int h, int sh, ResourceLocation res) {
		this.x = x;
		this.y = y;
		this.sx = x;
		this.sy = y;
		this.w = w;
		this.h = h;
		this.sh = sh;
		this.res = res;
	}
	
	@Override
	public void render(PoseStack matrix, int mx, int my, float ticks) {
		if(info == null) {
			LogUtils.getLogger().info("Slider Render Info is null");
			return;
		}
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, res);
		blit(matrix, this.sx, this.sy, info.getFromX(), info.getFromY(), info.getToX(), 
				info.getToY());
	}
	
	public float getProgress() {
		return ((this.sy + 0.0f) - this.y) / (this.h - (this.sh * 0.5f));
	}
	
	@Override
	public boolean mouseDragged(double x, double y, int p_94742_, double p_94743_, double p_94744_) {
		if(x > this.x && x < this.x + this.w && y > this.y && y < this.y + this.h){
            this.sy = (int) Mth.clamp(y, this.y, this.y + this.h - (this.sh * 0.5f));
        }
		return GuiEventListener.super.mouseDragged(x, y, p_94742_, p_94743_, p_94744_);
	}

	public void setXOffset(int xoffset){
        this.x += xoffset;
        this.sx = x;
    }

	public void setYOffset(int yoffset){
        this.y += yoffset;
        this.sy = y;
    }
	
	// Getters and setters
	
	public ResourceLocation getRes() {
		return res;
	}
	
	public void setX(int x) {
		this.x = x;
		this.sx = x;
	}

	public void setY(int y) {
		this.y = y;
		this.sy = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSx() {
		return sx;
	}

	public int getSy() {
		return sy;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int getSh() {
		return sh;
	}
	
	public void setRenderableInfo(RenderableInfo info) {
		this.info = info;
	}

}
