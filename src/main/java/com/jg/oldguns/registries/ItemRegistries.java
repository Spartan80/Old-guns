package com.jg.oldguns.registries;

import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.GunPart.GunPartProperties;
import com.jg.oldguns.guns.items.Winchester;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistries {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister
			.create(ForgeRegistries.ITEMS, OldGuns.MODID);
	
	public static final RegistryObject<Item> WINCHESTERST = regAdd("winchester_st", 
			() -> new GunPart(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 0));
	
	public static final RegistryObject<Item> WINCHESTERBO = regAdd("winchester_bo", 
			() -> new GunPart(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64),
					new GunPartProperties().shootSound(null), 1));
	
	public static final RegistryObject<Item> WINCHESTERBA = regAdd("winchester_ba", 
			() -> new GunPart(new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 2));
	
	public static final RegistryObject<Item> WINCHESTER = regAdd("winchester", 
			() -> new Winchester(new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
	public static <I extends Item> RegistryObject<I> regAdd(String name, final Supplier<? extends I> sup) {
		RegistryObject<I> obj = ITEMS.register(name, sup);
		return obj;
	}
}
