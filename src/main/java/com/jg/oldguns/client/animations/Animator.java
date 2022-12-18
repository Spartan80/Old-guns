package com.jg.oldguns.client.animations;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.utils.MathUtils;
import com.mojang.logging.LogUtils;

import net.minecraft.util.Mth;

public class Animator {

	public static Map<String, Easing> easings = new HashMap<>();
	protected int prevStartTick;
	protected int prevDur;
	protected int current;
	protected float tick;
	protected float prog;
	protected float prevTick;
	
	protected boolean updateParts;
	
	protected Animation animation;
	protected Easing easing;
	
	protected GunModel model;
	
	protected Map<GunModelPart, float[]> currentTranslations;
	protected Map<GunModelPart, float[]> currentRotations;
	protected Map<GunModelPart, float[]> prevTranslations;
	protected Map<GunModelPart, float[]> prevRotations;
	
	public Animator(GunModel model) {
		this.model = model;
		currentTranslations = new HashMap<>();
		currentRotations = new HashMap<>();
		prevTranslations = new HashMap<>();
		prevRotations = new HashMap<>();
		this.easing = (x) -> x;
		for(int i = 0; i < model.getGunParts().size(); i++){
            this.prevTranslations.put(model.getGunParts().get(i), 
            		new float[] { 0, 0, 0 });
            this.prevRotations.put(model.getGunParts().get(i), 
                new float[] { 0, 0, 0 });
        }
		/*if(model.getPart("rightarm") != null) {
    		LogUtils.getLogger().info("Start Rightarm: " + 
    				Arrays.toString(model.getPart("rightarm").getTransform().rot) + " prev: " + 
    				Arrays.toString(prevRotations.get(model.getPart("rightarm"))) + " current: " + 
    				Arrays.toString(currentRotations.get(model.getPart("rightarm"))));
    	}*/
		animation = null;
		easings.put("easeInOut", (x) -> { return 0; });
	}

	public void setAnimation(Animation animation) {
		current = 0;
		this.animation = animation;
		this.tick = 0;
		this.prevTick = 0;
		this.prog = 0;
		if(!animation.keyframes.isEmpty()) {
			currentTranslations = animation.keyframes.get(current).translations;
			currentRotations = animation.keyframes.get(current).rotations;
			this.prevDur = 0;
			this.easing = easings.getOrDefault(animation.keyframes.get(current).easing, (x) -> x);
			/*if(model.getPart("rightarm") != null) {
	    		LogUtils.getLogger().info("Start Rightarm: " + 
	    				Arrays.toString(model.getPart("rightarm").getTransform().rot) + " prev: " + 
	    				Arrays.toString(prevRotations.get(model.getPart("rightarm"))) + " current: " + 
	    				Arrays.toString(currentRotations.get(model.getPart("rightarm"))));
	    	}*/
		}
	}
	
	public void switchUpdateParts() {
		this.updateParts = !this.updateParts;
	}
	
