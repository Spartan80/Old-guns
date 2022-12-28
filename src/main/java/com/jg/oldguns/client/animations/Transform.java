package com.jg.oldguns.client.animations;

import java.util.Arrays;

public class Transform {
	
	public static final Transform EMPTY = new Transform();
	
	public float[] pos;
	public float[] rot;
	public float[] sca;
	
	public Transform() {
		pos = new float[] { 0.0f, 0.0f, 0.0f };
		rot = new float[] { 0.0f, 0.0f, 0.0f };
		sca = new float[] { 1.0f, 1.0f, 1.0f };
	}
	
	public Transform(float x, float y, float z, float rx, float ry, float rz) {
		this(x, y, z, rx, ry, rz, 1, 1, 1);
	}
	
	public Transform(float x, float y, float z, float rx, float ry, float rz, float sx, float sy,
			float sz) {
		pos = new float[] { x, y, z };
		rot = new float[] { rx, ry, rz };
		sca = new float[] { sx, sy, sz };
	}
	
	public Transform(Transform transform) {
		pos = new float[] { transform.pos[0], transform.pos[1], transform.pos[2] };
		rot = new float[] { transform.rot[0], transform.rot[1], transform.rot[2] };
		sca = new float[] { transform.sca[0], transform.sca[1], transform.sca[2] };
	}
	
	public void setPos(float[] pos) {
		this.pos[0] = pos[0];
		this.pos[1] = pos[1];
		this.pos[2] = pos[2];
	}
	
	public void setRot(float[] rot) {
		this.rot[0] = rot[0];
		this.rot[1] = rot[1];
		this.rot[2] = rot[2];
	}
	
	public void setScale(float[] sca) {
		this.sca[0] = sca[0];
		this.sca[1] = sca[1];
		this.sca[2] = sca[2];
	}
	
	public void setPos(float x, float y, float z) {
		pos[0] = x;
		pos[1] = y;
		pos[2] = z;
	}
	
	public void setRot(float rx, float ry, float rz) {
		rot[0] = rx;
		rot[1] = ry;
		rot[2] = rz;
	}
	
	public void setScale(float sx, float sy, float sz) {
		sca[0] = sx;
		sca[1] = sy;
		sca[2] = sz;
	}
	
	public void addPos(float[] pos) {
		this.pos[0] = this.pos[0] + pos[0];
		this.pos[1] = this.pos[1] + pos[1];
		this.pos[2] = this.pos[2] + pos[2];
	}
	
	public void addRot(float[] rot) {
		this.rot[0] = this.rot[0] + rot[0];
		this.rot[1] = this.rot[1] + rot[1];
		this.rot[2] = this.rot[2] + rot[2];
	}
	
	public void addScale(float[] scale) {
		this.sca[0] = this.sca[0] + sca[0];
		this.sca[1] = this.sca[1] + sca[1];
		this.sca[2] = this.sca[2] + sca[2];
	}
	
	public void addPos(float x, float y, float z) {
		pos[0] = pos[0] + x;
		pos[1] = pos[1] + y;
		pos[2] = pos[2] + z;
	}
	
	public void addRot(float rx, float ry, float rz) {
		rot[0] = rot[0] + rx;
		rot[1] = rot[1] + ry;
		rot[2] = rot[2] + rz;
	}
	
	public void addScale(float sx, float sy, float sz) {
		sca[0] = sca[0] + sx;
		sca[1] = sca[1] + sy;
		sca[2] = sca[2] + sz;
	}
	
	public Transform combine(Transform transform) {
		Transform t = new Transform();
		t.addPos(transform.pos);
		t.addRot(transform.rot);
		t.setScale(1, 1, 1);
		t.addScale(Math.abs(1-transform.sca[0]), Math.abs(1-transform.sca[1]), 
				Math.abs(1-transform.sca[2]));
		t.addPos(pos);
		t.addRot(rot);
		t.addScale(Math.abs(1-sca[0]), Math.abs(1-sca[1]), 
				Math.abs(1-sca[2]));
		
		return t;
	}
	
	public void copyFrom(Transform t) {
		this.pos = new float[] { t.pos[0], t.pos[1], t.pos[2] };
		this.rot = new float[] { t.rot[0], t.rot[1], t.rot[2] };
		this.sca = new float[] { t.sca[0], t.sca[1], t.sca[2] };
	}
	
	public Transform copy() {
		Transform t = new Transform();
		t.addPos(pos);
		t.addRot(rot);
		t.addScale(sca);
		return t;
	}
	
	@Override
	public String toString() {
		return "Pos: " + Arrays.toString(pos) + " Rot: " + Arrays.toString(rot) + " Sca: " + 
			Arrays.toString(sca);
	}
	
}
