package com.jg.oldguns.client.handlers;

public class SprintHandler extends BasicHandler {
	
	public SprintHandler() {
		MAX = 3f;
	}
		
	public void tick(boolean sprinting) {
		prev = prog;
		if(sprinting) {
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
