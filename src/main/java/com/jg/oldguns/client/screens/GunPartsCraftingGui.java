package com.jg.oldguns.client.screens;

import java.util.Random;
import java.util.function.Supplier;

import com.jg.oldguns.client.handlers.ItemsReg;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.screens.widgets.JgButton;
import com.jg.oldguns.client.screens.widgets.JgListWidget;
import com.jg.oldguns.client.screens.widgets.JgListWidget.JgListKey;
import com.jg.oldguns.client.screens.widgets.OnlyViewWidget;
import com.jg.oldguns.containers.GunPartsContainer;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.MagItem;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.InventoryUtils;
import com.jg.oldguns.utils.InventoryUtils.InvData;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
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

public class GunPartsCraftingGui extends AbstractContainerScreen<GunPartsContainer> {

	private OnlyViewWidget<ItemStack> parent;
	private OnlyViewWidget<ItemStack> result;
	private OnlyViewWidget<ItemStack> ing1;
	private OnlyViewWidget<ItemStack> ing2;
	
	private JgListWidget<GunItemKey> gunsList;
	private JgListWidget<GunPartItemKey> partsList;
	
	private JgButton craftBtn;
	
	private Inventory pi;
	
	private int metal;
	private int wood;
	
	public static ResourceLocation GUNPARTS_GUI = 
			Utils.loc("textures/gui/container/gun_parts_crafting_table_gui.png");
	
	public GunPartsCraftingGui(GunPartsContainer p_97741_, Inventory p_97742_, Component p_97743_) {
		super(p_97741_, p_97742_, new TranslatableComponent("gui.oldguns.gun_parts_crafting_table_gui"));
		this.pi = p_97742_;
	}
	
