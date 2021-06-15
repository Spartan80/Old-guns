package com.redwyvern.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.redwyvern.callbacks.KeyboardCallback;
import com.redwyvern.config.Config;
import com.redwyvern.network.BulletsMessage;
import com.redwyvern.network.CleanGunSlotMessage;
import com.redwyvern.network.ConsumeStackMessage;
import com.redwyvern.network.GunCraftMessage;
import com.redwyvern.network.HandleShootMessage;
import com.redwyvern.network.MoldingCraftMessage;
import com.redwyvern.network.MoldingPartsDataMessage;
import com.redwyvern.network.PlaySoundMessage;
import com.redwyvern.network.PlaySoundOnServerMessage;
import com.redwyvern.network.ShootAnimMessage;
import com.redwyvern.network.ShootticksMessage;
import com.redwyvern.network.UpdateGunShowing;
import com.redwyvern.network.setScopeMessage;
import com.redwyvern.proxy.ClientProxy;
import com.redwyvern.proxy.IProxy;
import com.redwyvern.proxy.ServerProxy;
import com.redwyvern.registries.BlockRegistries;
import com.redwyvern.registries.ContainerRegistries;
import com.redwyvern.registries.EnchantmentRegistries;
import com.redwyvern.registries.EntityRegistries;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.registries.LootFunctionRegistries;
import com.redwyvern.registries.SoundRegistries;
import com.redwyvern.screen.GunCraftingScreen;
import com.redwyvern.screen.MoldingPartsGUI;
import com.redwyvern.screen.SmeltingPartsGui;
import com.redwyvern.util.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod("oldguns")
@Mod.EventBusSubscriber(modid = OldGuns.MODID)
public class OldGuns {
	public static final Logger logger = LogManager.getLogger();
	public static final String MODID = "oldguns";
	public static final String PROTOCOL_VERSION = "1";
	public static IProxy proxy;
	public static SimpleChannel channel;
	public static ModTab modtab;
	private static int packetsRegistered = 0;

	public OldGuns() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
		modtab = new ModTab();

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.server_config);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.client_config);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		Config.loadConfig(Config.client_config, FMLPaths.CONFIGDIR.get().resolve("oldguns-client.toml").toString());
		Config.loadConfig(Config.server_config, FMLPaths.CONFIGDIR.get().resolve("oldguns-server.toml").toString());

		OldGuns.proxy.registerModEventListeners(bus);

		LootFunctionRegistries.init();
		EntityRegistries.ENTITIES.register(bus);
		EntityRegistries.TILE_ENTITIES.register(bus);
		ContainerRegistries.CONTAINERS.register(bus);
		BlockRegistries.BLOCKS.register(bus);
		ItemRegistries.ITEMS.register(bus);
		EnchantmentRegistries.REGISTER.register(bus);
		SoundRegistries.REGISTER.register(bus);

		bus = MinecraftForge.EVENT_BUS;
		OldGuns.proxy.registerForgeEventListeners(bus);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {

		new KeyboardCallback().setup(Minecraft.getInstance());

		OldGuns.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(OldGuns.MODID, "main"),
				() -> OldGuns.PROTOCOL_VERSION, OldGuns.PROTOCOL_VERSION::equals, OldGuns.PROTOCOL_VERSION::equals);

		channel.registerMessage(packetsRegistered++, GunCraftMessage.class, GunCraftMessage::encode,
				GunCraftMessage::decode, GunCraftMessage::handle);
		channel.registerMessage(packetsRegistered++, UpdateGunShowing.class, UpdateGunShowing::encode,
				UpdateGunShowing::decode, UpdateGunShowing::handle);
		channel.registerMessage(packetsRegistered++, MoldingCraftMessage.class, MoldingCraftMessage::encode,
				MoldingCraftMessage::decode, MoldingCraftMessage::handle);
		channel.registerMessage(packetsRegistered++, ConsumeStackMessage.class, ConsumeStackMessage::encode,
				ConsumeStackMessage::decode, ConsumeStackMessage::handle);
		channel.registerMessage(packetsRegistered++, BulletsMessage.class, BulletsMessage::encode,
				BulletsMessage::decode, BulletsMessage::handle);
		channel.registerMessage(packetsRegistered++, ShootticksMessage.class, ShootticksMessage::encode,
				ShootticksMessage::decode, ShootticksMessage::handle);
		channel.registerMessage(packetsRegistered++, CleanGunSlotMessage.class, CleanGunSlotMessage::encode,
				CleanGunSlotMessage::decode, CleanGunSlotMessage::handle);
		channel.registerMessage(packetsRegistered++, MoldingPartsDataMessage.class, MoldingPartsDataMessage::encode,
				MoldingPartsDataMessage::decode, MoldingPartsDataMessage::handle);
		channel.registerMessage(packetsRegistered++, setScopeMessage.class, setScopeMessage::encode,
				setScopeMessage::decode, setScopeMessage::handle);
		channel.registerMessage(packetsRegistered++, PlaySoundMessage.class, PlaySoundMessage::encode,
				PlaySoundMessage::decode, PlaySoundMessage::handle);
		channel.registerMessage(packetsRegistered++, PlaySoundOnServerMessage.class, PlaySoundOnServerMessage::encode,
				PlaySoundOnServerMessage::decode, PlaySoundOnServerMessage::handle);
		channel.registerMessage(packetsRegistered++, ShootAnimMessage.class, ShootAnimMessage::encode,
				ShootAnimMessage::decode, ShootAnimMessage::handle);
		channel.registerMessage(packetsRegistered++, HandleShootMessage.class, HandleShootMessage::encode,
				HandleShootMessage::decode, HandleShootMessage::handle);

		OldGuns.proxy.init();
	}

	@SuppressWarnings("deprecation")
	private void doClientStuff(final FMLClientSetupEvent event) {

		ScreenManager.register(ContainerRegistries.gun_crafting_container.get(), GunCraftingScreen::new);
		ScreenManager.register(ContainerRegistries.smelting_container.get(), SmeltingPartsGui::new);
		ScreenManager.register(ContainerRegistries.molding_container.get(), MoldingPartsGUI::new);

		ClientRegistry.registerKeyBinding(ClientProxy.RELOAD);
		ClientRegistry.registerKeyBinding(ClientProxy.ATT);

		Util.init();
	}

}
