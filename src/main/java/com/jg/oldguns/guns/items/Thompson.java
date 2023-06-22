package com.jg.oldguns.guns.items;

import com.jg.oldguns.config.Config;
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
		return Config.SERVER.thompsonDmg.get().floatValue();
	}

	@Override
	public float getShootTime() {
		return Config.SERVER.thompsonShootTime.get().floatValue();
	}
	
	@Override
	public float getPower() {
		return Config.SERVER.thompsonPower.get().floatValue();
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return Config.SERVER.thompsonRange.get().intValue();
	}

	@Override
	public float getRangeDamageReduction() {
		return Config.SERVER.thompsonRangeDmgRed.get().floatValue();
	}

	@Override
	public float getInnacuracy() {
		return Config.SERVER.thompsonInnacuracy.get().floatValue();
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
