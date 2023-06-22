package com.jg.oldguns.config;

import java.io.File;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

	public static final ForgeConfigSpec client_config;
	public static final Config.Client CLIENT;

	public static final ForgeConfigSpec server_config;
	public static final Config.Server SERVER;
	
	public static class Server {
		
		// Crafting
		
		public final ForgeConfigSpec.BooleanValue craftAks74u;
		public final ForgeConfigSpec.BooleanValue craftColt1911;
		public final ForgeConfigSpec.BooleanValue craftGalil;
		public final ForgeConfigSpec.BooleanValue craftIthacaModel37;
		public final ForgeConfigSpec.BooleanValue craftKar98k;
		public final ForgeConfigSpec.BooleanValue craftMp40;
		public final ForgeConfigSpec.BooleanValue craftScorpion;
		public final ForgeConfigSpec.BooleanValue craftSten;
		public final ForgeConfigSpec.BooleanValue craftThompson;
		public final ForgeConfigSpec.BooleanValue craftWinchester;
		
		public final ForgeConfigSpec.IntValue smallBulletCraftingResult;
		public final ForgeConfigSpec.IntValue mediumBulletCraftingResult;
		public final ForgeConfigSpec.IntValue bigBulletCraftingResult;
		public final ForgeConfigSpec.IntValue shotgunBulletCraftingResult;
		
		// Guns
		
		public final ForgeConfigSpec.DoubleValue aksDmg;
		public final ForgeConfigSpec.IntValue aksShootTime;
		public final ForgeConfigSpec.IntValue aksPower;
		public final ForgeConfigSpec.DoubleValue aksRange;
		public final ForgeConfigSpec.DoubleValue aksRangeDmgRed;
		public final ForgeConfigSpec.DoubleValue aksInnacuracy;
		public final ForgeConfigSpec.DoubleValue aksMeleeDmg;
		
		public final ForgeConfigSpec.DoubleValue colt1911Dmg;
		public final ForgeConfigSpec.IntValue colt1911ShootTime;
		public final ForgeConfigSpec.IntValue colt1911Power;
		public final ForgeConfigSpec.DoubleValue colt1911Range;
		public final ForgeConfigSpec.DoubleValue colt1911RangeDmgRed;
		public final ForgeConfigSpec.DoubleValue colt1911Innacuracy;
		public final ForgeConfigSpec.DoubleValue colt1911MeleeDmg;
		
		public final ForgeConfigSpec.DoubleValue galilDmg;
		public final ForgeConfigSpec.IntValue galilShootTime;
		public final ForgeConfigSpec.IntValue galilPower;
		public final ForgeConfigSpec.DoubleValue galilRange;
		public final ForgeConfigSpec.DoubleValue galilRangeDmgRed;
		public final ForgeConfigSpec.DoubleValue galilInnacuracy;
		public final ForgeConfigSpec.DoubleValue galilMeleeDmg;
		
		public final ForgeConfigSpec.DoubleValue ithacaModel37Dmg;
		public final ForgeConfigSpec.IntValue ithacaModel37ShootTime;
		public final ForgeConfigSpec.IntValue ithacaModel37Power;
		public final ForgeConfigSpec.DoubleValue ithacaModel37Range;
		public final ForgeConfigSpec.DoubleValue ithacaModel37RangeDmgRed;
		public final ForgeConfigSpec.DoubleValue ithacaModel37Innacuracy;
		public final ForgeConfigSpec.DoubleValue ithacaModel37MeleeDmg;
		
		public final ForgeConfigSpec.DoubleValue kar98kDmg;
		public final ForgeConfigSpec.IntValue kar98kShootTime;
		public final ForgeConfigSpec.IntValue kar98kPower;
		public final ForgeConfigSpec.DoubleValue kar98kRange;
		public final ForgeConfigSpec.DoubleValue kar98kRangeDmgRed;
		public final ForgeConfigSpec.DoubleValue kar98kInnacuracy;
		public final ForgeConfigSpec.DoubleValue kar98kMeleeDmg;
		
		public final ForgeConfigSpec.DoubleValue mp40Dmg;
		public final ForgeConfigSpec.IntValue mp40ShootTime;
		public final ForgeConfigSpec.IntValue mp40Power;
		public final ForgeConfigSpec.DoubleValue mp40Range;
		public final ForgeConfigSpec.DoubleValue mp40RangeDmgRed;
		public final ForgeConfigSpec.DoubleValue mp40Innacuracy;
		public final ForgeConfigSpec.DoubleValue mp40MeleeDmg;
		
		public final ForgeConfigSpec.DoubleValue scorpionDmg;
		public final ForgeConfigSpec.IntValue scorpionShootTime;
		public final ForgeConfigSpec.IntValue scorpionPower;
		public final ForgeConfigSpec.DoubleValue scorpionRange;
		public final ForgeConfigSpec.DoubleValue scorpionRangeDmgRed;
		public final ForgeConfigSpec.DoubleValue scorpionInnacuracy;
		public final ForgeConfigSpec.DoubleValue scorpionMeleeDmg;
		
		public final ForgeConfigSpec.DoubleValue stenDmg;
		public final ForgeConfigSpec.IntValue stenShootTime;
		public final ForgeConfigSpec.IntValue stenPower;
		public final ForgeConfigSpec.DoubleValue stenRange;
		public final ForgeConfigSpec.DoubleValue stenRangeDmgRed;
		public final ForgeConfigSpec.DoubleValue stenInnacuracy;
		public final ForgeConfigSpec.DoubleValue stenMeleeDmg;
		
		public final ForgeConfigSpec.DoubleValue thompsonDmg;
		public final ForgeConfigSpec.IntValue thompsonShootTime;
		public final ForgeConfigSpec.IntValue thompsonPower;
		public final ForgeConfigSpec.DoubleValue thompsonRange;
		public final ForgeConfigSpec.DoubleValue thompsonRangeDmgRed;
		public final ForgeConfigSpec.DoubleValue thompsonInnacuracy;
		public final ForgeConfigSpec.DoubleValue thompsonMeleeDmg;
		
		public final ForgeConfigSpec.DoubleValue winchesterDmg;
		public final ForgeConfigSpec.IntValue winchesterShootTime;
		public final ForgeConfigSpec.IntValue winchesterPower;
		public final ForgeConfigSpec.DoubleValue winchesterRange;
		public final ForgeConfigSpec.DoubleValue winchesterRangeDmgRed;
		public final ForgeConfigSpec.DoubleValue winchesterInnacuracy;
		public final ForgeConfigSpec.DoubleValue winchesterMeleeDmg;
		
		public Server(ForgeConfigSpec.Builder builder) {
			
			// Crafting
			
			builder.push("Crafting");
			
			craftAks74u = builder.comment("Do you want to prevent players from crafting aks74-u?")
					.define("cancel aks74-u crafting?", false);
			craftColt1911 = builder.comment("Do you want to prevent players from crafting colt1911?")
					.define("cancel colt1911 crafting?", false);
			craftGalil = builder.comment("Do you want to prevent players from crafting Galil?")
					.define("cancel galil crafting?", false);
			craftIthacaModel37 = builder.comment("Do you want to prevent players from crafting Ithaca Model 37?")
					.define("cancel ithaca model 37 crafting?", false);
			craftKar98k = builder.comment("Do you want to prevent players from crafting Kar98k?")
					.define("cancel kar98k crafting?", false);
			craftMp40 = builder.comment("Do you want to prevent players from crafting Mp40?")
					.define("cancel mp40 crafting?", false);
			craftScorpion = builder.comment("Do you want to prevent players from crafting Scorpion?")
					.define("cancel scorpion crafting?", false);
			craftSten = builder.comment("Do you want to prevent players from crafting Sten?")
					.define("cancel sten crafting?", false);
			craftThompson = builder.comment("Do you want to prevent players from crafting Thompson?")
					.define("cancel thompson crafting?", false);
			craftWinchester = builder.comment("Do you want to prevent players from crafting Winchester?")
					.define("cancel winchester crafting?", false);
			
			builder.push("How many bullets would you like to get by crafting a bullet recipe?");
			smallBulletCraftingResult = builder.comment("smallBullet")
					.defineInRange("smallBullet result", 4, 1, 20);
			mediumBulletCraftingResult = builder.comment("mediumBullet")
					.defineInRange("mediumBullet result", 3, 1, 20);
			bigBulletCraftingResult = builder.comment("bigBullet")
					.defineInRange("bigBullet result", 2, 1, 20);
			shotgunBulletCraftingResult = builder.comment("shotgunBullet")
					.defineInRange("shotgunBullet result", 3, 1, 20);
			builder.pop();
			builder.pop();
			
			// Guns
			
			builder.push("guns");
			
			builder.push("Aks-74u");
			aksDmg = builder.comment("aksDamage").defineInRange("aksDamage", 4, 0.1F, 20);
			aksShootTime = builder.comment("aksShootTime").defineInRange("aksShootTime", 2, 0, 20);
			aksPower = builder.comment("aksPower").defineInRange("aksPower", 12, 0, 200);
			aksRange = builder.comment("aks - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("aksRange", 14, 0.1D, 20);
			aksRangeDmgRed = builder
					.comment("aksRangeDmgRed - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("aksRangeDmgRed", 0.98f, 0.1D, 20);
			aksInnacuracy = builder.comment("aksInnacuracy").defineInRange("aksInnacuracy", 0.1f, 0.01D, 20);
			aksMeleeDmg = builder.comment("aksMeleeDamage").defineInRange("aksMeleeDamage", 4, 0.1D, 20);
			builder.pop();
			
			builder.push("Colt1911");
			colt1911Dmg = builder.comment("colt1911Damage").defineInRange("colt1911Damage", 3, 0.1D, 20);
			colt1911ShootTime = builder.comment("colt1911ShootTime").defineInRange("colt1911ShootTime", 4, 0, 20);
			colt1911Power = builder.comment("colt1911Power").defineInRange("colt1911Power", 8, 0, 200);
			colt1911Range = builder.comment("colt1911Range - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("colt1911Range", 6, 0.1D, 20);
			colt1911RangeDmgRed = builder.comment("colt1911RangeDmgRed").defineInRange("colt1911RangeDmgRed", 0.7f, 0.1D, 20);
			colt1911Innacuracy = builder.comment("colt1911Innacuracy").defineInRange("colt1911Innacuracy", 0.1f, 0.01D, 20);
			colt1911MeleeDmg = builder.comment("colt1911MeleeDamage").defineInRange("colt1911MeleeDamage", 4, 0.1D, 20);
			builder.pop();
			
			builder.push("Galil");
			galilDmg = builder.comment("galilDamage").defineInRange("galilDamage", 6, 0.1F, 20);
			galilShootTime = builder.comment("galilShootTime").defineInRange("galilShootTime", 3, 0, 20);
			galilPower = builder.comment("galilPower").defineInRange("galilPower", 16, 0, 200);
			galilRange = builder.comment("galil - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("galilRange", 20, 0.1D, 20);
			galilRangeDmgRed = builder.comment("galilRangeDmgRed").defineInRange("galilRangeDmgRed", 0.93f, 0.1D, 20);
			galilInnacuracy = builder.comment("galilInnacuracy").defineInRange("galilInnacuracy", 0.1f, 0.01D, 20);
			galilMeleeDmg = builder.comment("galilMeleeDamage").defineInRange("galilMeleeDamage", 6, 0.1D, 20);
			builder.pop();
			
			builder.push("IthacaModel37");
			ithacaModel37Dmg = builder.comment("ithacaModel37Damage").defineInRange("ithacaDamage", 2, 0.1F, 20);
			ithacaModel37ShootTime = builder.comment("ithacaModel37ShootTime").defineInRange("ithacaModel37ShootTime", 4, 0, 20);
			ithacaModel37Power = builder.comment("ithacaModel37Power").defineInRange("ithacaModel37Power", 8, 0, 200);
			ithacaModel37Range = builder.comment("ithacaModel37 - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("ithacaModel37Range", 4, 0.1D, 20);
			ithacaModel37RangeDmgRed = builder.comment("ithacaModel37RangeDmgRed").defineInRange("ithacaModel37RangeDmgRed", 0.4f, 0.1D, 20);
			ithacaModel37Innacuracy = builder.comment("ithacaModel37Innacuracy").defineInRange("ithacaModel37Innacuracy", 2f, 0.01D, 20);
			ithacaModel37MeleeDmg = builder.comment("ithacaModel37MeleeDamage").defineInRange("ithacaModel37MeleeDamage", 6, 0.1D, 20);
			builder.pop();
			
			builder.push("Kar98k");
			kar98kDmg = builder.comment("kar98kDamage").defineInRange("kar98kDamage", 12, 0.1F, 20);
			kar98kShootTime = builder.comment("kar98kShootTime").defineInRange("kar98kShootTime", 2, 0, 20);
			kar98kPower = builder.comment("kar98kPower").defineInRange("kar98kPower", 16, 0, 200);
			kar98kRange = builder.comment("kar98k - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("kar98kRange", 20, 0.1D, 20);
			kar98kRangeDmgRed = builder.comment("kar98kRangeDmgRed").defineInRange("kar98kRangeDmgRed", 0.9f, 0.1D, 20);
			kar98kInnacuracy = builder.comment("kar98kInnacuracy").defineInRange("kar98kInnacuracy", 0.04f, 0.01D, 20);
			kar98kMeleeDmg = builder.comment("kar98kMeleeDamage").defineInRange("kar98kMeleeDamage", 6, 0.1D, 20);
			builder.pop();
			
			builder.push("Mp40");
			mp40Dmg = builder.comment("mp40Damage").defineInRange("mp40Damage", 2, 0.1F, 20);
			mp40ShootTime = builder.comment("mp40ShootTime").defineInRange("mp40ShootTime", 3, 0, 20);
			mp40Power = builder.comment("mp40Power").defineInRange("mp40Power", 8, 0, 200);
			mp40Range = builder.comment("mp40 - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("mp40Range", 8, 0.1D, 20);
			mp40RangeDmgRed = builder.comment("mp40RangeDmgRed").defineInRange("mp40RangeDmgRed", 0.7f, 0.1D, 20);
			mp40Innacuracy = builder.comment("mp40Innacuracy").defineInRange("mp40Innacuracy", 0.1f, 0.01D, 20);
			mp40MeleeDmg = builder.comment("mp40MeleeDamage").defineInRange("mp40MeleeDamage", 4, 0.1D, 20);
			builder.pop();
			
			builder.push("Scorpion");
			scorpionDmg = builder.comment("scorpionDamage").defineInRange("scorpionDamage", 3.4f, 0.1F, 20);
			scorpionShootTime = builder.comment("scorpionShootTime").defineInRange("scorpionShootTime", 1, 0, 20);
			scorpionPower = builder.comment("scorpionPower").defineInRange("scorpionPower", 9, 0, 200);
			scorpionRange = builder.comment("scorpion - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("scorpionRange", 7, 0.1D, 20);
			scorpionRangeDmgRed = builder.comment("scorpionRangeDmgRed").defineInRange("scorpionRangeDmgRed", 0.7f, 0.1D, 20);
			scorpionInnacuracy = builder.comment("scorpionInnacuracy").defineInRange("scorpionInnacuracy", 0.3f, 0.01D, 20);
			scorpionMeleeDmg = builder.comment("scorpionMeleeDamage").defineInRange("scorpionMeleeDamage", 4, 0.1D, 20);
			builder.pop();
			
			builder.push("Sten");
			stenDmg = builder.comment("stenDamage").defineInRange("stenDamage", 4, 0.1F, 20);
			stenShootTime = builder.comment("stenShootTime").defineInRange("stenShootTime", 2, 0, 20);
			stenPower = builder.comment("stenPower").defineInRange("stenPower", 12, 0, 200);
			stenRange = builder.comment("sten - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("stenRange", 8, 0.1D, 20);
			stenRangeDmgRed = builder.comment("stenRangeDmgRed").defineInRange("stenRangeDmgRed", 0.8f, 0.1D, 20);
			stenInnacuracy = builder.comment("stenInnacuracy").defineInRange("stenInnacuracy", 0.2f, 0.01D, 20);
			stenMeleeDmg = builder.comment("stenMeleeDamage").defineInRange("stenMeleeDamage", 4, 0.1D, 20);
			builder.pop();
			
			builder.push("Thompson");
			thompsonDmg = builder.comment("thompsonDamage").defineInRange("thompsonDamage", 6, 0.1F, 20);
			thompsonShootTime = builder.comment("thompsonShootTime").defineInRange("thompsonShootTime", 2, 0, 20);
			thompsonPower = builder.comment("thompsonPower").defineInRange("thompsonPower", 8, 0, 200);
			thompsonRange = builder.comment("thompson - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("thompsonRange", 9, 0.1D, 20);
			thompsonRangeDmgRed = builder.comment("thompsonRangeDmgRed").defineInRange("thompsonRangeDmgRed", 0.9f, 0.1D, 20);
			thompsonInnacuracy = builder.comment("thompsonInnacuracy").defineInRange("thompsonInnacuracy", 0.3f, 0.01D, 20);
			thompsonMeleeDmg = builder.comment("thompsonMeleeDamage").defineInRange("thompsonMeleeDamage", 5, 0.1D, 20);
			builder.pop();
			
			builder.push("Winchester");
			winchesterDmg = builder.comment("winchesterDamage").defineInRange("winchesterDamage", 6, 0.1F, 20);
			winchesterShootTime = builder.comment("winchesterShootTime").defineInRange("winchesterShootTime", 2, 0, 20);
			winchesterPower = builder.comment("winchesterPower").defineInRange("winchesterPower", 14, 0, 200);
			winchesterRange = builder.comment("winchester - This value will multiply gunDmg when bullet range is rechead - for example: dmgRed: 0.9 dmg: 4 -> 4 * 0.9 = 3.6 <- this will happen every time the range is reached").defineInRange("winchesterRange", 20, 0.1D, 20);
			winchesterRangeDmgRed = builder.comment("winchesterRangeDmgRed").defineInRange("winchesterRangeDmgRed", 0.8f, 0.1D, 20);
			winchesterInnacuracy = builder.comment("winchesterInnacuracy").defineInRange("winchesterInnacuracy", 0.1f, 0.01D, 20);
			winchesterMeleeDmg = builder.comment("winchesterMeleeDamage").defineInRange("winchesterMeleeDamage", 6, 0.1D, 20);
			builder.pop();
			
			builder.pop();
		}
		
	}
	
	public static class Client {
		
		public final ForgeConfigSpec.BooleanValue doDebugStuff;
		public final ForgeConfigSpec.IntValue hitmarkerTime;
		
		public Client(ForgeConfigSpec.Builder builder) {
			builder.push("Debug config");
			{
				doDebugStuff = builder.comment("if you want to make transforms to the gun parts or hands active this")
						.define("do debug stuff", false);
				hitmarkerTime = builder.comment("Hitmarker duration")
						.defineInRange("Hitmarker Duration", 4, 0, 50);
			}
		}
	}
	
	static {
		final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		client_config = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();

		final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
		server_config = serverSpecPair.getRight();
		SERVER = serverSpecPair.getLeft();
	}

	public static void loadConfig(ForgeConfigSpec config, String path) {
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
				.writingMode(WritingMode.REPLACE).build();
		file.load();
		config.setConfig(file);

	}

	public static void saveClientConfig() {
		client_config.save();
	}
	
	public enum Properties {
		DMG, SHOOTTIME, POWER, RANGE, RANGEDMGRED, INN, MELEEDMG; 
	}
	
}
