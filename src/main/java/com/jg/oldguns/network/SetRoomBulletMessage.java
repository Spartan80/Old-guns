package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class SetRoomBulletMessage {

	public boolean hasbulletonroom;

	public SetRoomBulletMessage(boolean hasbulletonroom) {
		this.hasbulletonroom = hasbulletonroom;
	}

	public static void encode(SetRoomBulletMessage msg, FriendlyByteBuf buf) {
		buf.writeBoolean(msg.hasbulletonroom);
	}

	public static SetRoomBulletMessage decode(FriendlyByteBuf buf) {
		return new SetRoomBulletMessage(buf.readBoolean());
	}

	public static void handle(SetRoomBulletMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			player.getMainHandItem().getOrCreateTag().putBoolean(Constants.HASBULLETONROOM, msg.hasbulletonroom);
		});
		context.setPacketHandled(true);
	}

}
