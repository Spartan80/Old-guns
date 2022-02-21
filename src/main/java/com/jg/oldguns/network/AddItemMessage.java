package com.jg.oldguns.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.utils.Constants;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class AddItemMessage {

	public String path;
	public int count;

	public AddItemMessage(String path, int count) {
		this.path = path;
		this.count = count;
	}

	public static void encode(AddItemMessage msg, PacketBuffer buf) {
		buf.writeUtf(msg.path);
		buf.writeInt(msg.count);
	}

	public static AddItemMessage decode(PacketBuffer buf) {
		return new AddItemMessage(buf.readUtf(32727), buf.readInt());
	}

	public static void handle(AddItemMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(msg.path)));
			stack.setCount(msg.count);
			
			CompoundNBT nbt = stack.getOrCreateTag();
			
			if(stack.getItem() instanceof ItemGun) {
				nbt.putString(Constants.ID, UUID.randomUUID().toString());
			}
			if (player.inventory.getFreeSlot() != -1) {
				player.inventory.add(stack);
			} else {
				player.level.addFreshEntity(
						new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), stack));
			}
		});
		context.setPacketHandled(true);
	}
}
