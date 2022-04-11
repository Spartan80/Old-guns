package com.jg.oldguns.guns;

public class StockPart extends GunPart {

	public StockPart(Properties p_i48487_1_, int gunSlot, String gunid, int wood, int metal, boolean steel,
			float weight, StockPartConfig config) {
		super(p_i48487_1_, gunSlot, gunid, wood, metal, steel, weight);
		config.config(this);
	}

	public interface StockPartConfig{
		public void config(StockPart p);
	}
	
}
