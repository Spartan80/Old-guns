package com.jg.oldguns.client.animations;

import java.util.ArrayList;
import java.util.List;

import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.utils.Utils;
import com.mojang.logging.LogUtils;

public class RepetitiveAnimation extends Animation {

	public RepetitiveAnimation(String name, String gunModel) {
		super(name, gunModel);
	}
	
	@Override
	public RepetitiveAnimation startKeyframe(int dur) {
		this.startKeyframeIndex++;
		Keyframe kf = new Keyframe(dur);
		kf.startTick = this.startTick;
		kf.startVisualTick = this.startTick + dur;
		this.keyframes.add(kf);
		this.startTick += dur;
		return this;
	}
	
	@Override
	public RepetitiveAnimation startKeyframe(int dur, String easing) {
		this.startKeyframeIndex++;
		Keyframe kf = new Keyframe(dur, easing);
		kf.startTick = this.startTick;
		kf.startVisualTick = this.startTick + dur;
		this.keyframes.add(kf);
		this.startTick += dur;
		return this;
	}
	
	@Override
	public RepetitiveAnimation translate(GunModelPart part, float x, float y, float z) {
		this.keyframes.get(startKeyframeIndex).translations.put(part, 
				new float[] { x, y, z });
		return this;
	}
	
	@Override
	public RepetitiveAnimation rotate(GunModelPart part, float x, float y, float z) {
		this.keyframes.get(startKeyframeIndex).rotations.put(part, 
				new float[] { x, y, z });
		return this;
	}
	
	@Override
	public RepetitiveAnimation end() {
		for(Keyframe kf : keyframes) {
			this.dur += kf.dur;
			dur += kf.dur;
		}
		return this;
	}
	
	public RepetitiveAnimation type(int type) {
		keyframes.get(startKeyframeIndex).type = type;
		return this;
	}
	
	public void setTimes(int times) {
		// Set duration to 0 so it doesnt stack 
		dur = 0;
		int lastType = 0;
		boolean oneTime = false;
		boolean addToCycle = false;
		List<Keyframe> cycle = new ArrayList<>();
		List<Keyframe> finalList = new ArrayList<>();
		// Looping over keyframes
		for(int i = 0; i < keyframes.size(); i++) {
			Keyframe kf = keyframes.get(i);
			// With this if we avoid to stack cycles
			if(kf.type == 1 && !oneTime) {
				oneTime = true;
				addToCycle = true;
			}
			// End of the cycle
			if(kf.type == 0 && lastType == 2) {
				oneTime = false;
			}
			// Adding to the cycle
			if(addToCycle) {
				cycle.add(kf);
			} else if(!oneTime) {
				finalList.add(kf);
			}
			// Flag for the end of the cycle
			if(kf.type == 2) {
				addToCycle = false;
				// Adding cycle frames to the final keyframes
				for(int j = 0; j < times; j++) {
					if(j == 0) {
						finalList.addAll(cycle);
					} else {
						// Copying keyframes
						for(int k = 0; k < cycle.size(); k++) {
							Keyframe kfc = cycle.get(k).copy();
							finalList.add(kfc);
						}
					}
				}
				// Here we clear the cycle so is doesnt stack
				cycle.clear();
			}
			// We need this to know when the cycle ends
			lastType = kf.type;
		}
		keyframes.clear();
		keyframes.addAll(finalList);
		LogUtils.getLogger().info("Size: " + keyframes.size());

		// Updating keyframe data(startTick, startVisualTick and animation duration)
		Utils.updateKeyframesFromAnimation(this);
	}
	
}
