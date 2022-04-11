package com.jg.oldguns.client.handlers;

import net.minecraft.client.Minecraft;

public class AimHandler {
	public static final float MAXPROGRESS = 4;
	public static float renderPartialTicks;
	public float aimProgress, prevAimProgress;

	public AimHandler() {

	}

	public void updateAimProgress(boolean aim) {
		prevAimProgress = aimProgress;
		if (aim) {
			if (aimProgress < MAXPROGRESS) {
				aimProgress += renderPartialTicks;
				if (aimProgress > MAXPROGRESS) {
					aimProgress = MAXPROGRESS;
				}
			}
		} else {
			if (aimProgress > 0) {
				aimProgress -= renderPartialTicks;
				if (aimProgress < 0) {
					aimProgress = 0;
				}
			}
		}
	}
	
	public void setAimProgress(float aim) {
		this.prevAimProgress = aimProgress;
		aimProgress = aim;
	}

	public float getProgress() {
		return (prevAimProgress + (aimProgress - prevAimProgress) * (prevAimProgress == 0 || prevAimProgress == MAXPROGRESS ? 0 : AimHandler.renderPartialTicks)) / MAXPROGRESS;
	}

	public boolean isAiming() {
		return aimProgress != 0;
	}

	public boolean isFullAimingProgress() {
		return aimProgress >= 1;
	}
	
	public void reset() {
		aimProgress = 0;
		prevAimProgress = 0;
	}
	
}
