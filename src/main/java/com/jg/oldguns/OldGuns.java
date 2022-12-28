package com.jg.oldguns;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jg.oldguns.client.handlers.ClientEventHandler;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.network.BodyHitMessage;
import com.jg.oldguns.network.ConsumeItemMessage;
import com.jg.oldguns.network.InitGunMessage;
import com.jg.oldguns.network.MagBulletPathMessage;
import com.jg.oldguns.network.MakeMagMessage;
import com.jg.oldguns.network.OpenGunGuiMessage;
import com.jg.oldguns.network.PlaySoundMessage;
import com.jg.oldguns.network.RestoreMagMessage;
import com.jg.oldguns.network.SetBulletsMessage;
import com.jg.oldguns.network.SetHammerMessage;
import com.jg.oldguns.network.ShootMessage;
import com.jg.oldguns.network.StartHitmarkerMessage;
import com.jg.oldguns.proxy.ClientProxy;
import com.jg.oldguns.proxy.IProxy;
import com.jg.oldguns.proxy.ServerProxy;
import com.jg.oldguns.registries.BlockRegistries;
import com.jg.oldguns.registries.ContainerRegistries;
import com.jg.oldguns.registries.EntityRegistries;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(OldGuns.MODID)
public class OldGuns {
	
	public static final String MODID = "oldguns";
	private static IProxy proxy;
	public static SimpleChannel channel;
    private static int packetsRegistered = 0;
    public static final Logger LOGGER = LogManager.getLogger();
	public static final String PROTOCOL_VERSION = "1";
	private static final CreativeModeTab tab = new CreativeModeTab("OldGuns") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(Items.GUNPOWDER);
		}
	};
	
	public OldGuns() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
	
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.server_config);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.client_config);
	
		// Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        OldGuns.proxy.registerModEventListeners(bus);
        
        //Register mod stuff (Items, Entities, Containers, etc.)
        SoundRegistries.SOUNDS.register(bus);
        ItemRegistries.ITEMS.register(bus);
        EntityRegistries.ENTITIES.register(bus);
        ContainerRegistries.CONTAINERS.register(bus);
        BlockRegistries.BLOCKS.register(bus);
        
        bus = MinecraftForge.EVENT_BUS;
        OldGuns.proxy.registerForgeEventListeners(bus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		OldGuns.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, "main"), 
        		() -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
		channel.registerMessage(packetsRegistered++, ShootMessage.class, 
				ShootMessage::encode, ShootMessage::decode, 
				ShootMessage::handle);
		channel.registerMessage(packetsRegistered++, PlaySoundMessage.class, 
				PlaySoundMessage::encode, PlaySoundMessage::decode, 
				PlaySoundMessage::handle);
		channel.registerMessage(packetsRegistered++, ConsumeItemMessage.class, 
				ConsumeItemMessage::encode, ConsumeItemMessage::decode, 
				ConsumeItemMessage::handle);
		channel.registerMessage(packetsRegistered++, AddItemMessage.class, 
				AddItemMessage::encode, AddItemMessage::decode, 
				AddItemMessage::handle);
		channel.registerMessage(packetsRegistered++, BodyHitMessage.class, 
				BodyHitMessage::encode, BodyHitMessage::decode, 
				BodyHitMessage::handle);
		
		channel.registerMessage(packetsRegistered++, MagBulletPathMessage.class, 
				MagBulletPathMessage::encode, MagBulletPathMessage::decode, 
				MagBulletPathMessage::handle);
		channel.registerMessage(packetsRegistered++, MakeMagMessage.class, 
				MakeMagMessage::encode, MakeMagMessage::decode, 
				MakeMagMessage::handle);
		channel.registerMessage(packetsRegistered++, RestoreMagMessage.class, 
				RestoreMagMessage::encode, RestoreMagMessage::decode, 
				RestoreMagMessage::handle);
		channel.registerMessage(packetsRegistered++, SetBulletsMessage.class, 
				SetBulletsMessage::encode, SetBulletsMessage::decode, 
				SetBulletsMessage::handle);
		channel.registerMessage(packetsRegistered++, SetHammerMessage.class, 
				SetHammerMessage::encode, SetHammerMessage::decode, 
				SetHammerMessage::handle);
		channel.registerMessage(packetsRegistered++, StartHitmarkerMessage.class, 
				StartHitmarkerMessage::encode, StartHitmarkerMessage::decode, 
				StartHitmarkerMessage::handle);
		
		channel.registerMessage(packetsRegistered++, OpenGunGuiMessage.class, 
				OpenGunGuiMessage::encode, OpenGunGuiMessage::decode, 
				OpenGunGuiMessage::handle);
		channel.registerMessage(packetsRegistered++, InitGunMessage.class, 
				InitGunMessage::encode, InitGunMessage::decode, 
				InitGunMessage::handle);
	}
	
	private void doClientStuff(final FMLClientSetupEvent event) {
		ClientEventHandler.setup();
	}
	
	public static CreativeModeTab getTab() {
		return tab;
	}
}
