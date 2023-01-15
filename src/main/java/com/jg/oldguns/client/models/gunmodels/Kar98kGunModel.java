package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.RepetitiveAnimation;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.models.modmodels.Kar98kModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Kar98kGunModel extends GunModel {

	public static Animation look;
	public static RepetitiveAnimation reload;
	public static Animation kickback;
	protected int times;
	
	public Kar98kGunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", -0.05f, -0.22f, -0.21f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", -0.11f, 0.36f, -0.61f, 0.005235f, -1.117001f, 0, 
						1.3f, 2.5f, 1.3f), 
				new GunModelPart("gun", 0.757f, -0.626f, -1.1f, 0.056365f, 0, 0),
				new GunModelPart("hammer"),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("leftarmhammer"),
				new GunModelPart("aim", -0.806f, 0.465f, 0.561f, -0.062831f, -0.001745f, 0), 
				new GunModelPart("sprint", 1.32f, -0.89f, -0.221f, 0.548036f, 1.326451f, -0.191986f),
				new GunModelPart("recoil", 0.0f, -0.02f, 0.04f, 0f, 0f, 0f) }, 
				ItemRegistries.KAR98K.get(), client);
		
		look = new Animation("lookAnim", "oldguns:kar98k")
				.startKeyframe(12)
				.translate(parts[1], -0.07999999f, 0.5499998f, 0.38999993f)
				.translate(parts[5], 0.0f, -0.87999946f, 0.0f)
				.rotate(parts[5], 0.80285174f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], -0.07999999f, 0.5499998f, 0.38999993f)
				.translate(parts[5], -0.24000005f, -1.479999f, -0.4299999f)
				.rotate(parts[5], 0.80285174f, 0.0f, 0.82030505f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(24)
				.translate(parts[1], -0.07999999f, 0.5499998f, 0.38999993f)
				.translate(parts[5], -0.24000005f, -1.479999f, -0.4299999f)
				.rotate(parts[5], 0.80285174f, 0.0f, 0.82030505f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.089999974f, 0.6499997f, 0.5199998f)
				.translate(parts[5], 0.4099999f, -0.5799998f, 0.17999999f)
				.rotate(parts[5], 0.80285174f, 0.0f, -0.29670593f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(24)
				.translate(parts[1], 0.089999974f, 0.6499997f, 0.5199998f)
				.translate(parts[5], 0.4099999f, -0.5799998f, 0.17999999f)
				.rotate(parts[5], 0.80285174f, 0.0f, -0.29670593f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInQuart")
				.translate(parts[1], 0.089999974f, 0.6499997f, 0.5199998f)
				.translate(parts[5], 0.4099999f, -0.5799998f, 0.17999999f)
				.rotate(parts[5], 0.80285174f, 0.0f, -0.29670593f)
				.rotate(parts[1], 0.0f, 0.2792527f, 0.0f)
				.startKeyframe(4, "easeOutQuart")
				.translate(parts[1], 0.089999974f, 0.6499997f, 0.5199998f)
				.translate(parts[5], 0.4099999f, -0.5799998f, 0.17999999f)
				.rotate(parts[5], 0.80285174f, 0.0f, -0.29670593f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(4)
				.translate(parts[1], 0.089999974f, 0.6499997f, 0.5199998f)
				.translate(parts[5], 0.4099999f, -0.5799998f, 0.17999999f)
				.rotate(parts[5], 0.80285174f, -0.034906585f, -0.29670593f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(4)
				.translate(parts[1], 0.089999974f, 0.6499997f, 0.5199998f)
				.translate(parts[5], 0.4099999f, -0.5799998f, 0.17999999f)
				.rotate(parts[5], 0.80285174f, 0.0f, -0.29670593f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.end();

		reload = new RepetitiveAnimation("reloadAnim", "oldguns:kar98k")
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.16f, 0.09999997f, 0.0f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.18f, 0.3899999f, 0.0f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(6)
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[2], 0.0f, 0.0f, 0.01f)
				.translate(parts[0], 0.18f, 0.3899999f, 0.0f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.01f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(6, "easeOutQuint")
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[0], 0.18f, 0.3899999f, 0.0f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(22)
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.18f, -0.22000004f, 0.0f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(24)
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.18f, -0.22000004f, 0.0f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.20000002f, 0.33999994f, -0.28f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.06981317f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(12)
				.type(1)
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.20000002f, 0.33999994f, -0.28f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.06981317f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(6)
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[2], 0.0f, -0.01f, 0.0f)
				.translate(parts[0], 0.20000002f, 0.33999994f, -0.28f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], -0.034906585f, 0.06981317f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(4, "easeOutQuint")
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[2], 0.0f, -0.01f, -0.01f)
				.translate(parts[0], 0.20000002f, 0.33999994f, -0.31999996f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], -0.034906585f, 0.06981317f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(8, "easeInOutExpo")
				.type(2)
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.20000002f, 0.33999994f, -0.31999996f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], -0.034906585f, 0.06981317f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.16f)
				.translate(parts[0], 0.18f, 0.3899999f, 0.0f)
				.translate(parts[1], -0.31999996f, 0.0f, 0.0f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(6, "easeInQuint")
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, -0.01f)
				.translate(parts[0], 0.18f, 0.3899999f, 0.0f)
				.translate(parts[1], -0.31999996f, 0.0f, -0.01f)
				.translate(parts[5], -0.41999987f, -0.44999984f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.43633226f)
				.startKeyframe(18)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.end();
		
		kickback = new Animation("kickbackAnim", "oldguns:kar98k")
				.startKeyframe(8, "easeInExpo")
				.translate(parts[0], -0.02f, -0.16f, -0.4899998f)
				.translate(parts[1], -0.9899994f, 0.02f, 0.79999954f)
				.translate(parts[2], -0.09999997f, -0.039999984f, -0.45999986f)
				.rotate(parts[4], 0.0f, 0.3839724f, 0.0f)
				.rotate(parts[0], 0.24434613f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 1.937317f, 1.3962643f)
				.startKeyframe(12)
				.translate(parts[0], -0.02f, -0.16f, -0.4899998f)
				.translate(parts[1], -0.9899994f, 0.02f, 0.79999954f)
				.translate(parts[2], -0.09999997f, -0.039999984f, -0.45999986f)
				.rotate(parts[4], 0.0f, 0.3839724f, 0.0f)
				.rotate(parts[0], 0.24434613f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 1.937317f, 1.3962643f)
				.startKeyframe(12, "easeInSine")
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.end();
		
		shootAnim = new Animation("shootAnim", "oldguns:kar98k")
				.startKeyframe(12, "easeInSine")
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, -0.30999997f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.20000002f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.2792527f)
				.startKeyframe(12)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, -0.30999997f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.16f, 0.3899999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.2792527f)
				.startKeyframe(12, "easeInExpo")
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, -0.30999997f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.16f, 0.3899999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.13999999f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.2792527f)
				.startKeyframe(4)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, -0.30999997f, 0.02f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.16f, 0.3899999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.13999999f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.2792527f)
				.startKeyframe(12, "easeInExpo")
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, -0.30999997f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.16f, 0.3899999f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.2792527f)
				.startKeyframe(12)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, -0.30999997f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.16f, 0.06999998f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.2792527f)
				.startKeyframe(8)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.0f)
				.end();
	}

	@Override
	public float getKnockback() {
		return 0;
	}

	@Override
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix, int light) {
		matrix.pushPose();
		lerpGunStuff(matrix, stack, 7, 8, 9);
		translateAndRotateAndScale(parts[4].getCombined(), matrix);
		leftArm(matrix, player, buffer, light, 1);
		matrix.pushPose();
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		matrix.pushPose();
		translateAndRotateAndScale(parts[6].getTransform(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		matrix.popPose();
		renderGunPart(player, stack, buffer, matrix, light);
		matrix.pushPose();
		translateAndRotateAndScale(parts[6].getTransform(), matrix);
		hammer(matrix, player, stack, buffer, light, 3, stuff.getHammers()[0]);
		matrix.popPose();
		matrix.popPose();
		matrix.popPose();
	}
	
	@Override
	public void shoot(Player player, ItemStack stack) {
		super.shoot(player, stack);
		setAnimation(shootAnim);
	}

	@Override
	public void tick(Player player, ItemStack stack) {
		super.tick(player, stack);
	}
	
	@Override
	public void reload(Player player, ItemStack stack) {
		reload.setTimes(6);
		setAnimation(reload);
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
		return null;
	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		return new Kar98kModModel(origin);
	}

	@Override
	public List<GunModelPart> getGunParts() {
		return List.of(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
	}

	@Override
	public GunModelPart getGunModelPart() {
		return parts[2];
	}
	
}
