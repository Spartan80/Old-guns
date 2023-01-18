package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.modmodels.Colt1911ModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.Paths;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Colt1911GunModel extends GunModel {

	public static Animation look;
	public static Animation kickback;
	public static Animation reloadNoMag;
	public static Animation getOutMag;
	public static Animation reloadMagByMagEeasterEgg;
	public static Animation reloadMagByMag;
	
	public Colt1911GunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				/*new GunModelPart("rightarm", 0.42f, -0.62f, -1.1f, -0.6f, 0, 0.6f, 
						1.3f, 2.5f, 1.3f), */
				new GunModelPart("rightarm", -0.0500001f, -0.19999996f, -0.9999999f, -0.6f, 0, 0.6f, 
						1.3f, 2.5f, 1.3f), 
				new GunModelPart("leftarm", 0.43f, -0.309f, -0.51f, 0.005235f, -1.117001f, 0, 
						1.3f, 2.5f, 1.3f), 
				new GunModelPart("gun", 0.987f, -0.906f, -1.13f, 0.021459f, -0.034906f, 0),
				new GunModelPart("hammer"),
				new GunModelPart("slider"),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("leftarmhammerslider"),
				new GunModelPart("aim", -1.239f, 0.973993f, 0.923997f, -0.075048f, 0.036651f, 0), 
				new GunModelPart("sprint", 0f, -1.589998f, 0f, 0.733038f, -0.025481f, 0f),
				new GunModelPart("recoil", 0, -0.2f, 0.29999995f, 0.104719f, 0, 0) }, 
				ItemRegistries.COLT1911.get(), client);
		
		shootAnim = new Animation("shootAnim", "oldguns:colt1911")
				.startKeyframe(3)
				.translate(parts[5], 0, -0.2f, 0.24f)
				.rotate(parts[5], 0.104719f, 0, 0)
				.startKeyframe(3)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0f, 0f, 0f)
				.end();
		look = new Animation("lookAnim", "oldguns:colt1911")
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
		kickback = new Animation("kickbackAnim", "oldguns:colt1911")
				.startKeyframe(12)
				.translate(parts[1], -1.0599993f, 0.88999945f, 1.0699993f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.31415927f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[1], -1.2799991f, 0.88999945f, 1.2899991f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.31415927f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.startKeyframe(4, "easeOutQuint")
				.translate(parts[1], -1.1799992f, 0.88999945f, -0.06999992f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, -0.15707964f, 0.0f)
				.rotate(parts[6], -0.34906584f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[1], -1.1799992f, 0.88999945f, -0.06999992f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, -0.15707964f, 0.0f)
				.rotate(parts[6], -0.34906584f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12, "easeInCubic")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[0], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.end();
		kickback = new Animation("kickbackAnim", "oldguns:colt1911")
				.startKeyframe(8, "easeIntQuint")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 1.2299991f, -1.5899988f, 0.7499996f)
				.rotate(parts[1], -0.47123882f, 0.6981318f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 1.2740911f, 1.047198f)
				.startKeyframe(8, "easeInExpo")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.60999966f, -1.3199991f, 0.43999988f)
				.rotate(parts[1], -0.47123882f, 0.6981318f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.89011824f, 1.047198f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.60999966f, -1.3199991f, 0.43999988f)
				.rotate(parts[1], -0.47123882f, 0.6981318f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.89011824f, 1.047198f)
				.startKeyframe(12, "easeInSine")
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		getOutMag = new Animation("getOutMagAnim", "oldguns:colt1911")
				.startKeyframe(12, "easeInCubic")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3799999f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.17453294f, 0.0f, 0.0f)
				.startKeyframe(8)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3799999f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.104719736f, 0.0f, 0.0f)
				.startKeyframe(8)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3799999f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.104719736f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		reloadNoMag = new Animation("reloadNoMagAnim", "oldguns:colt1911")
				.startKeyframe(18, "easeInOutSine")
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3799999f, 0.0f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.36999992f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3799999f, -0.45999983f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.36999992f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3799999f, -0.45999983f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.36999992f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.3799999f, -0.45999983f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.36999992f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12, "easeInOutExpo")
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.22000004f, -0.09999997f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.3899999f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.5099998f, 0.27000004f, 0.0f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(12)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.39999992f, 0.79999954f, -0.019999985f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(6, "easeOutExpo")
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.1999999f)
				.translate(parts[7], 0.0f, 0.0f, 0.11999998f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.39999992f, 0.79999954f, -0.019999985f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(6, "easeOutCirc")
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.1999999f)
				.translate(parts[7], 0.0f, 0.0f, 0.11999998f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.39999992f, 0.79999954f, -0.019999985f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(6, "easeOutCirc")
				.translate(parts[3], 0.0f, 0.0f, -0.109999985f)
				.translate(parts[8], 0.0f, 0.0f, 0.1999999f)
				.translate(parts[7], 0.0f, 0.0f, 0.11999998f)
				.translate(parts[4], 0.0f, 0.0f, -0.109999985f)
				.translate(parts[1], -0.39999992f, 0.79999954f, -0.019999985f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(12)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		reloadMagByMagEeasterEgg = new Animation("reloadMagByMagAnim", "oldguns:colt1911")
				.startKeyframe(12, "easeInQuint")
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.17f, 0.31999996f, 0.5099998f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.17f, 0.29999998f, 0.79999954f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.17f, 0.33999994f, 0.79999954f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.039999995f, 0.0f)
				.translate(parts[7], 0.0f, -0.8499995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(20, "easeOutExpo")
				.translate(parts[6], 0.0f, 0.039999995f, 0.0f)
				.translate(parts[7], 0.0f, -0.8499995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.039999995f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(6, "easeInQuint")
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.27f, 0.16999999f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.25000003f, 0.4799998f, 0.77999955f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(4, "easeOutExpo")
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.01f, 0.29999998f)
				.translate(parts[1], -0.25000003f, 0.35999992f, 0.77999955f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(4, "easeOutExpo")
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.01f, 0.29999998f)
				.translate(parts[1], -0.25000003f, 0.35999992f, 0.77999955f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(2)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.25000003f, 0.35999992f, 0.77999955f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.25000003f, -0.080000006f, 0.23000005f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		reloadMagByMag = new Animation("reloadMagByMagAnim", "oldguns:colt1911")
				.startKeyframe(12, "easeInCubic")
				.translate(parts[1], -0.3799999f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.17453294f, 0.0f, 0.0f)
				.startKeyframe(8)
				.translate(parts[1], -0.3799999f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.104719736f, 0.0f, 0.0f)
				.startKeyframe(12, "easeOutExpo")
				.translate(parts[1], -0.3799999f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], -0.104719736f, 0.0f, 0.0f)
				.startKeyframe(18, "easeInOutSine")
				.translate(parts[1], -0.3799999f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.36999992f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12)
				.translate(parts[1], -0.3799999f, -0.45999983f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.36999992f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12)
				.translate(parts[1], -0.3799999f, -0.45999983f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.36999992f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12)
				.translate(parts[1], -0.3799999f, -0.45999983f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.36999992f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12, "easeInOutExpo")
				.translate(parts[1], 0.22000004f, -0.09999997f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.41999987f, 0.3899999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, -0.24434613f)
				.startKeyframe(12)
				.translate(parts[1], -0.5099998f, 0.27000004f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(12)
				.translate(parts[1], -0.39999992f, 0.79999954f, -0.019999985f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[8], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(4, "easeOutBack")
				.translate(parts[1], -0.39999992f, 0.79999954f, -0.019999985f)
				.translate(parts[7], 0.0f, 0.0f, 0.09999999f)
				.translate(parts[8], 0.0f, 0.0f, 0.1999999f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(6, "easeOutCirc")
				.translate(parts[1], -0.39999992f, 0.79999954f, -0.019999985f)
				.translate(parts[7], 0.0f, 0.0f, 0.09999999f)
				.translate(parts[8], 0.0f, 0.0f, 0.1999999f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(6, "easeOutCirc")
				.translate(parts[4], 0.0f, 0.0f, -0.109999985f)
				.translate(parts[1], -0.39999992f, 0.79999954f, -0.019999985f)
				.translate(parts[3], 0.0f, 0.0f, -0.109999985f)
				.translate(parts[8], 0.0f, 0.0f, 0.1999999f)
				.translate(parts[7], 0.0f, 0.0f, 0.11999998f)
				.translate(parts[6], -0.6299997f, -0.66999966f, 0.02f)
				.rotate(parts[6], 0.06981318f, -0.034906585f, 0.43633223f)
				.startKeyframe(12)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
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
		shootAnim = new Animation("shootAnim", "oldguns:colt1911")
				.startKeyframe(3)
				.translate(parts[5], 0, -0.2f, 0.24f)
				.rotate(parts[5], 0.104719f, 0, 0)
				.translate(parts[3], 0.0f, -0.010000001f, 0.13000004f)
				.translate(parts[4], 0.0f, -0.010000001f, 0.13000004f)
				.startKeyframe(6)
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0f, 0f, 0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[4], 0.0f, 0.0f, 0.0f)
				.end();
		
		matrix.pushPose(); // 1+
		lerpGunStuff(matrix, stack, 8, 9, 10);
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		matrix.pushPose(); // 2+
		translateAndRotateAndScale(parts[7].getCombined(), matrix);
		leftArm(matrix, player, buffer, light, 1);
		matrix.popPose(); // 2-
		matrix.pushPose(); // 3+
		translateAndRotateAndScale(parts[6].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		matrix.pushPose(); // 4+
		renderGunPart(player, stack, buffer, matrix, light);
		matrix.pushPose();
		translateAndRotateAndScale(parts[7].getCombined(), matrix);
		lerpTransform(matrix, client.getCooldownRecoil().getCooldownPercent(gun, 
				Minecraft.getInstance().getFrameTime()), 
				new Transform(0.0f, -0.010000001f, 0.13000004f, 0, 0, 0));
		hammer(matrix, player, stack, buffer, light, 3, Paths.COLT1911HAMMER);
		hammer(matrix, player, stack, buffer, light, 4, Paths.COLT1911SLIDER);
		matrix.popPose();
		matrix.popPose(); // 4-
		matrix.popPose(); // 3-
		matrix.popPose(); // 1-
	}

	@Override
	public void tick(Player player, ItemStack stack) {
		super.tick(player, stack);
		Animation anim = getAnimation();
		float tick = animator.getTick();
		if(anim == reloadMagByMag) {
			if(tick == 20) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get());
			} else if(tick == 96) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get());
			} else if(tick == 126) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get());
				reloadMagByMagStuff();
			}
		} else if(anim == reloadNoMag) {
			if(tick == 60) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGIN.get());
			} else if(tick == 93) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTSLIDER.get());
				reloadNoMagStuff();
			}
		} else if(anim == getOutMag) {
			if(tick == 20) {
				SoundHandler.playSoundOnServer(SoundRegistries.COLTMAGOUT.get());
				getOutMagStuff();
			}
		}
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
		return new Colt1911ModModel(origin);
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