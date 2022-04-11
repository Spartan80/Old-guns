package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class MagBulletPathMessage {
	public String path;

	public MagBulletPathMessage(String path) {
		this.path = path;
	}

	public static void encode(MagBulletPathMessage msg, FriendlyByteBuf buf) {
		buf.writeUtf(msg.path);
	}

	public static MagBulletPathMessage decode(FriendlyByteBuf buf) {
		return new MagBulletPathMessage(buf.readUtf(32727));
	}

	public static void handle(MagBulletPathMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			player.getMainHandItem().getOrCreateTag().putString(Constants.MAGBULLETPATH, msg.path);
		});
		context.setPacketHandled(true);
	}
}
