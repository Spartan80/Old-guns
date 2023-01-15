package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.RepetitiveAnimation;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.models.modmodels.IthacaModel37ModModel;
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

public class IthacaModel37GunModel extends GunModel {

	public static Animation look;
	public static RepetitiveAnimation reload;
	public static Animation kickback;
	protected int times;
	
	public IthacaModel37GunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", 0.25f, -0.55f, -0.72f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", 0.21f, 0.02f, -1.68f, 0.005235f, -1.117001f, 0, 
						1.3f, 2.5f, 1.3f), 
				new GunModelPart("gun", 0.687001f, -0.866001f, -0.88f, 0.056365f, 0.0f, 0),
				new GunModelPart("hammer"),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("aim", -1.107f, 0.732993f, 0.386998f, -0.052359f, 0f, 0), 
				new GunModelPart("sprint", 1.32f, -0.89f, -0.221f, 0.548036f, 1.326451f, -0.191986f),
				new GunModelPart("recoil", 0, 0, 0, 0, 0, 0) }, 
				ItemRegistries.ITHACAMODEL37.get(), client);
		
		look = new Animation("lookAnim", "oldguns:ithacamodel37")
				.startKeyframe(12).end();

		reload = new RepetitiveAnimation("reloadAnim", "oldguns:ithacamodel37")
				.startKeyframe(12)
				.translate(parts[1], -0.21000002f, -0.11999998f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], -0.80285174f, 0.0f, 0.0f)
				.startKeyframe(8)
				.type(1)
				.translate(parts[1], -1.4901161E-8f, 0.14f, 0.43999985f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], -1.4901161E-8f, 0.24000005f, 0.3999999f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(6)
				.translate(parts[1], -1.4901161E-8f, 0.26000005f, 0.26000002f)
				.translate(parts[5], 0.0f, 0.0f, -0.01f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(12, "easeOutQuint")
				.type(2)
				.translate(parts[1], -1.4901161E-8f, 0.24000005f, 0.26000002f)
				.translate(parts[5], 0.0f, 0.0f, -0.01f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(16)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.end();
		
		kickback = new Animation("kickbackAnim", "oldguns:ithacamodel37")
				.startKeyframe(8, "easeInExpo")
				.translate(parts[1], -2.1699984f, -0.02f, 1.0799993f)
				.translate(parts[0], -0.44999987f, -0.36999992f, -0.45999983f)
				.translate(parts[2], 0.55999976f, -0.44999984f, -1.1599993f)
				.rotate(parts[0], 0.4886921f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.rotate(parts[4], 0.0f, 0.47123882f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], -2.1699984f, -0.02f, 1.0799993f)
				.translate(parts[0], -0.44999987f, -0.36999992f, -0.45999983f)
				.translate(parts[2], 0.55999976f, -0.44999984f, -1.1599993f)
				.rotate(parts[0], 0.4886921f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.rotate(parts[4], 0.0f, 0.47123882f, 0.0f)
				.startKeyframe(12, "easeInSine")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.0f)
				.end();

		shootAnim = new Animation("shootAnim", "oldguns:ithacamodel37")
				.startKeyframe(12)
				.startKeyframe(4, "easeOutQuint")
				.translate(parts[1], 0.0f, 0.0f, 0.42999986f)
				.translate(parts[3], 0.0f, 0.0f, 0.42999986f)
				.startKeyframe(8)
				.translate(parts[1], 0.0f, 0.0f, 0.42999986f)
				.translate(parts[3], 0.0f, 0.0f, 0.42999986f)
				.startKeyframe(8, "easeOutQuint")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.end();
	}

	@Override
	public float getKnockback() {
		return 0;
	}

	@Override
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix, int light) {
		renderOneHammerNoMag(matrix, stack, player, buffer, light);
		/*matrix.pushPose(); // 1+
		lerpGunStuff(matrix, stack, 6, 7, 8);
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		leftArm(matrix, player, buffer, light, 1);
		matrix.pushPose(); // 3+
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		matrix.pushPose(); // 4+
		renderGunPart(player, stack, buffer, matrix, light);
		hammer(matrix, player, stack, buffer, light, 3, Paths.ITHACAMODEL37HAMMER);
		matrix.popPose();
		matrix.popPose(); // 3-
		matrix.popPose(); // 1-*/
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
		return new IthacaModel37ModModel(origin);
	}

	@Override
	public List<GunModelPart> getGunParts() {
		return List.of(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
	}

	@Override
	public GunModelPart getGunModelPart() {
		return parts[2];
	}

}