	@Override
	protected void init() {
		super.init();
		
		int i = leftPos;
		int j = topPos;
		
		craftBtn = new JgButton(this, i + 153, j + 16, 153, 16, 16, 16, GUNPARTS_GUI, 
				() -> {
			searchIngredients();
			tryToCraft();
		});
		
		addRenderableWidget(craftBtn);
		
		parent = new OnlyViewWidget<ItemStack>(this, i + 127, j + 16, 16, 16) {

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
					GunPartsCraftingGui.this.renderComponentTooltip(matrix, 
							item.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), x, y);
				}
			}
		};
		
		addRenderableWidget(parent);
		
		result = new OnlyViewWidget<ItemStack>(this, i + 155, j + 48, 16, 16) {

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
					GunPartsCraftingGui.this.renderComponentTooltip(matrix, 
							item.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), x, y);
				}
			}
		};
		
		addRenderableWidget(result);
		
		ing1 = new OnlyViewWidget<ItemStack>(this, i + 127, j + 36, 16, 16) {

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
					GunPartsCraftingGui.this.renderComponentTooltip(matrix, 
							item.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), x, y);
				}
			}
		};
		
		addRenderableWidget(ing1);
		
		ing2 = new OnlyViewWidget<ItemStack>(this, i + 127, j + 60, 16, 16) {

			@Override
			public void renderSlot(PoseStack matrix, int x, int y) {
				if(item != null) {
					Minecraft.getInstance().getItemRenderer().renderGuiItem(item, this.x, this.y);
					Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, item, 
							this.x, this.y);
					if(!isHovered) {
						if(wood < item.getCount()) {
							shadow(matrix, this.x, this.y);
						}
					}
				}
			}

			@Override
			public void renderHovered(PoseStack matrix, int x, int y) {
				if(item != null) {
					highlight(matrix, this.x, this.y);
					GunPartsCraftingGui.this.renderComponentTooltip(matrix, 
							item.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), x, y);
				}
			}
		};
		
		addRenderableWidget(ing2);
		
		partsList = new JgListWidget<>(i + 76, j + 17, 32, 48, 16, GUNPARTS_GUI);
		partsList.getSideBar().setXOffset(4);
		
		addRenderableWidget(partsList);
		
		gunsList = new JgListWidget<>(i + 8, j + 17, 48, 48, 16, GUNPARTS_GUI);
		for(Supplier<? extends Item> item : ItemsReg.INSTANCE.getGuns()) {
			gunsList.addItem(new GunItemKey(new ItemStack(item.get())));
		}
		gunsList.addItem(new GunItemKey(new ItemStack(ItemRegistries.THOMPSON.get())));
		gunsList.addItem(new GunItemKey(new ItemStack(ItemRegistries.THOMPSON.get())));
		gunsList.getSideBar().setXOffset(4);
		
		addRenderableWidget(gunsList);
		
		searchIngredients();
	}
	
	@Override
	protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GUNPARTS_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(p_97787_, i, j, 0, 0, this.imageWidth, this.imageHeight);
	}
	
	@Override
	public void render(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
		super.render(p_97795_, p_97796_, p_97797_, p_97798_);
	}
	
	@Override
	public boolean mouseDragged(double p_97752_, double p_97753_, int p_97754_, double p_97755_, double p_97756_) {
		gunsList.mouseDragged(p_97752_, p_97753_, p_97754_, p_97755_, p_97756_);
		partsList.mouseDragged(p_97752_, p_97753_, p_97754_, p_97755_, p_97756_);
		return super.mouseDragged(p_97752_, p_97753_, p_97754_, p_97755_, p_97756_);
	}
	
	private void searchIngredients() {
		if(result.getItem() != null) {
			metal = ServerUtils.getTotalItemAmout(pi, Items.IRON_INGOT);
			wood = InventoryUtils.getTotalCountForTag(Minecraft.getInstance().player, 
					ItemTags.PLANKS);
		}
	}
	
	private void setResult(ItemStack stack) {
		searchIngredients();
		if(stack != null) {
			GunPart part = (GunPart) stack.getItem();
			result.setItem(new ItemStack(part));
			ing1.setItem(new ItemStack(ItemRegistries.SteelIngot.get(), part.getMetal()));
			ing2.setItem(new ItemStack(ForgeRegistries.ITEMS.tags().getTag(ItemTags.PLANKS)
					.getRandomElement(new Random()).get(), part.getWood()));
		} else {
			result.setItem(null);
			ing1.setItem(null);
			ing2.setItem(null);
		}
	}
	
	private void tryToCraft() {
		if(result.getItem() != null) {
			searchIngredients();
			if(metal >= ing1.getItem().getCount() && wood >= ing2.getItem().getCount()) {
				GunPart part = (GunPart) result.getItem().getItem();
				ServerUtils.removeItemInDifIndexes(pi,
						ItemRegistries.SteelIngot.get(), part.getMetal());
				InventoryUtils.consumeItems(Minecraft.getInstance()
						.player, InventoryUtils.getTotalCountAndIndexForItem(Minecraft.getInstance()
						.player, ItemTags.PLANKS, part.getWood()).getData());
				ServerUtils.addItem(part, 1);
				SoundHandler.playSoundOnServer(SoundRegistries.CRAFT_SOUND.get());
			}
		}
	}

	private class GunItemKey extends JgListKey {

		ItemStack stack;
		
		public GunItemKey(ItemStack stack) {
			this.stack = stack;
		}
		
		@Override
		public void onClick(double mx, double my, int index) {
			GunItem gun = (GunItem) stack.getItem();
			parent.setItem(stack);
			for(Supplier<? extends Item> item : ItemsReg.INSTANCE.getGunParts().get(gun.getGunId())) {
				partsList.addItem(new GunPartItemKey(new ItemStack(item.get())));
			}
		}

		@Override
		public void onUnselect(double mx, double my, int index) {
			partsList.clearItems();
			parent.setItem(null);
		}

		@Override
		public void render(PoseStack matrix, int x, int y, int mouseX, int mouseY, int index, boolean selected) {
			RenderSystem.enableBlend();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, GUNPARTS_GUI);
			
			blit(matrix, x, y, 203, 0, 16, 16);
			Minecraft.getInstance().getItemRenderer().renderGuiItem(stack, x, y);
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, stack, x, y);
			if(selected) {
				shadow(matrix, x, y, 16, 16);
			}
		}

		@Override
		public void renderHovered(PoseStack matrix, int x, int y, int mouseX, int mouseY, int index, boolean selected) {
			if(isHovered(x, y, mouseX, mouseY)) {
				GunPartsCraftingGui.this.renderComponentTooltip(matrix, 
						stack.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), 
						mouseX, mouseY);
			}
		}
		
	}
	
	private class GunPartItemKey extends JgListKey {

		ItemStack stack;
		
		public GunPartItemKey(ItemStack stack) {
			this.stack = stack;
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
		public void render(PoseStack matrix, int x, int y, int mouseX, int mouseY, int index, boolean selected) {
			RenderSystem.enableBlend();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, GUNPARTS_GUI);
			
			blit(matrix, x, y, 203, 0, 16, 16);
			Minecraft.getInstance().getItemRenderer().renderGuiItem(stack, x, y);
			Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, stack, x, y);
			if(selected) {
				shadow(matrix, x, y, 16, 16);
			}
		}

		@Override
		public void renderHovered(PoseStack matrix, int x, int y, int mouseX, int mouseY, int index, boolean selected) {
			if(isHovered(x, y, mouseX, mouseY)) {
				GunPartsCraftingGui.this.renderComponentTooltip(matrix, 
						stack.getTooltipLines((Player)null, TooltipFlag.Default.NORMAL), 
						mouseX, mouseY);
			}
		}
		
	}
	
}
