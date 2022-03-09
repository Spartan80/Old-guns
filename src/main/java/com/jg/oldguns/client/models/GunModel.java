package com.jg.oldguns.client.models;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.animations.AnimationHandler;
import com.jg.oldguns.animations.Animator;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.RecoilHandler;
import com.jg.oldguns.debug.AnimWriter;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class GunModel {

	/*
	 * Reload types
	 * 
	 * R0 = Reload empty mag with full mag no bullet on room
	 * 
	 * R1 = Reload mag with another mag bullet on room
	 * 
	 * R2 = Reload nothing with full mag no bullet on room
	 * 
	 * R3 = Reload nothing with new mag bullet on room or mag empty
	 * 
	 * R4 = Get out the current mag
	 * 
	 * End
	 */

	public static Animation EMPTY = Animation.create(0);

	public static final int RIGHT = 0;
	public static final int LEFT = 1;

	public static final String DEFAULT_SCOPE = "jgguns:special/default_scope";
	
	protected GunModelPart rightarm;
	protected GunModelPart leftarm;
	protected GunModelPart gun;
	protected GunModelPart scope;
	protected GunModelPart hammer;
	protected GunModelPart mag;
	protected GunModelPart aim;

	protected Animation animation;

	protected Animator animator;

	public float animTick;

	protected boolean hasScope;
	protected boolean both;

	public int index;

	public GunModelPart[] parts;

	protected ItemGun gunitem;

	protected int gunbullets, magbullets;
	protected int ammoindex;
	
	protected boolean renderMuzzleFlash;
	private boolean debugMuzzleFlash;

	public GunModel(Item gunitem) {
		rightarm = new GunModelPart("rightarm");
		leftarm = new GunModelPart("leftarm");
		gun = new GunModelPart("gun");
		scope = new GunModelPart("scope");
		hammer = new GunModelPart("hammer");
		mag = new GunModelPart("mag");
		aim = new GunModelPart("aim");
		animator = new Animator();
		this.gunitem = (ItemGun) gunitem;
		this.hasScope = this.gunitem.hasScope();
		this.both = this.gunitem.isBoth();
		initExtraParts();
		parts = getParts();
		setupParts();
		animator.initGunParts(parts);
	}

	public void tick() {
		AnimationHandler.INSTANCE.updateAnimations(this);
		if(AnimWriter.shouldRunSetupParts() || AnimWriter.shouldContinueAnimation()) {
			setupParts();
		}
		animator.update(this);
		setupAnimations();
	}

	public void renderMuzzleFlash(PoseStack stack, BufferSource BufferSource, float partialTicks, ItemStack istack) {
		if (renderMuzzleFlash || debugMuzzleFlash) {
			if(!debugMuzzleFlash) {
				RenderUtil.renderMuzzleFlash(stack, 
					BufferSource, partialTicks, 
					getMuzzleFlashPos(istack).x(), 
					getMuzzleFlashPos(istack).y(), 
					getMuzzleFlashPos(istack).z());
			}else {
				RenderUtil.renderMuzzleFlash(stack, 
						BufferSource, partialTicks, 
						getPart().transform.offsetX, 
						getPart().transform.offsetY, 
						getPart().transform.offsetZ);
			}
			setCanRenderMuzzleFlash(false);
		}
	}
	
	public void render(PoseStack matrix, ItemStack stack, MultiBufferSource itembuffer, BufferSource armbuffer, int light,
			int overlay, float partialTicks, float aimProgress, float sprintProgress, RecoilHandler handler) {
		if (!(ServerUtils.hasScope(stack) && ClientEventHandler.handlers.get(ClientEventHandler.getPlayer().getUUID())
				.getAimingHandler().isFullAimingProgress())) {
			matrix.pushPose();

			handleAim(matrix, aimProgress);
			renderMuzzleFlash(matrix, armbuffer, partialTicks, stack);
			handleSprint(matrix, sprintProgress);

			matrix.translate(0, 0, this.gunitem.handleZRecoil(handler.getRecoil(stack), stack));
			matrix.mulPose(new Quaternion(0,
					0, this.gunitem.handleRHorRecoil(handler.getRecoil(stack), stack), true));
			renderRightArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			if (both) {
				renderLeftArm(matrix, armbuffer, light, Minecraft.getInstance().player);
			}
			armbuffer.endBatch();
			if (hasMultipleParts()) {
				renderBarrel(matrix, stack, itembuffer, light, overlay);
				renderBody(matrix, stack, itembuffer, light, overlay);
				renderStock(matrix, stack, itembuffer, light, overlay);
			} else {
				renderGun(matrix, stack, itembuffer, light, overlay);
			}
			renderHammer(matrix, stack, itembuffer, light, overlay);
			if (canRenderMag(stack)) {
				renderMag(matrix, stack, itembuffer, light, overlay);
			}
			if(hasScope) {
				if(ServerUtils.getScopePath(stack) != "") {
					renderScope(matrix, stack, itembuffer, light, overlay);
				}
			}
			matrix.popPose();
		}
	}

	public void renderGun(PoseStack matrix, ItemStack stack, MultiBufferSource buffer, int light, int overlay) {
		matrix.pushPose();
		Screen s;
		translateRotateForPart(gun, matrix);
		RenderUtil.renderModel(ModelHandler.INSTANCE.getModel(gunitem.getRegistryName().toString()), matrix, stack,
				buffer, light, overlay);
		matrix.popPose();
	}

	public void renderStock(PoseStack matrix, ItemStack stack, MultiBufferSource buffer, int light, int overlay) {
		matrix.pushPose();
		translateRotateForPart(gun, matrix);
		if (ServerUtils.getStock(stack) == "") {
			RenderUtil.renderModel(ModelHandler.INSTANCE.getModel(gunitem.getStock().getRegistryName().toString()),
					matrix, stack, buffer, light, overlay);
		} else {
			RenderUtil.renderModel(ModelHandler.INSTANCE.getModel(ServerUtils.getStock(stack)), matrix, stack, buffer,
					light, overlay);
		}
		matrix.popPose();
	}

	public void renderBody(PoseStack matrix, ItemStack stack, MultiBufferSource buffer, int light, int overlay) {
		matrix.pushPose();
		translateRotateForPart(gun, matrix);
		if (ServerUtils.getBody(stack) == "") {
			RenderUtil.renderModel(ModelHandler.INSTANCE.getModel(gunitem.getBody().getRegistryName().toString()),
					matrix, stack, buffer, light, overlay);
		} else {
			RenderUtil.renderModel(ModelHandler.INSTANCE.getModel(ServerUtils.getBody(stack)), matrix, stack, buffer,
					light, overlay);
		}
		matrix.popPose();
	}

	public void renderBarrel(PoseStack matrix, ItemStack stack, MultiBufferSource buffer, int light, int overlay) {
		matrix.pushPose();
		translateRotateForPart(gun, matrix);
		if (ServerUtils.getBarrel(stack) == "") {
			RenderUtil.renderModel(ModelHandler.INSTANCE.getModel(gunitem.getBarrel().getRegistryName().toString()),
					matrix, stack, buffer, light, overlay);
		} else {
			RenderUtil.renderModel(ModelHandler.INSTANCE.getModel(ServerUtils.getBarrel(stack)), matrix, stack, buffer,
					light, overlay);
		}
		matrix.popPose();
	}

	public void renderMag(PoseStack matrix, ItemStack stack, MultiBufferSource buffer, int light, int overlay) {
		Item magitem = gunitem.getMagPath(stack);
		
		BakedModel model = ModelHandler.INSTANCE.getModel(magitem.getRegistryName());
		if (model != null) {
			matrix.pushPose();
			translateRotateForPart(mag, matrix);
			RenderUtil.renderModel(model, matrix, stack, buffer,
					light, overlay);
			matrix.popPose();
		}
	}

	public void renderHammer(PoseStack matrix, ItemStack stack, MultiBufferSource buffer, int light, int overlay) {
		if (getHammerPath() != null) {
			BakedModel model = ModelHandler.INSTANCE.getModel(getHammerPath());
			if (model != null) {
				matrix.pushPose();
				translateRotateForPart(hammer, matrix);
				RenderUtil.renderModel(model, matrix, stack, buffer, light,
						overlay);
				matrix.popPose();
			}
		}
	}
	
	public void renderScope(PoseStack matrix, ItemStack stack, MultiBufferSource buffer, int light, int overlay) {
		BakedModel model = ModelHandler.INSTANCE.getModel(ServerUtils.getScopePath(stack));
		if (model != null) {
				matrix.pushPose();
				translateRotateForPart(scope, matrix);
				RenderUtil.renderModel(model, matrix, stack, buffer, light,
						overlay);
				matrix.popPose();
		}
	}

	public void renderRightArm(PoseStack matrix, BufferSource buffer, int light, AbstractClientPlayer player) {
		matrix.pushPose();
		matrix.scale(0.7f, 1f, 1.6f);
		translateHand(rightarm, matrix);
		RenderUtil.renderRightArm(matrix, buffer, light, player, ClientHandler.model,
				rightarm.transform.getCombinedRotationX(), rightarm.transform.getCombinedRotationY(),
				rightarm.transform.getCombinedRotationZ());
		matrix.popPose();
	}

	public void renderLeftArm(PoseStack matrix, BufferSource buffer, int light, AbstractClientPlayer player) {
		matrix.pushPose();
		matrix.scale(0.7f, 1f, 1.6f);
		translateHand(leftarm, matrix);
		RenderUtil.renderLeftArm(matrix, buffer, light, player, ClientHandler.model,
				leftarm.transform.getCombinedRotationX(), leftarm.transform.getCombinedRotationY(),
				leftarm.transform.getCombinedRotationZ());
		matrix.popPose();
	}

	public void translateRotateForPart(GunModelPart part, PoseStack matrix) {
		matrix.translate(part.transform.getCombinedOffsetX(), part.transform.getCombinedOffsetY(),
				part.transform.getCombinedOffsetZ());
		matrix.mulPose(Vector3f.XP.rotation(part.transform.getCombinedRotationX()));
		matrix.mulPose(Vector3f.YP.rotation(part.transform.getCombinedRotationY()));
		matrix.mulPose(Vector3f.ZP.rotation(part.transform.getCombinedRotationZ()));
	}

	public void translateHand(GunModelPart part, PoseStack matrix) {
		matrix.translate(part.transform.getCombinedOffsetX(), part.transform.getCombinedOffsetY(),
				part.transform.getCombinedOffsetZ());
	}

	public void setPartDisplayTransform(GunModelPart part, float x, float y, float z, float rx, float ry, float rz) {
		part.transform.setDisplayOffset(x, y, z);
		part.transform.setDisplayRotation(rx, ry, rz);
		part.transform.resetOffset();
		part.transform.resetRotation();
	}

	public Animation getAnimation() {
		return animation;
	}

	public float getAnimationTick() {
		return animTick;
	}

	public void setAnimationTick(float animTick) {
		onAnimTick(animTick);
		this.animTick = animTick;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public void debugAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, aim.transform.offsetX), Mth.lerp(p, 0, aim.transform.offsetY),
				Mth.lerp(p, 0, aim.transform.offsetZ));
		matrix.mulPose(new Quaternion(Mth.lerp(p, 0, aim.transform.rotationX),
				Mth.lerp(p, 0, aim.transform.rotationY), Mth.lerp(p, 0, aim.transform.rotationZ), false));
	}

	public void debugGun() {
		mag.transform.setOffset(gun.transform.offsetX, gun.transform.offsetY, gun.transform.offsetZ);
		mag.transform.setRotation(gun.transform.rotationX, gun.transform.rotationY, gun.transform.rotationZ);
		hammer.transform.setOffset(gun.transform.offsetX, gun.transform.offsetY, gun.transform.offsetZ);
		hammer.transform.setRotation(gun.transform.rotationX, gun.transform.rotationY, gun.transform.rotationZ);
	}

	public void translateHandToCamera(int hand) {
		switch (hand) {
		case RIGHT:
			rightarm.transform.setDisplayOffset(0.0400f, 0.4210f, 0.7298f);
			rightarm.transform.setDisplayRotation(-0.5060f, -0.5410f, -0.4009f);
			break;
		case LEFT:
			leftarm.transform.setDisplayOffset(-0.3197f, 0.8998f, 0.6989f);
			leftarm.transform.setDisplayRotation(0.3666f, 0.8202f, 0.2619f);
			break;
		}
	}

	public void translateGunPartToCamera(GunModelPart part) {
		part.transform.setDisplayOffset(-0.24f, -0.78f, -0.08f);
		part.transform.setDisplayRotation(-1.5533f, 0, 0.1047f);
		part.transform.resetOffset();
		part.transform.resetRotation();
	}

	public void translateHandsToCamera() {
		// Translate hands to camera
		leftarm.transform.setDisplayOffset(-0.3197f, 0.8998f, 0.6989f);
		leftarm.transform.setDisplayRotation(0.3666f, 0.8202f, 0.2619f);
		rightarm.transform.setDisplayOffset(0.0400f, 0.4210f, 0.7298f);
		rightarm.transform.setDisplayRotation(-0.5060f, -0.5410f, -0.4009f);
		leftarm.transform.resetOffset();
		leftarm.transform.resetRotation();

		rightarm.transform.resetOffset();
		rightarm.transform.resetRotation();
	}

	public void translateGunPartsToCamera() {
		// Translate gunparts to camera
		for (GunModelPart part : getParts()) {
			part.transform.setDisplayOffset(-0.24f, -0.78f, -0.08f);
			part.transform.setDisplayRotation(-1.5533f, 0, 0.1047f);
			part.transform.resetOffset();
			part.transform.resetRotation();
		}
	}

	public ItemMag getMagItem() {
		return (ItemMag) ClientEventHandler.getPlayer().getInventory().getItem(ammoindex).getItem();
	}

	public ItemStack getMagStack() {
		return ClientEventHandler.getPlayer().getInventory().getItem(ammoindex);
	}

	public String getMBPath() {
		return getMagStack().getOrCreateTag().getString(Constants.MAGBULLETPATH);
	}
	
	public String getMPath() {
		return getGunItem().getMagPath(Util.getStack()).getRegistryName().toString();
	}

	public void rotateAroundXR(PoseStack matrix, GunModelPart part, float angle) {
		matrix.translate(0, part.transform.getCombinedOffsetY(), part.transform.getCombinedOffsetZ());
		matrix.mulPose(Vector3f.XP.rotation(angle));
		matrix.translate(0, -part.transform.getCombinedOffsetY(), -part.transform.getCombinedOffsetZ());
	}

	public void rotateAroundYR(PoseStack matrix, GunModelPart part, float angle) {
		matrix.translate(part.transform.getCombinedOffsetX(), 0, part.transform.getCombinedOffsetZ());
		matrix.mulPose(Vector3f.YP.rotation(angle));
		matrix.translate(-part.transform.getCombinedOffsetX(), 0, -part.transform.getCombinedOffsetZ());
	}

	public void rotateAroundZR(PoseStack matrix, GunModelPart part, float angle) {
		matrix.translate(part.transform.getCombinedOffsetX(), part.transform.getCombinedOffsetY(), 0);
		matrix.mulPose(Vector3f.ZP.rotation(angle));
		matrix.translate(-part.transform.getCombinedOffsetX(), -part.transform.getCombinedOffsetY(), 0);
	}

	public void rotateAroundXD(PoseStack matrix, GunModelPart part, float angle) {
		matrix.translate(0, part.transform.getCombinedOffsetY(), part.transform.getCombinedOffsetZ());
		matrix.mulPose(Vector3f.XP.rotationDegrees(angle));
		matrix.translate(0, -part.transform.getCombinedOffsetY(), -part.transform.getCombinedOffsetZ());
	}

	public void rotateAroundYD(PoseStack matrix, GunModelPart part, float angle) {
		matrix.translate(part.transform.getCombinedOffsetX(), 0, part.transform.getCombinedOffsetZ());
		matrix.mulPose(Vector3f.YP.rotationDegrees(angle));
		matrix.translate(-part.transform.getCombinedOffsetX(), 0, -part.transform.getCombinedOffsetZ());
	}

	public void rotateAroundZD(PoseStack matrix, GunModelPart part, float angle) {
		matrix.translate(part.transform.getCombinedOffsetX(), part.transform.getCombinedOffsetY(), 0);
		matrix.mulPose(Vector3f.ZP.rotationDegrees(angle));
		matrix.translate(-part.transform.getCombinedOffsetX(), -part.transform.getCombinedOffsetY(), 0);
	}

	public void reset(int gun, int mag, int ammo) {
		gunbullets = gun;
		magbullets = mag;
		ammoindex = ammo;
	}

	public void resetForGetOutMag(int gun, int mag) {
		gunbullets = gun;
		magbullets = mag;
	}

	protected void researchAmmoIndex(Item item) {
		ammoindex = ServerUtils.getIndexForItem(ClientEventHandler.getPlayer().getInventory(), item);
		magbullets = ServerUtils.getItemCount(ClientEventHandler.getPlayer().getInventory(), item);
	}

	protected void setDisplayToNonDisplay() {
		for(GunModelPart part : parts) {
			part.transform.setDisplaytoNonD();
		}
	}
	
	protected void resetDisplay() {
		for(GunModelPart part : parts) {
			part.transform.resetDisplay();;
		}
	}
	
	protected void doAbleToRepos(GunModelPart part) {
		if(!part.transform.isDZero()) {
			part.transform.setDisplaytoNonD();
			part.transform.resetDisplay();
		}
	}
	
	public void resetNonD() {
		for(GunModelPart p : getParts()) {
			p.transform.setOffset(0, 0, 0);
			p.transform.setRotation(0, 0, 0);
		}
	}
	
	
	public void updateCurrent(int size) {
		animator.update(size);
	}
	
	public void prev(int size) {
		animator.prev(size);
	}
	
	public void next(int size) {
		animator.next(size);
	}
	
	public Animator getAnimator() {
		return animator;
	}
	
	public abstract void doGetOutMag(ItemStack stack);

	public abstract void startKickBackAnim();
	
	public abstract void startInspectAnim();
	
	public abstract Vector3f getMuzzleFlashPos(ItemStack stack);
	
	// Getters

	public void setCanRenderMuzzleFlash(boolean render) {
		this.renderMuzzleFlash = render;
	}
	
	public void setDebugMuzzleFlash(boolean render) {
		this.debugMuzzleFlash = render;
	}
	
	public void setGunBullets(int gunbullets) {
		this.gunbullets = gunbullets;
	}

	public int getGunBullets() {
		return gunbullets;
	}

	public void setMagBullets(int magbullets) {
		this.magbullets = magbullets;
	}

	public int getMagBullets() {
		return magbullets;
	}

	public void setMagIndex(int ammoindex) {
		this.ammoindex = ammoindex;
	}

	public int getMagIndex() {
		return ammoindex;
	}

	public ItemGun getGunItem() {
		return gunitem;
	}
	
	public GunModelPart[] getCopyGunModelParts() {
		GunModelPart[] nparts = new GunModelPart[parts.length];
		for(int i = 0; i < parts.length; i++) {
			nparts[i] = parts[i].copy();
		}
		return nparts;
	}

	// Abstract methods

	public abstract void handleAim(PoseStack matrix, float aimProgress);

	public abstract void handleSprint(PoseStack matrix, float aimProgress);

	public abstract GunModelPart[] getParts();

	public abstract boolean canRenderMag(ItemStack stack);

	public abstract boolean canShoot(ItemStack stack);
	
	public abstract void setupParts();

	public abstract void setupAnimations();

	public abstract void initReloadAnimation(int currentgunbullets, int magbullets);

	public abstract void onAnimTick(float animTick);

	public abstract void onShoot(ItemStack stack);

	public abstract void onAnimationEnd();

	public abstract void initExtraParts();

	public abstract BakedModel getModifiedModel(BakedModel origin);

	public abstract String getHammerPath();

	public abstract boolean hasMultipleParts();

	// Debug

	public void incIndex() {
		index = (index + 1) % parts.length;
		System.out.println("index increased to: " + index + " name: " + getPart().getName());
	}

	public void decIndex() {
		index = (index - 1 + parts.length) % parts.length;
		System.out.println("index decreased to: " + index + " name: " + getPart().getName());
	}

	public GunModelPart getPart() {
		return parts[index];
	}
	// Debug end
}
