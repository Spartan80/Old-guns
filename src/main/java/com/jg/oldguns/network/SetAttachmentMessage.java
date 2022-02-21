package com.jg.oldguns.network;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SetAttachmentMessage {

	public String att, value;

	public SetAttachmentMessage(String att, String value) {
		this.att = att;
		this.value = value;
	}

	public static void encode(SetAttachmentMessage msg, PacketBuffer buf) {
		buf.writeUtf(msg.att);
		buf.writeUtf(msg.value);
	}

	public static SetAttachmentMessage decode(PacketBuffer buf) {
		return new SetAttachmentMessage(buf.readUtf(32767), buf.readUtf(32767));
	}

	public static void handle(SetAttachmentMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			player.getMainHandItem().getOrCreateTag().putString(msg.att, msg.value);
		});
		context.setPacketHandled(true);
	}
}
