package com.jg.oldguns.client.render;

import com.jg.oldguns.entities.Bullet;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BulletRender extends EntityRenderer<Bullet> {

	public BulletRender(EntityRendererManager p_i46179_1_) {
		super(p_i46179_1_);
	}

	@Override
	public ResourceLocation getTextureLocation(Bullet p_110775_1_) {
		return null;
	}

}
