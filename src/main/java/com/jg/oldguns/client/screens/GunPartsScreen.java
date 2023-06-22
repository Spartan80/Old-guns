package com.jg.oldguns.client.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.screens.widgets.GunPartKey;
import com.jg.oldguns.client.screens.widgets.JGSelectionList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.CycleButton.OnValueChange;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class GunPartsScreen extends Screen {

	private JGSelectionList gunPartList;
	private AnimationScreen animScreen;
	
	private final List<EditBox> edits;
	private final List<Button> buttons;
	private final List<CycleButton<Boolean>> booleanCycles;
	
	private GunModel model;
	
	private Transform t;
	
	private boolean ctrl;
	private boolean init;

	public GunPartsScreen(GunModel gunModel) {
		super(new TranslatableComponent("GunParts Screen"));
		this.edits = new ArrayList<>();
		this.buttons = new ArrayList<>();
		this.booleanCycles = new ArrayList<>();
		this.model = gunModel;
	}

	@Override
	protected void init() {
		super.init();
		//if(!init) {
			edits.clear();
			buttons.clear();
			booleanCycles.clear();
	
			buttons.add(new Button(342, 2, 30, 20, new TranslatableComponent("Anim"), (b) -> {
				Minecraft.getInstance().setScreen(animScreen);
			}));
	
			GunPartKey[] gunParts = new GunPartKey[model.getGunParts().size()];
			for (int i = 0; i < model.getGunParts().size(); i++) {
				gunParts[i] = new GunPartKey(font, model.getGunParts().get(i));
			}
			gunPartList = new JGSelectionList(gunParts, this, this.font, 100, 2, 60, 14, 4, (k, i) -> {
				edits.get(0).setValue(String.valueOf(model.getGunParts().get(i).getTransform().pos[0]));
				edits.get(1).setValue(String.valueOf(model.getGunParts().get(i).getTransform().pos[1]));
				edits.get(2).setValue(String.valueOf(model.getGunParts().get(i).getTransform().pos[2]));
				edits.get(3).setValue(String.valueOf(model.getGunParts().get(i).getTransform().rot[0]));
				edits.get(4).setValue(String.valueOf(model.getGunParts().get(i).getTransform().rot[1]));
				edits.get(5).setValue(String.valueOf(model.getGunParts().get(i).getTransform().rot[2]));
			});
	
			// Pos
			// x
			edits.add(new EditBox(font, 16, 0, 80, 20, new TranslatableComponent("animationScreen.pos.x")));
			edits.get(0).setValue("0");
			edits.get(0).setResponder((s) -> {
				try {
					float val = Float.parseFloat(s);
					Transform[] transforms = getTransforms();
					int i = 0;
					for (Transform t : transforms) {
						t.pos[0] = val;
						LogUtils.getLogger().info("i: " + i);
						i++;
					}
				} catch (Exception e) {
	
				}
			});
			// y
			edits.add(new EditBox(font, 16, 20, 80, 20, new TranslatableComponent("animationScreen.pos.y")));
			edits.get(1).setValue("0");
			edits.get(1).setResponder((s) -> {
				try {
					float val = Float.parseFloat(s);
					Transform[] transforms = getTransforms();
					for (Transform t : transforms) {
						t.pos[1] = val;
					}
				} catch (Exception e) {
	
				}
			});
			// z
			edits.add(new EditBox(font, 16, 40, 80, 20, new TranslatableComponent("animationScreen.pos.z")));
			edits.get(2).setValue("0");
			edits.get(2).setResponder((s) -> {
				try {
					float val = Float.parseFloat(s);
					Transform[] transforms = getTransforms();
					for (Transform t : transforms) {
						t.pos[2] = val;
					}
				} catch (Exception e) {
	
				}
			});
	
			// Rot
			// rx
			edits.add(new EditBox(font, 16, 60, 80, 20, new TranslatableComponent("animationScreen.rot.x")));
			edits.get(3).setValue("0");
			edits.get(3).setResponder((s) -> {
				try {
					float val = Float.parseFloat(s);
					Transform[] transforms = getTransforms();
					for (Transform t : transforms) {
						t.rot[0] = val;
					}
				} catch (Exception e) {
	
				}
			});
			// ry
			edits.add(new EditBox(font, 16, 80, 80, 20, new TranslatableComponent("animationScreen.rot.y")));
			edits.get(4).setValue("0");
			edits.get(4).setResponder((s) -> {
				try {
					float val = Float.parseFloat(s);
					Transform[] transforms = getTransforms();
					for (Transform t : transforms) {
						t.rot[1] = val;
					}
				} catch (Exception e) {
	
				}
			});
			// rz
			edits.add(new EditBox(font, 16, 100, 80, 20, new TranslatableComponent("animationScreen.rot.z")));
			edits.get(5).setValue("0");
			edits.get(5).setResponder((s) -> {
				try {
					float val = Float.parseFloat(s);
					if (!gunPartList.getSelectedIndexes().isEmpty()) {
						LogUtils.getLogger().info("not empty");
						for (int i : gunPartList.getSelectedIndexes()) {
							// LogUtils.getLogger().info("val: " + i);
						}
						Transform[] transforms = getTransforms();
						for (Transform t : transforms) {
							t.rot[2] = val;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
	
			// Buttons
			buttons.add(new Button(170, 2, 30, 20, new TranslatableComponent("Copy"), (b) -> {
				List<Integer> indexes = gunPartList.getSelectedIndexes();
				if(indexes.size() == 1) {
					if(!booleanCycles.get(0).getValue()) {
						this.t = model.getGunParts().get(indexes.get(0)).getTransform().copy();
					} else {
						this.t = model.getGunParts().get(indexes.get(0)).getDTransform().copy();
					}
				}
			}));
			
			buttons.add(new Button(170, 24, 30, 20, new TranslatableComponent("Paste"), (b) -> {
				List<Integer> indexes = gunPartList.getSelectedIndexes();
				if(indexes.size() == 1 && this.t != null) {
					int i = indexes.get(0);
					if(!booleanCycles.get(0).getValue()) {
						model.getGunParts().get(i).getTransform().copyFrom(this.t);
						edits.get(0).setValue(String.valueOf(model.getGunParts().get(i).getTransform().pos[0]));
						edits.get(1).setValue(String.valueOf(model.getGunParts().get(i).getTransform().pos[1]));
						edits.get(2).setValue(String.valueOf(model.getGunParts().get(i).getTransform().pos[2]));
						edits.get(3).setValue(String.valueOf(model.getGunParts().get(i).getTransform().rot[0]));
						edits.get(4).setValue(String.valueOf(model.getGunParts().get(i).getTransform().rot[1]));
						edits.get(5).setValue(String.valueOf(model.getGunParts().get(i).getTransform().rot[2]));
					} else {
						model.getGunParts().get(i).getDTransform().copyFrom(this.t);
						edits.get(0).setValue(String.valueOf(model.getGunParts().get(i).getDTransform().pos[0]));
						edits.get(1).setValue(String.valueOf(model.getGunParts().get(i).getDTransform().pos[1]));
						edits.get(2).setValue(String.valueOf(model.getGunParts().get(i).getDTransform().pos[2]));
						edits.get(3).setValue(String.valueOf(model.getGunParts().get(i).getDTransform().rot[0]));
						edits.get(4).setValue(String.valueOf(model.getGunParts().get(i).getDTransform().rot[1]));
						edits.get(5).setValue(String.valueOf(model.getGunParts().get(i).getDTransform().rot[2]));
					}
				}
			}));
			
			booleanCycles.add(buildBooleanCycle((s) -> {
				if(s) {
					return new TranslatableComponent("Dis");
				} else {
					return new TranslatableComponent("Tra");
				}
			}, false, 170, 46, 50, 20, 
				new TranslatableComponent("Type"), (c, v) -> {
						
				})
			);
	
			for (CycleButton<Boolean> e : booleanCycles) {
				addRenderableWidget(e);
			}
			for (EditBox edit : edits) {
				addRenderableWidget(edit);
			}
			for (Button button : buttons) {
				addRenderableWidget(button);
			}
			
			LogUtils.getLogger().info("Buttons size: " + buttons.size());
			init = true;
		//}
	}

	public Transform[] getTransforms() {
		Transform[] transforms = new Transform[gunPartList.getSelectedIndexes().size()];
		List<Integer> indexes = gunPartList.getSelectedIndexes();
		for (int i : indexes) {
			LogUtils.getLogger().info("val: " + i);
		}
		LogUtils.getLogger().info("indexes size: " + indexes.size());
		for (int i = 0; i < indexes.size(); i++) {
			LogUtils.getLogger().info("indexes val: " + indexes.get(i) + " i: " + i);
			int ind = indexes.get(i);
			GunPartKey gunKey = ((GunPartKey) gunPartList.getKeys().get(ind));
			transforms[i] = gunKey.getPart().getTransform();
		}
		if (!indexes.isEmpty()) {
			return transforms;
		}
		return new Transform[0];
	}

	@Override
	public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
		super.render(p_96562_, p_96563_, p_96564_, p_96565_);
		gunPartList.render(p_96562_, p_96563_, p_96564_, p_96565_);
	}
	
	private CycleButton<Boolean> buildBooleanCycle(Function<Boolean, Component> f, boolean initVal, int x, int y, int w,
			int h, Component t, OnValueChange<Boolean> ch) {
		CycleButton<Boolean> cycle = CycleButton.builder(f).withValues(true, false).withInitialValue(initVal).create(x,
				y, w, h, t, ch);
		return cycle;
	}

	@Override
	public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
		gunPartList.onClick((int) p_94695_, (int) p_94696_, ctrl);
		return super.mouseClicked(p_94695_, p_94696_, p_94697_);
	}

	@Override
	public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
		gunPartList.check((int) p_94699_, (int) p_94700_);
		return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
	}

	@Override
	public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
		gunPartList.onScroll((float) (p_94686_ * (-p_94688_)));
		return super.mouseScrolled(p_94686_, p_94687_, p_94688_);
	}

	@Override
	public boolean keyPressed(int key, int p_96553_, int p_96554_) {
		LogUtils.getLogger().info("key: " + key);
		if (key == 341) {
			ctrl = true;
		}
		return super.keyPressed(key, p_96553_, p_96554_);
	}

	@Override
	public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
		if (p_94715_ == 341) {
			ctrl = false;
		}
		return super.keyReleased(p_94715_, p_94716_, p_94717_);
	}

	@Override
	public void tick() {
		super.tick();
		gunPartList.tick();
	}

	public GunPartsScreen setAnimScreen() {
		this.animScreen = new AnimationScreen(model, this);
		return this;
	}

}
