package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.ServerUtils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class SetHammerMessage {
	boolean hammer;

	public SetHammerMessage(boolean hammer) {
		this.hammer = hammer;
	}

	public static void encode(SetHammerMessage msg, FriendlyByteBuf buf) {
		buf.writeBoolean(msg.hammer);
	}

	public static SetHammerMessage decode(FriendlyByteBuf buf) {
		return new SetHammerMessage(buf.readBoolean());
	}

	public static void handle(SetHammerMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ServerUtils.setHammer(player, msg.hammer);
		});
		context.setPacketHandled(true);
	}
}
