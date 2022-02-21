package com.jg.oldguns.animations.events;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.models.GunModel;

import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class AnimationEvent<T extends GunModel> extends Event {
	protected Animation animation;
	private final T gun;

	AnimationEvent(T gun, Animation animation) {
		this.gun = gun;
		this.animation = animation;
	}

	public T getGun() {
		return this.gun;
	}

	public Animation getAnimation() {
		return this.animation;
	}

	@Cancelable
	public static class Start<T extends GunModel> extends AnimationEvent<T> {
		public Start(T gun, Animation animation) {
			super(gun, animation);
		}

		public void setAnimation(Animation animation) {
			this.animation = animation;
		}
	}

	public static class Tick<T extends GunModel> extends AnimationEvent<T> {
		protected float tick;

		public Tick(T gun, Animation animation, float tick) {
			super(gun, animation);
			this.tick = tick;
		}

		public float getTick() {
			return this.tick;
		}
	}
}
