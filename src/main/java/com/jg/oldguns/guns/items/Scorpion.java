package com.jg.oldguns.guns.items;

import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Scorpion extends GunItem {

	public Scorpion(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.SCORPIONST, 
				ItemRegistries.SCORPIONBO, ItemRegistries.SCORPIONBA, ItemRegistries.SCORPIONMAG,
				new String[] { Paths.SCORPIONHAMMER }, false, false, false));
	}

	@Override
	public float getDamage() {
		return 3.4f;
	}

	@Override
	public float getShootTime() {
		return 0.6f;
	}

	@Override
	public float getPower() {
		return 9;
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return 7;
	}

	@Override
	public float getRangeDamageReduction() {
		return 0.7f;
	}

	@Override
	public float getInnacuracy() {
		return 0.3f;
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
		return SoundRegistries.SKORPIONSHOOT.get();
	}

}
