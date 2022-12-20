package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.client.handlers.StartHitmarkerUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class StartHitmarkerMessage {

	public StartHitmarkerMessage() {
	}

	public static void encode(StartHitmarkerMessage msg, FriendlyByteBuf buf) {
	}

	public static StartHitmarkerMessage decode(FriendlyByteBuf buf) {
		return new StartHitmarkerMessage();
	}

	public static void handle(StartHitmarkerMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> StartHitmarkerUtil.start());
		context.setPacketHandled(true);
	}
}
