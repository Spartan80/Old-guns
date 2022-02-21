package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.ServerUtils;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SetHammerMessage {
	boolean hammer;

	public SetHammerMessage(boolean hammer) {
		this.hammer = hammer;
	}

	public static void encode(SetHammerMessage msg, PacketBuffer buf) {
		buf.writeBoolean(msg.hammer);
	}

	public static SetHammerMessage decode(PacketBuffer buf) {
		return new SetHammerMessage(buf.readBoolean());
	}

	public static void handle(SetHammerMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ServerUtils.setHammer(player, msg.hammer);
		});
		context.setPacketHandled(true);
	}
}
