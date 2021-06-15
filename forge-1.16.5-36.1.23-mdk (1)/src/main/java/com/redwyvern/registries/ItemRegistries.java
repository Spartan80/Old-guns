package com.redwyvern.registries;

import com.redwyvern.items.AttachmentItem;
import com.redwyvern.items.BigBullet;
import com.redwyvern.items.GunPart;
import com.redwyvern.items.GunPartMold;
import com.redwyvern.items.MusketBullet;
import com.redwyvern.items.guns.Kar98K;
import com.redwyvern.items.guns.PiratePistol;
import com.redwyvern.items.guns.PirateRifle;
import com.redwyvern.items.guns.Revolver;
import com.redwyvern.items.guns.Trabuco;
import com.redwyvern.items.guns.WinchesterRifle;
import com.redwyvern.mod.OldGuns;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistries {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OldGuns.MODID);

	public static final RegistryObject<Item> kar98k = ITEMS.register("guns/kark/kark", () -> new Kar98K());

	public static final RegistryObject<Item> trabuco = ITEMS.register("guns/trabuco/trabuco", () -> new Trabuco());

	public static final RegistryObject<Item> pirate_pistol = ITEMS.register("guns/piratepistol/pirate_pistol",
			() -> new PiratePistol());

	public static final RegistryObject<Item> revolver = ITEMS.register("guns/revolver/revolver", () -> new Revolver());

	public static final RegistryObject<Item> pirate_rifle = ITEMS.register("guns/piraterifle/pirate_rifle",
			() -> new PirateRifle());

	public static final RegistryObject<Item> winchester = ITEMS.register("guns/winchester/winchester",
			() -> new WinchesterRifle());

	public static final RegistryObject<Item> piratebullet = ITEMS.register("musket_bullet", () -> new MusketBullet());

	public static final RegistryObject<Item> steel_ingot = ITEMS.register("steel_ingot",
			() -> new Item(new Item.Properties().tab(OldGuns.modtab).stacksTo(64)));

	public static final RegistryObject<Item> mold = ITEMS.register("mold", () -> new GunPartMold());

	public static final RegistryObject<Item> big_bullet = ITEMS.register("big_bullet", () -> new BigBullet());

	public static final RegistryObject<Item> ppb = ITEMS.register("guns/piratepistol/pirate_pistol_b",
			() -> new GunPart());

	public static final RegistryObject<Item> ppby = ITEMS.register("guns/piratepistol/pirate_pistol_bw",
			() -> new GunPart());

	public static final RegistryObject<Item> ppbm = ITEMS.register("guns/piratepistol/pirate_pistol_bm",
			() -> new GunPart());

	public static final RegistryObject<Item> prb = ITEMS.register("guns/piraterifle/pirate_rifle_b",
			() -> new GunPart());

	public static final RegistryObject<Item> prby = ITEMS.register("guns/piraterifle/pirate_rifle_bw",
			() -> new GunPart());

	public static final RegistryObject<Item> prbm = ITEMS.register("guns/piraterifle/pirate_rifle_bm",
			() -> new GunPart());

	public static final RegistryObject<Item> wbw = ITEMS.register("guns/winchester/winchester_bw", () -> new GunPart());

	public static final RegistryObject<Item> wb = ITEMS.register("guns/winchester/winchester_b", () -> new GunPart());

	public static final RegistryObject<Item> wbm = ITEMS.register("guns/winchester/winchester_bm", () -> new GunPart());

	public static final RegistryObject<Item> tbw = ITEMS.register("guns/trabuco/trabuco_bw", () -> new GunPart());

	public static final RegistryObject<Item> tb = ITEMS.register("guns/trabuco/trabuco_b", () -> new GunPart());

	public static final RegistryObject<Item> tbm = ITEMS.register("guns/trabuco/trabuco_bm", () -> new GunPart());

	public static final RegistryObject<Item> rbw = ITEMS.register("guns/revolver/revolver_bw", () -> new GunPart());

	public static final RegistryObject<Item> rb = ITEMS.register("guns/revolver/revolver_b", () -> new GunPart());

	public static final RegistryObject<Item> rbm = ITEMS.register("guns/revolver/revolver_bm", () -> new GunPart());

	public static final RegistryObject<Item> kbw = ITEMS.register("guns/kark/kark_bw", () -> new GunPart());

	public static final RegistryObject<Item> kb = ITEMS.register("guns/kark/kark_b", () -> new GunPart());

	public static final RegistryObject<Item> kbm = ITEMS.register("guns/kark/kark_bm", () -> new GunPart());

	public static final RegistryObject<Item> scope = ITEMS.register("scope", () -> new AttachmentItem());

	public static final RegistryObject<Item> iron_with_coal = ITEMS.register("iron_with_coal",
			() -> new Item(new Item.Properties().tab(OldGuns.modtab).stacksTo(64)));

	public static final RegistryObject<BlockItem> gun_crafting = ITEMS.register("gun_crafting_block",
			() -> new BlockItem(BlockRegistries.gun_crafting_block.get(), new Item.Properties().tab(OldGuns.modtab)));

	public static final RegistryObject<BlockItem> smelting_itemblock = ITEMS.register("smelting_parts_block",
			() -> new BlockItem(BlockRegistries.smelting_parts_block.get(), new Item.Properties().tab(OldGuns.modtab)));

	public static final RegistryObject<BlockItem> molding_itemblock = ITEMS.register("molding_parts_block",
			() -> new BlockItem(BlockRegistries.molding_parts_block.get(), new Item.Properties().tab(OldGuns.modtab)));

	public static final RegistryObject<BlockItem> steel_block = ITEMS.register("steel_block",
			() -> new BlockItem(BlockRegistries.steel_block.get(), new Item.Properties().tab(OldGuns.modtab)));

}
