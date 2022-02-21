package com.jg.oldguns.client.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class SprintHandler {

	public static final float MAXPROGRESS = 4;
	public float sprintProgress, prevSprintProgress, output;

	public SprintHandler() {

	}

	public void updateSprintProgress(boolean sprint) {
		float renderPartialTicks = Minecraft.getInstance().getDeltaFrameTime();
		if (sprint) {

			if (sprintProgress < MAXPROGRESS) {
				prevSprintProgress = sprintProgress;
				// sprintProgress += MathHelper.smoothstep(1 * renderPartialTicks);
				float blend = (float) (1f - Math.pow(1f - 0.1f, renderPartialTicks * 20f));
				sprintProgress = MathHelper.lerp(blend, sprintProgress, 1);
				if (sprintProgress > 0.9f) {
					sprintProgress = 1;
				}
				if (sprintProgress >= 1) {
					sprintProgress = 1;
				}
				/*
				 * sprintProgress += (renderPartialTicks + (sprintProgress -
				 * prevSprintProgress)); //System.out.println("aiming: sprintProgress: " +
				 * sprintProgress + " prevSprintProgress: " + prevSprintProgress);
				 * if(sprintProgress > MAXPROGRESS) { sprintProgress = MAXPROGRESS; }
				 */
			}
			// System.out.println("output: " + output);
			// System.out.println("try: aim - prev: " + (sprintProgress -
			// prevSprintProgress));
		} else {
			if (sprintProgress > 0) {
				prevSprintProgress = sprintProgress;
				/*
				 * sprintProgress -= (renderPartialTicks + (sprintProgress -
				 * prevSprintProgress)); //System.out.println("aiming: sprintProgress: " +
				 * sprintProgress + " prevSprintProgress: " + prevSprintProgress);
				 * if(sprintProgress < renderPartialTicks) { sprintProgress = 0; }
				 */
				float blend = (float) (1f - Math.pow(1f - 0.1f, renderPartialTicks * 20f));
				sprintProgress = MathHelper.lerp(blend, sprintProgress, 0);
				if (sprintProgress < 0.1f) {
					sprintProgress = 0;
				}
				// sprintProgress -= MathHelper.smoothstep(1 * renderPartialTicks);
				if (sprintProgress <= 0) {
					sprintProgress = 0;
				}
			}
		}
	}

	public float getProgress() {
		// return aimProgress/MAXPROGRESS;
		// return (float)(1 - Math.pow(1 - (sprintProgress/MAXPROGRESS), 4));
		// return output;
		return sprintProgress;// (float)MathHelper.smoothstep((double)(sprintProgress/MAXPROGRESS));
	}

	public void reset() {
		sprintProgress = 0;
		prevSprintProgress = 0;
	}
	
}
