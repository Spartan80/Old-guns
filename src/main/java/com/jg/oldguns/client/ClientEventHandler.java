package com.jg.oldguns.client;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.animations.Transform;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.render.BulletRender;
import com.jg.oldguns.client.screens.GunAmmoGui;
import com.jg.oldguns.client.screens.GunCraftingGui;
import com.jg.oldguns.client.screens.GunGui;
import com.jg.oldguns.client.screens.GunPartsGui;
import com.jg.oldguns.client.screens.MagGui;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.containers.GunContainer;
import com.jg.oldguns.debug.AnimWriter;
import com.jg.oldguns.events.GunModelRegistryEvent;
import com.jg.oldguns.events.GunPartPropertiesRegistryEvent;
import com.jg.oldguns.gunmodels.ColtGunModel;
import com.jg.oldguns.gunmodels.Kar98kGunModel;
import com.jg.oldguns.gunmodels.LugerP08GunModel;
import com.jg.oldguns.gunmodels.Model37GunModel;
import com.jg.oldguns.gunmodels.Mp40GunModel;
import com.jg.oldguns.gunmodels.PiratePistolGunModel;
import com.jg.oldguns.gunmodels.PirateRifleGunModel;
import com.jg.oldguns.gunmodels.RevolverGunModel;
import com.jg.oldguns.gunmodels.ThompsonGunModel;
import com.jg.oldguns.gunmodels.WinchesterGunModel;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.ItemGun.ShootMode;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.network.InitGunMessage;
import com.jg.oldguns.network.InitGunPartPropertiesMessage;
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
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = OldGuns.MODID, value = Dist.CLIENT)
public class ClientEventHandler {

	public static Map<UUID, ClientHandler> handlers = new HashMap<UUID, ClientHandler>();
	public static float stepR = (float) Math.toRadians(1);
	public static float stepT = 0.01f;

	// Keybinds

	public static final KeyBinding RELOAD = new KeyBinding("key.jgguns.reload", GLFW.GLFW_KEY_R, OldGuns.MODID);
	public static final KeyBinding ATTACHMENTS = new KeyBinding("key.jgguns.attachments", GLFW.GLFW_KEY_V,
			OldGuns.MODID);

	public static boolean init = false;

	public static void setup() {

		// Register Screens

		ScreenManager.register(ContainerRegistries.GUN_CONTAINER.get(), GunGui::new);
		ScreenManager.register(ContainerRegistries.MAG_CONTAINER.get(), MagGui::new);
		ScreenManager.register(ContainerRegistries.GUN_PARTS_CONTAINER.get(), GunPartsGui::new);
		ScreenManager.register(ContainerRegistries.GUN_CRAFTING_CONTAINER.get(), GunCraftingGui::new);
		ScreenManager.register(ContainerRegistries.GUN_AMMO_CONTAINER.get(), GunAmmoGui::new);

		// Register Entity Renders

		RenderingRegistry.registerEntityRenderingHandler(EntityRegistries.BULLET.get(),
				manager -> new BulletRender(manager));

		// Register KeyBindings

		ClientRegistry.registerKeyBinding(RELOAD);
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
		bus.addListener(ClientEventHandler::renderWorldLastEvent);
		
	}

	public static void registerGunModels(GunModelRegistryEvent e) {
		
		System.out.println("sads");
		
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
	}
	
