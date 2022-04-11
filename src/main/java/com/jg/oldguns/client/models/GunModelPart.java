package com.jg.oldguns.client.models;

import com.jg.oldguns.animations.Transform;

public class GunModelPart {

	private String name;
	public Transform transform;

	public GunModelPart(String name) {
		transform = new Transform();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public GunModelPart copy() {
		GunModelPart part = new GunModelPart(name);
		part.transform.copyAll(transform);
		return part;
	}
}
