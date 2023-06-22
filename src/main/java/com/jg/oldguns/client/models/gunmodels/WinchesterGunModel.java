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
import com.jg.oldguns.client.models.modmodels.WinchesterModModel;
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

public class WinchesterGunModel extends GunModel {
	
	public static Animation look;
	public static RepetitiveAnimation reload;
	public static Animation kickback;
	protected int times;
	
	public WinchesterGunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", -0.11f, -0.18f, -0.31f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", -0.11f, 0.51f, -0.61f, 0.005235f, -1.117001f, 0, 1.3f, 2.5f, 1.3f), 
				new GunModelPart("gun", 0.667001f, -0.446002f, -1.1f, 0.021459f, -0.034906f, 0),
				new GunModelPart("hammer", 0.667001f, -0.446002f, -1.1f, 0.021459f, -0.034906f, 0),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("aim", -0.728f, 0.176f, 0, -0.027925f, 0.040142f, 0), 
				new GunModelPart("sprint", 1.32f, -0.89f, -0.221f, 0.548036f, 1.326451f, -0.191986f),
				new GunModelPart("recoil", 0.02f, -0.18f, 0.02f, 0.139626f, -0.069813f, 0) }, 
				ItemRegistries.WINCHESTER.get(), client);

		look = new Animation("lookAnim", "oldguns:winchester", AnimationType.LOOK)
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
		
		reload = new RepetitiveAnimation("reloadAnim", "oldguns:winchester")
				.startKeyframe(12)
				.translate(parts[5], 0.30999997f, 0.12999998f, 0.0f)
				.translate(parts[1], -0.21000002f, -0.11999998f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.38397238f)
				.rotate(parts[1], -0.80285174f, 0.0f, 0.0f)
				.startKeyframe(12)
				.type(1)
				.translate(parts[5], 0.30999997f, 0.12999998f, 0.0f)
				.translate(parts[1], 0.049999982f, 1.4901161E-8f, 0.02f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, -0.38397238f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[5], 0.30999997f, 0.12999998f, 0.0f)
				.translate(parts[1], 0.049999982f, 0.040000014f, 0.02f)
				.rotate(parts[5], 0.0f, 0.0f, -0.38397238f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInQuint")
				.type(2)
				.translate(parts[5], 0.30999997f, 0.12999998f, 0.0f)
				.translate(parts[1], 0.049999982f, 0.080000006f, -0.109999985f)
				.translate(parts[2], 0.0f, 0.0f, -0.02f)
				.rotate(parts[5], 0.0f, 0.0f, -0.38397238f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.end();
		kickback = new Animation("kickbackAnim", "oldguns:winchester", AnimationType.MELEEHIT)
				.startKeyframe(8, "easeInQuint")
				.translate(parts[2], 0.55999976f, -0.29f, -0.58999974f)
				.translate(parts[4], -0.87999946f, 0.0f, 0.0f)
				.translate(parts[0], 0.5399998f, 0.02f, -0.6099997f)
				.translate(parts[1], -0.8199995f, -0.09999999f, 0.38999993f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.rotate(parts[4], 0.0f, 0.17453294f, 0.0f)
				.startKeyframe(12, "easeInOutBounce")
				.translate(parts[2], 0.55999976f, -0.29f, -0.58999974f)
				.translate(parts[4], -0.87999946f, 0.0f, 0.0f)
				.translate(parts[0], 0.5399998f, 0.02f, -0.6099997f)
				.translate(parts[1], -0.8199995f, -0.09999999f, 0.38999993f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.rotate(parts[4], 0.0f, 0.17453294f, 0.0f)
				.startKeyframe(12, "easeInSine")
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[4], 0.0f, 0.0f, 0.0f)
				.end();
		
		/*kickback = new Animation("kickbackAnim", "oldguns:winchester")
				.startKeyframe(12, "easeInBack")
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, -0.13962634f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.startKeyframe(8)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -2.0099986f, 0.11999998f, 0.9399994f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.33999994f, 0.16f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.66322523f, 0.0f)
				.rotate(parts[2], 0.0f, 1.2042779f, 0.0f)
				.rotate(parts[0], 0.0f, 0.45378554f, 0.0f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -2.0099986f, 0.11999998f, 0.9399994f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.33999994f, 0.16f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.66322523f, 0.0f)
				.rotate(parts[2], 0.0f, 1.2042779f, 0.0f)
				.rotate(parts[0], 0.0f, 0.45378554f, 0.0f)
				.startKeyframe(12, "easeInCubic")
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.end();*/
		
		shootAnim = new Animation("shootAnim", "oldguns:winchester", AnimationType.SHOOT)
				.startKeyframe(8)
				.translate(parts[5], 0.0f, -0.3799999f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.07999999f, -0.19000001f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.296706f)
				.startKeyframe(12)
				.translate(parts[0], 0.0f, 0.0f, -0.30999997f)
				.translate(parts[5], 0.0f, -0.3799999f, 0.0f)
				.translate(parts[1], 0.07999999f, -0.19000001f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.296706f)
				.startKeyframe(8)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, -0.3799999f, 0.0f)
				.translate(parts[1], 0.07999999f, -0.19000001f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.296706f)
				.startKeyframe(8)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.end();
	}

	@Override
	public void tick(Player player, ItemStack stack) {
		super.tick(player, stack);
		float tick = animator.getTick();
		if(getAnimation() == shootAnim) {
			if(tick == 18) {
				SoundHandler.playSoundOnServer(SoundRegistries.WINCHESTERBOLTFORWARD.get());
			} else if(tick == 26) {
				SoundHandler.playSoundOnServer(SoundRegistries.WINCHESTERBOLTBACK.get());
			}
		} else if(getAnimation() == reload) {
			if(isRepTick(12, 41, tick, 36, times)) {
				SoundHandler.playSoundOnServer(SoundRegistries.WINCHESTERINSERTSHELL.get());
				ReloadHandler.growOneBullet(stack);
				LogUtils.getLogger().info("Bullet inserted");
			}
		} else if(getAnimation() == kickback) {
			if(tick == 4) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} else if(tick == 8) {
				MeleeHelper.hit(Config.SERVER.winchesterMeleeDmg.get().floatValue());
			}
		}
	}
	
	@Override
	public float getKnockback() {
		return 0;
	}
	
	@Override
	public void shoot(Player player, ItemStack stack) {
		super.shoot(player, stack);
		setAnimation(shootAnim);
	}

	@Override
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer,  
			MultiBufferSource.BufferSource impl, PoseStack matrix, int light) {
		renderOneHammerNoMag(matrix, stack, player, buffer, light);
	}

	@Override
	public void reload(Player player, ItemStack stack) {
		times = fillReloadDataNoMag(ItemRegistries.BigBullet.get(), player, reload, 
				stack, 6);
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
	public List<GunModelPart> getGunParts() {
		return List.of(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
	}

	@Override
	public GunModelPart getGunModelPart() {
		return parts[2];
	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		return new WinchesterModModel(origin);
	}

	@Override
	public Vector3f getMuzzleFlashPosition() {
		return new Vector3f(0.1f, 0.16f, -2.73f);
	}
	
}