	public static void modelRegistry(ModelRegistryEvent e) {
		System.out.println("Loading models");
		Map<String, ModelResourceLocation> OldGunsMap = new HashMap<String, ModelResourceLocation>();
		ModelHandler.INSTANCE.pushMod(OldGuns.MODID, OldGunsMap);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.KARKHAM);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.PPHAM);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.COLTHAM);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.COLTSLIDER);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.COLTT);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.KARKT);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.PPT);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.THOMPSONT);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.THOMPSONH);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.PRT);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.PRH);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.WINCHESTERT);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.WINCHESTERHAMMER);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.MP40T);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.MP40H);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.LUGERP08T);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.LUGERP08HB);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.LUGERP08HF);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.REVOLVERHAMMER);
		ModelHandler.INSTANCE.registerSpecialModel(Paths.MODEL37LOADER);
		ModelHandler.INSTANCE.initSpecialModels(OldGunsMap);
	}

	public static void bakeEvent(ModelBakeEvent e) {
		
		if (!ModelHandler.INSTANCE.init) {
			ModelHandler.INSTANCE.setInit();
			ModelHandler.INSTANCE.initModels();
			
			MinecraftForge.EVENT_BUS.start();
			GunModelRegistryEvent re = new GunModelRegistryEvent();
			MinecraftForge.EVENT_BUS.post(re);
			
			GunPartPropertiesRegistryEvent partRegEvent = new GunPartPropertiesRegistryEvent();
			MinecraftForge.EVENT_BUS.post(partRegEvent);
			
			Map<ResourceLocation, IBakedModel> models = e.getModelRegistry();

			for (ResourceLocation l : models.keySet()) {
				if (l.toString().contains("oldguns")) {
					System.out.println(l.toString());
				}
			}

			for (Item item : ModelHandler.INSTANCE.gunmodels.keySet()) {
				models.put(new ModelResourceLocation(item.getRegistryName().toString(), "inventory"),
						ModelHandler.INSTANCE.getGunModel(item)
								.getModifiedModel(ModelHandler.INSTANCE.getModel(item.getRegistryName().toString())));
			}
			
			System.out.println(ModelHandler.INSTANCE.getGunModelMap().isEmpty());
			System.out.println("Hook");
		}

	}

	public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
		System.out.println("Player logged, player is null? " + e.getPlayer() == null);
		if (!init) {
			OldGuns.channel.sendTo(new InitMessage(), ((ServerPlayerEntity) e.getEntity()).connection.connection,
					NetworkDirection.PLAY_TO_CLIENT);
			OldGuns.channel.sendToServer(new InitGunPartPropertiesMessage());
		}
		/*
		 * if(!init) { handlers.put(e.getPlayer().getUUID(), new ClientHandler()); init
		 * = true; }
		 */
	}

	// Models end

	// Fov

	public static void fovModifier(EntityViewRenderEvent.FOVModifier e) {
		PlayerEntity player = getPlayer();
		if (Util.isAvailable(player)) {
			if (Util.canWork(player)) {
				if (!init)
					return;
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler != null) {
					if (handler.getAimingHandler().isAiming()) {
						ItemGun gun = (ItemGun) player.getMainHandItem().getItem();
						float currentFov = (float) Minecraft.getInstance().options.fov;
						e.setFOV(MathHelper.lerp(handler.getAimingHandler().getProgress(),
								currentFov,
								currentFov-((currentFov*gun.getNBTScopePercent(player.getMainHandItem()))*0.01f)));
					}
				}
			}
		}
	}

	// Rendering

	// Rendering gun and all parts

	public static void renderHand(RenderHandEvent e) {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null) {
			ItemStack stack = player.getMainHandItem();

			if (Util.canWork(stack)) {
				stepT = 0.01f;
				stepR = (float)Math.toRadians(1f);
				e.setCanceled(true);
				Impl impl = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());

				if (!init)
					return;
				ClientHandler handler = handlers.get(getPlayer().getUUID());
				if (handler != null) {
					handler.render(e.getMatrixStack(), stack, e.getBuffers(), impl, e.getLight(),
							OverlayTexture.NO_OVERLAY);
				}

			}
		}
	}

	// Rendering Overlay

	public static void renderOverlayPre(RenderGameOverlayEvent.Pre e) {
		if (Util.canWork(getPlayer())) {
			if (e.getType() == ElementType.CROSSHAIRS) {
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
	
	public static void renderWorldLastEvent(RenderWorldLastEvent e) {
		/*if(Minecraft.getInstance().player.getMainHandItem().getItem() instanceof ItemGun) {
		
			ClientHandler handler = handlers.get(getPlayer().getUUID());
			if (handler == null)
				return;
			GunModel model = handler.getGunModel();
			MatrixStack matrixStack = e.getMatrixStack();
			Impl renderBuffers = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
			IVertexBuilder vertexBuilderLines = renderBuffers.getBuffer(RenderType.LINES);
			Color color = Color.RED;
			
			float s = 0.01f;
			
			final Vector3d [] BASE_VERTICES = {
					  new Vector3d(0, 0, 0),
		              new Vector3d(s, 0, s),
		              new Vector3d(0, 0, 0),
		              new Vector3d(-s, 0, 0),
		              new Vector3d(0, 0, 0),
		              new Vector3d(0, s, 0)
		              //new Vector3d(0, 0, s)
		      };
			
			matrixStack.pushPose();
			int v = 3;
			int r = 0;
			int r1 = 0;
			int r2 = 90;
			matrixStack.translate(v, v, v);
			matrixStack.mulPose(new Quaternion(r, r1, r2, true));
			
			Matrix4f matrixPos = e.getProjectionMatrix().copy();//matrixStack.last().pose();  //retrieves the current transformation matrix
			//matrixPos.translate(new Vector3f((model.getPart().transform.offsetX*0.1f), model.getPart().transform.offsetY, model.getPart().transform.offsetZ));
			//matrixPos.translate(new Vector3f(0, 0, 0));
			// draw the base
		    for (int i = 1; i < BASE_VERTICES.length; ++i) {
		      drawLine(matrixPos, vertexBuilderLines, color, BASE_VERTICES[i-1], BASE_VERTICES[i]);
		    }
		    
		    matrixStack.popPose();
		    
		    renderBuffers.endBatch();
		}*/
		
	}
	
	private static void drawLine(Matrix4f matrixPos, IVertexBuilder renderBuffer,
            Color color,
            Vector3d startVertex, Vector3d endVertex) {
			renderBuffer.vertex(matrixPos, (float) startVertex.x(), (float) startVertex.y(), (float) startVertex.z())
			.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())   // there is also a version for floats (0 -> 1)
			.endVertex();
			renderBuffer.vertex(matrixPos, (float) endVertex.x(), (float) endVertex.y(), (float) endVertex.z())
			.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())   // there is also a version for floats (0 -> 1)
			.endVertex();
}

	// Rendering end

	//

	// Chat events

	public static void onChatMessage(ClientChatEvent e) {

		if (Config.CLIENT.doDebugStuff.get()) {
			String msg = e.getMessage();

			if(msg.contains("#help")) {
				Minecraft mc = Minecraft.getInstance();
				mc.player.sendMessage(new TranslationTextComponent("§2save §c- §fSaves animations on a file with the given name - Usage save,<filename.txt>"), null);
				mc.player.sendMessage(new TranslationTextComponent("§2print §c- §fPrints the current animations - Usage print"), null);
				mc.player.sendMessage(new TranslationTextComponent("§2sk §c- §fStarts a keyframe with specified duration - Usage sk,<duration>"), null);
				mc.player.sendMessage(new TranslationTextComponent("§2ek §c- §fEnds a keyframe with specified duration, if no duration specified it ends with no duration - Usage ek,<duration> or ek for no duration"), null);
				mc.player.sendMessage(new TranslationTextComponent("§2t §c- §fAdds the translation of the current part to the current animation - Usage t"), null);
				mc.player.sendMessage(new TranslationTextComponent("§2r §c- §fAdds the rotation of the current part to the current animation - Usage r"), null);
				mc.player.sendMessage(new TranslationTextComponent("§2tr §c- §fAdds the translation and rotation of the current part to the current animation - Usage tr"), null);
				mc.player.sendMessage(new TranslationTextComponent("§2all §c- §fAdds the translation and rotation of all modified parts to the current animation - Usage all"), null);
				mc.player.sendMessage(new TranslationTextComponent("§2pp §c- §fPrints the current transform of the part oriented to positioning the gun part - Usage pp"), null);
			}else if (msg.contains("save")) {
				String[] characters = e.getMessage().split(",");

				try {

					String name = characters[1];
					AnimWriter.save(name);

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			} else if (msg.equals("print")) {
				System.out.println(AnimWriter.animation);
			} else if (msg.contains("sk")) {
				if (msg.contains(",")) {
					String[] characters = msg.split(",");

					try {

						int length = Integer.valueOf(characters[1]);
						AnimWriter.addStart(length);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					System.out.println("You must speify a length");
				}
			} else if (msg.contains("ek")) {

				if (msg.contains(",")) {

					String[] characters = msg.split(",");

					try {

						int length = Integer.valueOf(characters[1]);

						AnimWriter.finish(length);

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				} else {
					AnimWriter.finish();
				}

			} else if (msg.equals("t")) {
				AnimWriter.writeTransform("t");
			} else if (msg.equals("r")) {
				AnimWriter.writeTransform("r");
			} else if (msg.equals("tr")) {
				AnimWriter.writeTransform("tr");
			} else if (msg.equals("all")) {
				AnimWriter.all();
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
			} 
		}
	}

	// Chat events end

	// Ticking Client

	public static void tickClient(ClientTickEvent e) {

		if (e.phase != Phase.START) {
			PlayerEntity player = getPlayer();
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
						
						/*
						 * for(Minecraft.getInstance().getModelManager().getModel(null)) {
						 * 
						 * }
						 */

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
		PlayerEntity player = getPlayer();

		if (player != null) {
			if (!init)
				return;
			ClientHandler handler = handlers.get(getPlayer().getUUID());
			if (handler == null || Minecraft.getInstance().screen != null)
				return;
			// System.out.println(Minecraft.getInstance().screen == null);
			ItemStack stack = player.getMainHandItem();
			GunModel model = handler.gunmodel;

			stepT = 0.001f;
			stepR = (float)Math.toRadians(0.1f);
			
			if (stack.getItem() instanceof ItemGun) {

				if (model == null)
					return;

				if (e.getAction() != GLFW.GLFW_PRESS) {

					GameSettings s = Minecraft.getInstance().options;
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

						if (!handler.rot) {
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
							int index = ServerUtils.getIndexForCorrectMag(player.inventory, gun.getGunId(), gun.getAcceptedBulletType(stack));
							if (index != -1) {
								handler.tryToReload(gun.getNBTBullets(stack),
										player.inventory.getItem(index).getOrCreateTag().getInt(Constants.BULLETS),
										index);
							}
						} else {
							int index = ServerUtils.getIndexForItem(player.inventory,
									gun.getBulletItem(stack));
							if (index != -1) {
								Item item = gun.getBulletItem(stack);
								int bullets = ServerUtils.getItemCount(player.inventory, item);
								int bulletsindex = ServerUtils.getIndexForItem(player.inventory, item);
								handler.tryToReload(gun.getNBTBullets(stack), bullets, bulletsindex);
							}
						}
					}
					
					if(e.getKey() == GLFW.GLFW_KEY_LEFT_SHIFT) {
						handler.invertDebugAim();
						System.out.println("Inverting aim");
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
						ReloadHandler.findCorrectBullet(player.inventory, stack);
					}
				}
			}
		}

	}

	// Keyboard end

	// Mouse

	// Mouse end

	public static void scrollEvent(InputEvent.MouseScrollEvent e) {
		PlayerEntity player = getPlayer();
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
		PlayerEntity player = getPlayer();
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
	public static PlayerRenderer getRenderPlayer(AbstractClientPlayerEntity player) {
		Minecraft mc = Minecraft.getInstance();
		EntityRendererManager manager = mc.getEntityRenderDispatcher();
		return manager.getSkinMap().get("slim");
	}

	@OnlyIn(Dist.CLIENT)
	public static PlayerModel<AbstractClientPlayerEntity> getPlayerModel(AbstractClientPlayerEntity player) {
		return getRenderPlayer(player).getModel();
	}

	public static PlayerEntity getPlayer() {
		return Minecraft.getInstance().player;
	}

}
