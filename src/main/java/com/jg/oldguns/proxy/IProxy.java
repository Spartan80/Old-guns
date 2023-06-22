package com.jg.oldguns.proxy;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkEvent.Context;

public interface IProxy {
	public void registerModEventListeners(IEventBus bus);

	public void registerForgeEventListeners(IEventBus bus);

	public Player getPlayerFromContext(Context context);
}
