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
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GunCraftingGui extends ContainerScreen<GunCraftingTableContainer> {

	private int tab, tabs;

	private List<GunSlot> slots;

	private ItemStack current;
	private IBakedModel model;

	private GunPartCheckSlot[] parts;

	private PlayerInventory pi;

	public static ResourceLocation GUN_GUI = new ResourceLocation(
			Util.loc("textures/gui/container/guncrafting_table.png"));

	public GunCraftingGui(GunCraftingTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent c) {
		super(p_i51105_1_, p_i51105_2_, new TranslationTextComponent("gui.oldguns.gun_crafting_gui"));
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

		parts[0] = new GunPartCheckSlot(i + 8, j + 35, 177, 80, 24, 14);
		parts[1] = new GunPartCheckSlot(i + 8, j + 50, 177, 80, 24, 14);
		parts[2] = new GunPartCheckSlot(i + 8, j + 65, 177, 80, 24, 14);

		int size = ItemsReg.INSTANCE.getGuns().size();

		tabs = (int) Math.floor((size / 24) + 1);

		// Cols = 6 Rows = 4

		int t = 0;

		for (int g = 0; g < size; g++) {

			int wg = g - (t * 24);

			slots.add(new GunSlot(t, (i + 34) + (wg % 6) * 18, (j + 9) + (int) Math.floor(wg / 6) * 18, 177, 60, 18, 18,
					18, GUN_GUI, ItemsReg.INSTANCE.getGun(g)) {
				@Override
				public void onPress() {
					super.onPress();
					current = new ItemStack(this.gun.getItem());
					// model = ModelHandler.INSTANCE.getNonSpecialModel(this.gun.getItem());
					GunCraftingGui.this.model = ModelHandler.INSTANCE.getModel(this.gun.getItem().getRegistryName());
					
					ItemGun gun = (ItemGun) current.getItem();
					if (gun.getBarrel() != null) {
						parts[0].setPart(gun.getBarrel());
					}
					if (gun.getBody() != null) {
						parts[1].setPart(gun.getBody());
					}
					if (gun.getStock() != null) {
						parts[2].setPart(gun.getStock());
					}
				}
			});

			if (wg > 22) {
				t++;
			}
		}

		// Right
		addButton(new Button(i + 143, j + 8, 177, 0, 26, 18, 26, GUN_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				tab = (tab + 1) % tabs;
				updateButtons();
			}
		});

		// Left
		addButton(new Button(i + 143, j + 30, 177, 20, 26, 18, 26, GUN_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				tab = (tab - 1 + tabs) % tabs;
				updateButtons();
			}
		});

		// Craft button
		addButton(new Button(i + 143, j + 64, 177, 40, 26, 18, 26, GUN_GUI) {
			@Override
			public void onPress() {
				super.onPress();
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
			addButton(g);
		}

	}

	@Override
	public void tick() {
		super.tick();
		for (GunPartCheckSlot g : parts) {
			g.update(pi);
		}
	}

	public void updateButtons() {
		for (GunSlot g : slots) {
			g.updateTab(tab);
		}
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		Minecraft.getInstance().textureManager.bind(GUN_GUI);
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
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		/*
		 * this.font.draw(matrixStack, this.title, (float) this.titleLabelX-4, (float)
		 * this.titleLabelY-4, 4210752); this.font.draw(matrixStack,
		 * this.inventory.getDisplayName(), (float) this.inventoryLabelX, (float)
		 * this.inventoryLabelY, 4210752);
		 */
		this.renderTooltip(matrixStack, x - leftPos, y - topPos);
	}

}