	public void update() {
		if(this.animation != null && model.canPlayAnimation()){
	            this.prevTick = this.tick;
	            this.tick += 1;
	            Keyframe kf = this.animation.keyframes.get(current);
	            if(this.tick-kf.startTick > kf.dur){
	                this.tick = kf.startTick + kf.dur;
	                this.currentTranslations = kf.translations;
	                this.currentRotations = kf.rotations;
	                this.prevStartTick = kf.startTick;
	            }
	            float prosTick = this.tick - kf.startTick;
	            float prosPrevTick = this.prevTick - kf.startTick;
	            this.prog = (prosPrevTick + (prosTick - prosPrevTick) * 
	            		ClientHandler.partialTicks) / kf.dur;
	            if(this.tick-kf.startTick == kf.dur){
	                this.prog = 1.0f;
	            }
	            /*String key = "easeInQuint";
	            this.prog = easings.getOrDefault(key, (x) -> x).get(this.prog);
	            LogUtils.getLogger().info("Easing: " + easings.containsKey(key));*/
	            doEasing();
	        	LogUtils.getLogger().info("Prog: " + prog);
	        	for(Entry<String, Easing> e : easings.entrySet()) {
	        		if(e.getValue() == easing) {
	        			LogUtils.getLogger().info("Easing: " + e.getKey());
	        		}
	        	}
	            for(GunModelPart part : currentTranslations.keySet()) {
	            	if(!prevTranslations.containsKey(part)) {
	            		prevTranslations.put(part, new float[] { 0, 0, 0 });
	            	}
	            	/*LogUtils.getLogger().info("t prev: " + 
	            			Arrays.toString(prevTranslations.get(part)) + " current: " + 
	            			Arrays.toString(currentTranslations.get(part)));*/
	            	try {
	            	part.getTransform().pos[0] = Mth.lerp(this.prog, this.prevTranslations
	            			.get(part)[0], this.currentTranslations.get(part)[0]);
	            	part.getTransform().pos[1] = Mth.lerp(this.prog, this.prevTranslations
	            			.get(part)[1], this.currentTranslations.get(part)[1]);
	            	part.getTransform().pos[2] = Mth.lerp(this.prog, this.prevTranslations
	            			.get(part)[2], this.currentTranslations.get(part)[2]);
	            	} catch (NullPointerException e) {
						e.printStackTrace();
					}
	            }
	            for(GunModelPart part : currentRotations.keySet()) {
	            	if(!prevRotations.containsKey(part)) {
	            		prevRotations.put(part, new float[] { 0, 0, 0 });
	            	}
	            	/*LogUtils.getLogger().info("r prev: " + 
	            			Arrays.toString(prevRotations.get(part)) + " current: " + 
	            			Arrays.toString(currentRotations.get(part)));*/
	            	/*if(part.getName().equals("rightarm")) {
	            		LogUtils.getLogger().info("Rightarm: " + 
	            				Arrays.toString(part.getTransform().rot) + " prev: " + 
	            				Arrays.toString(prevRotations.get(part)) + " current: " + 
	            				Arrays.toString(currentRotations.get(part)));
	            	}*/
	            	try {
	            	part.getTransform().rot[0] = MathUtils.rotLerp(this.prog, this.prevRotations
	            			.get(part)[0], this.currentRotations.get(part)[0]);
	            	part.getTransform().rot[1] = MathUtils.rotLerp(this.prog, this.prevRotations
	            			.get(part)[1], this.currentRotations.get(part)[1]);
	            	part.getTransform().rot[2] = MathUtils.rotLerp(this.prog, this.prevRotations
	            			.get(part)[2], this.currentRotations.get(part)[2]);
	            	} catch (NullPointerException e) {
						e.printStackTrace();
					}
	            }
	            if(this.tick-kf.startTick >= kf.dur){
	                this.current++;
	                if(this.current+1 > this.animation.keyframes.size()){ 
	                	finishAll();
	                    return;
	                }
	                this.easing = easings.getOrDefault(kf.easing, (x) -> x);
	                LogUtils.getLogger().info("Keyframe easing: " + kf.easing);
	                this.updateCurrentMaps();
			}
        }
	}
	
	public void doEasing() {
		this.prog = easing.get(this.prog);
	}
	
	public void finishAll() {
		this.prevRotations = new HashMap<>();
    	this.prevTranslations = new HashMap<>();
    	this.currentRotations = new HashMap<>();
    	this.currentTranslations = new HashMap<>();
    	this.tick = 0;
    	this.prevTick = 0;
    	this.prevStartTick = 0;
    	this.prevDur = 0;
    	this.animation = null;
	}
	
	public void updateCurrentMaps() {
		this.prevTranslations = this.currentTranslations;
        this.prevRotations = this.currentRotations;
        this.currentTranslations = this.animation.keyframes.get(current)
            .translations;
        this.currentRotations = this.animation.keyframes.get(current)
            .rotations;
	}
	
