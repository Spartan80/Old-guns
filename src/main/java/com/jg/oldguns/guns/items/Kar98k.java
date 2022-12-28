package com.jg.oldguns.guns.items;

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
		return 12;
	}

	@Override
	public float getPower() {
		return 16;
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
		return 0.9f;
	}

	@Override
	public float getInnacuracy() {
		return 0.04f;
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
		return SoundRegistries.KARKSHOOT.get();
	}

}
