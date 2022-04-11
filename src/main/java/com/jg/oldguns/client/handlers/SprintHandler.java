package com.jg.oldguns.client.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class SprintHandler {

	public static final float MAXPROGRESS = 4;
	public float sprintProgress, prevSprintProgress, output;

	public SprintHandler() {

	}

	public void updateSprintProgress(boolean sprint) {
		prevSprintProgress = sprintProgress;
		if (sprint) {
			if (sprintProgress < MAXPROGRESS) {
				sprintProgress += AimHandler.renderPartialTicks;
				if (sprintProgress > MAXPROGRESS) {
					sprintProgress = MAXPROGRESS;
				}
			}
		} else {
			if (sprintProgress > 0) {
				sprintProgress -= AimHandler.renderPartialTicks;
				if (sprintProgress < 0) {
					sprintProgress = 0;
				}
			}
		}
	}

	public float getProgress() {
		return (prevSprintProgress + (sprintProgress - prevSprintProgress) * (prevSprintProgress == 0 || prevSprintProgress == MAXPROGRESS ? 0 : AimHandler.renderPartialTicks)) / MAXPROGRESS;
	}

	public void reset() {
		sprintProgress = 0;
		prevSprintProgress = 0;
	}
	
}
