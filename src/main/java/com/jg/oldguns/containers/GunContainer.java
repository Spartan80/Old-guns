package com.jg.oldguns.containers;

import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.Scope;
import com.jg.oldguns.registries.ContainerRegistries;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class GunContainer extends JGGunContainer {

	public GunContainer(int id, PlayerInventory pi) {
		super(ContainerRegistries.GUN_CONTAINER.get(), pi, id, 5);

		ItemGun gun = (ItemGun) stack.getItem();

		// Scope slot
		this.addSlot(new Slot(inv, 0, 74, 27) {
			@Override
			public boolean mayPlace(ItemStack p_75214_1_) {
				boolean temp = false;
				System.out.println(p_75214_1_.getItem().getRegistryName().toString());
				if (p_75214_1_.getItem() instanceof Scope) {
					System.out.println(((Scope) p_75214_1_.getItem()).getGunId());
					if (((Scope) p_75214_1_.getItem()).getGunId().equals("any") || ((Scope) p_75214_1_.getItem()).getGunId().equals(gun.getGunId())) {
						temp = true;
					}
				}
				return gun.hasScope() && temp;
			}

			@Override
			public int getMaxStackSize() {
				return 1;
			}

			@Override
			public int getMaxStackSize(ItemStack p_178170_1_) {
				return 1;
			}

		});

		// Stock slot
		this.addSlot(new GunPartSlot(inv, 1, 44, 51) {

			@Override
			public boolean mayPlace(ItemStack stack) {
				return super.mayPlace(stack) && Util.canEquip(stack, getSlotIndex()) && gun.canModifyStock();
			}

			@Override
			public boolean mayPickup(PlayerEntity p_82869_1_) {
				ItemStack carried = player.inventory.getCarried();
				return super.mayPickup(p_82869_1_) && getSlot(2).hasItem() && Util.canEquip(carried, getSlotIndex())
						&& gun.canModifyStock();
			}
		});

		// Body slot
		this.addSlot(new GunPartSlot(inv, 2, 74, 51) {

			@Override
			public boolean mayPlace(ItemStack p_75214_1_) {
				return super.mayPlace(p_75214_1_) && gun.canModifyBody() && Util.canEquip(p_75214_1_, getSlotIndex());
			}

			@Override
			public boolean mayPickup(PlayerEntity p_82869_1_) {
				ItemStack carried = player.inventory.getCarried();
				return gun.canModifyBody() && Util.canEquip(carried, getSlotIndex());
			}
		});

		// Barrel slot
		this.addSlot(new GunPartSlot(inv, 3, 104, 51) {

			@Override
			public boolean mayPlace(ItemStack p_75214_1_) {
				return super.mayPlace(p_75214_1_) && gun.canModifyBarrel() && Util.canEquip(p_75214_1_, getSlotIndex());
			}

			@Override
			public boolean mayPickup(PlayerEntity p_82869_1_) {
				ItemStack carried = player.inventory.getCarried();
				return super.mayPickup(p_82869_1_) && getSlot(2).hasItem() && Util.canEquip(carried, getSlotIndex())
						&& gun.canModifyBarrel();
			}
		});

		// Barrel mouth slot
		this.addSlot(new GunPartSlot(inv, 4, 134, 51) {

			@Override
			public boolean mayPlace(ItemStack p_75214_1_) {
				return super.mayPlace(p_75214_1_) && gun.canModifyBarrelMouth();
			}

			@Override
			public boolean mayPickup(PlayerEntity p_82869_1_) {
				return super.mayPickup(p_82869_1_) && getSlot(3).hasItem() && gun.canModifyBarrelMouth();
			}
		});

		addPlayerSlots(pi);
		if (!player.level.isClientSide) {

			loadData();

			// Stock
			if (gun.getNBTStock(stack) == "") {
				inv.setItem(1, new ItemStack(gun.getStock()));
				System.out.println("nothing");
			}

			// Body
			if (gun.getNBTBody(stack) == "") {
				inv.setItem(2, new ItemStack(gun.getBody()));
				System.out.println("nothing");
			}

			// Barrel
			if (gun.getNBTBarrel(stack) == "") {
				inv.setItem(3, new ItemStack(gun.getBarrel()));
				System.out.println("nothing");
			}
		}
	}

	@Override
	public boolean stillValid(PlayerEntity p_75145_1_) {
		return true;
	}

	@Override
	public void slotsChanged(IInventory p_75130_1_) {
		super.slotsChanged(p_75130_1_);
		if (!player.level.isClientSide) {
			ItemGun gun = (ItemGun) stack.getItem();

			if (inv.getItem(0).isEmpty()) {
				ServerUtils.setScope(stack, 0);
				ServerUtils.setScopeGunId(stack, "empty");
				ServerUtils.setScopePath(stack, "");
			} else {
				ServerUtils.setScope(stack, ((Scope) inv.getItem(0).getItem()).getScopePercent());
				ServerUtils.setScopeGunId(stack, ((Scope) inv.getItem(0).getItem()).getGunId());
				ServerUtils.setScopePath(stack, inv.getItem(0).getItem().getRegistryName().toString());
			}

			if (inv.getItem(1).isEmpty()) {
				ServerUtils.setStock(stack, Constants.EMPTY);
			} else if ((GunPart) inv.getItem(1).getItem() == (GunPart) gun.getStock()) {
				ServerUtils.setStock(stack, "");
			} else {
				ServerUtils.setStock(stack, inv.getItem(1).getItem().getRegistryName().toString());
			}

			if (inv.getItem(2).isEmpty()) {
				ServerUtils.setBody(stack, Constants.EMPTY);
			} else if ((GunPart) inv.getItem(2).getItem() == (GunPart) gun.getBody()) {
				ServerUtils.setBody(stack, "");
			} else {
				ServerUtils.setBody(stack, inv.getItem(2).getItem().getRegistryName().toString());
			}

			if (inv.getItem(3).isEmpty()) {
				System.out.println("Empty stack");
				ServerUtils.setBarrel(stack, Constants.EMPTY);
			} else if ((GunPart) inv.getItem(3).getItem() == (GunPart) gun.getBarrel()) {
				System.out.println("default part");
				ServerUtils.setBarrel(stack, "");
			} else {
				System.out.println("custom part: " + inv.getItem(3).getItem().getRegistryName().toString());
				ServerUtils.setBarrel(stack, inv.getItem(3).getItem().getRegistryName().toString());
				// OldGuns.channel.sendTo(new StartHitmarkerMessage(),
				// ((ServerPlayerEntity)shooter).connection.connection,
				// NetworkDirection.PLAY_TO_CLIENT);
			}

			System.out.println(ServerUtils.getBarrel(stack));

			if (inv.getItem(4).isEmpty()) {
				ServerUtils.setBarrelMouth(stack, Constants.EMPTY);
			} else {
				ServerUtils.setBarrelMouth(stack, inv.getItem(4).getItem().getRegistryName().toString());
			}

		}
		broadcastChanges();

	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int index) {
		Slot slot = this.getSlot(index);
		ItemStack stack = slot.getItem();
		ItemStack e = ItemStack.EMPTY;

		if (slot != null && slot.hasItem()) {
			e = stack.copy();
			if (index >= 0 && index <= 4) {
				if (!this.moveItemStackTo(stack, 5, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(stack, e);
			} else if (stack.getItem() instanceof GunPart) {
				GunPart part = (GunPart) stack.getItem();
				switch (part.getGunSlot()) {
				case 1:
					if (getSlot(1).mayPlace(stack)) {
						ItemStack targetstack = getSlot(1).getItem();
						getSlot(1).set(stack);
						slot.set(targetstack);
					} else {
						return ItemStack.EMPTY;
					}
					break;
				case 2:
					if (getSlot(2).mayPlace(stack)) {
						ItemStack targetstack = getSlot(2).getItem();
						getSlot(2).set(stack);
						slot.set(targetstack);
					} else {
						return ItemStack.EMPTY;
					}
				case 3:
					if (getSlot(3).mayPlace(stack)) {
						ItemStack targetstack = getSlot(3).getItem();
						getSlot(3).set(stack);
						slot.set(targetstack);
					} else {
						return ItemStack.EMPTY;
					}
				case 4:
					if (!this.moveItemStackTo(stack, 4, 5, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (stack.getItem() instanceof Scope) {
				if (!this.moveItemStackTo(stack, 0, 1, false)) {
					return ItemStack.EMPTY;
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

	@Override
	public void removed(PlayerEntity p_75134_1_) {
		if (!p_75134_1_.level.isClientSide) {
			saveData();
			System.out.println("Override Barrel: " + ServerUtils.getBarrel(stack) + " Body: "
					+ ServerUtils.getBody(stack) + " Stock: " + ServerUtils.getStock(stack));
		} else {
			if (ModelHandler.INSTANCE.cache.containsKey(stack.getOrCreateTag().getString(Constants.ID))) {
				ModelHandler.INSTANCE.cache.get(stack.getOrCreateTag().getString(Constants.ID)).reconstruct();
				System.out.println("reconstructing");
			}
			System.out.println("Override Barrel: " + ServerUtils.getBarrel(stack) + " Body: "
					+ ServerUtils.getBody(stack) + " Stock: " + ServerUtils.getStock(stack));
		}
		broadcastChanges();
		super.removed(p_75134_1_);
	}

}
