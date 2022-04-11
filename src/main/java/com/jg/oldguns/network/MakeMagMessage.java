package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class MakeMagMessage {
	public int maxAmmo;
	public boolean hasmag;
	public String bpath;
	public String mpath;

	public MakeMagMessage(int maxAmmo, boolean hasmag, String bpath, String mpath) {
		this.maxAmmo = maxAmmo;
		this.hasmag = hasmag;
		this.bpath = bpath;
		this.mpath = mpath;
	}

	public static void encode(MakeMagMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.maxAmmo);
		buf.writeBoolean(msg.hasmag);
		buf.writeUtf(msg.bpath);
		buf.writeUtf(msg.mpath);
	}

	public static MakeMagMessage decode(FriendlyByteBuf buf) {
		return new MakeMagMessage(buf.readInt(), buf.readBoolean(), buf.readUtf(32727), buf.readUtf(32727));
	}

	public static void handle(MakeMagMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			CompoundTag nbt = player.getMainHandItem().getOrCreateTag();
			nbt.putBoolean(Constants.HASMAG, msg.hasmag);
			if (msg.hasmag) {
				nbt.putInt(Constants.MAXAMMO, msg.maxAmmo);
				nbt.putString(Constants.MAGBULLETPATH, msg.bpath);
				nbt.putString(Constants.MAGPATH, msg.mpath);
			}
		});
		context.setPacketHandled(true);
	}
}
