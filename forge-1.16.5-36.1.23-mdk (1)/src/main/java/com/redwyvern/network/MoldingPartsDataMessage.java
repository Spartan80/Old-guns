package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.container.MoldingPartsContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MoldingPartsDataMessage {
	private int index, value;

	public MoldingPartsDataMessage(int index, int value) {
		this.index = index;
		this.value = value;
	}

	public static void encode(MoldingPartsDataMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.index);
		buf.writeInt(msg.value);
	}

	public static MoldingPartsDataMessage decode(PacketBuffer buf) {
		return new MoldingPartsDataMessage(buf.readInt(), buf.readInt());
	}

	public static void handle(MoldingPartsDataMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			PlayerEntity player = context.getSender();

			if (player != null && player.containerMenu instanceof MoldingPartsContainer) {
				MoldingPartsContainer container = (MoldingPartsContainer) player.containerMenu;
				container.datam.set(msg.index, msg.value);
			}
		});

		context.setPacketHandled(true);
	}
}
