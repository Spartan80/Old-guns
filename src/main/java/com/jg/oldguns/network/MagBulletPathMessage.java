package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MagBulletPathMessage {
	public String path;

	public MagBulletPathMessage(String path) {
		this.path = path;
	}

	public static void encode(MagBulletPathMessage msg, PacketBuffer buf) {
		buf.writeUtf(msg.path);
	}

	public static MagBulletPathMessage decode(PacketBuffer buf) {
		return new MagBulletPathMessage(buf.readUtf(32727));
	}

	public static void handle(MagBulletPathMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			player.getMainHandItem().getOrCreateTag().putString(Constants.MAGBULLETPATH, msg.path);
		});
		context.setPacketHandled(true);
	}
}
