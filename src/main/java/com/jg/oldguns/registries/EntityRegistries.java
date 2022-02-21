package com.jg.oldguns.registries;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.utils.Util;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistries {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			OldGuns.MODID);

	public static final RegistryObject<EntityType<Bullet>> BULLET = ENTITIES.register("bullet",
			() -> EntityType.Builder.<Bullet>of(Bullet::new, EntityClassification.MISC).sized(0.6f, 0.6f)
					.build(Util.locR("bullet").toString()));

}
