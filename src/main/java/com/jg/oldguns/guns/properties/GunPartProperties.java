package com.jg.oldguns.guns.properties;

public class GunPartProperties<T extends GunPartProperties> {
	
	public String gunid;
	
	int recTime;
	
	float innacuracy;
	float rHorRec;
	float rVertRec;
	float zRec;
	float speedMod;
	float baseSpeedMod;

	public GunPartProperties() {
		
	}
	
	public T setRecTime(int recTime){
		this.recTime = recTime;
		return (T)this;
	}
	
	public T setInnacuracy(float innacuracy){
		this.innacuracy = innacuracy;
		return (T)this;
	}
	
	public T setHorRec(float horRec){
		this.rHorRec = horRec;
		return (T)this;
	}
	
	public T setVertRec(float vertRec){
		this.rVertRec = vertRec;
		return (T)this;
	}
	
	public T setZRec(float zRec){
		this.zRec = zRec;
		return (T)this;
	}
	
	public T setSpeedMod(float speedMod){
		this.speedMod = speedMod;
		return (T)this;
	}
	
	public T setBaseSpeedMod(float baseSpeed){
		this.baseSpeedMod = baseSpeed;
		return (T)this;
	}

	public float getrHorRec() {
		return rHorRec;
	}

	public float getrVertRec() {
		return rVertRec;
	}

	public float getzRec() {
		return zRec;
	}

	public int getRecTime() {
		return recTime;
	}

	public float getInnacuracy() {
		return innacuracy;
	}

	public float getSpeedMod() {
		return speedMod;
	}

	public float getBaseSpeedMod() {
		return baseSpeedMod;
	}
	
	public String getGunId() {
		return gunid;
	}
	
}
