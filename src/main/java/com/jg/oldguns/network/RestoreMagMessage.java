package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.Constants;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class RestoreMagMessage {

	public String path;
	public int bullets;

	public RestoreMagMessage(String path, int bullets) {
		this.path = path;
		this.bullets = bullets;
	}

	public static void encode(RestoreMagMessage msg, PacketBuffer buf) {
		buf.writeUtf(msg.path);
		buf.writeInt(msg.bullets);
	}

	public static RestoreMagMessage decode(PacketBuffer buf) {
		return new RestoreMagMessage(buf.readUtf(32727), buf.readInt());
	}

	public static void handle(RestoreMagMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(msg.path)));
			stack.getOrCreateTag().putInt(Constants.BULLETS, msg.bullets);
			stack.getOrCreateTag().putString(Constants.MAGBULLETPATH,
					player.getMainHandItem().getOrCreateTag().getString(Constants.MAGBULLETPATH));
			System.out.println("mag bullets: " + stack.getOrCreateTag().getInt(Constants.BULLETS) + " msg bullets: "
					+ msg.bullets);
			player.inventory.add(stack);
		});
		context.setPacketHandled(true);
	}
}
