package com.jg.oldguns.client.screens;

import java.util.ArrayList;
import java.util.List;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ItemsReg;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.screens.widgets.Button;
import com.jg.oldguns.client.screens.widgets.GunPartCheckSlot;
import com.jg.oldguns.client.screens.widgets.GunSlot;
import com.jg.oldguns.containers.GunCraftingTableContainer;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class GunCraftingGui extends AbstractContainerScreen<GunCraftingTableContainer> {

	private int tab, tabs;

	private List<GunSlot> slots;

	private ItemStack current;
	private BakedModel model;

	private GunPartCheckSlot[] parts;

	private Inventory pi;

	public static ResourceLocation GUN_GUI = new ResourceLocation(
			Util.loc("textures/gui/container/guncrafting_table.png"));

	public GunCraftingGui(GunCraftingTableContainer p_i51105_1_, Inventory p_i51105_2_, Component c) {
		super(p_i51105_1_, p_i51105_2_, new TranslatableComponent("gui.oldguns.gun_crafting_gui"));
		this.tab = 0;
		this.tabs = 0;
		this.slots = new ArrayList<GunSlot>();
		parts = new GunPartCheckSlot[3];
		this.pi = p_i51105_2_;
	}

	@Override
	protected void init() {
		super.init();
		int i = leftPos;
		int j = topPos;

		parts[0] = new GunPartCheckSlot(this, i + 8, j + 35, 177, 80, 24, 14);
		parts[1] = new GunPartCheckSlot(this, i + 8, j + 50, 177, 80, 24, 14);
		parts[2] = new GunPartCheckSlot(this, i + 8, j + 65, 177, 80, 24, 14);

		int size = ItemsReg.INSTANCE.getGuns().size();

		tabs = (int) Math.floor((size / 24) + 1);

		// Cols = 6 Rows = 4

		int t = 0;

		for (int g = 0; g < size; g++) {

			int wg = g - (t * 24);

			slots.add(new GunSlot(this, t, (i + 34) + (wg % 6) * 18, (j + 9) + (int) Math.floor(wg / 6) * 18, 177, 60, 18, 18,
					18, GUN_GUI, ItemsReg.INSTANCE.getGun(g)) {
				@Override
				public void onPress() {
					super.onPress();
					current = new ItemStack(this.gun.getItem());
					// model = ModelHandler.INSTANCE.getNonSpecialModel(this.gun.getItem());
					GunCraftingGui.this.model = ModelHandler.INSTANCE.getModel(this.gun.getItem().getRegistryName());
					
					ItemGun gun = (ItemGun) current.getItem();
					if (gun.getBarrel() != null) {
						parts[0].setPart(gun.getBarrel(), pi);
					}
					if (gun.getBody() != null) {
						parts[1].setPart(gun.getBody(), pi);
					}
					if (gun.getStock() != null) {
						parts[2].setPart(gun.getStock(), pi);
					}
				}
			});

			if (wg > 22) {
				t++;
			}
		}

		// Right
		addRenderableWidget(new Button(this, i + 143, j + 8, 177, 0, 26, 18, 26, GUN_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				tab = (tab + 1) % tabs;
				updateButtons();
			}
		});

		// Left
		addRenderableWidget(new Button(this, i + 143, j + 30, 177, 20, 26, 18, 26, GUN_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				tab = (tab - 1 + tabs) % tabs;
				updateButtons();
			}
		});

		// Craft button
		addRenderableWidget(new Button(this, i + 143, j + 64, 177, 40, 26, 18, 26, GUN_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				parts[0].check(pi);
				parts[1].check(pi);
				parts[2].check(pi);
				if (parts[0].hasPart() && parts[1].hasPart() && parts[2].hasPart()) {
					ReloadHandler.removeItem(parts[0].getSlot(), 1);
					ReloadHandler.removeItem(parts[1].getSlot(), 1);
					ReloadHandler.removeItem(parts[2].getSlot(), 1);
					OldGuns.channel.sendToServer(new AddItemMessage(current.getItem().getRegistryName().toString(), 1));
					SoundHandler.playSoundOnServer(SoundRegistries.HAMMER_WORKING_SOUND.get());
				}
			}
		});

		for (GunSlot g : slots) {
			addRenderableWidget(g);
		}

	}

	public void updateButtons() {
		for (GunSlot g : slots) {
			g.updateTab(tab);
		}
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		Minecraft.getInstance().textureManager.bindForSetup(GUN_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
		if (current != null && model != null) {
			
			RenderUtil.renderGuiItem(current, (i + 8) + 3, (j + 9) + 3, model);
			for (GunPartCheckSlot g : parts) {
				g.render(matrixStack, p_230450_3_, p_230450_4_, p_230450_2_);
			}
		}
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int x, int y) {
		/*
		 * this.font.draw(matrixStack, this.title, (float) this.titleLabelX-4, (float)
		 * this.titleLabelY-4, 4210752); this.font.draw(matrixStack,
		 * this.inventory.getDisplayName(), (float) this.inventoryLabelX, (float)
		 * this.inventoryLabelY, 4210752);
		 */
		this.renderTooltip(matrixStack, x - leftPos, y - topPos);
	}

}
