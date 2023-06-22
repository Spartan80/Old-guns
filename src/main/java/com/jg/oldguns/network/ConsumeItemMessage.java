package com.jg.oldguns.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class ConsumeItemMessage {

	int[] data;
	
	public ConsumeItemMessage(int index, int amount) {
		this(new int[] { index, amount });
	}
	
	public ConsumeItemMessage(int[] data) {
		this.data = data;
	}
	
	public static void encode(ConsumeItemMessage msg, FriendlyByteBuf buf) {
		buf.writeVarIntArray(msg.data);
	}
	
	public static ConsumeItemMessage decode(FriendlyByteBuf buf) {
		return new ConsumeItemMessage(buf.readVarIntArray());
	}
	
	public static void handle(ConsumeItemMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();
		
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			for(int i = 0; i < msg.data.length; i += 2) {
				player.getInventory().removeItem(msg.data[i], msg.data[i+1]);
			}
		});
		context.setPacketHandled(true);
	}

}
