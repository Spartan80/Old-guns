package com.jg.oldguns.debug;

import java.util.ArrayList;
import java.util.List;

import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;

public class TransformState {
	
	private int current;
	private List<GunModelPart[]> parts;
	private GunModel gunModel;
	
	public TransformState(GunModel model) {
		parts = new ArrayList<GunModelPart[]>();
		if(model != null) {
			this.gunModel = model;
		}else {
			System.out.println("No GunModel provided");
			Minecraft.getInstance().player.sendMessage(new TranslatableComponent("No model provided"), null);
		}
	}
	
	public void recover() {
		if(parts.size() > 0 && gunModel != null) {
			for(int i = 0; i < parts.get(current).length; i++) {
				GunModelPart currentPart = parts.get(current)[i];
				gunModel.getParts()[i].transform.setOffset(
						currentPart.transform.offsetX, 
						currentPart.transform.offsetY, 
						currentPart.transform.offsetZ);
				gunModel.getParts()[i].transform.setRotation(
						currentPart.transform.rotationX, 
						currentPart.transform.rotationY, 
						currentPart.transform.rotationZ);
			}
		}else {
			System.out.println("Parts is empty");
			Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Could not activate state because states size is 0"), null);
		}
	}
	
	public void addState() {
		if(gunModel != null) {
			parts.add(gunModel.getCopyGunModelParts());
			last();
		}else {
			System.out.println("GunModelParts is empty");
			Minecraft.getInstance().player.sendMessage(new TranslatableComponent("GunModel is null"), null);
		}
	}
	
	public void next() {
		if(parts.size() > 0) {
			for(GunModelPart part : gunModel.getParts()) {
				System.out.println("before original part offsetX " + part.transform.doffsetX);
			}
			current = (current - 1 + parts.size()) % parts.size();
			recover();
			Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Activating next transform state"), null);
			for(GunModelPart[] partArrays : parts) {
				for(GunModelPart part : partArrays) {
					System.out.println("part offsetX " + part.transform.doffsetX);
				}
			}
			for(GunModelPart part : gunModel.getParts()) {
				System.out.println("original part offsetX " + part.transform.doffsetX);
			}
		}else {
			System.out.println("size of parts <= 0");
			Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Could not activate state because states size is 0"), null);
		}
	}
	
	public void prev() {
		if(parts.size() > 0) {
			current = (current + 1) % parts.size();
			recover();
		}else {
			System.out.println("size of parts <= 0");
			Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Could not activate state because states size is 0"), null);
		}
	}
	
	public void last() {
		if(parts.size() > 0) {
			current = parts.size()-1;
			recover();
		}else {
			System.out.println("size of parts <= 0");
			Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Could not activate state because states size is 0"), null);
		}
	}
	
	public void removeLastState() {
		if(parts.size() > 0) {
			parts.remove(parts.size()-1);
			if(parts.size() > 0) {
				current = (current + 1) % parts.size();
			}
		}else {
			System.out.println("size of parts <= 0");
			Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Could not activate state because states size is 0"), null);
		}
	}
	
	public void removeCurrentState() {
		if(parts.size() > 0) {
			parts.remove(current);
			if(parts.size() > 0) {
				current = (current + 1) % parts.size();
			}
		}else {
			System.out.println("size of parts <= 0");
			Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Could not activate state because states size is 0"), null);
		}
	}
	
}
