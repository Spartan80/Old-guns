package com.jg.oldguns.client.handlers;

import com.jg.oldguns.config.Config;

public class MuzzleFlashHandler {
	
	private int timer;
	
	public MuzzleFlashHandler() {
		
	}
	
	public void resetTimer() {
		timer = 1;//Config.CLIENT.muzzleFlashTime.get();
	}
	
	public void tick() {
		if(timer > 0) {
			timer--;
			//System.out.println("Reducing timer");
			//System.out.println("Timer: " + timer + " is ready");
		}
	}
	
	public boolean canRenderMuzzleFlash() {
		return timer > 0;
	}
}
