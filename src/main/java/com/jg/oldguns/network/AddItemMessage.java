package com.jg.oldguns.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.utils.Constants;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class AddItemMessage {

	public String path;
	public int count;

	public AddItemMessage(String path, int count) {
		this.path = path;
		this.count = count;
	}

	public static void encode(AddItemMessage msg, FriendlyByteBuf buf) {
		buf.writeUtf(msg.path);
		buf.writeInt(msg.count);
	}

	public static AddItemMessage decode(FriendlyByteBuf buf) {
		return new AddItemMessage(buf.readUtf(32727), buf.readInt());
	}

	public static void handle(AddItemMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(msg.path)));
			stack.setCount(msg.count);
			
			CompoundTag nbt = stack.getOrCreateTag();
			
			if(stack.getItem() instanceof ItemGun) {
				nbt.putString(Constants.ID, UUID.randomUUID().toString());
			}
			if (player.getInventory().getFreeSlot() != -1) {
				player.getInventory().add(stack);
			} else {
				player.level.addFreshEntity(
						new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack));
			}
		});
		context.setPacketHandled(true);
	}
}
