package com.jg.oldguns.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class SetAttachmentMessage {

	public String att, value;

	public SetAttachmentMessage(String att, String value) {
		this.att = att;
		this.value = value;
	}

	public static void encode(SetAttachmentMessage msg, FriendlyByteBuf buf) {
		buf.writeUtf(msg.att);
		buf.writeUtf(msg.value);
	}

	public static SetAttachmentMessage decode(FriendlyByteBuf buf) {
		return new SetAttachmentMessage(buf.readUtf(32767), buf.readUtf(32767));
	}

	public static void handle(SetAttachmentMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			player.getMainHandItem().getOrCreateTag().putString(msg.att, msg.value);
		});
		context.setPacketHandled(true);
	}
}
