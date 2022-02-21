package com.jg.oldguns.guns.items;

import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.ItemBullet;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.properties.BodyProperties.BulletProvider;
import com.jg.oldguns.registries.ItemRegistries;
import com.jg.oldguns.registries.SoundRegistries;
import com.jg.oldguns.utils.RenderUtil;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class PiratePistol extends ItemGun {
	
	public PiratePistol(Properties prop, RegistryObject<Item> mag) {
		super(prop, mag);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Item getBarrel() {
		return ItemRegistries.PiratePistolBarrel.get();
	}

	@Override
	public Item getBarrelMouth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getBaseSpeedModifier() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Item getBody() {
		// TODO Auto-generated method stub
		return ItemRegistries.PiratePistolBody.get();
	}

	@Override
	public String getGunId() {
		// TODO Auto-generated method stub
		return "pp";
	}

	@Override
	public ResourceLocation getNullPartsResLoc() {
		// TODO Auto-generated method stub
		return RenderUtil.SCOPEEMPTYPART;
	}

	@Override
	public ResourceLocation getScopeResLoc() {
		// TODO Auto-generated method stub
		return RenderUtil.SCOPE;
	}

	@Override
	public Item getStock() {
		return ItemRegistries.PiratePistolStock.get();
	}

	@Override
	public boolean hasScope() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isBoth() {
		return true;
	}

	@Override
	public void onHit(ItemStack arg0, LivingEntity arg1) {

	}

	@Override
	public void tickServer(ItemStack arg0, World arg1, PlayerEntity arg2) {
		applySpeedModifier(arg2, arg0);
	}
	
	@Override
	public boolean shouldRenderCrosshair() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canChangeParts() {
		return false;
	}

	@Override
	public ShootMode getShootMode(ItemStack stack) {
		return ShootMode.SEMI;
	}

	@Override
	public double getGunDamage(ItemStack stack) {
		return 4;
	}

	@Override
	public double getPower(ItemStack stack) {
		return 6;
	}

	@Override
	public float getInaccuracy(ItemStack stack) {
		return 0.3f;
	}

	@Override
	public float getRHorRecoil(ItemStack stack) {
		return 0.05f;
	}

	@Override
	public float getRVertRecoil(ItemStack stack) {
		return 0;
	}

	@Override
	public float getZRecoil(ItemStack stack) {
		return 0.05f;
	}

	@Override
	public float getBurstTargetTime(ItemStack stack) {
		return 0;
	}

	@Override
	public int getRecoilTime(ItemStack stack) {
		return 4;
	}

	@Override
	public int getShootTime(ItemStack stack) {
		return 4;
	}

	@Override
	public int getRange(ItemStack stack) {
		return 4;
	}

	@Override
	public int getMaxAmmo(ItemStack stack) {
		return 1;
	}

	@Override
	public int getShotgunBullets(ItemStack stack) {
		return 0;
	}

	@Override
	public int getBurstShots(ItemStack stack) {
		return 0;
	}

	@Override
	public float getSpeedModifier(ItemStack stack) {
		return 0;
	}

	@Override
	public float getRangeDamageReduction(ItemStack stack) {
		return 0.5f;
	}

	@Override
	public boolean isShotgun(ItemStack stack) {
		return false;
	}

	@Override
	public boolean requiresMag(ItemStack stack) {
		return false;
	}

	@Override
	public String getAcceptedBulletType(ItemStack stack) {
		return ItemBullet.MUSKET;
	}
	
	@Override
	public SoundEvent getShootSound(ItemStack stack) {
		return SoundRegistries.PPSHOOT.get();
	}

	@Override
	public Item getMagPath(ItemStack stack) {
		return null;
	}

	@Override
	public Item getBulletItem(ItemStack stack) {
		return ItemRegistries.MusketBullet.get();
	}

	@Override
	public BulletProvider<? extends Bullet> getProjectile(ItemStack stack) {
		return Bullet::new;
	}

}
