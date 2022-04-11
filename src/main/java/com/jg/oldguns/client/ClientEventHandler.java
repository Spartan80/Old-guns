package com.jg.oldguns.client;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.animations.Animator;
import com.jg.oldguns.animations.Transform;
import com.jg.oldguns.client.handlers.AimHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.modmodels.GalilModModel;
import com.jg.oldguns.client.render.BulletRender;
import com.jg.oldguns.client.render.types.RenderTypes;
import com.jg.oldguns.client.screens.GunAmmoGui;
import com.jg.oldguns.client.screens.GunCraftingGui;
import com.jg.oldguns.client.screens.GunGui;
import com.jg.oldguns.client.screens.GunPartsGui;
import com.jg.oldguns.client.screens.MagGui;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.debug.AnimWriter;
import com.jg.oldguns.events.GunModelRegistryEvent;
import com.jg.oldguns.gunmodels.Ak74uGunModel;
import com.jg.oldguns.gunmodels.AsValGunModel;
import com.jg.oldguns.gunmodels.ColtGunModel;
import com.jg.oldguns.gunmodels.GalilGunModel;
import com.jg.oldguns.gunmodels.Kar98kGunModel;
import com.jg.oldguns.gunmodels.LugerP08GunModel;
import com.jg.oldguns.gunmodels.Model37GunModel;
import com.jg.oldguns.gunmodels.Mp40GunModel;
import com.jg.oldguns.gunmodels.Mp5GunModel;
import com.jg.oldguns.gunmodels.PiratePistolGunModel;
import com.jg.oldguns.gunmodels.PirateRifleGunModel;
import com.jg.oldguns.gunmodels.RevolverGunModel;
import com.jg.oldguns.gunmodels.StenGunModel;
import com.jg.oldguns.gunmodels.ThompsonGunModel;
import com.jg.oldguns.gunmodels.WinchesterGunModel;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.ItemGun.ShootMode;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.network.InitGunMessage;
import com.jg.oldguns.network.InitMessage;
import com.jg.oldguns.network.OpenGunGuiMessage;
import com.jg.oldguns.registries.ContainerRegistries;
import com.jg.oldguns.registries.EntityRegistries;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = OldGuns.MODID, value = Dist.CLIENT)
public class ClientEventHandler {

	public static Map<UUID, ClientHandler> handlers = new HashMap<UUID, ClientHandler>();
	public static float stepR = (float) Math.toRadians(1);
	public static float stepT = 0.01f;

	// Keybinds

	public static final KeyMapping RELOAD = new KeyMapping("key.oldguns.reload", GLFW.GLFW_KEY_R, OldGuns.MODID);
	public static final KeyMapping INSPECTANIM = new KeyMapping("key.oldguns.inspectanim", GLFW.GLFW_KEY_LEFT_ALT, OldGuns.MODID);
	public static final KeyMapping KICKBACK = new KeyMapping("key.oldguns.kickback", GLFW.GLFW_KEY_B, OldGuns.MODID);
	public static final KeyMapping ATTACHMENTS = new KeyMapping("key.oldguns.attachments", GLFW.GLFW_KEY_V,
			OldGuns.MODID);

	public static boolean init = false;

	public static void setup() {

		// Register Screens

		MenuScreens.register(ContainerRegistries.GUN_CONTAINER.get(), GunGui::new);
		MenuScreens.register(ContainerRegistries.MAG_CONTAINER.get(), MagGui::new);
		MenuScreens.register(ContainerRegistries.GUN_PARTS_CONTAINER.get(), GunPartsGui::new);
		MenuScreens.register(ContainerRegistries.GUN_CRAFTING_CONTAINER.get(), GunCraftingGui::new);
		MenuScreens.register(ContainerRegistries.GUN_AMMO_CONTAINER.get(), GunAmmoGui::new);

		// Register Entity Renders

		EntityRenderers.register(EntityRegistries.BULLET.get(),
				manager -> new BulletRender(manager));

		// Register KeyBindings

		ClientRegistry.registerKeyBinding(RELOAD);
		ClientRegistry.registerKeyBinding(KICKBACK);
		ClientRegistry.registerKeyBinding(INSPECTANIM);
		ClientRegistry.registerKeyBinding(ATTACHMENTS);
	
	}

	public static void registerModEventListeners(IEventBus bus) {
		bus.addListener(ClientEventHandler::bakeEvent);
		bus.addListener(ClientEventHandler::modelRegistry);
	}