	public void nextTick() {
		this.prevTick = tick;
		this.tick += 1;
		Keyframe kf = this.animation.keyframes.get(current);
    	if(this.tick-kf.startTick > kf.dur){
            this.currentTranslations = kf.translations;
            this.currentRotations = kf.rotations;
            this.prevStartTick = kf.startTick;
        }
    	float prosTick = this.tick - kf.startTick;
        float prosPrevTick = this.prevTick - kf.startTick;
    	this.prog = (prosPrevTick + (prosTick - prosPrevTick) 
    			* ClientHandler.partialTicks) / kf.dur;
    	if(this.tick-kf.startTick == kf.dur){
            this.prog = 1.0f;
        }
        for(GunModelPart part : currentTranslations.keySet()) {
        	if(!prevTranslations.containsKey(part)) {
        		prevTranslations.put(part, new float[] { part.getTransform().pos[0],
        				part.getTransform().pos[1], part.getTransform().pos[2] });
        	}
        	float[] part1 = this.prevTranslations.get(part);
        	float[] part2 = this.currentTranslations.get(part);
        	try {
        	part.getTransform().pos[0] = Mth.lerp(prog, part1[0], 
        			part2[0]);
        	part.getTransform().pos[1] = Mth.lerp(prog, this.prevTranslations
        			.get(part)[1], this.currentTranslations.get(part)[1]);
        	part.getTransform().pos[2] = Mth.lerp(prog, this.prevTranslations
        			.get(part)[2], this.currentTranslations.get(part)[2]);
        	} catch (NullPointerException e) {
				e.printStackTrace();
			}
        }
        for(GunModelPart part : currentRotations.keySet()) {
        	if(!prevRotations.containsKey(part)) {
        		prevRotations.put(part, new float[] { part.getTransform().rot[0],
        				part.getTransform().rot[1], part.getTransform().rot[2] });
        	}
        	float[] part1 = this.prevRotations.get(part);
        	float[] part2 = this.currentRotations.get(part);
        	try {
        	part.getTransform().rot[0] = MathUtils.rotLerp(this.prog, part1[0], 
        			part2[0]);
        	part.getTransform().rot[1] = MathUtils.rotLerp(this.prog, this.prevRotations
        			.get(part)[1], this.currentRotations.get(part)[1]);
        	part.getTransform().rot[2] = MathUtils.rotLerp(this.prog, this.prevRotations
        			.get(part)[2], this.currentRotations.get(part)[2]);
        	} catch (NullPointerException e) {
				e.printStackTrace();
			}
        }
        if(this.tick-kf.startTick >= kf.dur){
            this.current++;
            if(this.current+1 > this.animation.keyframes.size()){
            	finishAll();
                return;
            }
            this.updateCurrentMaps();
        }
        /*LogUtils.getLogger().info("Current: " + current + " prog: " + prog + 
        		" tick: " + tick + 
        		" kf startTick: " + kf.startTick + " kf visualTick: " + kf.startVisualTick
        		+ " tick-kf.startTick: " + (tick-kf.startTick) + " kf.dur: " + kf.dur);
		*/
        LogUtils.getLogger().info("Tick: " + tick);
	}
	
