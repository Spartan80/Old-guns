package com.jg.oldguns.registries;

import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.ItemsReg;
import com.jg.oldguns.guns.*;
import com.jg.oldguns.guns.GunPart.GunPartProperties;
import com.jg.oldguns.guns.items.Winchester;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistries {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister
			.create(ForgeRegistries.ITEMS, OldGuns.MODID);
	
	public static final RegistryObject<Item> BigBullet = regAddExtraItem("bullets/big_bullet",
			() -> new BulletItem(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					BulletItem.BIG, 1, 3, true));
	
	public static final RegistryObject<Item> MediumBullet = regAddExtraItem("bullets/medium_bullet",
			() -> new BulletItem(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					BulletItem.MEDIUM, 1, 1, false));

	public static final RegistryObject<Item> SmallBullet = regAddExtraItem("bullets/small_bullet",
			() -> new BulletItem(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					BulletItem.SMALL, 1, 1, false));

	public static final RegistryObject<Item> ShotgunBullet = regAddExtraItem("bullets/shotgun_bullet",
			() -> new BulletItem(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					BulletItem.SHOTGUN, 2, 2, false));
	
	public static final RegistryObject<Item> WINCHESTERST = regAdd("winchester_st", 
			() -> new GunPart(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 0));
	
	public static final RegistryObject<Item> WINCHESTERBO = regAdd("winchester_bo", 
			() -> new GunPart(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64),
					new GunPartProperties(), 1));
	
	public static final RegistryObject<Item> WINCHESTERBA = regAdd("winchester_ba", 
			() -> new GunPart(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 2));
	
	public static final RegistryObject<Item> WINCHESTERBAGRIP = regAdd("winchester_ba_grip", 
			() -> new GunPart(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties().bulletsPerShoot(2), 2));
	
	public static final RegistryObject<Item> WINCHESTER = regAdd("winchester", 
			() -> new Winchester(new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
	public static <I extends Item> RegistryObject<I> regAddGun(String name, final Supplier<? extends I> sup) {
		RegistryObject<I> obj = ITEMS.register(name, sup);
		ItemsReg.INSTANCE.registerGun(obj);
		return obj;
	}

	public static <I extends Item> RegistryObject<I> regAddGunPart(String name, String id,
			final Supplier<? extends I> sup) {
		RegistryObject<I> obj = ITEMS.register(name, sup);
		ItemsReg.INSTANCE.registerPartForGun(obj, id);
		return obj;
	}

	public static <I extends Item> RegistryObject<I> regAddExtraItem(String name, final Supplier<? extends I> sup) {
		RegistryObject<I> obj = ITEMS.register(name, sup);
		ItemsReg.INSTANCE.registerExtraItem(obj);
		return obj;
	}
	
	public static <I extends Item> RegistryObject<I> regAdd(String name, final Supplier<? extends I> sup) {
		RegistryObject<I> obj = ITEMS.register(name, sup);
		return obj;
	}
}
