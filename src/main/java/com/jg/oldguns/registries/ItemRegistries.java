package com.jg.oldguns.registries;

import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.ItemsReg;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.GunPart.GunPartProperties;
import com.jg.oldguns.guns.MagItem;
import com.jg.oldguns.guns.items.Aks74u;
import com.jg.oldguns.guns.items.Colt1911;
import com.jg.oldguns.guns.items.Galil;
import com.jg.oldguns.guns.items.IthacaModel37;
import com.jg.oldguns.guns.items.Kar98k;
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
	
	public static final RegistryObject<Item> MP40MAG = regAddMag("mp40_mag", 
			() -> new MagItem(new Item.Properties().tab(OldGuns.getTab()), "mp40", 20, 
					BulletItem.SMALL, 2, false, 0, "oldguns:bullet"));
	
	public static final RegistryObject<Item> MP40 = regAddGun("mp40", 
			() -> new Mp40("mp40", new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
	public static final RegistryObject<Item> AKS74UST = regAddGunPart("aks-74u_st", 
			"aks-74u", () -> new GunPart("aks-74u", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 1, 3, 0, false));
	
	public static final RegistryObject<Item> AKS74UBO = regAddGunPart("aks-74u_bo", 
			"aks-74u", () -> new GunPart("aks-74u", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64),
					new GunPartProperties(), 2, 4, 3, true));
	
	public static final RegistryObject<Item> AKS74UBA = regAddGunPart("aks-74u_ba", 
			"aks-74u", () -> new GunPart("aks-74u", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 3, 1, 3, true));
	
	public static final RegistryObject<Item> AKS74UMAG = regAddMag("aks-74u_mag", 
			() -> new MagItem(new Item.Properties().tab(OldGuns.getTab()), "aks-74u", 20, 
					BulletItem.MEDIUM, 2, false, 0, "oldguns:bullet"));
	
	public static final RegistryObject<Item> AKS74U = regAddGun("aks-74u", 
			() -> new Aks74u("aks-74u", new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
	public static final RegistryObject<Item> COLT1911ST = regAddGunPart("colt1911_st", 
			"colt1911", () -> new GunPart("colt1911", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 1, 3, 0, false));
	
	public static final RegistryObject<Item> COLT1911BO = regAddGunPart("colt1911_bo", 
			"colt1911", () -> new GunPart("colt1911", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64),
					new GunPartProperties(), 2, 4, 3, true));
	
	public static final RegistryObject<Item> COLT1911BA = regAddGunPart("colt1911_ba", 
			"colt1911", () -> new GunPart("colt1911", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 3, 1, 3, true));
	
	public static final RegistryObject<Item> COLT1911HAMMER = regAdd("colt1911_hammer", 
			() -> new Item(new Item.Properties().stacksTo(64)));
	
	public static final RegistryObject<Item> COLT1911SLIDER = regAdd("colt1911_slider", 
			() -> new Item(new Item.Properties().stacksTo(64)));
	
	public static final RegistryObject<Item> COLT1911MAG = regAddMag("colt1911_mag", 
			() -> new MagItem(new Item.Properties().tab(OldGuns.getTab()), "colt1911", 20, 
					BulletItem.SMALL, 2, false, 0, "oldguns:bullet"));
	
	public static final RegistryObject<Item> COLT1911 = regAddGun("colt1911", 
			() -> new Colt1911("colt1911", new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
	public static final RegistryObject<Item> GALILST = regAddGunPart("galil_st", 
			"galil", () -> new GunPart("galil", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 1, 3, 0, false));
	
	public static final RegistryObject<Item> GALILBO = regAddGunPart("galil_bo", 
			"galil", () -> new GunPart("galil", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64),
					new GunPartProperties(), 2, 4, 3, true));
	
	public static final RegistryObject<Item> GALILBA = regAddGunPart("galil_ba", 
			"galil", () -> new GunPart("galil", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 3, 1, 3, true));
	
	public static final RegistryObject<Item> GALILMAG = regAddMag("galil_mag", 
			() -> new MagItem(new Item.Properties().tab(OldGuns.getTab()), "galil", 20, 
					BulletItem.MEDIUM, 2, false, 0, "oldguns:bullet"));
	
	public static final RegistryObject<Item> GALIL = regAddGun("galil", 
			() -> new Galil("galil", new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
	public static final RegistryObject<Item> ITHACAMODEL37ST = regAddGunPart("ithaca_model_37_st", 
			"ithacamodel37", () -> new GunPart("ithacamodel37", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 1, 3, 0, false));
	
	public static final RegistryObject<Item> ITHACAMODEL37BO = regAddGunPart("ithaca_model_37_bo", 
			"ithacamodel37", () -> new GunPart("ithacamodel37", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64),
					new GunPartProperties(), 2, 4, 3, true));
	
	public static final RegistryObject<Item> ITHACAMODEL37BA = regAddGunPart("ithaca_model_37_ba", 
			"ithacamodel37", () -> new GunPart("ithacamodel37", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 3, 1, 3, true));
	
	public static final RegistryObject<Item> ITHACAMODEL37 = regAddGun("ithacamodel37", 
			() -> new IthacaModel37("ithacamodel37", new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
	public static final RegistryObject<Item> KAR98KST = regAddGunPart("kar98k_st", 
			"kar98k", () -> new GunPart("kar98k", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 1, 3, 0, false));
	
	public static final RegistryObject<Item> KAR98KBO = regAddGunPart("kar98k_bo", 
			"kar98k", () -> new GunPart("kar98k", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64),
					new GunPartProperties(), 2, 4, 3, true));
	
	public static final RegistryObject<Item> KAR98KBA = regAddGunPart("kar98k_ba", 
			"kar98k", () -> new GunPart("kar98k", new Item.Properties().tab(OldGuns.getTab()).stacksTo(64), 
					new GunPartProperties(), 3, 1, 3, true));
	
	public static final RegistryObject<Item> KAR98K = regAddGun("kar98k", 
			() -> new Kar98k("kar98k", new Item.Properties().tab(OldGuns.getTab()).stacksTo(1)));
	
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
	
	public static <I extends Item> RegistryObject<I> regAddMag(String name,
			final Supplier<? extends I> sup) {
		RegistryObject<I> obj = ITEMS.register(name, sup);
		ItemsReg.INSTANCE.registerMag(obj);
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
