package com.jg.oldguns.gunmodels;

import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.client.models.GunModelPart;
import com.jg.oldguns.client.models.wrapper.WrapperModel;
import com.jg.oldguns.client.modmodels.Mp5ModModel;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.utils.Paths;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Mp5GunModel extends GunModel {

	public Mp5GunModel() {
		super(ItemRegistries.Mp5.get());
	}

	@Override
	public void doGetOutMag(ItemStack stack) {
		
	}

	@Override
	public void startKickBackAnim() {
		
	}

	@Override
	public void startInspectAnim() {
		
	}

	@Override
	public Vector3f getMuzzleFlashPos(ItemStack stack) {
		return new Vector3f(0, 0, 0);
	}

	@Override
	public void handleAim(PoseStack matrix, float aimProgress) {
		
	}

	@Override
	public void handleSprint(PoseStack matrix, float aimProgress) {
		
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
		return getAnimation() == EMPTY;
	}

	@Override
	public void setupParts() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupAnimations() {
		// TODO Auto-generated method stub
		
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
		return new Mp5ModModel(origin, gunitem);
	}

	@Override
	public String getHammerPath() {
		// TODO Auto-generated method stub
		return Paths.MP5HAMMER;
	}

	@Override
	public boolean hasMultipleParts() {
		// TODO Auto-generated method stub
		return true;
	}

}
