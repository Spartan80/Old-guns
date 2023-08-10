package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.mojang.logging.LogUtils;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class AddItemMessage {

	int[] data;
	String path;
	
	public AddItemMessage(String path, int[] data) {
		this.path = path;
		this.data = data;
	}
	
	public static void encode(AddItemMessage msg, FriendlyByteBuf buf) {
		buf.writeUtf(msg.path);
		buf.writeVarIntArray(msg.data);
	}
	
	public static AddItemMessage decode(FriendlyByteBuf buf) {
		return new AddItemMessage(buf.readUtf(32727), buf.readVarIntArray());
	}
	
	public static void handle(AddItemMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();
		
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			LogUtils.getLogger().info("Offhand Size: " + player.getInventory().offhand.size() 
					+ " Items Size: " + player.getInventory().items.size() + " Armor Size: " 
					+ player.getInventory().armor.size());
			/*for(int i = 0; i < player.getInventory().items.size(); i++) {
				LogUtils.getLogger().info("Item: " + player.getInventory().items.get(i).getItem()
						.getRegistryName().toString() + " Index: " + i + 
						" Count: " + player.getInventory().items.get(i).getCount());
			}*/
			//LogUtils.getLogger().info("Player InventoryMenu");
			for(int i = 0; i < msg.data.length - 1; i += 2) {
				/*LogUtils.getLogger().info("Item: " + msg.path + " Index: " + msg.data[i] + 
						" Count: " + msg.data[i + 1]);*/
				NonNullList<ItemStack> items = player.inventoryMenu.getItems();
				if(items.get(msg.data[i]) == ItemStack.EMPTY) {
					items.set(msg.data[i], new ItemStack(ForgeRegistries.ITEMS
							.getValue(new ResourceLocation(msg.path)), msg.data[i + 1]));
				} else {
					items.get(msg.data[i]).grow(msg.data[i + 1]);
				}
				player.getInventory().add(8, 
						new ItemStack(ForgeRegistries.ITEMS
						.getValue(new ResourceLocation(msg.path)), msg.data[i + 1]));
				/*LogUtils.getLogger().info("Added? " + player.getInventory().add(msg.data[i], 
						new ItemStack(ForgeRegistries.ITEMS
						.getValue(new ResourceLocation(msg.path)), msg.data[i + 1])));*/
			}
			int amount = msg.data[msg.data.length - 1];
			if(amount > 0) {
				/*player.level.addFreshEntity(new ItemEntity((ServerLevel) player.level, player.getX(), 
						player.getY(), player.getZ(), new ItemStack(ForgeRegistries.ITEMS
								.getValue(new ResourceLocation(msg.path)), amount)));*/
				player.drop(new ItemStack(ForgeRegistries.ITEMS
						.getValue(new ResourceLocation(msg.path)), amount), false);
				/*((ServerLevel)(player.level)).(new ItemStack(ForgeRegistries.ITEMS
								.getValue(new ResourceLocation(msg.path)), amount)));*/
			}
			/*player.getInventory().add(new ItemStack(ForgeRegistries.ITEMS
					.getValue(new ResourceLocation(msg.path)), msg.amount));*/
		});
		context.setPacketHandled(true);
	}

}
