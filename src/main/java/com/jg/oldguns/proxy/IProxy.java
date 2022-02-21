package com.jg.oldguns.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public interface IProxy {
	public void registerModEventListeners(IEventBus bus);

	public void registerForgeEventListeners(IEventBus bus);

	public PlayerEntity getPlayerFromContext(Context context);

}
