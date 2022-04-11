package com.jg.oldguns.proxy;

import com.jg.oldguns.client.ClientEventHandler;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkEvent.Context;

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
	public Player getPlayerFromContext(Context context) {
		return context.getSender();
	}

}
