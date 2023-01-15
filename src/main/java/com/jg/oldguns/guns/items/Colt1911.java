package com.jg.oldguns.guns.items;

import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Colt1911 extends GunItem {

	public Colt1911(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.COLT1911ST, ItemRegistries.COLT1911BO, 
				ItemRegistries.COLT1911BA, null, new String[] { Paths.COLT1911HAMMER, 
						Paths.COLT1911SLIDER },
				true, true, true));
	}

	@Override
	public float getDamage() {
		return 3;
	}
	
	@Override
	public float getShootTime() {
		return 4;
	}

	@Override
	public float getPower() {
		return 8;
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return 6;
	}

	@Override
	public float getRangeDamageReduction() {
		return 0.7f;
	}

	@Override
	public float getInnacuracy() {
		return 0;
	}

	@Override
	public boolean hasScope() {
		return false;
	}

	@Override
	public FireMode getFireMode() {
		return FireMode.SEMI;
	}

	@Override
	public SoundEvent getShootSound() {
		return SoundRegistries.COLTSHOOT.get();
	}

}
