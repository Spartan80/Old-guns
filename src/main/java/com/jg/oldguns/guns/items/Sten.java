package com.jg.oldguns.guns.items;

import com.jg.oldguns.config.Config;
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
		return Config.SERVER.stenDmg.get().floatValue();
	}

	@Override
	public float getShootTime() {
		return Config.SERVER.stenShootTime.get().floatValue();
	}
	
	@Override
	public float getPower() {
		return Config.SERVER.stenPower.get().floatValue();
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return Config.SERVER.stenRange.get().intValue();
	}

	@Override
	public float getRangeDamageReduction() {
		return Config.SERVER.stenRangeDmgRed.get().floatValue();
	}

	@Override
	public float getInnacuracy() {
		return Config.SERVER.stenInnacuracy.get().floatValue();
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
