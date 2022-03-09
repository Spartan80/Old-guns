package com.jg.oldguns.registries;

import com.jg.oldguns.OldGuns;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistries {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			OldGuns.MODID);

	public static final RegistryObject<SoundEvent> KARKSHOOT = register("karkshoot");

	public static final RegistryObject<SoundEvent> KARKBACK = register("karkback");

	public static final RegistryObject<SoundEvent> KARKFORWARD = register("karkforward");

	public static final RegistryObject<SoundEvent> KARKPUSH = register("karkpushbullet");

	public static final RegistryObject<SoundEvent> COLTSHOOT = register("coltshot");

	public static final RegistryObject<SoundEvent> COLTMAGIN = register("coltmagin");

	public static final RegistryObject<SoundEvent> COLTMAGOUT = register("coltmagout");

	public static final RegistryObject<SoundEvent> COLTSLIDER = register("coltslider");

	public static final RegistryObject<SoundEvent> THOMPSONCOCKING = register("thompsoncocking");

	public static final RegistryObject<SoundEvent> THOMPSONMAGIN = register("thompsonmagin");

	public static final RegistryObject<SoundEvent> THOMPSONMAGOUT = register("thompsonmagout");

	public static final RegistryObject<SoundEvent> THOMPSONSHOOT = register("thompsonshoot");

	public static final RegistryObject<SoundEvent> WINCHESTERBOLTBACK = register("winchesterboltback");

	public static final RegistryObject<SoundEvent> WINCHESTERBOLTFORWARD = register("winchesterboltforward");

	public static final RegistryObject<SoundEvent> WINCHESTERINSERTSHELL = register("winchesterinsertshell");

	public static final RegistryObject<SoundEvent> WINCHESTERSHOOT = register("winchestershoot");

	public static final RegistryObject<SoundEvent> PPSHOOT = register("ppshoot");

	public static final RegistryObject<SoundEvent> PRSHOOT = register("prshoot");

	public static final RegistryObject<SoundEvent> FLINTLOCK_HAMMER = register("flintlock_hammer");

	public static final RegistryObject<SoundEvent> MP40_CLIPIN = register("mp40clipin");

	public static final RegistryObject<SoundEvent> MP40_CLIPOUT = register("mp40clipout");

	public static final RegistryObject<SoundEvent> MP40COKING_FIRST = register("mp40cocking_first");

	public static final RegistryObject<SoundEvent> MP40COKING_LAST = register("mp40cocking_last");

	public static final RegistryObject<SoundEvent> MP40SHOOT = register("mp40shoot");

	public static final RegistryObject<SoundEvent> LUGERMAGIN = register("pmmagazinein");

	public static final RegistryObject<SoundEvent> LUGERMAGOUT = register("pmmagazineout");

	public static final RegistryObject<SoundEvent> LUGERSHOOT = register("pmshoot");

	public static final RegistryObject<SoundEvent> LUGERSLIDE = register("pmsliderelease");
	
	public static final RegistryObject<SoundEvent> REVOLVERSHOT = register("revolver_shot");
	
	public static final RegistryObject<SoundEvent> REVOLVERSPIN = register("revolver_spin");
	
	public static final RegistryObject<SoundEvent> REVOLVER_BULLETS_INSIDE = register("revolver_bullets_inside");
	
	public static final RegistryObject<SoundEvent> REVOLVER_BULLETS_OUT = register("revolver_bullets_out");
	
	public static final RegistryObject<SoundEvent> MODEL37_SHELL_INSERT = register("hauer77shellinsert");
	
	public static final RegistryObject<SoundEvent> MODEL37_SHOOT = register("hauer77shoot");
	
	public static final RegistryObject<SoundEvent> MODEL37_SLIDE_BACK = register("hauer77slideback");
	
	public static final RegistryObject<SoundEvent> MODEL37_SLIDE_FORWARD = register("hauer77slideforward");

	public static final RegistryObject<SoundEvent> MELEE = register("melee");
	
	public static final RegistryObject<SoundEvent> SWING = register("swing");
	
	public static final RegistryObject<SoundEvent> HITMARKER = register("hitmarker");

	public static final RegistryObject<SoundEvent> CRAFT_SOUND = register("craft_sound");

	public static final RegistryObject<SoundEvent> HAMMER_WORKING_SOUND = register("hammer_working");

	public static RegistryObject<SoundEvent> register(String key) {
		return SOUNDS.register(key, () -> new SoundEvent(new ResourceLocation(OldGuns.MODID, key)));
	}
}
