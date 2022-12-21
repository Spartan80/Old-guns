package com.jg.oldguns.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.jg.oldguns.utils.NBTUtils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class InitGunMessage {
	public UUID id;

	public InitGunMessage(UUID id) {
		this.id = id;
	}

	public static void encode(InitGunMessage msg, FriendlyByteBuf buf) {
		buf.writeUUID(msg.id);
	}

	public static InitGunMessage decode(FriendlyByteBuf buf) {
		return new InitGunMessage(buf.readUUID());
	}

	public static void handle(InitGunMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ItemStack stack = player.getMainHandItem();
			CompoundTag nbt = stack.getOrCreateTag();
			nbt.putString(NBTUtils.ID, msg.id.toString());

		});
		context.setPacketHandled(true);
	}
}
