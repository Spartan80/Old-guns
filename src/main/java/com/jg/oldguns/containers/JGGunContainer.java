package com.jg.oldguns.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public abstract class JGGunContainer extends Container {

	protected ItemStack stack;
	protected PlayerEntity player;
	protected GunInventory inv;

	public JGGunContainer(ContainerType<?> p_i50105_1_, PlayerInventory pi, int id, int invsize) {
		super(p_i50105_1_, id);

		this.player = pi.player;
		this.stack = this.player.getMainHandItem();

		inv = new GunInventory(invsize, (x) -> true) {
			@Override
			public void setChanged() {
				super.setChanged();
				JGGunContainer.this.slotsChanged(this);
			}
		};
	}

	protected void addPlayerSlots(PlayerInventory pi) {
		for (int y1 = 0; y1 < 3; ++y1) {
			for (int x1 = 0; x1 < 9; ++x1) {
				this.addSlot(new Slot(pi, x1 + y1 * 9 + 9, 8 + x1 * 18, 84 + y1 * 18));
			}
		}

		for (int x1 = 0; x1 < 9; ++x1) {
			this.addSlot(new Slot(pi, x1, 8 + x1 * 18, 142));
		}

		broadcastChanges();
	}

	protected void saveData() {
		stack.getOrCreateTag().put("attachments", inv.handler.serializeNBT());
		inv.handler.deserializeNBT(stack.getOrCreateTag().getCompound("attachments"));
	}

	protected void loadData() {
		inv.handler.deserializeNBT(stack.getOrCreateTag().getCompound("attachments"));
		stack.getOrCreateTag().put("attachments", inv.handler.serializeNBT());
	}

}
