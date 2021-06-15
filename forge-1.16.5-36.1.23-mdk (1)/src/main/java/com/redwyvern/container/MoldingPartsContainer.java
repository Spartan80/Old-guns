package com.redwyvern.container;

import java.util.ArrayList;

import com.redwyvern.items.GunPartMold;
import com.redwyvern.registries.BlockRegistries;
import com.redwyvern.registries.ContainerRegistries;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.tileentities.data.MoldingPartsData;
import com.redwyvern.util.InventoryHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IWorldPosCallable;

public class MoldingPartsContainer extends Container {

	private final IWorldPosCallable worldPosCallable;
	private PlayerEntity player;
	public Slot slot, data, slotS, gunS, slotInteractiveReq;
	public MoldingPartsData datam;
	private final ArrayList<Item> guns = new ArrayList<Item>();
	private final CraftResultInventory result_inventory = new CraftResultInventory();
	private final CraftingInventory refill = new CraftingInventory(this, 4, 1);

	public MoldingPartsContainer(int id, PlayerInventory pi) {
		this(id, pi, IWorldPosCallable.NULL, new MoldingPartsData());
	}

	public MoldingPartsContainer(int id, PlayerInventory pi, IWorldPosCallable worldPosCallable,
			MoldingPartsData datam) {
		super(ContainerRegistries.molding_container.get(), id);
		this.worldPosCallable = worldPosCallable;
		this.player = pi.player;
		this.datam = datam;

		this.addSlot(data = new Slot(result_inventory, 0, 152, 5) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			@Override
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
				crafted(stack);
				return super.onTake(thePlayer, stack);
			}

		});

		this.addSlot(slot = new Slot(refill, 1, 152, 38) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return datam.get(0) == 0 ? stack.getItem() instanceof GunPartMold
						: ItemTags.PLANKS.contains(stack.getItem());// Util.isValidWood(stack.getItem());
			}

			@Override
			public void setChanged() {
				super.setChanged();
				if (hasItem() && slotS.hasItem()) {
					if (getItem().getItem() instanceof GunPartMold) {
						data.set(new ItemStack(ItemRegistries.mold.get()));
						GunPartMold mold = ((GunPartMold) data.getItem().getItem());
						mold.setNBTMoldName(data.getItem(), slotS.getItem().getItem().getDescriptionId());
						mold.setNBTPart(data.getItem(), slotS.getItem().getItem().getRegistryName().toString());
					} else {
						if (getItem().getCount() >= datam.get(1)) {
							data.set(slotS.getItem().copy());
						}
					}
				} else {
					data.set(ItemStack.EMPTY);
				}
			}

		});

		this.addSlot(slotS = new Slot(refill, 2, 152, 62) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			@Override
			public boolean mayPickup(PlayerEntity playerIn) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		this.addSlot(gunS = new Slot(refill, 3, 62, 52) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}

			@Override
			public boolean mayPickup(PlayerEntity playerIn) {
				// TODO Auto-generated method stub
				return false;
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

		this.addDataSlots(datam);
	}

	@Override
	protected boolean moveItemStackTo(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) {
		boolean flag = false;
		int i = p_75135_2_;
		if (p_75135_4_) {
			i = p_75135_3_ - 1;
		}

		if (p_75135_1_.isStackable()) {
			while (!p_75135_1_.isEmpty()) {
				if (p_75135_4_) {
					if (i < p_75135_2_) {
						break;
					}
				} else if (i >= p_75135_3_) {
					break;
				}

				Slot slot = this.slots.get(i);
				ItemStack itemstack = slot.getItem();
				if (!itemstack.isEmpty() && consideredTheSameItem(p_75135_1_, itemstack)) {
					int j = itemstack.getCount() + p_75135_1_.getCount();
					int maxSize = Math.min(slot.getMaxStackSize(), p_75135_1_.getMaxStackSize());
					if (j <= maxSize) {
						p_75135_1_.setCount(0);
						itemstack.setCount(j);
						slot.setChanged();
						flag = true;
					} else if (itemstack.getCount() < maxSize) {
						p_75135_1_.shrink(maxSize - itemstack.getCount());
						itemstack.setCount(maxSize);
						slot.setChanged();
						flag = true;
					}
				}

				if (p_75135_4_) {
					--i;
				} else {
					++i;
				}
			}
		}

		if (!p_75135_1_.isEmpty()) {
			if (p_75135_4_) {
				i = p_75135_3_ - 1;
			} else {
				i = p_75135_2_;
			}

			while (true) {
				if (p_75135_4_) {
					if (i < p_75135_2_) {
						break;
					}
				} else if (i >= p_75135_3_) {
					break;
				}

				Slot slot1 = this.slots.get(i);
				ItemStack itemstack1 = slot1.getItem();
				if (itemstack1.isEmpty() && slot1.mayPlace(p_75135_1_)) {
					if (p_75135_1_.getCount() > slot1.getMaxStackSize()) {
						slot1.set(p_75135_1_.split(slot1.getMaxStackSize()));
					} else {
						slot1.set(p_75135_1_.split(p_75135_1_.getCount()));
					}

					slot1.setChanged();
					flag = true;
					break;
				}

				if (p_75135_4_) {
					--i;
				} else {
					++i;
				}
			}
		}

		return flag;
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {

		Slot slot = this.getSlot(index);
		ItemStack stack = slot.getItem();
		ItemStack e = ItemStack.EMPTY;

		if (!stack.isEmpty() && slot != null) {
			e = stack.copy();

			if (index == 0) {
				if (!this.moveItemStackTo(stack, 4, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stack, e);
			} else if (index != 1) {
				if (stack.getItem() instanceof GunPartMold || ItemTags.PLANKS.contains(stack.getItem())) {
					if (!moveItemStackTo(stack, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
					slot.onQuickCraft(stack, e);
				}
			} else if (!moveItemStackTo(stack, 4, 39, false)) {
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

	public void crafted(ItemStack stack) {
		if (datam.get(0) == 0) {
			slot.remove(1);
		} else {
			slot.remove(datam.get(1));
		}
	}

	@Override
	public void removed(PlayerEntity playerIn) {
		if (playerIn.level.isClientSide)
			return;

		if (slot.hasItem()) {
			InventoryHelper.addStackOrDrop(slot.getItem(), playerIn);
		}
		super.removed(playerIn);
	}

	@Override
	public boolean stillValid(PlayerEntity p_75145_1_) {
		// TODO Auto-generated method stub
		return super.stillValid(worldPosCallable, p_75145_1_, BlockRegistries.molding_parts_block.get());
	}
}
