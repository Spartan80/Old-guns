package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.client.StartHitmarkerUtil;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class StartHitmarkerMessage {

	public StartHitmarkerMessage() {
	}

	public static void encode(StartHitmarkerMessage msg, PacketBuffer buf) {
	}

	public static StartHitmarkerMessage decode(PacketBuffer buf) {
		return new StartHitmarkerMessage();
	}

	public static void handle(StartHitmarkerMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> StartHitmarkerUtil.start());
		context.setPacketHandled(true);
	}
}
