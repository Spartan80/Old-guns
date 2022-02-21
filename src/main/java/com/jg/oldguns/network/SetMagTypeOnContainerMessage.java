package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SetMagTypeOnContainerMessage {

	public int index, magtype;

	public SetMagTypeOnContainerMessage(int index, int magtype) {
		this.index = index;
		this.magtype = magtype;
	}

	public static void encode(SetMagTypeOnContainerMessage msg, PacketBuffer buf) {
		buf.writeInt(msg.index);
		buf.writeInt(msg.magtype);
	}

	public static SetMagTypeOnContainerMessage decode(PacketBuffer buf) {
		return new SetMagTypeOnContainerMessage(buf.readInt(), buf.readInt());
	}

	public static void handle(SetMagTypeOnContainerMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			PlayerEntity player = context.getSender();
			player.containerMenu.getSlot(msg.index).getItem().getOrCreateTag().putInt(Constants.MAGTYPE, msg.magtype);
		});
		context.setPacketHandled(true);
	}
}
