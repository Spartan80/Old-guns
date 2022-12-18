package com.jg.oldguns.client.animations.serializers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Keyframe;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;

import net.minecraft.client.Minecraft;

public class AnimationSerializer {

	public static String serialize(Animation anim) {
		String res = "";
		res += "animation>start\n";
		res += "animation>dur=" + anim.getDuration() + "\n";
		res += "animation>name=\"" + anim.getName() + "\n";
		res += "animation>gunModel=\"" + anim.getGunModel() + "\n";
		res += "animation>end\n";
		for(Keyframe kf :anim.getKeyframes()) {
			res += KeyframeSerializer.serialize(kf);
		}
		return res;
	}

	public static Animation deserialize(String json) {
		if(!json.isBlank()) {
			Map<String, String> anim = new HashMap<>(); 
			List<Keyframe> keyframes = new ArrayList<>();
			String[] sData = json.split("\n");
			boolean building = false;
			boolean onlyKeyframes = false;
			String keyframe = "";
			
			for(int i = 0; i < sData.length; i++) {
				String s = sData[i];
				if(!onlyKeyframes) {
					if(s.contains("=")) {
						// Contains parameters
						
						String[] conv = s.split("=");
						String[] commands = conv[0].split(">");
						
						if(building) {
							if(commands[0].equals("animation")) {
								// Building Animation
								anim.put(commands[1], conv[1].replace("\"", ""));
							}	
						}
					} else {
						// Just a method or function
						String[] commands = s.split(">");
						// Start command
						if(commands[1].equals("start")) {
							building = true;
						}
						// End command
						if(commands[1].equals("end")) {
							onlyKeyframes = true;
							building = false;
						}
					}
				} else {
					String[] commands = s.split(">");
					if(!commands[1].equals("end")) {
						keyframe += s + "\n";
					} else {
						keyframe += "keyframe>end\n";
						keyframes.add(KeyframeSerializer.deserialize(keyframe, 
								anim.get("gunModel")));
						keyframe = "";
					}
				}
			}
			Animation animation = new Animation(anim.get("name"), anim.get("gunModel"),
					Integer.parseInt(anim.get("dur")));
			animation.setKeyframes(keyframes);
			return animation;
		} else {
			return null;
		}
	}
	
	public static String serializeWithCode(Animation animation, GunModel model) {
		String anim = "";
		anim += " = new Animation(\"" + animation.getName() + "\", \"" 
				+ animation.getGunModel() + "\")\n";
		for(Keyframe kf : animation.getKeyframes()) {
			anim += ".startKeyframe(" + kf.dur;
			if(!kf.easing.equals("empty") && !kf.easing.isEmpty()) {
				anim += ", \"" + kf.easing + "\"";
			}
			anim += ")\n";
			for(Entry<GunModelPart, float[]> e : kf.translations.entrySet()) {
				anim += ".translate(parts[" + getIndexForPart(e.getKey(), model)
					+ "], " + e.getValue()[0] + "f, " + e.getValue()[1] + "f, " + 
					e.getValue()[2] + "f)\n";
			}
			for(Entry<GunModelPart, float[]> e : kf.rotations.entrySet()) {
				anim += ".rotate(parts[" + getIndexForPart(e.getKey(), model)
					+ "], " + e.getValue()[0] + "f, " + e.getValue()[1] + "f, " + 
					e.getValue()[2] + "f)\n";
			}
		}
		anim += ".end();";
		System.out.println(anim);
		Minecraft.getInstance().keyboardHandler.setClipboard(anim);
		return anim;
	}
	
	public static int getIndexForPart(GunModelPart part, GunModel model) {
		for(int i = 0; i < model.getGunParts().size(); i++) {
			if(model.getGunParts().get(i).getName().equals(part.getName())) {
				return i;
			}
		}
		return -1;
	}
	
}
