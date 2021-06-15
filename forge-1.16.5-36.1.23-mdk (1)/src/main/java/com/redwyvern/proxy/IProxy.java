package com.redwyvern.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public interface IProxy {
	public void registerModEventListeners(IEventBus bus);

	public void registerForgeEventListeners(IEventBus bus);

	public void init();

	public PlayerEntity getPlayerFromContext(Context context);
}
