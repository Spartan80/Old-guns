package com.jg.oldguns.client.handlers;

public class BasicHandler {

	protected float MAX = 4.0f;
	protected float prog, prev;
	
	public BasicHandler() {
		
	}
	
	public float getProgress() {
		float v = (prev + (prog - prev)
				* (prev == 0 || 
				prev == MAX ? 0 : 
				ClientHandler.partialTicks)) / MAX;
		//LogUtils.getLogger().info("Progress: " + v);
		return v;
	}
	
	public boolean hasStarted() {
		return prog == 0;
	}
	
}
