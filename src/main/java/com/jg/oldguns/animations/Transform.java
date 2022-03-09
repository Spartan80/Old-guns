package com.jg.oldguns.animations;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author Alexthe666
 * @since 1.0.0
 */
@OnlyIn(Dist.CLIENT)
public class Transform {
	public float rotationX;
	public float rotationY;
	public float rotationZ;
	public float offsetX;
	public float offsetY;
	public float offsetZ;

	public float drotationX;
	public float drotationY;
	public float drotationZ;
	public float doffsetX;
	public float doffsetY;
	public float doffsetZ;

	public Transform() {

	}

	/**
	 * Add rotation to this transformation
	 *
	 * @param x the x rotation
	 * @param y the y rotation
	 * @param z the z rotation
	 */
	public void addRotation(float x, float y, float z) {
		this.rotationX += x;
		this.rotationY += y;
		this.rotationZ += z;
	}

	/**
	 * Add offset to this transformation
	 *
	 * @param x the x offset
	 * @param y the y offset
	 * @param z the z offset
	 */
	public void addOffset(float x, float y, float z) {
		this.offsetX += x;
		this.offsetY += y;
		this.offsetZ += z;
	}

	/**
	 * Reset the rotation of this transformation
	 */
	public void resetRotation() {
		this.rotationX = 0.0F;
		this.rotationY = 0.0F;
		this.rotationZ = 0.0F;

	}

	/**
	 * Reset the offset of this transformation
	 */
	public void resetOffset() {
		this.offsetX = 0.0F;
		this.offsetY = 0.0F;
		this.offsetZ = 0.0F;
	}

	/**
	 * Reset the display offset of this transformation
	 */
	public void resetDisplayOffset() {
		this.doffsetX = 0.0F;
		this.doffsetY = 0.0F;
		this.doffsetZ = 0.0F;
	}

	/**
	 * Reset the display rotation of this transformation
	 */
	public void resetDisplayRotation() {
		this.drotationX = 0.0F;
		this.drotationY = 0.0F;
		this.drotationZ = 0.0F;
	}

	/**
	 * Reset the offset and rotation of this transformation
	 */
	public void reset() {
		resetOffset();
		resetRotation();
	}

	public void resetAll() {
		resetOffset();
		resetRotation();
		resetDisplayOffset();
		resetDisplayRotation();
	}

	/**
	 * Reset the display offset and rotation of this transformation
	 */

	public void resetDisplay() {
		resetDisplayOffset();
		resetDisplayRotation();
	}

	/**
	 * Set the rotation of this transformation
	 *
	 * @param x the x rotation
	 * @param y the y rotation
	 * @param z the z rotation
	 */
	public void setRotation(float x, float y, float z) {
		this.resetRotation();
		this.addRotation(x, y, z);
	}

	/**
	 * Set the offset of this transformation
	 *
	 * @param x the x offset
	 * @param y the y offset
	 * @param z the z offset
	 */
	public void setOffset(float x, float y, float z) {
		this.resetOffset();
		this.addOffset(x, y, z);
	}

	/**
	 * Set the display offset of this transformation
	 *
	 * @param x the x offset
	 * @param y the y offset
	 * @param z the z offset
	 */
	public void setDisplayOffset(float x, float y, float z) {
		this.doffsetX = x;
		this.doffsetY = y;
		this.doffsetZ = z;
	}

	/**
	 * Set the display rotation of this transformation
	 *
	 * @param x the x rotation
	 * @param y the y rotation
	 * @param z the z rotation
	 */
	public void setDisplayRotation(float x, float y, float z) {
		this.drotationX = x;
		this.drotationY = y;
		this.drotationZ = z;
	}

	public float getCombinedOffsetX() {
		return this.doffsetX + this.offsetX;
	}

	public float getCombinedOffsetY() {
		return this.doffsetY + this.offsetY;
	}

	public float getCombinedOffsetZ() {
		return this.doffsetZ + this.offsetZ;
	}

	public float getCombinedRotationX() {
		return this.drotationX + this.rotationX;
	}

	public float getCombinedRotationY() {
		return this.drotationY + this.rotationY;
	}

	public float getCombinedRotationZ() {
		return this.drotationZ + this.rotationZ;
	}

	public Transform copy() {
		Transform t = new Transform();
		t.setOffset(offsetX, offsetY, offsetZ);
		t.setRotation(rotationX, rotationY, rotationZ);
		return t;
	}

	public void copy(Transform transform) {
		setOffset(transform.offsetX, transform.offsetY, transform.offsetZ);
		setRotation(transform.rotationX, transform.rotationY, transform.rotationZ);
	}
	
	public void copyAll(Transform transform) {
		setOffset(transform.offsetX, transform.offsetY, transform.offsetZ);
		setRotation(transform.rotationX, transform.rotationY, transform.rotationZ);
		setDisplayOffset(transform.doffsetX, transform.doffsetY, transform.doffsetZ);
		setDisplayRotation(transform.drotationX, transform.drotationY, transform.drotationZ);
	}

	public void copyFromD(Transform transform) {
		setOffset(transform.doffsetX, transform.doffsetY, transform.doffsetZ);
		setRotation(transform.drotationX, transform.drotationY, transform.drotationZ);
	}

	public void setDisplaytoNonD() {
		setOffset(doffsetX, doffsetY, doffsetZ);
		setRotation(drotationX, drotationY, drotationZ);
	}

	public void setNDisplaytoD() {
		setDisplayOffset(offsetX, offsetY, offsetZ);
		setDisplayRotation(rotationX, rotationY, rotationZ);
	}

	public boolean isTZero() {
		System.out.println("offsetX: " + offsetX + " offsetY: " + offsetY + " offsetZ: " + offsetZ);
		return offsetX == 0 && offsetY == 0 && offsetZ == 0;
	}

	public boolean isRZero() {
		System.out.println("rotationX: " + rotationX + " rotationY: " + rotationY + " rotationZ: " + rotationZ);
		return rotationX == 0 && rotationY == 0 && rotationZ == 0;
	}

	public boolean isOZero() {
		return rotationX == 0 && rotationY == 0 && rotationZ == 0 && offsetX == 0 && offsetY == 0 && offsetZ == 0;
	}
	
	public boolean isDZero() {
		return drotationX == 0 && drotationY == 0 && drotationZ == 0 && doffsetX == 0 && doffsetY == 0 && doffsetZ == 0;
	}

}
