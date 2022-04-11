package com.jg.oldguns.client.screens;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.ClientHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.CycleButton.OnValueChange;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Column.Ray;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class AnimationScreen extends Screen {

	private final List<Button> buttons;
	private final List<EditBox> edits;
	private final List<CycleButton<Integer>> cycles;
	
	private int axis = 0;
	private double mouseX, mouseY;
	private double deltaX, deltaY;
	private double lastScrolled;
	
	public static final ResourceLocation screen = new ResourceLocation(OldGuns.MODID, "textures/elements/gun_gui.png");
	
	public AnimationScreen() {
		super(new TranslatableComponent("Animation Screen"));
		buttons = new ArrayList<>();
		edits = new ArrayList<>();
		cycles = new ArrayList<>();
	}
	
	private CycleButton<Integer> buildIntCycle(Function<Integer, Component> f, int initVal, int x, int y, int w, int h, TranslatableComponent t, OnValueChange<Integer> ch, Integer... values){
		CycleButton<Integer> cycle = CycleButton.builder(f).withValues(values).withInitialValue(initVal).create(x, y, w, h, t, ch);
		return cycle;
	}
	
	@Override
	protected void init() {
		super.init();
		init2();
	}
	
	public void init2() {
		cycles.add(buildIntCycle((s) -> {
			switch(s) {
			case 0:
				return new TranslatableComponent("x");
			case 1:
				return new TranslatableComponent("y");
			case 2:
				return new TranslatableComponent("z");
			default:
				return new TranslatableComponent("x");
			}
			
		}, 0, 0, 140, 60, 20, new TranslatableComponent("Axis"), (c, v) -> {
			axis = v;
		}, 0, 1, 2));
		/*buttons.add(new Button(0, 100, 60, 20, new TranslatableComponent("Swap Axis"), (b) -> {
			axis = (axis + 1) % 3;
		}));*/
		
		//Translation
		edits.add(new EditBox(this.font, 1, 0, 80, 20, new TranslatableComponent("animator.stepT")));
		edits.get(0).setResponder((s) -> {
			try {
				float val = Float.parseFloat(s);
				ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
				if (handler != null) {
					handler.getGunModel().getPart().transform.offsetX = val;
					System.out.println(val);
				}
			} catch (Exception e) {
				
			}
		});
		edits.add(new EditBox(this.font, 1, 20, 80, 20, new TranslatableComponent("animator.stepT")));
		edits.get(1).setResponder((s) -> {
			try {
				float val = Float.parseFloat(s);
				ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
				if (handler != null) {
					handler.getGunModel().getPart().transform.offsetY = val;
					System.out.println(val);
				}
			} catch (Exception e) {
				
			}
		});
		edits.add(new EditBox(this.font, 1, 40, 80, 20, new TranslatableComponent("animator.stepT")));
		edits.get(2).setResponder((s) -> {
			try {
				float val = Float.parseFloat(s);
				ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
				if (handler != null) {
					handler.getGunModel().getPart().transform.offsetZ = val;
					System.out.println(val);
				}
			} catch (Exception e) {
				
			}
		});
		
		//Rotation
		edits.add(new EditBox(this.font, 1, 60, 80, 20, new TranslatableComponent("animator.stepT")));
		edits.get(3).setResponder((s) -> {
			try {
				float val = Float.parseFloat(s);
				ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
				if (handler != null) {
					handler.getGunModel().getPart().transform.rotationX = val;
					System.out.println(val);
				}
			} catch (Exception e) {

			}
		});
		edits.add(new EditBox(this.font, 1, 80, 80, 20, new TranslatableComponent("animator.stepT")));
		edits.get(4).setResponder((s) -> {
			try {
				float val = Float.parseFloat(s);
				ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
				if (handler != null) {
					handler.getGunModel().getPart().transform.rotationY = val;
					System.out.println(val);
				}
			} catch (Exception e) {

			}
		});
		edits.add(new EditBox(this.font, 1, 100, 80, 20, new TranslatableComponent("animator.stepT")));
		edits.get(5).setResponder((s) -> {
			try {
				float val = Float.parseFloat(s);
				ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
				if (handler != null) {
					handler.getGunModel().getPart().transform.rotationZ = val;
					System.out.println(val);
				}
			} catch (Exception e) {

			}
		});
		
		//Initializing widgets
		for(Button b : buttons) {
			addRenderableWidget(b);
		}
		
		for(EditBox e : edits) {
			addRenderableWidget(e);
		}
		
		for(CycleButton<Integer> e : cycles) {
			addRenderableWidget(e);
		}
	}
	
	@Override
	public boolean mouseScrolled(double p_94686_, double p_94687_, double dir) {
		if(dir > 0) {
			ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
			if(handler != null) {
				if(handler.isOnRotation()) {
					switch(axis) {
					case 0:
						handler.getGunModel().getPart().transform.rotationX += 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.rotationX);
					break;
					case 1:
						handler.getGunModel().getPart().transform.rotationY += 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.rotationY);
					break;
					case 2:
						handler.getGunModel().getPart().transform.rotationZ += 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.rotationZ);
					break;
					}
				}else {
					switch(axis) {
					case 0:
						handler.getGunModel().getPart().transform.offsetX += 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.offsetX);
					break;
					case 1:
						handler.getGunModel().getPart().transform.offsetY += 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.offsetY);
					break;
					case 2:
						handler.getGunModel().getPart().transform.offsetZ += 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.offsetZ);
					break;
					}
				}
			}
		}else {
			ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
			if(handler != null) {
				if(handler.isOnRotation()) {
					switch(axis) {
					case 0:
						handler.getGunModel().getPart().transform.rotationX -= 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.rotationX);
					break;
					case 1:
						handler.getGunModel().getPart().transform.rotationY -= 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.rotationX);
					break;
					case 2:
						handler.getGunModel().getPart().transform.rotationY -= 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.rotationX);
					break;
					}
				}else {
					switch(axis) {
					case 0:
						handler.getGunModel().getPart().transform.offsetX -= 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.offsetX);
					break;
					case 1:
						handler.getGunModel().getPart().transform.offsetY -= 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.offsetY);
					break;
					case 2:
						handler.getGunModel().getPart().transform.offsetZ -= 0.1f;
						System.out.println(handler.getGunModel().getPart().transform.offsetZ);
					break;
					}
				}
			}
		}
		return super.mouseScrolled(p_94686_, p_94687_, dir);
	}
	
	@Override
	public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
		return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
	}
	
	@Override
	public boolean mouseClicked(double x, double y, int p_94697_) {
		return super.mouseClicked(x, y, p_94697_);
	}
	
	@Override
	public boolean keyPressed(int p_96552_, int p_96553_, int p_96554_) {
		return super.keyPressed(p_96552_, p_96553_, p_96554_);
	}
	
	@Override
	public void render(PoseStack stack, int x, int y, float p_96565_) {
		/*int i = (this.width - 252) / 2;
	    int j = (this.height - 140) / 2;
		super.render(stack, x, y, p_96565_);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	    RenderSystem.enableBlend();
	    RenderSystem.setShader(GameRenderer::getPositionTexShader);
	    RenderSystem.setShaderTexture(0, screen);
	    this.blit(stack, i, j, 0, 0, 252, 140);*/
		super.render(stack, x, y, p_96565_);
	}
	
	@Override
	public void renderBackground(PoseStack p_96557_) {
		super.renderBackground(p_96557_);
	}

}
