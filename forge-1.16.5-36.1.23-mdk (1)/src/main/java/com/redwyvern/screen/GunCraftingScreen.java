package com.redwyvern.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.redwyvern.container.GunCraftingTableContainer;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.network.GunCraftMessage;
import com.redwyvern.network.UpdateGunShowing;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.util.InventoryHelper;
import com.redwyvern.util.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GunCraftingScreen extends ContainerScreen<GunCraftingTableContainer> {

	private static final ResourceLocation VOID_CRAFTING_GUI = new ResourceLocation(
			"oldguns:textures/gui/container/crafting_gui_void.png");
	private GunCraftingTableContainer container;
	private int actgun = 0;
	public boolean craft = false;
	public ItemShow gs;
	public CraftButton cb;
	public ArrayList<CustomButton> buttons = new ArrayList<CustomButton>(4);

	public GunCraftingScreen(GunCraftingTableContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		container = screenContainer;
	}

	@Override
	protected void init() {
		super.init();
		int i = this.leftPos;
		int j = this.topPos;

		//////////////// Left gun

		buttons.add(new CustomButton(i + 48, j + 14, "<", i, j) {

			@Override
			public void onPress() {
				actgun = (actgun - 1 + Util.guns.size()) % Util.guns.size();
				container.getSlot(0).set(new ItemStack(Util.guns.get(actgun)));
				putGunAndParts(Util.guns.get(actgun));
				OldGuns.channel
						.sendToServer(new UpdateGunShowing(Util.guns.get(actgun).getRegistryName().toString(), true));
				container.broadcastChanges();

			}

		});

		//////////////// Right gun

		buttons.add(new CustomButton(i + 85, j + 14, ">", i, j) {

			@Override
			public void onPress() {
				actgun = (actgun + 1) % Util.guns.size();
				putGunAndParts(Util.guns.get(actgun));
				OldGuns.channel
						.sendToServer(new UpdateGunShowing(Util.guns.get(actgun).getRegistryName().toString(), true));
				container.broadcastChanges();
			}
		});

		///////////////// Setting buttons

		this.addButton(buttons.get(0));

		this.addButton(buttons.get(1));

		this.addButton(cb = new CraftButton(i + 119, j + 31, this.inventory, container.getSlot(0), VOID_CRAFTING_GUI));
		putGunAndParts(ItemRegistries.pirate_pistol.get());
		OldGuns.channel.sendToServer(
				new UpdateGunShowing(ItemRegistries.pirate_pistol.get().getRegistryName().toString(), true));

	}

	public void putGunAndParts(Item gun) {
		ItemGun gunc = (ItemGun) gun;
		container.getSlot(0).set(new ItemStack(gun));
		container.getSlot(1).set(new ItemStack(gunc.getBarrel()));
		container.getSlot(2).set(new ItemStack(gunc.getBodyMetal()));
		container.getSlot(3).set(new ItemStack(gunc.getBodyWood()));
	}

	@Override
	protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().textureManager.bind(VOID_CRAFTING_GUI);
		int i = this.leftPos;
		int j = this.topPos;

		this.blit(p_230450_1_, i, j, 0, 0, this.imageWidth, this.imageHeight);

		if (container.getSlot(0).hasItem()) {
			ItemGun gun = (ItemGun) container.getSlot(0).getItem().getItem();
			int b = InventoryHelper.hasItem(inventory, gun.getBarrel());
			int bm = InventoryHelper.hasItem(inventory, gun.getBodyMetal());
			int bw = InventoryHelper.hasItem(inventory, gun.getBodyWood());
			if (b != -1) {
				this.blit(p_230450_1_, i + 44, j + 42, 200, 0, 16, 16);
			} else {
				this.blit(p_230450_1_, i + 44, j + 42, 218, 0, 16, 16);
			}

			if (bm != -1) {
				this.blit(p_230450_1_, i + 64, j + 42, 200, 0, 16, 16);
			} else {
				this.blit(p_230450_1_, i + 64, j + 42, 218, 0, 16, 16);
			}

			if (bw != -1) {
				this.blit(p_230450_1_, i + 84, j + 42, 200, 0, 16, 16);
			} else {
				this.blit(p_230450_1_, i + 84, j + 42, 218, 0, 16, 16);
			}
		}
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		super.renderLabels(matrixStack, x, y);
		this.renderTooltip(matrixStack, x - leftPos, y - topPos);
	}

	public abstract static class CustomButton extends AbstractButton {

		int firstx, firsty, i, j;

		public CustomButton(int x, int y, String text, int i, int j) {
			super(x, y, 10, 20, new TranslationTextComponent(text));
			firstx = x;
			firsty = y;
			this.i = i;
			this.j = j;

		}

	}

	public static class ItemSlot extends AbstractButton {
		public ItemStack item;
		Slot slot, s1, s2, s3;
		CraftButton button;
		public boolean clicked = false;
		ItemGun gun;

		public ItemSlot(int x, int y, Item item, Slot slot, Slot s1, Slot s2, Slot s3, CraftButton button) {
			super(x, y, 24, 20, new TranslationTextComponent(""));
			this.item = new ItemStack(item);
			this.gun = (ItemGun) item;
			this.slot = slot;
			this.s1 = s1;
			this.s2 = s2;
			this.s3 = s3;
			this.button = button;
		}

		@Override
		public void render(MatrixStack matrix, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
			Minecraft mc = Minecraft.getInstance();
			mc.getTextureManager().bind(VOID_CRAFTING_GUI);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			int y = 166;
			if (this.isHovered()) {
				y += 19;
			}
			if (clicked) {
				y += 19;
			}
			this.blit(matrix, this.x, this.y, 0, y, this.width, this.height);
			mc.getItemRenderer().renderGuiItem(this.item, this.x + 5, this.y);
		}

		@Override
		public void onPress() {
			clicked = true;
			slot.set(ItemStack.EMPTY);
			slot.set(new ItemStack(this.item.getItem()));
			s1.set(ItemStack.EMPTY);
			s1.set(new ItemStack(this.gun.getBarrel()));
			s2.set(ItemStack.EMPTY);
			s2.set(new ItemStack(this.gun.getBodyMetal()));
			s3.set(ItemStack.EMPTY);
			s3.set(new ItemStack(this.gun.getBodyWood()));
			button.item = this.item;
		}

		@Override
		public void onRelease(double p_231000_1_, double p_231000_3_) {
			clicked = false;
		}
	}

	public static class ItemShow extends AbstractButton {
		public ItemStack item;

		public ItemShow(int x, int y, Item item) {
			super(x, y, 20, 20, new TranslationTextComponent(""));
			this.item = new ItemStack(item);
			this.active = false;
		}

		@Override
		public void render(MatrixStack matrix, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
			Minecraft mc = Minecraft.getInstance();
			FontRenderer textrender = mc.font;
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			mc.getItemRenderer().renderGuiItem(this.item, this.x + 5, this.y);
		}

		@Override
		public void onPress() {

		}
	}

	public static class CraftButton extends AbstractButton {
		public ItemStack item;
		public PlayerInventory inv;
		public Slot s;
		public boolean clicked = false;
		public ResourceLocation texture;
		public boolean craft;

		public CraftButton(int x, int y, PlayerInventory inv, Slot s, ResourceLocation texture) {
			super(x, y, 18, 18, new TranslationTextComponent(""));
			this.item = s.getItem();
			this.s = s;
			this.inv = inv;
			this.texture = texture;
			craft = false;
		}

		@Override
		public void render(MatrixStack matrix, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
			if (visible) {
				RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.enableDepthTest();
				Minecraft.getInstance().getTextureManager().bind(this.texture);
				int xp = 177;
				if (this.isMouseOver(p_230431_2_, p_230431_3_)) {
					xp += 19;
					this.isHovered = true;
				} else {
					this.isHovered = false;
				}
				if (clicked) {
					xp += 19;
				}
				this.blit(matrix, this.x, this.y, xp, 64, 18, 18);
			}
		}

		@Override
		public void onPress() {
			if (s.hasItem()) {
				ItemGun gun = (ItemGun) s.getItem().getItem();
				int b = InventoryHelper.hasItem(inv, gun.getBarrel());
				int bm = InventoryHelper.hasItem(inv, gun.getBodyMetal());
				int bw = InventoryHelper.hasItem(inv, gun.getBodyWood());
				if (b != -1 && bm != -1 && bw != -1) {
					OldGuns.channel.sendToServer(new GunCraftMessage(gun.getRegistryName().toString(), bw, bm, b));
					craft = true;
				}
			}
			clicked = true;
		}

		public boolean hasCrafted() {
			return craft;
		}

		@Override
		public void onRelease(double p_231000_1_, double p_231000_3_) {
			clicked = false;
		}

	}

}
