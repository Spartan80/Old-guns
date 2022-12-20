package com.jg.oldguns.client.handlers;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.DistExecutor;

public class StartHitmarkerUtil {

	public static DistExecutor.SafeRunnable start() {
		return new DistExecutor.SafeRunnable() {
			@Override
			public void run() {
				//LogUtils.getLogger().info("hitmarker");
				ClientsHandler.getClient(Minecraft.getInstance().getUser()).getHitmarker().reset();
			}
		};
	}
}
