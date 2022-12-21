package com.jg.oldguns.client.animations.parts;

import java.util.List;
import java.util.Map;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Animator;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.serializers.AnimationSerializer;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.render.RenderHelper;
import com.jg.oldguns.client.screens.AnimationScreen;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunStuff;
import com.jg.oldguns.guns.MagItem;
import com.jg.oldguns.network.ShootMessage;
import com.jg.oldguns.utils.MathUtils;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Quaternion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
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
		this.parts = gunModelParts;
		this.gun = (GunItem) gun;
		this.client = client;
		this.animator = new Animator(this);
		this.stuff = new GunModelStuff(this.gun.getStuff());
		this.hasChanges = true;
		this.playAnimation = true;
		this.shootAnim = new Animation("shootAnim", Utils.getR(gun).toString())
				.startKeyframe(4).end();
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

	// Gun Methods

	public void shoot(Player player, ItemStack stack) {
		setAnimation(shootAnim);
		OldGuns.channel.sendToServer(
				new ShootMessage(player.getYRot(), player.getXRot(), 
						gun.getShootSound().getLocation().toString()));
		markChanges();
		LogUtils.getLogger().info(shootAnim.getGunModel());
	}

	public boolean canShoot(Player player, ItemStack stack) {
		return true;//NBTUtils.getAmmo(stack) > 0 && !NBTUtils.getSafe(stack);
	}

	// Rendering

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
			if(hammer != -1) {
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
		if(!NBTUtils.getMag(stack).equals("")) {
			matrix.pushPose();
			translateAndRotateAndScale(parts[6].getCombined(), matrix);
			renderItem(player, stuff.mag, buffer, matrix, light, parts[4].getCombined());
			matrix.popPose();
		}
		matrix.popPose();
		matrix.pushPose();
		translateAndRotateAndScale(parts[6].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		renderGunPart(player, stack, buffer, matrix, light);
		matrix.pushPose();
		translateAndRotateAndScale(parts[3].getCombined(), matrix);
		//LogUtils.getLogger().info(parts[3].getCombined().toString());
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
		matrix.pushPose();
		translateAndRotateAndScale(parts[2].getCombined(), matrix);
		Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, 
				stack,
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light);
		/*Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stuff.getBarrel(),
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light);
		Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stuff.getBody(),
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light);
		Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(player, stuff.getStock(),
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light);*/
		matrix.popPose();
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
		Minecraft.getInstance().getItemRenderer().render(stack, TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix,
				buffer, light, OverlayTexture.NO_OVERLAY, model);
		matrix.popPose();
	}

	protected void renderModel(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix,
			int light, BakedModel model, Transform transform) {
		matrix.pushPose();
		translateAndRotateAndScale(transform, matrix);
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

	public MagItem getMagItem() {
		return (MagItem) Minecraft.getInstance().player.getInventory().getItem(ammoindex)
				.getItem();
	}

	public ItemStack getMagStack() {
		return Minecraft.getInstance().player.getInventory().getItem(ammoindex);
	}
	
	public String getMBPath() {
		return getMagStack().getOrCreateTag().getString(NBTUtils.MAGBULLET);
	}

	public String getMPath() {
		return Utils.getR(NBTUtils.getMag(getMagStack()));
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
		int current = (int)Math.floor(t/cycleDur);
		target = target - start;
		return tick > start && current < times && t - (current*cycleDur) == target;
	}

	// Getters and setters
	
	public GunModelPart[] getParts() {
		return parts;
	}

	public void setAnimation(Animation anim) {
		this.animator.setAnimation(anim);
		if (Minecraft.getInstance().screen instanceof AnimationScreen) {
			AnimationScreen screen = (AnimationScreen) Minecraft.getInstance().screen;
			screen.initKeyframes();
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

	public abstract WrapperModel getModifiedModel(BakedModel origin);

	public abstract List<GunModelPart> getGunParts();

	public abstract GunModelPart getGunModelPart();

}
