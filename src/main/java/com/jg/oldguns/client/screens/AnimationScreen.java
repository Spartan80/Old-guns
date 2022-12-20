package com.jg.oldguns.client.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Keyframe;
import com.jg.oldguns.client.animations.RepetitiveAnimation;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.animations.serializers.AnimationSerializer;
import com.jg.oldguns.client.handlers.ClientsHandler;
import com.jg.oldguns.client.screens.widgets.GunPartKey;
import com.jg.oldguns.client.screens.widgets.JGSelectionList;
import com.jg.oldguns.client.screens.widgets.JGSelectionList.Key;
import com.jg.oldguns.client.screens.widgets.JgEditBox;
import com.jg.oldguns.client.screens.widgets.KeyframeLineWidget;
import com.jg.oldguns.utils.MathUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.CycleButton.OnValueChange;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AnimationScreen extends Screen {

	public static ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");

	private GunPartsScreen gunPartScreen;
	private final List<Button> buttons;
	private final List<EditBox> edits;
	private final List<OptionsList> options;
	private final List<CycleButton<Integer>> integerCycles;
	private final List<CycleButton<Boolean>> booleanCycles;
	private final List<Integer> cycles;
	List<Pair<GunModelPart, float[]>> transforms;
	// private AnimationSelectionList list;
	private final GunModel model;

	KeyframeLineWidget keyframeLine;
	JGSelectionList gunPartList;
	JGSelectionList posList;
	JGSelectionList rotList;

	private int i;
	private int j;

	private int minX, maxX, deltaX;
	private int scrollMax, scale;
	private int current;
	
	private boolean cycleStarted;
	private boolean ctrl;
	private boolean start;
	private boolean rot;
	private float prog;
	private float prev;
	private float MAX = 4f;
	private boolean[] keys;
	private boolean init;
	private boolean keyframe;

	private Keyframe kf;

	public AnimationScreen(GunModel model, GunPartsScreen screen) {
		super(Component.translatable("Animation Screen"));
		this.gunPartScreen = screen;
		this.buttons = new ArrayList<>();
		this.edits = new ArrayList<>();
		this.options = new ArrayList<>();
		this.integerCycles = new ArrayList<>();
		this.booleanCycles = new ArrayList<>();
		this.cycles = new ArrayList<>();
		this.transforms = new ArrayList<>();
		this.model = model;
		this.keyframe = true;
		this.i = width / 2;
		this.j = height / 2;
		this.keys = new boolean[400];
	}

	@Override
	protected void init() {
		super.init();
		// if(!init) {

		edits.clear();
		buttons.clear();
		booleanCycles.clear();

		posList = new JGSelectionList(new JGSelectionList.Key[0], this, this.font, 202, 2, 60, 14, 4, (k, i) -> {
			if (model.getAnimation() != null) {
				if(keyframeLine.getSelected() != -1) {
					Keyframe kf = model.getAnimation().getKeyframes().get(keyframeLine
							.getSelected());
					GunModelPart part = Utils.getGunPartByName(model, k.getKey());
					if(kf.translations.containsKey(part)) {
						edits.get(0).setValue(String.valueOf(kf.translations.get(part)[0]));
						edits.get(1).setValue(String.valueOf(kf.translations.get(part)[1]));
						edits.get(2).setValue(String.valueOf(kf.translations.get(part)[2]));
						LogUtils.getLogger().info("key: " + k.getKey());
					} else {
						LogUtils.getLogger().info("key: " + k.getKey() + 
								" doesnt have a transform");
					}
				}
			}
		});
		rotList = new JGSelectionList(new JGSelectionList.Key[0], this, this.font, 273, 2, 60, 14, 4, (k, i) -> {
			if (model.getAnimation() != null) {
				if(keyframeLine.getSelected() != -1) {
					Keyframe kf = model.getAnimation().getKeyframes().get(keyframeLine
							.getSelected());
					GunModelPart part = Utils.getGunPartByName(model, k.getKey());
					if(kf.rotations.containsKey(part)) {
						edits.get(3).setValue(String.valueOf(kf.rotations.get(part)[0]));
						edits.get(4).setValue(String.valueOf(kf.rotations.get(part)[1]));
						edits.get(5).setValue(String.valueOf(kf.rotations.get(part)[2]));
					}
				}
			}
		});

		GunPartKey[] gunParts = new GunPartKey[model.getGunParts().size()];
		for (int i = 0; i < model.getGunParts().size(); i++) {
			gunParts[i] = new GunPartKey(font, model.getGunParts().get(i));
		}
		gunPartList = new JGSelectionList(gunParts, this, this.font, 100, 2, 60, 14, 4, (k, i) -> {

		});

		keyframeLine = new KeyframeLineWidget(posList, rotList, font, model, this);

		booleanCycles.add(buildBooleanCycle((s) -> {
			if (s) {
				return Component.translatable("R");
			} else {
				return Component.translatable("T");
			}
		}, false, 335, 2, 30, 20, Component.translatable(""), (c, v) -> {
			rot = v;
		}));
		
		booleanCycles.add(buildBooleanCycle((s) -> {
			if (s) {
				return Component.translatable("K");
			} else {
				return Component.translatable("T");
			}
		}, false, 100, 166, 60, 20, Component.translatable("Keyframe"), (c, v) -> {
			keyframe = v;
		}));
		
		booleanCycles.add(buildBooleanCycle((s) -> {
			if (s) {
				return Component.translatable("T");
			} else {
				return Component.translatable("F");
			}
		}, false, 100, 210, 60, 20, Component.translatable("Loop"), (c, v) -> {
			ClientsHandler.getClient(Minecraft.getInstance().getUser()).loop = v;
		}));
		booleanCycles.get(2).setValue(ClientsHandler.getClient(Minecraft.getInstance()
				.getUser()).loop);
		
		integerCycles.add(buildIntCycle((s) -> {
			if (s == 0) {
				return Component.translatable("NON");
			} else if (s == 1) {
				return Component.translatable("SC");
			} else if(s == 2) {
				return Component.translatable("EC");
			}
			return Component.translatable("NON");
		}, 0, 100, 188, 60, 20, Component.translatable("Type"), (c, v) -> {
			if(model.getAnimation() != null) {
				if(keyframeLine.getSelected() != -1) {
					LogUtils.getLogger().info("v: " + v);
					if(v == 1 && !cycleStarted) {
						getSelectedKeyframe().type = v;
					} else if(v == 1 && cycleStarted) {
						LogUtils.getLogger().info("A cycle is already started");
					} else {
						getSelectedKeyframe().type = v;
					}
					if(v == 1) {
						cycleStarted = true;
					} else if(v == 2) {
						cycleStarted = false;
					}
				}
			}
		}, 0, 1, 2));

		buttons.add(new Button(372, 2, 50, 20, Component.translatable("GunParts"), (b) -> {
			Minecraft.getInstance().setScreen(gunPartScreen);
		}));

		// String.valueOf(((GunPartKey)gunPartList.getSelectedKey())
		// .getPart().getTransform().pos[0])
		// Pos
		// x
		edits.add(new EditBox(font, 16, 0, 80, 20, Component.translatable("animationScreen.pos.x")));
		edits.get(0).setValue("0");
		edits.get(0).setResponder((s) -> {
			try {
				/*float val = Float.parseFloat(s);
				if (!posList.getSelectedIndexes().isEmpty()) {
					if (model.getAnimation() != null) {
						setVecVal(false, 0, val);
						LogUtils.getLogger().info("here");
					}
				}*/
				if (model.getAnimation() == null)
					return;
				if (!posList.getSelectedIndexes().isEmpty()) {
					getSelectedKeyframe().translations.get(getPart(posList
							.getSelectedKey().getKey()))[0] = Float
							.parseFloat(s);
					System.out.println("Val: "
							+ getSelectedKeyframe().translations.get(getPart(
									posList.getSelectedKey().getKey()))[0]);
				}
			} catch (Exception e) {

			}
		});
		// y
		edits.add(new EditBox(font, 16, 20, 80, 20, Component.translatable("animationScreen.pos.y")));
		edits.get(1).setValue("0");
		edits.get(1).setResponder((s) -> {
			try {
				/*float val = Float.parseFloat(s);
				if (!posList.getSelectedIndexes().isEmpty()) {
					if (model.getAnimation() != null) {
						LogUtils.getLogger().info("changing value");
						setVecVal(false, 1, val);
					}
				}*/
				if (model.getAnimation() == null)
					return;
				if (!posList.getSelectedIndexes().isEmpty()) {
					getSelectedKeyframe().translations.get(getPart(posList
							.getSelectedKey().getKey()))[1] = Float
							.parseFloat(s);
					System.out.println("Val: "
							+ getSelectedKeyframe().translations.get(getPart(
									posList.getSelectedKey().getKey()))[1]);
				}
			} catch (Exception e) {

			}
		});
		// z
		edits.add(new EditBox(font, 16, 40, 80, 20, Component.translatable("animationScreen.pos.z")));
		edits.get(2).setValue("0");
		edits.get(2).setResponder((s) -> {
			try {
				/*float val = Float.parseFloat(s);
				if (!posList.getSelectedIndexes().isEmpty()) {
					if (model.getAnimation() != null) {
						setVecVal(false, 2, val);
					}
				}*/
				if (model.getAnimation() == null)
					return;
				if (!posList.getSelectedIndexes().isEmpty()) {
					getSelectedKeyframe().translations.get(getPart(posList
							.getSelectedKey().getKey()))[2] = Float
							.parseFloat(s);
					System.out.println("Val: "
							+ getSelectedKeyframe().translations.get(getPart(
									posList.getSelectedKey().getKey()))[2]);
				}
			} catch (Exception e) {

			}
		});

		// Rot
		// rx
		edits.add(new EditBox(font, 16, 60, 80, 20, Component.translatable("animationScreen.rot.x")));
		edits.get(3).setValue("0");
		edits.get(3).setResponder((s) -> {
			try {
				/*float val = Float.parseFloat(s);
				if (!posList.getSelectedIndexes().isEmpty()) {
					if (model.getAnimation() != null) {
						setVecVal(true, 0, val);
					}
				}*/
				if (model.getAnimation() == null)
					return;
				if (!rotList.getSelectedIndexes().isEmpty()) {
					getSelectedKeyframe().rotations.get(getPart(rotList
							.getSelectedKey().getKey()))[0] = Float
							.parseFloat(s);
					System.out.println("Val: "
							+ getSelectedKeyframe().rotations.get(getPart(
									rotList.getSelectedKey().getKey()))[0]);
				}
			} catch (Exception e) {

			}
		});
		// ry
		edits.add(new EditBox(font, 16, 80, 80, 20, Component.translatable("animationScreen.rot.y")));
		edits.get(4).setValue("0");
		edits.get(4).setResponder((s) -> {
			try {
				/*float val = Float.parseFloat(s);
				if (!posList.getSelectedIndexes().isEmpty()) {
					if (model.getAnimation() != null) {
						setVecVal(true, 1, val);
					}
				}*/
				if (model.getAnimation() == null)
					return;
				if (!rotList.getSelectedIndexes().isEmpty()) {
					getSelectedKeyframe().rotations.get(getPart(rotList
							.getSelectedKey().getKey()))[1] = Float
							.parseFloat(s);
					System.out.println("Val: "
							+ getSelectedKeyframe().rotations.get(getPart(
									rotList.getSelectedKey().getKey()))[1]);
				}
			} catch (Exception e) {

			}
		});
		// rz
		edits.add(new EditBox(font, 16, 100, 80, 20, Component.translatable("animationScreen.rot.z")));
		edits.get(5).setValue("0");
		edits.get(5).setResponder((s) -> {
			try {
				/*float val = Float.parseFloat(s);
				if (!posList.getSelectedIndexes().isEmpty()) {
					if (model.getAnimation() != null) {
						setVecVal(true, 2, val);
					}
				}*/
				if (model.getAnimation() == null)
					return;
				if (!rotList.getSelectedIndexes().isEmpty()) {
					getSelectedKeyframe().rotations.get(getPart(rotList
							.getSelectedKey().getKey()))[2] = Float
							.parseFloat(s);
					System.out.println("Val: "
							+ getSelectedKeyframe().rotations.get(getPart(
									rotList.getSelectedKey().getKey()))[2]);
				}
			} catch (Exception e) {

			}
		});
		// File managing
		edits.add(new JgEditBox(font, 100, 59, 80, 20, Component.translatable("animationScreen.file")) {

		});
		edits.get(6).setValue("");
		edits.get(6).setResponder((s) -> {

		});
		edits.add(new EditBox(font, 16, 120, 80, 20, Component.translatable("animationScreen.dur")));
		edits.get(7).setValue("0");
		edits.get(7).setResponder((s) -> {
			/*
			 * if(keyframeLine.getSelected() != -1 && !edits.get(7).getValue().isBlank()) {
			 * keyframeLine.getKeyframes()[keyframeLine.getSelected()].dur =
			 * Integer.parseInt(edits.get(7).getValue()); }
			 */
			if (model.getAnimation() == null)
				return;
			if(keyframeLine.getSelected() != -1) {
				getSelectedKeyframe().dur = (int)Float.parseFloat(s.isEmpty() ? "1" 
						: s);
				Utils.updateKeyframesFromAnimation(model.getAnimation());
				keyframeLine.update(model.getAnimation());
			}
		});
		edits.add(new EditBox(font, 16, 160, 80, 20, Component.translatable("animationScreen.scale")));
		edits.get(8).setValue("1");
		edits.get(8).setResponder((s) -> {
			if (!edits.get(8).getValue().isBlank()) {
				keyframeLine.setScale(Integer.parseInt(edits.get(8).getValue()));
			}
		});
		edits.add(new EditBox(font, 16, 182, 80, 20, Component.translatable("animationScreen.easing")));
		edits.get(9).setValue("empty");
		edits.get(9).setResponder((s) -> {
			if (!edits.get(9).getValue().isBlank()) {
				if(keyframeLine.getSelected() != -1) {
					getSelectedKeyframe().easing = s;
				}
			}
		});
		edits.add(new EditBox(font, 16, 138, 80, 20, Component.translatable("animationScreen.times")));
		edits.get(10).setValue("1");
		
		// Buttons
		buttons.add(new Button(342, 24, 30, 20, Component.translatable("Play"), (b) -> {
			model.setPlayAnimation(true);
		}));
		buttons.add(new Button(374, 24, 30, 20, Component.translatable("Stop"), (b) -> {
			model.setPlayAnimation(false);
		}));
		buttons.add(new Button(342, 44, 30, 20, Component.translatable("Next"), (b) -> {
			if (model.getAnimation() != null) {
				model.getAnimator().nextTick();
			}
		}));
		buttons.add(new Button(374, 44, 30, 20, Component.translatable("Prev"), (b) -> {
			if (model.getAnimation() != null) {
				model.getAnimator().prevTick();
			}
		}));
		buttons.add(new Button(202, 59, 30, 20, Component.translatable("Update"), (b) -> {
			if(model.getAnimation() instanceof RepetitiveAnimation) {
				if(!edits.get(10).getValue().isBlank()) {
					((RepetitiveAnimation)model.getAnimation())
						.setTimes(Integer.parseInt(edits.get(10).getValue()));
				} else {
					((RepetitiveAnimation)model.getAnimation()).setTimes(1);
				}
				keyframeLine.update(model.getAnimation());
			}
		}));
		buttons.add(new Button(234, 59, 30, 20, Component.translatable("Add"), (b) -> {
			if (model.getAnimation() != null) {
				if (keyframeLine.getSelected() != -1) {
					Keyframe kf = new Keyframe(4);
					Keyframe kfsel = getSelectedKeyframe();
					kf.startTick = kfsel.startTick + kfsel.dur;
					kf.startVisualTick = kf.startTick + 4;
					Utils.insertInto(keyframeLine.getSelected(), 
							model.getAnimation().getKeyframes(), kf);
				} else {
					Keyframe kf = new Keyframe(4);
					kf.startTick = 0;
					kf.startVisualTick = 4;
					model.getAnimation().getKeyframes().add(kf);
				}
				Utils.updateKeyframesFromAnimation(model.getAnimation());
				keyframeLine.update(model.getAnimation());
			}
		}));

		buttons.add(new Button(266, 59, 30, 20, Component.translatable("Remove"), (b) -> {
			Key[] posKeys = posList.getSelectedKeys();
			Key[] rotKeys = rotList.getSelectedKeys();
			boolean pos = false;
			boolean rot = false;
			if (posKeys.length > 0) {
				for (Key key : posKeys) {
					model.getAnimation().getKeyframes().get(keyframeLine.getSelected())
					.translations.remove(getPart(key.getKey()));
					posList.removeKey(key);
					pos = true;
					LogUtils.getLogger().info("deleted");
				}
			}
			if (rotKeys.length > 0) {
				for (Key key : rotKeys) {
					model.getAnimation().getKeyframes().get(keyframeLine.getSelected())
					.rotations.remove(getPart(key.getKey()));
					rotList.removeKey(key);
					rot = true;
				}
			}
			if (!pos && !rot) {
				if (model.getAnimation() != null && keyframeLine.getSelected() != -1) {
					Utils.updateKeyframesFromAnimation(model.getAnimation());
					keyframeLine.update(model.getAnimation());
				}
			}
		}));

		buttons.add(new Button(298, 59, 30, 20, Component.translatable("Set"), (b) -> {
			if (model.getAnimation() != null && 
					!gunPartList.getSelectedIndexes().isEmpty() && 
					keyframeLine.getSelected() != -1) {
				Keyframe kf = getSelectedKeyframe();
				GunModelPart part = model.getGunParts().get(gunPartList
						.getSelectedIndexes().get(0));
				LogUtils.getLogger().info("sd");
				LogUtils.getLogger().info("index: " + gunPartList.getSelectedIndexes().get(0));
				if (!booleanCycles.get(0).getValue()) {
					LogUtils.getLogger().info("sd");
					if(!kf.translations.containsKey(part)) {
						kf.translations.put(part, new float[] { 0, 0, 0 });
						posList.addKey(new Key(font, part.getName()));
					}
					kf.translations.get(part)[0] = part.getTransform().pos[0];
					kf.translations.get(part)[1] = part.getTransform().pos[1];
					kf.translations.get(part)[2] = part.getTransform().pos[2];
				} else {
					if(!kf.rotations.containsKey(part)) {
						kf.rotations.put(part, new float[] { 0, 0, 0 });
						rotList.addKey(new Key(font, part.getName()));
					}
					kf.rotations.get(part)[0] = part.getTransform().rot[0];
					kf.rotations.get(part)[1] = part.getTransform().rot[1];
					kf.rotations.get(part)[2] = part.getTransform().rot[2];
				}
			}
			
		}));
		
		buttons.add(new Button(100, 144, 30, 20, Component.translatable("Save"), (b) -> {
			if(model.getAnimation() != null) {
				AnimationSerializer.serializeWithCode(model.getAnimation(), model);
			}
		}));
		
		buttons.add(new Button(100, 122, 60, 20, Component.translatable("UpdateParts"), (b) -> {
			model.getAnimator().switchUpdateParts();
			Utils.updateKeyframesFromAnimation(model.getAnimation());
			keyframeLine.update(model.getAnimation());
		}));
		
		buttons.add(new Button(132, 144, 28, 20, Component.translatable("Quit"), (b) -> {
			if(model.getAnimation() != null) {
				model.getAnimator().finishAll();
			}
		}));
		
		buttons.add(new Button(164, 122, 30, 20, Component.translatable("NextK"), (b) -> {
			if(model.getAnimation() != null) {
				if(current < model.getAnimation().getKeyframes().size()-1) {
					current++;
					LogUtils.getLogger().info("Current: " + current);
					Keyframe kf = model.getAnimation().getKeyframes().get(current);
					for(Entry<GunModelPart, float[]> e : kf.translations.entrySet()) {
						e.getKey().getTransform().pos[0] = Mth.lerp(1, 0, 
								e.getValue()[0]);
						e.getKey().getTransform().pos[1] = Mth.lerp(1, 0, 
								e.getValue()[1]);
						e.getKey().getTransform().pos[2] = Mth.lerp(1, 0, 
								e.getValue()[2]);
					}
					for(Entry<GunModelPart, float[]> e : kf.rotations.entrySet()) {
						e.getKey().getTransform().rot[0] = MathUtils.rotLerp(1, 0, 
								e.getValue()[0]);
						e.getKey().getTransform().rot[1] = MathUtils.rotLerp(1, 0, 
								e.getValue()[1]);
						e.getKey().getTransform().rot[2] = MathUtils.rotLerp(1, 0, 
								e.getValue()[2]);
					}
				} else {
					current = 0;
				}
			}
		}));
		
		buttons.add(new Button(164, 144, 30, 20, Component.translatable("PrevK"), (b) -> {
			if(model.getAnimation() != null) {
				if(current > 0) {
					current--;
					LogUtils.getLogger().info("Current: " + current);
					Keyframe kf = model.getAnimation().getKeyframes().get(current);
					for(Entry<GunModelPart, float[]> e : kf.translations.entrySet()) {
						e.getKey().getTransform().pos[0] = Mth.lerp(1, 0, 
								e.getValue()[0]);
						e.getKey().getTransform().pos[1] = Mth.lerp(1, 0, 
								e.getValue()[1]);
						e.getKey().getTransform().pos[2] = Mth.lerp(1, 0, 
								e.getValue()[2]);
					}
					for(Entry<GunModelPart, float[]> e : kf.rotations.entrySet()) {
						e.getKey().getTransform().rot[0] = MathUtils.rotLerp(1, 0, 
								e.getValue()[0]);
						e.getKey().getTransform().rot[1] = MathUtils.rotLerp(1, 0, 
								e.getValue()[1]);
						e.getKey().getTransform().rot[2] = MathUtils.rotLerp(1, 0, 
								e.getValue()[2]);
					}
				}
			}
		}));
		
		buttons.add(new Button(164, 166, 60, 20, Component.translatable("AddZeroKf"), (b) -> {
			Animation anim = model.getAnimation();
			if(anim != null) {
				if(keyframeLine.getSelected() == -1) {
					if(!anim.getKeyframes().isEmpty()) {
						Keyframe cero = new Keyframe(12);
						Keyframe last = anim.getKeyframes().get(anim.getKeyframes().size()-1);
						List<Integer> posIndexes = posList.getSelectedIndexes();
						if(!posIndexes.isEmpty()) {
							for(int i : posIndexes) {
								cero.translations.put(Utils.getGunPartByName(model, 
										posList.getKeys().get(i).getKey()), 
										new float[] { 0, 0, 0 });
							}
						} else {
							for(Entry<GunModelPart, float[]> e : last.translations.entrySet()) {
								if(e.getValue()[0] != 0 || e.getValue()[1] != 0 || 
										e.getValue()[2] != 0) {
									cero.translations.put(e.getKey(), new float[] { 0, 0, 0 });
								}
								LogUtils.getLogger().info(Arrays.toString(e.getValue()));
							}
						}
						List<Integer> rotIndexes = rotList.getSelectedIndexes();
						if(!rotIndexes.isEmpty()) {
							for(int i : rotIndexes) {
								cero.rotations.put(Utils.getGunPartByName(model, 
										rotList.getKeys().get(i).getKey()), 
										new float[] { 0, 0, 0 });
							}
						} else {
							for(Entry<GunModelPart, float[]> e : last.rotations.entrySet()) {
								if(e.getValue()[0] != 0 || e.getValue()[1] != 0 || 
										e.getValue()[2] != 0) {
									cero.rotations.put(e.getKey(), new float[] { 0, 0, 0 });
								}
							}
						}
						anim.getKeyframes().add(cero);
						Utils.updateKeyframesFromAnimation(model.getAnimation());
						keyframeLine.update(model.getAnimation());
					} else {
						Keyframe cero = new Keyframe(12);
						for(GunModelPart part : model.getGunParts()) {
							cero.translations.put(part, new float[] { 0, 0, 0 });
							cero.rotations.put(part, new float[] { 0, 0, 0 });
						}
						anim.getKeyframes().add(cero);
						Utils.updateKeyframesFromAnimation(model.getAnimation());
						keyframeLine.update(model.getAnimation());
					}
				} else {
					Keyframe kf = getSelectedKeyframe();
					List<Integer> parts = gunPartList.getSelectedIndexes();
					if(!parts.isEmpty()) {
						for(int index : parts) {
							GunModelPart part = model.getGunParts().get(index);
							if (!booleanCycles.get(0).getValue()) {
								kf.translations.put(part, new float[] { 0, 0, 0 });
								posList.addKey(new Key(font, part.getName()));
							} else {
								kf.rotations.put(part, new float[] { 0, 0, 0 });
								rotList.addKey(new Key(font, part.getName()));
							}
						}
					}
				}
			}
		}));
		
		// Initializing widgets
		for (Button b : buttons) {
			addRenderableWidget(b);
		}

		for (EditBox e : edits) {
			addRenderableWidget(e);
		}

		for (OptionsList b : options) {
			addRenderableWidget(b);
		}
		for (CycleButton<Boolean> e : booleanCycles) {
			addRenderableWidget(e);
		}
		for (CycleButton<Integer> e : integerCycles) {
			addRenderableWidget(e);
		}

		if (model.getAnimation() != null) {
			keyframeLine.setAnimDur(model.getAnimation().getDuration());
			keyframeLine.setKeyframes(model.getAnimation().getKeyframes(), 
					model.getAnimation().getDuration());
		}
		init = true;
		// }
	}

	@Override
	public void render(PoseStack matrix, int x, int y, float p_96565_) {
		// Field Indicators
		this.font.drawShadow(matrix, "x: ", (float) 10 + (-AnimationScreen.this.font.width("x: ") / 2), (float) (4),
				16777215, true);
		this.font.drawShadow(matrix, "y: ", (float) 10 + (-AnimationScreen.this.font.width("y: ") / 2), (float) (24),
				16777215, true);
		this.font.drawShadow(matrix, "z: ", (float) 10 + (-AnimationScreen.this.font.width("z: ") / 2), (float) (44),
				16777215, true);
		this.font.drawShadow(matrix, "rx: ", (float) 10 + (-AnimationScreen.this.font.width("rx: ") / 2), (float) (64),
				16777215, true);
		this.font.drawShadow(matrix, "ry: ", (float) 10 + (-AnimationScreen.this.font.width("ry: ") / 2), (float) (84),
				16777215, true);
		this.font.drawShadow(matrix, "rz: ", (float) 10 + (-AnimationScreen.this.font.width("rz: ") / 2), (float) (104),
				16777215, true);
		this.font.drawShadow(matrix, "d: ", (float) 10 + (-AnimationScreen.this.font.width("d: ") / 2), (float) (124),
				16777215, true);
		this.font.drawShadow(matrix, "tms: ", (float) 10 + (-AnimationScreen.this.font.width("st: ") / 2), (float) (144),
				16777215, true);
		this.font.drawShadow(matrix, "s: ", (float) 10 + (-AnimationScreen.this.font.width("st: ") / 2), (float) (164),
				16777215, true);
		this.font.drawShadow(matrix, "es: ", (float) 10 + (-AnimationScreen.this.font.width("es: ") / 2), (float) (184),
				16777215, true);
		if(model.getAnimation() != null) {
			this.font.drawShadow(matrix, "Animation Name: " + model.getAnimation().getName(),
					(float) 226
							+ (-AnimationScreen.this.font.width("Animation Name: " + model.getAnimation().getName()) / 2),
					(float) (192), 16777215, true);
		}
		this.font.drawShadow(matrix, "Update Parts: " + model.getAnimator().shouldUpdateParts(),
				(float) 14
						+ (-AnimationScreen.this.font.width("Update Parts: " + model.getAnimator().shouldUpdateParts()) / 2),
				(float) (208), 16777215, true);
		this.font.drawShadow(matrix, "Current: " + current,
				(float) 28
						+ (-AnimationScreen.this.font.width("Current: " + current) / 2),
				(float) (222), 16777215, true);
		
		renderWidget(matrix, 100, 14, 0, 66, 200, 20, 100, 20);
		renderWidget(matrix, 100, 51, 0, 46, 200, 20, 100, -14);

		super.render(matrix, x, y, p_96565_);

		// Gun Parts Selection Rendering
		gunPartList.render(matrix, x, y, p_96565_);
		posList.render(matrix, x, y, p_96565_);
		rotList.render(matrix, x, y, p_96565_);

		keyframeLine.render(matrix, x, y, p_96565_);
		minX = 100;
		maxX = 400;
		deltaX = maxX - minX;
	}

	@Override
	public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
		keyframeLine.onClick((int) p_94695_, (int) p_94696_);
		posList.onClick((int) p_94695_, (int) p_94696_, ctrl);
		rotList.onClick((int) p_94695_, (int) p_94696_, ctrl);
		gunPartList.onClick((int) p_94695_, (int) p_94696_, ctrl);
		return super.mouseClicked(p_94695_, p_94696_, p_94697_);
	}

	@Override
	public void tick() {
		super.tick();
		this.posList.tick();
		this.rotList.tick();
		gunPartList.tick();
		/*if(model.getAnimation() != null) {
			if(keyframeLine.getSelected() != -1) {
				Keyframe kf = model.getAnimation().getKeyframes()
						.get(keyframeLine.getSelected());
				for(Entry<String, float[]> val : kf.pos.entrySet()) {
					LogUtils.getLogger().info("Key: " + val.getKey() + " val: " + 
							Arrays.toString(val.getValue()));
				}
			}
		}*/
	}

	public Keyframe getSelectedKeyframe() {
		return model.getAnimator().getAnimation().getKeyframes().get(
				keyframeLine.getSelected() > 
				model.getAnimator().getAnimation().getKeyframes().size()-1 ? 
					model.getAnimator().getAnimation().getKeyframes().size()-1 : 
					keyframeLine.getSelected());
	}
	
	@Override
	public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
		posList.check((int) p_94699_, (int) p_94700_);
		rotList.check((int) p_94699_, (int) p_94700_);
		gunPartList.check((int) p_94699_, (int) p_94700_);
		keyframeLine.check((int) p_94699_, (int) p_94700_);
		return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
	}

	@Override
	public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
		return super.mouseReleased(p_94722_, p_94723_, p_94724_);
	}

	@Override
	public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
		posList.onScroll((float) (p_94686_ * (-p_94688_)));
		rotList.onScroll((float) (p_94686_ * (-p_94688_)));
		gunPartList.onScroll((float) (p_94686_ * (-p_94688_)));
		return super.mouseScrolled(p_94686_, p_94687_, p_94688_);
	}

	@Override
	public boolean keyPressed(int key, int p_96553_, int p_96554_) {
		keys[key] = true;
		System.out.println(key);
		if (model.getAnimation() != null) {
			for(EditBox edit : edits) {
				if(edit.isFocused()) {
					return super.keyPressed(key, p_96553_, p_96554_);
				}
			}
			if (key == 129) {
				ctrl = true;
			}
			try {
			// Ctrl + c
			if (keys[341] && keys[67]) {
				if (keyframeLine.getSelected() != -1) {
					if (booleanCycles.get(0).getValue()) {
						kf = getSelectedKeyframe();
					} else {
						transforms.clear();
						if (booleanCycles.get(0).getValue()) {
							List<Integer> indices = posList.getSelectedIndexes();
							for (int i : indices) {
								GunModelPart part = getPart(posList.getKeys().get(i).getKey());
								transforms.add(new Pair<GunModelPart, float[]>(part,
										getSelectedKeyframe().translations.get(part).clone()));
							}
						} else {
							List<Integer> indices = rotList.getSelectedIndexes();
							for (int i : indices) {
								GunModelPart part = getPart(rotList.getKeys().get(i).getKey());
								transforms.add(new Pair<GunModelPart, float[]>(part,
										getSelectedKeyframe().rotations.get(part).clone()));
							}
						}
					}
				}
			}
			// Ctrl + v
			if (keys[341] && keys[86]) {
				if (model.getAnimation() != null) {
					System.out.println("here6");
					if (keyframeLine.getSelected() != -1) {
						System.out.println("here5");
						if (booleanCycles.get(0).getValue()) {
							System.out.println("here4");
							if (kf != null) {
								model.getAnimation().getKeyframes()
									.get(keyframeLine.getSelected())
									.copyTransformFrom(kf);
							}
						} else {
							System.out.println("here3");
							if (!transforms.isEmpty()) {
								Keyframe kf = getSelectedKeyframe();
								System.out.println("here2");
								for (Pair<GunModelPart, float[]> pair : transforms) {
									if (booleanCycles.get(0).getValue()) {
										kf.translations.put(pair.getLeft(), pair.getRight());
										Key[] newPosKeys = new Key[kf.translations.size()];
										int j = 0;
										for (GunModelPart s : kf.translations.keySet()) {
											newPosKeys[j] = new Key(font, s.getName());
											j++;
										}
										posList.setKeys(newPosKeys);
										System.out.println("here1");
									} else {
										kf.rotations.put(pair.getLeft(), pair.getRight());
										Key[] newRotKeys = new Key[kf.rotations.size()];
										int j = 0;
										for (GunModelPart s : kf.rotations.keySet()) {
											newRotKeys[j] = new Key(font, s.getName());
											j++;
										}
										rotList.setKeys(newRotKeys);
										System.out.println("here0");
									}
								}
							}
						}
					}
				}
				System.out.println("Pressing");
			}
	
			// Ctrl + d
			if (keys[341] && keys[68]) {
				if (keyframeLine.getSelected() != -1) {
					Keyframe kfselCopy = getSelectedKeyframe().copy();
					Utils.insertInto(keyframeLine.getSelected(), 
							model.getAnimation().getKeyframes(), kfselCopy);
					Utils.updateKeyframesFromAnimation(model.getAnimation());
					keyframeLine.update(model.getAnimation());
				}
			}
			
			// Suprimir
			if(keys[261]) {
				if(keyframeLine.getSelected() != -1) {
					model.getAnimation().getKeyframes().remove(keyframeLine.getSelected());
					Utils.updateKeyframesFromAnimation(model.getAnimation());
					keyframeLine.update(model.getAnimation());
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(keys[341] && keys[162]) {
			for(GunModelPart part : model.getGunParts()) {
				LogUtils.getLogger().info("Name: " + part.getName() + " dtransform: "
						+ part.getDTransform().toString());
			}
		}
		return super.keyPressed(key, p_96553_, p_96554_);
	}

	@Override
	public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
		keys[p_94715_] = false;
		if (p_94715_ == 341) {
			ctrl = false;
		}
		return super.keyReleased(p_94715_, p_94716_, p_94717_);
	}
	
	public GunModelPart getPart(String key) {
		for (GunModelPart p : model.getGunParts()) {
			if (p.getName().equals(key)) {
				return p;
			}
		}
		return null;
	}

	public Transform getTransform() {
		Key key = gunPartList.getSelectedKey();
		if (key != null) {
			return ((GunPartKey) gunPartList.getSelectedKey()).getPart().getTransform();
		}
		return Transform.EMPTY;
	}

	public void setVecVal(boolean rot, int i, float v) {
		Keyframe kf = model.getAnimation().getKeyframes().get(keyframeLine.getSelected());
		if (!rot) {
			LogUtils.getLogger().info("SA");
			if (!kf.translations.isEmpty()) {
				LogUtils.getLogger().info("Not Empty");
				Key[] keys = posList.getSelectedKeys();
				for (Key key : keys) {
					kf.translations.get(Utils.getGunPartByName(model, key.getKey()))[i] = v;
					LogUtils.getLogger().info("key: " + key.getKey());
				}
			}
		} else {
			if (!kf.rotations.isEmpty()) {
				Key[] keys = rotList.getSelectedKeys();
				for (Key key : keys) {
					kf.rotations.get(Utils.getGunPartByName(model, key.getKey()))[i] = v;
				}
			}
		}
	}

	public void renderWidget(PoseStack matrix, int x, int y, int i, int j, int w, int h, int w2, int h2) {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, WIDGETS);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
		bufferbuilder.vertex((i) + x, ((j + h) + y) + h2, 0).uv(0, (j + h) / 256f).color(1.0F, 1.0F, 1.0F, 1.0f)
				.endVertex();
		bufferbuilder.vertex(((i + w) + x) + w2, ((j + h) + y) + h2, 0).uv((i + w) / 256f, (j + h) / 256f)
				.color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
		bufferbuilder.vertex(((i + w) + x) + w2, (j) + y, 0).uv((i + w) / 256f, (j) / 256f)
				.color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
		bufferbuilder.vertex((i) + x, (j) + y, 0).uv(0, (j) / 256f).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
		tesselator.end();
		
		/*RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, WIDGETS);
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		Matrix4f last = matrix.last().pose();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(last, (i) + x, ((j + h) + y) + h2, 0).uv(0, (j + h) / 256f).color(1.0F, 1.0F, 1.0F, 1.0f)
				.endVertex();
		bufferbuilder.vertex(last, ((i + w) + x) + w2, ((j + h) + y) + h2, 0).uv((i + w) / 256f, (j + h) / 256f)
				.color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
		bufferbuilder.vertex(last, ((i + w) + x) + w2, (j) + y, 0).uv((i + w) / 256f, (j) / 256f)
				.color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
		bufferbuilder.vertex(last, (i) + x, (j) + y, 0).uv(0, (j) / 256f).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
		BufferUploader.draw(bufferbuilder.end());*/
	}

	public void initKeyframes() {
		if (model.getAnimation() != null) {
			keyframeLine.setAnimDur(model.getAnimation().getDuration());
			keyframeLine.setKeyframes(model.getAnimation().getKeyframes(), 
					model.getAnimation().getDuration());
		}
	}

	public Font getFont() {
		return font;
	}

	public GunModel getGunModel() {
		return model;
	}
	
	public int getCurrent() {
		return current;
	}

	public KeyframeLineWidget getKeyframeLineWidget() {
		return keyframeLine;
	}

	public JGSelectionList getGunPartList() {
		return gunPartList;
	}

	public JGSelectionList getPosList() {
		return posList;
	}

	public JGSelectionList getRotList() {
		return rotList;
	}

	public List<EditBox> getEditBoxes() {
		return edits;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public List<CycleButton<Integer>> getIntCycles() {
		return integerCycles;
	}

	public List<CycleButton<Boolean>> getBooleanCycles() {
		return booleanCycles;
	}

	private CycleButton<Integer> buildIntCycle(Function<Integer, Component> f, int initVal, 
			int x, int y, int w, int h,
			Component t, OnValueChange<Integer> ch, Integer... values) {
		CycleButton<Integer> cycle = CycleButton.builder(f).withValues(values).withInitialValue(initVal).create(x, y, w,
				h, t, ch);
		return cycle;
	}

	private CycleButton<Boolean> buildBooleanCycle(Function<Boolean, Component> f, 
			boolean initVal, int x, int y, int w,
			int h, Component t, OnValueChange<Boolean> ch) {
		CycleButton<Boolean> cycle = CycleButton.builder(f).withValues(true, false).withInitialValue(initVal).create(x,
				y, w, h, t, ch);
		return cycle;
	}
	
	public class Pair<T, R> {
		protected T t;
		protected R r;

		public Pair(T t, R r) {
			this.t = t;
			this.r = r;
		}

		public T getLeft() {
			return t;
		}

		public R getRight() {
			return r;
		}

	}

}
