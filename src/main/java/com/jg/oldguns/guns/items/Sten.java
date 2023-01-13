package com.jg.oldguns.guns.items;

import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Sten extends GunItem {

	public Sten(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.STENST, ItemRegistries.STENBO, 
				ItemRegistries.STENBA, ItemRegistries.STENMAG, 
				new String[] { Paths.STENHAMMER }, true, true, true));
	}

	@Override
	public float getDamage() {
		return 4;
	}

	@Override
	public float getShootTime() {
		return 2;
	}

	@Override
	public float getPower() {
		return 12;
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
		return 0.8f;
	}

	@Override
	public float getInnacuracy() {
		return 0.2f;
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
		return SoundRegistries.STENSHOOT.get();
	}

}
