package com.jg.oldguns.client;

import net.minecraftforge.fml.DistExecutor;

public class StartHitmarkerUtil {

	public static DistExecutor.SafeRunnable start() {

		return new DistExecutor.SafeRunnable() {
			@Override
			public void run() {
				ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID()).getHitmarkerHandler().reset();
			}
		};
	}
}
