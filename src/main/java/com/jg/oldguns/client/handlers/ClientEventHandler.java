package com.jg.oldguns.client.handlers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.lwjgl.glfw.GLFW;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.models.gunmodels.Aks74uGunModel;
import com.jg.oldguns.client.models.gunmodels.Colt1911GunModel;
import com.jg.oldguns.client.models.gunmodels.GalilGunModel;
import com.jg.oldguns.client.models.gunmodels.IthacaModel37GunModel;
import com.jg.oldguns.client.models.gunmodels.Kar98kGunModel;
import com.jg.oldguns.client.models.gunmodels.Mp40GunModel;
import com.jg.oldguns.client.models.gunmodels.ScorpionGunModel;
import com.jg.oldguns.client.models.gunmodels.StenGunModel;
import com.jg.oldguns.client.models.gunmodels.ThompsonGunModel;
import com.jg.oldguns.client.models.gunmodels.WinchesterGunModel;
import com.jg.oldguns.client.models.wrapper.ExtraItemWrapperModel;
import com.jg.oldguns.client.render.RenderHelper;
import com.jg.oldguns.client.screens.AmmoCraftingGui;
import com.jg.oldguns.client.screens.AnimationScreen;
import com.jg.oldguns.client.screens.GunAmmoGui;
import com.jg.oldguns.client.screens.GunCraftingGui2;
import com.jg.oldguns.client.screens.GunGui;
import com.jg.oldguns.client.screens.GunPartsCraftingGui;
import com.jg.oldguns.client.screens.GunPartsGui;
import com.jg.oldguns.client.screens.GunPartsScreen;
import com.jg.oldguns.client.screens.MagGui;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.events.RegisterEasingsEvent;
import com.jg.oldguns.events.RegisterGunModelEvent;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.MagItem;
import com.jg.oldguns.guns.items.Aks74u;
import com.jg.oldguns.network.InitGunMessage;
import com.jg.oldguns.network.OpenGunGuiMessage;
import com.jg.oldguns.registries.ContainerRegistries;
import com.jg.oldguns.registries.EntityRegistries;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.logging.LogUtils;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;

public class ClientEventHandler {

	private static Minecraft mc = Minecraft.getInstance();
	private static ClientHandler client = new ClientHandler();

	public static final KeyMapping SWITCH = new KeyMapping("key.oldguns.switch", GLFW.GLFW_KEY_C, OldGuns.MODID);
	public static final KeyMapping DISPLAY = new KeyMapping("key.oldguns.display", GLFW.GLFW_KEY_KP_ADD, OldGuns.MODID);
	public static final KeyMapping RELOAD = new KeyMapping("key.oldguns.reload", GLFW.GLFW_KEY_R, OldGuns.MODID);
	public static final KeyMapping LOOKANIM = new KeyMapping("key.oldguns.look", GLFW.GLFW_KEY_LEFT_ALT, OldGuns.MODID);
	public static final KeyMapping KICKBACK = new KeyMapping("key.oldguns.kickback", GLFW.GLFW_KEY_B, OldGuns.MODID);
	public static final KeyMapping ATTACHMENTS = new KeyMapping("key.oldguns.attachments", GLFW.GLFW_KEY_V,
			OldGuns.MODID);
	public static final KeyMapping GETOUTMAG = new KeyMapping("key.oldguns.getoutmag", GLFW.GLFW_KEY_U, OldGuns.MODID);

	public static final KeyMapping LEFT = new KeyMapping("key.oldguns.left", GLFW.GLFW_KEY_LEFT, OldGuns.MODID);
	public static final KeyMapping UP = new KeyMapping("key.oldguns.up", GLFW.GLFW_KEY_UP, OldGuns.MODID);
	public static final KeyMapping DOWN = new KeyMapping("key.oldguns.down", GLFW.GLFW_KEY_DOWN, OldGuns.MODID);
	public static final KeyMapping RIGHT = new KeyMapping("key.oldguns.right", GLFW.GLFW_KEY_RIGHT, OldGuns.MODID);
	public static final KeyMapping N = new KeyMapping("key.oldguns.n", GLFW.GLFW_KEY_N, OldGuns.MODID);
	public static final KeyMapping M = new KeyMapping("key.oldguns.m", GLFW.GLFW_KEY_M, OldGuns.MODID);
	public static final KeyMapping H = new KeyMapping("key.oldguns.z", GLFW.GLFW_KEY_H, OldGuns.MODID);
	public static final KeyMapping J = new KeyMapping("key.oldguns.x", GLFW.GLFW_KEY_J, OldGuns.MODID);
	public static final KeyMapping MINUS = new KeyMapping("key.oldguns.-", GLFW.GLFW_KEY_MINUS, OldGuns.MODID);

