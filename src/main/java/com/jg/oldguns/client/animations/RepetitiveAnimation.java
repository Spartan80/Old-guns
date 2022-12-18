package com.jg.oldguns.client.animations;

public class RepetitiveAnimation extends Animation {

	protected Lambda lambda;
	
	public RepetitiveAnimation(String name, String gunModel) {
		super(name, gunModel);
	}
	
	public RepetitiveAnimation cycle(Lambda cycle) {
		this.lambda = cycle;
		return this;
	}
	
	public RepetitiveAnimation endCycle(int times) {
		for(int i = 0; i < times; i++) {
			lambda.doCycle(this);
		}
		return this;
	}
	
	@Override
	public Animation end() {
		return super.end();
	}

	public interface Lambda {
		public void doCycle(RepetitiveAnimation ra);
	}
	
}