	public void prevTick() {
		this.prevTick = tick;
		this.tick -= 1;
		Keyframe kf = this.animation.keyframes.get(current);
    	if(this.tick-kf.startTick <= -1){
            this.currentTranslations = kf.translations;
            this.currentRotations = kf.rotations;
            this.prevStartTick = kf.startTick;
        }
    	if(this.tick < 0) {
    		this.tick = 0;
    	}
    	float prosTick = this.tick - kf.startTick;
        float prosPrevTick = this.prevTick - kf.startTick;
    	this.prog = (prosPrevTick + (prosTick - prosPrevTick)  * 
    			ClientHandler.partialTicks) / kf.dur;
    	if(this.tick-kf.startTick == kf.dur){
            this.prog = 1.0f;
        }
    	
        for(GunModelPart part : currentTranslations.keySet()) {
        	if(!prevTranslations.containsKey(part)) {
        		prevTranslations.put(part, new float[] { part.getTransform().pos[0],
        				part.getTransform().pos[1], part.getTransform().pos[2] });
        	}
        	float[] part1 = this.prevTranslations.get(part);
        	float[] part2 = this.currentTranslations.get(part);
        	try {
        	part.getTransform().pos[0] = Mth.lerp(prog, part1[0], 
        			part2[0]);
        	part.getTransform().pos[1] = Mth.lerp(prog, part1[1], part2[1]);
        	part.getTransform().pos[2] = Mth.lerp(prog, part1[2], part2[2]);
        	} catch (NullPointerException e) {
				e.printStackTrace();
			}
        	/*LogUtils.getLogger().info("Name: " + part.getName() + " transform: " + 
        			part.getTransform().toString() + " part1: " 
        			+ Arrays.toString(part1) + " part2: " + Arrays.toString(part2) +
        			" currentTransform: " + Arrays.toString(this.currentTranslations.get(part)) + 
        			" lerp values: x: " + Mth.lerp(prog, part1[0], 
                			part2[0]) + " y: " + Mth.lerp(prog, part1[1], 
                        			part2[1]) + " z: " + Mth.lerp(prog, part1[2], 
                                			part2[2]));*/
        }
        for(GunModelPart part : currentRotations.keySet()) {
        	if(!prevRotations.containsKey(part)) {
        		prevRotations.put(part, new float[] { part.getTransform().rot[0],
        				part.getTransform().rot[1], part.getTransform().rot[2] });
        	}
        	float[] part1 = this.prevRotations.get(part);
        	float[] part2 = this.currentRotations.get(part);
        	try {
        		part.getTransform().rot[0] = MathUtils.rotLerp(prog, part1[0], 
            			part2[0]);
            	part.getTransform().rot[1] = MathUtils.rotLerp(prog, part1[1], part2[1]);
            	part.getTransform().rot[2] = MathUtils.rotLerp(prog, part1[2], part2[2]);
        	} catch (NullPointerException e) {
				e.printStackTrace();
			}
        }
        if(this.tick-kf.startTick <= -1){
        	this.current--;
        	if(this.current <= 0) {
        		this.current = 0;
        		this.prevTranslations = new HashMap<>();
        		this.prevRotations = new HashMap<>();
        		for(int i = 0; i < model.getGunParts().size(); i++){
                    this.prevTranslations.put(model.getGunParts().get(i), 
                    		new float[] { 0, 0, 0 });
                    this.prevRotations.put(model.getGunParts().get(i), 
                        new float[] { 0, 0, 0 });
                }
                this.currentTranslations = this.animation.keyframes.get(current)
                    .translations;
                this.currentRotations = this.animation.keyframes.get(current)
                    .rotations;
                //LogUtils.getLogger().info("Current <= 0");
        	} else if(current > 0){
        		try {
        		this.prevTranslations = this.animation.keyframes.get(current-1)
        				.translations;
        		this.prevRotations = this.animation.keyframes.get(current-1)
        				.rotations;
        		this.currentTranslations = this.animation.keyframes.get(current)
                        .translations;
                this.currentRotations = this.animation.keyframes.get(current)
                        .rotations;
        		} catch(IndexOutOfBoundsException e) {
        			e.printStackTrace();
        		}
        	}
        }
        LogUtils.getLogger().info("Tick: " + tick);
	}

	public int getPrevStartTick() {
		return prevStartTick;
	}

	public void setPrevStartTick(int prevStartTick) {
		this.prevStartTick = prevStartTick;
	}

	public int getPrevDur() {
		return prevDur;
	}

	public void setPrevDur(int prevDur) {
		this.prevDur = prevDur;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public float getTick() {
		return tick;
	}

	public void setTick(float tick) {
		this.tick = tick;
	}

	public float getProg() {
		return prog;
	}

	public void setProg(float prog) {
		this.prog = prog;
	}

	public float getPrevTick() {
		return prevTick;
	}

	public void setPrevTick(float prevTick) {
		this.prevTick = prevTick;
	}

	public boolean shouldUpdateParts() {
		return updateParts;
	}

	public Map<GunModelPart, float[]> getCurrentTranslations() {
		return currentTranslations;
	}

	public void setCurrentTranslations(Map<GunModelPart, float[]> currentTranslations) {
		this.currentTranslations = currentTranslations;
	}

	public Map<GunModelPart, float[]> getCurrentRotations() {
		return currentRotations;
	}

	public void setCurrentRotations(Map<GunModelPart, float[]> currentRotations) {
		this.currentRotations = currentRotations;
	}

	public Map<GunModelPart, float[]> getPrevTranslations() {
		return prevTranslations;
	}

	public void setPrevTranslations(Map<GunModelPart, float[]> prevTranslations) {
		this.prevTranslations = prevTranslations;
	}

	public Map<GunModelPart, float[]> getPrevRotations() {
		return prevRotations;
	}

	public void setPrevRotations(Map<GunModelPart, float[]> prevRotations) {
		this.prevRotations = prevRotations;
	}

	public Animation getAnimation() {
		return animation;
	}
	
	public interface Easing{
		public float get(float x);
	}
	
}
