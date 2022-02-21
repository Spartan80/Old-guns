package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.ServerUtils;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SetBulletsMessage {

	int bullets;

	public SetBulletsMessage(int bullets) {
		this.bullets = bullets;
	}

	public static void encode(SetBulletsMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.bullets);
	}

	public static SetBulletsMessage decode(PacketBuffer buf) {
		return new SetBulletsMessage(buf.readInt());
	}

	public static void handle(SetBulletsMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ServerUtils.setGunBullets(player, msg.bullets);
		});
		context.setPacketHandled(true);
	}

}
