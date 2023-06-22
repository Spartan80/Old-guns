package com.jg.oldguns.utils;

public class MathUtils {
	public static float rotLerp(float p, float a, float b){
	    double cs = ((1-p)*Math.cos(a) + p*Math.cos(b));
	    double sn = (1-p)*Math.sin(a) + p*Math.sin(b);
	    return (float)Math.atan2(sn, cs);
	}
}
