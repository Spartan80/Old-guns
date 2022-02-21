package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SetRoomBulletMessage {

	public boolean hasbulletonroom;

	public SetRoomBulletMessage(boolean hasbulletonroom) {
		this.hasbulletonroom = hasbulletonroom;
	}

	public static void encode(SetRoomBulletMessage msg, PacketBuffer buf) {
		buf.writeBoolean(msg.hasbulletonroom);
	}

	public static SetRoomBulletMessage decode(PacketBuffer buf) {
		return new SetRoomBulletMessage(buf.readBoolean());
	}

	public static void handle(SetRoomBulletMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			player.getMainHandItem().getOrCreateTag().putBoolean(Constants.HASBULLETONROOM, msg.hasbulletonroom);
		});
		context.setPacketHandled(true);
	}

}
