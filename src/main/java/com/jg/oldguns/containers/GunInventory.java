package com.jg.oldguns.containers;

import java.util.function.Predicate;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class GunInventory implements Container {
	private Predicate<Player> canPlayerAccessInventoryLambda = x -> true;
	public final ItemStackHandler handler;

	public GunInventory(int size) {
		this.handler = new ItemStackHandler(size);
	}

	public GunInventory(int size, Predicate<Player> canPlayerAccessInventoryLambda) {
		this.handler = new ItemStackHandler(size);
		this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
	}

	public static GunInventory createForTileEntity(int size, Predicate<Player> canPlayerAccessInventoryLambda) {
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
	public void startOpen(Player p_174889_1_) {

	}

	@Override
	public void stopOpen(Player p_174886_1_) {

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
	public boolean stillValid(Player player) {
		return canPlayerAccessInventoryLambda.test(player);
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return handler.isItemValid(index, stack);
	}

	public CompoundTag serializeNBT() {
		return handler.serializeNBT();
	}

	/**
	 * Fills the chest contents from the nbt; resizes automatically to fit. (used to
	 * load the contents from disk)
	 * 
	 * @param nbt
	 */
	public void deserializeNBT(CompoundTag nbt) {
		handler.deserializeNBT(nbt);

	}
}
