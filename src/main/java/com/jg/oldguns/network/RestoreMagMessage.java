package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.utils.NBTUtils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class RestoreMagMessage {

	public String path;
	public int bullets;

	public RestoreMagMessage(String path, int bullets) {
		this.path = path;
		this.bullets = bullets;
	}

	public static void encode(RestoreMagMessage msg, FriendlyByteBuf buf) {
		buf.writeUtf(msg.path);
		buf.writeInt(msg.bullets);
	}

	public static RestoreMagMessage decode(FriendlyByteBuf buf) {
		return new RestoreMagMessage(buf.readUtf(32727), buf.readInt());
	}

	public static void handle(RestoreMagMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ItemStack stack = new ItemStack(ForgeRegistries.ITEMS
					.getValue(new ResourceLocation(msg.path)));
			stack.getOrCreateTag().putInt(NBTUtils.BULLETS, msg.bullets);
			stack.getOrCreateTag().putString(NBTUtils.MAGBULLET,
					player.getMainHandItem().getOrCreateTag().getString(NBTUtils.MAGBULLET));
			System.out.println("mag bullets: " + stack.getOrCreateTag().getInt(NBTUtils.BULLETS) + " msg bullets: "
					+ msg.bullets);
			player.getInventory().add(stack);
		});
		context.setPacketHandled(true);
	}
}
