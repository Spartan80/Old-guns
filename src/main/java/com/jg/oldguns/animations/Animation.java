package com.jg.oldguns.animations;

/**
 * @author Alexthe666
 * @since 1.0.0
 */
public class Animation {
	
	@Deprecated
	protected int id;
	protected int duration;

	public Animation(int duration) {
		this.duration = duration;
	}

	/**
	 * @param id       the model id
	 * @param duration the model duration
	 * @return an model with the given id and duration
	 * @deprecated use {@link Animation#create(int)} instead.
	 */
	@Deprecated
	public static Animation create(int id, int duration) {
		Animation animation = Animation.create(duration);
		animation.id = id;
		return animation;
	}

	/**
	 * @param duration the model duration
	 * @return an model with the given id and duration
	 * @since 1.1.0
	 */
	public static Animation create(int duration) {
		return new Animation(duration);
	}

	public static RepetitiveAnimation createRep(int duration) {
		return new RepetitiveAnimation(duration, 0);
	}
	
	/**
	 * @return the id of this model
	 * @deprecated IDs aren't used anymore since 1.1.0.
	 */
	@Deprecated
	public int getID() {
		return this.id;
	}

	/**
	 * @return the duration of this model
	 */
	public int getDuration() {
		return this.duration;
	}

}
