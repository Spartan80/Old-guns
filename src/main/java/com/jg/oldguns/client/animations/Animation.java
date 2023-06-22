package com.jg.oldguns.client.animations;

import java.util.ArrayList;
import java.util.List;

import com.jg.oldguns.client.animations.parts.GunModelPart;

public class Animation {
	
	protected String name;
	protected String gunModel;
	protected int dur;
	protected List<Keyframe> keyframes;
	protected int startKeyframeIndex;
	protected int startTick;
	protected AnimationType type;
	
	public Animation(String name, String gunModel) {
		this(name, gunModel, 0, AnimationType.NONE);
	}
	
	public Animation(String name, String gunModel, AnimationType type) {
		this(name, gunModel, 0, type);
	}
	
	public Animation(String name, String gunModel, int dur, AnimationType type) {
		this.name = name;
		this.gunModel = gunModel;
		this.dur = dur;
		this.keyframes = new ArrayList<>();
		this.startKeyframeIndex = -1;
		this.type = type;
	}
	
	public static Animation empty(String name, String id) {
		return new Animation(name, id).startKeyframe(4).end();
	}
	
	public Animation startKeyframe(int dur) {
		this.startKeyframeIndex++;
		Keyframe kf = new Keyframe(dur);
		kf.startTick = this.startTick;
		kf.startVisualTick = this.startTick + dur;
		this.keyframes.add(kf);
		this.startTick += dur;
		return this;
	}
	
	public Animation startKeyframe(int dur, String easing) {
		this.startKeyframeIndex++;
		Keyframe kf = new Keyframe(dur, easing);
		kf.startTick = this.startTick;
		kf.startVisualTick = this.startTick + dur;
		this.keyframes.add(kf);
		this.startTick += dur;
		return this;
	}
	
	public Animation translate(GunModelPart part, float x, float y, float z) {
		this.keyframes.get(startKeyframeIndex).translations.put(part, 
				new float[] { x, y, z });
		return this;
	}
	
	public Animation rotate(GunModelPart part, float x, float y, float z) {
		this.keyframes.get(startKeyframeIndex).rotations.put(part, 
				new float[] { x, y, z });
		return this;
	}
	
	public Animation end() {
		for(Keyframe kf : keyframes) {
			this.dur += kf.dur;
		}
		return this;
	}

	public int getDuration() {
		return dur;
	}

	public void setDuration(int dur) {
		this.dur = dur;
	}

	public List<Keyframe> getKeyframes() {
		return keyframes;
	}

	public void setKeyframes(List<Keyframe> keyframes) {
		this.keyframes = keyframes;
	}
	
	public Animation setType(AnimationType type) {
		this.type = type;
		return this;
	}
	
	public AnimationType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getGunModel() {
		return gunModel;
	}
	
	public void set(Animation anim) {
		this.dur = anim.dur;
		this.startKeyframeIndex = anim.startKeyframeIndex;
		this.startTick = anim.startTick;
		this.keyframes = anim.keyframes;
		this.name = anim.name;
		this.gunModel = anim.gunModel;
	}
	
	public static enum AnimationType {
		RELOAD, LOOK, SHOOT, MELEEHIT, NONE;
	}
	
}
