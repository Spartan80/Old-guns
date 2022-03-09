package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.ServerUtils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class SetScopeMessage {

	int scope;

	public SetScopeMessage(int scope) {
		this.scope = scope;
	}

	public static void encode(SetScopeMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.scope);
	}

	public static SetScopeMessage decode(FriendlyByteBuf buf) {
		return new SetScopeMessage(buf.readInt());
	}

	public static void handle(SetScopeMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ServerUtils.setScope(player, msg.scope);
		});
		context.setPacketHandled(true);
	}
}
