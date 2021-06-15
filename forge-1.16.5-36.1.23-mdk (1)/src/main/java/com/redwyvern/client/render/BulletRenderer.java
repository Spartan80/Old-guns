package com.redwyvern.client.render;

import com.redwyvern.entities.projectiles.Bullet;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class BulletRenderer extends EntityRenderer<Bullet> {

	protected BulletRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		// TODO Auto-generated constructor stub
	}

	public static class RenderFactory implements IRenderFactory<Bullet> {

		@Override
		public EntityRenderer<? super Bullet> createRenderFor(EntityRendererManager manager) {
			// TODO Auto-generated method stub
			return new BulletRenderer(manager);
		}

	}

	@Override
	public ResourceLocation getTextureLocation(Bullet p_110775_1_) {
		// TODO Auto-generated method stub
		return null;
	}

}
