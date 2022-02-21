package com.jg.oldguns.animations;

import java.util.HashMap;

import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author Alexthe666
 * @since 1.0.0
 */
@OnlyIn(Dist.CLIENT)
public class Animator {
	private int tempTick;
	private int prevTempTick;
	private GunModel gun;
	private boolean correctAnimation;
	private HashMap<GunModelPart, Transform> transformMap;
	private HashMap<GunModelPart, Transform> prevTransformMap;

	public Animator() {
		this.tempTick = 0;
		this.correctAnimation = false;
		this.transformMap = new HashMap<>();
		this.prevTransformMap = new HashMap<>();
	}

	/**
	 * @return a new Animator instance
	 */
	public static Animator create() {
		return new Animator();
	}

	/**
	 * Update the animations of this model.
	 *
	 * @param gun the gun instance
	 */
	public void update(GunModel gun) {
		this.tempTick = this.prevTempTick = 0;
		this.correctAnimation = false;
		this.gun = gun;
		this.transformMap.clear();
		this.prevTransformMap.clear();
	}

	/**
	 * Start an model
	 *
	 * @param animation the model instance
	 * @return true if it's the current model
	 */
	public boolean setAnimation(Animation animation) {
		this.tempTick = this.prevTempTick = 0;
		// System.out.println("gun null? " + (this.gun == null) + " animation null? " +
		// (gun.getAnimation() == null));
		this.correctAnimation = this.gun.getAnimation() == animation;
		return this.correctAnimation;
	}

	/**
	 * Start a keyframe for the current model.
	 *
	 * @param duration the keyframe duration
	 */
	public void startKeyframe(int duration) {
		if (!this.correctAnimation) {
			return;
		}
		this.prevTempTick = this.tempTick;
		this.tempTick += duration;
	}

	/**
	 * Add a static keyframe with a specific duration to the model.
	 *
	 * @param duration the keyframe duration
	 */
	public void setStaticKeyframe(int duration) {
		this.startKeyframe(duration);
		this.endKeyframe(true);
	}

	/**
	 * Reset this keyframe to its original state
	 *
	 * @param duration the keyframe duration
	 */
	public void resetKeyframe(int duration) {
		this.startKeyframe(duration);
		this.endKeyframe();
	}

	/**
	 * Rotate a box in the current keyframe. All the values are relative.
	 *
	 * @param box the box to rotate
	 * @param x   the x rotation
	 * @param y   the y rotation
	 * @param z   the z rotation
	 */
	public void rotate(GunModelPart box, float x, float y, float z) {
		if (!this.correctAnimation) {
			return;
		}
		this.getTransform(box).addRotation(x, y, z);
	}

	/**
	 * Move a box in the current keyframe. All the values are relative.
	 *
	 * @param box the box to move
	 * @param x   the x offset
	 * @param y   the y offset
	 * @param z   the z offset
	 */
	public void move(GunModelPart box, float x, float y, float z) {
		if (!this.correctAnimation) {
			return;
		}
		this.getTransform(box).addOffset(x, y, z);
	}

	private Transform getTransform(GunModelPart box) {
		return this.transformMap.computeIfAbsent(box, b -> new Transform());
	}

	/**
	 * End the current keyframe. this will reset all box transformations to their
	 * original state.
	 */
	public void endKeyframe() {
		this.endKeyframe(false);
	}

	public void endStationaryKeyframe() {
		this.endKeyframe(true);
	}

	private void endKeyframe(boolean stationary) {
		if (!this.correctAnimation) {
			return;
		}
		float animationTick = this.gun.getAnimationTick();

		if (animationTick >= this.prevTempTick && animationTick < this.tempTick) {
			if (stationary) {
				for (GunModelPart box : this.transformMap.keySet()) {
					Transform transform = this.transformMap.get(box);

					box.transform.setRotation(transform.rotationX, transform.rotationY, transform.rotationZ);
					box.transform.setOffset(transform.offsetX, transform.offsetY, transform.offsetZ);
				}
			} else {
				float tick = (animationTick - this.prevTempTick + Minecraft.getInstance().getFrameTime())
						/ (this.tempTick - this.prevTempTick);
				float inc = MathHelper.sin((float) (tick * Math.PI / 2.0F)), dec = 1.0F - inc;
				for (GunModelPart box : this.prevTransformMap.keySet()) {
					Transform transform = this.prevTransformMap.get(box);
					box.transform.addRotation(dec * transform.rotationX, dec * transform.rotationY,
							dec * transform.rotationZ);
					box.transform.addOffset(dec * transform.offsetX, dec * transform.offsetY, dec * transform.offsetZ);
				}
				for (GunModelPart box : this.transformMap.keySet()) {
					Transform transform = this.transformMap.get(box);
					box.transform.addRotation(inc * transform.rotationX, inc * transform.rotationY,
							inc * transform.rotationZ);
					box.transform.addOffset(inc * transform.offsetX, inc * transform.offsetY, inc * transform.offsetZ);
				}
			}
		}

		if (!stationary) {
			this.prevTransformMap.clear();
			this.prevTransformMap.putAll(this.transformMap);
			this.transformMap.clear();
		}
	}
}
