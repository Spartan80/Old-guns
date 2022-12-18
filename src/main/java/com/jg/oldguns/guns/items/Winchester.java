package com.jg.oldguns.guns.items;

import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Winchester extends GunItem {

	public Winchester(Properties prop) {
		super(prop, new GunStuff(ItemRegistries.WINCHESTERBA, ItemRegistries.WINCHESTERBO, 
				ItemRegistries.WINCHESTERST, null, new String[] { Paths.WINCHESTERBULLETLOADER }));
	}

	@Override
	public float getDamage() {
		return 4;
	}

	@Override
	public float getPower() {
		return 14;
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
		return 0.8f;
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
	public SoundEvent getShootSound() {
		return SoundRegistries.WINCHESTERSHOOT.get();
	}

}
