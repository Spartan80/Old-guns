package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.gun.ItemGun;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class BulletsMessage {
	private int hasbullet;

	public BulletsMessage(int hasbullet) {
		this.hasbullet = hasbullet;
	}

	public static void encode(BulletsMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.hasbullet);
	}

	public static BulletsMessage decode(PacketBuffer buf) {
		return new BulletsMessage(buf.readInt());
	}

	public static void handle(BulletsMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ItemStack stack = player.getMainHandItem();

			if (player != null && !player.isSpectator() && player.isAlive() && stack.getItem() instanceof ItemGun) {
				ItemGun gun = (ItemGun) stack.getItem();
				gun.setNBTBullets(stack, msg.hasbullet);
			}
		});

		context.setPacketHandled(true);
	}
}
