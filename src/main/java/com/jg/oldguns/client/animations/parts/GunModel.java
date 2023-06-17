package com.jg.oldguns.client.animations.parts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Animator;
import com.jg.oldguns.client.animations.RepetitiveAnimation;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.render.RenderHelper;
import com.jg.oldguns.client.screens.AnimationScreen;
import com.jg.oldguns.guns.FireMode;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.guns.MagItem;
import com.jg.oldguns.network.ShootMessage;
import com.jg.oldguns.utils.InventoryUtils;
import com.jg.oldguns.utils.InventoryUtils.InvData;
import com.jg.oldguns.utils.MathUtils;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Quaternion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class GunModel {

	protected Map<String, Object> data;

	protected ClientHandler client;

	protected GunModelPart[] parts;

	public GunItem gun;

	protected WrapperModel model;

	protected Animator animator;

	protected Animation shootAnim;

	protected GunModelStuff stuff;

	protected boolean shouldUpdateAnimation;
	protected boolean hasChanges;
	protected boolean playAnimation;
	protected boolean debugMode;
	protected boolean init;

	public GunModel(GunModelPart[] gunModelParts, Item gun, ClientHandler client) {
		data = new HashMap<>();
		this.parts = gunModelParts;
		this.gun = (GunItem) gun;
		this.client = client;
		this.animator = new Animator(this);
		this.stuff = new GunModelStuff(this.gun.getStuff());

		this.hasChanges = true;
		this.playAnimation = true;
		this.shootAnim = new Animation("shootAnim", Utils.getR(gun).toString()).startKeyframe(4).end();
	}

	// Transform

	protected void translateAndRotateAndScale(Transform t, PoseStack matrix) {
		matrix.translate(t.pos[0], t.pos[1], t.pos[2]);
		matrix.mulPose(new Quaternion(t.rot[0], t.rot[1], t.rot[2], false));
		matrix.scale(t.sca[0], t.sca[1], t.sca[2]);
	}

	protected void translateAndRotateAndScaleAndScale(Transform t, PoseStack matrix) {
		matrix.translate(t.pos[0], t.pos[1], t.pos[2]);
		matrix.mulPose(new Quaternion(t.rot[0], t.rot[1], t.rot[2], false));
		matrix.scale(t.sca[0], getKnockback(), getKnockback());
	}

	protected void lerpTransform(PoseStack matrix, float p, Transform t) {
		matrix.translate(Mth.lerp(p, 0, t.pos[0]), Mth.lerp(p, 0, t.pos[1]), Mth.lerp(p, 0, t.pos[2]));
		matrix.mulPose(new Quaternion(MathUtils.rotLerp(p, 0, t.rot[0]), MathUtils.rotLerp(p, 0, t.rot[1]),
				MathUtils.rotLerp(p, 0, t.rot[2]), false));
	}

	protected void lerpGunStuff(PoseStack matrix, ItemStack stack, int aim, int sprint, int cooldown) {
		lerpTransform(matrix, client.getAimHandler().getProgress(), parts[aim].getDTransform());
		lerpTransform(matrix, client.getSprintHandler().getProgress(), parts[sprint].getDTransform());
		lerpTransform(matrix,
				client.getCooldownRecoil().getCooldownPercent(gun, Minecraft.getInstance().getFrameTime()),
				parts[cooldown].getDTransform());
		// lerpTransform(matrix,
		// client.getCooldown().getProgress(NBTUtils.getId(stack)),
		// parts[cooldown].getDTransform());
	}

	protected void leftArm(PoseStack matrix, LocalPlayer player, MultiBufferSource buffer, int light, int leftarm) {
		matrix.pushPose(); // 2+
		renderArm(player, buffer, matrix, light, HumanoidArm.LEFT, parts[leftarm].getCombined());
		matrix.popPose(); // 2-
	}

	protected void leftArmMag(PoseStack matrix, LocalPlayer player, MultiBufferSource buffer, int light, int leftarm,
			int leftarmwithmag) {
		matrix.pushPose(); // 2+
		translateAndRotateAndScale(parts[leftarmwithmag].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.LEFT, parts[leftarm].getCombined());
		matrix.popPose(); // 2-
	}

	protected void gunWithMag(PoseStack matrix, LocalPlayer player, ItemStack stack, MultiBufferSource buffer,
			int light, int mag) {
		renderGunPart(player, stack, buffer, matrix, light);
		if (NBTUtils.hasMag(stack) && renderMag()) {
			matrix.pushPose();
			translateAndRotateAndScale(parts[7].getCombined(), matrix);
			renderItem(player,
					new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(NBTUtils.getMag(stack)))), buffer,
					matrix, light, parts[mag].getCombined());
			matrix.popPose();
		}
	}

	protected void hammer(PoseStack matrix, LocalPlayer player, ItemStack stack, MultiBufferSource buffer, int light,
			int hammer, String path) {
		renderModel(
				player, stack, buffer, matrix, light, Minecraft.getInstance().getItemRenderer().getItemModelShaper()
						.getModelManager().getModel(new ModelResourceLocation(path, "inventory")),
				parts[hammer].getTransform());
	}

	// Gun Methods

	public void shoot(Player player, ItemStack stack) {
		if (gun.getFireMode() == FireMode.SEMI) {
			Utils.spawnParticlesOnPlayerView(player, 50, 0, 0, 0);
		}
		// setAnimation(shootAnim);
		OldGuns.channel.sendToServer(
				new ShootMessage(player.getYRot(), player.getXRot(), gun.getShootSound().getLocation().toString()));
		markChanges();
		LogUtils.getLogger().info(shootAnim.getGunModel());
	}

	public boolean canShoot(Player player, ItemStack stack) {
		return NBTUtils.getAmmo(stack) > 0 || player.isCreative();// && !NBTUtils.getSafe(stack);
	}

	// Rendering

	protected void renderOneHammerNoMag(PoseStack matrix, ItemStack stack, LocalPlayer player, MultiBufferSource buffer,
			int light) {
		matrix.pushPose();
		lerpGunStuff(matrix, stack, 6, 7, 8);
		translateAndRotateAndScale(parts[4].getCombined(), matrix);
		leftArm(matrix, player, buffer, light, 1);
		matrix.pushPose();
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		renderGunPart(player, stack, buffer, matrix, light);
		hammer(matrix, player, stack, buffer, light, 3, stuff.hammers[0]);
		matrix.popPose();
		matrix.popPose();
	}

	protected void renderAll(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix, int light,
			int rightarm, int leftarm, int gun, int hammer, int mag, int all, int allminusleft, int leftarmmag, int aim,
			int sprint, int recoil) {
		matrix.pushPose();
		if (aim != -1) {
			lerpTransform(matrix, client.getAimHandler().getProgress(), parts[aim].getDTransform());
		}
		if (sprint != -1) {
			lerpTransform(matrix, client.getSprintHandler().getProgress(), parts[sprint].getDTransform());
		}
		if (recoil != -1) {
			lerpTransform(matrix, client.getRecoilHandler().getProgress(), parts[recoil].getDTransform());
		}
		if (all != -1) {
			translateAndRotateAndScaleAndScale(parts[all].getCombined(), matrix);
		}
		if (leftarm != -1) {
			matrix.pushPose();
			translateAndRotateAndScale(parts[leftarmmag].getCombined(), matrix);
			renderArm(player, buffer, matrix, light, HumanoidArm.LEFT, parts[1].getCombined());
			matrix.pushPose();
			translateAndRotateAndScale(parts[allminusleft].getCombined(), matrix);
			renderItem(player, stuff.mag, buffer, matrix, light, parts[mag].getCombined());
			matrix.popPose();
			matrix.popPose();
		}
		matrix.pushPose();
		translateAndRotateAndScale(parts[allminusleft].getCombined(), matrix);
		if (rightarm != -1) {
			renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		}
		if (gun != -1) {
			renderGunPart(player, stack, buffer, matrix, light);
			if (hammer != -1) {
				matrix.pushPose();
				translateAndRotateAndScale(parts[3].getCombined(), matrix);
				for (String hammerP : stuff.hammers) {
					renderModel(player, stack, buffer, matrix, light,
							Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager()
									.getModel(new ModelResourceLocation(hammerP, "inventory")));
				}
				matrix.popPose();
			}
		}
		matrix.popPose();
		matrix.popPose();
	}

	protected void renderAll(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix,
			int light) {
		matrix.pushPose();
		lerpTransform(matrix, client.getAimHandler().getProgress(), parts[8].getDTransform());
		lerpTransform(matrix, client.getSprintHandler().getProgress(), parts[9].getDTransform());
		lerpTransform(matrix, client.getRecoilHandler().getProgress(), parts[10].getDTransform());
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		matrix.pushPose();
		parts[1].getDTransform().setScale(1.3f, 2.5f, 1.3f);
		translateAndRotateAndScale(parts[7].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.LEFT, parts[1].getCombined());
		if (NBTUtils.hasMag(stack) && renderMag()) {
			// LogUtils.getLogger().info("HasMag: " + NBTUtils.getMag(stack));
			matrix.pushPose();
			translateAndRotateAndScale(parts[6].getCombined(), matrix);
			renderItem(player,
					new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(NBTUtils.getMag(stack)))), buffer,
					matrix, light, parts[4].getCombined());
			matrix.popPose();
		}
		matrix.popPose();
		matrix.pushPose();
		translateAndRotateAndScale(parts[6].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		renderGunPart(player, stack, buffer, matrix, light);
		matrix.pushPose();
		// translateAndRotateAndScale(parts[3].getCombined(), matrix);
		// LogUtils.getLogger().info(parts[3].getCombined().toString());
		for (String hammerP : stuff.hammers) {
			renderModel(player, stack, buffer, matrix, light, Minecraft.getInstance().getItemRenderer()
					.getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(hammerP, "inventory")));
		}
		matrix.popPose();
		matrix.popPose();
		matrix.popPose();
	}

	protected void renderGunPart(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix,
			int light) {
		translateAndRotateAndScale(parts[2].getCombined(), matrix);
		Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stuff.getBarrel(),
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light);
		Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stuff.getBody(),
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light);
		Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stuff.getStock(),
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light);
	}

	protected void renderItem(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix,
			int light) {
		matrix.pushPose();
		Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stack,
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light);
		matrix.popPose();
	}

	protected void renderItem(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix,
			int light, Transform transform) {
		matrix.pushPose();
		translateAndRotateAndScale(transform, matrix);
		Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stack,
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light);
		matrix.popPose();
	}

	protected void renderModel(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix,
			int light, BakedModel model) {
		matrix.pushPose();
		/*
		 * VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer,
		 * Sheets.translucentItemSheet(), true, stack.hasFoil());
		 * Minecraft.getInstance().getItemRenderer() .renderModelLists(model, stack,
		 * light, OverlayTexture.NO_OVERLAY, matrix, builder);
		 */
		Minecraft.getInstance().getItemRenderer().render(stack, TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix,
				buffer, light, OverlayTexture.NO_OVERLAY, model);
		matrix.popPose();
	}

	protected void renderModel(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix,
			int light, BakedModel model, Transform transform) {
		matrix.pushPose();
		translateAndRotateAndScale(transform, matrix);
		/*
		 * VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer,
		 * Sheets.translucentItemSheet(), true, stack.hasFoil());
		 * Minecraft.getInstance().getItemRenderer() .renderModelLists(model, stack,
		 * light, OverlayTexture.NO_OVERLAY, matrix, builder);
		 */
		Minecraft.getInstance().getItemRenderer().render(stack, TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix,
				buffer, light, OverlayTexture.NO_OVERLAY, model);
		matrix.popPose();
	}

	protected void renderArm(LocalPlayer player, MultiBufferSource buffer, PoseStack matrix, int light,
			HumanoidArm arm) {
		matrix.pushPose();
		RenderHelper.renderPlayerArm(matrix, buffer, light, 0, 0, arm);
		matrix.popPose();
	}

	protected void renderArm(LocalPlayer player, MultiBufferSource buffer, PoseStack matrix, int light, HumanoidArm arm,
			Transform transform) {
		matrix.pushPose();
		translateAndRotateAndScale(transform, matrix);
		RenderHelper.renderPlayerArm(matrix, buffer, light, 0, 0, arm);
		matrix.popPose();
	}

	// Misc

	// Reloading

	public void fillReloadData(String bulletSize, Player player, ItemStack stack, Animation magByMag, Animation noMag) {
		int index = ServerUtils.getIndexForCorrectMag(player.getInventory(), gun.getGunId(), bulletSize);
		if (index != -1) {
			data.put("index", index);
			LogUtils.getLogger().info("index: " + index);
			ItemStack mag = getMagStack(index);
			data.put("mag", mag);
			data.put("magBullets", mag.getOrCreateTag().getInt(NBTUtils.BULLETS));
			if (NBTUtils.hasMag(stack)) {
				setAnimation(magByMag);
			} else {
				setAnimation(noMag);
			}
		}
	}

	public int fillReloadDataNoMag(Item bullet, Player player, RepetitiveAnimation reload, ItemStack stack,
			int maxAmmo) {
		InvData data = InventoryUtils.getTotalCountAndIndexForItem(player, bullet, maxAmmo - NBTUtils.getAmmo(stack));
		int times = 0;
		if (data.getTotal() > 0) {
			GunModel.this.data.put("data", data.getData());
			InventoryUtils.consumeItems(player, data.getData());
			times = data.getTotal();
			reload.setTimes(times);
			setAnimation(reload);
		}
		return times;
	}

	public void reloadMagByMagStuff() {
		ReloadHandler.restoreMag(getMPath(), ServerUtils.getBullets(Utils.getStack()));
		ReloadHandler.setBullets((int) data.get("magBullets"));
		ReloadHandler.removeItem((int) data.get("index"), 1);
	}

	public void reloadNoMagStuff() {
		MagItem magItem = getMagItem((int) data.get("index"));
		ReloadHandler.setMag(this, magItem.getMaxAmmo(), true, getMBPath((int) data.get("index")), magItem);
		ReloadHandler.removeItem((int) data.get("index"), 1);
		ReloadHandler.setBullets((int) data.get("magBullets"));
	}

	public void getOutMagStuff() {
		ReloadHandler.restoreMag(getMPath(), ServerUtils.getBullets(Utils.getStack()));
		ReloadHandler.setMag(this, 0, false, "", "");
		ReloadHandler.setBullets(0);
	}

	public MagItem getMagItem(int index) {
		return (MagItem) Minecraft.getInstance().player.getInventory().getItem(index).getItem();
	}

	public ItemStack getMagStack(int index) {
		return Minecraft.getInstance().player.getInventory().getItem(index);
	}

	public String getMBPath(int index) {
		return NBTUtils.getMagBullet(getMagStack(index));
	}

	public String getMPath() {
		return NBTUtils.getMag(Utils.getStack());
	}

	public void updateGunParts(Player player) {
		ItemStack stack = player.getMainHandItem();
		this.stuff.setBarrel(
				new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(NBTUtils.getBarrel(stack)))));
		this.stuff
				.setBody(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(NBTUtils.getBody(stack)))));
		this.stuff.setStock(
				new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(NBTUtils.getStock(stack)))));
		this.stuff.setMag(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(NBTUtils.getMag(stack)))));
		this.stuff.setHammers(NBTUtils.getHammers(stack));
	}

	public GunModelPart getPart(String name) {
		for (GunModelPart part : parts) {
			if (part.getName().equals(name)) {
				return part;
			}
		}
		return null;
	}

	public void markChanges() {
		hasChanges = true;
		LogUtils.getLogger().info("Mark changes");
	}

	public boolean isRepTick(int start, float target, float tick, int cycleDur, int times) {
		float t = tick - start;
		int current = (int) Math.floor(t / cycleDur);
		target = target - start;
		return tick > start && current < times && t - (current * cycleDur) == target;
	}

	// Getters and setters

	public GunModelPart[] getParts() {
		return parts;
	}

	public boolean renderMag() {
		return true;
	}

	public void setAnimation(Animation anim) {
		if (getAnimation() == null) {
			this.animator.setAnimation(anim);
			if (Minecraft.getInstance().screen instanceof AnimationScreen) {
				AnimationScreen screen = (AnimationScreen) Minecraft.getInstance().screen;
				screen.initKeyframes();
			}
		}
	}

	public Animation getAnimation() {
		return animator.getAnimation();
	}

	public boolean canPlayAnimation() {
		return playAnimation;
	}

	public void setPlayAnimation(boolean playAnimation) {
		this.playAnimation = playAnimation;
	}

	public boolean shouldUpdateAnimation() {
		return shouldUpdateAnimation;
	}

	public void setShouldUpdateAnimation(boolean shouldUpdateAnimation) {
		this.shouldUpdateAnimation = shouldUpdateAnimation;
	}

	public boolean isDebugModeEnabled() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public Animator getAnimator() {
		return animator;
	}

	public WrapperModel getModel() {
		return model;
	}

	public void setModel(WrapperModel model) {
		this.model = model;
	}

	public GunModelStuff getStuff() {
		return stuff;
	}

	// Abstract methods

	public void tick(Player player, ItemStack stack) {
		animator.update();
	}

	public abstract float getKnockback();

	public abstract void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix,
			int light);

	public abstract void reload(Player player, ItemStack stack);

	public abstract Animation getLookAnimation();

	public abstract Animation getKickbackAnimation();

	public abstract Animation getGetOutMagAnimation();

	public abstract WrapperModel getModifiedModel(BakedModel origin);

	public abstract List<GunModelPart> getGunParts();

	public abstract GunModelPart getGunModelPart();

}
