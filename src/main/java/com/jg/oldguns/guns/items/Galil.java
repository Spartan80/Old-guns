package com.jg.oldguns.guns.items;

import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Galil extends GunItem {

	public Galil(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.GALILST, ItemRegistries.GALILBO, 
				ItemRegistries.GALILBA, ItemRegistries.GALILMAG, 
				new String[] { Paths.GALILHAMMER }, true, true, true));
	}

	@Override
	public float getDamage() {
		return 6;
	}
	
	@Override
	public float getShootTime() {
		return 1.2f;
	}

	@Override
	public float getPower() {
		return 16;
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return 20;
	}

	@Override
	public float getRangeDamageReduction() {
		return 0.93f;
	}

	@Override
	public float getInnacuracy() {
		return 0.1f;
	}

	@Override
	public boolean hasScope() {
		return false;
	}

	@Override
	public FireMode getFireMode() {
		return FireMode.AUTO;
	}

	@Override
	public SoundEvent getShootSound() {
		return SoundRegistries.GALILSHOOT.get();
	}

}
