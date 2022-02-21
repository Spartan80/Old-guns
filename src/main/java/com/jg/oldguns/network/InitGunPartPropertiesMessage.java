package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.events.GunPartPropertiesRegistryEvent;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class InitGunPartPropertiesMessage {
	
	public InitGunPartPropertiesMessage() {
		
	}

	public static void encode(InitGunPartPropertiesMessage msg, PacketBuffer buf) {
	}

	public static InitGunPartPropertiesMessage decode(PacketBuffer buf) {
		return new InitGunPartPropertiesMessage();
	}

	public static void handle(InitGunPartPropertiesMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();
		context.enqueueWork(() -> {
			GunPartPropertiesRegistryEvent partRegEvent = new GunPartPropertiesRegistryEvent();
			MinecraftForge.EVENT_BUS.start();
			MinecraftForge.EVENT_BUS.post(partRegEvent);
			MinecraftForge.EVENT_BUS.shutdown();
		});
		context.setPacketHandled(true);
	}
}
