package com.jg.oldguns.containers;

import com.jg.oldguns.guns.ItemBullet;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.registries.ContainerRegistries;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.ServerUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class MagContainer extends JGGunContainer {

	public MagContainer(int id, Inventory pi) {
		super(ContainerRegistries.MAG_CONTAINER.get(), pi, id, 1);

		addSlot(new Slot(inv, 0, 80, 49) {
			@Override
			public boolean mayPlace(ItemStack p_75214_1_) {
				ItemStack stack = player.getMainHandItem();
				if (stack.getItem() instanceof ItemMag && p_75214_1_.getItem() instanceof ItemBullet) {
					ItemMag mag = (ItemMag) stack.getItem();
					ItemBullet bullet = (ItemBullet) p_75214_1_.getItem();
					if (mag.getAcceptedSize().equals(bullet.getSize())) {
						return super.mayPlace(p_75214_1_);
					}
				}
				return false;
			}

			@Override
			public int getMaxStackSize(ItemStack p_178170_1_) {
				System.out.println(((ItemMag) stack.getItem()).getMaxAmmo());
				return ((ItemMag) stack.getItem()).getMaxAmmo();
			}

			@Override
			public int getMaxStackSize() {
				System.out.println(((ItemMag) stack.getItem()).getMaxAmmo());
				return ((ItemMag) stack.getItem()).getMaxAmmo();
			}
		});

		addPlayerSlots(pi);

		if (!player.level.isClientSide) {
			inv.setItem(0,
					new ItemStack(
							ForgeRegistries.ITEMS.getValue(
									new ResourceLocation(stack.getOrCreateTag().getString(Constants.MAGBULLETPATH))),
							ServerUtils.getBullets(stack)));
			ServerUtils.setBullets(player.getMainHandItem(), inv.getItem(0).getCount());
		}
	}

	@Override
	public void removed(Player p_75134_1_) {
		if (!p_75134_1_.level.isClientSide) {
			ServerUtils.setBullets(player.getMainHandItem(), inv.getItem(0).getCount());
			if (!inv.getItem(0).isEmpty()) {
				stack.getOrCreateTag().putString(Constants.MAGBULLETPATH,
						inv.getItem(0).getItem().getRegistryName().toString());
			}
		}
		super.removed(p_75134_1_);
	}
	
	@Override
	public void slotsChanged(Container p_75130_1_) {
		super.slotsChanged(p_75130_1_);
	}

	@Override
	public boolean stillValid(Player p_75145_1_) {
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		Slot slot = this.getSlot(index);
		ItemStack stack = slot.getItem();
		ItemStack e = ItemStack.EMPTY;

		if (slot != null && slot.hasItem()) {
			e = stack.copy();
			if (index == 0) {
				if (!this.moveItemStackTo(stack, 1, 37, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(stack, e);
			} else if (stack.getItem() instanceof ItemBullet) {
				if (!this.moveItemStackTo(stack, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stack, 1, 37, false)) {
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
