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
		
		public Server(ForgeConfigSpec.Builder builder) {
			craftAks74u = builder.comment("Do you want to prevent players from crafting aks74-u?")
					.define("cancel aks74-u crafting?", true);
			craftColt1911 = builder.comment("Do you want to prevent players from crafting colt1911?")
					.define("cancel colt1911 crafting?", true);
			craftGalil = builder.comment("Do you want to prevent players from crafting Galil?")
					.define("cancel galil crafting?", true);
			craftIthacaModel37 = builder.comment("Do you want to prevent players from crafting Ithaca Model 37?")
					.define("cancel ithaca model 37 crafting?", true);
			craftKar98k = builder.comment("Do you want to prevent players from crafting Kar98k?")
					.define("cancel kar98k crafting?", true);
			craftMp40 = builder.comment("Do you want to prevent players from crafting Mp40?")
					.define("cancel mp40 crafting?", true);
			craftScorpion = builder.comment("Do you want to prevent players from crafting Scorpion?")
					.define("cancel scorpion crafting?", true);
			craftSten = builder.comment("Do you want to prevent players from crafting Sten?")
					.define("cancel sten crafting?", true);
			craftThompson = builder.comment("Do you want to prevent players from crafting Thompson?")
					.define("cancel thompson crafting?", true);
			craftWinchester = builder.comment("Do you want to prevent players from crafting Winchester?")
					.define("cancel winchester crafting?", true);
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
	
}
