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
	
	public boolean isRepTime(int start, int step, float tick) {
		return tick >= start && (tick-start)%step == 0 && (((tick-start)/step)+1) <= getTimes();
	}
	
	public boolean nearToEnd(int start, int step, int offset, float tick) {
		return tick == ((start + (getTimes()*step))+offset);
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
