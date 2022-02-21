package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MakeMagMessage {
	public boolean hasmag;
	public int type;
	public String bpath;

	public MakeMagMessage(boolean hasmag, int type, String bpath) {
		this.hasmag = hasmag;
		this.type = type;
		this.bpath = bpath;
	}

	public static void encode(MakeMagMessage msg, PacketBuffer buf) {
		buf.writeBoolean(msg.hasmag);
		buf.writeInt(msg.type);
		buf.writeUtf(msg.bpath);
	}

	public static MakeMagMessage decode(PacketBuffer buf) {
		return new MakeMagMessage(buf.readBoolean(), buf.readInt(), buf.readUtf(32727));
	}

	public static void handle(MakeMagMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			player.getMainHandItem().getOrCreateTag().putBoolean(Constants.HASMAG, msg.hasmag);
			if (msg.hasmag) {
				System.out.println("Has mag");
				player.getMainHandItem().getOrCreateTag().putInt(Constants.MAGTYPE, msg.type);
				player.getMainHandItem().getOrCreateTag().putString(Constants.MAGBULLETPATH, msg.bpath);
			}
		});
		context.setPacketHandled(true);
	}
}
