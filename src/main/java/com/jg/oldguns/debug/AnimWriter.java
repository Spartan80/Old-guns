package com.jg.oldguns.debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.animations.Animator;
import com.jg.oldguns.animations.Transform;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.config.Config;

import net.minecraft.world.entity.player.Player;

public class AnimWriter {

	private static int size = 0;
	
	private static boolean setupParts = true;
	private static boolean loop = false;
	private static boolean continueAnimation = false;
	
	private static String animation = "";
	private static String aAnimation = "";
	
	private static Map<GunModelPart, Boolean> translations = new HashMap<GunModelPart, Boolean>();
	private static Map<GunModelPart, Boolean> rotations = new HashMap<GunModelPart, Boolean>();
	
	private static ArrayList<GunModelPart> aTranslations = new ArrayList<GunModelPart>();
	private static ArrayList<GunModelPart> aRotations = new ArrayList<GunModelPart>();
	private static ArrayList<String> keyframes = new ArrayList<String>();
	
	public static void writeTransform(String type) {
		if (type == "t") {
			Player player = ClientEventHandler.getPlayer();
			if (player != null) {
				if (!ClientEventHandler.handlers.containsKey(ClientEventHandler.getPlayer().getUUID()))
					return;
				ClientHandler handler = ClientEventHandler.handlers.get(player.getUUID());
				if (handler == null)
					return;
				// Minecraft.getInstance().pac
				GunModel model = handler.getGunModel();
				GunModelPart part = model.parts[model.index];
				System.out.println("added translation for: " + part.getName());
				Transform t = model.parts[model.index].transform;
				animation += "animator.move(" + part.getName() + ", " + t.offsetX + "f, " + t.offsetY + "f, " + t.offsetZ
						+ "f);\n";
			}
		} else if (type == "r") {
			Player player = ClientEventHandler.getPlayer();
			if (player != null) {
				if (!ClientEventHandler.handlers.containsKey(ClientEventHandler.getPlayer().getUUID()))
					return;
				ClientHandler handler = ClientEventHandler.handlers.get(player.getUUID());
				if (handler == null)
					return;
				GunModel model = handler.getGunModel();
				GunModelPart part = model.parts[model.index];
				System.out.println("added rotation for: " + part.getName());
				Transform t = part.transform;
				animation += "animator.rotate(" + part.getName() + ", " + t.rotationX + "f, " + t.rotationY + "f, "
						+ t.rotationZ + "f);\n";
			}
		} else if (type == "tr") {
			Player player = ClientEventHandler.getPlayer();
			if (player != null) {
				if (!ClientEventHandler.handlers.containsKey(ClientEventHandler.getPlayer().getUUID()))
					return;
				ClientHandler handler = ClientEventHandler.handlers.get(player.getUUID());
				if (handler == null)
					return;
				GunModel model = handler.getGunModel();
				GunModelPart part = model.parts[model.index];
				System.out.println("added translation and rotation for: " + part.getName());
				Transform t = model.parts[model.index].transform;
				animation += "animator.move(" + part.getName() + ", " + t.offsetX + "f, " + t.offsetY + "f, " + t.offsetZ
						+ "f);\n";
				animation += "animator.rotate(" + part.getName() + ", " + t.rotationX + "f, " + t.rotationY + "f, "
						+ t.rotationZ + "f);\n";
			}
		}
	}

	public static void addStart(int length) {
		System.out.println("started keyframe with length: " + length);
		animation += "animator.startKeyframe(" + length + ");\n";
	}

	public static void finish() {
		System.out.println("finished keyframe ");
		animation += "animator.endKeyframe();\n";
	}

	public static void finish(int length) {
		System.out.println("finished keyframe with length: " + length);
		animation += "animator.resetKeyframe(" + length + ");\n";
		animation += "animator.endKeyframe();\n";
	}

