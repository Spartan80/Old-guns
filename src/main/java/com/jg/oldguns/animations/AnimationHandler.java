package com.jg.oldguns.animations;

import com.jg.oldguns.animations.events.AnimationEvent;
import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.debug.AnimWriter;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author iLexiconn
 * @since 1.0.0
 */
public enum AnimationHandler {
	INSTANCE;

	/**
	 * Updates all animations for a given gun
	 *
	 * @param gun the gun with an animation to be updated
	 * @param <T> the gun type
	 */
	@OnlyIn(Dist.CLIENT)
	public <T extends GunModel> void updateAnimations(T gun) {
		if (gun.getAnimation() == null) {
			gun.setAnimation(GunModel.EMPTY);
		} else if (gun.getAnimation() != GunModel.EMPTY) {
			if (gun.getAnimationTick() == 0) {
				AnimationEvent event = new AnimationEvent.Start<>(gun, gun.getAnimation());
				if (!MinecraftForge.EVENT_BUS.post(event)) {
					gun.setAnimation(event.getAnimation());
				}
			}

			if (!AnimWriter.debugAnimation()) {
				if (gun.getAnimationTick() < gun.getAnimation().getDuration()) {
					gun.setAnimationTick(gun.animTick + 1);
					MinecraftForge.EVENT_BUS
							.post(new AnimationEvent.Tick<>(gun, gun.getAnimation(), gun.getAnimationTick()));
				}
			}

			if (gun.getAnimationTick() >= gun.getAnimation().getDuration()) {
				gun.animTick = 0;
				gun.onAnimationEnd();
				gun.setAnimation(GunModel.EMPTY);
			}
		}
	}
}
