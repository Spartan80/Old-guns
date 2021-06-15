package com.redwyvern.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.redwyvern.animation.AnimationManager;
import com.redwyvern.callbacks.KeyboardCallback.KeyPressedEvent;
import com.redwyvern.client.render.RenderingEntityRegister;
import com.redwyvern.config.Config;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.handlers.ShootHandler;
import com.redwyvern.items.GunPartMold;
import com.redwyvern.items.guns.PirateRifle;
import com.redwyvern.loot.EnchantRandomWithLevel;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.network.setScopeMessage;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.util.EnchUtil;
import com.redwyvern.util.InventoryHelper;
import com.redwyvern.util.RenderUtil;
import com.redwyvern.util.Util;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputEvent.MouseInputEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class ClientProxy implements IProxy {

	public static final Supplier<KeyBinding> MOUSERIGHT = () -> Minecraft.getInstance().options.keyAttack;
	public static final KeyBinding LEFT = new KeyBinding("left", GLFW.GLFW_KEY_LEFT, OldGuns.MODID);
	public static final KeyBinding RIGHT = new KeyBinding("right", GLFW.GLFW_KEY_RIGHT, OldGuns.MODID);
	public static final KeyBinding UP = new KeyBinding("up", GLFW.GLFW_KEY_UP, OldGuns.MODID);
	public static final KeyBinding DOWN = new KeyBinding("down", GLFW.GLFW_KEY_DOWN, OldGuns.MODID);
	public static final KeyBinding FRONT = new KeyBinding("front", GLFW.GLFW_KEY_N, OldGuns.MODID);
	public static final KeyBinding BACK = new KeyBinding("back", GLFW.GLFW_KEY_M, OldGuns.MODID);

	public static final KeyBinding RELOAD = new KeyBinding("reload", GLFW.GLFW_KEY_R, OldGuns.MODID);
	public static final KeyBinding ATT = new KeyBinding("att", GLFW.GLFW_KEY_B, OldGuns.MODID);

	public static final KeyBinding CHANGEHAMMER = new KeyBinding("hammer", GLFW.GLFW_KEY_Z, OldGuns.MODID);
	public static final KeyBinding CHANGETYPE = new KeyBinding("change", GLFW.GLFW_KEY_C, OldGuns.MODID);
	public static final KeyBinding CHANGEHAND = new KeyBinding("other", GLFW.GLFW_KEY_H, OldGuns.MODID);
	public static final KeyBinding CHANGEHANDSIDE = new KeyBinding("otherhandside", GLFW.GLFW_KEY_G, OldGuns.MODID);
	public static final KeyBinding XSCALE = new KeyBinding("plusscale", GLFW.GLFW_KEY_L, OldGuns.MODID);
	public static final KeyBinding YSCALE = new KeyBinding("minusscale", GLFW.GLFW_KEY_K, OldGuns.MODID);
	public static final KeyBinding ZSCALE = new KeyBinding("zscale", GLFW.GLFW_KEY_DELETE, OldGuns.MODID);

	public static final int MAXAIMTICKS = 3;
	public static final int MAXSPRINTTICKS = 3;

	public static float tx, ty, tz, rx, ry, rz;
	private float atx, aty, atz, arx, ary, arz;
	private float latx, laty, latz, larx, lary, larz;
	private float htx, hty, htz, hrx, hry, hrz;
	private boolean rotate, arm, right, hammer;
	private float sx, sy, sz;
	private boolean debug = false;
	public static Map<UUID, AnimationManager> manager = new HashMap<UUID, AnimationManager>();
	public static Map<UUID, ShootHandler> shoot = new HashMap<UUID, ShootHandler>();
	public ResourceLocation scopeimage = new ResourceLocation("oldguns:textures/blocks/optic_rifle.png");
	float stepmodel = 0.004f;
	float stepscale = 0.01f;

	IBakedModel scope;
	IBakedModel kar98khammer;
	public static final ModelResourceLocation scoperes = new ModelResourceLocation("oldguns:scope", "inventory");
	public static final ModelResourceLocation hammerres = new ModelResourceLocation("oldguns:pirate_pistol_hammer_down",
			"inventory");

	@Override
	public void registerModEventListeners(IEventBus bus) {
		bus.addListener(this::registerModels);

	}

	@Override
	public void registerForgeEventListeners(IEventBus bus) {
		bus.addListener(this::modifyLootTable);
		bus.addListener(this::clientTick);
		bus.addListener(this::onPlayerChangeItem);
		bus.addListener(this::renderHand);
		bus.addListener(this::renderItemTooltip);
		bus.addListener(this::drawOverlay);
		bus.addListener(this::drawOverlayPost);
		bus.addListener(this::onMouseClick);
		bus.addListener(this::onMouseScroll);
		bus.addListener(this::onKeyboardInput);
		bus.addListener(this::onPlayerJoin);
		bus.addListener(this::onFov);

	}

	public void modifyLootTable(LootTableLoadEvent event) {
		if ((event.getName().equals(LootTables.SHIPWRECK_MAP) || event.getName().equals(LootTables.SHIPWRECK_SUPPLY)
				|| event.getName().equals(LootTables.SHIPWRECK_TREASURE))) {
			LootEntry.Builder item = ItemLootEntry.lootTableItem(ItemRegistries.pirate_pistol.get()).setQuality(20)
					.setWeight(5);
			LootPool.Builder builder = new LootPool.Builder().name("oldguns_piratepistol").add(item)
					.when(RandomChance.randomChance(0.35f)).setRolls(ConstantRange.exactly(1)).bonusRolls(0, 1);
			event.getTable().addPool(builder.build());
		}
		if (event.getName().equals(LootTables.DESERT_PYRAMID) || event.getName().equals(LootTables.JUNGLE_TEMPLE)
				|| event.getName().equals(LootTables.ABANDONED_MINESHAFT)
				|| event.getName().equals(LootTables.WOODLAND_MANSION)) {
			LootEntry.Builder book = ItemLootEntry.lootTableItem(Items.ENCHANTED_BOOK)
					.apply(EnchantRandomWithLevel.enchantRandomWithLevels()).setQuality(20).setWeight(5);
			LootPool.Builder builder = new LootPool.Builder().name("oldguns_piratepistol").add(book)
					.when(RandomChance.randomChance(0.35f)).setRolls(ConstantRange.exactly(1)).bonusRolls(0, 1);
			event.getTable().addPool(builder.build());
		}
		if (event.getName().toString().contains("village")) {
			LootEntry.Builder gunpart = ItemLootEntry
					.lootTableItem(Util.gunwoodparts.get((int) Util.numInRange(0, Util.gunwoodparts.size() - 1)))
					.setQuality(20).setWeight(5);
			LootPool.Builder builder = new LootPool.Builder().name("oldguns_piratepistol").add(gunpart)
					.when(RandomChance.randomChance(0.35f)).setRolls(new RandomValueRange(1, 2)).bonusRolls(0, 1);
			event.getTable().addPool(builder.build());
		}
	}

	public static Minecraft getMc() {
		return Minecraft.getInstance();
	}

	@Override
	public void init() {
		RenderingEntityRegister.RenderingEntityRegister();

	}

	/////////////////////////////////// Fov Events

	public void onFov(EntityViewRenderEvent.FOVModifier m) {
		Minecraft mc = Minecraft.getInstance();
		AnimationManager man = manager.get(mc.player.getUUID());
		if (man == null)
			return;

		if (man.aimprogressconv != 0) {
			PlayerEntity player = mc.player;
			if (player != null) {
				if (isAvailable(player)) {
					m.setFOV(MathHelper.lerp(man.aimprogressconv, mc.options.fov,
							player.getMainHandItem().getOrCreateTag().getBoolean("scope") ? 30 : 50));
				}
			}
		}
	}

	/////////////////////////////////// Input Events

	public void onMouseClick(MouseInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (event.getAction() == 1 && !mc.isPaused() && mc.screen == null && mc.isWindowActive()) {
			PlayerEntity player = mc.player;
			if (player == null) {
				return;
			}
			if (event.getButton() == 0) {
				System.out.println(player.getMainHandItem().getOrCreateTag().toString());
				if (player.getMainHandItem().getItem() instanceof ItemGun) {
					ItemStack stack = player.getMainHandItem();
					ItemGun gun = (ItemGun) stack.getItem();
					AnimationManager man = manager.get(player.getUUID());
					if (man == null)
						return;
					ShootHandler sh = shoot.get(player.getUUID());
					if (sh == null)
						return;
					sh.tryShoot(stack, player, man);
				}
			}

		}
	}

	public void onMouseScroll(InputEvent.MouseScrollEvent e) {
		AnimationManager man = manager.get(Minecraft.getInstance().player.getUUID());
		if (man != null) {
			if (man.start && Minecraft.getInstance().player.getMainHandItem().getItem() instanceof ItemGun) {
				e.setCanceled(true);
			} else if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof ItemGun) {
				man.reset();
				man.resetShoot();
			}

		}

	}

	public void onKeyboardInput(KeyPressedEvent e) {
		if (e.action == 1 && Minecraft.getInstance().screen == null) {
			System.out.println(e.key);
			AnimationManager man = manager.get(Minecraft.getInstance().player.getUUID());
			if (e.key >= 48 && e.key <= 57 || e.key == 81) {
				if (man != null) {
					if (man.start && Minecraft.getInstance().player.getMainHandItem().getItem() instanceof ItemGun) {
						e.setCanceled(true);
					} else if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof ItemGun) {
						man.reset();
						man.resetShoot();
					}
				}
			}
			if (man != null) {
				if (man.start) {
					if (Minecraft.getInstance().options.keyJump.getKey().getValue() == e.key) {
						e.setCanceled(true);
					}
					if (Minecraft.getInstance().options.keySprint.getKey().getValue() == e.key) {
						e.setCanceled(true);
					}
				}

			}
		}
	}

	/////////////////////////////////// Model laoder

	public void registerModels(ModelRegistryEvent e) {
		ModelLoader.addSpecialModel(new ModelResourceLocation(
				ItemRegistries.pirate_pistol.get().getRegistryName() + "_hammer_down", "inventory"));
		ModelLoader.addSpecialModel(new ModelResourceLocation("oldguns:scope", "inventory"));
		ModelLoader.addSpecialModel(new ModelResourceLocation("oldguns:guns/kark/kark_hammer", "inventory"));
	}

	/////////////////////////////////// Inventory

	/////////////////////////////////// Render Event

	public void renderHand(RenderHandEvent e) {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null) {
			if (player.getMainHandItem().getItem() instanceof ItemGun) {
				AbstractClientPlayerEntity aplayer = (AbstractClientPlayerEntity) player;
				ResourceLocation skinLoc = aplayer.getSkinTextureLocation();
				IVertexBuilder builder = e.getBuffers().getBuffer(RenderType.entityCutout(skinLoc));

				e.setCanceled(true);

				MatrixStack matrix = e.getMatrixStack();
				int light = e.getLight();
				ItemStack stack = player.getMainHandItem();

				Impl buffer = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
				int blockLight = player.isOnFire() ? 15
						: player.level.getLightEmission(new BlockPos(player.getEyePosition(e.getPartialTicks())));

				ItemGun gun = (ItemGun) stack.getItem();
				IBakedModel basemodel = gun.model;
				IBakedModel hammer = gun.hammer;
				boolean scopeb = gun.getNBTScope(stack);

				AnimationManager man = manager.get(player.getUUID());
				if (man == null)
					return;

				if ((man.aiming && gun.getNBTScope(stack) && man.aimprogress >= 2))
					return;

				// render right hand
				matrix.pushPose();
				matrix.translate(-0.28f, 0.28, 0.76);

				if (gun.isBoth()) {// y -0.02
					matrix.translate(0, -0.02f, 0.44f);
					matrix.mulPose(new Quaternion(0, 2, 0, true));
				}

				if (debug) {
					matrix.translate(atx, aty, atz);
					matrix.mulPose(new Quaternion(arx, ary, arz, true));
				}
				man.animRightArm(matrix);
				man.animShootRightArm(matrix);
				man.animRecoilRightArm(matrix);

				man.animRightHandAim(matrix, scopeb);
				man.animRightHandSprint(matrix);
				gun.configMatrixRightHand(matrix);

				RenderUtil.renderRightPlayerArm(matrix, player.getMainHandItem(), buffer, light, player.xRot,
						-2.9802322E-8f, 0.28f, 0.07999997f, -12, -24, -28, builder);
				matrix.popPose();

				// render left hand
				matrix.pushPose();
				matrix.translate(-0.28f, 0.28, 0.76);

				man.animLeftArm(matrix);

				if (debug) {
					matrix.translate(latx, laty, latz);
					matrix.mulPose(new Quaternion(larx, lary, larz, true));
				}
				man.animRecoilLeftArm(matrix);

				man.animShootLeftArm(matrix);

				gun.configMatrixLeftHand(matrix);

				man.animLeftHandAim(matrix, scopeb);

				man.animLeftHandSprint(matrix);

				if (gun.isBoth()) {
					matrix.translate(1.18f, 0, -3.36f);
					matrix.mulPose(new Quaternion(0, -20, 0, true));
				}
				matrix.translate(1.58f, 0.32f, 1.5f);
				matrix.translate(-1.18f, -1.1f, 2.14f);
				matrix.mulPose(new Quaternion(24, -32, 0, true));
				matrix.translate(-1.28f, 1.12f, 2.24f);
				matrix.translate(-0.96f, 0.56f, 2.26f);
				matrix.mulPose(new Quaternion(0, 12, 0, true));

				RenderUtil.renderLeftPlayerArm(matrix, player.getMainHandItem(), buffer, light, player.xRot,
						-2.9802322E-8f, 0.28f, 0.07999997f, 0/* ,tx, ty, tz */, -12, -24, -28, builder);
				matrix.popPose();
				buffer.endBatch();

				// render gun
				matrix.pushPose();

				RenderUtil.setupModel(matrix, basemodel);

				if (debug) {
					matrix.translate(tx, ty, tz);
					matrix.mulPose(new Quaternion(rx, ry, rz, true));
				}

				man.animGunAim(matrix, scopeb);

				man.animGunSprint(matrix);

				man.animShootGun(matrix);
				man.animRecoilGun(matrix);
				man.animGun(matrix);

				gun.configMatrixGun(matrix);
				if (gun.isBoth()) {
					matrix.translate(-0.27f, 0.12f, 0.2f);
				}

				RenderUtil.renderItemStack(player, matrix, e.getBuffers(), light);
				matrix.popPose();

				// render hammer

				if (gun.hasHammer()) {

					if (hammer != null) {
						matrix.pushPose();

						RenderUtil.setupModel(matrix, basemodel);

						matrix.translate(-0.68f, -0.6f, 0.08f);
						matrix.translate(-0.01f, -0.04f, -0.04f);

						man.animHammer(matrix);

						man.animHammerAim(matrix, scopeb);
						man.animHammerSprint(matrix);
						man.animShootHammer(matrix);
						man.animRecoilHammer(matrix);

						if (debug) {
							matrix.translate(htx, hty, htz);
							matrix.mulPose(new Quaternion(hrx, hry, hrz, true));
						}
						gun.configMatrixHammer(matrix, stack);
						if (gun instanceof PirateRifle) {/////// z -0.2f
							matrix.translate(-0.1f, 0, -0.02f);
						}

						RenderUtil.renderModel(hammer, matrix, stack, e.getBuffers(), light, OverlayTexture.NO_OVERLAY);
						matrix.popPose();

					}

				}

				/////// render scope

				if (scopeb) {

					if (scope != null) {
						matrix.pushPose();

						RenderUtil.setupModel(matrix, basemodel);

						gun.configMatrixScope(matrix);
						man.animScope(matrix);
						man.animScopeAim(matrix, scopeb);
						man.animScopeSprint(matrix);
						man.animRecoilScope(matrix);

						if (debug) {
							matrix.translate(htx, hty, htz);
							matrix.mulPose(new Quaternion(hrx, hry, hrz, true));
						}
						/////////////////

						matrix.translate(-0.002f, -0.024f, 0);
						matrix.translate(0.356f, -1.1080f, 0);
						matrix.mulPose(new Quaternion(6, 0, 0, true));

						matrix.scale(2.8480f, 2.5f, 1);
						matrix.translate(-0.48f, -0.171f, 1.426f);

						matrix.mulPose(new Quaternion(-92, 0, 0, true));
						gun.configMatrixGun(matrix);
						if (gun.isBoth()) {
							matrix.translate(-0.27f, 0.12f, 0.2f);
						}

						RenderUtil.renderModel(scope, matrix, stack, e.getBuffers(), light, OverlayTexture.NO_OVERLAY);

						matrix.popPose();
					}

				}

			}
		}
	}

	public static void drawDrawFullscreenImage(ResourceLocation rl, int texWidth, int texHeight, MainWindow sr) {
		RenderSystem.pushMatrix();

		RenderSystem.enableBlend();
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.color4f(1F, 1F, 1F, 1F);
		RenderSystem.disableAlphaTest();

		Minecraft.getInstance().getTextureManager().bind(rl);

		double x = sr.getGuiScaledWidth();
		double y = sr.getGuiScaledHeight();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder b = tessellator.getBuilder();

		b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		b.vertex(x * 0.5D - 2 * y, y, -90D).uv(0F, 1F).endVertex();
		b.vertex(x * 0.5D + 2 * y, y, -90D).uv(1F, 1F).endVertex();
		b.vertex(x * 0.5D + 2 * y, 0D, -90D).uv(1F, 0F).endVertex();
		b.vertex(x * 0.5D - 2 * y, 0D, -90D).uv(0F, 0F).endVertex();

		tessellator.end();

		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.enableAlphaTest();

		RenderSystem.popMatrix();
	}

	public static boolean isAvailable(PlayerEntity player) {
		return (player != null && player.isAlive()) && !player.isSpectator();
	}

	public void drawOverlay(RenderGameOverlayEvent.Pre e) {
		Minecraft minecraft = Minecraft.getInstance();
		PlayerEntity player = minecraft.player;

		if (isAvailable(player)) {
			ItemStack stack = player.getMainHandItem();
			if (stack.getItem() instanceof ItemGun) {
				ItemGun gun = ((ItemGun) stack.getItem());
				AnimationManager man = manager.get(minecraft.player.getUUID());
				if (man == null)
					return;

				if (e.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
					e.setCanceled(true);
					if (man.renderCrosshair) {
						RenderUtil.drawHitmarker(e.getMatrixStack());
						man.renderCrosshair = false;
					}
				}

				if (!man.aiming || !gun.getNBTScope(stack) || man == null) {
					return;
				}
				if (e.getType() == RenderGameOverlayEvent.ElementType.ALL && isAvailable(minecraft.player)
						&& minecraft.isWindowActive() && minecraft.options.getCameraType() == PointOfView.FIRST_PERSON
						&& minecraft.screen == null && man.aimprogress >= MAXAIMTICKS) {
					drawDrawFullscreenImage(scopeimage, 1024, 256, e.getWindow());

				}

			}

		}
	}

	public void drawOverlayPost(RenderGameOverlayEvent.Post e) {
		Minecraft minecraft = Minecraft.getInstance();
		PlayerEntity player = minecraft.player;

		if (isAvailable(player)) {
			ItemStack stack = player.getMainHandItem();
			if (stack.getItem() instanceof ItemGun) {
				ItemGun gun = ((ItemGun) stack.getItem());

				if (e.getType() == ElementType.TEXT) {
					if (true) {//////////// temporal
						Minecraft mc = Minecraft.getInstance();// 100 180
						mc.font.draw(e.getMatrixStack(), gun.getNBTBullets(stack) + "/" + gun.getMaxAmmo(),
								tx == 0 ? mc.getWindow().getGuiScaledWidth() / 7 : tx,
								ty == 0 ? mc.getWindow().getGuiScaledHeight() / 1.5f : ty, 0xFFFFFF);
					}
				}

			}

		}
	}

	public void renderItemTooltip(RenderTooltipEvent.PostText e) {
		if (e.getStack().getItem() instanceof GunPartMold) {
			String res = e.getStack().getOrCreateTag().getString("res_loc");
			if (res != "") {
				Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(res));
				if (item != null) {
					RenderSystem.pushMatrix();
					RenderSystem.translatef(0, 0, 306);
					RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
					RenderUtil.drawTooltipBox(e.getX() - 4, e.getY() + e.getHeight() + 4, 20, 14, 0xF0b5b5b5);
					RenderUtil.renderStack(e.getX() - 2, e.getY() + e.getHeight() + 2, new ItemStack(item));
					RenderSystem.popMatrix();
				}
			}
		}
	}

	/////////////////////////////////// Tick Event

	public void clientTick(TickEvent.ClientTickEvent e) {
		if (e.phase != Phase.START)
			return;
		PlayerEntity player = Minecraft.getInstance().player;
		if (player == null) {
			return;
		}

		if (!player.isAlive()) {
			return;
		}

		if (!(player.getMainHandItem().getItem() instanceof ItemGun)) {
			return;
		}

		ItemStack stack = player.getMainHandItem();
		ItemGun gun = ((ItemGun) stack.getItem());

		AnimationManager man = manager.get(player.getUUID());
		if (man == null)
			return;
		if (!Minecraft.getInstance().isPaused()) {
			man.getAnimForGun(gun.getRegistryName().toString());
			man.tick();
		}

		if (RELOAD.consumeClick()) {
			if (man.aimprogress != 0)
				return;
			if (gun.isPrimitive()) {
				int gunpowder = InventoryHelper.findTotalGunPowder(player.inventory);
				int bullets = InventoryHelper.findTotalBullets(player.inventory, true);
				if (gun.getNBTBullets(stack) >= 0 && gun.getNBTBullets(stack) < gun.getMaxAmmo()
						&& bullets > (gun.isShotgun() ? 6 : 0) && gunpowder > (gun.isShotgun() ? 1 : 0)
						|| player.isCreative()) {
					System.out.println("starting");
					if (manager.get(Minecraft.getInstance().player.getUUID()) != null) {
						manager.get(Minecraft.getInstance().player.getUUID()).reset();
						manager.get(Minecraft.getInstance().player.getUUID()).setStart(true, gun.getNBTBullets(stack),
								bullets >= gun.getMaxAmmo() ? gun.getMaxAmmo() : bullets, gun.getMaxAmmo(),
								EnchUtil.getFastReloadLevel(stack));

					}
				}
			} else {
				int bullets = InventoryHelper.findTotalBullets(player.inventory, false);
				if (gun.getNBTBullets(stack) >= 0 && gun.getNBTBullets(stack) < gun.getMaxAmmo() && bullets > 0) {
					if (manager.get(Minecraft.getInstance().player.getUUID()) != null) {
						manager.get(Minecraft.getInstance().player.getUUID()).reset();
						System.out.println("starting");
						manager.get(Minecraft.getInstance().player.getUUID()).setStart(true, gun.getNBTBullets(stack),
								bullets >= gun.getMaxAmmo() ? gun.getMaxAmmo() : bullets, gun.getMaxAmmo(),
								EnchUtil.getFastReloadLevel(stack));

					}
				}
			}
		}

		if (player.isSprinting() && !man.start) {
			if (man.sprintprogress < MAXSPRINTTICKS) {
				man.sprintprogress++;
			}
		} else if (!man.start) {
			if (man.sprintprogress > 0) {
				man.sprintprogress--;
			}
		}

		man.sprintprogressconv = man.sprintprogress / MAXSPRINTTICKS;

		if (man.aiming && !man.start && !player.isSprinting()) {
			if (man.aimprogress < MAXAIMTICKS) {
				man.aimprogress++;
			}
		} else if (!man.start) {
			if (man.aimprogress > 0) {
				man.aimprogress--;
			}
		}

		man.aimprogressconv = man.aimprogress / MAXAIMTICKS;

		if (GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(),
				GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS && !player.isSprinting()
				&& Minecraft.getInstance().screen == null) {
			man.aiming = true;
		} else {
			if (!player.isSprinting()) {
				man.aiming = false;
			}
		}

		if (ATT.consumeClick()) {
			if (player.level.isClientSide && gun.hasScope()) {
				OldGuns.channel.sendToServer(new setScopeMessage(Config.CLIENT.useoffhandsystem.get()));
			}
		}

		if (debug) {

			if (XSCALE.consumeClick()) {
				if (!rotate) {
					sx += stepscale;
					System.out.println("sx: " + sx + " sy: " + sy + " sz: " + sz);
				} else {
					sx -= stepscale;
					System.out.println("sx: " + sx + " sy: " + sy + " sz: " + sz);
				}
			}

			if (YSCALE.consumeClick()) {
				if (!rotate) {
					sy += stepscale;
					System.out.println("sx: " + sx + " sy: " + sy + " sz: " + sz);
				} else {
					sy -= stepscale;
					System.out.println("sx: " + sx + " sy: " + sy + " sz: " + sz);
				}

			}

			if (ZSCALE.consumeClick()) {
				if (!rotate) {
					sz += stepscale;
					System.out.println("sx: " + sx + " sy: " + sy + " sz: " + sz);
				} else {
					sz -= stepscale;
					System.out.println("sx: " + sx + " sy: " + sy + " sz: " + sz);
				}

			}

			if (LEFT.consumeClick()) {
				if (!arm) {
					if (!rotate) {
						if (!hammer) {
							tx -= stepmodel;
							System.out.println("tx: " + tx + " ty: " + ty + " tz: " + tz);
						} else {
							htx -= stepmodel;
							System.out.println("htx: " + htx + " hty: " + hty + " htz: " + htz);
						}
					} else {
						if (!hammer) {
							rx -= 2f;
							System.out.println("rx: " + rx + " ty: " + ry + " rz: " + rz);
						} else {
							hrx -= 2f;
							System.out.println("hrx: " + hrx + " hry: " + hry + " hrz: " + hrz);
						}
					}
				} else {
					if (!rotate) {
						if (right) {
							atx -= stepmodel;
							System.out.println("tx: " + atx + " ty: " + aty + " tz: " + atz);
						} else {
							latx -= stepmodel;
							System.out.println("ltx: " + latx + " lty: " + laty + " ltz: " + latz);
						}
					} else {
						if (right) {
							arx -= 2f;
							System.out.println("rx: " + arx + " ty: " + ary + " rz: " + arz);
						} else {
							larx -= 2f;
							System.out.println("ltx: " + larx + " lty: " + lary + " ltz: " + larz);
						}
					}
				}
			} else if (RIGHT.consumeClick()) {
				if (!arm) {
					if (!rotate) {
						if (!hammer) {
							tx += stepmodel;
							System.out.println("tx: " + tx + " ty: " + ty + " tz: " + tz);
						} else {
							htx += stepmodel;
							System.out.println("htx: " + htx + " hty: " + hty + " htz: " + htz);
						}
					} else {
						if (!hammer) {
							rx += 2f;
							System.out.println("rx: " + rx + " ty: " + ry + " rz: " + rz);
						} else {
							hrx += 2f;
							System.out.println("hrx: " + hrx + " hry: " + hry + " hrz: " + hrz);
						}
					}
				} else {
					if (!rotate) {
						if (right) {
							atx += stepmodel;
							System.out.println("tx: " + atx + " ty: " + aty + " tz: " + atz);
						} else {
							latx += stepmodel;
							System.out.println("ltx: " + latx + " lty: " + laty + " ltz: " + latz);
						}
					} else {
						if (right) {
							arx += 2f;
							System.out.println("rx: " + arx + " ty: " + ary + " rz: " + arz);
						} else {
							larx += 2f;
							System.out.println("ltx: " + larx + " lty: " + lary + " ltz: " + larz);
						}
					}
				}
			}

			if (UP.consumeClick()) {
				if (!arm) {
					if (!rotate) {
						if (!hammer) {
							ty -= stepmodel;
							System.out.println("tx: " + tx + " ty: " + ty + " tz: " + tz);
						} else {
							hty -= stepmodel;
							System.out.println("htx: " + htx + " hty: " + hty + " htz: " + htz);
						}
					} else {
						if (!hammer) {
							ry -= 2f;
							System.out.println("rx: " + rx + " ty: " + ry + " rz: " + rz);
						} else {
							hry -= 2f;
							System.out.println("hrx: " + hrx + " hry: " + hry + " hrz: " + hrz);
						}
					}
				} else {
					if (!rotate) {
						if (right) {
							aty -= stepmodel;
							System.out.println("tx: " + atx + " ty: " + aty + " tz: " + atz);
						} else {
							laty -= stepmodel;
							System.out.println("ltx: " + latx + " lty: " + laty + " ltz: " + latz);
						}
					} else {
						if (right) {
							ary -= 2f;
							System.out.println("rx: " + arx + " ty: " + ary + " rz: " + arz);
						} else {
							lary -= 2f;
							System.out.println("ltx: " + larx + " lty: " + lary + " ltz: " + larz);
						}
					}
				}
			} else if (DOWN.consumeClick()) {
				if (!arm) {
					if (!rotate) {
						if (!hammer) {
							ty += stepmodel;
							System.out.println("tx: " + tx + " ty: " + ty + " tz: " + tz);
						} else {
							hty += stepmodel;
							System.out.println("htx: " + htx + " hty: " + hty + " htz: " + htz);
						}
					} else {
						if (!hammer) {
							ry += 2f;
							System.out.println("rx: " + rx + " ty: " + ry + " rz: " + rz);
						} else {
							hry += 2f;
							System.out.println("hrx: " + hrx + " hry: " + hry + " hrz: " + hrz);
						}
					}
				} else {
					if (!rotate) {
						if (right) {
							aty += stepmodel;
							System.out.println("tx: " + atx + " ty: " + aty + " tz: " + atz);
						} else {
							laty += stepmodel;
							System.out.println("ltx: " + latx + " lty: " + laty + " ltz: " + latz);
						}
					} else {
						if (right) {
							ary += 2f;
							System.out.println("rx: " + arx + " ty: " + ary + " rz: " + arz);
						} else {
							lary += 2f;
							System.out.println("ltx: " + larx + " lty: " + lary + " ltz: " + larz);
						}
					}
				}
			}

			if (FRONT.consumeClick()) {
				if (!arm) {
					if (!rotate) {
						if (!hammer) {
							tz -= stepmodel;
							System.out.println("tx: " + tx + " ty: " + ty + " tz: " + tz);
						} else {
							htz -= stepmodel;
							System.out.println("htx: " + htx + " hty: " + hty + " htz: " + htz);
						}
					} else {
						if (!hammer) {
							rz -= 2f;
							System.out.println("rx: " + rx + " ty: " + ry + " rz: " + rz);
						} else {
							hrz -= 2f;
							System.out.println("hrx: " + hrx + " hry: " + hry + " hrz: " + hrz);
						}
					}
				} else {
					if (!rotate) {
						if (right) {
							atz -= stepmodel;
							System.out.println("tx: " + atx + " ty: " + aty + " tz: " + atz);
						} else {
							latz -= stepmodel;
							System.out.println("ltx: " + latx + " lty: " + laty + " ltz: " + latz);
						}
					} else {
						if (right) {
							arz -= 2f;
							System.out.println("rx: " + arx + " ty: " + ary + " rz: " + arz);
						} else {
							larz -= 2f;
							System.out.println("ltx: " + larx + " lty: " + lary + " ltz: " + larz);
						}
					}
				}
			} else if (BACK.consumeClick()) {
				if (!arm) {
					if (!rotate) {
						if (!hammer) {
							tz += stepmodel;
							System.out.println("tx: " + tx + " ty: " + ty + " tz: " + tz);
						} else {
							htz += stepmodel;
							System.out.println("htx: " + htx + " hty: " + hty + " htz: " + htz);
						}
					} else {
						if (!hammer) {
							rz += 2f;
							System.out.println("rx: " + rx + " ty: " + ry + " rz: " + rz);
						} else {
							hrz += 2f;
							System.out.println("hrx: " + hrx + " hry: " + hry + " hrz: " + hrz);
						}
					}
				} else {
					if (!rotate) {
						if (right) {
							atz += stepmodel;
							System.out.println("tx: " + atx + " ty: " + aty + " tz: " + atz);
						} else {
							latz += stepmodel;
							System.out.println("ltx: " + latx + " lty: " + laty + " ltz: " + latz);
						}
					} else {
						if (right) {
							arz += 2f;
							System.out.println("rx: " + arx + " ty: " + ary + " rz: " + arz);
						} else {
							larz += 2f;
							System.out.println("ltx: " + larx + " lty: " + lary + " ltz: " + larz);
						}
					}
				}
			}

			if (CHANGETYPE.consumeClick()) {
				rotate = !rotate;
			}

			if (CHANGEHAND.consumeClick()) {
				arm = !arm;
			}

			if (CHANGEHANDSIDE.consumeClick()) {
				right = !right;
			}

			if (CHANGEHAMMER.consumeClick()) {
				hammer = !hammer;
			}

		}

	}

	/////////////////////////////////// Player Events

	public void onPlayerChangeItem(PlayerTickEvent e) {
		if (e.phase != Phase.START)
			return;
		PlayerEntity player = Minecraft.getInstance().player;

		if (player == null) {
			return;
		}

		if (!player.isAlive()) {
			return;
		}

		if (!(player.getMainHandItem().getItem() instanceof ItemGun)) {
			return;
		}

		ItemStack stack = player.getMainHandItem();
		ItemGun gun = ((ItemGun) stack.getItem());

		////////////////////////////////// Attachment

		////////////////////////////////// Attachment

	}

	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e) {
		manager.put(e.getPlayer().getUUID(), new AnimationManager());
		shoot.put(e.getPlayer().getUUID(), new ShootHandler());
		scope = Minecraft.getInstance().getModelManager().getModel(scoperes);
		for (Item item : Util.guns) {
			((ItemGun) item)
					.setupModel(Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(item));
		}
	}

//////////////////////////////////////////////////////////////////////

	@Override
	public PlayerEntity getPlayerFromContext(Context context) {
		return context.getSender();
	}

	@OnlyIn(Dist.CLIENT)
	public static PlayerRenderer getRenderPlayer(AbstractClientPlayerEntity player) {
		Minecraft mc = Minecraft.getInstance();
		EntityRendererManager manager = mc.getEntityRenderDispatcher();
		return manager.getSkinMap().get("default");
	}

	@OnlyIn(Dist.CLIENT)
	public static PlayerModel<AbstractClientPlayerEntity> getPlayerModel(AbstractClientPlayerEntity player) {
		return getRenderPlayer(player).getModel();
	}

}
