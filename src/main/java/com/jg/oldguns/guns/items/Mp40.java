package com.jg.oldguns.guns.items;

import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Mp40 extends GunItem {

	public Mp40(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.MP40ST, ItemRegistries.MP40BO, 
				ItemRegistries.MP40BA, null, new String[] { Paths.MP40HAMMER },
				true, true, true));
	}

	@Override
	public float getDamage() {
		return 2;
	}

	@Override
	public float getShootTime() {
		return 1.5f;
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
		return 8;
	}

	@Override
	public float getRangeDamageReduction() {
		return 0.7f;
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
		return SoundRegistries.MP40SHOOT.get();
	}

}
