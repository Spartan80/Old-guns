package com.jg.oldguns.guns.items;

import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class IthacaModel37 extends GunItem {

	public IthacaModel37(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.ITHACAMODEL37ST, 
				ItemRegistries.ITHACAMODEL37BO, ItemRegistries.ITHACAMODEL37BA, null, 
				new String[] { Paths.ITHACAMODEL37HAMMER }, 
				true, true, true));
	}

	@Override
	public float getDamage() {
		return 2;
	}

	@Override
	public float getShootTime() {
		return 4;
	}
	
	@Override
	public float getPower() {
		return 8;
	}

	@Override
	public int getBulletsPerShoot() {
		return 8;
	}

	@Override
	public int getRange() {
		return 4;
	}

	@Override
	public float getRangeDamageReduction() {
		return 0.4f;
	}

	@Override
	public float getInnacuracy() {
		return 2f;
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
		return SoundRegistries.MODEL37_SHOOT.get();
	}

}
