package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.ServerUtils;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SetAttachmentsMessage {

	String barrelmouth, barrel, body, stock;
	int scope;

	public SetAttachmentsMessage(String barrelmouth, String barrel, String body, String stock, int scope) {
		this.barrelmouth = barrelmouth;
		this.barrel = barrel;
		this.body = body;
		this.stock = stock;
		this.scope = scope;
	}

	public static void encode(SetAttachmentsMessage msg, PacketBuffer buf) {
		buf.writeUtf(msg.barrelmouth);
		buf.writeUtf(msg.barrel);
		buf.writeUtf(msg.body);
		buf.writeUtf(msg.stock);
		buf.writeInt(msg.scope);
	}

	public static SetAttachmentsMessage decode(PacketBuffer buf) {
		return new SetAttachmentsMessage(buf.readUtf(32767), buf.readUtf(32767), buf.readUtf(32767), buf.readUtf(32767),
				buf.readInt());
	}

	public static void handle(SetAttachmentsMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ServerUtils.setBarrelMouth(player, msg.barrelmouth);
			ServerUtils.setBarrel(player, msg.barrel);
			ServerUtils.setBody(player, msg.body);
			ServerUtils.setStock(player, msg.stock);
			ServerUtils.setScope(player, msg.scope);
		});
		context.setPacketHandled(true);
	}

}
