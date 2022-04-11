package com.jg.oldguns.gunmodels;

import com.jg.oldguns.animations.Animation;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.RecoilHandler;
import com.jg.oldguns.client.handlers.ReloadHandler;
import com.jg.oldguns.client.handlers.SoundHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.modmodels.ColtModModel;
import com.jg.oldguns.client.modmodels.StenModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.MeleeHelper;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class StenGunModel extends GunModel{

	public static final Animation SA = Animation.create(3);
	public static final Animation R1 = Animation.create(28);
	public static final Animation R2 = Animation.create(44);
	public static final Animation R3 = Animation.create(14);
	public static final Animation R4 = Animation.create(30);
	public static final Animation GOM = Animation.create(16);
	public static final Animation KB = Animation.create(16);
	public static final Animation IA = Animation.create(28);
	
	public StenGunModel() {
		super(ItemRegistries.Sten.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		setAnimation(GOM);
		
	}

	@Override
	public void startKickBackAnim() {
		setAnimation(KB);
		
	}

	@Override
	public void startInspectAnim() {
		setAnimation(IA);
		
	}

	@Override
	public Vector3f getMuzzleFlashPos(ItemStack stack) {
		return new Vector3f(0.15f, -0.15f, -1.06f);
	}

	@Override
	public void handleAim(PoseStack matrix, float p) {
		matrix.translate(Mth.lerp(p, 0, -0.2639f), Mth.lerp(p, 0, 0.1378f), Mth.lerp(p, 0, 0.1428f));
		matrix.mulPose(new Quaternion(Mth.lerp(p, 0, -0.0418f), Mth.lerp(p, 0, -0.1029f), 0, false));
		
	}

	@Override
	public void handleSprint(PoseStack matrix, float p) {
		matrix.mulPose(Vector3f.XP.rotation((float) Math.toRadians(Mth.rotLerp(p, 0, -18))));
		
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] { rightarm, leftarm, gun, mag, hammer, aim, scope};
	}

	@Override
	public boolean canRenderMag(ItemStack stack) {
		return ServerUtils.hasMag(stack);
	}

	@Override
	public boolean canShoot(ItemStack stack) {
		return getAnimation() == EMPTY;
	}

	@Override
	public void setupParts() {
		setPartDisplayTransform(rightarm, 0.03f, 0.441f, 0.8598f, -0.5409f, -0.541f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.4397f, 0.8698f, 0.9289f, 0.3666f, 0.8202f, 0.2619f);
		setPartDisplayTransform(gun, -0.28f, -0.68f, -0.14f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(hammer, -0.28f, -0.68f, -0.14f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(mag, -0.28f, -0.68f, -0.14f, -1.5358f, 0, 0.1047f);
	}

	@Override
	public void setupAnimations() {
		//ReloadHandler.setMag(20, true, ItemRegistries.SmallBullet.get().getRegistryName().toString(), (ItemMag)ItemRegistries.StenMag.get());
		
		animator.setAnimation(R1);
		animator.startKeyframe(2);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R2);
		animator.startKeyframe(2);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.move(hammer, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.move(hammer, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.move(hammer, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(12);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.move(hammer, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.13999999f, 0.14999999f, 0.13999999f);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.07999999f, -0.16f, 0.14999999f);
		animator.move(hammer, 0.07999999f, -0.16f, 0.14999999f);
		animator.rotate(rightarm, -0.33161256f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.4886921f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, -0.33161256f, 0.0f);
		animator.rotate(hammer, -3.7252903E-9f, -0.33161256f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.13999999f, 0.14999999f, 0.18f);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.07999999f, -0.16f, 0.14999999f);
		animator.move(hammer, 0.07999999f, -0.16f, 0.14999999f);
		animator.rotate(rightarm, -0.33161256f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.4886921f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, -0.33161256f, 0.0f);
		animator.rotate(hammer, -3.7252903E-9f, -0.33161256f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.13999999f, 0.14999999f, 0.13999999f);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.07999999f, -0.16f, 0.14999999f);
		animator.move(hammer, 0.07999999f, -0.16f, 0.14999999f);
		animator.rotate(rightarm, -0.33161256f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.4886921f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, -0.33161256f, 0.0f);
		animator.rotate(hammer, -3.7252903E-9f, -0.33161256f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R3);
		animator.startKeyframe(4);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(R4);
		animator.startKeyframe(4);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.13999999f, 0.14999999f, 0.13999999f);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.07999999f, -0.16f, 0.14999999f);
		animator.rotate(rightarm, -0.33161256f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.4886921f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, -0.33161256f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.13999999f, 0.14999999f, 0.18f);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.07999999f, -0.16f, 0.14999999f);
		animator.rotate(rightarm, -0.33161256f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.4886921f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, -0.33161256f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.13999999f, 0.14999999f, 0.13999999f);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.07999999f, -0.16f, 0.14999999f);
		animator.rotate(rightarm, -0.33161256f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, -0.4886921f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, -0.33161256f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(GOM);
		animator.startKeyframe(2);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(rightarm, -0.002f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.003f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, -0.0020000003f, 0.0f, 0.0f);
		animator.move(leftarm, -0.18100019f, 0.0f, 0.0f);
		animator.move(mag, -0.08399999f, 0.0f, 0.0f);
		animator.move(gun, -0.0030000005f, 0.0f, 0.0f);
		animator.rotate(leftarm, 1.0122914f, 0.0f, 0.0f);
		animator.rotate(mag, -1.0122914f, 0.0f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(IA);
		animator.startKeyframe(4);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.16f, -0.06f, 0.09f);
		animator.move(mag, 0.0f, 0.0f, 0.0f);
		animator.move(gun, 0.09f, -0.16f, 0.15f);
		animator.move(mag, 0.09f, -0.16f, 0.15f);
		animator.move(hammer, 0.09f, -0.16f, 0.15f);
		animator.rotate(rightarm, -0.24434613f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, 0.0f, 0.0f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, -0.33161253f, 0.0f);
		animator.rotate(mag, -3.7252903E-9f, -0.33161253f, 0.0f);
		animator.rotate(hammer, -3.7252903E-9f, -0.33161253f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.16f, -0.059999995f, 0.08999999f);
		animator.move(gun, 0.08999999f, -0.16f, 0.14999999f);
		animator.move(mag, 0.08999999f, -0.16f, 0.14999999f);
		animator.move(hammer, 0.08999999f, -0.16f, 0.14999999f);
		animator.rotate(rightarm, -0.24434613f, 0.0f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(mag, -3.7252903E-9f, -0.33161253f, 0.0f);
		animator.rotate(hammer, -3.7252903E-9f, -0.33161253f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, -0.33161253f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(4);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.16f, -0.059999995f, 0.08999999f);
		animator.move(hammer, 0.02f, 0.21000002f, -0.059999995f);
		animator.move(mag, 0.02f, 0.21000002f, -0.059999995f);
		animator.move(gun, 0.02f, 0.21000002f, -0.059999995f);
		animator.rotate(rightarm, 0.33161253f, 1.1175871E-8f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(hammer, -3.7252903E-9f, 0.33161256f, 0.0f);
		animator.rotate(mag, -3.7252903E-9f, 0.33161256f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, 0.33161256f, 0.0f);
		animator.endKeyframe();
		animator.startKeyframe(8);
		animator.move(rightarm, 0.0f, 0.0f, 0.0f);
		animator.move(leftarm, -0.16f, -0.059999995f, 0.08999999f);
		animator.move(gun, 0.02f, 0.21000002f, -0.059999995f);
		animator.move(mag, 0.02f, 0.21000002f, -0.059999995f);
		animator.move(hammer, 0.02f, 0.21000002f, -0.059999995f);
		animator.rotate(rightarm, 0.33161253f, 1.1175871E-8f, 0.0f);
		animator.rotate(leftarm, 0.0f, 0.0f, 0.0f);
		animator.rotate(hammer, -3.7252903E-9f, 0.33161256f, 0.0f);
		animator.rotate(gun, -3.7252903E-9f, 0.33161256f, 0.0f);
		animator.rotate(mag, -3.7252903E-9f, 0.33161256f, 0.0f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();

		animator.setAnimation(KB);
		animator.startKeyframe(1);
		animator.move(gun, 0.49999982f, 0.02f, 0.11999998f);
		animator.move(rightarm, 0.08999999f, 0.02f, 0.0f);
		animator.move(leftarm, -0.16f, 0.0f, 0.07999999f);
		animator.rotate(gun, 1.1175871E-8f, 0.0f, 0.872665f);
		animator.endKeyframe();
		animator.startKeyframe(2);
		animator.move(gun, 0.49999982f, 0.02f, 0.11999998f);
		animator.move(rightarm, 0.08999999f, 0.02f, 0.0f);
		animator.move(leftarm, -0.16f, 0.0f, 0.07999999f);
		animator.rotate(gun, 1.1175871E-8f, 0.0f, 0.872665f);
		animator.endKeyframe();
		animator.resetKeyframe(4);
		animator.endKeyframe();
		
		animator.setAnimation(SA);
		animator.startKeyframe(1);
		animator.move(hammer, 0.008f, -0.004f, 0.105f);
		animator.endKeyframe();
		animator.resetKeyframe(2);
		animator.endKeyframe();
	}

	@Override
	public void initReloadAnimation(int currentgunbullets, int magbullets, boolean hasMag) {
		if(hasMag) {
			if(magbullets == 0) {
				System.out.println("R1");
				setAnimation(R1);
			}else {
				System.out.println("R2");
				setAnimation(R2);
			}
		}else {
			if(magbullets == 0) {
				System.out.println("R3");
				setAnimation(R3);
			}else {
				System.out.println("R4");
				setAnimation(R4);
			}
		}
	}

	@Override
	public void onAnimTick(float animTick) {
		if(getAnimation() == R1) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGOUT.get());
			} else if(animTick == 8) {
				ReloadHandler.restoreMag(getMPath(), gunbullets);
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			} else if(animTick == 22) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGIN.get());
				ReloadHandler.setBullets(magbullets);
			}
		} else if(getAnimation() == R2) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGOUT.get());
			} else if(animTick == 8) {
				ReloadHandler.restoreMag(getMPath(), gunbullets);
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			} else if(animTick == 22) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGIN.get());
			} else if(animTick == 31) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENHAMMERBACK.get());
			} else if(animTick == 37) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENHAMMERFORWARD.get());
				ReloadHandler.setBullets(magbullets);
			}
		} else if(getAnimation() == R3) {
			if(animTick == 6) {
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			}else if(animTick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGIN.get());
			}
		} else if(getAnimation() == R4) {
			if(animTick == 6) {
				ReloadHandler.setMag(this, getMagItem().getMaxAmmo(), true, getMBPath(), getMagItem());
				ReloadHandler.removeItem(ammoindex, 1);
			} else if(animTick == 8) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGIN.get());
			} else if(animTick == 16) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENHAMMERBACK.get());
			} else if(animTick == 23) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENHAMMERFORWARD.get());
				ReloadHandler.setBullets(magbullets);
			} 
		} else if(getAnimation() == GOM) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.STENMAGOUT.get());
			} else if(animTick == 10) {
				ReloadHandler.restoreMag(getMPath(), gunbullets);
				ReloadHandler.setMag(this, 0, false, "", Constants.EMPTY);
				ReloadHandler.setBullets(0);
			}
		} else if(getAnimation() == KB) {
			if(animTick == 1) {
				SoundHandler.playSoundOnServer(SoundRegistries.SWING.get());
			} if(animTick == 4) {
				MeleeHelper.hit(5);
			}
		}
	}

	@Override
	public void onShoot(ItemStack stack) {
		setAnimation(SA);
	}

	@Override
	public void onAnimationEnd() {

	}

	@Override
	public void initExtraParts() {
		
	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		return new StenModModel(origin);
	}

	@Override
	public String getHammerPath() {
		return Paths.STENHAMMER;
	}

	@Override
	public boolean hasMultipleParts() {
		return true;
	}

}
