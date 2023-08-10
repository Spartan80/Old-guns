package com.jg.oldguns.client.screens;

import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.ItemsReg;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.screens.widgets.JgButton;
import com.jg.oldguns.client.screens.widgets.JgListWidget;
import com.jg.oldguns.client.screens.widgets.JgListWidget.JgListKey;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.client.screens.widgets.OnlyViewWidget;
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
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.registries.ForgeRegistries;

public class AmmoCraftingGui extends AbstractContainerScreen<AmmoCraftingTableContainer> {

	private OnlyViewWidget<ItemStack> result;
	private OnlyViewWidget<ItemStack> ing1;
	private OnlyViewWidget<ItemStack> ing2;
	
	private JgListWidget<AmmoItemKey> itemsList;
	
	private JgButton craftBtn;
	
	private Inventory pi;
	
	private int metal;
	private int gunPowder;
	
	public static ResourceLocation GUNAMMO_GUI = 
			Utils.loc("textures/gui/container/ammo_crafting_table_gui.png");
	
	public AmmoCraftingGui(AmmoCraftingTableContainer p_97741_, 
			Inventory p_97742_, Component p_97743_) {
		super(p_97741_, p_97742_, new TranslatableComponent("gui.oldguns.gun_ammo_table"));
		pi = p_97742_;
	}
	
