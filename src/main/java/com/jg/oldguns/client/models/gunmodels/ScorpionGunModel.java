package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.models.modmodels.ScorpionModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.Paths;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ScorpionGunModel extends GunModel {

	public static Animation look;
	public static Animation kickback;
	public static Animation reloadNoMag;
	public static Animation getOutMag;
	public static Animation reloadMagByMag;
	
	public ScorpionGunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", -0.25f, -0.16f, -0.3f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", -0.23f, 0.32f, -0.17f, 0.005235f, -1.117001f, 0, 
						1.3f, 2.5f, 1.3f), 
				new GunModelPart("gun", 0.597f, -0.626f, -0.98f, 0.056365f, 0.034906f, 0),
				new GunModelPart("hammer"),
				new GunModelPart("mag"),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("leftarmmag"),
				new GunModelPart("leftarmhammer"),
				new GunModelPart("aim", -0.6807992f, 0.5911991f, 0.56400204f, 
						-0.13508798f, -0.03874721f, 0), 
				new GunModelPart("sprint", 0f, 0f, 0f, -0.2792527f, 0f, 0f),
				new GunModelPart("recoil", 0.014f, -0.0019999999f, 0.080999985f, 0, 0, 0) }, 
				ItemRegistries.SCORPION.get(), client);
		
		look = new Animation("lookAnim", "oldguns:scorpion")
				.startKeyframe(12, "easeOutQuint")
				.translate(parts[1], -1.1399993f, 0.0f, 0.0f)
				.translate(parts[6], -0.8599995f, -0.68999964f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, 0.5934119f)
				.startKeyframe(24)
				.translate(parts[1], -1.1399993f, 0.0f, 0.0f)
				.translate(parts[6], -0.8599995f, -0.68999964f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, 0.5934119f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[1], -0.5599998f, 0.0f, 0.0f)
				.translate(parts[6], 0.23000002f, 0.14999998f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, -0.4886921f)
				.startKeyframe(24)
				.translate(parts[1], -0.5599998f, 0.0f, 0.0f)
				.translate(parts[6], 0.23000002f, 0.14999998f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, -0.4886921f)
				.startKeyframe(12, "easeInOutCirc")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		kickback = new Animation("kickbackAnim", "oldguns:scorpion")
				.startKeyframe(8, "easeInQuint")
				.translate(parts[1], -0.45999983f, -0.02f, 0.22000003f)
				.translate(parts[0], 0.0f, 0.0f, -0.25000003f)
				.translate(parts[2], 0.21000004f, -0.049999982f, -0.58999974f)
				.translate(parts[6], 0.0f, 0.0f, -0.20000002f)
				.rotate(parts[5], 0.0f, 0.17453294f, 0.0f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.startKeyframe(12)
				.translate(parts[1], -0.45999983f, -0.02f, 0.22000003f)
				.translate(parts[0], 0.0f, 0.0f, -0.25000003f)
				.translate(parts[2], 0.21000004f, -0.049999982f, -0.58999974f)
				.translate(parts[6], 0.0f, 0.0f, -0.20000002f)
				.rotate(parts[5], 0.0f, 0.17453294f, 0.0f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.startKeyframe(12, "easeInSine")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.end();
		getOutMag = new Animation("getOutMagAnim", "oldguns:scorpion")
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[7], 0.0f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.end();
		reloadNoMag = new Animation("reloadNoMagAnim", "oldguns:scorpion")
				.startKeyframe(12, "easeInQuint")
				.translate(parts[7], 0.0f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(18)
				.translate(parts[7], 0.0f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeOutQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.3999999f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.5199998f, 0.3899999f, 0.24000004f)
				.rotate(parts[6], 0.0f, 0.0f, 0.872665f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.3999999f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.33999997f, 0.3899999f, 0.24000004f)
				.rotate(parts[6], 0.0f, 0.0f, 0.872665f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInExpo")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.3999999f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.33999997f, 0.3899999f, 0.36999992f)
				.rotate(parts[6], 0.0f, 0.0f, 0.872665f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.3999999f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.33999997f, 0.3899999f, 0.24000004f)
				.rotate(parts[6], 0.0f, 0.0f, 0.872665f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.end();
		reloadMagByMag = new Animation("reloadMagByMagAnim", "oldguns:scorpion")
				.startKeyframe(12)
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.translate(parts[7], 0.0f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.startKeyframe(12)
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.translate(parts[7], 0.0f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.startKeyframe(12, "easeOutQuint")
				.translate(parts[1], 0.02f, -0.11999998f, 0.24000004f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.3799999f, 0.21000002f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.4014257f)
				.startKeyframe(12)
				.translate(parts[1], -0.5199998f, 0.3899999f, 0.24000004f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.3999999f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.872665f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], -0.33999997f, 0.3899999f, 0.24000004f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.3999999f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.872665f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInExpo")
				.translate(parts[1], -0.33999997f, 0.3899999f, 0.36999992f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.3999999f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.872665f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], -0.33999997f, 0.3899999f, 0.24000004f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.3999999f, -0.77999955f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.872665f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.end();
	}

	@Override
	public float getKnockback() {
		return 0;
	}

	@Override
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix, int light) {
		matrix.pushPose(); // 1+
		lerpGunStuff(matrix, stack, 9, 10, 11);
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		leftArmMag(matrix, player, buffer, light, 1, 7);
		matrix.pushPose(); // 3+
		translateAndRotateAndScale(parts[6].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		matrix.pushPose(); // 4+
		gunWithMag(matrix, player, stack, buffer, light, 4);
		lerpTransform(matrix, client.getCooldown().getProgress(NBTUtils.getId(stack)), 
				new Transform(0.0f, -0.010000001f, 0.33000004f, 0, 0, 0));
		hammer(matrix, player, stack, buffer, light, 3, Paths.SCORPIONHAMMER);
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
		fillReloadData(BulletItem.SMALL, player, stack, reloadMagByMag, reloadNoMag);
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
		return new ScorpionModModel(origin);
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
