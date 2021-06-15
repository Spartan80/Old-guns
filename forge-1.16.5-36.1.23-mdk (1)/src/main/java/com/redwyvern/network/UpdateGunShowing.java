package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.container.GunCraftingTableContainer;
import com.redwyvern.gun.ItemGun;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class UpdateGunShowing {
	private String name;
	private boolean crafting;

	public UpdateGunShowing(String name, boolean crafting) {
		this.name = name;
		this.crafting = crafting;
	}

	public static void encode(UpdateGunShowing msg, PacketBuffer buf) {
		buf.writeUtf(msg.name);
		buf.writeBoolean(msg.crafting);
	}

	public static UpdateGunShowing decode(PacketBuffer buf) {
		return new UpdateGunShowing(buf.readUtf(), buf.readBoolean());
	}

	public static void handle(UpdateGunShowing msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			PlayerEntity player = context.getSender();

			if (player != null && player.containerMenu instanceof GunCraftingTableContainer && msg.crafting) {
				Container container = player.containerMenu;
				container.getSlot(0).set(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(msg.name))));
				ItemGun gun = (ItemGun) (container.getSlot(0).getItem().getItem());
				container.getSlot(1).set(new ItemStack(gun.getBarrel()));
				container.getSlot(2).set(new ItemStack(gun.getBodyMetal()));
				container.getSlot(3).set(new ItemStack(gun.getBodyWood()));
			} else {
				Container container = player.containerMenu;
				container.getSlot(3).set(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(msg.name))));
			}
		});

		context.setPacketHandled(true);
	}
}
