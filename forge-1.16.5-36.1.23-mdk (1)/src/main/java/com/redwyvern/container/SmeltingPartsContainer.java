package com.redwyvern.container;

import java.util.ArrayList;

import com.redwyvern.items.GunPartMold;
import com.redwyvern.registries.BlockRegistries;
import com.redwyvern.registries.ContainerRegistries;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.tileentities.data.SmeltingPartsData;
import com.redwyvern.tileentities.data.SmeltingPartsSlotHandler;
import com.redwyvern.util.Util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;

public class SmeltingPartsContainer extends Container {

	public SmeltingPartsData data;
	private final IWorldPosCallable worldPosCallable;
	private PlayerEntity player;
	public ItemSlot rs, fs, is, ms, ss;
	public SmeltingPartsSlotHandler msh;
	public ArrayList<Item> items = new ArrayList<Item>();
	public IInventory inv = new IInventory() {

		ItemStackHandler handler = new ItemStackHandler();

		@Override
		public boolean isEmpty() {
			return handler.getStackInSlot(0).isEmpty();
		}

		@Override
		public void clearContent() {
			handler.setStackInSlot(0, ItemStack.EMPTY);

		}

		@Override
		public int getContainerSize() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public ItemStack getItem(int p_70301_1_) {
			// TODO Auto-generated method stub
			return handler.getStackInSlot(0);
		}

		@Override
		public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
			if (p_70298_2_ < 0)
				throw new IllegalArgumentException("count should be >= 0:" + p_70298_2_);
			return handler.extractItem(0, p_70298_2_, false);
		}

		@Override
		public ItemStack removeItemNoUpdate(int p_70304_1_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
			handler.setStackInSlot(0, p_70299_2_);

		}

		@Override
		public void setChanged() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean stillValid(PlayerEntity p_70300_1_) {
			// TODO Auto-generated method stub
			return true;
		}
	};

	public SmeltingPartsContainer(int id, PlayerInventory inv) {
		this(id, inv, IWorldPosCallable.NULL, new SmeltingPartsData(),
				SmeltingPartsSlotHandler.createForClientSideContainer(5));
	}

	public SmeltingPartsContainer(int id, PlayerInventory pi, IWorldPosCallable worldPosCallable,
			SmeltingPartsData data, SmeltingPartsSlotHandler msh) {
		super(ContainerRegistries.smelting_container.get(), id);
		this.worldPosCallable = worldPosCallable;
		this.player = pi.player;
		this.data = data;
		this.msh = msh;

		this.addSlot(rs = new ItemSlot(msh, 0, 115, 34) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void check() {
				// TODO Auto-generated method stub

			}
		});

		this.addSlot(is = new ItemSlot(msh, 1, 64, 20) {

			@Override
			public boolean mayPlace(ItemStack stack) {
				return ss.hasItem() ? (Util.isIron(ms.getItem().getOrCreateTag().getString("res_loc"))
						? stack.getItem() == Items.IRON_INGOT
						: stack.getItem() == ItemRegistries.steel_ingot.get()) : false;
			}

			@Override
			public void check() {
				// TODO Auto-generated method stub

			}

		});

		this.addSlot(ms = new ItemSlot(msh, 2, 64, 50) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				// TODO Auto-generated method stub
				return stack.getItem() instanceof GunPartMold;
			}

			@Override
			public int getMaxStackSize() {
				// TODO Auto-generated method stub
				return 1;
			}

			@Override
			public void check() {

			}

			@Override
			public void setChanged() {
				super.setChanged();
				if (hasItem()) {
					String rs = getItem().getOrCreateTag().getString("res_loc");
					if (rs != "" && rs != null) {
						ss.set(new ItemStack(Util.isIron(rs) ? Items.IRON_INGOT : ItemRegistries.steel_ingot.get(),
								Util.getIngotsFor(rs)));
					}
				} else {
					ss.set(ItemStack.EMPTY);
				}
			}
		});

		this.addSlot(fs = new ItemSlot(msh, 3, 38, 50) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				// TODO Auto-generated method stub
				return ForgeHooks.getBurnTime(stack) > 0;// stack.getItem() == Items.COAL || stack.getItem() ==
															// Items.CHARCOAL;
			}

			@Override
			public void check() {
				// TODO Auto-generated method stub

			}
		});
		this.addSlot(ss = new ItemSlot(inv, 4, 14, 20) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mayPickup(PlayerEntity playerIn) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void check() {
				// TODO Auto-generated method stub

			}
		});

		for (int y1 = 0; y1 < 3; ++y1) {
			for (int x1 = 0; x1 < 9; ++x1) {
				this.addSlot(new Slot(player.inventory, x1 + y1 * 9 + 9, 8 + x1 * 18, 84 + y1 * 18));
			}
		}

		for (int x1 = 0; x1 < 9; ++x1) {
			this.addSlot(new Slot(player.inventory, x1, 8 + x1 * 18, 142));
		}

		this.addDataSlots(data);
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return super.stillValid(worldPosCallable, playerIn, BlockRegistries.smelting_parts_block.get());
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {

		Slot slot = this.getSlot(index);
		ItemStack stack = slot.getItem();
		ItemStack e = ItemStack.EMPTY;

		if (slot != null && slot.hasItem()) {
			e = stack.copy();
			if (index == 0) {
				if (!this.moveItemStackTo(stack, 5, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(stack, e);
			} else if (index != 1 && index != 2 && index != 3) {
				if (stack.getItem() == Items.IRON_INGOT || stack.getItem() == ItemRegistries.steel_ingot.get()) {
					if (!this.moveItemStackTo(stack, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (stack.getItem() instanceof GunPartMold) {
					if (!this.moveItemStackTo(stack, 2, 3, false)) {
						return ItemStack.EMPTY;
					}
				} else if (ForgeHooks.getBurnTime(
						stack) > 0/* stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL */) {
					if (!this.moveItemStackTo(stack, 3, 4, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.moveItemStackTo(stack, 5, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (stack.getCount() == e.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, stack);
		}
		return ItemStack.EMPTY;
	}

}
