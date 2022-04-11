package com.jg.oldguns.proxy;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkEvent.Context;

public class ServerProxy implements IProxy {

	@Override
	public void registerModEventListeners(IEventBus bus) {

	}

	@Override
	public void registerForgeEventListeners(IEventBus bus) {
		
	}

	@Override
	public Player getPlayerFromContext(Context context) {
		return context.getSender();
	}

}
