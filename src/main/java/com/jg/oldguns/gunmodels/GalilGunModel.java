package com.jg.oldguns.gunmodels;

import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.modmodels.GalilModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GalilGunModel extends GunModel{

	public GalilGunModel() {
		super(ItemRegistries.Galil.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startKickBackAnim() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startInspectAnim() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector3f getMuzzleFlashPos(ItemStack stack) {
		// TODO Auto-generated method stub
		return new Vector3f();
	}

	@Override
	public void handleAim(PoseStack matrix, float aimProgress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleSprint(PoseStack matrix, float aimProgress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GunModelPart[] getParts() {
		return new GunModelPart[] {rightarm, leftarm, gun, mag, hammer, aim, scope};
	}

	@Override
	public boolean canRenderMag(ItemStack stack) {
		return gunitem.getNBTHasMag(stack);
	}

	@Override
	public boolean canShoot(ItemStack stack) {
		// TODO Auto-generated method stub
		return getAnimation() == EMPTY;
	}

	@Override
	public void setupParts() {
		/*setPartDisplayTransform(rightarm, 0.03f, 0.441f, 0.7098f, -0.5409f, -0.4886f, -0.4009f);
		setPartDisplayTransform(leftarm, -0.3397f, 0.8498f, 0.7089f, 0.3666f, 0.8202f, 0.2619f);
		setPartDisplayTransform(gun, -0.22f, -0.83f, -0.07f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(scope, -0.235f, -0.793f, 0.3709f, -1.5358f, 0, 0.1047f);
		setPartDisplayTransform(hammer, -0.24f, -0.84f, -0.08f, -1.5533f, 0, 0.1047f);
		setPartDisplayTransform(mag, -0.22f, -0.84f, -0.07f, -1.5533f, 0, 0.1047f);*/
	}

	@Override
	public void setupAnimations() {
		
	}

	@Override
	public void initReloadAnimation(int currentgunbullets, int magbullets, boolean hasMag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimTick(float animTick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShoot(ItemStack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initExtraParts() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WrapperModel getModifiedModel(BakedModel origin) {
		// TODO Auto-generated method stub
		return new GalilModModel(origin, gunitem);
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.GALILHAMMER;
	}

	@Override
	public boolean hasMultipleParts() {
		// TODO Auto-generated method stub
		return true;
	}

}
