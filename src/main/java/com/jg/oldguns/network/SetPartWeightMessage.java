package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class SetPartWeightMessage {
	
	public int part;
	public float weight;

	public SetPartWeightMessage(int part, float weight) {
		this.part = part;
		this.weight = weight;
	}

	public static void encode(SetPartWeightMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.part);
		buf.writeFloat(msg.weight);
	}

	public static SetPartWeightMessage decode(FriendlyByteBuf buf) {
		return new SetPartWeightMessage(buf.readInt() ,buf.readFloat());
	}

	public static void handle(SetPartWeightMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			int part = msg.part;
			switch(part) {
				case 1:
					player.getMainHandItem().getOrCreateTag().putFloat(Constants.STOCKW, msg.weight);
				break;
				case 2:
					player.getMainHandItem().getOrCreateTag().putFloat(Constants.BODYW, msg.weight);
				break;
				case 3:
					player.getMainHandItem().getOrCreateTag().putFloat(Constants.BARRELW, msg.weight);
				break;
			}
			
		});
		context.setPacketHandled(true);
	}
}
