package com.jg.oldguns.guns.items;

import com.jg.oldguns.config.Config;
import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Kar98k extends GunItem {

	public Kar98k(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.KAR98KST, 
				ItemRegistries.KAR98KBO, ItemRegistries.KAR98KBA, null, 
				new String[] { Paths.KAR98KHAMMER }, 
				true, true, true));
	}

	@Override
	public float getDamage() {
		return Config.SERVER.kar98kDmg.get().floatValue();
	}

	@Override
	public float getShootTime() {
		return Config.SERVER.kar98kShootTime.get().floatValue();
	}
	
	@Override
	public float getPower() {
		return Config.SERVER.kar98kPower.get().floatValue();
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return Config.SERVER.kar98kRange.get().intValue();
	}

	@Override
	public float getRangeDamageReduction() {
		return Config.SERVER.kar98kRangeDmgRed.get().floatValue();
	}

	@Override
	public float getInnacuracy() {
		return Config.SERVER.kar98kInnacuracy.get().floatValue();
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
	public int getMaxAmmo() {
		return 5;
	}

	@Override
	public SoundEvent getShootSound() {
		return SoundRegistries.KARKSHOOT.get();
	}

}
