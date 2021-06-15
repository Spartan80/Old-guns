package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.container.MoldingPartsContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class CleanGunSlotMessage {
	public CleanGunSlotMessage() {

	}

	public static void encode(CleanGunSlotMessage msg, PacketBuffer buf) {

	}

	public static CleanGunSlotMessage decode(PacketBuffer buf) {
		return new CleanGunSlotMessage();
	}

	public static void handle(CleanGunSlotMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			PlayerEntity player = context.getSender();

			if (player != null && player.containerMenu instanceof MoldingPartsContainer) {
				Container container = player.containerMenu;
				container.getSlot(2).set(ItemStack.EMPTY);
				container.getSlot(0).set(ItemStack.EMPTY);
				if (player.inventory.getFreeSlot() != -1) {
					player.inventory.add(container.getSlot(1).getItem());
				}
			}
		});

		context.setPacketHandled(true);
	}
}
