package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.models.modmodels.GalilModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.Paths;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class GalilGunModel extends GunModel {

	public static Animation look;
	public static Animation kickback;
	public static Animation reloadNoMag;
	public static Animation getOutMag;
	public static Animation reloadMagByMag;
	
	public GalilGunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", -0.27f, -0.18f, -0.5f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", -0.204f, 0.329f, -0.83f, 0.005235f, -1.117001f, 0, 
						1.3f, 2.5f, 1.3f), 
				new GunModelPart("gun", 0.987f, -0.926f, -1.56f, 0.021459f, -0.034906f, 0),
				new GunModelPart("hammer"),
				new GunModelPart("mag"),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("leftarmmag"),
				new GunModelPart("aim", -0.57f, 0.319f, 0.76f, -0.0209433f, 0.036651f, 0), 
				new GunModelPart("sprint", 1.32f, -0.89f, -0.221f, 0.548036f, 1.326451f, -0.191986f),
				new GunModelPart("recoil", 0, 0, 0.02f, 0, 0, 0) }, 
				ItemRegistries.GALIL.get(), client);
		
		look = new Animation("lookAnim", "oldguns:galil")
				.startKeyframe(12, "easeOutQuad")
				.translate(parts[6], -0.8599995f, -0.68999964f, 0.0f)
				.translate(parts[1], -1.1399993f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, 0.5934119f)
				.startKeyframe(24)
				.translate(parts[6], -0.8599995f, -0.68999964f, 0.0f)
				.translate(parts[1], -1.1399993f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, 0.5934119f)
				.startKeyframe(12, "easeOutQuad")
				.translate(parts[6], 0.23000002f, 0.14999998f, 0.0f)
				.translate(parts[1], -0.5599998f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, -0.4886921f)
				.startKeyframe(24)
				.translate(parts[6], 0.23000002f, 0.14999998f, 0.0f)
				.translate(parts[1], -0.5599998f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, -0.4886921f)
				.startKeyframe(12, "easeInOutCirc")
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		kickback = new Animation("kickbackAnim", "oldguns:galil")
				.startKeyframe(8, "easeInExpo")
				.translate(parts[2], -0.6399997f, 0.57999974f, -0.45999986f)
				.translate(parts[0], 0.0f, -0.26000002f, -0.29f)
				.translate(parts[1], -0.7299996f, -0.02f, 0.77999955f)
				.rotate(parts[2], 0.0f, 1.9547703f, 1.3962643f)
				.rotate(parts[5], 0.0f, 0.4886921f, 0.0f)
				.rotate(parts[0], 0.26179942f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[2], -0.6399997f, 0.57999974f, -0.45999986f)
				.translate(parts[0], 0.0f, -0.26000002f, -0.29f)
				.translate(parts[1], -0.7299996f, -0.02f, 0.77999955f)
				.rotate(parts[2], 0.0f, 1.9547703f, 1.3962643f)
				.rotate(parts[5], 0.0f, 0.4886921f, 0.0f)
				.rotate(parts[0], 0.26179942f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInSine")
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.end();
		getOutMag = new Animation("getOutMagAnim", "oldguns:galil")
				.startKeyframe(12, "easeInSine")
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, -0.5999997f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, -0.5999997f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(18, "easeOutSine")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.end();
		reloadNoMag = new Animation("reloadNoMagAnim", "oldguns:galil")
				.startKeyframe(18, "easeInSine")
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, -0.5999997f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(24)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, -0.5999997f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeOutQuint")
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(4)
				.translate(parts[2], 0.0f, 0.01f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], -0.23000002f, -0.6299997f, 0.0f)
				.translate(parts[1], 0.25000003f, -0.8399995f, 1.0599993f)
				.rotate(parts[5], 0.0f, 0.0f, 0.5235987f)
				.rotate(parts[1], 0.66322523f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInExpo")
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], -0.23000002f, -0.6299997f, 0.0f)
				.translate(parts[1], 0.25000003f, -0.8399995f, 1.2799991f)
				.rotate(parts[5], 0.0f, 0.0f, 0.5235987f)
				.rotate(parts[1], 0.66322523f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], -0.23000002f, -0.6299997f, 0.0f)
				.translate(parts[1], 0.25000003f, -0.8399995f, 1.0599993f)
				.rotate(parts[5], 0.0f, 0.0f, 0.5235987f)
				.rotate(parts[1], 0.66322523f, 0.0f, 0.0f)
				.startKeyframe(18, "easeOutSine")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.end();
		reloadMagByMag = new Animation("reloadMagByMagAnim", "oldguns:galil")
				.startKeyframe(12, "easeInSine")
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.translate(parts[7], 0.0f, -0.5999997f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.startKeyframe(24)
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.translate(parts[7], 0.0f, -0.5999997f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.startKeyframe(12, "easeOutQuint")
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.startKeyframe(4)
				.translate(parts[1], 0.0f, -0.19f, 0.5399998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.5299998f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.01f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.36651912f)
				.startKeyframe(12)
				.translate(parts[1], 0.25000003f, -0.8399995f, 1.0599993f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], -0.23000002f, -0.6299997f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.66322523f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.5235987f)
				.startKeyframe(12, "easeInExpo")
				.translate(parts[1], 0.25000003f, -0.8399995f, 1.2799991f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], -0.23000002f, -0.6299997f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.66322523f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.5235987f)
				.startKeyframe(12)
				.translate(parts[1], 0.25000003f, -0.8399995f, 1.0599993f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], -0.23000002f, -0.6299997f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.66322523f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.5235987f)
				.startKeyframe(18, "easeOutSine")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.end();
	}

	@Override
	public float getKnockback() {
		return 0;
	}

	@Override
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix, int light) {
		/*matrix.pushPose(); // 1+
		lerpGunStuff(matrix, stack, 9, 10, 11);
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		matrix.pushPose(); // 2+
		parts[1].getDTransform().setScale(1.3f, 2.5f, 1.3f);
		translateAndRotateAndScale(parts[7].getCombined(), matrix);
		translateAndRotateAndScale(parts[8].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.LEFT, parts[1].getCombined());
		matrix.popPose(); // 2-
		matrix.pushPose(); // 3+
		translateAndRotateAndScale(parts[6].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		matrix.pushPose(); // 4+
		renderGunPart(player, stack, buffer, matrix, light);
		if(NBTUtils.hasMag(stack) && renderMag()) {
			//LogUtils.getLogger().info("HasMag: " + NBTUtils.getMag(stack));
			matrix.pushPose();
			translateAndRotateAndScale(parts[6].getCombined(), matrix);
			renderItem(player, new ItemStack(ForgeRegistries.ITEMS
					.getValue(new ResourceLocation(NBTUtils.getMag(stack)))), buffer, matrix, light, 
					parts[4].getCombined());
			matrix.popPose();
		}
		//translateAndRotateAndScale(parts[3].getCombined(), matrix);
		//LogUtils.getLogger().info(parts[3].getCombined().toString());
		//matrix.scale(2, 2, 2);
		matrix.pushPose();
		translateAndRotateAndScale(parts[8].getCombined(), matrix);
		renderModel(player, stack, buffer, matrix, light, Minecraft.getInstance()
				.getItemRenderer()
				.getItemModelShaper().getModelManager()
				.getModel(new ModelResourceLocation(Paths.GALILHAMMER, "inventory")), 
				parts[3].getTransform());
		matrix.popPose();
		matrix.popPose(); // 4-
		matrix.popPose(); // 3-
		matrix.popPose(); // 1-*/
		
		matrix.pushPose(); // 1+
		lerpGunStuff(matrix, stack, 8, 9, 10);
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		leftArmMag(matrix, player, buffer, light, 1, 7);
		matrix.pushPose(); // 3+
		translateAndRotateAndScale(parts[6].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		matrix.pushPose(); // 4+
		gunWithMag(matrix, player, stack, buffer, light, 4);
		lerpTransform(matrix, client.getCooldown().getProgress(NBTUtils.getId(stack)), 
				new Transform(0.0f, -0.008f, 0.068f, 0, 0, 0));
		hammer(matrix, player, stack, buffer, light, 3, Paths.GALILHAMMER);
		matrix.popPose();
		matrix.popPose(); // 3-
		matrix.popPose(); // 1-
	}

	@Override
	public void tick(Player player, ItemStack stack) {
		super.tick(player, stack);
	}
	
	@Override
	public void reload(Player player, ItemStack stack) {
		fillReloadData(BulletItem.BIG, player, stack, reloadMagByMag, reloadNoMag);
	}

	@Override
	public Animation getLookAnimation() {
		return look;
	}

	@Override
	public Animation getKickbackAnimation() {
		return kickback;
	}

	@Override
	public Animation getGetOutMagAnimation() {
		return getOutMag;
	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		return new GalilModModel(origin);
	}

	@Override
	public List<GunModelPart> getGunParts() {
		return List.of(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
	}

	@Override
	public GunModelPart getGunModelPart() {
		return parts[2];
	}

}
