package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.ServerUtils;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SetScopeMessage {

	int scope;

	public SetScopeMessage(int scope) {
		this.scope = scope;
	}

	public static void encode(SetScopeMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.scope);
	}

	public static SetScopeMessage decode(PacketBuffer buf) {
		return new SetScopeMessage(buf.readInt());
	}

	public static void handle(SetScopeMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ServerUtils.setScope(player, msg.scope);
		});
		context.setPacketHandled(true);
	}
}
