package com.jg.oldguns.debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.jg.oldguns.animations.Transform;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.config.Config;

import net.minecraft.entity.player.PlayerEntity;

public class AnimWriter {

	public static String animation = "";
	public static Map<GunModelPart, Boolean> translations = new HashMap<GunModelPart, Boolean>();
	public static Map<GunModelPart, Boolean> last_translations = new HashMap<GunModelPart, Boolean>();
	public static Map<GunModelPart, Boolean> rotations = new HashMap<GunModelPart, Boolean>();
	public static Map<GunModelPart, Boolean> last_rotations = new HashMap<GunModelPart, Boolean>();

	public static void writeTransform(String type) {
		if (type == "t") {
			PlayerEntity player = ClientEventHandler.getPlayer();
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
			PlayerEntity player = ClientEventHandler.getPlayer();
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
			PlayerEntity player = ClientEventHandler.getPlayer();
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

		PlayerEntity player = ClientEventHandler.getPlayer();

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

	public static void addTForPart(GunModelPart part) {
		translations.putIfAbsent(part, true);
	}

	public static void addRForPart(GunModelPart part) {
		rotations.putIfAbsent(part, true);
	}

	public static void restart() {
		last_translations = translations;
		translations.clear();
		last_rotations = rotations;
		rotations.clear();
	}
}
