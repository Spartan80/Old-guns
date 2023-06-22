package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Animation.AnimationType;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.modmodels.Aks74uModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.MeleeHelper;
import com.jg.oldguns.utils.Paths;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Aks74uGunModel extends GunModel {

	public static Animation look;
	public static Animation kickback;
	public static Animation reloadNoMag;
	public static Animation getOutMag;
	public static Animation reloadMagByMag;
	
	public Aks74uGunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", -0.11f, -0.18f, -0.31f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", -0.12f, 0.309f, -0.08f, 0.005235f, -1.117001f, 0, 1.3f, 2.5f, 1.3f), 
				new GunModelPart("gun", 0.987f, -0.926f, -1.56f, 0.021459f, -0.034906f, 0),
				new GunModelPart("hammer"),
				new GunModelPart("mag"),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("leftarmmag"),
				new GunModelPart("aim", -0.712f, 0.45f, 0.691f, -0.0209433f, 0.036651f, 0), 
				new GunModelPart("sprint", 1.32f, -0.89f, -0.221f, 0.548036f, 1.326451f, -0.191986f),
				new GunModelPart("recoil", 0, 0, 0.07999999f, 0, 0, 0) }, 
				ItemRegistries.AKS74U.get(), client);
		
		shootAnim = new Animation("shootAnim", "oldguns:aks-74u", AnimationType.SHOOT)
				.startKeyframe(3)
				.translate(parts[3], 0.0f, -0.010000001f, 0.33000004f)
				.startKeyframe(3)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.end();
		look = new Animation("lookAnim", "oldguns:aks-74u", AnimationType.LOOK)
				.startKeyframe(12, "easeOutQuint")
				.translate(parts[5], 0.0f, 0.45999983f, 0.0f)
				.translate(parts[1], -1.1399993f, 0.0f, 0.0f)
				.translate(parts[6], -0.8599995f, -0.68999964f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, 0.5934119f)
				.startKeyframe(24)
				.translate(parts[5], 0.0f, 0.45999983f, 0.0f)
				.translate(parts[1], -1.1399993f, 0.0f, 0.0f)
				.translate(parts[6], -0.8599995f, -0.68999964f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, 0.5934119f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[5], 0.0f, 0.45999983f, 0.0f)
				.translate(parts[1], -0.5599998f, 0.0f, 0.0f)
				.translate(parts[6], 0.23000002f, 0.14999998f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, -0.4886921f)
				.startKeyframe(24)
				.translate(parts[5], 0.0f, 0.45999983f, 0.0f)
				.translate(parts[1], -0.5599998f, 0.0f, 0.0f)
				.translate(parts[6], 0.23000002f, 0.14999998f, 0.0f)
				.rotate(parts[6], 0.0f, -0.17453294f, -0.4886921f)
				.startKeyframe(12, "easeInOutCirc")
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		kickback = new Animation("kickbackAnim", "oldguns:aks-74u", AnimationType.MELEEHIT)
				.startKeyframe(8, "easeInQuint")
				.translate(parts[1], -0.33999994f, -0.04f, -0.03f)
				.translate(parts[0], 0.0f, 0.0f, -0.25000003f)
				.translate(parts[2], -0.6499997f, 0.4099999f, -0.32f)
				.rotate(parts[5], 0.0f, 0.593412f, 0.0f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.startKeyframe(12, "easeInOutBounce")
				.translate(parts[1], -0.33999994f, -0.04f, -0.03f)
				.translate(parts[0], 0.0f, 0.0f, -0.25000003f)
				.translate(parts[2], -0.6499997f, 0.4099999f, -0.32f)
				.rotate(parts[5], 0.0f, 0.593412f, 0.0f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.startKeyframe(12, "easeInSine")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.end();
		getOutMag = new Animation("getOutMagAnim", "oldguns:aks-74u", AnimationType.RELOAD)
				.startKeyframe(12, "easeInSine")
				.translate(parts[6], 0.3799999f, 0.0f, 0.0f)
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(8, "easeOutQuint")
				.translate(parts[6], 0.3799999f, -0.02f, 0.0f)
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, -0.46999982f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(12)
				.translate(parts[6], 0.3799999f, -0.02f, 0.0f)
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, -0.46999982f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(20)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		reloadNoMag = new Animation("reloadNoMagAnim", "oldguns:aks-74u", AnimationType.RELOAD)
				.startKeyframe(8)
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, -0.46999982f, 0.0f)
				.translate(parts[6], 0.3799999f, -0.02f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(12)
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, -0.46999982f, 0.0f)
				.translate(parts[6], 0.3799999f, -0.02f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.3799999f, -0.02f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(12)
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.3799999f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(12)
				.translate(parts[1], -0.33999994f, -0.059999995f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(12)
				.translate(parts[1], -0.28f, 0.31999996f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(8)
				.translate(parts[1], -0.28f, 0.31999996f, 0.22000003f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(4, "easeInExpo")
				.translate(parts[1], -0.28f, 0.31999996f, 0.22000003f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(8, "easeInExpo")
				.translate(parts[1], -0.28f, 0.31999996f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(20)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		reloadMagByMag = new Animation("reloadMagByMagAnim", "oldguns:aks-74u", AnimationType.RELOAD)
				.startKeyframe(12)
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.3799999f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(8, "easeOutQuint")
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, -0.46999982f, 0.0f)
				.translate(parts[6], 0.3799999f, -0.02f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(12)
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, -0.46999982f, 0.0f)
				.translate(parts[6], 0.3799999f, -0.02f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.3799999f, -0.02f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(12)
				.translate(parts[1], 0.24000004f, -0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.3799999f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.17453294f)
				.startKeyframe(12)
				.translate(parts[1], -0.33999994f, -0.059999995f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(12)
				.translate(parts[1], -0.28f, 0.31999996f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(8)
				.translate(parts[1], -0.28f, 0.31999996f, 0.22000003f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(4, "easeInExpo")
				.translate(parts[1], -0.28f, 0.31999996f, 0.22000003f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(8, "easeInExpo")
				.translate(parts[1], -0.28f, 0.31999996f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.34999996f, -0.66999966f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7155851f)
				.startKeyframe(20)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
	}

	@Override
	public float getKnockback() {
		return 0;
	}

	@Override
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, 
			MultiBufferSource.BufferSource impl, PoseStack matrix, int light) {
		matrix.pushPose(); // 1+
		lerpGunStuff(matrix, stack, 8, 9, 10);
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		leftArmMag(matrix, player, buffer, light, 1, 7);
		matrix.pushPose(); // 3+
		translateAndRotateAndScale(parts[6].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		matrix.pushPose(); // 4+
		gunWithMag(matrix, player, stack, buffer, impl, light, 4);
		lerpTransform(matrix, client.getCooldownRecoil().getCooldownPercent(gun, 
				Minecraft.getInstance().getFrameTime()), 
				new Transform(0.0f, -0.010000001f, 0.33000004f, 0, 0, 0));
		hammer(matrix, player, stack, buffer, light, 3, Paths.AKS74UHAMMER);
		matrix.popPose();
		matrix.popPose(); // 3-
		matrix.popPose(); // 1-
	}

	@Override
	public void tick(Player player, ItemStack stack) {
		super.tick(player, stack);
		
		Animation anim = getAnimation();
		float tick = animator.getTick();
		if(anim == reloadMagByMag) {
			if(tick == 10) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGOUT.get());
			} else if(tick == 40) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGIN.get());
			} else if(tick == 88) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UHAMMERBACK.get());
			}  else if(tick == 98) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UHAMMERFORWARD.get());
				reloadMagByMagStuff();
			} 
		} else if(anim == reloadNoMag) {
			if(tick == 18) {
				reloadNoMagStuff();
			} else if(tick == 24) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGIN.get());
			} else if(tick == 72) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UHAMMERBACK.get());
			} else if(tick == 86) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UHAMMERFORWARD.get());
			}
		} else if(anim == getOutMag) {
			if(tick == 10) {
				SoundHandler.playSoundOnServer(SoundRegistries.AK74UMAGOUT.get());
			} else if(tick == 22) {
				getOutMagStuff();
			}
		} else if(anim == kickback) {
			if(tick == 3) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} else if(tick == 8) {
				MeleeHelper.hit(Config.SERVER.aksMeleeDmg.get().floatValue());
			}
		}
	}
	
	@Override
	public void reload(Player player, ItemStack stack) {
		fillReloadData(BulletItem.MEDIUM, player, stack, reloadMagByMag, reloadNoMag);
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
		return new Aks74uModModel(origin);
	}

	@Override
	public List<GunModelPart> getGunParts() {
		return List.of(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
	}

	@Override
	public GunModelPart getGunModelPart() {
		return parts[2];
	}

	@Override
	public Vector3f getMuzzleFlashPosition() {
		return new Vector3f(-0.23f, 0.39f, -0.41f);
	}

}
