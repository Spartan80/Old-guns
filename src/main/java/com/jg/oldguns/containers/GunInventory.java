package com.jg.oldguns.containers;

import java.util.function.Predicate;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class GunInventory implements IInventory {
	private Predicate<PlayerEntity> canPlayerAccessInventoryLambda = x -> true;
	public final ItemStackHandler handler;

	public GunInventory(int size) {
		this.handler = new ItemStackHandler(size);
	}

	public GunInventory(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda) {
		this.handler = new ItemStackHandler(size);
		this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
	}

	public static GunInventory createForTileEntity(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda) {
		return new GunInventory(size, canPlayerAccessInventoryLambda);
	}

	/**
	 * Use this constructor to create a FurnaceZoneContents which is not linked to
	 * any parent TileEntity; i.e. is used by the client side container: * does not
	 * permanently store items * cannot ask questions/provide notifications to a
	 * parent TileEntity
	 * 
	 * @param size the max number of ItemStacks in the inventory
	 * @return the new ChestContents
	 */
	public static GunInventory createForClientSideContainer(int size) {
		return new GunInventory(size);
	}

	@Override
	public void setChanged() {

	}

	@Override
	public void startOpen(PlayerEntity p_174889_1_) {

	}

	@Override
	public void stopOpen(PlayerEntity p_174886_1_) {

	}

	@Override
	public void clearContent() {
		for (int i = 0; i < handler.getSlots(); ++i) {
			handler.setStackInSlot(i, ItemStack.EMPTY);
		}

	}

	@Override
	public int getContainerSize() {

		return handler.getSlots();
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < handler.getSlots(); ++i) {
			if (!handler.getStackInSlot(i).isEmpty())
				return false;
		}
		return true;
	}

	@Override
	public ItemStack getItem(int index) {
		return handler.getStackInSlot(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		if (count < 0)
			throw new IllegalArgumentException("count should be >= 0:" + count);
		return handler.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		int maxPossibleItemStackSize = handler.getSlotLimit(index);
		return handler.extractItem(index, maxPossibleItemStackSize, false);
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		handler.setStackInSlot(index, stack);

	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return canPlayerAccessInventoryLambda.test(player);
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return handler.isItemValid(index, stack);
	}

	public CompoundNBT serializeNBT() {
		return handler.serializeNBT();
	}

	/**
	 * Fills the chest contents from the nbt; resizes automatically to fit. (used to
	 * load the contents from disk)
	 * 
	 * @param nbt
	 */
	public void deserializeNBT(CompoundNBT nbt) {
		handler.deserializeNBT(nbt);

	}
}
