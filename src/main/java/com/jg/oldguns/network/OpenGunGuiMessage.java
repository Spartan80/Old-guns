package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.containers.GunContainer;
import com.jg.oldguns.guns.ItemGun;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkHooks;

public class OpenGunGuiMessage {

	public OpenGunGuiMessage() {

	}

	public static void encode(OpenGunGuiMessage msg, PacketBuffer buf) {

	}

	public static OpenGunGuiMessage decode(PacketBuffer buf) {
		return new OpenGunGuiMessage();
	}

	public static void handle(OpenGunGuiMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();
			ItemStack stack = player.getMainHandItem();

			if (stack.getItem() instanceof ItemGun) {
				System.out.println("opening gui");
				NetworkHooks.openGui(player,
						new SimpleNamedContainerProvider((id, pi, cplayer) -> new GunContainer(id, pi),
								new TranslationTextComponent("gui.jgguns.gun_gui")));
			}
		});
		context.setPacketHandled(true);
	}
}
