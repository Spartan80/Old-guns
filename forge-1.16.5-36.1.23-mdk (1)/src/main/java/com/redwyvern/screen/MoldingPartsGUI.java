package com.redwyvern.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.redwyvern.container.MoldingPartsContainer;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.items.GunPartMold;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.network.CleanGunSlotMessage;
import com.redwyvern.network.MoldingCraftMessage;
import com.redwyvern.network.MoldingPartsDataMessage;
import com.redwyvern.network.UpdateGunShowing;
import com.redwyvern.proxy.ClientProxy;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.util.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MoldingPartsGUI extends ContainerScreen<MoldingPartsContainer> {

	public MoldingPartsContainer container;
	private int actgun;
	private boolean metal = true;
	private int woodreq = 0;

	public ArrayList<ItemSlot> metalitems = new ArrayList<MoldingPartsGUI.ItemSlot>();
	public ArrayList<ItemSlot> wooditems = new ArrayList<MoldingPartsGUI.ItemSlot>();
	public static final ResourceLocation MOLDING_PARTS_GUI = new ResourceLocation(
			"oldguns:textures/gui/container/molding_parts_gui.png");

	public MoldingPartsGUI(MoldingPartsContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		container = screenContainer;
	}

	@Override
	protected void init() {
		super.init();
		int i = this.leftPos;
		int j = this.topPos;

		for (int row = 0; row < 2; row++) {
			for (int index = 0; index < 6; index++) {
				ItemSlot slot = new ItemSlot(i + 5 + (index * 21), j + 14 + (row * 14),
						Util.gunmetalparts.get((row * 6) + index), container.slot, container.data, container.slotS) {
					@Override
					public void onPress() {
						clicked = true;
						if (slot.hasItem()) {
							slotr.set(new ItemStack(ItemRegistries.mold.get()));
							GunPartMold mold = ((GunPartMold) slotr.getItem().getItem());
							mold.setNBTMoldName(slotr.getItem(), this.item.getDescriptionId());
							mold.setNBTPart(slotr.getItem(), this.item.getItem().getRegistryName().toString());
							slots.set(this.item);
							OldGuns.channel.sendToServer(new MoldingCraftMessage(this.item.getDescriptionId(),
									this.item.getItem().getRegistryName().toString()));
						}

					}
				};
				addButton(slot);
				metalitems.add(slot);
			}
		}

		for (int index = 0; index < 6; index++) {
			ItemSlot slot = new ItemSlot(i + 5 + (index * 21), j + 14, Util.gunwoodparts.get(index), container.slot,
					container.data, container.slotS) {
				@Override
				public void onPress() {
					clicked = true;
					woodreq = Util.getWoodsFor(this.item.getItem().getRegistryName().toString());
					slots.set(this.item);
					OldGuns.channel.sendToServer(new MoldingPartsDataMessage(1, woodreq));
					menu.datam.set(1, woodreq);
					if (!menu.getSlot(1).getItem().isEmpty()) {
						if (menu.getSlot(1).getItem().getCount() > woodreq) {
							menu.getSlot(0).set(this.item);
						}
					}
					OldGuns.channel.sendToServer(
							new MoldingCraftMessage("", this.item.getItem().getRegistryName().toString()));
				}
			};
			addButton(slot);
			wooditems.add(slot);
		}

		makeMetalItems(true);
		makeWoodItems(false);

		addButton(new Button(this.leftPos + 44, this.topPos + 51, 14, 18, new TranslationTextComponent("<"), (b) -> {
			actgun = (actgun - 1 + Util.guns.size()) % Util.guns.size();
			container.getSlot(3).set(new ItemStack(Util.guns.get(actgun)));
			OldGuns.channel
					.sendToServer(new UpdateGunShowing(Util.guns.get(actgun).getRegistryName().toString(), false));
			container.broadcastChanges();

			if (metal) {
				makeMetalItemVisible();
			} else {
				makeWoodItemVisible();
			}
		}));

		addButton(new Button(this.leftPos + 82, this.topPos + 51, 14, 18, new TranslationTextComponent(">"), (b) -> {
			actgun = (actgun + 1) % Util.guns.size();
			container.getSlot(3).set(new ItemStack(Util.guns.get(actgun)));
			OldGuns.channel
					.sendToServer(new UpdateGunShowing(Util.guns.get(actgun).getRegistryName().toString(), false));
			container.broadcastChanges();
			if (metal) {
				makeMetalItemVisible();
			} else {
				makeWoodItemVisible();
			}
		}));

		addButton(new Button(this.leftPos + 6, this.topPos + 51, 32, 18,
				new TranslationTextComponent("gui.oldguns.molding_parts_block.filter"), (b) -> {
					filterItemsFor((ItemGun) Util.guns.get(actgun));

				}));

		addButton(new Button(this.leftPos + 106, this.topPos + 51, 32, 18,
				new TranslationTextComponent("gui.oldguns.molding_parts_block.switch"), (b) -> {
					if (container.getSlot(1).hasItem()) {
						if (this.inventory.getFreeSlot() != -1) {
							this.inventory.add(menu.getSlot(1).getItem());
						} else {
							return;
						}
					}

					metal = !metal;
					OldGuns.channel.sendToServer(new MoldingPartsDataMessage(0, metal ? 0 : 1));
					menu.datam.set(0, metal ? 0 : 1);
					menu.getSlot(2).set(ItemStack.EMPTY);
					menu.getSlot(0).set(ItemStack.EMPTY);

					OldGuns.channel.sendToServer(new CleanGunSlotMessage());
					if (metal) {
						makeWoodItems(false);
						makeMetalItems(true);
					} else {
						makeMetalItems(false);
						makeWoodItems(true);
					}
				}));

		metal = true;
		OldGuns.channel.sendToServer(new MoldingPartsDataMessage(0, 0));
		menu.datam.set(0, 0);
		container.getSlot(3).set(new ItemStack(Util.guns.get(0)));
		OldGuns.channel.sendToServer(new UpdateGunShowing(Util.guns.get(0).getRegistryName().toString(), false));
	}

	@Override
	protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().textureManager.bind(MOLDING_PARTS_GUI);
		this.blit(p_230450_1_, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		this.blit(p_230450_1_, this.leftPos + 175, this.topPos + 35, 196, 35, 21, 22);

		if (metal) {
			Minecraft.getInstance().getItemRenderer().renderGuiItem(new ItemStack(ItemRegistries.mold.get()),
					this.leftPos + 177, this.topPos + 37);
		} else {
			if (container.slots.isEmpty()) {
				Minecraft.getInstance().getItemRenderer().renderGuiItem(new ItemStack(Items.OAK_PLANKS),
						this.leftPos + 177, this.topPos + 37);
			} else {
				Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(Minecraft.getInstance().player,
						new ItemStack(Items.OAK_PLANKS), this.leftPos + 177, this.topPos + 37);
				Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font,
						new ItemStack(Items.OAK_PLANKS, menu.datam.get(1)), this.leftPos + 177 + (int) ClientProxy.tx,
						this.topPos + 37 + (int) ClientProxy.ty);
			}
		}

	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		super.renderLabels(matrixStack, x, y);
		this.renderTooltip(matrixStack, x - this.leftPos, y - this.topPos);
		if (metal) {
			for (ItemSlot slot : metalitems) {
				if (slot.isHovered()) {
					this.renderTooltip(matrixStack, slot.item, x - this.leftPos, y - this.topPos);
				}
			}
		} else {
			for (ItemSlot slot : wooditems) {
				if (slot.isHovered()) {
					this.renderTooltip(matrixStack, slot.item, x - this.leftPos, y - this.topPos);
				}
			}
		}
	}

	public void filterItemsFor(ItemGun gun) {
		if (metal) {
			for (ItemSlot is : metalitems) {
				if (!(is.item.getItem() == gun.getBarrel() || is.item.getItem() == gun.getBodyMetal())) {
					is.active = false;
					is.visible = false;
				}
			}
		} else {
			for (ItemSlot is : wooditems) {
				if (!(is.item.getItem() == gun.getBodyWood())) {
					is.active = false;
					is.visible = false;
				}
			}
		}
	}

	public void makeMetalItemVisible() {
		for (ItemSlot is : metalitems) {
			if (!is.visible) {
				is.visible = true;
			}
			if (!is.active) {
				is.active = true;
			}
		}
	}

	public void makeWoodItemVisible() {
		for (ItemSlot is : wooditems) {
			if (!is.visible) {
				is.visible = true;
			}
			if (!is.active) {
				is.active = true;
			}
		}
	}

	public void makeWoodItems(boolean make) {
		for (ItemSlot is : wooditems) {
			is.active = make;
			is.visible = make;
		}
	}

	public void makeMetalItems(boolean make) {
		for (ItemSlot is : metalitems) {
			is.active = make;
			is.visible = make;
		}
		metal = make;
	}

	public abstract static class ItemSlot extends AbstractButton {
		public ItemStack item;
		Slot slot, slotr, slots;
		public boolean clicked = false;

		public ItemSlot(int x, int y, Item item, Slot slot, Slot slotr, Slot slots) {
			super(x, y, 21, 14, new TranslationTextComponent(""));
			this.item = item == null ? ItemStack.EMPTY : new ItemStack(item);
			this.slot = slot;
			this.slotr = slotr;
			this.slots = slots;
		}

		@Override
		public void render(MatrixStack matrix, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
			if (visible) {
				Minecraft.getInstance().getTextureManager().bind(MOLDING_PARTS_GUI);
				RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				int y = 166;
				if (this.isMouseOver(p_230431_2_, p_230431_3_)) {
					y += 15;
					this.isHovered = true;
				} else {
					this.isHovered = false;
				}
				if (clicked) {
					y += 15;
				}

				this.blit(matrix, this.x, this.y, 0, y, this.width, this.height);
				Minecraft.getInstance().getItemRenderer().renderGuiItem(this.item, this.x + 2, this.y);
			}
		}

		@Override
		public void onRelease(double p_231000_1_, double p_231000_3_) {
			clicked = false;
		}
	}
}
