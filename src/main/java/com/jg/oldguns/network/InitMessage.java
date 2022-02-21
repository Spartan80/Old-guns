package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.client.helpers.InitHelper;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class InitMessage {
	public InitMessage() {
	}

	public static void encode(InitMessage msg, PacketBuffer buf) {
	}

	public static InitMessage decode(PacketBuffer buf) {
		return new InitMessage();
	}

	public static void handle(InitMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> InitHelper.start());
		context.setPacketHandled(true);
	}
}
