package com.jg.oldguns.containers;

import com.jg.oldguns.registries.BlockRegistries;
import com.jg.oldguns.registries.ContainerRegistries;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GunPartsContainer extends AbstractContainerMenu {

	public GunPartsContainer(int id, Inventory pi) {
		super(ContainerRegistries.GUN_PARTS_CONTAINER.get(), id);

		for (int y1 = 0; y1 < 3; ++y1) {
			for (int x1 = 0; x1 < 9; ++x1) {
				this.addSlot(new Slot(pi, x1 + y1 * 9 + 9, 8 + x1 * 18, 84 + y1 * 18));
			}
		}

		for (int x1 = 0; x1 < 9; ++x1) {
			this.addSlot(new Slot(pi, x1, 8 + x1 * 18, 142));
		}

	}

	@Override
	public ItemStack quickMoveStack(Player p_82846_1_, int p_82846_2_) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean stillValid(Player p_75145_1_) {
		return super.stillValid(ContainerLevelAccess.NULL, p_75145_1_, BlockRegistries.Gun_parts_block.get());
	}

}
