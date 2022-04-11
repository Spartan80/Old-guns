package com.jg.oldguns.client.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ItemsReg;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.screens.widgets.Button;
import com.jg.oldguns.client.screens.widgets.GunSlot;
import com.jg.oldguns.containers.GunPartsContainer;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GunPartsGui extends AbstractContainerScreen<GunPartsContainer> {

	private int gunTab, gunTabs, partTab, partTabs;

	private boolean gunLevel;

	private List<GunSlot> slots;
	private List<GunSlot> partSlots;

	private ItemStack gunStack;
	private BakedModel gunModel;

	private GunPart part;
	private ItemStack partStack;
	private BakedModel partModel;
	
	private BakedModel iron;
	private BakedModel steel;
	private BakedModel wood;

	int woodCount, metalCount;
	int woodSlot, metalSlot;

	boolean hasWood, hasMetal;

	private Inventory pi;

	private List<Item> WOOD_PLANKS = ItemTags.getAllTags().getAllTags().get(new ResourceLocation("minecraft:planks"))
			.getValues();

	public static ResourceLocation GUNPARTS_GUI = new ResourceLocation(
			Util.loc("textures/gui/container/gunparts_table.png"));

	public GunPartsGui(GunPartsContainer p_i51105_1_, Inventory p_i51105_2_, Component c) {
		super(p_i51105_1_, p_i51105_2_, new TranslatableComponent("gui.oldguns.gun_parts_gui"));

		this.slots = new ArrayList<GunSlot>();
		this.partSlots = new ArrayList<GunSlot>();
		this.gunLevel = true;
		this.pi = p_i51105_2_;
	}

	@Override
	protected void init() {
		super.init();

		iron = ModelHandler.INSTANCE.getModel(Util.IRON_INGOT.getItem().getRegistryName());
		steel = ModelHandler.INSTANCE.getModel(Util.STEEL_INGOT.getItem().getRegistryName());
		wood = ModelHandler.INSTANCE.getModel(Util.WOOD.getItem().getRegistryName());
		
		int i = leftPos;
		int j = topPos;

		int size = ItemsReg.INSTANCE.getGuns().size();

		int cols = 6;
		int rows = 3;
		int cap = cols * rows;

		gunTabs = (int) Math.floor((size / cap) + 1);

		// Cols = 7 Rows = 3

		int t = 0;

		for (int g = 0; g < size; g++) {

			int wg = g - (t * cap);

			ItemGun gunitem = (ItemGun) ItemsReg.INSTANCE.getGun(g);

			slots.add(new GunSlot(this, t, (i + 8) + (wg % cols) * 18, (j + 26) + (int) Math.floor(wg / cols) * 18, 178, 0,
					18, 18, 18, GUNPARTS_GUI, gunitem) {
				@Override
				public void onPress() {
					super.onPress();

					if (!ItemsReg.INSTANCE.getGunParts().containsKey(gunitem.getGunId()))
						return;

					gunStack = new ItemStack(this.gun.getItem());
					GunPartsGui.this.gunModel = ModelHandler.INSTANCE
							.getModel(this.gun.getItem().getRegistryName().toString());

					updateTab(gunTab);

					gunLevel = false;

					int t2 = 0;

					List<GunPart> matchingparts = new ArrayList<GunPart>();

					for (Supplier<? extends Item> s : ItemsReg.INSTANCE.getGunParts().get(gunitem.getGunId())) {
						GunPart part = (GunPart) s.get();

						if (part.getGunId().equals(gunitem.getGunId())) {
							matchingparts.add(part);
						}
					}

					for (int g2 = 0; g2 < matchingparts.size(); g2++) {

						partTabs = (int) Math.floor((matchingparts.size() / cap) + 1);

						int wg2 = g2 - (t2 * cap);

						partSlots.add(new GunSlot(GunPartsGui.this, t2, (i + 8) + (wg2 % cols) * 18,
								(j + 26) + (int) Math.floor(wg2 / cols) * 18, 178, 0, 18, 18, 18, GUNPARTS_GUI,
								matchingparts.get(g2)) {
							public void onPress() {

								GunPartsGui.this.hasMetal = false;
								GunPartsGui.this.hasWood = false;
								
								partStack = new ItemStack(this.gun.getItem());
								GunPartsGui.this.partModel = ModelHandler.INSTANCE
										.getModel(this.gun.getItem().getRegistryName().toString());

								GunPartsGui.this.part = (GunPart) this.gun.getItem();

								GunPartsGui.this.woodCount = part.getWood();
								GunPartsGui.this.metalCount = part.getMetal();

								updateTab(partTab);

								if (part != null) {
									searchMaterials();
								}
							};
						});

						if (wg2 > cap - 2) {
							t2++;
						}
					}

					for (GunSlot s : slots) {
						s.visible = false;
						s.active = false;
					}

				}
			});

			if (wg > cap - 2) {
				t++;
			}
		}

		addRenderableWidget(new Button(this, i + 61, j + 7, 178, 60, 26, 18, 26, GUNPARTS_GUI) {
			@Override
			public void onPress() {
				super.onPress();

				if (!gunLevel) {
					if (partTab == 0) {

						for (GunSlot s : slots) {
							s.visible = true;
							s.active = true;
						}

						partSlots.clear();

						gunStack = null;
						gunModel = null;
						partStack = null;
						partModel = null;
						part = null;

						gunLevel = true;
					} else {
						partTab = (partTab - 1 + partTabs) % partTabs;
					}
				} else {
					gunTab = (gunTab - 1 + gunTabs) % gunTabs;
				}

			}
		});

		addRenderableWidget(new Button(this, i + 91, j + 7, 178, 40, 26, 18, 26, GUNPARTS_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				if (gunLevel) {
					gunTab = (gunTab + 1) % gunTabs;
				} else {
					partTab = (partTab + 1) % partTabs;
				}
			}
		});

		addRenderableWidget(new Button(this, i + 133, j + 63, 178, 20, 26, 18, 26, GUNPARTS_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				/*
				 * boolean wood = false; boolean metal = false;
				 * 
				 * boolean ws = false; boolean ms = false;
				 * 
				 * if(part != null) { if(part.getWood() != 0) { if(woodSlot != -1) { wood =
				 * true; ws = true; } }else { wood = true; } if(part.getMetal() != 0) {
				 * if(metalSlot != -1) { metal = true; ms = true; } }else { metal = true; } }
				 * 
				 * if(wood && metal) { if(ws) { ReloadHandler.removeItem(woodSlot,
				 * part.getWood()); } if(ms) { ReloadHandler.removeItem(metalSlot,
				 * part.getMetal()); } OldGuns.channel.sendToServer(new
				 * AddItemMessage(part.getRegistryName().toString(), 1));
				 * SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get()); }
				 */
				if (part != null) {

					searchMaterials();

					if (hasWood && hasMetal) {
						if (part.getWood() != 0) {
							ServerUtils.removeItemInDifIndexes(pi, WOOD_PLANKS, part.getWood());
							System.out.println("Wood removed");
						}
						if (part.getMetal() != 0) {
							ServerUtils.removeItemInDifIndexes(pi,
									part.isSteel() ? ItemRegistries.SteelIngot.get() : Items.IRON_INGOT,
									part.getMetal());
							System.out.println("Metal removed");
						}
						OldGuns.channel.sendToServer(new AddItemMessage(part.getRegistryName().toString(), 1));
						SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
						System.out.println("Crafted");
					}

				}
			}
		});

		for (GunSlot g : slots) {
			addRenderableWidget(g);
		}

	}

	public void searchMaterials() {
		
		int woodCount = ServerUtils.getTotalItemAmout(pi, WOOD_PLANKS);

		if(woodCount > 0) {
			System.out.println("Has wood");
		}
		
		if (part.getWood() == 0) {
			hasWood = true;
		} else if (woodCount >= part.getWood()) {
			hasWood = true;
		}else{
			hasWood = false;
		}
		
		int metalCount = part.isSteel() ? ServerUtils.getTotalItemAmout(pi, ItemRegistries.SteelIngot.get())
				: ServerUtils.getTotalItemAmout(pi, Items.IRON_INGOT);

		if (part.getMetal() == 0) {
			hasMetal = true;
		} else if (metalCount >= part.getMetal()) {
			hasMetal = true;
		}else {
			hasMetal = false;
		}

	}

	@Override
	protected void renderBg(PoseStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		Minecraft.getInstance().textureManager.bindForSetup(GUNPARTS_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

		for (GunSlot gs : partSlots) {
			gs.render(matrixStack, p_230450_3_, p_230450_4_, p_230450_2_);
		}

		if (gunStack != null && gunModel != null) {
			RenderUtil.renderGuiItem(gunStack, i + 141, j + 14, gunModel);

			if (partStack != null && partModel != null) {
				RenderUtil.renderGuiItem(partStack, i + 138, j + 39, partModel);

				if (part != null) {

					RenderUtil.renderGuiItem(Util.WOOD, (i + 12), (j + 8),
							wood);
					Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, Util.WOOD, (i + 12),
							(j + 8), String.valueOf(woodCount));
					if (hasWood) {
						Minecraft.getInstance().textureManager.bindForSetup(GUNPARTS_GUI);
						this.blit(matrixStack, i + 8, j + 8, 178, 80, 26, 16);
					}
					if (part.isSteel()) {
						RenderUtil.renderGuiItem(Util.STEEL_INGOT, i + 40, j + 8,
								steel);
						Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, Util.STEEL_INGOT,
								i + 40, j + 8, String.valueOf(metalCount));
					} else {
						RenderUtil.renderGuiItem(Util.IRON_INGOT, i + 40, j + 8, iron
								);
						Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, Util.STEEL_INGOT,
								i + 40, j + 8, String.valueOf(metalCount));
					}
					if (hasMetal) {
						Minecraft.getInstance().textureManager.bindForSetup(GUNPARTS_GUI);
						this.blit(matrixStack, i + 34, j + 8, 178, 80, 26, 16);
					}
				}

			}

		}

	}

	@Override
	public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
		for (GunSlot gs : partSlots) {
			gs.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
		}
		return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int x, int y) {
		this.renderTooltip(matrixStack, x - leftPos, y - topPos);
	}

}