	private static boolean clicked = false;

	public static void setup() {
		ClientsHandler.register(mc.getUser(), client);

		MenuScreens.register(ContainerRegistries.GUN_AMMO_CONTAINER.get(), AmmoCraftingGui::new);
		MenuScreens.register(ContainerRegistries.GUN_CONTAINER.get(), GunGui::new);
		MenuScreens.register(ContainerRegistries.GUN_CRAFTING_CONTAINER.get(), GunCraftingGui2::new);
		MenuScreens.register(ContainerRegistries.GUN_PARTS_CONTAINER.get(), GunPartsCraftingGui::new);
		MenuScreens.register(ContainerRegistries.MAG_CONTAINER.get(), MagGui::new);

		EntityRenderers.register(EntityRegistries.BULLET.get(), ThrownItemRenderer::new);
		
		ClientRegistry.registerKeyBinding(RELOAD);
		ClientRegistry.registerKeyBinding(KICKBACK);
		ClientRegistry.registerKeyBinding(LOOKANIM);
		ClientRegistry.registerKeyBinding(ATTACHMENTS);
		ClientRegistry.registerKeyBinding(GETOUTMAG);
	}

	public static void registerModEventListeners(IEventBus bus) {
		bus.addListener(ClientEventHandler::registerSpecialModels);
		bus.addListener(ClientEventHandler::bakeModels);
	}

	public static void registerForgeEventListeners(IEventBus bus) {
		bus.addListener(ClientEventHandler::registerGunModels);
		bus.addListener(ClientEventHandler::renderFirstPersonStuff);
		bus.addListener(ClientEventHandler::registerEasings);
		bus.addListener(ClientEventHandler::clientTick);
		bus.addListener(ClientEventHandler::interruptInteractions);
		bus.addListener(ClientEventHandler::renderOverlayPre);
		bus.addListener(ClientEventHandler::renderOverlayPost);
		bus.addListener(ClientEventHandler::handleKeyboard);
		bus.addListener(ClientEventHandler::handleRawMouse);
	}

	// Models

