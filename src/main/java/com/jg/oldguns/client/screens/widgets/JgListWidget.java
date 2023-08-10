package com.jg.oldguns.client.screens.widgets;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class JgListWidget<T extends JgListWidget
	.JgListKey> extends GuiComponent implements Widget, GuiEventListener, NarratableEntry {

	protected ResourceLocation res;
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected int itemWidth;
	protected int itemHeight;
	protected int cols;
	protected int rows;
	protected int itemsToShow;
	protected int from;
	protected int to;
	protected int offset;
	protected int selected;
	
	protected List<T> items;
	protected List<Integer> toRemove;
	
	protected RenderableInfo sliderRenderInfo;
	
	protected SideBarWidget slider;
	
	public JgListWidget(int x, int y, int w, int h, int itemSize, ResourceLocation res) {
		this(x, y, w, h, itemSize, itemSize, res);
	}
	
	public JgListWidget(int x, int y, int w, int h, int itemWidth, int itemHeight, 
			ResourceLocation res) {
		this(x, y, w, h, itemWidth, itemHeight, 0, 0, res);
	}
	
	public JgListWidget(int x, int y, int w, int h, int itemWidth, int itemHeight, int cols,
			int rows, ResourceLocation res) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.itemWidth = itemWidth;
		this.itemHeight = itemHeight;
		this.cols = cols == 0 ? h / itemHeight : cols;
		this.rows = rows == 0 ? w / itemWidth : rows;
		this.items = new ArrayList<>();
		this.toRemove = new ArrayList<>();
		this.itemsToShow = this.cols * this.rows > this.items.size() ? 
	            this.items.size() : this.cols * this.rows;
	    this.from = 0;
	    this.to = this.itemsToShow;
	    this.offset = 0;
	    this.selected = -1;
	    this.res = res;
	    this.slider = new SideBarWidget(x + w, y, 20, h, 30, res);
	    this.sliderRenderInfo = new RenderableInfo(176, 0, 187, 14, 12, 0);
	    this.slider.setRenderableInfo(new RenderableInfo(176, 0, 12, 15, 12, 0));
	}
	
	@Override
	public void render(PoseStack matrix, int mx, int my, float partialTicks) {
		if(sliderRenderInfo == null) {
			LogUtils.getLogger().info("Slider Render Info is null");
			return;
		}
		if(res == null) {
			LogUtils.getLogger().info("Texture can't be null");
			return;
		}
		
		for(int i : toRemove) {
			items.remove(i);
			update();
			selected = -1;
		}
		if(!toRemove.isEmpty()) {
			toRemove.clear();
		}
		
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, res);
		
		try {
		
		for(int i = this.from; i < this.to; i++){
            int index = i - this.offset;
            int x = (int) (this.x + ((index - (Math.floor(index / this.rows) * this.rows)) 
                * this.itemWidth));
            int y = (int) (this.y + (Math.floor(index / this.rows) * this.itemHeight));
            //((i - sideBar.getFrom()) * this.itemHeight)
            //LogUtils.getLogger().info("i: " + i + " offset: " + offset + " index: " + index 
            //		+ " keys size: " + items.size());
            
            items.get(i).render(matrix, x, y, mx, my, index, 
            		selected == i);
        }
		
		this.slider.render(matrix, mx, my, partialTicks);
		
		for(int i = this.from; i < this.to; i++){
            int index = i - this.offset;
            int x = (int) (this.x + ((index - (Math.floor(index / this.rows) * this.rows)) 
                * this.itemWidth));
            int y = (int) (this.y + (Math.floor(index / this.rows) * this.itemHeight));
            
            items.get(index).renderHovered(matrix, x, y, mx, my, index, 
            		selected == i);
        }
		
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean mouseClicked(double mx, double my, int btn) {
		if(btn == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			for(int i = this.from; i < this.to; i++){
	            int index = i - this.offset;
	            int x = (int) (this.x + ((index - (Math.floor(index / this.rows) * this.rows)) 
	                * this.itemWidth));
	            int y = (int) (this.y + (Math.floor(index / this.rows) * this.itemHeight));
	            if(mx > x && mx < x + this.itemWidth &&
	                my > y && my < y + this.itemHeight){
	            	if(this.selected == i){
	                    this.selected = -1;
	                    items.get(i).superOnUnselect(mx, my, i);
	                } else{
	                    this.selected = i;
	                    items.get(i).superOnClick(mx, my, i);
	                }
	            }
	        }
		}
		return GuiEventListener.super.mouseClicked(mx, my, btn);
	}
	
	@Override
	public boolean mouseReleased(double p_94753_, double p_94754_, int p_94755_) {
		return GuiEventListener.super.mouseReleased(p_94753_, p_94754_, p_94755_);
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h;
	}

	protected void update() {
		this.itemsToShow = this.cols * this.rows > this.items.size() ? 
	            this.items.size() : this.cols * this.rows;
	    this.from = 0;
	    this.to = this.itemsToShow;
	}
	
	public void addItem(T item) {
		this.items.add(item);
		update();
	}
	
	public void removeItem(int index) {
		toRemove.add(index);
		/*this.items.remove(index);
		update();
		selected = -1;*/
	}
	
	public void removeItem(T item) {
		int index = -1;
		for(int i = 0; i < items.size(); i++) {
			if(item == items.get(i)) {
				index = i;
				break;
			}
		}
		if(index != -1) {
			items.remove(index);
			update();
			selected = -1;
		} else {
			throw new IllegalStateException("No element found");
		}
	}
	
	public void clearItems() {
		this.items.clear();
		update();
	}
	
	public void tick() {
		
	}
	
	@Override
	public boolean mouseDragged(double p_94740_, double p_94741_, int p_94742_, double p_94743_, double p_94744_) {
		
		/*int maxVisibleItems = this.itemsToShow;
		
		this.offset = (int) Math.floor(Mth.lerp(this.slider.getProgress(), 0, 
				this.items.size() - maxVisibleItems));
	        this.offset = (int) (Math.ceil(this.offset / this.rows) * this.rows);
	        this.offset = this.offset > this.items.size() ? this.items.size() : 
	            this.offset;
		
        // this.prepareOffset(maxVisibleItems);
        
        this.from = this.offset;
        this.to = this.offset + this.itemsToShow > this.items.size() ? 
            this.items.size() : this.offset + this.itemsToShow;
		
        LogUtils.getLogger().info("process: " + slider.getProgress() +  " offset: " 
        		+ offset + " from: " + from + " to: " + to);*/
        
        /*this.itemsToShow = this.cols * this.rows > items.size() ? items.size() : 
        	this.cols * this.rows;
        this.offset = (int) Mth.lerp(slider.getProgress(), 0, items.size() - itemsToShow);
        this.from = offset;
        this.to = offset;*/
        int maxVisibleItems = cols * rows; 
        int toUse = rows > cols ? rows : cols;
        int step = (int) Mth.lerp(slider.getProgress(), 0, Math.ceil(maxVisibleItems / toUse 
        		+ 0.0f));
        this.itemsToShow = this.cols * this.rows > items.size() ? items.size() : 
        	this.cols * this.rows;
        this.offset = (step * toUse);/*(int) Mth.lerp(slider.getProgress(), 0, (items.size() - 1) - 
        		itemsToShowTest);*/ //  - 1 < 0 ? 0 : (step * rows) - 1
        this.from = offset;
        int toShow = items.size() - offset > maxVisibleItems ? maxVisibleItems : 
        	items.size() - offset;
        this.to = offset + toShow; //  - 1 < 0 ? 0 : offsetTest + toShow - 1
        
        LogUtils.getLogger().info("itemsToShow: " + itemsToShow + " offsetTest: " + offset
        		+ " fromTest: " + from + " toTest: " + to + " itemsSize: " + items.size()
        		+ " step: " + step + " toShow: " + toShow);
        
		this.slider.mouseDragged(p_94740_, p_94741_, p_94742_, p_94743_, p_94744_);
		return GuiEventListener.super.mouseDragged(p_94740_, p_94741_, p_94742_, p_94743_, p_94744_);
	}
	
	// Getters and setters
	
	public void setSliderPos(int x, int y) {
		this.slider.setX(x);
		this.slider.setY(y);
	}
	
	public JgListWidget<T> setItems(List<T> items){
		this.items = items;
		return this;
	}

	public void setSliderRenderInfo(RenderableInfo sliderRenderInfo) {
		this.sliderRenderInfo = sliderRenderInfo;
		this.slider.setRenderableInfo(sliderRenderInfo);
	}
	
	public int getSelectedIndex() {
		return selected;
	}
	
	public T getItem(int index) {
		return items.get(index);
	}
	
	public T getSelectedItem() {
		if(selected != -1) {
			return items.get(selected);
		}
		return null;
	}
	
	public void select(int index) {
		this.selected = index;
        items.get(index).superOnClick(0, 0, index);
	}
	
	public RenderableInfo getSliderRenderInfo() {
		return sliderRenderInfo;
	}
	
	public SideBarWidget getSideBar() {
		return slider;
	}
	
	public static abstract class JgListKey extends GuiComponent {
		
		protected boolean pressed; 
		
		public JgListKey() {
			
		}

		public void shadow(PoseStack matrix, int x, int y, int w, int h) {
			RenderSystem.disableDepthTest();
		    RenderSystem.colorMask(true, true, true, false);
		    // Llamada a fillGradient con los colores personalizados
		    fillGradient(matrix, x, y, x + w, y + h, 0x90000000, 0x90000000);
		    RenderSystem.colorMask(true, true, true, true);
		    RenderSystem.enableDepthTest();
		}
		
		public void shadow(PoseStack matrix, int x, int y) {
			RenderSystem.disableDepthTest();
		    RenderSystem.colorMask(true, true, true, false);
		    // Llamada a fillGradient con los colores personalizados
		    fillGradient(matrix, x, y, x + 16, y + 16, 0x90000000, 0x90000000);
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
		
		public void highlight(PoseStack matrix, int x, int y) {
			RenderSystem.disableDepthTest();
		    RenderSystem.colorMask(true, true, true, false);
		    fillGradient(matrix, x, y, x + 16, y + 16, 
		    		-2130706433, -2130706433, getBlitOffset());
		    RenderSystem.colorMask(true, true, true, true);
		    RenderSystem.enableDepthTest();
		}
		
		public boolean isHovered(int x, int y, int mouseX, int mouseY, int w, int h) {
			return mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h;
		}
		
		public boolean isHovered(int x, int y, int mouseX, int mouseY) {
			return mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16;
		}
		
		public void superOnClick(double mx, double my, int index) {
			pressed = true;
			onClick(mx, my, index);
		}
		
		public void superOnUnselect(double mx, double my, int index) {
			pressed = false;
			onUnselect(mx, my, index);
		}
		
		public abstract void onClick(double mx, double my, int index);
		
		//public abstract void onRelease(double mx, double my, int index);
		
		public abstract void onUnselect(double mx, double my, int index);
		
		public abstract void render(PoseStack matrix, int x, int y, 
				int mouseX, int mouseY, int index, boolean selected);
		
		public abstract void renderHovered(PoseStack matrix, int x, int y, 
				int mouseX, int mouseY, int index, boolean selected);
	}
	
	public static class RenderableInfo {
		
		private int fromX;
		private int fromY;
		private int toX;
		private int toY;
		private int offsetX;
		private int offsetY;
		
		public RenderableInfo() {
			
		}
		
		public RenderableInfo(int fromX, int fromY, int toX, int toY) {
			this(fromX, fromY, toX, toY, 16, 16);
		}
		
		public RenderableInfo(int fromX, int fromY, int toX, int toY, int offsetX, int offsetY) {
			this.fromX = fromX;
			this.fromY = fromY;
			this.toX = toX;
			this.toY = toY;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
		}

		public int getFromX() {
			return fromX;
		}

		public RenderableInfo setFromX(int fromX) {
			this.fromX = fromX;
			return this;
		}

		public int getFromY() {
			return fromY;
		}

		public RenderableInfo setFromY(int fromY) {
			this.fromY = fromY;
			return this;
		}

		public int getToX() {
			return toX;
		}

		public RenderableInfo setToX(int toX) {
			this.toX = toX;
			return this;
		}

		public int getToY() {
			return toY;
		}

		public RenderableInfo setToY(int toY) {
			this.toY = toY;
			return this;
		}

		public int getOffsetX() {
			return offsetX;
		}

		public RenderableInfo setOffsetX(int offsetX) {
			this.offsetX = offsetX;
			return this;
		}

		public int getOffsetY() {
			return offsetY;
		}

		public RenderableInfo setOffsetY(int offsetY) {
			this.offsetY = offsetY;
			return this;
		}
		
	}

	@Override
	public void updateNarration(NarrationElementOutput p_169152_) {
		
	}

	@Override
	public NarrationPriority narrationPriority() {
		return NarrationPriority.NONE;
	}
	
}
