package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.gun.ItemGun;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ShootticksMessage {
	private int ticks;

	public ShootticksMessage(int ticks) {
		this.ticks = ticks;
	}

	public static void encode(ShootticksMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.ticks);
	}

	public static ShootticksMessage decode(PacketBuffer buf) {
		return new ShootticksMessage(buf.readInt());
	}

	public static void handle(ShootticksMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ItemStack stack = player.getMainHandItem();

			if (player != null && !player.isSpectator() && player.isAlive() && stack.getItem() instanceof ItemGun) {
				stack.getOrCreateTag().putInt("shoot_ticks", stack.getOrCreateTag().getInt("shoot_ticks") + 1);
				player.sendMessage(
						new TranslationTextComponent(
								"Gun shootticks server: " + stack.getOrCreateTag().getInt("shoot_ticks")),
						player.getUUID());
				System.out.println("server shootticks: " + stack.getOrCreateTag().getInt("shoot_ticks"));
			}
		});

		context.setPacketHandled(true);
	}
}
