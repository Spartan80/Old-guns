package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.container.GunCraftingTableContainer;
import com.redwyvern.gun.ItemGun;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class GunCraftMessage {
	private String name;
	private int wi, ii, si;

	public GunCraftMessage(String name, int wi, int ii, int si) {
		this.name = name;
		this.wi = wi;
		this.ii = ii;
		this.si = si;
	}

	public static void encode(GunCraftMessage msg, PacketBuffer buf) {
		buf.writeUtf(msg.name);
		buf.writeInt(msg.wi);
		buf.writeInt(msg.ii);
		buf.writeInt(msg.si);
	}

	public static GunCraftMessage decode(PacketBuffer buf) {
		return new GunCraftMessage(buf.readUtf(), buf.readInt(), buf.readInt(), buf.readInt());
	}

	public static void handle(GunCraftMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			PlayerEntity player = context.getSender();

			if (player != null && player.containerMenu instanceof GunCraftingTableContainer) {
				Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(msg.name));
				ItemGun gun = (ItemGun) item;
				PlayerInventory inv = player.inventory;
				inv.removeItem(msg.wi, 1);
				inv.removeItem(msg.si, 1);
				inv.removeItem(msg.ii, 1);
				inv.add(new ItemStack(item));

			}
		});

		context.setPacketHandled(true);
	}
}
