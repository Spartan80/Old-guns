package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.containers.GunContainer;
import com.jg.oldguns.guns.GunItem;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkHooks;

public class OpenGunGuiMessage {

	public OpenGunGuiMessage() {

	}

	public static void encode(OpenGunGuiMessage msg, FriendlyByteBuf buf) {

	}

	public static OpenGunGuiMessage decode(FriendlyByteBuf buf) {
		return new OpenGunGuiMessage();
	}

	public static void handle(OpenGunGuiMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ItemStack stack = player.getMainHandItem();

			if (stack.getItem() instanceof GunItem) {
				System.out.println("opening gui");
				NetworkHooks.openGui(player, new SimpleMenuProvider((id, pi, cplayer) -> new GunContainer(id, pi),
						new TranslatableComponent("gui.oldguns.gun_gui")));
			}
		});
		context.setPacketHandled(true);
	}
}
