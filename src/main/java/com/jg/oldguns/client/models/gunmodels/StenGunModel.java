package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.modmodels.StenModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.guns.MagItem;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StenGunModel extends GunModel {

	public static Animation look;
	public static Animation kickback;
	public static Animation reloadNoMag;
	public static Animation getOutMag;
	public static Animation reloadMagByMag;
	
	public StenGunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", -0.37f, 0.08f, 0.02f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", -0.48f, 0.55f, 0.33f, 0.005235f, -1.117001f, 0, 
						1.3f, 2.5f, 1.3f), 
				new GunModelPart("gun", 0.547f, -0.526f, -0.9f, 0.021459f, -0.034906f, 0),
				new GunModelPart("hammer"),
				new GunModelPart("mag"),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("leftarmmag"),
				new GunModelPart("leftarmhammer"),
				new GunModelPart("aim", -0.4268f, 0.371598f, 0.476f, -0.093025f, 0.029844f, 0), 
				new GunModelPart("sprint", 0.54f, 0.05f, -0.40099987f, 
						-0.16754894f, 1.326451f, -0.191986f),
				new GunModelPart("recoil", 0, 0, 0.02f, 0, 0, 0) }, 
				ItemRegistries.STEN.get(), client);
		
		look = new Animation("lookAnim", "oldguns:sten")
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
		kickback = new Animation("kickbackAnim", "oldguns:sten")
				.startKeyframe(12)
				.translate(parts[1], -0.5999997f, 0.0f, 0.7399996f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.31415927f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[1], -0.5999997f, 0.0f, 1.0699993f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.31415927f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.startKeyframe(4, "easeOutQuint")
				.translate(parts[1], -0.44999987f, 0.0f, -0.16999999f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.34906584f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], -0.44999987f, 0.0f, -0.16999999f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.34906584f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInCubic")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.end();
		
		kickback = new Animation("kickbackAnim", "oldguns:sten")
				.startKeyframe(8, "easeInQuint")
				.translate(parts[0], 0.0f, 0.0f, -0.25000003f)
				.translate(parts[1], -0.14999998f, -0.09999999f, -0.08999999f)
				.translate(parts[2], -0.15999998f, 0.21000004f, -0.45999986f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.rotate(parts[5], 0.0f, 0.17453294f, 0.0f)
				.startKeyframe(12)
				.translate(parts[0], 0.0f, 0.0f, -0.25000003f)
				.translate(parts[1], -0.14999998f, -0.09999999f, -0.08999999f)
				.translate(parts[2], -0.15999998f, 0.21000004f, -0.45999986f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.rotate(parts[5], 0.0f, 0.17453294f, 0.0f)
				.startKeyframe(12, "easeInSine")
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.end();
		getOutMag = new Animation("getOutMagAnim", "oldguns:sten")
				.startKeyframe(12, "easeOutBack")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(24)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		reloadNoMag = new Animation("reloadNoMagAnim", "oldguns:sten")
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(24)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(4, "easeInBack")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.01f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], -0.13999999f, 0.24000004f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(12)
				.translate(parts[1], -0.02f, 0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(6, "easeInQuint")
				.translate(parts[1], -0.02f, 0.22000003f, 0.17f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(8, "easeOutBack")
				.translate(parts[1], -0.02f, 0.22000003f, 0.17f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(6, "easeOutExpo")
				.translate(parts[1], -0.02f, 0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		reloadMagByMag = new Animation("reloadMagByMagAnim", "oldguns:sten")
				.startKeyframe(12, "easeOutBack")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(24)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, -0.67999965f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], -0.07999999f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(4, "easeInBack")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.01f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], -0.13999999f, 0.24000004f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(12)
				.translate(parts[1], -0.02f, 0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(6, "easeInQuint")
				.translate(parts[1], -0.02f, 0.22000003f, 0.17f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(8, "easeOutBack")
				.translate(parts[1], -0.02f, 0.22000003f, 0.17f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(6, "easeOutExpo")
				.translate(parts[1], -0.02f, 0.22000003f, 0.12999998f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.08999999f, -0.58999974f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.7853984f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
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
				new Transform(0.0f, 0.0f, 0.07699998f, 0, 0, 0));
		hammer(matrix, player, stack, buffer, light, 3, Paths.STENHAMMER);
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
			if(tick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGOUT.get());
			} else if(tick == 60) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGIN.get());
			} else if(tick == 103) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENHAMMERBACK.get());
			} else if(tick == 118) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENHAMMERFORWARD.get());
				ReloadHandler.restoreMag(getMPath(), ServerUtils.getBullets(Utils.getStack()));
				ReloadHandler.setBullets((int)data.get("magBullets"));
				ReloadHandler.removeItem((int)data.get("index"), 1);
			}
		} else if(anim == reloadNoMag) {
			if(tick == 18) {
				MagItem magItem = getMagItem((int)data.get("index"));
				ReloadHandler.setMag(this, magItem.getMaxAmmo(), true, 
						getMBPath((int)data.get("index")), magItem);
				ReloadHandler.removeItem((int)data.get("index"), 1);
				ReloadHandler.setBullets((int)data.get("magBullets"));
			} else if(tick == 41) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGIN.get());
			} else if(tick == 93) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENHAMMERBACK.get());
			} else if(tick == 107) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENHAMMERFORWARD.get());
			}
		} else if(anim == getOutMag) {
			if(tick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGOUT.get());
			} else if(tick == 26) {
				ReloadHandler.restoreMag(getMPath(), ServerUtils.getBullets(Utils.getStack()));
				ReloadHandler.setMag(this, 0, false, 
						"", "");
			}
		} else if(anim == kickback) {
			if(tick == 6) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			}
		}
	}

	@Override
	public void reload(Player player, ItemStack stack) {
		int index = ServerUtils
				.getIndexForCorrectMag(player.getInventory(), 
						gun.getGunId(),
				BulletItem.SMALL);
		if(index != -1) {
			data.put("index", index);
			LogUtils.getLogger().info("index: " + index);
			ItemStack mag = getMagStack(index);
			data.put("mag", mag);
			data.put("magBullets", NBTUtils.getAmmo(mag));
			if(NBTUtils.hasMag(stack)) {
				setAnimation(reloadMagByMag);
			} else {
				setAnimation(reloadNoMag);
			}
		}
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
		return new StenModModel(origin);
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
