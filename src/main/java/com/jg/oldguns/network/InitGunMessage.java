package com.jg.oldguns.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class InitGunMessage {
	public UUID id;

	public InitGunMessage(UUID id) {
		this.id = id;
	}

	public static void encode(InitGunMessage msg, PacketBuffer buf) {
		buf.writeUUID(msg.id);
	}

	public static InitGunMessage decode(PacketBuffer buf) {
		return new InitGunMessage(buf.readUUID());
	}

	public static void handle(InitGunMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ItemStack stack = player.getMainHandItem();
			CompoundNBT nbt = stack.getOrCreateTag();
			nbt.putString(Constants.ID, msg.id.toString());
			
		});
		context.setPacketHandled(true);
	}
}
