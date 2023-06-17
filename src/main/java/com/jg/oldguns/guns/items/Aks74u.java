package com.jg.oldguns.guns.items;

import com.jg.oldguns.config.Config;
import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Paths;

import net.minecraft.sounds.SoundEvent;

public class Aks74u extends GunItem {

	public Aks74u(String gunId, Properties prop) {
		super(gunId, prop, new GunStuff(ItemRegistries.AKS74UST, ItemRegistries.AKS74UBO, 
				ItemRegistries.AKS74UBA, null, new String[] { Paths.AKS74UHAMMER },
				true, true, true));
	}

	@Override
	public float getDamage() {
		return Config.SERVER.aksDmg.get().floatValue();
	}

	@Override
	public float getShootTime() {
		return Config.SERVER.aksShootTime.get().floatValue();
	}
	
	@Override
	public float getPower() {
		return Config.SERVER.aksPower.get().floatValue();
	}

	@Override
	public int getBulletsPerShoot() {
		return 1;
	}

	@Override
	public int getRange() {
		return Config.SERVER.aksRange.get().intValue();
	}

	@Override
	public float getRangeDamageReduction() {
		return Config.SERVER.aksRangeDmgRed.get().floatValue();
	}

	@Override
	public float getInnacuracy() {
		return Config.SERVER.aksInnacuracy.get().floatValue();
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
		return SoundRegistries.AK74USHOOT.get();
	}

}
