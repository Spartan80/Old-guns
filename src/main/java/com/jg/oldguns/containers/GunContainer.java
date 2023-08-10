package com.jg.oldguns.containers;

import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.handlers.GunModelsHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.registries.ContainerRegistries;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class GunContainer extends JGGunContainer {

	public GunContainer(int id, Inventory pi) {
		super(ContainerRegistries.GUN_CONTAINER.get(), pi, id, 5);

		GunItem gun = (GunItem) stack.getItem();

		// Scope slot
		this.addSlot(new Slot(inv, 0, 74, 27) {
			@Override
			public boolean mayPlace(ItemStack p_75214_1_) {
				boolean temp = false;
				LogUtils.getLogger().info(Utils.getR(p_75214_1_.getItem()).toString());
				/*if (p_75214_1_.getItem() instanceof Scope) {
					LogUtils.getLogger().info(((Scope) p_75214_1_.getItem()).getGunId());
					if (((Scope) p_75214_1_.getItem()).getGunId().equals("any")
							|| ((Scope) p_75214_1_.getItem()).getGunId().equals(gun.getGunId())) {
						temp = true;
					}
				}*/
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
				return super.mayPlace(stack) && Utils.canEquip(stack, getSlotIndex()) 
						&& gun.getStuff().canModifyStock();
			}

			@Override
			public boolean mayPickup(Player p_82869_1_) {
				return false;
			}
		});

		// Body slot
		this.addSlot(new GunPartSlot(inv, 2, 74, 51) {

			@Override
			public boolean mayPlace(ItemStack p_75214_1_) {
				return super.mayPlace(p_75214_1_) && gun.getStuff().canModifyBody() 
						&& Utils.canEquip(p_75214_1_, getSlotIndex());
			}

			@Override
			public boolean mayPickup(Player p_82869_1_) {
				return false;
			}
		});

		// Barrel slot
		this.addSlot(new GunPartSlot(inv, 3, 104, 51) {

			@Override
			public boolean mayPlace(ItemStack p_75214_1_) {
				LogUtils.getLogger().info("ItemStack: " + p_75214_1_.toString() + 
						" canModifyBarrel: " + gun.getStuff().canModifyBarrel() + 
						" canEquip: " + Utils.canEquip(p_75214_1_, getSlotIndex()) + 
						" superMayPlace: " + super.mayPlace(p_75214_1_));
				return super.mayPlace(p_75214_1_) && gun.getStuff().canModifyBarrel() 
						&& Utils.canEquip(p_75214_1_, getSlotIndex());
			}
			
			@Override
			public boolean mayPickup(Player player) {
				return false;
			}
		});

		addPlayerSlots(pi);
		if (!player.level.isClientSide) {

			loadData();

			// Stock
			if (NBTUtils.getStock(stack).equals("")) {
				inv.setItem(1, new ItemStack(gun.getStock()));
			} else {
				inv.setItem(1, new ItemStack(ForgeRegistries.ITEMS
						.getValue(new ResourceLocation(NBTUtils.getStock(stack)))));
				GunModelsHandler.get(stack.getItem().getDescriptionId()).getModel()
					.reconstruct(stack);
			}

			// Body
			if (NBTUtils.getBody(stack).equals("")) {
				inv.setItem(2, new ItemStack(gun.getBody()));
			} else {
				inv.setItem(2, new ItemStack(ForgeRegistries.ITEMS
						.getValue(new ResourceLocation(NBTUtils.getBody(stack)))));
				GunModelsHandler.get(stack.getItem().getDescriptionId()).getModel()
					.reconstruct(stack);
			}

			// Barrel
			if (NBTUtils.getBarrel(stack).equals("")) {
				inv.setItem(3, new ItemStack(gun.getBarrel()));
			} else {
				inv.setItem(3, new ItemStack(ForgeRegistries.ITEMS
						.getValue(new ResourceLocation(NBTUtils.getBarrel(stack)))));
				GunModelsHandler.get(stack.getItem().getDescriptionId()).getModel()
					.reconstruct(stack);
			}
		}
	}

	@Override
	public boolean stillValid(Player p_75145_1_) {
		return true;
	}

	@Override
	public void slotsChanged(Container p_75130_1_) {
		super.slotsChanged(p_75130_1_);
		if (!player.level.isClientSide) {
			GunItem gun = (GunItem) stack.getItem();
			
			/*if (inv.getItem(0).isEmpty()) {
				ServerUtils.setScope(stack, 0);
				ServerUtils.setScopeGunId(stack, "empty");
				ServerUtils.setScopePath(stack, "");
			} else {
				ServerUtils.setScope(stack, ((Scope) inv.getItem(0).getItem()).getScopePercent());
				ServerUtils.setScopeGunId(stack, ((Scope) inv.getItem(0).getItem()).getGunId());
				ServerUtils.setScopePath(stack, Utils.getR(inv.getItem(0).getItem()));
			}*/

			if (inv.getItem(1).isEmpty()) {
				ServerUtils.setStock(stack, NBTUtils.EMPTY);
				
				//ServerUtils.setStockW(stack, 0);
			} else if ((GunPart) inv.getItem(1).getItem() == (GunPart) gun.getStock()) {
				ServerUtils.setStock(stack, Utils.getR(gun.getStock()).toString());
				//ServerUtils.setStockW(stack, ((GunPart) gun.getStock()).getWeight());
			} else {
				ServerUtils.setStock(stack, Utils.getR(inv.getItem(1).getItem()).toString());
				stack.getOrCreateTag().putBoolean(NBTUtils.MODIFIED, true);
				//ServerUtils.setStockW(stack, ((GunPart) inv.getItem(1).getItem()).getWeight());
			}

			if (inv.getItem(2).isEmpty()) {
				ServerUtils.setBody(stack, NBTUtils.EMPTY);
				//ServerUtils.setBodyW(stack, 0);
			} else if ((GunPart) inv.getItem(2).getItem() == (GunPart) gun.getBody()) {
				ServerUtils.setBody(stack, Utils.getR(gun.getBody()).toString());
				//ServerUtils.setBodyW(stack, ((GunPart) gun.getBody()).getWeight());
			} else {
				ServerUtils.setBody(stack, Utils.getR(inv.getItem(2).getItem()).toString());
				stack.getOrCreateTag().putBoolean(NBTUtils.MODIFIED, true);
				//ServerUtils.setBodyW(stack, ((GunPart) inv.getItem(2).getItem()).getWeight());
			}

			if (inv.getItem(3).isEmpty()) {
				ServerUtils.setBarrel(stack, NBTUtils.EMPTY);
				//ServerUtils.setBodyW(stack, 0);
			} else if ((GunPart) inv.getItem(3).getItem() == (GunPart)gun.getBarrel()) {
				ServerUtils.setBarrel(stack, Utils.getR(gun.getBarrel()).toString());
				//ServerUtils.setBodyW(stack, ((GunPart) gun.getBarrel()).getWeight());
			} else {
				ServerUtils.setBarrel(stack, Utils.getR(inv.getItem(3).getItem()).toString());
				stack.getOrCreateTag().putBoolean(NBTUtils.MODIFIED, true);
				//ServerUtils.setBodyW(stack, ((GunPart) inv.getItem(3).getItem()).getWeight());
				// OldGuns.channel.sendTo(new StartHitmarkerMessage(),
				// ((ServerPlayer)shooter).connection.connection,
				// NetworkDirection.PLAY_TO_CLIENT);
			}

			/*if (inv.getItem(4).isEmpty()) {
				ServerUtils.setBarrelMouth(stack, NBTUtils.EMPTY);
			} else {
				ServerUtils.setBarrelMouth(stack, Utils.getR(inv.getItem(4).getItem()));
			}*/

		}
		broadcastChanges();
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		Slot slot = this.getSlot(index);
		ItemStack stack = slot.getItem();
		ItemStack e = ItemStack.EMPTY;

		if (slot != null && slot.hasItem()) {
			e = stack.copy();
			if (index >= 0 && index <= 3) {
				if (!this.moveItemStackTo(stack, 4, 39, true)) {
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
				}
			} /*else if (stack.getItem() instanceof Scope) {
				if (!this.moveItemStackTo(stack, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} */else if (!this.moveItemStackTo(stack, 4, 39, false)) {
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
	public void removed(Player p_75134_1_) {
		if (!p_75134_1_.level.isClientSide) {
			saveData();
			LogUtils.getLogger().info("Override Barrel: " + ServerUtils.getBarrel(stack) + " Body: "
					+ ServerUtils.getBody(stack) + " Stock: " + ServerUtils.getStock(stack));
		} else {
			if (ModelHandler.INSTANCE.getModels()
					.containsKey(NBTUtils.getId(stack))) {
				GunModel model = GunModelsHandler.get(player.getMainHandItem().getItem()
						.getDescriptionId());
				if(!getSlot(1).getItem().isEmpty()) {
					model.getStuff().setStock(getSlot(1).getItem());
				} else {
					model.getStuff().setStock(model.gun.getStock());
				}
				if(!getSlot(2).getItem().isEmpty()) {
					model.getStuff().setBody(getSlot(2).getItem());
				} else {
					model.getStuff().setBody(model.gun.getBody());
				}
				if(!getSlot(3).getItem().isEmpty()) {
					model.getStuff().setBarrel(getSlot(3).getItem());
				} else {
					model.getStuff().setBarrel(model.gun.getBarrel());
				}
				ModelHandler.INSTANCE.getModels()
				.get(stack.getOrCreateTag().getString(NBTUtils.ID)).reconstruct();
				LogUtils.getLogger().info("reconstructing");
			} else {
				LogUtils.getLogger().info("Id " + NBTUtils.getId(stack) + " is not registered");
			}
			LogUtils.getLogger().info("Override Barrel: " + ServerUtils.getBarrel(stack) + " Body: "
					+ ServerUtils.getBody(stack) + " Stock: " + ServerUtils.getStock(stack));
		}
		broadcastChanges();
		super.removed(p_75134_1_);
	}

}
