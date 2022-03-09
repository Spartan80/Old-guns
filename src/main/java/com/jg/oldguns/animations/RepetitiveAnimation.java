package com.jg.oldguns.animations;

public class RepetitiveAnimation extends Animation{

	protected int times;
	
	public RepetitiveAnimation(int duration, int times) {
		super(duration);
		this.times = times;
	}
	
	public void anim(doAnim toDo) {
		for(int i = 0 ; i < times; i++) {
			toDo.test();
		}
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setTimes(int times) {
		this.times = times;
	}
	
	public int getTimes() {
		return this.times;
	}
	
	public interface doAnim{
		public void test();
	}
	
}
