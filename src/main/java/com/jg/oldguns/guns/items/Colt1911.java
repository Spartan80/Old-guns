package com.jg.oldguns.guns.items;

import com.jg.oldguns.config.Config;
import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Colt1911 extends GunItem {

	public Colt1911(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.COLT1911ST, ItemRegistries.COLT1911BO, 
				ItemRegistries.COLT1911BA, null, new String[] { Paths.COLT1911HAMMER, 
						Paths.COLT1911SLIDER },
				true, true, true));
	}
	
	@Override
	public float getDamage() {
		return Config.SERVER.colt1911Dmg.get().floatValue();
	}

	@Override
	public float getShootTime() {
		return Config.SERVER.colt1911ShootTime.get().floatValue();
	}
	
	@Override
	public float getPower() {
		return Config.SERVER.colt1911Power.get().floatValue();
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return Config.SERVER.colt1911Range.get().intValue();
	}

	@Override
	public float getRangeDamageReduction() {
		return Config.SERVER.colt1911RangeDmgRed.get().floatValue();
	}

	@Override
	public float getInnacuracy() {
		return Config.SERVER.colt1911Innacuracy.get().floatValue();
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
		return SoundRegistries.COLTSHOOT.get();
	}

}
