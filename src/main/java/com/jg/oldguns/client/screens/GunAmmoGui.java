package com.jg.oldguns.client.screens;

import java.util.ArrayList;
import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ItemsReg;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.screens.widgets.Button;
import com.jg.oldguns.client.screens.widgets.GunSlot;
import com.jg.oldguns.containers.AmmoCraftingTableContainer;
import com.jg.oldguns.guns.ItemBullet;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GunAmmoGui extends ContainerScreen<AmmoCraftingTableContainer>{
	
	int tabs;
	int gunTab;
	int metal;
	int gunPowder;
	
	boolean isIron;
	boolean isAltPressed;
	
	IBakedModel model;
	IBakedModel bulletModel;
	
	ItemMag mag;
	
	ItemBullet bullet;
	
	ItemStack stack;
	ItemStack bulletStack;
	
	ArrayList<GunSlot> slots = new ArrayList<GunSlot>();
	ArrayList<Item> items = new ArrayList<Item>();
	
	PlayerInventory pi;
	
	public static ResourceLocation GUNAMMO_GUI = new ResourceLocation(
			Util.loc("textures/gui/container/ammo_crafting_table.png"));
	
	public GunAmmoGui(AmmoCraftingTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent c) {
		super(p_i51105_1_, p_i51105_2_, new TranslationTextComponent("gui.oldguns.gun_ammo_gui"));
		pi = p_i51105_2_;
		for(Supplier<? extends Item> item : ItemsReg.INSTANCE.getExtraItems()) {
			if(item.get() instanceof ItemMag) {
				items.add(item.get());
			}
		}
		for(Supplier<? extends Item> item : ItemsReg.INSTANCE.getExtraItems()) {
			if(item.get() instanceof ItemBullet) {
				items.add(item.get());
			}
		}
	}

	@Override
	protected void init() {
		super.init();
		
		int i = leftPos;
		int j = topPos;

		int size = items.size();

		int cols = 6;
		int rows = 3;
		int cap = cols * rows;
		
		tabs = (int) Math.floor((size / cap) + 1);
		
		int t = 0;

		for (int g = 0; g < size; g++) {
		
		int wg = g - (t * cap);
	
			slots.add(new GunSlot(t, (i + 8) + (wg % cols) * 18, (j + 26) + (int) Math.floor(wg / cols) * 18, 178, 0,
					18, 18, 18, GUNAMMO_GUI, items.get(g)) {
				@Override
				public void onPress() {
					super.onPress();
					
					System.out.println(this.gun.getItem() instanceof ItemBullet);
					if(this.gun.getItem() instanceof ItemMag) {
						for(Item item : items) {
							if(item instanceof ItemBullet) {
								ItemBullet b = (ItemBullet)item;
								if(b.getSize().equals(((ItemMag)this.gun.getItem()).getAcceptedSize())) {
									bulletModel = ModelHandler.INSTANCE.getModel(b.getRegistryName());
									bulletStack = new ItemStack(item);
								}
							}
						}
						GunAmmoGui.this.stack = new ItemStack(this.gun.getItem());
						GunAmmoGui.this.mag = (ItemMag)this.gun.getItem();
						GunAmmoGui.this.model = ModelHandler.INSTANCE
								.getModel(this.gun.getItem().getRegistryName().toString());
						searchMetal();
					}else {
						GunAmmoGui.this.bulletModel = ModelHandler.INSTANCE.getModel(this.gun.getItem().getRegistryName());
						GunAmmoGui.this.bulletStack = new ItemStack(this.gun.getItem());
						GunAmmoGui.this.bullet = (ItemBullet)bulletStack.getItem();
						GunAmmoGui.this.stack = null;
						GunAmmoGui.this.mag = null;
						GunAmmoGui.this.model = null;
						searchMetal();
					}
					
				}
			});
		
			if (wg > cap - 2) {
				t++;
			}
			
		}
		
		addButton(new Button(i + 63, j + 7, 178, 60, 26, 18, 26, GUNAMMO_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				gunTab = (gunTab - 1 + tabs) % tabs;
				updateTab();
			}
		});

		addButton(new Button(i + 91, j + 7, 178, 40, 26, 18, 26, GUNAMMO_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				gunTab = (gunTab + 1) % tabs;
				updateTab();
			}
		});
		
		addButton(new Button(i + 133, j + 63, 178, 20, 26, 18, 26, GUNAMMO_GUI) {
			@Override
			public void onPress() {
				super.onPress();
				searchMetal();
				if(mag != null) {
					if(metal >= mag.getMetal()) {
						
						if(!isAltPressed) {
							ServerUtils.removeItemInDifIndexes(pi,
									mag.isIron() ? Items.IRON_INGOT
											: ItemRegistries.SteelIngot.get(), mag.getMetal());
							OldGuns.channel.sendToServer(new AddItemMessage(mag.getRegistryName().toString(), 1));
							SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
						}else {
							int metals = metal / mag.getMetal();
							ServerUtils.removeItemInDifIndexes(pi,
									mag.isIron() ? Items.IRON_INGOT
											: ItemRegistries.SteelIngot.get(), mag.getMetal() * metals);
							OldGuns.channel.sendToServer(new AddItemMessage(mag.getRegistryName().toString(), metals));
							SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
						}
						
					}
				}else if(bullet != null){
					if(metal >= bullet.getMetal()) {
						if(!isAltPressed) {
							ServerUtils.removeItemInDifIndexes(pi,
									bullet.requiresIngots() ? Items.IRON_INGOT
											: Items.IRON_NUGGET, bullet.getMetal());
							ServerUtils.removeItemInDifIndexes(pi,
									Items.GUNPOWDER, bullet.getGunPowder());
							OldGuns.channel.sendToServer(new AddItemMessage(bullet.getRegistryName().toString(), 1));
							SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
						}else {
							int metals = metal / bullet.getMetal();
							ServerUtils.removeItemInDifIndexes(pi,
									bullet.requiresIngots() ? Items.IRON_INGOT
											: Items.IRON_NUGGET, bullet.getMetal()*metals);
							ServerUtils.removeItemInDifIndexes(pi,
									Items.GUNPOWDER, bullet.getGunPowder()*metals);
							OldGuns.channel.sendToServer(new AddItemMessage(bullet.getRegistryName().toString(), metals));
							SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
						}
					}
				}
			}
		});
		
		for (GunSlot g : slots) {
			addButton(g);
		}
		
	}
	
	private void searchMetal() {
		if(stack != null) {
			metal = mag.isIron() ? ServerUtils.getTotalItemAmout(pi, Items.IRON_INGOT)
					: ServerUtils.getTotalItemAmout(pi, ItemRegistries.SteelIngot.get());
		}else if(bulletStack != null){
			metal = bullet.requiresIngots() ? ServerUtils.getTotalItemAmout(pi, Items.IRON_INGOT)
					: ServerUtils.getTotalItemAmout(pi, Items.IRON_NUGGET);
			gunPowder = ServerUtils.getTotalItemAmout(pi, Items.GUNPOWDER);
		}
	}
	
	private void updateTab() {
		for(GunSlot slot : slots) {
			slot.updateTab(gunTab);
		}
	}
	
	private void renderMaterials(int i, int j) {
		if(mag.isIron()) {
			ItemStack ironStack = new ItemStack(Items.IRON_INGOT, mag.getMetal());
			RenderUtil.renderGuiItem(ironStack, i + 7, j + 8, ModelHandler.INSTANCE.getModel(Items.IRON_INGOT.getRegistryName()));
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, ironStack, (i + 12),
					(j + 8), String.valueOf(mag.getMetal()));
		}else {
			ItemStack steelStack = new ItemStack(ItemRegistries.SteelIngot.get(), mag.getMetal());
			RenderUtil.renderGuiItem(new ItemStack(ItemRegistries.SteelIngot.get(), mag.getMetal()), i + 7, j + 8, ModelHandler.INSTANCE.getModel(ItemRegistries.SteelIngot.get().getRegistryName()));
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, steelStack, (i + 12),
					(j + 8), String.valueOf(mag.getMetal()));
		}
	}
	
	private void renderBulletAndHasMaterials(int i, int j, MatrixStack matrixStack) {
		if(bulletStack != null && bulletModel != null) {
			RenderUtil.renderGuiItem(bulletStack, i + 138, j + 39, bulletModel);

			if(metal >= mag.getMetal()) {
				Minecraft.getInstance().textureManager.bind(GUNAMMO_GUI);
				this.blit(matrixStack, i + 7, j + 8, 178, 80, 26, 16);
			}else {
				Minecraft.getInstance().textureManager.bind(GUNAMMO_GUI);
				this.blit(matrixStack, i + 7, j + 8, 204, 80, 26, 16);
			}
		}
	}
	
	private void doBulletStuff(int i, int j, MatrixStack matrixStack) {
		if(bulletStack != null && bulletModel != null) {
			
			if(bullet.requiresIngots()) {
				ItemStack ironStack = new ItemStack(Items.IRON_INGOT, bullet.getMetal());
				RenderUtil.renderGuiItem(ironStack, i + 10, j + 8, ModelHandler.INSTANCE.getModel(Items.IRON_INGOT.getRegistryName()));
				Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, ironStack, (i + 12),
						(j + 8), String.valueOf(bullet.getMetal()));
			}else {
				ItemStack ironNuggetStack = new ItemStack(Items.IRON_NUGGET, bullet.getMetal());
				RenderUtil.renderGuiItem(new ItemStack(Items.IRON_NUGGET, bullet.getMetal()), i + 10, j + 8, ModelHandler.INSTANCE.getModel(Items.IRON_NUGGET.getRegistryName()));
				Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, ironNuggetStack, (i + 12),
						(j + 8), String.valueOf(bullet.getMetal()));
			}
			
			ItemStack gunPowderStack = new ItemStack(Items.GUNPOWDER, bullet.getGunPowder());
			RenderUtil.renderGuiItem(new ItemStack(Items.GUNPOWDER, bullet.getGunPowder()), i + 40, j + 8, ModelHandler.INSTANCE.getModel(Items.GUNPOWDER.getRegistryName()));
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, gunPowderStack, (i + 44),
					(j + 8), String.valueOf(bullet.getGunPowder()));
			
			RenderUtil.renderGuiItem(bulletStack, i + 138, j + 39, bulletModel);
			if(metal >= bullet.getMetal()) {
				Minecraft.getInstance().textureManager.bind(GUNAMMO_GUI);
				this.blit(matrixStack, i + 8, j + 8, 178, 80, 26, 16);
			}else {
				Minecraft.getInstance().textureManager.bind(GUNAMMO_GUI);
				this.blit(matrixStack, i + 8, j + 8, 204, 80, 26, 16);
			}
			
			if(gunPowder >= bullet.getGunPowder()) {
				Minecraft.getInstance().textureManager.bind(GUNAMMO_GUI);
				this.blit(matrixStack, i + 36, j + 8, 178, 80, 26, 16);
			}else {
				Minecraft.getInstance().textureManager.bind(GUNAMMO_GUI);
				this.blit(matrixStack, i + 36, j + 8, 204, 80, 26, 16);
			}
		}
	}
	
	@Override
	protected void renderBg(MatrixStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		Minecraft.getInstance().textureManager.bind(GUNAMMO_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

		for (GunSlot gs : slots) {
			gs.render(matrixStack, p_230450_3_, p_230450_4_, p_230450_2_);
		}
		
		if(stack != null && model != null) {
			
			renderMaterials(i, j);
			
			RenderUtil.renderGuiItem(stack, i + 141, j + 14, model);
			
			renderBulletAndHasMaterials(i, j, matrixStack);
			
		}else {
			
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
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		this.renderTooltip(matrixStack, x - leftPos, y - topPos);
	}
	
	@Override
	public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
		if(p_231046_1_ == 340) {
			isAltPressed = true;
		}
		return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
	}
	
	@Override
	public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) {
		if(p_223281_1_ == 340) {
			isAltPressed = false;
		}
		return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
	}
	
}
