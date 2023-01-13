package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.models.modmodels.ThompsonModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.Paths;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ThompsonGunModel extends GunModel {

	public static Animation look;
	public static Animation kickback;
	public static Animation reloadNoMag;
	public static Animation getOutMag;
	public static Animation reloadMagByMag;
	
	public ThompsonGunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", -0.19f, -0.04f, -0.23f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", -0.18f, 0.3689999f, -0.06f, 0.005235f, -1.117001f, 0, 
						1.3f, 2.5f, 1.3f), 
				new GunModelPart("gun", 0.327f, -0.546f, -0.72f, 0.021459f, 0, 0),
				new GunModelPart("hammer"),
				new GunModelPart("mag"),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("leftarmmag"),
				new GunModelPart("aim", -0.631601f, 0.446f, 0.45100307f, 
						-0.10820972f, -0.0017462336f, 0), 
				new GunModelPart("sprint", 1.32f, -0.89f, -0.221f, 0.548036f, 1.326451f, -0.191986f),
				new GunModelPart("recoil", 0, 0, 0.02f, 0, 0, 0) }, 
				ItemRegistries.THOMPSON.get(), client);
		
		look = new Animation("lookAnim", "oldguns:thompson").startKeyframe(12).end();
		kickback = new Animation("kickbackAnim", "oldguns:thompson").startKeyframe(12).end();
		getOutMag = new Animation("getOutMagAnim", "oldguns:thompson")
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.startKeyframe(24)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.end();
		reloadNoMag = new Animation("reloadNoMagAnim", "oldguns:thompson")
				.startKeyframe(12, "easeInSine")
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.startKeyframe(24)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.startKeyframe(12, "easeOutQuint")
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.startKeyframe(12)
				.translate(parts[6], -0.22000003f, -0.6299997f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3999999f, 0.41999987f, 0.33999994f)
				.rotate(parts[6], 0.0f, 0.0f, 0.8377583f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.startKeyframe(18, "easeInExpo")
				.translate(parts[6], -0.22000003f, -0.6299997f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3999999f, 0.41999987f, 0.45999983f)
				.rotate(parts[6], 0.0f, 0.0f, 0.8377583f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.startKeyframe(6, "easeInExpo")
				.translate(parts[6], -0.22000003f, -0.6299997f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3999999f, 0.41999987f, 0.45999983f)
				.rotate(parts[6], 0.0f, 0.0f, 0.8377583f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[6], -0.22000003f, -0.6299997f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3999999f, 0.41999987f, 0.33999994f)
				.rotate(parts[6], 0.0f, 0.0f, 0.8377583f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.end();
		reloadMagByMag = new Animation("reloadMagByMag", "oldguns:thompson")
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[7], 0.0f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(24)
				.translate(parts[7], 0.0f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeOutQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.31999996f, 0.11999998f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, -0.07999999f, 0.13999999f)
				.rotate(parts[5], 0.0f, 0.0f, -0.41887897f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.22000003f, -0.6299997f, 0.0f)
				.translate(parts[1], -0.3999999f, 0.41999987f, 0.33999994f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.8377583f)
				.startKeyframe(18, "easeInExpo")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.22000003f, -0.6299997f, 0.0f)
				.translate(parts[1], -0.3999999f, 0.41999987f, 0.45999983f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.8377583f)
				.startKeyframe(6, "easeInExpo")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.22000003f, -0.6299997f, 0.0f)
				.translate(parts[1], -0.3999999f, 0.41999987f, 0.45999983f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.8377583f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.22000003f, -0.6299997f, 0.0f)
				.translate(parts[1], -0.3999999f, 0.41999987f, 0.33999994f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.8377583f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
	}

	@Override
	public float getKnockback() {
		return 0;
	}

	@Override
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix, int light) {
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
		hammer(matrix, player, stack, buffer, light, 3, Paths.THOMPSONHAMMER);
		matrix.popPose();
		matrix.popPose(); // 3-
		matrix.popPose(); // 1-
	}

	@Override
	public void reload(Player player, ItemStack stack) {
		setAnimation(getOutMag);
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
		return new ThompsonModModel(origin);
	}

	@Override
	public List<GunModelPart> getGunParts() {
		return List.of(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
	}

	@Override
	public GunModelPart getGunModelPart() {
		return parts[2];
	}

}