	private static void registerSpecialModels(ModelRegistryEvent e) {
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.WINCHESTERBULLETLOADER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.MP40HAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.AKS74UHAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.COLT1911HAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.COLT1911SLIDER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.GALILHAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.ITHACAMODEL37HAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.KAR98KHAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.STENHAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.SCORPIONHAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.THOMPSONHAMMER, "inventory"));
	}

	private static void bakeModels(ModelBakeEvent e) {
		LogUtils.getLogger().info("Baking models");
		GunModelsHandler.setInit(false);
		if (!GunModelsHandler.getInit()) {
			GunModelsHandler.setInit(true);

			MinecraftForge.EVENT_BUS.start();
			MinecraftForge.EVENT_BUS.post(new RegisterGunModelEvent());
			MinecraftForge.EVENT_BUS.post(new RegisterEasingsEvent());
		}

		Map<ResourceLocation, BakedModel> models = e.getModelRegistry();

		for (String item : GunModelsHandler.getMap().keySet()) {
			GunModel gunModel = GunModelsHandler.get(item);
			gunModel.setModel(gunModel.getModifiedModel(models.get(Utils.getMR(gunModel.gun))));
			models.put(Utils.getMR(gunModel.gun), gunModel.getModel());
			LogUtils.getLogger().info("Registering " + item + " modelRes: " + Utils.getMR(gunModel.gun).toString());
		}
		for (Entry<String, ArrayList<Supplier<? extends Item>>> entry : ItemsReg.INSTANCE.getGunParts().entrySet()) {
			for (Supplier<? extends Item> sup : entry.getValue()) {
				ModelResourceLocation modelRes = Utils.getMR(sup.get());
				models.put(modelRes, new ExtraItemWrapperModel(models.get(modelRes)));
			}
		}
		for (Supplier<? extends Item> sup : ItemsReg.INSTANCE.getMags()) {
			ModelResourceLocation modelRes = Utils.getMR(sup.get());
			models.put(modelRes, new ExtraItemWrapperModel(models.get(modelRes)));
		}
	}

	// Custom events

	private static void registerGunModels(RegisterGunModelEvent e) {
		LogUtils.getLogger().info("Registering models");
		e.register(ItemRegistries.WINCHESTER.get(), new WinchesterGunModel(client));
		e.register(ItemRegistries.MP40.get(), new Mp40GunModel(client));
		e.register(ItemRegistries.AKS74U.get(), new Aks74uGunModel(client));
		e.register(ItemRegistries.COLT1911.get(), new Colt1911GunModel(client));
		e.register(ItemRegistries.GALIL.get(), new GalilGunModel(client));
		e.register(ItemRegistries.ITHACAMODEL37.get(), new IthacaModel37GunModel(client));
		e.register(ItemRegistries.KAR98K.get(), new Kar98kGunModel(client));
		e.register(ItemRegistries.STEN.get(), new StenGunModel(client));
		e.register(ItemRegistries.SCORPION.get(), new ScorpionGunModel(client));
		e.register(ItemRegistries.THOMPSON.get(), new ThompsonGunModel(client));
	}

	// Rendering

	private static void renderFirstPersonStuff(RenderHandEvent e) {
		Player player = mc.player;
		if (player != null) {
			ClientHandler.partialTicks = e.getPartialTicks();
			if (e.getHand() == InteractionHand.MAIN_HAND) {
				ItemStack stack = player.getMainHandItem();
				if (stack.getItem() instanceof GunItem) {
					e.setCanceled(true);
					MultiBufferSource.BufferSource impl = MultiBufferSource
							.immediate(Tesselator.getInstance().getBuilder());
					client.render(e.getPoseStack(), e.getMultiBufferSource(), impl, e.getPackedLight());
				}
			}
		}
	}

	// Rendering Overlay

	public static void renderOverlayPre(RenderGameOverlayEvent.PreLayer e) {
		Player player = mc.player;
		if (player == null)
			return;

		if (player.getMainHandItem().getItem() instanceof GunItem) {
			if (e.getOverlay() == ForgeIngameGui.CROSSHAIR_ELEMENT) {
				if (mc.options.getCameraType().isFirstPerson()) {
					e.setCanceled(true);
				}
			}
			client.renderHitmarker = false;
			if (client.renderHitmarker || client.debugAim || client.getHitmarker().hitmarkerTime > 0) {
				RenderHelper.drawHitmarker(e.getMatrixStack(), RenderHelper.HITMARKER, 8);
			}
		}
	}

	public static void renderOverlayPost(RenderGameOverlayEvent.PostLayer e) {
		Player player = mc.player;
		if (player == null)
			return;

		ItemStack stack = player.getMainHandItem();
		if (e.getOverlay() == ForgeIngameGui.HOTBAR_ELEMENT) {
			if (stack.getItem() instanceof GunItem) {
				GunItem gun = (GunItem)stack.getItem();
				int maxAmmo = gun.getMaxAmmo() == -1 ? NBTUtils.getMaxAmmo(stack) : 
					gun.getMaxAmmo();
				mc.font.draw(e.getMatrixStack(), NBTUtils.getAmmo(stack) + "/" + 
						maxAmmo,
						Minecraft.getInstance().getWindow().getGuiScaledWidth() / 7,
						Minecraft.getInstance().getWindow().getGuiScaledHeight() / 1.5f, 
						0xFFFFFF);
			} else if (stack.getItem() instanceof MagItem) {
				MagItem mag = ((MagItem) stack.getItem());
				mc.font.draw(e.getMatrixStack(), stack.getOrCreateTag().getInt(NBTUtils.BULLETS) 
						+ "/" + mag.getMaxAmmo(),
						Minecraft.getInstance().getWindow().getGuiScaledWidth() / 7,
						Minecraft.getInstance().getWindow().getGuiScaledHeight() / 1.5f, 0xFFFFFF);
			}
		}
	}

	private static void registerEasings(RegisterEasingsEvent e) {
		e.register("empty", (x) -> x);
		e.register("easeInSine", (x) -> (float) (1 - Math.cos((x * Math.PI) / 2)));
		e.register("easeOutSine", (x) -> (float) (Math.sin((x * Math.PI) / 2)));
		e.register("easeInOutSine", (x) -> (float) (-(Math.cos(Math.PI * x) - 1) / 2));

		e.register("easeInQuad", (x) -> (float) (x * x));
		e.register("easeOutQuad", (x) -> (float) (1 - (1 - x) * (1 - x)));
		e.register("easeInOutQuad", (x) -> (float) (x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2));

		e.register("easeInCubic", (x) -> (float) (x * x * x));
		e.register("easeOutCubic", (x) -> (float) (1 - Math.pow(1 - x, 3)));
		e.register("easeInOutCubic", (x) -> (float) (x < 0.5 ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2));

		e.register("easeInQuart", (x) -> (float) (x * x * x * x));
		e.register("easeOutQuart", (x) -> (float) (1 - Math.pow(1 - x, 4)));
		e.register("easeInOutQuart", (x) -> (float) (x < 0.5 ? 8 * x * x * x * x : 1 - Math.pow(-2 * x + 2, 4) / 2));

		e.register("easeInQuint", (x) -> (float) (x * x * x * x * x));
		e.register("easeOutQuint", (x) -> (float) (1 - Math.pow(1 - x, 5)));
		e.register("easeInOutQuint",
				(x) -> (float) (x < 0.5 ? 16 * x * x * x * x * x : 1 - Math.pow(-2 * x + 2, 5) / 2));

		e.register("easeInExpo", (x) -> (float) (x == 0 ? 0 : Math.pow(2, 10 * x - 10)));
		e.register("easeOutExpo", (x) -> (float) (x == 1 ? 1 : 1 - Math.pow(2, -10 * x)));
		e.register("easeInOutExpo", (x) -> (float) (x == 0 ? 0
				: x == 1 ? 1 : x < 0.5 ? Math.pow(2, 20 * x - 10) / 2 : (2 - Math.pow(2, -20 * x + 10)) / 2));

		e.register("easeInCirc", (x) -> (float) (1 - Math.sqrt(1 - Math.pow(x, 2))));
		e.register("easeOutCirc", (x) -> (float) (Math.sqrt(1 - Math.pow(x - 1, 2))));
		e.register("easeInOutCirc", (x) -> (float) (x < 0.5 ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2
				: (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2));

		e.register("easeInBack", (x) -> (float) (2.70158 * x * x * x - 1.70158f * x * x));
		e.register("easeOutBack", (x) -> (float) (1 + 2.70158 * Math.pow(x - 1, 3) + 1.70158 * Math.pow(x - 1, 2)));
		e.register("easeInOutBack",
				(x) -> (float) (x < 0.5 ? (Math.pow(2 * x, 2) * ((2.5949095f + 1) * 2 * x - 2.5949095f)) / 2
						: (Math.pow(2 * x - 2, 2) * ((2.5949095f + 1) * (x * 2 - 2) + 2.5949095f) + 2) / 2));

		e.register("easeInElastic", (x) -> (float) (x == 0 ? 0
				: x == 1 ? 1 : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * ((2 * Math.PI) / 3))));
		e.register("easeOutElastic", (x) -> (float) (x == 0 ? 0
				: x == 1 ? 1 : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75f) * ((2 * Math.PI) / 3)) + 1));
		e.register("easeInOutElastic", (x) -> (float) (x == 0 ? 0
				: x == 1 ? 1
						: x < 0.5
								? -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * ((2 * Math.PI) / 4.5))) / 2
								: (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * ((2 * Math.PI) / 4.5))) / 2
										+ 1));

		e.register("easeInBounce", (x) -> (float) (1 - e.getEasing("easeOutBounce").get(1 - x)));
		e.register("easeOutBounce", (x) -> {
			float n1 = 7.5625f;
			float d1 = 2.75f;

			if (x < 1 / d1) {
				return n1 * x * x;
			} else if (x < 2 / d1) {
				return (float) (n1 * (x -= 1.5 / d1) * x + 0.75);
			} else if (x < 2.5 / d1) {
				return (float) (n1 * (x -= 2.25 / d1) * x + 0.9375);
			} else {
				return (float) (n1 * (x -= 2.625 / d1) * x + 0.984375);
			}
		});
		e.register("easeInOutBounce", (x) -> (float) (x < 0.5 ? (1 - e.getEasing("easeOutBounce").get(1 - 2 * x)) / 2
				: (1 + e.getEasing("easeOutBounce").get(2 * x - 1)) / 2));
		LogUtils.getLogger().info("Registering easings");
	}

	private static void clientTick(ClientTickEvent e) {
		if (e.phase == Phase.START) {
			Player player = mc.player;
			if (player != null) {
				ItemStack stack = player.getMainHandItem();
				if (stack.getItem() instanceof GunItem) {

					if (stack.getOrCreateTag().getString(NBTUtils.ID) == "") {
						OldGuns.channel.sendToServer(new InitGunMessage(UUID.randomUUID()));
						System.out.println("Id set to: " + stack.getOrCreateTag().getString(NBTUtils.ID));
					}

					client.tick();
					client.selectGunModel();
					if (client.getGunModel() != null) {
						// Sprint handler
						client.getSprintHandler().tick(player.isSprinting());
						// Aim handler
						if (GLFW.glfwGetMouseButton(mc.getWindow().getWindow(),
								GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS && !player.isSprinting()
								&& mc.screen == null) {
							client.getAimHandler().tick(true);
						} else {
							client.getAimHandler().tick(false);
						}
						// Shoot handler
						if (GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(),
								GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
							// client.shoot(player);
							if (!player.isSprinting() && Minecraft.getInstance().screen == null
									&& ((GunItem) stack.getItem()).getFireMode() == FireMode.AUTO) {
								if (client.getGunModel().getAnimation() == null &&
								// !client.getCooldown().hasCooldown(NBTUtils.getId(stack))) {
										!client.getCooldownRecoil().isOnCooldown(player.getMainHandItem().getItem())) {
									client.shoot(player);
								}
							}
						}
					}
				}
			}
		}
	}

	// Player

	public static void interruptInteractions(PlayerInteractEvent.LeftClickBlock e) {
		Player player = e.getPlayer();
		if (player != null) {
			if (player.getMainHandItem().getItem() instanceof GunItem) {
				e.setCanceled(true);
			}
		}
	}

	// Input

	private static void handleKeyboard(InputEvent.KeyInputEvent e) {
		Screen screen = mc.screen;
		if (screen == null || screen instanceof AnimationScreen || screen instanceof GunPartsScreen) {
			boolean animEditFocused = false;
			if (screen instanceof AnimationScreen) {
				AnimationScreen animScreen = (AnimationScreen) screen;
				for (EditBox edit : animScreen.getEditBoxes()) {
					if (edit.isFocused()) {
						animEditFocused = true;
						break;
					}
				}
			}
			if (animEditFocused)
				return;
			// if(e.getAction() == GLFW.GLFW_PRESS) {
			Player player = mc.player;

			if (player != null) {
				ItemStack stack = player.getMainHandItem();
				if (stack.getItem() instanceof GunItem) {
					
					if (client.getGunModel() == null)
						return;
					
					if (e.getAction() == GLFW.GLFW_PRESS) {
						if (RELOAD.getKey().getValue() == e.getKey() 
								&& !player.isSprinting() && client.getGunModel().canReload()) {
							// OldGuns.channel.sendToServer(new LoadBulletMessage(true));
							client.getGunModel().reload(player, stack);
						} else if (LOOKANIM.getKey().getValue() == e.getKey() 
								&& !player.isSprinting()) {
							client.getGunModel().setAnimation(client.getGunModel().getLookAnimation());
							LogUtils.getLogger().info("Setting look animation");
						} else if (KICKBACK.getKey().getValue() == e.getKey() 
								&& !player.isSprinting()) {
							client.getGunModel().setAnimation(client.getGunModel().getKickbackAnimation());
						} else if (e.getKey() == 93 && Config.CLIENT.doDebugStuff.get()) {
							MinecraftForge.EVENT_BUS.start();
							MinecraftForge.EVENT_BUS.post(new RegisterGunModelEvent());
							MinecraftForge.EVENT_BUS.post(new RegisterEasingsEvent());
						} else if (ATTACHMENTS.getKey().getValue() == e.getKey()) {
							OldGuns.channel.sendToServer(new OpenGunGuiMessage());
						} else if (e.getKey() == 75) {
							Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(NBTUtils.getBody(stack)));
							LogUtils.getLogger()
									.info("Body string: " + NBTUtils.getBody(stack) + " item == null: " + (item == null)
											+ "" + ((item == null) ? "" : " item reg: " + Utils.getR(item)) + " "
											+ ((item instanceof GunPart)
													? ((GunPart) item).getGunPartProperties().getValidSize()
													: "Nothing"));
							if (!NBTUtils.hasMag(stack)) {
								String size = item == null ? BulletItem.MEDIUM
										: ((GunPart) item).getGunPartProperties().getValidSize();
								LogUtils.getLogger().info("Size: " + size);
								int index = ServerUtils.getIndexForCorrectMag(player.getInventory(),
										client.getGunModel().gun.getGunId(), size);
								LogUtils.getLogger().info("index: " + index);
								if (index != -1) {
									ItemStack mag = mc.player.getInventory().getItem(index);
									// int magBullets = NBTUtils.getAmmo(mag);
									ReloadHandler.setMag(client.getGunModel(), NBTUtils.getMaxAmmo(mag), true,
											NBTUtils.getMagBullet(mag), (MagItem) mag.getItem());
								}
							} else {
								ReloadHandler.setMag(client.getGunModel(), 0, false, "", (MagItem) null);
								LogUtils.getLogger().info("Setting mag to false");
							}
						} else if (e.getKey() == GETOUTMAG.getKey().getValue()) {
							if (client.getGunModel().getGetOutMagAnimation() != null) {
								if(NBTUtils.hasMag(stack)) {
									client.getGunModel().setAnimation(client.getGunModel().getGetOutMagAnimation());
								}
							}
						}
						if (Config.CLIENT.doDebugStuff.get()) {
							if (H.getKey().getValue() == e.getKey()) {
								client.right();
							} else if (J.getKey().getValue() == e.getKey()) {
								client.left();
							}
							if (47 == e.getKey() && mc.screen == null) {
								mc.setScreen(new GunPartsScreen(client.getGunModel()).setAnimScreen());
							} else if (SWITCH.getKey().getValue() == e.getKey()) {
								client.switchRotationMode();
							} else if (DISPLAY.getKey().getValue() == e.getKey()) {
								client.display = !client.display;
								LogUtils.getLogger().info("Display: " + client.display);
							} else if (e.getKey() == 89) {
								client.getGunModel().setShouldUpdateAnimation(true);
							}
						}
					}
					if (Config.CLIENT.doDebugStuff.get()) {
						if (LEFT.getKey().getValue() == e.getKey()) {
							client.dec(0);
						} else if (RIGHT.getKey().getValue() == e.getKey()) {
							client.inc(0);
						} else if (UP.getKey().getValue() == e.getKey()) {
							client.inc(1);
						} else if (DOWN.getKey().getValue() == e.getKey()) {
							client.dec(1);
						} else if (M.getKey().getValue() == e.getKey()) {
							client.inc(2);
						} else if (N.getKey().getValue() == e.getKey()) {
							client.dec(2);
						}
					}
				} else if(stack.getItem() instanceof MagItem) {
					if (RELOAD.getKey().getValue() == e.getKey() 
							&& !player.isSprinting()) {
						ReloadHandler.findCorrectBullet(player.getInventory(), stack);
					}	
				}
			}
		}
	}

	private static void handleRawMouse(InputEvent.RawMouseEvent e) {
		if (mc.screen == null) {
			Player player = mc.player;
			if (player != null) {
				if (player.getMainHandItem().getItem() instanceof GunItem) {
					if (((GunItem) player.getMainHandItem().getItem()).getFireMode() != FireMode.SEMI)
						return;
					if (e.getAction() == GLFW.GLFW_PRESS && !clicked) {
						clicked = true;
						if (e.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
							if (client.getGunModel() != null) {
								if (!client.getCooldownRecoil().isOnCooldown(player.getMainHandItem().getItem())
										&& client.getGunModel().getAnimation() == null) {// client.getRecoilHandler().getProgress()
																							// == 0) {
									client.shoot(player);
								}
							}
						}
					} else if (e.getAction() == GLFW.GLFW_RELEASE) {
						clicked = false;
					}
				}
			}
		}
	}

}
