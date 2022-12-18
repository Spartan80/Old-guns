package com.jg.oldguns.client.animations.serializers;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import com.jg.oldguns.client.animations.Keyframe;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.GunModelsHandler;
import com.jg.oldguns.utils.Utils;
import com.mojang.logging.LogUtils;

public class KeyframeSerializer {
	
	public static String serialize(Keyframe keyframe) {
		String ser = "";
		ser += "keyframe>start\n";
		ser += "keyframe>dur=" + keyframe.dur + "\n";
		for(Entry<GunModelPart, float[]> entry : keyframe.translations.entrySet()) {
			ser += "keyframe>pos>" + entry.getKey().getName() + "=" + vectorToString(entry.getValue()) + "\n";
		}
		for(Entry<GunModelPart, float[]> entry : keyframe.rotations.entrySet()) {
			ser += "keyframe>rot>" + entry.getKey().getName() + "=" + vectorToString(entry.getValue()) + "\n";
		}
		ser += "keyframe>end\n";
		return ser;
	}
	
	public static Keyframe deserialize(String json, String gunModel) {
		Keyframe kf = new Keyframe(0);
		String[] sData = json.split("\n");
		GunModel model = GunModelsHandler.get(gunModel);
		
		for(int i = 0; i < sData.length; i++) {
			String s = sData[i];
			if(s.contains("=")) {
				// Contains parameters
				String[] conv = s.split("=");
				String[] commands = conv[0].split(">");
				
				if(commands[1].equals("pos")) {
					kf.translations.put(Utils.getGunPartByName(model, commands[2]), 
							getVectorFromString(conv[1]));
				} else if(commands[1].equals("rot")) {
					kf.rotations.put(Utils.getGunPartByName(model, commands[2]), 
							getVectorFromString(conv[1]));
					LogUtils.getLogger().info("Vector: " + 
							Arrays.toString(getVectorFromString(conv[1])));
				} else if(commands[1].equals("dur")) {
					kf.dur = Integer.parseInt(conv[1]);
				}
			}
		}
		return kf;
	}
	
	private static float[] getVectorFromString(String s) {
		String[] vec = s.split(",");
		return new float[] { Float.parseFloat(vec[0]), Float.parseFloat(vec[1]),
				Float.parseFloat(vec[2]) };
	}
	
	private static String vectorToString(float[] vec) {
		String res = "";
		for(int i = 0; i < vec.length; i++) {
			res += String.valueOf(vec[i]);
			if(i != 2) {
				res += ",";
			}
		}
		return res;
	}
	
	public static void showMap(Map<String, float[]> map) {
		for(Entry<String, float[]> entry : map.entrySet()) {
			System.out.println("Key: " + entry.getKey() + " value: " + entry.getValue()[0]
					+ " " + entry.getValue()[1] + " " + entry.getValue()[2]);
		}
	}
	
}
