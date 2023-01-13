package com.jg.oldguns.guns.items;

import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Thompson extends GunItem {

	public Thompson(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.THOMPSONST, ItemRegistries.THOMPSONBO,
				ItemRegistries.THOMPSONBA, ItemRegistries.THOMPSONMAG, 
				new String[] { Paths.THOMPSONHAMMER }, true, true, true));
	}

	@Override
	public float getDamage() {
		return 6;
	}

	@Override
	public float getShootTime() {
		return 1;
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
		return 9;
	}

	@Override
	public float getRangeDamageReduction() {
		return 0.9f;
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
		return SoundRegistries.THOMPSONSHOOT.get();
	}

}
