package com.redwyvern.util;

import net.minecraft.util.math.MathHelper;

public class MathUtils {
	public static float lerpDegrees(float lerpFactor, float a, float b) {
		float result;
		float diff = b - a;
		if (diff < -180) {
			b += 360;
			result = MathHelper.lerp(lerpFactor, a, b);
			if (result >= 360) {
				result -= 360;
			}
		} else if (diff > 180) {
			b -= 360;
			result = MathHelper.lerp(lerpFactor, a, b);
			if (result < 0) {
				result += 360;
			}
		} else {
			result = MathHelper.lerp(lerpFactor, a, b);
		}

		return result;
	}

	public static float getPorcentageFrom(float num, int percentage) {
		return num - ((num * percentage) / 100);
	}
}
