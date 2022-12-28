package com.jg.oldguns.client.handlers;

public class RecoilHandler extends BasicHandler {

	private float progD, prevD, MAXD;
	private boolean shoot;
	
	public RecoilHandler() {
		
	}
	
	public void tick() {
		prev = prog;
		prevD = progD;
		MAX = 1f;
		MAXD = 1f;
		if(shoot) {
			if (prog < MAX) {
				prog += ClientHandler.partialTicks;
				if (prog > MAX) {
					prog = MAX;
				}
			} else {
				shoot = false;
				prevD = progD = MAXD;
			}
		}else {
			if (prog > 0) {
				prog -= ClientHandler.partialTicks;
				if (prog < 0) {
					prog = 0;
				}
			}else if (progD > 0) {
				progD -= ClientHandler.partialTicks;
				if (progD < 0) {
					progD = 0;
				}
			}
		}
	}
	
	public void setShoot() {
		this.shoot = true;
	}
	
	@Override
	public float getProgress() {
		float dv = (prevD + (progD - prevD)
				* (ClientHandler.partialTicks)) / MAXD;
		return shoot ? super.getProgress() : dv;
	}
	
}
