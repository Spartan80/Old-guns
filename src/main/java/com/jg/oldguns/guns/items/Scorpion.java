package com.jg.oldguns.guns.items;

import com.jg.oldguns.config.Config;
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
		return Config.SERVER.scorpionDmg.get().floatValue();
	}

	@Override
	public float getShootTime() {
		return Config.SERVER.scorpionShootTime.get().floatValue();
	}
	
	@Override
	public float getPower() {
		return Config.SERVER.scorpionPower.get().floatValue();
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return Config.SERVER.scorpionRange.get().intValue();
	}

	@Override
	public float getRangeDamageReduction() {
		return Config.SERVER.scorpionRangeDmgRed.get().floatValue();
	}

	@Override
	public float getInnacuracy() {
		return Config.SERVER.scorpionInnacuracy.get().floatValue();
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
