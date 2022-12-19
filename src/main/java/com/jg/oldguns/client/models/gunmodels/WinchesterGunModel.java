package com.jg.oldguns.client.models.gunmodels;

import java.util.List;

import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.RepetitiveAnimation;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.client.handlers.ClientHandler;
import com.jg.oldguns.client.models.modmodels.WinchesterModModel;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WinchesterGunModel extends GunModel {
	
	public Animation look;
	public RepetitiveAnimation reload;
	
	public WinchesterGunModel(ClientHandler client) {
		super(new GunModelPart[] { 
				new GunModelPart("rightarm", -0.11f, -0.18f, -0.31f, -0.6f, 0, 0.6f), 
				new GunModelPart("leftarm", -0.11f, 0.51f, -0.61f, 0.005235f, -1.117001f, 0), 
				new GunModelPart("gun", 0.667001f, -0.446002f, -1.1f, 0.021459f, -0.034906f, 0),
				new GunModelPart("hammer", 0.667001f, -0.446002f, -1.1f, 0.021459f, -0.034906f, 0),
				new GunModelPart("mag", 0.8f, -0.6f, -1.1f, 0.1f, 0, 0),
				new GunModelPart("all"),
				new GunModelPart("alllessleft"),
				new GunModelPart("leftarmmag"),
				new GunModelPart("aim", -0.728f, 0.176f, 0, -0.027925f, 0.040142f, 0), 
				new GunModelPart("sprint", 1.32f, -0.89f, -0.221f, 0.548036f, 1.326451f, -0.191986f),
				new GunModelPart("recoil", 0.02f, -0.18f, 0.02f, 0.139626f, -0.069813f, 0) }, 
				ItemRegistries.WINCHESTER.get(), client);
		
		look = new Animation("lookAnim", "oldguns:winchester")
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

		reload = new RepetitiveAnimation("reloadAnim", "oldguns:winchester")
				.startKeyframe(12)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.21000002f, -0.11999998f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], -0.80285174f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.3839724f)
				.startKeyframe(12)
				.type(1)
				.translate(parts[2], 0.0f, 0.0f, 0.0f)
				.translate(parts[1], -0.32999998f, 0.06000001f, -0.07999999f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.3839724f)
				.startKeyframe(12)
				.translate(parts[2], 0.0f, 0.0f, -0.02f)
				.translate(parts[1], -0.27000004f, 1.4901161E-8f, -0.44999984f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.3839724f)
				.startKeyframe(12)
				.type(2)
				.translate(parts[2], 0.0f, 0.0f, -0.02f)
				.translate(parts[1], -0.32999998f, 0.06000001f, -0.07999999f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], -0.22689286f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, -0.3839724f)
				.startKeyframe(12)
				.translate(parts[1], 0.0f, 0.0f, 0.0f)
				.translate(parts[6], 0.0f, 0.0f, 0.0f)
				.rotate(parts[1], 0.0f, 0.0f, 0.0f)
				.rotate(parts[6], 0.0f, 0.0f, 0.0f)
				.end();
	}

	@Override
	public void tick(Player player, ItemStack stack) {
		super.tick(player, stack);
	}
	
	@Override
	public float getKnockback() {
		return 0;
	}

	@Override
	public void render(LocalPlayer player, ItemStack stack, MultiBufferSource buffer, PoseStack matrix, int light) {
		renderAll(player, stack, buffer, matrix, light);
	}

	@Override
	public void reload(Player player, ItemStack stack) {
		setAnimation(reload);
	}

	@Override
	public Animation getLookAnimation() {
		return look;
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
	public WrapperModel getModifiedModel(BakedModel origin) {
		return new WinchesterModModel(origin);
	}
	
}
