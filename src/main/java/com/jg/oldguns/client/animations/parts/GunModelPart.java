package com.jg.oldguns.client.animations.parts;

import com.jg.oldguns.client.animations.Transform;

public class GunModelPart {

	protected Transform transform;
	protected Transform dtransform;
	protected boolean visible;
	protected String name;
	
	public GunModelPart(String name) {
		this.name = name;
		transform = new Transform();
		dtransform = new Transform();
	}
	
	public GunModelPart(String name, float x, float y, float z, float rx, float ry, float rz) {
		this.name = name;
		dtransform = new Transform(x, y, z, rx, ry, rz);
		transform = new Transform();
	}
	
	public GunModelPart(String name, float x, float y, float z, float rx, float ry, float rz,
			float sx, float sy, float sz) {
		this.name = name;
		dtransform = new Transform(x, y, z, rx, ry, rz, sx, sy, sz);
		transform = new Transform();
	}
	
	public void reset() {
		for(int i = 0; i < transform.pos.length; i++) {
			transform.pos[i] = 0;
		}
		for(int i = 0; i < transform.rot.length; i++) {
			transform.rot[i] = 0;
		}
	}

	public Transform getCombined() {
		return dtransform.combine(transform);
	}
	
	public Transform getTransform() {
		return transform;
	}

	public Transform getDTransform() {
		return dtransform;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public String getName() {
		return name;
	}
	
}
