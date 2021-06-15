package com.redwyvern.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ServerProxy implements IProxy {

	@Override
	public void registerModEventListeners(IEventBus bus) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerForgeEventListeners(IEventBus bus) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public PlayerEntity getPlayerFromContext(Context context) {
		// TODO Auto-generated method stub
		return context.getSender();
	}

}
