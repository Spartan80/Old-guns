package com.redwyvern.registries;

import com.redwyvern.mod.OldGuns;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegistries {
	public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			OldGuns.MODID);

	public static final RegistryObject<SoundEvent> HIT_MARKER_SOUND = register("hitmarker");

	public static final RegistryObject<SoundEvent> PirateRifleShootSound = register("piraterifle_shot");

	public static final RegistryObject<SoundEvent> PiratePistolShootSound = register("piratepistol_shot");

	public static final RegistryObject<SoundEvent> RevolverShootSound = register("revolver_shot");

	public static final RegistryObject<SoundEvent> TrabucoShootSound = register("trabuco_shot");

	public static final RegistryObject<SoundEvent> WinchesterShootSound = register("winchester_shot");

	public static final RegistryObject<SoundEvent> Kar98kShootSound = register("kark_shot");

	public static final RegistryObject<SoundEvent> KarkHammerBackSound = register("kark_hammer_back");

	public static final RegistryObject<SoundEvent> KarkHammerFrontSound = register("kark_hammer_front");

	public static final RegistryObject<SoundEvent> FlintlockHammerSound = register("flintlock_hammer");

	public static final RegistryObject<SoundEvent> Kar98kHammerShootSound = register("kark_hammer_shot");

	public static final RegistryObject<SoundEvent> LoadingBulletSound = register("loading_bullet");

	public static final RegistryObject<SoundEvent> RevolverSpinSound = register("revolver_spin");

	public static final RegistryObject<SoundEvent> RevolverBulletsInsideSound = register("revolver_bullets_inside");

	public static final RegistryObject<SoundEvent> RevolverBulletsOutSound = register("revolver_bullets_out");

	public static final RegistryObject<SoundEvent> WinchesterHammerReloadSound = register("winchester_hammer_reload");

	private static RegistryObject<SoundEvent> register(String key) {
		return REGISTER.register(key, () -> new SoundEvent(new ResourceLocation(OldGuns.MODID, key)));
	}

}
