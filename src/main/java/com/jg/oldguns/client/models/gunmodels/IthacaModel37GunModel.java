package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.RepetitiveAnimation;
import com.jg.oldguns.client.animations.Animation.AnimationType;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.modmodels.IthacaModel37ModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.config.Config;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.InventoryUtils;
import com.jg.oldguns.utils.MeleeHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3f;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
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
				new GunModelPart("recoil", 0.024000002f, 0, 0.42799804f, 0, -0.06981317f, -0.03490659f) }, 
				ItemRegistries.ITHACAMODEL37.get(), client);
		
		look = new Animation("lookAnim", "oldguns:ithacamodel37", AnimationType.LOOK)
				.startKeyframe(12, "easeOutSine")
				.translate(parts[5], -0.7499996f, -0.57999974f, 0.0f)
				.translate(parts[1], -1.1499993f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.61086524f)
				.startKeyframe(36)
				.translate(parts[5], -0.7499996f, -0.57999974f, 0.0f)
				.translate(parts[1], -1.1499993f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.61086524f)
				.startKeyframe(12, "easInSine")
				.translate(parts[5], 0.4799998f, 0.31999996f, 0.0f)
				.translate(parts[1], -0.7099997f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.6457719f)
				.startKeyframe(36)
				.translate(parts[5], 0.4799998f, 0.31999996f, 0.0f)
				.translate(parts[1], -0.7099997f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.6457719f)
				.startKeyframe(12)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.end();

		reload = new RepetitiveAnimation("reloadAnim", "oldguns:ithacamodel37")
				.startKeyframe(12)
				.translate(parts[5], 0.4999998f, 0.37999994f, 0.0f)
				.translate(parts[1], -0.21000002f, -0.11999998f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.50614536f)
				.rotate(parts[1], -0.80285174f, 0.0f, 0.0f)
				.startKeyframe(8)
				.type(1)
				.translate(parts[5], 0.4999998f, 0.37999994f, 0.0f)
				.translate(parts[1], -0.16000001f, 0.11f, 0.3999999f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.50614536f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[5], 0.4999998f, 0.37999994f, 0.0f)
				.translate(parts[1], -0.080000006f, 0.20000003f, 0.3999999f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.50614536f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(6)
				.translate(parts[5], 0.4999998f, 0.37999994f, 0.0f)
				.translate(parts[1], -0.080000006f, 0.22000004f, 0.26000002f)
				.translate(parts[2], 0.0f, 0.0f, -0.02f)
				.rotate(parts[5], 0.0f, 0.0f, -0.50614536f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(12, "easeOutQuint")
				.type(2)
				.translate(parts[5], 0.4999998f, 0.37999994f, 0.0f)
				.translate(parts[1], -0.080000006f, 0.22000004f, 0.26000002f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.50614536f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(16)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.end();
		kickback = new Animation("kickbackAnim", "oldguns:ithacamodel37", AnimationType.MELEEHIT)
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

		shootAnim = new Animation("shootAnim", "oldguns:ithacamodel37", AnimationType.SHOOT)
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
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer,  
			MultiBufferSource.BufferSource impl, PoseStack matrix, int light) {
		renderOneHammerNoMag(matrix, stack, player, buffer, light);
		/*float[] pos = parts[8].getTransform().pos;
		return new Vector3f(pos[0], pos[1], pos[2]);*/
		
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
	public void shoot(Player player, ItemStack stack) {
		super.shoot(player, stack);
		setAnimation(shootAnim);
	}
	
	@Override
	public void tick(Player player, ItemStack stack) {
		super.tick(player, stack);
		Animation anim = getAnimation();
		float tick = animator.getTick();
		if(anim == shootAnim) {
			if(tick == 10) {
				SoundHandler.playSoundOnServer(SoundRegistries.MODEL37_SLIDE_BACK.get());
			} else if(tick == 30) {
				SoundHandler.playSoundOnServer(SoundRegistries.MODEL37_SLIDE_FORWARD.get());
			}
		} else if(anim == reload) {
			if(isRepTick(12, 30, tick, 38, times)) {
				SoundHandler.playSoundOnServer(SoundRegistries.MODEL37_SHELL_INSERT.get());
				ReloadHandler.growOneBullet(stack);
				//ReloadHandler.removeOneItemFrom(times);
				LogUtils.getLogger().info("Bullet inserted");
			}
		} else if(anim == kickback) {
			if(tick == 4) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} else if(tick == 8) {
				MeleeHelper.hit(Config.SERVER.ithacaModel37MeleeDmg.get().floatValue());
			}
		}
	}
	
	@Override
	public void reload(Player player, ItemStack stack) {
		times = fillReloadDataNoMag(ItemRegistries.ShotgunBullet.get(), player, reload, 
				stack, 5);
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

	@Override
	public Vector3f getMuzzleFlashPosition() {
		return new Vector3f(0.43f, 0.08f, -3.49f);
	}
	
}
