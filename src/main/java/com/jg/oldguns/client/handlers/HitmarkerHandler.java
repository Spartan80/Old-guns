package com.jg.oldguns.client.handlers;

import com.jg.oldguns.config.Config;
import com.mojang.logging.LogUtils;

public class HitmarkerHandler {

	public int hitmarkerTime;

	public HitmarkerHandler() {
		hitmarkerTime = 0;
	}

	public int getHitmarkerTime() {
		return hitmarkerTime;
	}

	public void setHitmarkerTime(int hit) {
		this.hitmarkerTime = hit;
	}

	public void reset() {
		this.hitmarkerTime = Config.CLIENT.hitmarkerTime.get();
	}

	public void tick() {
		if (this.hitmarkerTime > 0) {
			this.hitmarkerTime--;
			LogUtils.getLogger().info("HitmarkerTime: " + hitmarkerTime);
		}
	}

	public void resetTo0() {
		hitmarkerTime = 0;
	}

}
