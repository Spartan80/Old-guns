package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.gun.ItemGun;
import com.redwyvern.registries.ItemRegistries;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class setScopeMessage {
	private boolean useOffSystem;

	public setScopeMessage(boolean useOffSystem) {
		this.useOffSystem = useOffSystem;
	}

	public static void encode(setScopeMessage msg, PacketBuffer buf) {
		buf.writeBoolean(msg.useOffSystem);
	}

	public static setScopeMessage decode(PacketBuffer buf) {
		return new setScopeMessage(buf.readBoolean());
	}

	public static void handle(setScopeMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ItemStack mainstack = player.getMainHandItem();

			/*
			 * if(player != null && !player.isSpectator() && player.isAlive() &&
			 * stack.getItem() instanceof ItemGun){
			 * stack.getOrCreateTag().putBoolean("scope", msg.scope); }
			 */

			ItemStack ostack = player.getOffhandItem();
			ItemGun gun = (ItemGun) player.getMainHandItem().getItem();

			if (msg.useOffSystem) {
				if (ostack.getItem() == ItemRegistries.scope.get()) {
					if (!gun.getNBTScope(mainstack)) {
						gun.setNBTScope(mainstack, true);
						ostack.shrink(1);
					} else {
						gun.setNBTScope(mainstack, false);
						ostack.grow(1);
					}
				} else {
					if (gun.getNBTScope(mainstack)) {
						if (ostack.isEmpty()) {
							player.setItemInHand(Hand.OFF_HAND, new ItemStack(ItemRegistries.scope.get()));
							gun.setNBTScope(mainstack, false);
						} else {
							ostack.grow(1);
						}
					}
				}
			} else {
				if (gun.getNBTScope(mainstack)) {
					if (player.inventory.getFreeSlot() != -1) {
						gun.setNBTScope(mainstack, false);
						player.inventory.add(new ItemStack(ItemRegistries.scope.get()));
					}
				} else {
					int slot = player.inventory.findSlotMatchingItem(new ItemStack(ItemRegistries.scope.get()));
					if (slot != -1) {
						player.inventory.getItem(slot).shrink(1);
						gun.setNBTScope(mainstack, true);
					}
				}
			}
		});

		context.setPacketHandled(true);
	}
}
