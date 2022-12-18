package com.jg.oldguns.client.handlers;

public class AimHandler extends BasicHandler {

	public AimHandler() {
		MAX = 3f;
	}
	
	public void tick(boolean aiming) {
		prev = prog;
		if(aiming) {
			if (prog < MAX) {
				prog += ClientHandler.partialTicks;
				if (prog > MAX) {
					prog = MAX;
				}
			}
		}else {
			if (prog > 0) {
				prog -= ClientHandler.partialTicks;
				if (prog < 0) {
					prog = 0;
				}
			}
		}
	}
	
}
