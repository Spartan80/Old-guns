package com.jg.oldguns.client.handlers;

import net.minecraft.client.Minecraft;

public class HitmarkerClientHandler {
	public static void handle() {
		ClientsHandler.getClient(Minecraft.getInstance().getUser()).getHitmarker().reset();
	}
}
