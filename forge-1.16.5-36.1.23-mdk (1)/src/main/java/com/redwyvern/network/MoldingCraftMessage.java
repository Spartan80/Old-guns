package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.container.MoldingPartsContainer;
import com.redwyvern.items.GunPartMold;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.util.Util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class MoldingCraftMessage {

	private String name, partname;

	public MoldingCraftMessage(String name, String partname) {
		this.name = name;
		this.partname = partname;
	}

	public static void encode(MoldingCraftMessage msg, PacketBuffer buf) {
		buf.writeUtf(msg.name);
		buf.writeUtf(msg.partname);
	}

	public static MoldingCraftMessage decode(PacketBuffer buf) {
		return new MoldingCraftMessage(buf.readUtf(), buf.readUtf());
	}

	public static void handle(MoldingCraftMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			PlayerEntity player = context.getSender();

			if (player != null && player.containerMenu instanceof MoldingPartsContainer) {
				Container container = player.containerMenu;
				if (msg.name != "") {
					container.getSlot(0).set(new ItemStack(ItemRegistries.mold.get()));
					Slot slotr = container.getSlot(0);
					GunPartMold mold = ((GunPartMold) slotr.getItem().getItem());
					mold.setNBTMoldName(slotr.getItem(), msg.name);
					mold.setNBTPart(slotr.getItem(), msg.partname);
					container.getSlot(2)
							.set(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(msg.partname))));
				} else {
					Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(msg.partname));
					container.getSlot(2).set(new ItemStack(item));
					container.getSlot(0).set(ItemStack.EMPTY);

					if (container.getSlot(1).getItem().getCount() > Util
							.getWoodsFor(item.getRegistryName().toString())) {
						container.getSlot(0).set(new ItemStack(item));
					}
				}
			}
		});

		context.setPacketHandled(true);
	}
}
