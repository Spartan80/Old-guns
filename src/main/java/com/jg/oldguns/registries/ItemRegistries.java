package com.jg.oldguns.registries;

import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.ItemsReg;
import com.jg.oldguns.guns.*;
import com.jg.oldguns.guns.GunPart.GunPartProperties;
import com.jg.oldguns.guns.items.Mp40;
import com.jg.oldguns.guns.items.Winchester;

import net.minecraft.world.item.BlockItem;
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
	
	public static final RegistryObject<Item> WINCHESTERST = regAddGunPart("winchester_st", 
			"winchester", () -> new GunPart("winchester", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 1, 3, 0, false));
	
	public static final RegistryObject<Item> WINCHESTERBO = regAddGunPart("winchester_bo", 
			"winchester", () -> new GunPart("winchester", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64),
					new GunPartProperties(), 2, 4, 3, true));
	
	public static final RegistryObject<Item> WINCHESTERBA = regAddGunPart("winchester_ba", 
			"winchester", () -> new GunPart("winchester", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 3, 1, 3, true));
	
	public static final RegistryObject<Item> WINCHESTERBAGRIP = regAddGunPart("winchester_ba_grip", 
			"winchester", () -> new GunPart("winchester", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties().bulletsPerShoot(2), 3, 2, 3, true));
	
	public static final RegistryObject<Item> WINCHESTER = regAddGun("winchester", 
			() -> new Winchester(new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
	public static final RegistryObject<Item> MP40ST = regAddGunPart("mp40_st", 
			"mp40", () -> new GunPart("mp40", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 1, 3, 0, false));
	
	public static final RegistryObject<Item> MP40BO = regAddGunPart("mp40_bo", 
			"mp40", () -> new GunPart("mp40", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64),
					new GunPartProperties(), 2, 4, 3, true));
	
	public static final RegistryObject<Item> MP40BA = regAddGunPart("mp40_ba", 
			"mp40", () -> new GunPart("mp40", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 3, 1, 3, true));
	
	public static final RegistryObject<Item> MP40MAG = regAddExtraItem("mp40_mag", 
			() -> new MagItem(new Item.Properties().tab(OldGuns.getTab()), "mp40", 20, 
					BulletItem.SMALL, 2, false, 0, "oldguns:bullet"));
	
	public static final RegistryObject<Item> MP40 = regAddGun("mp40", 
			() -> new Mp40("mp40", new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
	public static final RegistryObject<Item> SteelIngot = regAdd("steel_ingot",
			() -> new Item(new Item.Properties().stacksTo(64).tab(OldGuns.getTab())));

	public static final RegistryObject<Item> IronWithCoal = regAdd("iron_with_coal",
			() -> new Item(new Item.Properties().stacksTo(64).tab(OldGuns.getTab())));
	
	public static final RegistryObject<BlockItem> GunCrafting = ITEMS.register("gun_crafting_block",
			() -> new BlockItem(BlockRegistries.Gun_crafting_block.get(),
					new Item.Properties().tab(OldGuns.getTab()).stacksTo(64)));

	public static final RegistryObject<BlockItem> GunParts = ITEMS.register("gun_parts_block",
			() -> new BlockItem(BlockRegistries.Gun_parts_block.get(),
					new Item.Properties().tab(OldGuns.getTab()).stacksTo(64)));

	public static final RegistryObject<BlockItem> GunAmmo = ITEMS.register("gun_ammo_block",
			() -> new BlockItem(BlockRegistries.Gun_ammo_block.get(),
					new Item.Properties().tab(OldGuns.getTab()).stacksTo(64)));

	public static final RegistryObject<BlockItem> Steel_block = ITEMS.register("steel_block",
			() -> new BlockItem(BlockRegistries.Steel_block.get(),
					new Item.Properties().tab(OldGuns.getTab()).stacksTo(64)));
	
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
