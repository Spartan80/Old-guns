package com.jg.oldguns.guns.items;

import com.jg.oldguns.config.Config;
import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Winchester extends GunItem {
	
	public Winchester(Properties prop) {
		super("winchester", prop, new GunStuff(ItemRegistries.WINCHESTERST, ItemRegistries.WINCHESTERBO, 
				ItemRegistries.WINCHESTERBA, null, new String[] { Paths.WINCHESTERBULLETLOADER },
				true, true, true));
	}
	
	@Override
	public float getDamage() {
		return Config.SERVER.winchesterDmg.get().floatValue();
	}

	@Override
	public float getShootTime() {
		return Config.SERVER.winchesterShootTime.get().floatValue();
	}
	
	@Override
	public float getPower() {
		return Config.SERVER.winchesterPower.get().floatValue();
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return Config.SERVER.winchesterRange.get().intValue();
	}

	@Override
	public float getRangeDamageReduction() {
		return Config.SERVER.winchesterRangeDmgRed.get().floatValue();
	}

	@Override
	public float getInnacuracy() {
		return Config.SERVER.winchesterInnacuracy.get().floatValue();
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
		return SoundRegistries.WINCHESTERSHOOT.get();
	}

}
