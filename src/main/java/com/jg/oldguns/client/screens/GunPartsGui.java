package com.jg.oldguns.client.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.ItemsReg;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.render.RenderHelper;
import com.jg.oldguns.client.screens.widgets.Button;
import com.jg.oldguns.client.screens.widgets.GunSlot;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.containers.GunPartsContainer;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.items.Aks74u;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GunPartsGui extends AbstractContainerScreen<GunPartsContainer> {

	private Map<String, Boolean> canCraft;
	
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

	private ItemStack WOOD;
	private ItemStack IRON_INGOT;
	private ItemStack STEEL_INGOT;
	
	private Predicate<ResourceLocation> pred = (s) -> {
    	return s.equals(new ResourceLocation("minecraft:planks"));
    };
    
	private List<Item> WOOD_PLANKS;

	public static ResourceLocation GUNPARTS_GUI = 
			Utils.loc("textures/gui/container/gunparts_table.png");

	public GunPartsGui(GunPartsContainer p_i51105_1_, Inventory p_i51105_2_, Component c) {
		super(p_i51105_1_, p_i51105_2_, new TranslatableComponent("gui.oldguns.gun_parts_gui"));
		this.slots = new ArrayList<GunSlot>();
		this.partSlots = new ArrayList<GunSlot>();
		this.gunLevel = true;
		this.pi = p_i51105_2_;
		WOOD_PLANKS = getWoodPlanksTags();
		WOOD = new ItemStack(Items.OAK_PLANKS);
		IRON_INGOT = new ItemStack(Items.IRON_INGOT);
		STEEL_INGOT = new ItemStack(ItemRegistries.SteelIngot.get());
		
		canCraft = new HashMap<>();
		canCraft.put(((GunItem)ItemRegistries.AKS74U.get()).getGunId(), 
				Config.SERVER.craftAks74u.get());
		canCraft.put(((GunItem)ItemRegistries.COLT1911.get()).getGunId(), 
				Config.SERVER.craftColt1911.get());
		canCraft.put(((GunItem)ItemRegistries.GALIL.get()).getGunId(), 
				Config.SERVER.craftGalil.get());
		canCraft.put(((GunItem)ItemRegistries.ITHACAMODEL37.get()).getGunId(), 
				Config.SERVER.craftIthacaModel37.get());
		canCraft.put(((GunItem)ItemRegistries.KAR98K.get()).getGunId(), 
				Config.SERVER.craftKar98k.get());
		canCraft.put(((GunItem)ItemRegistries.MP40.get()).getGunId(), 
				Config.SERVER.craftMp40.get());
		canCraft.put(((GunItem)ItemRegistries.SCORPION.get()).getGunId(), 
				Config.SERVER.craftScorpion.get());
		canCraft.put(((GunItem)ItemRegistries.STEN.get()).getGunId(), 
				Config.SERVER.craftSten.get());
		canCraft.put(((GunItem)ItemRegistries.THOMPSON.get()).getGunId(), 
				Config.SERVER.craftThompson.get());
		canCraft.put(((GunItem)ItemRegistries.WINCHESTER.get()).getGunId(), 
				Config.SERVER.craftWinchester.get());
	}

	@Override
	protected void init() {
		super.init();

		iron = Utils.getModel(IRON_INGOT.getItem());
		steel = Utils.getModel(STEEL_INGOT.getItem());
		wood = Utils.getModel(WOOD.getItem());

		int i = leftPos;
		int j = topPos;
		
		LogUtils.getLogger().info("i: " + i);
		LogUtils.getLogger().info("j: " + j);
		
		int size = ItemsReg.INSTANCE.getGuns().size();

		if(slots.size() == 0) {
			
			int cols = 6;
			int rows = 3;
			int cap = cols * rows;
			
			gunTabs = (int) Math.floor((size / cap) + 1);
	
			// Cols = 7 Rows = 3
	
			int t = 0;
			
			for (int g = 0; g < size; g++) {
	
				int wg = g - (t * cap);
	
				GunItem gunitem = (GunItem) ItemsReg.INSTANCE.getGun(g);
				
				slots.add(new GunSlot(this, t, (i + 8) + (wg % cols) * 18, 
						(j + 26) + (int) Math.floor(wg / cols) * 18, 178,
						0, 18, 18, 18, GUNPARTS_GUI, gunitem) {
					@Override
					public void onPress() {
						super.onPress();
						
						if (!ItemsReg.INSTANCE.getGunParts().containsKey(gunitem.getGunId()))
							return;
	
						gunStack = new ItemStack(this.gun.getItem());
						GunPartsGui.this.gunModel = Utils.getModel(this.gun.getItem());
	
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
	
							partSlots.add(new GunSlot(GunPartsGui.this, t2, 
									(i + 8) + (wg2 % cols) * 18,
									(j + 26) + (int) Math.floor(wg2 / cols) * 18, 
									178, 0, 18, 18, 18, GUNPARTS_GUI,
									matchingparts.get(g2)) {
								public void onPress() {
	
									GunPartsGui.this.hasMetal = false;
									GunPartsGui.this.hasWood = false;
	
									partStack = new ItemStack(this.gun.getItem());
									GunPartsGui.this.partModel = Utils.getModel(this.gun.getItem());
	
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
		
		} else {
			// (i + 8) + (wg % cols) * 18, (j + 26) + (int) Math.floor(wg / cols) * 18
			int cols = 6;
			int rows = 3;
			int cap = cols * rows;
			int t = 0;
			
			for(int g = 0; g < size; g++) {
				int wg = g - (t * cap);
				
				GunItem gunitem = (GunItem) ItemsReg.INSTANCE.getGun(g);
				
				slots.get(g).x = (i + 8) + (wg % cols) * 18;
				slots.get(g).y = (j + 26) + (int) Math.floor(wg / cols) * 18;
				
				// Part slots
				
				if(!partSlots.isEmpty() && gunitem != null) {
					int t2 = 0;
					
					List<GunPart> matchingparts = new ArrayList<GunPart>();
					if(!ItemsReg.INSTANCE.getGunParts()
							.containsKey(gunitem.getGunId())) continue;
					for (Supplier<? extends Item> s : ItemsReg.INSTANCE.getGunParts()
							.get(gunitem.getGunId())) {
						GunPart part = (GunPart) s.get();

						if (part.getGunId().equals(gunitem.getGunId())) {
							matchingparts.add(part);
						}
					}
					
					for (int g2 = 0; g2 < matchingparts.size()-1; g2++) {
						
						partTabs = (int) Math.floor((matchingparts.size() / cap) + 1);

						int wg2 = g2 - (t2 * cap);
						
						partSlots.get(g2).x = (i + 8) + (wg2 % cols) * 18;
						partSlots.get(g2).y = (j + 26) + (int) Math.floor(wg2 / cols) * 18;
						
						if (wg2 > cap - 2) {
							t2++;
						}
					}
				}
				
				// Tabs
				
				if (wg > cap - 2) {
					t++;
				}
			}
			
		}
		
		addRenderableWidget(new Button(this, i + 61, j + 7, 178, 60, 26, 18, 26, GUNPARTS_GUI) {
			@Override
			public void onPress() {
				super.onPress();

				LogUtils.getLogger().info("gunLevel: " + gunLevel);
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
					for (GunSlot g : slots) {
						g.updateTab(gunTab);
					}
				}

			}
		});

		addRenderableWidget(new Button(this, i + 91, j + 7, 178, 40, 26, 18, 26, GUNPARTS_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				LogUtils.getLogger().info("gunLevel: " + gunLevel);
				if (gunLevel) {
					gunTab = (gunTab + 1) % gunTabs;
					LogUtils.getLogger().info("gunTabs: " + gunTabs + " gunTab: " + gunTab);
					for (GunSlot g : slots) {
						g.updateTab(gunTab);
					}
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

					if(canCraft.get(part.getGunId())) return;
					
					LogUtils.getLogger().info(Config.SERVER.craftAks74u.get() + "");
					
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
						OldGuns.channel.sendToServer(new AddItemMessage(Utils.getR(part).toString(), 1));
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

		if (woodCount > 0) {
			System.out.println("Has wood");
		}

		if (part.getWood() == 0) {
			hasWood = true;
		} else if (woodCount >= part.getWood()) {
			hasWood = true;
		} else {
			hasWood = false;
		}

		int metalCount = part.isSteel() ? ServerUtils.getTotalItemAmout(pi, ItemRegistries.SteelIngot.get())
				: ServerUtils.getTotalItemAmout(pi, Items.IRON_INGOT);

		if (part.getMetal() == 0) {
			hasMetal = true;
		} else if (metalCount >= part.getMetal()) {
			hasMetal = true;
		} else {
			hasMetal = false;
		}

	}

	@Override
	protected void renderBg(PoseStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.enableBlend();
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GUNPARTS_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

		for (GunSlot gs : partSlots) {
			gs.render(matrixStack, p_230450_3_, p_230450_4_, p_230450_2_);
		}

		if (gunStack != null && gunModel != null) {
			RenderHelper.renderGuiItem(gunStack, i + 141, j + 14, gunModel);

			if (partStack != null && partModel != null) {
				RenderHelper.renderGuiItem(partStack, i + 138, j + 39, partModel);

				if (part != null) {

					RenderHelper.renderGuiItem(WOOD, (i + 12), (j + 8), wood);
					Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, WOOD, (i + 12),
							(j + 8), String.valueOf(woodCount));

					RenderSystem.setShader(GameRenderer::getPositionTexShader);
					RenderSystem.setShaderTexture(0, GUNPARTS_GUI);

					if (hasWood) {
						this.blit(matrixStack, i + 8, j + 8, 178, 80, 26, 16);
					} else {
						this.blit(matrixStack, i + 8, j + 8, 204, 80, 26, 16);
					}
					if (part.isSteel()) {
						RenderHelper.renderGuiItem(STEEL_INGOT, i + 40, j + 8, steel);
						Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, STEEL_INGOT,
								i + 40, j + 8, String.valueOf(metalCount));
					} else {
						RenderHelper.renderGuiItem(IRON_INGOT, i + 40, j + 8, iron);
						Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, IRON_INGOT,
								i + 40, j + 8, String.valueOf(metalCount));
					}
					RenderSystem.setShader(GameRenderer::getPositionTexShader);
					RenderSystem.setShaderTexture(0, GUNPARTS_GUI);
					if (hasMetal) {
						this.blit(matrixStack, i + 34, j + 8, 178, 80, 26, 16);
					} else {
						this.blit(matrixStack, i + 34, j + 8, 204, 80, 26, 16);
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

	public List<Item> getWoodPlanksTags(){
		List<Item> list = new ArrayList<>();
		Pair<TagKey<Item>, Named<Item>> pair = Registry.ITEM.getTags().filter((p_205410_) -> {
	         return pred.test(p_205410_.getFirst().location());
	    }).toList().get(0);
		for(int i2 = 0; i2 < pair.getSecond().size(); i2++) {
			String s = pair.getSecond().get(i2).toString();
			String st1 = s.split("/")[1];
			String st2 = st1.split("]")[0];
			st2 = st2.replace(" ", "");
			System.out.println(st2);
			list.add(Registry.ITEM.get(new ResourceLocation(st2)));
		}
		return list;
	}
	
}