	public static void save(String name) {
		if (!Config.CLIENT.animationsSavePath.equals("nothing")) {
			File f = new File(Config.CLIENT.animationsSavePath.get() + "\\" + name);
			if (!f.exists()) {

				try {

					f.createNewFile();
					BufferedWriter writer = new BufferedWriter(new FileWriter(f));
					writer.write(animation);
					System.out.println("animation file saved at: " + Config.CLIENT.animationsSavePath.get()
							+ " with name: " + name);
					writer.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		animation = "";
	}

	public static void all() {

		Player player = ClientEventHandler.getPlayer();

		if (player != null) {
			if (!ClientEventHandler.handlers.containsKey(ClientEventHandler.getPlayer().getUUID()))
				return;
			ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
			if (handler == null)
				return;

			GunModelPart[] parts = handler.getGunModel().parts;

			for (int i = 0; i < parts.length; i++) {
				System.out.println(parts[i].getName());
				if (!parts[i].transform.isTZero()) {
					addTForPart(parts[i]);
				}
				if (!parts[i].transform.isRZero()) {
					addRForPart(parts[i]);
				}
			}

			addStart(4);
			for (Map.Entry<GunModelPart, Boolean> entry : translations.entrySet()) {
				if (entry.getValue()) {
					Transform t = entry.getKey().transform;
					animation += "animator.move(" + entry.getKey().getName() + ", " + t.offsetX + "f, " + t.offsetY + "f, "
							+ t.offsetZ + "f);\n";

				}
			}
			for (Map.Entry<GunModelPart, Boolean> entry : rotations.entrySet()) {
				if (entry.getValue()) {
					Transform t = entry.getKey().transform;
					animation += "animator.rotate(" + entry.getKey().getName() + ", " + t.rotationX + "f, " + t.rotationY
							+ "f, " + t.rotationZ + "f);\n";
				}
			}
			finish();
			System.out.println(animation);
			restart();
		}
	}
	
	private static void addAStart(int dur) {
		aAnimation += "s," + dur + ";";
	}
	
	private static void endAKeyFrame() {
		aAnimation += "e;";
	}
	
	private static void stringToTransform(Animator animator) {
		String[] st = keyframes.get(animator.getCurrent()).split(";");
		for(String sp : st) {
			if(sp.startsWith("t")) {
				String[] str = sp.split(",");
				GunModelPart part = animator.getPart(str[1]);
				if(part != null) {
					part.transform.setOffset(
							Float.parseFloat(str[2]), 
							Float.parseFloat(str[3]), 
							Float.parseFloat(str[4]));
				}
			}else if(sp.startsWith("r")) {
				String[] str = sp.split(",");
				GunModelPart part = animator.getPart(str[1]);
				if(part != null) {
					part.transform.setRotation(
							Float.parseFloat(str[2]), 
							Float.parseFloat(str[3]), 
							Float.parseFloat(str[4]));
				}
			}
		}
	}
	
	private static void transformsToString(GunModelPart[] parts, Animator animator) {
		String res = "";
		for(GunModelPart part : parts) {
			res += "t," + part.getName() + "," + part.transform.offsetX + "," + 
					part.transform.offsetY + 
					"," + part.transform.offsetZ + ";" + "r," + 
					part.getName() + "," + part.transform.rotationX + "," 
					+ part.transform.rotationY + 
					"," + part.transform.rotationZ + ";";
		}
		keyframes.add(res);
		animator.update(keyframes.size());
	}
	
	public static void aAll(int startDur) {

		Player player = ClientEventHandler.getPlayer();

		if (player != null) {
			if (!ClientEventHandler.handlers.containsKey(ClientEventHandler.getPlayer().getUUID()))
				return;
			ClientHandler handler = ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID());
			if (handler == null)
				return;

			GunModelPart[] parts = handler.getGunModel().parts;
			
			transformsToString(parts, handler.getGunModel().getAnimator());
			System.out.println(handler.getGunModel().getPart().transform.rotationY);
			
			for (int i = 0; i < parts.length; i++) {
				System.out.println(parts[i].getName());
				if (!parts[i].transform.isTZero()) {
					addTForPart(parts[i]);
				}
				if (!parts[i].transform.isRZero()) {
					addRForPart(parts[i]);
				}
			}

			addAStart(startDur);
			for (Map.Entry<GunModelPart, Boolean> entry : translations.entrySet()) {
				if (entry.getValue()) {
					Transform t = entry.getKey().transform;
					aAnimation += "t," + entry.getKey().getName() + "," + t.offsetX + "," + t.offsetY + ","
							+ t.offsetZ + ";";

				}
			}
			for (Map.Entry<GunModelPart, Boolean> entry : rotations.entrySet()) {
				if (entry.getValue()) {
					Transform t = entry.getKey().transform;
					aAnimation += "r," + entry.getKey().getName() + "," + t.rotationX + "," + t.rotationY + ","
							+ t.rotationZ + ";";
				}
			}
			endAKeyFrame();
			System.out.println(aAnimation);
			//size = getBlocks(aAnimation.split(";")).size();
			//handler.getGunModel().getAnimator().update(size);
		}
	}
	
	private static void procces(Animator a) {
		String[] allT = aAnimation.split(";");
		
		for(String s : allT) {
			if(s.startsWith("t")) {
				String[] st = s.split(",");
				System.out.println("Add t");
				a.move(st[1], Float.parseFloat(st[2]), Float.parseFloat(st[3]), Float.parseFloat(st[4]));
			} else if(s.startsWith("r,")) {
				String[] st = s.split(",");
				a.rotate(st[1], Float.parseFloat(st[2]), Float.parseFloat(st[3]), Float.parseFloat(st[4]));
			} else if(s.startsWith("s")) {
				System.out.println("Start k");
				String[] st = s.split(",");
				a.startKeyframe(Integer.valueOf(st[1]));
			} else if(s.equals("e")) {
				System.out.println("Ending k");
				a.endKeyframe();
			} else if(s.startsWith("rk")) {
				String[] st = s.split(",");
				a.resetKeyframe(Integer.valueOf(st[1]));
			}
		}
	}
	
	public static void startAnimation(GunModel model, Animator a, Animation an) {
		if(shouldContinueAnimation()) {
			model.setAnimation(an);
			a.setAnimation(an);
			procces(a);
		}
	}
	
	public static void onEndAnimation(GunModel model, Animation an) {
		if(!isLoopOn()) {
			stop();
			System.out.println("Stopping animation");
		}
	}
	
	public static void resetKeyFrame(int dur) {
		aAnimation += "rk," + dur + ";";
	}
	
	private static void proccessConvert(String s) {
		if(s.startsWith("t")) {
			String[] st = s.split(",");
			animation += "animator.move(" + st[1] + ", " + Float.parseFloat(st[2]) + "f, "
					+ Float.parseFloat(st[3]) + "f, " + Float.parseFloat(st[4]) + "f);\n";
		} else if(s.startsWith("r,")) {
			String[] st = s.split(",");
			animation += "animator.rotate(" + st[1] + ", " + Float.parseFloat(st[2]) + "f, "
					+ Float.parseFloat(st[3]) + "f, " + Float.parseFloat(st[4]) + "f);\n";
		} else if(s.startsWith("s")) {
			String[] st = s.split(",");
			animation += "animator.startKeyframe(" + Integer.valueOf(st[1]) + ");\n";
		} else if(s.equals("e")) {
			animation += "animator.endKeyframe();\n";
		} else if(s.startsWith("rk")) {
			String[] st = s.split(",");
			animation += "animator.resetKeyframe(" + Integer.valueOf(st[1]) + ");\nanimator.endKeyframe();\n";
		}
	}
	
	public static void convertAAnimtoAnim() {
		String[] st = aAnimation.split(";");
		for(String s : st) {
			proccessConvert(s);
		}
	}
	
	public static String remLastInstr(String[] st) {
		String res = "";
		for(int i = 0; i < st.length-1; i++) {
			res += st[i] + ";";
		}
		return res;
	}
	
	public static String remFirstInstr(String[] st) {
		String res = "";
		for(int i = 1; i < st.length; i++) {
			res += st[i] + ";";
		}
		return res;
	}
	
	public static List<String> getBlocks(String[] st) {
		int start = -1;
		int end = -1;
		List<String> blocks = new ArrayList<String>();
		for(int i = 0; i < st.length; i++) {
			if(st[i].startsWith("s")) {
				start = i;
			}else if(st[i].startsWith("rk")) {
				start = i;
			}else if(st[i].equals("e")) {
				end = i+1;
			}
			String res = "";
			if(start != -1 && end != -1) {
				for(int i2 = start; i2 < end; i2++) {
					res += st[i2] + ";";
				}
				System.out.println(res);
				blocks.add(res);
				start = -1;
				end = -1;
			}
		}
		return blocks;
	}
	
	public static void setTransformsOfKeyframe(GunModel model, String[] st, int keyframe) {
		List<String> blocks = getBlocks(st);
		System.out.println("Blocks size: " + blocks.size());
		for(String s : blocks) {
			System.out.println("Block: " + s);
		}
		System.out.println("Animation: " + aAnimation);
		if(keyframe > blocks.size()-1) {
			keyframe = blocks.size()-1;
		}else if(keyframe < 0){
			keyframe = 0;
		}
		String block = blocks.get(keyframe);
		System.out.println("Block: " + block);
		String[] sb = block.split(";");
		if(block.startsWith("rk")) {
			model.resetNonD();
		}else if(block.startsWith("s")) {
			String res = remFirstInstr(sb);
			res = remLastInstr(res.split(";"));
			System.out.println("Res: " + res);
			String[] si = res.split(";");
			for(String s : si) {
				String[] str = s.split(",");
				if(str[0].equals("t")) {
					model.getAnimator().getPart(str[1]).transform
					.setOffset(Float.parseFloat(str[2]), 
							   Float.parseFloat(str[3]), 
							   Float.parseFloat(str[4]));
					System.out.println("Setting offset xyz");
				}else if(str[0].equals("r")) {
					model.getAnimator().getPart(str[1]).transform
					.setRotation(Float.parseFloat(str[2]), 
							     Float.parseFloat(str[3]), 
							     Float.parseFloat(str[4]));
					System.out.println("Setting rotation xyz");
				}
			}
		}
		
	}	
	
	public static void remLast(GunModel model) {
		model.prev(keyframes.size());
		keyframes.remove(keyframes.size()-1);
		System.out.println("New Size: " + keyframes.size());
	}
	
	public static void prev(GunModel model) {
		model.prev(keyframes.size());
		stringToTransform(model.getAnimator());
	}
	
	public static void next(GunModel model) {
		model.next(keyframes.size());
		stringToTransform(model.getAnimator());
	}
	
	//Misc no entry
	
	public static void start() {
		continueAnimation = true;
	}
	
	public static void stop() {
		continueAnimation = false;
	}
	
	//Getters and setters
	
	public static boolean shouldContinueAnimation() {
		return continueAnimation;
	}
	
	public static void setShouldRunSetupParts(boolean setupPartsp) {
		setupParts = setupPartsp;
	}
	
	public static boolean shouldRunSetupParts() {
		return setupParts;
	}
	
	public static void setLoop(boolean loopn) {
		loop = loopn;
	}
	
	public static boolean isLoopOn() {
		return loop;
	}
	
	public static String getAnimation() {
		return animation;
	}

	public static String getAanimation() {
		return aAnimation;
	}
	
	//Misc
	
	public static void resetAnimation() {
		animation = "";
	}
	
	public static void resetAanimation() {
		aAnimation = "";
		keyframes.clear();
	}
	
	public static void addTForPart(GunModelPart part) {
		translations.putIfAbsent(part, true);
	}

	public static void addRForPart(GunModelPart part) {
		rotations.putIfAbsent(part, true);
	}
	
	public static void addATForPart(GunModelPart part) {
		aTranslations.add(part);
	}

	public static void addARForPart(GunModelPart part) {
		aRotations.add(part);
	}

	public static void restart() {
		translations.clear();
		rotations.clear();
	}
	
	public static void aRestart() {
		aTranslations.clear();
		aRotations.clear();
	}
}
