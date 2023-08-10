package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.ServerUtils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class SetBulletsMessage {

	int bullets;

	public SetBulletsMessage(int bullets) {
		this.bullets = bullets;
	}

	public static void encode(SetBulletsMessage msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.bullets);
	}

	public static SetBulletsMessage decode(FriendlyByteBuf buf) {
		return new SetBulletsMessage(buf.readInt());
	}

	public static void handle(SetBulletsMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			if(player.getMainHandItem().getItem() instanceof GunItem) {
				ServerUtils.setGunBullets(player, msg.bullets);
			} else {
				player.getMainHandItem().getOrCreateTag().putInt(NBTUtils.BULLETS, msg.bullets);
			}
		});
		context.setPacketHandled(true);
	}

}
