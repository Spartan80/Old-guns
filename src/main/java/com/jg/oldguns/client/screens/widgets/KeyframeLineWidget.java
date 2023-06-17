package com.jg.oldguns.client.screens.widgets;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Keyframe;
import com.jg.oldguns.client.animations.RepetitiveAnimation;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.screens.AnimationScreen;
import com.jg.oldguns.client.screens.widgets.JGSelectionList.Key;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;

public class KeyframeLineWidget extends GuiComponent implements Widget {

	public JGSelectionList pos;
	public JGSelectionList rot;
	protected List<Keyframe> keyframes;
	protected Font font;
	
	protected int minX;
	protected int maxVX;
	protected int maxX;
	protected int deltaX;
	protected int animDur;
	protected int selected;
	protected float scale;
	
	protected int scrollX;
	protected int scrollWidth;
	protected int offset;
	protected int scrollY;
	
	protected boolean visible;
	
	protected GunModel model;
	protected AnimationScreen screen;
	
	public KeyframeLineWidget(JGSelectionList pos, JGSelectionList rot, Font font, 
			GunModel model, AnimationScreen screen) {
		this.pos = pos;
		this.rot = rot;
		this.visible = true;
		this.minX = 10;
		this.maxVX = 400;
		this.deltaX = this.maxVX-this.minX;
		this.scale = 1;
		this.font = font;
		this.model = model;
		this.selected = -1;
		this.screen = screen;
		if(this.keyframes != null) {
			int x = (int)(this.minX + (Mth.lerp(this.keyframes.get(this.keyframes.size()-1)
		            .startVisualTick / this.animDur, 0, 
		            this.deltaX)*this.scale) - 10);
			this.maxX = x;
			this.scrollY = 220;
			this.scrollX = this.minX;
		    this.scrollWidth = (int)Mth.lerp((this.maxVX-10)/x, 0, this.deltaX);
		    float part1 = this.minX - this.scrollX;
	        float part2 = ((this.deltaX)-this.scrollWidth);
	        if(part1 != 0 && part2 != 0){
	            this.offset = (int)Math.abs(Mth.lerp(part1/part2, 0, this.maxX-this.maxVX));
	        } else {
	            this.offset = 0;
	        }
		} else {
			this.maxX = this.maxVX;
			this.scrollY = 340;
			this.scrollWidth = deltaX;
			this.scrollX = this.minX;
		}
	}
	
	public void tick() {
		
	}
	
	@Override
	public void render(PoseStack matrix, int x, int y, float partialTicks) {
		this.minX = 100;
		this.deltaX = maxVX-this.minX;
		if(this.visible) {
			if(keyframes != null && model.getAnimation() != null) {
				boolean oneTime = false;
				boolean cycle = false;
				for(int i = 0; i < keyframes.size(); i++) {
					Keyframe kf = keyframes.get(i);
					if(kf == null) {
						LogUtils.getLogger().info("null at index: " + i);
					}
					int kfx = (int)(this.minX + (Mth.lerp(this.keyframes.get(i)
			                .startTick / (float)this.animDur, 0, this.deltaX)
							*this.scale) - 10 - this.offset);
					if(kfx > minX-20 && kfx < maxVX+10) {
						RenderSystem.setShader(GameRenderer::getPositionTexShader);
						float[] color = new float[] { 1.0F, 1.0F, 1.0F };
						if(model.getAnimation() instanceof RepetitiveAnimation) {
							if(kf.type == 0) {
								if(cycle) {
									if(!oneTime) {
										color = new float[] { 0.2235F, 0.3294F, 0.2156F };
									} else {
										color = new float[] { 0.0823F, 0.1176F, 0.0784F };
									}
								}
							} else if(kf.type == 1) {
								cycle = true;
								if(!oneTime) {
									color = new float[] { 0.6745F, 0.8470F, 0.9921F };
								} else {
									color = new float[] { 0.0549F, 0.0549F, 0.0549F };
								}
							} else if(kf.type == 2) {
								if(!oneTime) {
									color = new float[] { 0.4901F, 0.1529F, 0.1568F };
								} else {
									color = new float[] { 0.2450F, 0.0764F, 0.0784F };
								}
								oneTime = true;
								if(i+1 < keyframes.size()) {
									if(keyframes.get(i+1).type == 0) {
										oneTime = false;
										cycle = false;
									}
								}
							}
						}
						boolean animSelected = screen.getGunModel().getAnimator()
								.getCurrent()-1 == i;
						if(this.selected == i) {
							color = new float[] { 0.6F, 0.6F, 0.6F };
						}else if((screen.getCurrent() == i)){// || animSelected)) {
							color = new float[] { 0.3F, 0.3F, 0.3F };
						} else if(animSelected) {
							color = new float[] { 1.0F, 1.0F, 1.0F };
						}
						RenderSystem.setShaderColor(color[0], color[1], color[2], 1.0F); 
						RenderSystem.setShaderTexture(0, AnimationScreen.WIDGETS);
						blit(matrix, kfx, 92, 
								(this.selected == i || 
									animSelected || 
											screen.getCurrent() == i) ? 208 : 
									224, 0, 15, 15);
					}
				}
				renderProgressbar();
				renderScrollbar();
			}
		}
	}

