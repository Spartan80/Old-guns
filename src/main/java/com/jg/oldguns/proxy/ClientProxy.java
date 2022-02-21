package com.jg.oldguns.proxy;

import com.jg.oldguns.client.ClientEventHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ClientProxy implements IProxy {

	@Override
	public void registerModEventListeners(IEventBus bus) {
		ClientEventHandler.registerModEventListeners(bus);
	}

	@Override
	public void registerForgeEventListeners(IEventBus bus) {
		ClientEventHandler.registerForgeEventListeners(bus);
	}

	@Override
	public PlayerEntity getPlayerFromContext(Context context) {

		return context.getSender();
	}

}
