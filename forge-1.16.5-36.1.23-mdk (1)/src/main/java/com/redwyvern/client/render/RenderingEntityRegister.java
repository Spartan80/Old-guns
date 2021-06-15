package com.redwyvern.client.render;

import com.redwyvern.registries.EntityRegistries;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderingEntityRegister {
	public static void RenderingEntityRegister() {
		RenderingRegistry.registerEntityRenderingHandler(EntityRegistries.BULLET.get(),
				new BulletRenderer.RenderFactory());
	}
}
