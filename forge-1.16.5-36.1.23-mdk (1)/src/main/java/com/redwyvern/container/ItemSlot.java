package com.redwyvern.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public abstract class ItemSlot extends Slot {
	private boolean enabled;

	public ItemSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		enabled = true;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public abstract void check();

	public void checkOnMatrixChanged() {

	};

}