	public void renderProgressbar() {
		float x = Mth.lerp(model.getAnimator().getTick()/
				model.getAnimator().getAnimation().getDuration(), this.minX, this.maxX)-this.offset;
		if(x > minX-10 && x < maxVX-10) {
			Tesselator tesselator = Tesselator.getInstance();
			BufferBuilder bufferbuilder = tesselator.getBuilder();
			
			float nw = 10;
			float nh = 20;
			float nx = x;
			float ny = this.scrollY+92-10;
			RenderSystem.disableTexture();
			RenderSystem.setShader(GameRenderer::getPositionShader);
			RenderSystem.setShaderColor(0F, 0F, 0F, 0.1F);
			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
			bufferbuilder.vertex(nx, nh + ny, 0).endVertex();
			bufferbuilder.vertex(nw + nx, nh + ny, 0).endVertex();
			bufferbuilder.vertex(nw + nx, ny, 0).endVertex();
			bufferbuilder.vertex(nx, ny, 0).endVertex();
			tesselator.end();
		}
	}
	
	public void renderScrollbar() {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		
		float nw = this.deltaX;
		float nh = 7;
		float nx = this.minX;
		float ny = this.scrollY;
		RenderSystem.disableTexture();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0F, 0F, 0F, 0.1F);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		bufferbuilder.vertex(nx, nh + ny, 0).endVertex();
		bufferbuilder.vertex(nw + nx, nh + ny, 0).endVertex();
		bufferbuilder.vertex(nw + nx, ny, 0).endVertex();
		bufferbuilder.vertex(nx, ny, 0).endVertex();
		tesselator.end();

