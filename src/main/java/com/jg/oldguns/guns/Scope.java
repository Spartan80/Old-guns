package com.jg.oldguns.guns;

import net.minecraft.item.Item;

public class Scope extends Item {

	public final int scopePercent;
	public String gunId;

	public Scope(Item.Properties prop, int scopePercent) {
		super(prop);
		this.scopePercent = clapFrom0to100(scopePercent);
		this.gunId = "any";
	}
	
	public Scope(Item.Properties prop, int scopePercent, String gunId) {
		super(prop);
		this.scopePercent = clapFrom0to100(scopePercent);
		this.gunId = gunId;
	}

	public int clapFrom0to100(int scopePercent) {
		if(scopePercent > 100) {
			return 100;
		}else if(scopePercent < 0) {
			return 0;
		}
		return scopePercent;
	}
	
	public String getGunId() {
		return this.gunId;
	}

	public int getScopePercent() {
		return this.scopePercent;
	}

}
