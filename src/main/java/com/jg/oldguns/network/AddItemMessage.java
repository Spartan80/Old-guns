package com.jg.oldguns.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class AddItemMessage {

	String path;
	int amount;
	
	public AddItemMessage(String path, int amount) {
		this.path = path;
		this.amount = amount;
	}
	
	public static void encode(AddItemMessage msg, FriendlyByteBuf buf) {
		buf.writeUtf(msg.path);
		buf.writeInt(msg.amount);
	}
	
	public static AddItemMessage decode(FriendlyByteBuf buf) {
		return new AddItemMessage(buf.readUtf(32727), buf.readInt());
	}
	
	public static void handle(AddItemMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();
		
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			player.getInventory().add(new ItemStack(ForgeRegistries.ITEMS
					.getValue(new ResourceLocation(msg.path)), msg.amount));
		});
		context.setPacketHandled(true);
	}

}
