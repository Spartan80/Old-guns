package com.jg.oldguns.client.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class AimHandler {
	public static final float MAXPROGRESS = 4;
	private float test, prevTest;
	public float aimProgress, prevAimProgress;

	public AimHandler() {

	}

	public void updateAimProgress(boolean aim) {
		float renderPartialTicks = Minecraft.getInstance().getDeltaFrameTime();
		if (aim) {
			if (aimProgress < MAXPROGRESS) {
				prevAimProgress = aimProgress;
				float blend = (float) (1f - Math.pow(1f - 0.01f, renderPartialTicks * 15f));
				aimProgress = Mth.lerp(blend, aimProgress, 1);
				aimProgress += prevAimProgress + (aimProgress - prevAimProgress + renderPartialTicks * 0.6f) ;
				if (aimProgress > 0.9f) {
					aimProgress = 1;
				}
				if (aimProgress >= 1) {
					aimProgress = 1;
				}
			}
		} else {
			if (aimProgress > 0) {
				prevAimProgress = aimProgress;
				float blend = (float) (1f - Math.pow(1f - 0.1f, renderPartialTicks * 20f));
				aimProgress = Mth.lerp(blend, aimProgress, 0);
				if (aimProgress < 0.1f) {
					aimProgress = 0;
				}
				if (aimProgress < 0) {
					aimProgress = 0;
				}
			}
		}
	}

	public void test(float partialTicks) {
		
	}
	
	public void setAimProgress(float aim) {
		this.prevAimProgress = aimProgress;
		aimProgress = aim;
	}

	public float getProgress() {
		return aimProgress;// (float)MathHelper.smoothstep((double)(aimProgress/MAXPROGRESS));
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
