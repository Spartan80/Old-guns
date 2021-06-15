package com.redwyvern.network;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ConsumeStackMessage {
	public int slotid;
	public int count;

	public ConsumeStackMessage(int slotid, int count) {
		this.slotid = slotid;
		this.count = count;
	}

	public static void encode(ConsumeStackMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.slotid);
		buf.writeInt(msg.count);
	}

	public static ConsumeStackMessage decode(PacketBuffer buf) {
		return new ConsumeStackMessage(buf.readInt(), buf.readInt());
	}

	public static void handle(ConsumeStackMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			PlayerEntity player = context.getSender();

			if (player != null) {
				PlayerInventory inv = player.inventory;
				inv.removeItem(msg.slotid, msg.count);

			}
		});

		context.setPacketHandled(true);
	}
}
