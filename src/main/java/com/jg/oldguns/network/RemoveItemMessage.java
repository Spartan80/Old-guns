package com.jg.oldguns.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class RemoveItemMessage {

	public int index, count;

	public RemoveItemMessage(int index, int count) {
		this.index = index;
		this.count = count;
	}

	public static void encode(RemoveItemMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.index);
		buf.writeInt(msg.count);
	}

	public static RemoveItemMessage decode(FriendlyByteBuf buf) {
		return new RemoveItemMessage(buf.readInt(), buf.readInt());
	}

	public static void handle(RemoveItemMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			player.getInventory().removeItem(msg.index, msg.count);
		});
		context.setPacketHandled(true);
	}
}