	@Override
	protected void init() {
		super.init();
		
		int i = leftPos;
		int j = topPos;
		
		craftBtn = new JgButton(this, i + 136, j + 61, 136, 61, 
				26, 18, GUNAMMO_GUI, () -> {
					searchIngredients();
					tryToCraft();
				});
		
		addRenderableWidget(craftBtn);
		
		itemsList = new JgListWidget<>(i + 8, j + 17, 96, 48, 16, GUNAMMO_GUI);
		itemsList.getSideBar().setXOffset(6);
		
		// Adding Items
		for (Supplier<? extends Item> item : ItemsReg.INSTANCE.getMags()) {
			itemsList.addItem(new AmmoItemKey(item.get()));
		}
		for (Supplier<? extends Item> item : ItemsReg.INSTANCE.getExtraItems()) {
			itemsList.addItem(new AmmoItemKey(item.get()));
		}
		for (Supplier<? extends Item> item : ItemsReg.INSTANCE.getMags()) {
			itemsList.addItem(new AmmoItemKey(item.get()));
		}
		for (Supplier<? extends Item> item : ItemsReg.INSTANCE.getExtraItems()) {
			itemsList.addItem(new AmmoItemKey(item.get()));
		}
		for (Supplier<? extends Item> item : ItemsReg.INSTANCE.getMags()) {
			itemsList.addItem(new AmmoItemKey(item.get()));
		}
		for (Supplier<? extends Item> item : ItemsReg.INSTANCE.getExtraItems()) {
			itemsList.addItem(new AmmoItemKey(item.get()));
		}
		
		addRenderableWidget(itemsList);
	
		result = new OnlyViewWidget<ItemStack>(this, i + 141, j + 16, 16, 16) {

			@Override
			public void renderSlot(PoseStack matrix, int x, int y) {
				if(item != null) {
					Minecraft.getInstance().getItemRenderer().renderGuiItem(item, this.x, this.y);
					Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, item, 
							this.x, this.y);
				}
			}

			@Override
			public void renderHovered(PoseStack matrix, int x, int y) {
				if(item != null) {
					highlight(matrix, this.x, this.y);
					AmmoCraftingGui.this.renderComponentTooltip(matrix, 
							item.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), x, y);
				}
			}
			
		};
		
		addRenderableWidget(result);
		
		ing1 = new OnlyViewWidget<ItemStack>(this, i + 129, j + 44, 16, 16) {
			
			@Override
			public void renderSlot(PoseStack matrix, int x, int y) {
				if(item != null) {
					Minecraft.getInstance().getItemRenderer().renderGuiItem(item, this.x, this.y);
					Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, item, 
							this.x, this.y);
					if(!isHovered) {
						if(metal < item.getCount()) {
							shadow(matrix, this.x, this.y);
						}
					}
				}
			}

			@Override
			public void renderHovered(PoseStack matrix, int x, int y) {
				if(item != null) {
					highlight(matrix, this.x, this.y);
					AmmoCraftingGui.this.renderComponentTooltip(matrix, 
							item.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), x, y);
				}
			}
			
		};
	
		addRenderableWidget(ing1);
		
		ing2 = new OnlyViewWidget<ItemStack>(this, i + 153, j + 44, 16, 16) {
			@Override
			public void renderSlot(PoseStack matrix, int x, int y) {
				if(item != null) {
					Minecraft.getInstance().getItemRenderer().renderGuiItem(item, this.x, this.y);
					Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, item, 
							this.x, this.y);
					if(!isHovered) {
						if(gunPowder < item.getCount()) {
							shadow(matrix, this.x, this.y);
						}
					}
				}
			}

			@Override
			public void renderHovered(PoseStack matrix, int x, int y) {
				if(item != null) {
					highlight(matrix, this.x, this.y);
					AmmoCraftingGui.this.renderComponentTooltip(matrix, 
							item.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), x, y);
				}
			}
		};
	
		addRenderableWidget(ing2);
		
		searchIngredients();
	}
	
	@Override
	public boolean mouseDragged(double p_97752_, double p_97753_, int p_97754_, double p_97755_, double p_97756_) {
		itemsList.mouseDragged(p_97752_, p_97753_, p_97754_, p_97755_, p_97756_);
		return super.mouseDragged(p_97752_, p_97753_, p_97754_, p_97755_, p_97756_);
	}

	@Override
	protected void renderBg(PoseStack matrixStack, float p_97788_, int p_97789_, int p_97790_) {
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GUNAMMO_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}
	
	private void searchIngredients() {
		if(result.getItem() != null) {
			if (result.getItem().getItem() instanceof MagItem) {
				MagItem mag = (MagItem) result.getItem().getItem();
				metal = mag.isIron() ? ServerUtils.getTotalItemAmout(pi, Items.IRON_INGOT)
						: ServerUtils.getTotalItemAmout(pi, ItemRegistries.SteelIngot.get());
			} else if (result.getItem().getItem() instanceof BulletItem) {
				BulletItem bullet = (BulletItem) result.getItem().getItem();
				metal = bullet.requiresIngots() ? ServerUtils.getTotalItemAmout(pi, Items.IRON_INGOT)
						: ServerUtils.getTotalItemAmout(pi, Items.IRON_NUGGET);
				gunPowder = ServerUtils.getTotalItemAmout(pi, Items.GUNPOWDER);
			}
		}
	}
	
	private void tryToCraft() {
		int freeSlot = pi.getFreeSlot();
		if (result.getItem() != null) {
			if (result.getItem().getItem() instanceof MagItem) {
				if (metal >= ing1.getItem().getCount() && freeSlot != -1) {
					MagItem mag = (MagItem) result.getItem().getItem();
					ServerUtils.removeItemInDifIndexes(pi,
							mag.isIron() ? Items.IRON_INGOT : ItemRegistries.SteelIngot.get(), mag.getMetal());
					ServerUtils.addItem(mag, 1);
					SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
				}
			} else {
				int slotMatching = pi.findSlotMatchingItem(result.getItem());
				BulletItem bullet = (BulletItem) result.getItem().getItem();
				int amount = 1;
				if (Utils.getR(bullet).toString().contains("small")) {
					amount = Config.SERVER.smallBulletCraftingResult.get();
				} else if (Utils.getR(bullet).toString().contains("medium")) {
					amount = Config.SERVER.mediumBulletCraftingResult.get();
				} else if (Utils.getR(bullet).toString().contains("big")) {
					amount = Config.SERVER.bigBulletCraftingResult.get();
				} else if (Utils.getR(bullet).toString().contains("shotgun")) {
					amount = Config.SERVER.shotgunBulletCraftingResult.get();
				}
				/*if(((slotMatching != -1 ? 64 - pi.getItem(slotMatching).getCount() >= amount 
						: false) || freeSlot != -1)) {*/
					LogUtils.getLogger().info("sdasd");
					if (metal >= ing1.getItem().getCount() && gunPowder >= ing2.getItem().getCount()) {
						ServerUtils.removeItemInDifIndexes(pi,
								bullet.requiresIngots() ? Items.IRON_INGOT : Items.IRON_NUGGET, bullet.getMetal());
						ServerUtils.removeItemInDifIndexes(pi, Items.GUNPOWDER, bullet.getGunPowder());
						ServerUtils.addItem(bullet, amount);
						SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
					}
				//}
			}
		}
	}
	
	private void setResult(ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() instanceof MagItem) {
				result.setItem(stack);
				MagItem mag = (MagItem) result.getItem().getItem();
				ing1.setItem(new ItemStack(mag.isIron() ? Items.IRON_INGOT : 
					ItemRegistries.SteelIngot.get(), mag.getMetal()));
			} else {
				Item bulletItem = stack.getItem();
				int amount = 1;
				if (Utils.getR(bulletItem).toString().contains("small")) {
					amount = Config.SERVER.smallBulletCraftingResult.get();
				} else if (Utils.getR(bulletItem).toString().contains("medium")) {
					amount = Config.SERVER.mediumBulletCraftingResult.get();
				} else if (Utils.getR(bulletItem).toString().contains("big")) {
					amount = Config.SERVER.bigBulletCraftingResult.get();
				} else if (Utils.getR(bulletItem).toString().contains("shotgun")) {
					amount = Config.SERVER.shotgunBulletCraftingResult.get();
				}
				result.setItem(new ItemStack(stack.getItem(), amount));
				BulletItem bullet = (BulletItem) result.getItem().getItem();
				ing1.setItem(new ItemStack(bullet.requiresIngots() ? Items.IRON_INGOT : 
					Items.IRON_NUGGET, bullet.getMetal()));
				ing2.setItem(new ItemStack(Items.GUNPOWDER, bullet.getGunPowder()));
			}
		} else {
			result.setItem(null);
			ing1.setItem(null);
			ing2.setItem(null);
		}
	}
	
	private class AmmoItemKey extends JgListKey {

		ItemStack stack;
		
		public AmmoItemKey(Item item) {
			this.stack = new ItemStack(item);
		}
		
		@Override
		public void onClick(double mx, double my, int index) {
			setResult(stack);
			searchIngredients();
		}

		@Override
		public void onUnselect(double mx, double my, int index) {
			setResult(null);
		}

		@Override
		public void render(PoseStack matrix, int x, int y, int mouseX, int mouseY, int index, 
				boolean selected) {
			RenderSystem.enableBlend();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, GUNAMMO_GUI);
			
			blit(matrix, x, y, 203, 0, 16, 16);
			Minecraft.getInstance().getItemRenderer().renderGuiItem(stack, x, y);
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, stack, x, y);
			if(selected) {
				shadow(matrix, x, y, 16, 16);
			}
		}

		@Override
		public void renderHovered(PoseStack matrix, int x, int y, int mouseX, int mouseY, 
				int index, boolean selected) {
			if(isHovered(x, y, mouseX, mouseY)) {
				AmmoCraftingGui.this.renderComponentTooltip(matrix, 
						stack.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), 
						mouseX, mouseY);
			}
		}
		
	}

}
