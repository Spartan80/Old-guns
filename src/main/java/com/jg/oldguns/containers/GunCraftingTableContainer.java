package com.jg.oldguns.containers;

import com.jg.oldguns.registries.BlockRegistries;
import com.jg.oldguns.registries.ContainerRegistries;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;

public class GunCraftingTableContainer extends Container {

	public GunCraftingTableContainer(int id, PlayerInventory inv) {
		super(ContainerRegistries.GUN_CRAFTING_CONTAINER.get(), id);

		for (int y1 = 0; y1 < 3; ++y1) {
			for (int x1 = 0; x1 < 9; ++x1) {
				this.addSlot(new Slot(inv, x1 + y1 * 9 + 9, 8 + x1 * 18, 84 + y1 * 18));
			}
		}

		for (int x1 = 0; x1 < 9; ++x1) {
			this.addSlot(new Slot(inv, x1, 8 + x1 * 18, 142));
		}

	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(PlayerEntity p_75145_1_) {
		return super.stillValid(IWorldPosCallable.NULL, p_75145_1_, BlockRegistries.Gun_crafting_block.get());
	}

}