	public static void registerForgeEventListeners(IEventBus bus) {
		bus.addListener(ClientEventHandler::renderHand);
		bus.addListener(ClientEventHandler::renderOverlayPre);
		bus.addListener(ClientEventHandler::renderOverlayPost);
		bus.addListener(ClientEventHandler::tickClient);
		bus.addListener(ClientEventHandler::handleKeyboard);
		bus.addListener(ClientEventHandler::handleMouse);
		bus.addListener(ClientEventHandler::scrollEvent);
		bus.addListener(ClientEventHandler::onChatMessage);
		bus.addListener(ClientEventHandler::fovModifier);
		bus.addListener(ClientEventHandler::registerGunModels);
		
	}

	public static void registerGunModels(GunModelRegistryEvent e) {
		e.registerGunModel(ItemRegistries.Kar98k.get(), new Kar98kGunModel());
		e.registerGunModel(ItemRegistries.PiratePistol.get(), new PiratePistolGunModel());
		e.registerGunModel(ItemRegistries.Colt1911.get(), new ColtGunModel());
		e.registerGunModel(ItemRegistries.Thompson.get(), new ThompsonGunModel());
		e.registerGunModel(ItemRegistries.PirateRifle.get(), new PirateRifleGunModel());
		e.registerGunModel(ItemRegistries.Winchester.get(), new WinchesterGunModel());
		e.registerGunModel(ItemRegistries.LugerP08.get(), new LugerP08GunModel());
		e.registerGunModel(ItemRegistries.Mp40.get(), new Mp40GunModel());
		e.registerGunModel(ItemRegistries.Revolver.get(), new RevolverGunModel());
		e.registerGunModel(ItemRegistries.Model37.get(), new Model37GunModel());
		e.registerGunModel(ItemRegistries.Sten.get(), new StenGunModel());
		e.registerGunModel(ItemRegistries.Ak74u.get(), new Ak74uGunModel());
		e.registerGunModel(ItemRegistries.AsVal.get(), new AsValGunModel());
		e.registerGunModel(ItemRegistries.Galil.get(), new GalilGunModel());
		e.registerGunModel(ItemRegistries.Mp5.get(), new Mp5GunModel());
	}
	