		float nw2 = this.scrollWidth;
		float nh2 = 7;
		float nx2 = this.scrollX;
		float ny2 = this.scrollY;

		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0.7706F, 0.7706F, 0.7706F, 0.1F);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		bufferbuilder.vertex(nx2, nh2 + ny2, 0).endVertex();
		bufferbuilder.vertex(nw2 + nx2, nh2 + ny2, 0).endVertex();
		bufferbuilder.vertex(nw2 + nx2, ny2, 0).endVertex();
		bufferbuilder.vertex(nx2, ny2, 0).endVertex();
		tesselator.end();
		RenderSystem.enableTexture();
	}
	
	public void check(int mouseX, int mouseY) {
		if(mouseX > this.minX && mouseX < this.maxVX && 
	            mouseY > this.scrollY+120 && mouseY < (this.scrollY)+130){
	            this.scrollX = mouseX-(this.scrollWidth/2);
	            this.wrapScrollbarX();
	            float part1 = this.minX - this.scrollX;
	            float part2 = ((this.deltaX)-this.scrollWidth);
	            if(part1 != 0 && part2 != 0){
	                this.offset = (int)Math.abs(Mth.lerp(part1/part2, 0, this.maxX-this.maxVX));
	            }
	        }
	}
	
	public void wrapScrollbarX() {
		if(this.scrollX > this.maxVX-this.scrollWidth){
            this.scrollX = this.maxVX-this.scrollWidth;
        }
        if(this.scrollX < this.minX){
            this.scrollX = this.minX;
        }
	}
	
	public void onClick(int mouseX, int mouseY) {
		if(this.visible) {
			if(keyframes != null) {
				LogUtils.getLogger().info("Keyframes not null");
				for(int i = 0; i < keyframes.size(); i++) {
					int kfx = (int) (this.minX + (Mth.lerp((float)this.keyframes.get(i)
			                .startTick / this.animDur, 0, this.deltaX)*this.scale) 
							- 10 - this.offset);
					if(mouseX > kfx && mouseX < kfx + 20 && mouseY > 92 
							&& mouseY < 112) {
						LogUtils.getLogger().info("collide");
						if(this.selected != i) {
							this.selected = i;
							LogUtils.getLogger().info("selected");
							processOnClick(i);
						} else {
							LogUtils.getLogger().info("else");
							this.selected = -1;
						}
					}
				}
			}
		}
	}

	public void processOnClick(int i) {
		try {
			Key[] newPosKeys = new Key[this.keyframes.get(i).translations.size()];
			int j = 0;
			for(GunModelPart s : this.keyframes.get(i).translations.keySet()) {
				newPosKeys[j] = new Key(font, s.getName());
				j++;
			}
			pos.setKeys(newPosKeys);
			Key[] newRotKeys = new Key[this.keyframes.get(i).rotations.size()];
			int l = 0;
			for(GunModelPart s : this.keyframes.get(i).rotations.keySet()) {
				newRotKeys[l] = new Key(font, s.getName());
				l++;
			}
			rot.setKeys(newRotKeys);
			screen.getEditBoxes().get(7).setValue(String.valueOf(
					this.keyframes.get(i).dur));
			screen.getEditBoxes().get(9).setValue(this.keyframes.get(i).easing);
			screen.getIntCycles().get(0).setValue(this.keyframes.get(i).type);
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public List<Keyframe> getKeyframes() {
		return keyframes;
	}

	public void setKeyframes(List<Keyframe> keyframes, int animDur) {
		this.animDur = animDur;
		this.keyframes = keyframes;
		if(this.keyframes != null) {
			float x = this.minX + (Mth.lerp(this.keyframes.get(this.keyframes.size()-1)
		            .startVisualTick / this.animDur, 0, 
		            this.deltaX)*this.scale) - 10 -
		                this.offset;
		    this.maxX = (int)x;
		    this.scrollWidth = (int)Mth.lerp((this.maxVX-10)/x, 0, this.deltaX);
		}
	}

	public void update(Animation animation) {
		this.animDur = animation.getDuration();
		this.keyframes = animation.getKeyframes();
		if(this.keyframes != null) {
			if(!keyframes.isEmpty()) {
				float x = this.minX + (Mth.lerp(this.keyframes.get(this.keyframes.size()-1)
			            .startVisualTick / this.animDur, 0, 
			            this.deltaX)*this.scale) - 10 -
			                this.offset;
			    this.maxX = (int)x;
			    this.scrollWidth = (int)Mth.lerp((this.maxVX-10)/x, 0, this.deltaX);
			    if(this.selected >= this.keyframes.size()) {
			    	this.selected = 0;
			    }
			} else {
				this.selected = -1;
			}
		}
	}
	
	public int getAnimDur() {
		return animDur;
	}

	public void setAnimDur(int animDur) {
		this.animDur = animDur;
		this.selected = -1;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setSelected(int selected) {
		this.selected = selected;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		if(keyframes != null) {
			float x = this.minX + (Mth.lerp(this.keyframes.get(this.keyframes.size()-1)
		            .startVisualTick / this.animDur, 0, 
		            this.deltaX)*this.scale) - 10 -
		                this.offset;
		    this.maxX = (int)x;
		    this.scrollWidth = (int)Mth.lerp((this.maxVX-10)/x, 0, this.deltaX);
		}
	}
	
	public float getScale() {
		return scale;
	}
	
	public int getSelected() {
		return selected;
	}

}
