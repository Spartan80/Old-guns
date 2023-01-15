package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Transform;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.models.modmodels.Mp40ModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.guns.BulletItem;
import com.jg.oldguns.guns.MagItem;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.NBTUtils;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

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

public class Mp40GunModel extends GunModel {

	public static Animation look;
	public static Animation kickback;
	public static Animation reloadNoMag;
	public static Animation getOutMag;
	public static Animation reloadMagByMag;
	
	public Mp40GunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", -0.11f, -0.18f, -0.31f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", -0.11f, 0.36f, -0.61f, 0.005235f, -1.117001f, 0), 
				new GunModelPart("gun", 0.667001f, -0.726f, -1.21f, 0.021459f, -0.034906f, 0),
				new GunModelPart("hammer", 0.667001f, -0.726f, -1.21f, 0.021459f, -0.034906f, 0),
				new GunModelPart("mag", 0.667001f, -0.726f, -1.21f, 0.021459f, -0.034906f, 0),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("leftarmmag"),
				new GunModelPart("aim", -0.67f, 0.392f, 0.691f, -0.013962f, 0.036651f, 0), 
				new GunModelPart("sprint", 1.32f, -0.89f, -0.221f, 0.548036f, 1.326451f, -0.191986f),
				new GunModelPart("recoil", 0, 0, 0.03f, 0, 0, 0) }, 
				ItemRegistries.MP40.get(), client);
		
		shootAnim = new Animation("shootAnim", "oldguns:mp40")
				.startKeyframe(1)
				.translate(parts[3], 0.0f, -0.010000001f, 0.33000004f)
				.startKeyframe(1)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.end();
		look = new Animation("lookAnim", "oldguns:mp40")
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
		kickback = new Animation("kickbackAnim", "oldguns:mp40")
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
		kickback = new Animation("kickbackAnim", "oldguns:mp40")
				.startKeyframe(8, "easeInQuint")
				.translate(parts[5], -0.41999987f, 0.0f, 0.0f)
				.translate(parts[2], 0.080000006f, 0.020000014f, -0.34999996f)
				.translate(parts[0], 0.0f, 0.0f, -0.25000003f)
				.translate(parts[1], -0.57999974f, -0.02f, 0.55999976f)
				.rotate(parts[5], 0.0f, 0.17453294f, 0.0f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.startKeyframe(12)
				.translate(parts[5], -0.41999987f, 0.0f, 0.0f)
				.translate(parts[2], 0.080000006f, 0.020000014f, -0.34999996f)
				.translate(parts[0], 0.0f, 0.0f, -0.25000003f)
				.translate(parts[1], -0.57999974f, -0.02f, 0.55999976f)
				.rotate(parts[5], 0.0f, 0.17453294f, 0.0f)
				.rotate(parts[2], 0.0f, 1.6929706f, 1.3962643f)
				.startKeyframe(12, "easeInSine")
				.translate(parts[5], 0.0f, 0.0f, 0.0f)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[0], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[5], 0.0f, 0.0f, 0.0f)
				.rotate(parts[2], 0.0f, 0.0f, 0.0f)
				.end();
		getOutMag = new Animation("getOutMagAnim", "oldguns:mp40")
				.startKeyframe(12)
				.translate(parts[6], 0.0f, -0.01f, 0.0f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.translate(parts[7], 0.0f, -0.8499995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.startKeyframe(18)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.translate(parts[7], 0.0f, -0.8499995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.startKeyframe(12)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.end();
		reloadNoMag = new Animation("reloadNoMagAnim", "oldguns:mp40")
				.startKeyframe(12, "easeInQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.17f, 0.31999996f, 0.5099998f)
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.17f, 0.29999998f, 0.79999954f)
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.17f, 0.33999994f, 0.79999954f)
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, -0.8499995f, 0.0f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.translate(parts[6], 0.0f, 0.039999995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(20, "easeOutExpo")
				.translate(parts[7], 0.0f, -0.8499995f, 0.0f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.translate(parts[6], 0.0f, 0.039999995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.translate(parts[6], 0.0f, 0.039999995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(6, "easeInQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.27f, 0.16999999f, 0.20000003f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.25000003f, 0.4799998f, 0.77999955f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(4, "easeOutExpo")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.25000003f, 0.35999992f, 0.77999955f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.01f, 0.29999998f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(4, "easeOutExpo")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.25000003f, 0.35999992f, 0.77999955f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.01f, 0.29999998f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(2)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.25000003f, 0.35999992f, 0.77999955f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.25000003f, -0.080000006f, 0.23000005f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
		reloadMagByMag = new Animation("reloadMagByMagAnim", "oldguns:mp40")
				.startKeyframe(12, "easeInQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.17f, 0.31999996f, 0.5099998f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.17f, 0.29999998f, 0.79999954f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12, "easeInQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, -0.11999998f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.17f, 0.33999994f, 0.79999954f)
				.rotate(parts[6], 0.0f, 0.0f, 0.17453294f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, -0.8499995f, 0.0f)
				.translate(parts[6], 0.0f, 0.039999995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(20, "easeOutExpo")
				.translate(parts[7], 0.0f, -0.8499995f, 0.0f)
				.translate(parts[6], 0.0f, 0.039999995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.039999995f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(6, "easeInQuint")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.029999984f, -0.17000002f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.27f, 0.16999999f, 0.20000003f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.29999998f)
				.translate(parts[1], -0.25000003f, 0.4799998f, 0.77999955f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(4, "easeInExpo")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.01f, 0.29999998f)
				.translate(parts[1], -0.25000003f, 0.35999992f, 0.77999955f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(4, "easeOutExpo")
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.01f, 0.29999998f)
				.translate(parts[1], -0.25000003f, 0.35999992f, 0.77999955f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(2)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.25000003f, 0.35999992f, 0.77999955f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.06999999f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.25000003f, -0.080000006f, 0.23000005f)
				.rotate(parts[6], 0.0f, 0.0f, -0.034906574f)
				.startKeyframe(12)
				.translate(parts[7], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.translate(parts[3], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
	}

	@Override
	public float getKnockback() {
		return 0;
	}

	@Override
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix, int light) {
		matrix.pushPose();
		lerpTransform(matrix, client.getAimHandler().getProgress(), parts[8].getDTransform());
		lerpTransform(matrix, client.getSprintHandler().getProgress(), parts[9].getDTransform());
		lerpTransform(matrix, client.getCooldown().getProgress(NBTUtils.getId(stack)), parts[10].getDTransform());
		translateAndRotateAndScale(parts[5].getCombined(), matrix);
		matrix.pushPose();
		parts[1].getDTransform().setScale(1.3f, 2.5f, 1.3f);
		translateAndRotateAndScale(parts[7].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.LEFT, parts[1].getCombined());
		if(NBTUtils.hasMag(stack) && renderMag()) {
			//LogUtils.getLogger().info("HasMag: " + NBTUtils.getMag(stack));
			matrix.pushPose();
			translateAndRotateAndScale(parts[6].getCombined(), matrix);
			renderItem(player, new ItemStack(ForgeRegistries.ITEMS
					.getValue(new ResourceLocation(NBTUtils.getMag(stack)))), buffer, matrix, light, 
					parts[4].getCombined());
			matrix.popPose();
		}
		matrix.popPose();
		matrix.pushPose();
		translateAndRotateAndScale(parts[6].getCombined(), matrix);
		renderArm(player, buffer, matrix, light, HumanoidArm.RIGHT, parts[0].getCombined());
		renderGunPart(player, stack, buffer, matrix, light);
		matrix.pushPose();
		//translateAndRotateAndScale(parts[3].getCombined(), matrix);
		//LogUtils.getLogger().info(parts[3].getCombined().toString());
		lerpTransform(matrix, client.getCooldown().getProgress(NBTUtils.getId(stack)), 
				new Transform(0.0f, -0.010000001f, 0.33000004f, 0, 0, 0));
		for (String hammerP : stuff.getHammers()) {
			renderModel(player, stack, buffer, matrix, light, Minecraft.getInstance().getItemRenderer()
					.getItemModelShaper().getModelManager()
					.getModel(new ModelResourceLocation(hammerP, "inventory")));
		}
		matrix.popPose();
		matrix.popPose();
		matrix.popPose();
	}

	@Override
	public void reload(Player player, ItemStack stack) {
		int index = ServerUtils
				.getIndexForCorrectMag(player.getInventory(), 
						gun.getGunId(),
				BulletItem.SMALL);
		if(index != -1) {
			LogUtils.getLogger().info("index: " + index);
			ItemStack mag = getMagStack(index);
			int magBullets = NBTUtils.getAmmo(mag);
			if(NBTUtils.hasMag(stack)) {
				ReloadHandler.restoreMag(getMPath(), ServerUtils.getBullets(Utils.getStack()));
				ReloadHandler.setBullets(magBullets);
				ReloadHandler.removeItem(index, 1);
				setAnimation(reloadMagByMag);
			} else {
				MagItem magItem = getMagItem(index);
				ReloadHandler.setMag(this, magItem.getMaxAmmo(), true, 
						getMBPath(index), magItem);
				ReloadHandler.removeItem(index, 1);
				ReloadHandler.setBullets(magBullets);
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
		return new Mp40ModModel(origin);
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