	public static void modelRegistry(ModelRegistryEvent e) {
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.KARKHAM, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.PPHAM, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.COLTHAM, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.COLTSLIDER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.THOMPSONH, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.PRH, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.WINCHESTERHAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.MP40H, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.LUGERP08HB, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.LUGERP08HF, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.REVOLVERHAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.MODEL37LOADER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.STENHAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.AK74UHAMMER, "inventory"));
		ForgeModelBakery.addSpecialModel(new ModelResourceLocation(Paths.ASVALHAMMER, "inventory"));
	}

	public static void bakeEvent(ModelBakeEvent e) {
		
		if (!ModelHandler.INSTANCE.init) {
			ModelHandler.INSTANCE.setInit();
			
			MinecraftForge.EVENT_BUS.start();
			GunModelRegistryEvent re = new GunModelRegistryEvent();
			MinecraftForge.EVENT_BUS.post(re);
		}
			Map<ResourceLocation, BakedModel> models = e.getModelRegistry();

			for (Item item : ModelHandler.INSTANCE.gunmodels.keySet()) {
				GunModel gunModel = ModelHandler.INSTANCE.getGunModel(item);
				gunModel.setModModel(gunModel.getModifiedModel(ModelHandler.INSTANCE.getModel(item.getRegistryName().toString())));
				models.put(new ModelResourceLocation(item.getRegistryName().toString(), "inventory"), gunModel.getModModel());
			}

	}

	public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
		System.out.println("Player logged, player is null? " + e.getPlayer() == null);
		if (!init) {
			OldGuns.channel.sendTo(new InitMessage(), ((ServerPlayer) e.getEntity()).connection.connection,
					NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	// Models end

	// Fov

	public static void fovModifier(EntityViewRenderEvent.FieldOfView e) {
		Player player = getPlayer();
		if (Util.isAvailable(player)) {
			if (Util.canWork(player)) {
				if (!init)
					return;
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler != null) {
					if (handler.getAimingHandler().isAiming()) {
						ItemGun gun = (ItemGun) player.getMainHandItem().getItem();
						float currentFov = (float) Minecraft.getInstance().options.fov;
						int scopePercent = gun.getNBTScopePercent(player.getMainHandItem());
						if(scopePercent != 0) {
							e.setFOV(Mth.lerp(handler.getAimingHandler().getProgress(),
									currentFov,
									currentFov-(currentFov*scopePercent*0.01f)));
						}else {
							e.setFOV(Mth.lerp(handler.getAimingHandler().getProgress(),
									currentFov,
									currentFov-(currentFov*15*0.01f)));
						}
					}
				}
			}
		}
	}

	// Rendering

	// Rendering gun and all parts

	public static void renderHand(RenderHandEvent e) {
		Player player = Minecraft.getInstance().player;
		if (player != null) {
			ItemStack stack = player.getMainHandItem();

			if (Util.canWork(stack)) {
				stepT = 0.01f;
				stepR = (float)Math.toRadians(1f);
				e.setCanceled(true);
				
				AimHandler.renderPartialTicks = e.getPartialTicks();
				BufferSource impl = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

				if (!init)
					return;
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler != null) {
					//0.12f, -0.3f, -1.63f
					//handler.getAimingHandler().test(e.getPartialTicks());
					//handler.renderMuzzleFlash(e.getMatrixStack(), impl, e.getPartialTicks(), stack);
					handler.render(e.getPoseStack(), stack, e.getMultiBufferSource(), impl, e.getPartialTicks(), e.getPackedLight(),
							OverlayTexture.NO_OVERLAY);
				}

			}
		}
	}

	// Rendering Overlay

	public static void renderOverlayPre(RenderGameOverlayEvent.PreLayer e) {
		if (Util.canWork(getPlayer())) {
			if (e.getOverlay() == ForgeIngameGui.CROSSHAIR_ELEMENT) {
				ItemStack stack = getPlayer().getMainHandItem();
				if (!((ItemGun) stack.getItem()).shouldRenderCrosshair()) {
					e.setCanceled(true);
				}
				if (!init)
					return;
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (getPlayer().getMainHandItem().getItem() instanceof ItemGun && handler != null) {
					if (handler.canRenderHitmarker() || handler.debugAim()) {
						RenderUtil.drawHitmarker(e.getMatrixStack(), RenderUtil.HITMARKER, 8);
					}
				}
			}

		}
	}

	public static void renderOverlayPost(RenderGameOverlayEvent.Post e) {
		ItemStack stack = Util.getStack();

		if (e.getType() == ElementType.TEXT) {
			if (stack.getItem() instanceof ItemGun) {
				ItemGun gun = ((ItemGun) stack.getItem());
				Minecraft.getInstance().font.draw(e.getMatrixStack(),
						gun.getNBTBullets(stack) + "/" + gun.getMaxAmmo(stack),
						Minecraft.getInstance().getWindow().getGuiScaledWidth() / 7,
						Minecraft.getInstance().getWindow().getGuiScaledHeight() / 1.5f, 0xFFFFFF);
			} else if (stack.getItem() instanceof ItemMag) {
				ItemMag mag = ((ItemMag) stack.getItem());
				Minecraft.getInstance().font.draw(e.getMatrixStack(), mag.getNBTBullets(stack) + "/" + mag.getMaxAmmo(),
						Minecraft.getInstance().getWindow().getGuiScaledWidth() / 7,
						Minecraft.getInstance().getWindow().getGuiScaledHeight() / 1.5f, 0xFFFFFF);
			}
		} else if (e.getType() == ElementType.ALL) {
			if (Util.canWork(stack)) {
				ItemGun gun = (ItemGun) stack.getItem();
				if (!init)
					return;
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				if (ServerUtils.hasScope(stack) && handler.getAimingHandler().isFullAimingProgress()) {
					
					if (gun.getScopeResLoc() != null && gun.getNullPartsResLoc() != null) {
						RenderUtil.renderScopeImage(e.getMatrixStack(), gun.getScopeResLoc(), gun.getNullPartsResLoc(),
								16, 8);
					}
				}
			}
		}
	}

	// Rendering end

	//

	// Chat events

	public static void onChatMessage(ClientChatEvent e) {

		if (Config.CLIENT.doDebugStuff.get()) {
			String msg = e.getMessage();
			
			if(msg.contains("#help")) {
				Minecraft mc = Minecraft.getInstance();
				mc.player.sendMessage(new TranslatableComponent("§2save §c- §fSaves animations on a file with the given name - Usage save,<filename.txt>"), null);
				mc.player.sendMessage(new TranslatableComponent("§2print §c- §fPrints the current animations - Usage print"), null);
				mc.player.sendMessage(new TranslatableComponent("§2sk §c- §fStarts a keyframe with specified duration - Usage sk,<duration>"), null);
				mc.player.sendMessage(new TranslatableComponent("§2t §c- §fAdds the translation of the current part to the current animation - Usage t"), null);
				mc.player.sendMessage(new TranslatableComponent("§2r §c- §fAdds the rotation of the current part to the current animation - Usage r"), null);
				mc.player.sendMessage(new TranslatableComponent("§2tr §c- §fAdds the translation and rotation of the current part to the current animation - Usage tr"), null);
				mc.player.sendMessage(new TranslatableComponent("§2all §c- §fAdds the translation and rotation of all modified parts to the current animation - Usage all"), null);
				mc.player.sendMessage(new TranslatableComponent("§2pp §c- §fPrints the current transform of the part oriented to positioning the gun part - Usage pp"), null);
			}else if (msg.contains("save")) {
				String[] characters = e.getMessage().split(",");

				try {
					String name = characters[1];
					AnimWriter.save(name);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Saving anim file"), null);
			} else if (msg.startsWith("print")) {
				if(msg.contains(",")) {
					String[] msgs = msg.split(",");
					if(msgs[1].equals("0")) {
						System.out.println(AnimWriter.getAnimation());
					}else if(msgs[1].equals("1")){
						System.out.println(AnimWriter.getAanimation());
					}
					Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Printing " + msgs[1]), null);
				}
			} else if (msg.contains("sk")) {
				if(msg.contains(",")) {
					String[] st = msg.split(",");
					AnimWriter.aAll(Integer.valueOf(st[1]));
					Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Adding keyframe with duration " + st[1]), null);
				}
				System.out.println("Adding keyframe");
			} else if(msg.contains("sp")) { 
				if(msg.contains(",")) {
					String[] characters = msg.split(",");
					if(characters[1].toLowerCase().startsWith("t")) {
						AnimWriter.setShouldRunSetupParts(true);
						System.out.println("Setting up parts");
						Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Setting up parts"), null);
					}else if(characters[1].toLowerCase().startsWith("f")) {
						AnimWriter.setShouldRunSetupParts(false);
						System.out.println("Not setting up parts");
						Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Not Setting up parts"), null);
					}
				}
			} else if(msg.startsWith("rk")) {
				if(msg.contains(",")) {
					String[] st = msg.split(",");
					AnimWriter.resetKeyFrame(Integer.valueOf(st[1]));
					Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Adding reset keyframe with duration " + st[1]), null);
				}
			} else if(msg.startsWith("loop")) {
				if(msg.contains(",")) {
					String[] st = msg.split(",");
					if(st[1].startsWith("t")) {
						AnimWriter.setLoop(true);
						System.out.println("Loop to true");
						Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Setting loop to true"), null);
					}else if(st[1].startsWith("f")) {
						AnimWriter.setLoop(false);
						System.out.println("Loop to false");
						Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Setting loop to false"), null);
					}
				}
			} else if(msg.equals("p")) {
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.prev(handler.getGunModel());
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Previous keyframe"), null);
				System.out.println("Prev animation");
			} else if(msg.equals("n")) {
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.next(handler.getGunModel());
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Next keyframe"), null);
				System.out.println("Next animation");
			} else if(msg.equals("rl")) {
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.remLast(handler.getGunModel());
				System.out.println("Removing last keyframe");
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Removing last keyframe"), null);
			}else if(msg.equals("start")) {
				AnimWriter.start();
				System.out.println("Start animation");
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Starting animation"), null);
			} else if(msg.equals("stop")) {
				AnimWriter.stop();
				System.out.println("Stopping animation");
			} else if(msg.equals("showAnim")) {
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.resetAnimation();
				AnimWriter.convertAAnimtoAnim(handler.getGunModel());
				System.out.println(AnimWriter.getAnimation());
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Converting raw animation to readable animation"), null);
			} else if(msg.equals("adm")){ 
				AnimWriter.switchDebugAnimation();
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Debug animation: " + AnimWriter.debugAnimation()), null);
			} else if(msg.startsWith("allGun")) { 
				if(msg.contains(",")) {
					String[] st = msg.split(",");
					AnimWriter.allGun(st[1].startsWith("t") ? true : false);
					Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Add all gun parts to animation"), null);
				}
			} else if(msg.equals("st")){
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.addStateFor(handler.getGunModel());
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Saving transform state"), null);
			} else if(msg.equals("acst")){
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.getStateFor(handler.getGunModel()).recover();
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Activating current transform state"), null);
			} else if(msg.equals("nst")){
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.getStateFor(handler.getGunModel()).next();
			} else if(msg.equals("pst")){
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.getStateFor(handler.getGunModel()).prev();
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Activating previous transform state"), null);
			} else if(msg.equals("lst")){
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.getStateFor(handler.getGunModel()).last();
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Activating last transform state"), null);
			} else if(msg.equals("cst")){
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.getStateFor(handler.getGunModel()).recover();
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Activating current transform state"), null);
			} else if(msg.equals("rlst")){
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.getStateFor(handler.getGunModel()).removeLastState();
			} else if(msg.equals("rcst")){
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				AnimWriter.getStateFor(handler.getGunModel()).removeCurrentState();
			} else if(msg.startsWith("rmf")) {
				if(msg.contains(",")) {
					String[] st = msg.split(",");
					ClientHandler handler = handlers.get(getPlayer().getUUID());
					if (handler == null)
						return;
					if(st[1].startsWith("t")) {
						handler.getGunModel().setDebugMuzzleFlash(true);
						Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Activating muzzle falsh debug mode"), null);
					}else {
						handler.getGunModel().setDebugMuzzleFlash(false);
						Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Deactivating muzzle falsh debug mode"), null);
					}
				}
			}else if(msg.startsWith("del")) {
				if(msg.contains(",")) {
					String[] st = msg.split(",");
					if(st[1].equals("0")) {
						AnimWriter.resetAnimation();
						Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Deleting animation"), null);
					}else {
						AnimWriter.resetAanimation();
						Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Deleting Raw animation"), null);
					}
				}else {
					AnimWriter.resetAnimation();
					AnimWriter.resetAanimation();
					Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Deleting all"), null);
				}
				System.out.println("Animations deleted");
			} else if (msg.equals("pp")) {
				ClientHandler handler = handlers.get(Minecraft.getInstance().player.getUUID());
				if (handler == null)
					return;
				GunModelPart part = handler.getGunModel().getPart();
				System.out.println("setPartDisplayTransform(" + part.getName() + 
						", " + part.transform.offsetX + 
						"f, " + part.transform.offsetY + 
						"f, " + part.transform.offsetZ + 
						"f, " + part.transform.rotationX + 
						"f, " + part.transform.rotationY + 
						"f, " + part.transform.rotationZ + 
						"f);");
				Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Printing part transform"), null);
			} 
		}
	}

	// Chat events end

	// Ticking Client

	public static void tickClient(ClientTickEvent e) {

		if (e.phase != Phase.START) {
			Player player = getPlayer();
			if (player != null) {
				ItemStack stack = player.getMainHandItem();
				// System.out.println("UUID null? " + player.getUUID());

				if (!Util.canWork(stack))
					return;
				if (!init) {
					ClientEventHandler.handlers.put(Minecraft.getInstance().player.getUUID(), new ClientHandler());
					ClientEventHandler.init = true;
				} else {
					ClientHandler handler = handlers.get(player.getUUID());
					if (handler == null)
						return;
					ItemGun gun = (ItemGun) stack.getItem();

					//System.out.println("Mag Path " + ServerUtils.getMagPath(stack));
					
					// Init models

					if (stack.getOrCreateTag().getString(Constants.ID) == "") {
						OldGuns.channel.sendToServer(new InitGunMessage(UUID.randomUUID()));
						System.out.println("Id setted to: " + stack.getOrCreateTag().getString(Constants.ID));
					}

					// update handler

					handler.tick();

					// handler.selectGunModel(stack);

					// update sprint progress

					if (!handler.getAimingHandler().isAiming()) {
						handler.getSprintHandler().updateSprintProgress(player.isSprinting());
					}

					// Handling aiming

					if (GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(),
							GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS && !player.isSprinting()
							&& Minecraft.getInstance().screen == null
							&& handler.getSprintHandler().sprintProgress == 0) {
						handler.getAimingHandler().updateAimProgress(true);
					} else {
						handler.getAimingHandler().updateAimProgress(false);
					}

					// end

					// Handle shooting

					if (GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(),
							GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
						if (!player.isSprinting() && Minecraft.getInstance().screen == null && gun.getShootMode(stack) == ItemGun.ShootMode.AUTO && handler.getGunModel().getAnimation() == GunModel.EMPTY) {
							gun.tryShoot(stack, handler);
						}
					}

					// end

					// Handle gun GUI

					if (ATTACHMENTS.isDown() && gun.canChangeParts()) {
						OldGuns.channel.sendToServer(new OpenGunGuiMessage());
					}

					// end
				}
			}
		}
	}

	// Ticking Client end

	// Input

	// Keyboard

	public static void handleKeyboard(InputEvent.KeyInputEvent e) {
		Player player = getPlayer();

		if (player != null) {
			if (!init)
				return;
			ClientHandler handler = handlers.get(getPlayer().getUUID());
			if (handler == null || Minecraft.getInstance().screen != null)
				return;
			// System.out.println(Minecraft.getInstance().screen == null);
			ItemStack stack = player.getMainHandItem();
			GunModel model = handler.getGunModel();

			//stepT = 0.01f;
			//stepR = (float)Math.toRadians(1f);
			
			if (stack.getItem() instanceof ItemGun) {

				if (model == null)
					return;

				if (e.getAction() != GLFW.GLFW_PRESS) {

					Options s = Minecraft.getInstance().options;
					if ((e.getKey() >= 48 && e.getKey() <= 57) || e.getKey() == s.keyDrop.getKey().getValue()
							|| e.getKey() == s.keySwapOffhand.getKey().getValue()) {
						handler.selectGunModel(stack);
					}

					if (Config.CLIENT.doDebugStuff.get()) {

						if (e.getKey() == GLFW.GLFW_KEY_H) {
							model.incIndex();

						} else if (e.getKey() == GLFW.GLFW_KEY_J) {
							model.decIndex();
						}

						if (e.getKey() == GLFW.GLFW_KEY_C) {
							handler.toogleRot();

						}

						if (!handler.isOnRotation()) {
							switch (e.getKey()) {
							case GLFW.GLFW_KEY_RIGHT:
								model.getPart().transform.addOffset(stepT, 0, 0);
								AnimWriter.addTForPart(model.getPart());
								System.out.println("tx: " + model.getPart().transform.offsetX + " ty: "
										+ model.getPart().transform.offsetY + " tz: "
										+ model.getPart().transform.offsetZ);
								break;
							case GLFW.GLFW_KEY_LEFT:
								model.getPart().transform.addOffset(-stepT, 0, 0);
								AnimWriter.addTForPart(model.getPart());
								System.out.println("tx: " + model.getPart().transform.offsetX + " ty: "
										+ model.getPart().transform.offsetY + " tz: "
										+ model.getPart().transform.offsetZ);
								break;
							case GLFW.GLFW_KEY_UP:
								model.getPart().transform.addOffset(0, stepT, 0);
								AnimWriter.addTForPart(model.getPart());
								System.out.println("tx: " + model.getPart().transform.offsetX + " ty: "
										+ model.getPart().transform.offsetY + " tz: "
										+ model.getPart().transform.offsetZ);
								break;
							case GLFW.GLFW_KEY_DOWN:
								model.getPart().transform.addOffset(0, -stepT, 0);
								AnimWriter.addTForPart(model.getPart());
								System.out.println("tx: " + model.getPart().transform.offsetX + " ty: "
										+ model.getPart().transform.offsetY + " tz: "
										+ model.getPart().transform.offsetZ);
								break;
							case GLFW.GLFW_KEY_N:
								model.getPart().transform.addOffset(0, 0, -stepT);
								AnimWriter.addTForPart(model.getPart());
								System.out.println("tx: " + model.getPart().transform.offsetX + " ty: "
										+ model.getPart().transform.offsetY + " tz: "
										+ model.getPart().transform.offsetZ);
								break;
							case GLFW.GLFW_KEY_M:
								model.getPart().transform.addOffset(0, 0, stepT);
								AnimWriter.addTForPart(model.getPart());
								System.out.println("tx: " + model.getPart().transform.offsetX + " ty: "
										+ model.getPart().transform.offsetY + " tz: "
										+ model.getPart().transform.offsetZ);
								break;
							}
						} else {
							switch (e.getKey()) {
							case GLFW.GLFW_KEY_RIGHT:
								model.getPart().transform.addRotation(stepR, 0, 0);
								AnimWriter.addRForPart(model.getPart());
								System.out.println("rx: " + model.getPart().transform.rotationX + " ry: "
										+ model.getPart().transform.rotationY + " rz: "
										+ model.getPart().transform.rotationZ);
								System.out.println("rx: " + Math.toDegrees(model.getPart().transform.rotationX)
										+ " ry: " + Math.toDegrees(model.getPart().transform.rotationY) + " rz: "
										+ Math.toDegrees(model.getPart().transform.rotationZ));
								break;
							case GLFW.GLFW_KEY_LEFT:
								model.getPart().transform.addRotation(-stepR, 0, 0);
								AnimWriter.addRForPart(model.getPart());
								System.out.println("rx: " + model.getPart().transform.rotationX + " ry: "
										+ model.getPart().transform.rotationY + " rz: "
										+ model.getPart().transform.rotationZ);
								System.out.println("rx: " + Math.toDegrees(model.getPart().transform.rotationX)
										+ " ry: " + Math.toDegrees(model.getPart().transform.rotationY) + " rz: "
										+ Math.toDegrees(model.getPart().transform.rotationZ));
								break;
							case GLFW.GLFW_KEY_UP:
								model.getPart().transform.addRotation(0, stepR, 0);
								AnimWriter.addRForPart(model.getPart());
								System.out.println("rx: " + model.getPart().transform.rotationX + " ry: "
										+ model.getPart().transform.rotationY + " rz: "
										+ model.getPart().transform.rotationZ);
								System.out.println("rx: " + Math.toDegrees(model.getPart().transform.rotationX)
										+ " ry: " + Math.toDegrees(model.getPart().transform.rotationY) + " rz: "
										+ Math.toDegrees(model.getPart().transform.rotationZ));
								break;
							case GLFW.GLFW_KEY_DOWN:
								model.getPart().transform.addRotation(0, -stepR, 0);
								AnimWriter.addRForPart(model.getPart());
								System.out.println("rx: " + model.getPart().transform.rotationX + " ry: "
										+ model.getPart().transform.rotationY + " rz: "
										+ model.getPart().transform.rotationZ);
								System.out.println("rx: " + Math.toDegrees(model.getPart().transform.rotationX)
										+ " ry: " + Math.toDegrees(model.getPart().transform.rotationY) + " rz: "
										+ Math.toDegrees(model.getPart().transform.rotationZ));
								break;
							case GLFW.GLFW_KEY_N:
								model.getPart().transform.addRotation(0, 0, stepR);
								AnimWriter.addRForPart(model.getPart());
								System.out.println("rx: " + model.getPart().transform.rotationX + " ry: "
										+ model.getPart().transform.rotationY + " rz: "
										+ model.getPart().transform.rotationZ);
								System.out.println("rx: " + Math.toDegrees(model.getPart().transform.rotationX)
										+ " ry: " + Math.toDegrees(model.getPart().transform.rotationY) + " rz: "
										+ Math.toDegrees(model.getPart().transform.rotationZ));
								break;
							case GLFW.GLFW_KEY_M:
								model.getPart().transform.addRotation(0, 0, -stepR);
								AnimWriter.addRForPart(model.getPart());
								System.out.println("rx: " + model.getPart().transform.rotationX + " ry: "
										+ model.getPart().transform.rotationY + " rz: "
										+ model.getPart().transform.rotationZ);
								System.out.println("rx: " + Math.toDegrees(model.getPart().transform.rotationX)
										+ " ry: " + Math.toDegrees(model.getPart().transform.rotationY) + " rz: "
										+ Math.toDegrees(model.getPart().transform.rotationZ));
								break;
							case GLFW.GLFW_KEY_Z:
								GunModel modeltest = handler.getGunModel();
								if (modeltest != null) {
									if (modeltest.getAnimation() != GunModel.EMPTY) {
										if (modeltest.getAnimationTick() > 0) {
											modeltest.setAnimationTick(modeltest.getAnimationTick() - 1);
											System.out.println(modeltest.getAnimationTick());
										}
									}
								}
								break;
							case GLFW.GLFW_KEY_X:
								GunModel modeltest2 = handler.getGunModel();
								if (modeltest2 != null) {
									if (modeltest2.getAnimation() != GunModel.EMPTY) {
										if (modeltest2.getAnimationTick() < modeltest2.getAnimation().getDuration()) {
											modeltest2.setAnimationTick(modeltest2.getAnimationTick() + 1);
											System.out.println(modeltest2.getAnimationTick());
										}
									}
								}
								break;
							case GLFW.GLFW_KEY_LEFT_ALT:
								AnimWriter.restart();
								break;
							}
						}

					}

					ItemGun gun = (ItemGun) stack.getItem();

					if (e.getKey() == RELOAD.getKey().getValue()
							&& handler.getGunModel().getAnimation() == GunModel.EMPTY) {

						if (gun.requiresMag(stack)) {
							int index = ServerUtils.getIndexForCorrectMag(player.getInventory(), gun.getGunId(), gun.getAcceptedBulletType(stack));
							if (index != -1) {
								handler.tryToReload(gun.getNBTBullets(stack),
										player.getInventory().getItem(index).getOrCreateTag().getInt(Constants.BULLETS),
										index, gun.getNBTHasMag(stack));
							}
						} else {
							int index = ServerUtils.getIndexForItem(player.getInventory(),
									gun.getBulletItem(stack));
							if (index != -1) {
								Item item = gun.getBulletItem(stack);
								int bullets = ServerUtils.getItemCount(player.getInventory(), item);
								int bulletsindex = ServerUtils.getIndexForItem(player.getInventory(), item);
								handler.tryToReload(gun.getNBTBullets(stack), bullets, bulletsindex, gun.getNBTHasMag(stack));
							}
						}
					}
					
					if(e.getKey() == GLFW.GLFW_KEY_LEFT_SHIFT) {
						handler.invertDebugAim();
						System.out.println("Inverting aim");
					}
					
					if(e.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL) {
						Animator.addHistory(handler.getGunModel());
					}else if(e.getKey() == GLFW.GLFW_KEY_RIGHT_ALT){
						Animator.comeBack(handler.getGunModel());
					}
					
					if(e.getKey() == KICKBACK.getKey().getValue()) {
						handler.getGunModel().startKickBackAnim();
					}
					
					if(e.getKey() == INSPECTANIM.getKey().getValue()) {
						handler.getGunModel().startInspectAnim();
					}
					
					if (e.getKey() == GLFW.GLFW_KEY_U && handler.getGunModel().getAnimation() == GunModel.EMPTY) {
						if (ServerUtils.hasMag(stack)) {
							int bullets = ServerUtils.getBullets(stack);
							model.resetForGetOutMag(bullets, bullets);
							model.doGetOutMag(stack);
						}
					}

				}
			} else if (stack.getItem() instanceof ItemMag) {
				if (e.getAction() == GLFW.GLFW_PRESS) {
					if (e.getKey() == RELOAD.getKey().getValue()) {
						ReloadHandler.findCorrectBullet(player.getInventory(), stack);
					}
				}
			}
		}

	}

	// Keyboard end

	// Mouse

	// Mouse end

	public static void scrollEvent(InputEvent.MouseScrollEvent e) {
		Player player = getPlayer();
		if (player != null) {

			ItemStack stack = player.getMainHandItem();
		
			if (Util.canWork(stack)) {
				if (!init)
					return;
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler != null) {
					//e.setCanceled(true);
					System.out.println(e.getScrollDelta());
					Transform tr = handler.getGunModel().getPart().transform;
					tr.setOffset(tr.offsetX+((float)e.getScrollDelta()*0.001f), tr.offsetY, tr.offsetZ);
					handler.reset();
				}
			}
		}
	}

	public static void handleMouse(InputEvent.RawMouseEvent e) {
		Player player = getPlayer();
		if (player != null) {
			
			ItemStack stack = player.getMainHandItem();

			if (Util.canWork(stack)) {
				if (!init)
					return;
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler == null)
					return;
				
				if(!e.isCanceled()) {
				
				if (e.getButton() == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
					handler.selectGunModel(stack);
				}

				if (e.getAction() == GLFW.GLFW_PRESS) {

					ItemGun gun = (ItemGun) stack.getItem();
					ShootMode mode = gun.getShootMode(stack);
					if (mode == ShootMode.BURST || mode == ShootMode.SEMI) {
						if (e.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
							if(mode == ShootMode.SEMI) {
								if (!getPlayer().isSprinting() && Minecraft.getInstance().screen == null && handler.getGunModel().getAnimation() == GunModel.EMPTY) {
									gun.tryShoot(stack, handler);
									//System.out.println("Trying to shoot");
								}
							}else {
								if (!getPlayer().isSprinting() && Minecraft.getInstance().screen == null) {
									handler.getShootHandler().setBurstsAndTarget(gun.getBurstShots(stack), gun.getBurstTargetTime(stack));
								}
							}
						}
					}
					
				}
				
				}
			}

		}
	}

	// End

	// Input end

	@OnlyIn(Dist.CLIENT)
	public static PlayerRenderer getRenderPlayer(AbstractClientPlayer player) {
		Minecraft mc = Minecraft.getInstance();
		EntityRenderDispatcher manager = mc.getEntityRenderDispatcher();
		return (PlayerRenderer)manager.getSkinMap().get("slim");
	}

	@OnlyIn(Dist.CLIENT)
	public static PlayerModel<AbstractClientPlayer> getPlayerModel(AbstractClientPlayer player) {
		return getRenderPlayer(player).getModel();
	}

	public static Player getPlayer() {
		return Minecraft.getInstance().player;
	}

}
