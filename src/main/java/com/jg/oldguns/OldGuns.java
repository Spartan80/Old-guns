package com.jg.oldguns;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.network.AddItemMessage;
import com.jg.oldguns.network.HandleShootMessage;
import com.jg.oldguns.network.InitGunMessage;
import com.jg.oldguns.network.InitGunPartPropertiesMessage;
import com.jg.oldguns.network.InitMessage;
import com.jg.oldguns.network.MagBulletPathMessage;
import com.jg.oldguns.network.MakeMagMessage;
import com.jg.oldguns.network.OpenGunGuiMessage;
import com.jg.oldguns.network.PlaySoundMessage;
import com.jg.oldguns.network.RemoveItemMessage;
import com.jg.oldguns.network.RestoreMagMessage;
import com.jg.oldguns.network.SetAttachmentMessage;
import com.jg.oldguns.network.SetAttachmentsMessage;
import com.jg.oldguns.network.SetBulletsMessage;
import com.jg.oldguns.network.SetHammerMessage;
import com.jg.oldguns.network.SetMagTypeOnContainerMessage;
import com.jg.oldguns.network.SetRoomBulletMessage;
import com.jg.oldguns.network.SetScopeMessage;
import com.jg.oldguns.network.StartHitmarkerMessage;
import com.jg.oldguns.proxy.ClientProxy;
import com.jg.oldguns.proxy.IProxy;
import com.jg.oldguns.proxy.ServerProxy;
import com.jg.oldguns.registries.BlockRegistries;
import com.jg.oldguns.registries.ContainerRegistries;
import com.jg.oldguns.registries.EntityRegistries;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(OldGuns.MODID)
@Mod.EventBusSubscriber(modid = OldGuns.MODID)
public class OldGuns {
	public static final String MODID = "oldguns";
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static IProxy proxy;
	public static SimpleChannel channel;
	private static int packetsRegistered = 0;
	public static final String PROTOCOL_VERSION = "1";
	public static final ItemGroup tab = new ItemGroup("OldGuns") {

		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ItemRegistries.Kar98k.get());
		}
	};

	public OldGuns() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.server_config);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.client_config);
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the doClientStuff method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		OldGuns.proxy.registerModEventListeners(bus);

		EntityRegistries.ENTITIES.register(bus);
		ItemRegistries.ITEMS.register(bus);
		BlockRegistries.BLOCKS.register(bus);
		ContainerRegistries.CONTAINERS.register(bus);
		SoundRegistries.SOUNDS.register(bus);

		bus = MinecraftForge.EVENT_BUS;
		OldGuns.proxy.registerForgeEventListeners(bus);
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		OldGuns.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(OldGuns.MODID, "main"),
				() -> OldGuns.PROTOCOL_VERSION, OldGuns.PROTOCOL_VERSION::equals, OldGuns.PROTOCOL_VERSION::equals);

		channel.registerMessage(packetsRegistered++, HandleShootMessage.class, HandleShootMessage::encode,
				HandleShootMessage::decode, HandleShootMessage::handle);
		channel.registerMessage(packetsRegistered++, MakeMagMessage.class, MakeMagMessage::encode,
				MakeMagMessage::decode, MakeMagMessage::handle);
		channel.registerMessage(packetsRegistered++, PlaySoundMessage.class, PlaySoundMessage::encode,
				PlaySoundMessage::decode, PlaySoundMessage::handle);
		channel.registerMessage(packetsRegistered++, SetBulletsMessage.class, SetBulletsMessage::encode,
				SetBulletsMessage::decode, SetBulletsMessage::handle);
		channel.registerMessage(packetsRegistered++, SetMagTypeOnContainerMessage.class,
				SetMagTypeOnContainerMessage::encode, SetMagTypeOnContainerMessage::decode,
				SetMagTypeOnContainerMessage::handle);
		channel.registerMessage(packetsRegistered++, SetAttachmentMessage.class, SetAttachmentMessage::encode,
				SetAttachmentMessage::decode, SetAttachmentMessage::handle);
		channel.registerMessage(packetsRegistered++, SetAttachmentsMessage.class, SetAttachmentsMessage::encode,
				SetAttachmentsMessage::decode, SetAttachmentsMessage::handle);
		channel.registerMessage(packetsRegistered++, SetScopeMessage.class, SetScopeMessage::encode,
				SetScopeMessage::decode, SetScopeMessage::handle);
		channel.registerMessage(packetsRegistered++, OpenGunGuiMessage.class, OpenGunGuiMessage::encode,
				OpenGunGuiMessage::decode, OpenGunGuiMessage::handle);
		channel.registerMessage(packetsRegistered++, RemoveItemMessage.class, RemoveItemMessage::encode,
				RemoveItemMessage::decode, RemoveItemMessage::handle);
		channel.registerMessage(packetsRegistered++, AddItemMessage.class, AddItemMessage::encode,
				AddItemMessage::decode, AddItemMessage::handle);
		channel.registerMessage(packetsRegistered++, RestoreMagMessage.class, RestoreMagMessage::encode,
				RestoreMagMessage::decode, RestoreMagMessage::handle);
		channel.registerMessage(packetsRegistered++, MagBulletPathMessage.class, MagBulletPathMessage::encode,
				MagBulletPathMessage::decode, MagBulletPathMessage::handle);
		channel.registerMessage(packetsRegistered++, StartHitmarkerMessage.class, StartHitmarkerMessage::encode,
				StartHitmarkerMessage::decode, StartHitmarkerMessage::handle);
		channel.registerMessage(packetsRegistered++, SetRoomBulletMessage.class, SetRoomBulletMessage::encode,
				SetRoomBulletMessage::decode, SetRoomBulletMessage::handle);
		channel.registerMessage(packetsRegistered++, SetHammerMessage.class, SetHammerMessage::encode,
				SetHammerMessage::decode, SetHammerMessage::handle);
		channel.registerMessage(packetsRegistered++, InitGunMessage.class, InitGunMessage::encode, InitGunMessage::decode,
				InitGunMessage::handle);
		channel.registerMessage(packetsRegistered++, InitMessage.class, InitMessage::encode, InitMessage::decode,
				InitMessage::handle);
		channel.registerMessage(packetsRegistered++, InitGunPartPropertiesMessage.class, InitGunPartPropertiesMessage::encode, InitGunPartPropertiesMessage::decode,
				InitGunPartPropertiesMessage::handle);
		System.out.println("Common sssss");
		System.out.println("Common sssss2");
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		ClientEventHandler.setup();
		
	}
	
	@SubscribeEvent
	public static void serverStart(FMLServerStartedEvent e) {
		//GunPartPropertiesRegistryEvent partRegEvent = new GunPartPropertiesRegistryEvent();
		//MinecraftForge.EVENT_BUS.post(partRegEvent);
	}
	
}
