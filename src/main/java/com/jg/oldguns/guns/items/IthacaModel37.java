package com.jg.oldguns.guns.items;

import com.jg.oldguns.config.Config;
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
		return Config.SERVER.ithacaModel37Dmg.get().floatValue();
	}

	@Override
	public float getShootTime() {
		return Config.SERVER.ithacaModel37ShootTime.get().floatValue();
	}
	
	@Override
	public float getPower() {
		return Config.SERVER.ithacaModel37Power.get().floatValue();
	}

	@Override
	public int getBulletsPerShoot() {
		return 8;
	}

	@Override
	public int getRange() {
		return Config.SERVER.ithacaModel37Range.get().intValue();
	}

	@Override
	public float getRangeDamageReduction() {
		return Config.SERVER.ithacaModel37RangeDmgRed.get().floatValue();
	}

	@Override
	public float getInnacuracy() {
		return Config.SERVER.ithacaModel37Innacuracy.get().floatValue();
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
