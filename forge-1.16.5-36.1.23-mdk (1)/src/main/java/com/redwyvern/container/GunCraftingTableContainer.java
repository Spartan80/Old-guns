package com.redwyvern.container;

import com.redwyvern.registries.BlockRegistries;
import com.redwyvern.registries.ContainerRegistries;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;

public class GunCraftingTableContainer extends Container {

	private final IWorldPosCallable worldPosCallable;
	private PlayerEntity player;
	private ItemSlot r1, r2, rr;
	public int index = 0;
	private final CraftingInventory refill = new CraftingInventory(this, 7, 1);
	public ItemStack result = ItemStack.EMPTY;

	public GunCraftingTableContainer(int id, PlayerInventory playerInventory) {
		this(id, playerInventory, IWorldPosCallable.NULL);
	}

	public GunCraftingTableContainer(int id, PlayerInventory pi, IWorldPosCallable worldPosCallable) {
		super(ContainerRegistries.gun_crafting_container.get(), id);
		this.worldPosCallable = worldPosCallable;
		this.player = pi.player;

		// Show gun
		this.addSlot(new ItemSlot(this.refill, 0, 64, 16) {

			@Override
			public boolean mayPlace(ItemStack p_75214_1_) {
				return false;
			}

			@Override
			public boolean mayPickup(PlayerEntity p_82869_1_) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void check() {
				// TODO Auto-generated method stub

			}
		});

		// Show gunparts/////////////////////////////////
		this.addSlot(new ItemSlot(this.refill, 1, 44, 42) {
			@Override
			public boolean mayPlace(ItemStack stack) {
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

		this.addSlot(new ItemSlot(this.refill, 2, 64, 42) {
			@Override
			public boolean mayPlace(ItemStack stack) {
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

		this.addSlot(new ItemSlot(this.refill, 3, 84, 42) {
			@Override
			public boolean mayPlace(ItemStack stack) {
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

	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
		return ItemStack.EMPTY;

	}

	@Override
	public boolean stillValid(PlayerEntity p_75145_1_) {
		// TODO Auto-generated method stub
		return super.stillValid(worldPosCallable, p_75145_1_, BlockRegistries.gun_crafting_block.get());
	}

}
