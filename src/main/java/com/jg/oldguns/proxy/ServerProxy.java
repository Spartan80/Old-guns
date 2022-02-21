package com.jg.oldguns.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ServerProxy implements IProxy {

	@Override
	public void registerModEventListeners(IEventBus bus) {

	}

	@Override
	public void registerForgeEventListeners(IEventBus bus) {
		
	}

	@Override
	public PlayerEntity getPlayerFromContext(Context context) {
		return context.getSender();
	}

}
