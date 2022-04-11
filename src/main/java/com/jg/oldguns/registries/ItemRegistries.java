package com.jg.oldguns.registries;

import java.util.function.Supplier;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ItemsReg;
import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.ItemBullet;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.guns.items.*;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistries {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OldGuns.MODID);

	public static final RegistryObject<Item> MediumBullet = regAddExtraItem("bullets/medium_bullet",
			() -> new ItemBullet(new Item.Properties().tab(OldGuns.tab).stacksTo(64), ItemBullet.MEDIUM, 5, 1, false));

	public static final RegistryObject<Item> SmallBullet = regAddExtraItem("bullets/small_bullet",
			() -> new ItemBullet(new Item.Properties().tab(OldGuns.tab).stacksTo(64), ItemBullet.SMALL, 2, 1, false));
	
	public static final RegistryObject<Item> ShotgunBullet = regAddExtraItem("bullets/shotgun_bullet",
			() -> new ItemBullet(new Item.Properties().tab(OldGuns.tab).stacksTo(64), ItemBullet.SHOTGUN, 9, 2, false));

	public static final RegistryObject<Item> BigBullet = regAddExtraItem("bullets/big_bullet",
			() -> new ItemBullet(new Item.Properties().tab(OldGuns.tab).stacksTo(64), ItemBullet.BIG, 1, 3, true));

	public static final RegistryObject<Item> MusketBullet = regAddExtraItem("bullets/musket_bullet",
			() -> new ItemBullet(new Item.Properties().tab(OldGuns.tab).stacksTo(64), ItemBullet.MUSKET, 2, 0, false));
	
	public static final RegistryObject<Item> Kar98k = regAddGun("guns/kark",
			() -> new Kar98k(new Item.Properties().stacksTo(1).tab(OldGuns.tab), null));

	public static final RegistryObject<Item> Kar98kBarrel = regAddGunPart("guns/kark_barrel", "kark",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "kark", 0, 8, true, GunPart.HEAVY));

	public static final RegistryObject<Item> Kar98kBody = regAddGunPart("guns/kark_body", "kark",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "kark", 8, 8, true, GunPart.HEAVY));

	public static final RegistryObject<Item> Kar98kStock = regAddGunPart("guns/kark_stock", "kark",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "kark", 8, 0, true, GunPart.HEAVY));

	public static final RegistryObject<Item> PiratePistol = regAddGun("guns/pirate_pistol",
			() -> new PiratePistol(new Item.Properties().stacksTo(1).tab(OldGuns.tab), null));

	public static final RegistryObject<Item> PiratePistolBarrel = regAddGunPart("guns/pirate_pistol_barrel", "pp",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "pp", 0, 8, false, GunPart.LIGHT));

	public static final RegistryObject<Item> PiratePistolBody = regAddGunPart("guns/pirate_pistol_body", "pp",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "pp", 6, 4, false, GunPart.LIGHT));

	public static final RegistryObject<Item> PiratePistolStock = regAddGunPart("guns/pirate_pistol_stock", "pp",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "pp", 4, 0, false, GunPart.LIGHT));

	public static final RegistryObject<Item> Colt1911Barrel = regAddGunPart("guns/colt_barrel", "colt1911",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "colt1911", 0, 6, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Colt1911Body = regAddGunPart("guns/colt_body", "colt1911",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "colt1911", 0, 6, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Colt1911Handle = regAddGunPart("guns/colt_handle", "colt1911",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "colt1911", 6, 6, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Colt1911Mag = regAddExtraItem("mags/colt_mag",
			() -> new ItemMag(new Item.Properties().tab(OldGuns.tab).stacksTo(1), "colt1911", 7, ItemBullet.SMALL, 3, true, ItemMag.LIGHT, Bullet::new));

	public static final RegistryObject<Item> Colt1911 = regAddGun("guns/colt",
			() -> new Colt1911(new Item.Properties().stacksTo(1).tab(OldGuns.tab), Colt1911Mag));

	public static final RegistryObject<Item> ThompsonBarrel = regAddGunPart("guns/thompson_barrel", "thompson",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "thompson", 4, 5, true, GunPart.MEDIUM));

	public static final RegistryObject<Item> ThompsonBarrel2 = regAddGunPart("guns/thompson_barrel_2", "thompson",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "thompson", 8, 5, true, GunPart.MEDIUM));
	
	public static final RegistryObject<Item> ThompsonBody = regAddGunPart("guns/thompson_body", "thompson",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "thompson", 4, 8, true, GunPart.MEDIUM));

	public static final RegistryObject<Item> ThompsonStock = regAddGunPart("guns/thompson_stock", "thompson",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "thompson", 6, 0, true, GunPart.MEDIUM));

	public static final RegistryObject<Item> ThompsonMag = regAddExtraItem("mags/thompson_mag",
			() -> new ItemMag(new Item.Properties().tab(OldGuns.tab).stacksTo(1), "thompson", 25, ItemBullet.SMALL, 4, true, ItemMag.LIGHT, Bullet::new));
	
	public static final RegistryObject<Item> ThompsonDrumMag = regAddExtraItem("mags/thompson_drum_mag",
			() -> new ItemMag(new Item.Properties().tab(OldGuns.tab).stacksTo(1), "thompson", 50, ItemBullet.SMALL, 11, true, ItemMag.HEAVY, Bullet::new));

	public static final RegistryObject<Item> Thompson = regAddGun("guns/thompson",
			() -> new Thompson(new Item.Properties().stacksTo(1).tab(OldGuns.tab), ThompsonMag));

	public static final RegistryObject<Item> PirateRifleBarrel = regAddGunPart("guns/pirate_rifle_barrel", "pr",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "pr", 0, 8, false, GunPart.LIGHT));

	public static final RegistryObject<Item> PirateRifleBody = regAddGunPart("guns/pirate_rifle_body", "pr",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "pr", 8, 4, false, GunPart.LIGHT));

	public static final RegistryObject<Item> PirateRifleStock = regAddGunPart("guns/pirate_rifle_stock", "pr",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "pr", 4, 0, false, GunPart.LIGHT));

	public static final RegistryObject<Item> PirateRifle = regAddGun("guns/pirate_rifle",
			() -> new PirateRifle(new Item.Properties().stacksTo(1).tab(OldGuns.tab), null));

	public static final RegistryObject<Item> WinchesterBarrel = regAddGunPart("guns/winchester_barrel", "winchester",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "winchester", 4, 7, true, GunPart.HEAVY));

	public static final RegistryObject<Item> WinchesterShotgunBarrel = regAddGunPart("guns/winchester_shotgun_barrel",
			"winchester",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "winchester", 4, 9, true, GunPart.HEAVY) {
				public void mergeGunProperties(ItemStack stack){
					System.out.println("test");
				}
			});

	public static final RegistryObject<Item> WinchesterBody = regAddGunPart("guns/winchester_body", "winchester",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "winchester", 2, 8, true, GunPart.HEAVY));

	public static final RegistryObject<Item> WinchesterStock = regAddGunPart("guns/winchester_stock", "winchester",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "winchester", 7, 0, true, GunPart.HEAVY));

	public static final RegistryObject<Item> Winchester = regAddGun("guns/winchester",
			() -> new Winchester(new Item.Properties().stacksTo(1).tab(OldGuns.tab), null));

	public static final RegistryObject<Item> LugerP08Mag = regAddExtraItem("mags/luger_mag",
			() -> new ItemMag(new Item.Properties().stacksTo(1).tab(OldGuns.tab), "lugerp08", 8, ItemBullet.SMALL, 3, true, ItemMag.LIGHT, Bullet::new));

	public static final RegistryObject<Item> LugerBarrel = regAddGunPart("guns/luger_barrel", "lugerp08",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "lugerp08", 0, 6, true, GunPart.LIGHT));

	public static final RegistryObject<Item> LugerBody = regAddGunPart("guns/luger_body", "lugerp08",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "lugerp08", 0, 8, true, GunPart.LIGHT));

	public static final RegistryObject<Item> LugerHandle = regAddGunPart("guns/luger_handle", "lugerp08",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "lugerp08", 6, 7, true, GunPart.LIGHT));

	public static final RegistryObject<Item> LugerP08 = regAddGun("guns/luger",
			() -> new LugerP08(new Item.Properties().stacksTo(1).tab(OldGuns.tab), LugerP08Mag));

	public static final RegistryObject<Item> Mp40Mag = regAddExtraItem("mags/mp40_mag",
			() -> new ItemMag(new Item.Properties().stacksTo(1).tab(OldGuns.tab), "mp40", 20, ItemBullet.SMALL, 7, true, ItemMag.LIGHT, Bullet::new));

	public static final RegistryObject<Item> Mp40Barrel = regAddGunPart("guns/mp40_barrel", "mp40",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "mp40", 0, 5, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Mp40Body = regAddGunPart("guns/mp40_body", "mp40",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "mp40", 2, 6, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Mp40Stock = regAddGunPart("guns/mp40_stock", "mp40",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "mp40", 0, 6, true, GunPart.LIGHT));
	
	public static final RegistryObject<Item> Mp40 = regAddGun("guns/mp40",
			() -> new Mp40(new Item.Properties().stacksTo(1).tab(OldGuns.tab), Mp40Mag));
	
	public static final RegistryObject<Item> Ak74uMag = regAddExtraItem("mags/aks-74u_mag",
			() -> new ItemMag(new Item.Properties().stacksTo(1).tab(OldGuns.tab), "ak74u", 20, ItemBullet.SMALL, 7, true, ItemMag.LIGHT, Bullet::new));

	public static final RegistryObject<Item> Ak74uBarrel = regAddGunPart("guns/aks-74u_barrel", "ak74u",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "ak74u", 0, 5, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Ak74uBody = regAddGunPart("guns/aks-74u_body", "ak74u",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "ak74u", 2, 6, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Ak74uStock = regAddGunPart("guns/aks-74u_stock", "ak74u",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "ak74u", 0, 6, true, GunPart.LIGHT));
	
	public static final RegistryObject<Item> Ak74u = regAddGun("guns/aks-74u",
			() -> new Ak74u(new Item.Properties().stacksTo(1).tab(OldGuns.tab), Ak74uMag));
	
	public static final RegistryObject<Item> StenMag = regAddExtraItem("mags/sten_mag",
			() -> new ItemMag(new Item.Properties().stacksTo(1).tab(OldGuns.tab), "sten", 20, ItemBullet.SMALL, 7, true, ItemMag.LIGHT, Bullet::new));

	public static final RegistryObject<Item> StenBarrel = regAddGunPart("guns/sten_barrel", "sten",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "sten", 0, 5, true, GunPart.LIGHT));

	public static final RegistryObject<Item> StenBody = regAddGunPart("guns/sten_body", "sten",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "sten", 2, 6, true, GunPart.LIGHT));

	public static final RegistryObject<Item> StenStock = regAddGunPart("guns/sten_stock", "sten",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "sten", 0, 6, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Sten = regAddGun("guns/sten",
			() -> new Sten(new Item.Properties().stacksTo(1).tab(OldGuns.tab), StenMag));
	
	public static final RegistryObject<Item> AsValMag = regAddExtraItem("mags/asval_mag",
			() -> new ItemMag(new Item.Properties().stacksTo(1).tab(OldGuns.tab), "asval", 20, ItemBullet.MEDIUM, 9, true, ItemMag.MEDIUM, Bullet::new));

	public static final RegistryObject<Item> AsValBarrel = regAddGunPart("guns/asval_barrel", "asval",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "asval", 0, 9, true, GunPart.MEDIUM));

	public static final RegistryObject<Item> AsValBody = regAddGunPart("guns/asval_body", "asval",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "asval", 0, 7, true, GunPart.MEDIUM));

	public static final RegistryObject<Item> AsValStock = regAddGunPart("guns/asval_stock", "asval",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "asval", 0, 8, true, GunPart.MEDIUM));

	public static final RegistryObject<Item> AsVal = regAddGun("guns/asval",
			() -> new AsVal(new Item.Properties().stacksTo(1).tab(OldGuns.tab), AsValMag));

	public static final RegistryObject<Item> RevolverBarrel = regAddGunPart("guns/revolver_barrel", "revolver",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "revolver", 0, 3, true, GunPart.LIGHT));
	
	//public static final RegistryObject<Item> RevolverShotgunBarrel = regAddGunPart("guns/revolver_sb", "revolver",
	//		() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "revolver", 0, 3, true, GunPart.LIGHT));

	public static final RegistryObject<Item> RevolverBody = regAddGunPart("guns/revolver_body", "revolver",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "revolver", 0, 4, true, GunPart.LIGHT));

	public static final RegistryObject<Item> RevolverStock = regAddGunPart("guns/revolver_handle", "revolver",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "revolver", 4, 0, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Revolver = regAddGun("guns/revolver",
			() -> new Revolver(new Item.Properties().stacksTo(1).tab(OldGuns.tab), null));

	public static final RegistryObject<Item> Model37Barrel = regAddGunPart("guns/model37_barrel", "model37",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "model37", 3, 8, true, GunPart.HEAVY));
	
	//public static final RegistryObject<Item> Model37ExtendedBarrel = regAddGunPart("guns/model37_ext_barrel", "model37",
	//		() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "model37", 3, 10, true, GunPart.HEAVY));

	public static final RegistryObject<Item> Model37Body = regAddGunPart("guns/model37_body", "model37",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "model37", 0, 6, true, GunPart.HEAVY));

	public static final RegistryObject<Item> Model37Stock = regAddGunPart("guns/model37_stock", "model37",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "model37", 6, 0, true, GunPart.HEAVY));

	public static final RegistryObject<Item> Model37 = regAddGun("guns/model37",
			() -> new Model37(new Item.Properties().stacksTo(1).tab(OldGuns.tab), null));
	
	public static final RegistryObject<Item> GalilMag = regAddExtraItem("mags/galil_mag",
			() -> new ItemMag(new Item.Properties().stacksTo(1).tab(OldGuns.tab), "galil", 30, ItemBullet.BIG, 9, true, ItemMag.MEDIUM, Bullet::new));

	public static final RegistryObject<Item> GalilBarrel = regAddGunPart("guns/galil_barrel", "asval",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "galil", 0, 9, true, GunPart.HEAVY));

	public static final RegistryObject<Item> GalilBody = regAddGunPart("guns/galil_body", "asval",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "galil", 0, 7, true, GunPart.HEAVY));

	public static final RegistryObject<Item> GalilStock = regAddGunPart("guns/galil_stock", "asval",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "galil", 0, 8, true, GunPart.HEAVY));
	
	public static final RegistryObject<Item> Galil = regAddGun("guns/galil",
			() -> new Galil(new Item.Properties().stacksTo(1).tab(OldGuns.tab), GalilMag));
	
	public static final RegistryObject<Item> Mp5Mag = regAddExtraItem("mags/mp5_mag",
			() -> new ItemMag(new Item.Properties().stacksTo(1).tab(OldGuns.tab), "mp5", 25, ItemBullet.SMALL, 9, true, ItemMag.LIGHT, Bullet::new));

	public static final RegistryObject<Item> Mp5Barrel = regAddGunPart("guns/mp5_barrel", "asval",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 3, "mp5", 0, 9, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Mp5Body = regAddGunPart("guns/mp5_body", "asval",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 2, "mp5", 0, 7, true, GunPart.LIGHT));

	public static final RegistryObject<Item> Mp5Stock = regAddGunPart("guns/mp5_stock", "asval",
			() -> new GunPart(new Item.Properties().stacksTo(16).tab(OldGuns.tab), 1, "mp5", 0, 8, true, GunPart.LIGHT));
	
	public static final RegistryObject<Item> Mp5 = regAddGun("guns/mp5",
			() -> new Mp5(new Item.Properties().stacksTo(1).tab(OldGuns.tab), Mp5Mag));
	
	public static final RegistryObject<Item> MediumScope = regAddExtraItem("guns/med_scope",
			() -> new com.jg.oldguns.guns.Scope(new Item.Properties().stacksTo(1).tab(OldGuns.tab), 50));
	
	public static final RegistryObject<Item> SteelIngot = regAdd("steel_ingot",
			() -> new Item(new Item.Properties().stacksTo(64).tab(OldGuns.tab)));

	public static final RegistryObject<Item> IronWithCoal = regAdd("iron_with_coal",
			() -> new Item(new Item.Properties().stacksTo(64).tab(OldGuns.tab)));

	public static final RegistryObject<BlockItem> GunCrafting = ITEMS.register("gun_crafting_block",
			() -> new BlockItem(BlockRegistries.Gun_crafting_block.get(),
					new Item.Properties().tab(OldGuns.tab).stacksTo(64)));

	public static final RegistryObject<BlockItem> GunParts = ITEMS.register("gun_parts_block",
			() -> new BlockItem(BlockRegistries.Gun_parts_block.get(),
					new Item.Properties().tab(OldGuns.tab).stacksTo(64)));
	
	public static final RegistryObject<BlockItem> GunAmmo = ITEMS.register("gun_ammo_block",
			() -> new BlockItem(BlockRegistries.Gun_ammo_block.get(),
					new Item.Properties().tab(OldGuns.tab).stacksTo(64)));

	public static final RegistryObject<BlockItem> Steel_block = ITEMS.register("steel_block",
			() -> new BlockItem(BlockRegistries.Steel_block.get(),
					new Item.Properties().tab(OldGuns.tab).stacksTo(64)));

	/*public static void initGunParts(RegistryObject<Item> stock, RegistryObject<Item> body, RegistryObject<Item> barrel, GunPartsConfig config) {
		stock = ITEMS.register("guns/" + , null)
	}*/
	
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
