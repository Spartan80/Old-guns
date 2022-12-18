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
		public Server(ForgeConfigSpec.Builder builder) {
			
		}
	}
	
	public static class Client {
		
		public final ForgeConfigSpec.BooleanValue doDebugStuff;
		
		public Client(ForgeConfigSpec.Builder builder) {
			builder.push("Debug config");
			{
				doDebugStuff = builder.comment("if you want to make transforms to de gun parts or hands active this")
						.define("do debug stuff", false);
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
