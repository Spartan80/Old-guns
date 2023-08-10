package com.jg.oldguns.client.screens;

import java.util.ArrayList;
import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.ItemsReg;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.render.RenderHelper;
import com.jg.oldguns.client.screens.widgets.Button;
import com.jg.oldguns.client.screens.widgets.GunSlot;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.containers.AmmoCraftingTableContainer;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.guns.MagItem;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GunAmmoGui extends AbstractContainerScreen<AmmoCraftingTableContainer> {

	int tabs;
	int gunTab;
	int metal;
	int gunPowder;
	int amount;
	
	boolean isIron;
	boolean isAltPressed;

	BakedModel model;
	BakedModel bulletModel;

	MagItem mag;

	BulletItem bullet;

	ItemStack stack;
	ItemStack bulletStack;

	ArrayList<GunSlot> slots = new ArrayList<GunSlot>();
	ArrayList<Item> items = new ArrayList<Item>();

	Inventory pi;

	public static ResourceLocation GUNAMMO_GUI = 
			Utils.loc("textures/gui/container/ammo_crafting_table.png");

	public GunAmmoGui(AmmoCraftingTableContainer p_i51105_1_, Inventory p_i51105_2_, Component c) {
		super(p_i51105_1_, p_i51105_2_, new TranslatableComponent("gui.oldguns.gun_ammo_gui"));
		pi = p_i51105_2_;
		for (Supplier<? extends Item> item : ItemsReg.INSTANCE.getMags()) {
			items.add(item.get());
		}
		for (Supplier<? extends Item> item : ItemsReg.INSTANCE.getExtraItems()) {
			items.add(item.get());
		}
	}

	@Override
	protected void init() {
		super.init();

		int i = leftPos;
		int j = topPos;

		int size = items.size();

		if(slots.size() == 0) {
		
			int cols = 6;
			int rows = 3;
			int cap = cols * rows;
	
			tabs = (int) Math.floor((size / cap) + 1);
	
			int t = 0;
	
			LogUtils.getLogger().info("Size: " + size + " gunSlots size: " + slots.size());
			
			for (int g = 0; g < size; g++) {
	
				int wg = g - (t * cap);
	
				slots.add(new GunSlot(this, t, (i + 8) + (wg % cols) * 18, (j + 26) + (int) Math.floor(wg / cols) * 18, 178,
						0, 18, 18, 18, GUNAMMO_GUI, items.get(g)) {
					@Override
					public void onPress() {
						super.onPress();
						if (this.gun.getItem() instanceof MagItem) {
							for (Item item : items) {
								if (item instanceof BulletItem) {
									BulletItem b = (BulletItem) item;
									if (b.getSize().equals(((MagItem) this.gun.getItem()).getAcceptedSize())) {
										bulletModel = Utils.getModel(item);
										bulletStack = new ItemStack(item);
										int amount = 1;
										if(Utils.getR(bullet).toString().contains("small")) {
											amount = Config.SERVER.smallBulletCraftingResult.get();
										} else if(Utils.getR(bullet).toString().contains("medium")) {
											amount = Config.SERVER.mediumBulletCraftingResult.get();
										} else if(Utils.getR(bullet).toString().contains("big")) {
											amount = Config.SERVER.bigBulletCraftingResult.get();
										} else if(Utils.getR(bullet).toString().contains("shotgun")) {
											amount = Config.SERVER.shotgunBulletCraftingResult.get();
										}
										GunAmmoGui.this.amount = amount;
									}
								}
							}
							GunAmmoGui.this.stack = new ItemStack(this.gun.getItem());
							GunAmmoGui.this.mag = (MagItem) this.gun.getItem();
							GunAmmoGui.this.model = Utils.getModel(this.gun.getItem());
							searchMetal();
						} else {
							GunAmmoGui.this.bulletModel = Utils.getModel(this.gun.getItem());
							GunAmmoGui.this.bulletStack = new ItemStack(this.gun.getItem());
							GunAmmoGui.this.bullet = (BulletItem) bulletStack.getItem();
							GunAmmoGui.this.stack = null;
							GunAmmoGui.this.mag = null;
							GunAmmoGui.this.model = null;
							int amount = 1;
							if(Utils.getR(bullet).toString().contains("small")) {
								amount = Config.SERVER.smallBulletCraftingResult.get();
							} else if(Utils.getR(bullet).toString().contains("medium")) {
								amount = Config.SERVER.mediumBulletCraftingResult.get();
							} else if(Utils.getR(bullet).toString().contains("big")) {
								amount = Config.SERVER.bigBulletCraftingResult.get();
							} else if(Utils.getR(bullet).toString().contains("shotgun")) {
								amount = Config.SERVER.shotgunBulletCraftingResult.get();
							}
							GunAmmoGui.this.amount = amount;
							searchMetal();
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
				
				slots.get(g).x = (i + 8) + (wg % cols) * 18;
				slots.get(g).y = (j + 26) + (int) Math.floor(wg / cols) * 18;
				
				if (wg > cap - 2) {
					t++;
				}
			}
			
			
		}

		addRenderableWidget(new Button(this, i + 63, j + 7, 178, 60, 26, 18, 26, GUNAMMO_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				gunTab = (gunTab - 1 + tabs) % tabs;
				updateTab();
			}
		});

		addRenderableWidget(new Button(this, i + 91, j + 7, 178, 40, 26, 18, 26, GUNAMMO_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				gunTab = (gunTab + 1) % tabs;
				updateTab();
			}
		});

		addRenderableWidget(new Button(this, i + 133, j + 63, 178, 20, 26, 18, 26, GUNAMMO_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				searchMetal();
				if (mag != null) {
					if (metal >= mag.getMetal()) {
						if (!isAltPressed) {
							ServerUtils.removeItemInDifIndexes(pi,
									mag.isIron() ? Items.IRON_INGOT : ItemRegistries.SteelIngot.get(), mag.getMetal());
							ServerUtils.addItem(mag, 1);
							SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
						} else {
							int metals = metal / mag.getMetal();
							ServerUtils.removeItemInDifIndexes(pi,
									mag.isIron() ? Items.IRON_INGOT : ItemRegistries.SteelIngot.get(),
									mag.getMetal() * metals);
							ServerUtils.addItem(mag, metals);
							SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
						}

					}
				} else if (bullet != null) {
					LogUtils.getLogger().info("GunPowder needed: " + bullet.getGunPowder());
					if (metal >= bullet.getMetal() && gunPowder >= bullet.getGunPowder()) {
						if (!isAltPressed) {
							ServerUtils.removeItemInDifIndexes(pi,
									bullet.requiresIngots() ? Items.IRON_INGOT : Items.IRON_NUGGET, bullet.getMetal());
							ServerUtils.removeItemInDifIndexes(pi, Items.GUNPOWDER, bullet.getGunPowder());
							int amount = 1;
							if(Utils.getR(bullet).toString().contains("small")) {
								amount = Config.SERVER.smallBulletCraftingResult.get();
							} else if(Utils.getR(bullet).toString().contains("medium")) {
								amount = Config.SERVER.mediumBulletCraftingResult.get();
							} else if(Utils.getR(bullet).toString().contains("big")) {
								amount = Config.SERVER.bigBulletCraftingResult.get();
							} else if(Utils.getR(bullet).toString().contains("shotgun")) {
								amount = Config.SERVER.shotgunBulletCraftingResult.get();
							}
							ServerUtils.addItem(bullet, amount);
							SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
						} else {
							int metals = metal / bullet.getMetal();
							int amount = 1;
							if(Utils.getR(bullet).toString().contains("small")) {
								amount = Config.SERVER.smallBulletCraftingResult.get();
							} else if(Utils.getR(bullet).toString().contains("medium")) {
								amount = Config.SERVER.mediumBulletCraftingResult.get();
							} else if(Utils.getR(bullet).toString().contains("big")) {
								amount = Config.SERVER.bigBulletCraftingResult.get();
							} else if(Utils.getR(bullet).toString().contains("shotgun")) {
								amount = Config.SERVER.shotgunBulletCraftingResult.get();
							}
							ServerUtils.removeItemInDifIndexes(pi,
									bullet.requiresIngots() ? Items.IRON_INGOT : Items.IRON_NUGGET,
									bullet.getMetal() * metals * amount);
							ServerUtils.removeItemInDifIndexes(pi, Items.GUNPOWDER, bullet.getGunPowder() * metals);
							ServerUtils.addItem(bullet, metals);
							SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
						}
					}
				}
			}
		});

		for (GunSlot g : slots) {
			addRenderableWidget(g);
		}

	}

	private void searchMetal() {
		if (stack != null) {
			metal = mag.isIron() ? ServerUtils.getTotalItemAmout(pi, Items.IRON_INGOT)
					: ServerUtils.getTotalItemAmout(pi, ItemRegistries.SteelIngot.get());
		} else if (bulletStack != null) {
			metal = bullet.requiresIngots() ? ServerUtils.getTotalItemAmout(pi, Items.IRON_INGOT)
					: ServerUtils.getTotalItemAmout(pi, Items.IRON_NUGGET);
			gunPowder = ServerUtils.getTotalItemAmout(pi, Items.GUNPOWDER);
		}
	}

	private void updateTab() {
		for (GunSlot slot : slots) {
			slot.updateTab(gunTab);
		}
	}

	private void renderMaterials(int i, int j) {
		if (mag.isIron()) {
			ItemStack ironStack = new ItemStack(Items.IRON_INGOT, mag.getMetal());
			RenderHelper.renderGuiItem(ironStack, i + 12, j + 8, Utils.getModel(Items.IRON_INGOT));
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, ironStack, (i + 12), (j + 8),
					String.valueOf(mag.getMetal()));
		} else {
			ItemStack steelStack = new ItemStack(ItemRegistries.SteelIngot.get(), mag.getMetal());
			RenderHelper.renderGuiItem(new ItemStack(ItemRegistries.SteelIngot.get(), mag.getMetal()), i + 12, j + 8,
					Utils.getModel(ItemRegistries.SteelIngot.get()));
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, steelStack, (i + 12), (j + 8),
					String.valueOf(mag.getMetal()));
		}
	}

	private void renderBulletAndHasMaterials(int i, int j, PoseStack matrixStack) {
		if (bulletStack != null && bulletModel != null) {
			RenderHelper.renderGuiItem(bulletStack, i + 138, j + 39, bulletModel);
			
			// RenderSystem.enableBlend();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, GUNAMMO_GUI);

			if (metal >= mag.getMetal()) {
				this.blit(matrixStack, i + 8, j + 8, 178, 80, 26, 16);
			} else {
				this.blit(matrixStack, i + 8, j + 8, 204, 80, 26, 16);
			}
		}
	}

	private void doBulletStuff(int i, int j, PoseStack matrixStack) {
		if (bulletStack != null && bulletModel != null) {
			// Render materials
			if (bullet.requiresIngots()) {
				ItemStack ironStack = new ItemStack(Items.IRON_INGOT, bullet.getMetal());
				RenderHelper.renderGuiItem(ironStack, i + 10, j + 8, Utils.getModel(Items.IRON_INGOT));
				Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, ironStack, (i + 12), (j + 8),
						String.valueOf(bullet.getMetal()));
			} else {
				ItemStack ironNuggetStack = new ItemStack(Items.IRON_NUGGET, bullet.getMetal());
				RenderHelper.renderGuiItem(new ItemStack(Items.IRON_NUGGET, bullet.getMetal()), i + 10, j + 8,
						Utils.getModel(Items.IRON_NUGGET));
				Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, ironNuggetStack, (i + 12),
						(j + 8), String.valueOf(bullet.getMetal()));
			}

			// Render gunpowder
			ItemStack gunPowderStack = new ItemStack(Items.GUNPOWDER, bullet.getGunPowder());
			RenderHelper.renderGuiItem(new ItemStack(Items.GUNPOWDER, bullet.getGunPowder()), i + 40, j + 8,
					Utils.getModel(Items.GUNPOWDER));
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, gunPowderStack, (i + 44), (j + 8),
					String.valueOf(bullet.getGunPowder()));
			
			// Render bullet
			ItemStack copy = bulletStack.copy();
			copy.setCount(amount);
			RenderHelper.renderGuiItem(copy, i + 138, j + 39, bulletModel);
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, 
					copy, i + 138, j + 39,
					String.valueOf(amount));
			
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, GUNAMMO_GUI);
			if (metal >= bullet.getMetal()) {
				this.blit(matrixStack, i + 8, j + 8, 178, 80, 26, 16);
			} else {
				this.blit(matrixStack, i + 8, j + 8, 204, 80, 26, 16);
			}

			if (gunPowder >= bullet.getGunPowder()) {
				this.blit(matrixStack, i + 36, j + 8, 178, 80, 26, 16);
			} else {
				this.blit(matrixStack, i + 36, j + 8, 204, 80, 26, 16);
			}

			RenderSystem.disableBlend();
		}
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GUNAMMO_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

		for (GunSlot gs : slots) {
			gs.render(matrixStack, p_230450_3_, p_230450_4_, p_230450_2_);
		}

		if (stack != null && model != null) {

			renderMaterials(i, j);

			RenderHelper.renderGuiItem(stack, i + 141, j + 14, model);

			renderBulletAndHasMaterials(i, j, matrixStack);

		} else {

			doBulletStuff(i, j, matrixStack);

		}

	}

	@Override
	public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
		for (GunSlot gs : slots) {
			gs.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
		}
		return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
	}

	@Override
	public void render(PoseStack p_97795_, int x, int y, float p_97798_) {
		super.render(p_97795_, x, y, p_97798_);
		
		// LogUtils.getLogger().info("x: " + x + " y: " + y);
		
		this.renderTooltip(p_97795_, x, y);
	}

	@Override
	public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
		if (p_231046_1_ == 340) {
			isAltPressed = true;
		}
		return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
	}

	@Override
	public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) {
		if (p_223281_1_ == 340) {
			isAltPressed = false;
		}
		return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
	}

}
