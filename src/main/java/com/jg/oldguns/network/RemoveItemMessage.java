package com.jg.oldguns.network;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class RemoveItemMessage {

	public int index, count;

	public RemoveItemMessage(int index, int count) {
		this.index = index;
		this.count = count;
	}

	public static void encode(RemoveItemMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.index);
		buf.writeInt(msg.count);
	}

	public static RemoveItemMessage decode(PacketBuffer buf) {
		return new RemoveItemMessage(buf.readInt(), buf.readInt());
	}

	public static void handle(RemoveItemMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			player.inventory.removeItem(msg.index, msg.count);
		});
		context.setPacketHandled(true);
	}
}
