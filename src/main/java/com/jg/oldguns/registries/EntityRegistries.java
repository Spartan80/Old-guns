package com.jg.oldguns.registries;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.entities.GunBullet;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistries {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister
			.create(ForgeRegistries.ENTITIES, OldGuns.MODID);
	
	public static final RegistryObject<EntityType<GunBullet>> BULLET = ENTITIES.register("bullet", () -> EntityType.Builder
			.<GunBullet>of(GunBullet::new, MobCategory.MISC).sized(0.375F, 0.375F).build(new ResourceLocation(OldGuns.MODID, "bullet").toString()));
}
