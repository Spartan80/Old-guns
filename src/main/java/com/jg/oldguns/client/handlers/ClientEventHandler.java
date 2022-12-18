package com.jg.oldguns.client.handlers;

import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.models.gunmodels.WinchesterGunModel;
import com.jg.oldguns.client.screens.AnimationScreen;
import com.jg.oldguns.client.screens.GunPartsScreen;
import com.jg.oldguns.events.RegisterEasingsEvent;
import com.jg.oldguns.events.RegisterGunModelEvent;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.registries.EntityRegistries;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.Utils;
import com.mojang.logging.LogUtils;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ClientEventHandler {

	private static Minecraft mc = Minecraft.getInstance();
	private static ClientHandler client = new ClientHandler();
	
	public static final KeyMapping SWITCH = new KeyMapping("key.jgpg.switch", GLFW.GLFW_KEY_C, OldGuns.MODID);
	public static final KeyMapping DISPLAY = new KeyMapping("key.jgpg.display", GLFW.GLFW_KEY_KP_ADD, OldGuns.MODID);
	public static final KeyMapping RELOAD = new KeyMapping("key.jgpg.reload", GLFW.GLFW_KEY_R, OldGuns.MODID);
	public static final KeyMapping LOOKANIM = new KeyMapping("key.jgpg.look", GLFW.GLFW_KEY_LEFT_ALT, OldGuns.MODID);
	public static final KeyMapping LEFT = new KeyMapping("key.jgpg.left", GLFW.GLFW_KEY_LEFT, OldGuns.MODID);
	public static final KeyMapping UP = new KeyMapping("key.jgpg.up", GLFW.GLFW_KEY_UP, OldGuns.MODID);
	public static final KeyMapping DOWN = new KeyMapping("key.jgpg.down", GLFW.GLFW_KEY_DOWN, OldGuns.MODID);
	public static final KeyMapping RIGHT = new KeyMapping("key.jgpg.right", GLFW.GLFW_KEY_RIGHT, OldGuns.MODID);
	public static final KeyMapping N = new KeyMapping("key.jgpg.n", GLFW.GLFW_KEY_N, OldGuns.MODID);
	public static final KeyMapping M = new KeyMapping("key.jgpg.m", GLFW.GLFW_KEY_M, OldGuns.MODID);
	public static final KeyMapping H = new KeyMapping("key.jgpg.z", GLFW.GLFW_KEY_H, OldGuns.MODID);
	public static final KeyMapping J = new KeyMapping("key.jgpg.x", GLFW.GLFW_KEY_J, OldGuns.MODID);
	public static final KeyMapping MINUS = new KeyMapping("key.jgpg.-", GLFW.GLFW_KEY_MINUS, OldGuns.MODID);
	
	public static void setup() {
		ClientsHandler.register(mc.getUser(), client);
		
		EntityRenderers.register(EntityRegistries.BULLET.get(), ThrownItemRenderer::new);
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
	
	private static void registerSpecialModels(ModelEvent.RegisterAdditional e) {
		e.register(new ModelResourceLocation(Paths.WINCHESTERBULLETLOADER, "inventory"));
	}
	
	private static void bakeModels(ModelEvent.BakingCompleted e) {
		LogUtils.getLogger().info("Baking models");
		GunModelsHandler.setInit(false);
		if(!GunModelsHandler.getInit()) {
			GunModelsHandler.setInit(true);
			
			MinecraftForge.EVENT_BUS.start();
			MinecraftForge.EVENT_BUS.post(new RegisterGunModelEvent());
			MinecraftForge.EVENT_BUS.post(new RegisterEasingsEvent());
		}
		
		Map<ResourceLocation, BakedModel> models = e.getModels();

		for (String item : GunModelsHandler.getMap().keySet()) {
			GunModel gunModel = GunModelsHandler.get(item);
			gunModel.setModel(
					gunModel.getModifiedModel(models.get(Utils.getMR(gunModel.gun))));
			models.put(Utils.getMR(gunModel.gun),
					gunModel.getModel());
			LogUtils.getLogger().info("Registering " + item + " modelRes: " + Utils.getMR(gunModel.gun).toString());
		}
	}
	
	// Custom events
	
	private static void registerGunModels(RegisterGunModelEvent e) {
		LogUtils.getLogger().info("Registering models");
		e.register(ItemRegistries.WINCHESTER.get(), new WinchesterGunModel(client));
	}
	
	// Rendering
	
	private static void renderFirstPersonStuff(RenderHandEvent e) {
		Player player = mc.player;
		if (player != null) {
			ClientHandler.partialTicks = e.getPartialTick();
			if (e.getHand() == InteractionHand.MAIN_HAND) {
				ItemStack stack = player.getMainHandItem();
				if (stack.getItem() instanceof GunItem) {
					e.setCanceled(true);
					client.render(e.getPoseStack(), e.getMultiBufferSource(), e.getPackedLight());
				}
			}
		}
	}
	
	// Rendering Overlay

		public static void renderOverlayPre(RenderGuiOverlayEvent.Pre e) {
			Player player = mc.player;
			if (player == null)
				return;
			
			if (player.getMainHandItem().getItem() instanceof GunItem) {
				if (e.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {
					if (mc.options.getCameraType().isFirstPerson()) {
						e.setCanceled(true);
					}
				}
			}
		}

		public static void renderOverlayPost(RenderGuiOverlayEvent.Post e) {
			Player player = mc.player;
			if (player == null)
				return;
			if (player.getMainHandItem().getItem() instanceof GunItem) {
				if(((GunItem)player.getMainHandItem().getItem()).hasScope()) {
					if (e.getOverlay()== VanillaGuiOverlay.HOTBAR.type()) {
						if(client.getAimHandler().getProgress() > 0.5f) {
							/*RenderHelper.renderScopeOverlay(
									client.getAimHandler().getProgress());*/
						}
					}
				}
			}
		}
	
	private static void registerEasings(RegisterEasingsEvent e){
		e.register("easeInSine", (x) -> (float)(1 - Math.cos((x * Math.PI) / 2)));
		e.register("easeOutSine", (x) -> (float)(Math.sin((x * Math.PI) / 2)));
		e.register("easeInOutSine", (x) -> (float)(-(Math.cos(Math.PI * x) - 1) / 2));
		
		e.register("easeInQuad", (x) -> (float)(x * x));
		e.register("easeOutQuad", (x) -> (float)(1 - (1 - x) * (1 - x)));
		e.register("easeInOutQuad", (x) -> (float)(x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2));
		
		e.register("easeInCubic", (x) -> (float)(x * x * x));
		e.register("easeOutCubic", (x) -> (float)(1 - Math.pow(1 - x, 3)));
		e.register("easeInOutCubic", (x) -> (float)(x < 0.5 ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2));
		
		e.register("easeInQuart", (x) -> (float)(x * x * x * x));
		e.register("easeOutQuart", (x) -> (float)(1 - Math.pow(1 - x, 4)));
		e.register("easeInOutQuart", (x) -> (float)(x < 0.5 ? 8 * x * x * x * x : 1 - Math.pow(-2 * x + 2, 4) / 2));
		
		e.register("easeInQuint", (x) -> (float)(x * x * x * x * x));
		e.register("easeOutQuint", (x) -> (float)(1 - Math.pow(1 - x, 5)));
		e.register("easeInOutQuint", (x) -> (float)(x < 0.5 ? 16 * x * x * x * x * x : 1 - Math.pow(-2 * x + 2, 5) / 2));
		
		e.register("easeInExpo", (x) -> (float)(x == 0 ? 0 : Math.pow(2, 10 * x - 10)));
		e.register("easeOutExpo", (x) -> (float)(x == 1 ? 1 : 1 - Math.pow(2, -10 * x)));
		e.register("easeInOutExpo", (x) -> (float)(x == 0
				  ? 0
				  : x == 1
				  ? 1
			      : x < 0.5 ? Math.pow(2, 20 * x - 10) / 2
				  : (2 - Math.pow(2, -20 * x + 10)) / 2));
		
		e.register("easeInCirc", (x) -> (float)(1 - Math.sqrt(1 - Math.pow(x, 2))));
		e.register("easeOutCirc", (x) -> (float)(Math.sqrt(1 - Math.pow(x - 1, 2))));
		e.register("easeInOutCirc", (x) -> (float)(x < 0.5
				  ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2
						  : (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2));
		
		e.register("easeInBack", (x) -> (float)(2.70158 * x * x * x - 1.70158f * x * x));
		e.register("easeOutBack", (x) -> (float)(1 + 2.70158 * Math.pow(x - 1, 3) + 1.70158 * Math.pow(x - 1, 2)));
		e.register("easeInOutBack", (x) -> (float)(x < 0.5
				  ? (Math.pow(2 * x, 2) * ((2.5949095f + 1) * 2 * x - 2.5949095f)) / 2
						  : (Math.pow(2 * x - 2, 2) * ((2.5949095f + 1) * (x * 2 - 2) + 2.5949095f) + 2) / 2));
		
		e.register("easeInElastic", (x) -> (float)(x == 0
				  ? 0
				  : x == 1
				  ? 1
				  : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) 
						  * ((2 * Math.PI) / 3))));
		e.register("easeOutElastic", (x) -> (float)(x == 0
				  ? 0
				  : x == 1
				  ? 1
				  : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75f) * 
						  ((2 * Math.PI) / 3)) + 1));
		e.register("easeInOutElastic", (x) -> (float)(x == 0
				  ? 0
				  : x == 1
				  ? 1
				  : x < 0.5
				  ? -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * 
						  ((2 * Math.PI) / 4.5))) / 2
				  : (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * 
						  ((2 * Math.PI) / 4.5))) / 2 + 1));
		
		e.register("easeInBounce", (x) -> (float)(1 - e.getEasing("easeOutBounce").get(1 - x)));
		e.register("easeOutBounce", (x) -> {
			float n1 = 7.5625f;
			float d1 = 2.75f;
			
			if (x < 1 / d1) {
			    return n1 * x * x;
			} else if (x < 2 / d1) {
			    return (float)(n1 * (x -= 1.5 / d1) * x + 0.75);
			} else if (x < 2.5 / d1) {
			    return (float)(n1 * (x -= 2.25 / d1) * x + 0.9375);
			} else {
			    return (float)(n1 * (x -= 2.625 / d1) * x + 0.984375);
			}
		});
		e.register("easeInOutBounce", (x) -> (float)(x < 0.5
				  ? (1 - e.getEasing("easeOutBounce").get(1 - 2 * x)) / 2
						  : (1 + e.getEasing("easeOutBounce").get(2 * x - 1)) / 2));
		LogUtils.getLogger().info("Registering easings");
	}

	private static void clientTick(ClientTickEvent e) {
		if (e.phase == Phase.START) {
			Player player = mc.player;
			if (player != null) {
				ItemStack stack = player.getMainHandItem();
				if (stack.getItem() instanceof GunItem) {
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
					}
				}
			}
		}
	}

	// Player
	
	public static void interruptInteractions(PlayerInteractEvent.LeftClickBlock e) {
		Player player = e.getEntity();
		if(player != null) {
			if(player.getMainHandItem().getItem() instanceof GunItem) {
				e.setCanceled(true);
			}
		}
	}
	
	// Input

		private static void handleKeyboard(InputEvent.Key e) {
			Screen screen = mc.screen;
			if (screen == null || screen instanceof AnimationScreen || screen instanceof GunPartsScreen) {
				boolean animEditFocused = false;
				if (screen instanceof AnimationScreen) {
					animEditFocused = ((AnimationScreen) screen).getEditBoxes().get(6).isFocused()
							|| ((AnimationScreen) screen).getEditBoxes().get(9).isFocused();
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
						//LogUtils.getLogger().info("Key: " + e.getKey());
						if (e.getAction() == GLFW.GLFW_PRESS) {
							if (RELOAD.getKey().getValue() == e.getKey()) {
								// OldGuns.channel.sendToServer(new LoadBulletMessage(true));
								client.getGunModel().reload(player, stack);
							} else if (LOOKANIM.getKey().getValue() == e.getKey()) {
								client.getGunModel().setAnimation(client.getGunModel().getLookAnimation());
								LogUtils.getLogger().info("Setting look animation");
							} else if(e.getKey() == 93) {
								MinecraftForge.EVENT_BUS.start();
								MinecraftForge.EVENT_BUS.post(new RegisterGunModelEvent());
								MinecraftForge.EVENT_BUS.post(new RegisterEasingsEvent());
							}
							if(true){//Config.CLIENT.doDebugStuff.get()) {
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
								} else if(e.getKey() == 89) {
									client.getGunModel().setShouldUpdateAnimation(true);
								}
							}
						}
						if(true){//Config.CLIENT.doDebugStuff.get()) {
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
					}
				}
			}
		}

		private static void handleRawMouse(InputEvent.MouseButton e) {
			if (mc.screen == null) {
				Player player = mc.player;
				if (player != null) {
					if (player.getMainHandItem().getItem() instanceof GunItem) {
						if (e.getAction() == GLFW.GLFW_PRESS) {
							if (e.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
								if (client.getGunModel() != null) {
									if (client.getRecoilHandler().getProgress() == 0) {
										client.shoot(player);
									}
								}
							}
						}
					}
				}
			}
		}
	
}
