package com.jg.oldguns.entities;

import com.jg.oldguns.registries.EntityRegistries;
import com.mojang.logging.LogUtils;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkHooks;

public class GunBullet extends ThrowableProjectile implements ItemSupplier {

	public static final ItemStack ITEM = new ItemStack(Items.AIR);
	public static final float BASE_SPEED = 20f;
	public static final int TICKS = 1;
	public LivingEntity shooter;
	public int dist;
	
	protected static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(GunBullet.class,
			EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Float> DAMAGERED = SynchedEntityData.defineId(GunBullet.class,
			EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(GunBullet.class,
			EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Integer> RANGE = SynchedEntityData.defineId(GunBullet.class,
			EntityDataSerializers.INT);
	
	public GunBullet(LivingEntity entity, Level worldIn) {
		super(EntityRegistries.BULLET.get(), worldIn);
		this.shooter = entity;
	}

	public GunBullet(Level worldIn) {
		super(EntityRegistries.BULLET.get(), worldIn);
	}

	public GunBullet(EntityType<? extends GunBullet> type, Level worldIn) {
		super(type, worldIn);
	}

	public GunBullet(EntityType<GunBullet> type, LivingEntity livingEntityIn, Level worldIn) {
		super(type, livingEntityIn, worldIn);
		this.shooter = livingEntityIn;
	}
	
	public GunBullet setDamage(float damage) {
		this.entityData.set(GunBullet.DAMAGE, damage);
		return this;
	}

	public GunBullet setGravity(float gravity) {
		this.entityData.set(GunBullet.GRAVITY, gravity);
		return this;
	}

	public GunBullet setDamageReduction(float damagered) {
		this.entityData.set(GunBullet.DAMAGERED, damagered);
		return this;
	}

	public GunBullet setRange(int range) {
		this.entityData.set(GunBullet.RANGE, range);
		return this;
	}

	@Override
	public void tick() {
		if (!level.isClientSide) {
			dist++;
			this.setDeltaMovement(this.getDeltaMovement().scale(1F / GunBullet.TICKS));
			super.tick();
			this.setDeltaMovement(this.getDeltaMovement().scale(GunBullet.TICKS));
			if (tickCount >= 200) {
				remove(RemovalReason.KILLED);
			}
			if (dist >= getRange()) {
				float damred = getGunBulletDamage() * getGunBulletDamageReduction();
				float g = entityData.get(GunBullet.GRAVITY);
				this.setDeltaMovement(this.getDeltaMovement().multiply(g, g, g));
				if (damred > 0) {
					LogUtils.getLogger().info("Dmg: " + damred);
					setDamage(damred);
					dist = 0;
				}
			}
		}
	}

	@Override
	protected void onHit(HitResult result) {
		if (result.getType() == Type.ENTITY) {
			EntityHitResult hit = (EntityHitResult) result;

			if (!this.level.isClientSide) {
				if (hit.getEntity() instanceof LivingEntity && this.shooter != null) {
					LivingEntity entity = (LivingEntity) hit.getEntity();
					entity.invulnerableTime = 0;
					DamageSource src = DamageSource.mobAttack(this.shooter);
					entity.hurt(src, entityData.get(GunBullet.DAMAGE).floatValue());
					LogUtils.getLogger().info("Dmg: " + entityData.get(GunBullet.DAMAGE).floatValue());
				}
			}

			remove(RemovalReason.KILLED);
		}else if(result.getType() == Type.BLOCK){
			remove(RemovalReason.KILLED);
		}
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(GunBullet.DAMAGE, 4F);
		this.entityData.define(GunBullet.GRAVITY, 1F);
		this.entityData.define(GunBullet.DAMAGERED, 5F);
		this.entityData.define(GunBullet.RANGE, 4);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected float getGunBulletDamage() {
		return this.entityData.get(GunBullet.DAMAGE);
	}

	protected float getGunBulletGravity() {
		return this.entityData.get(GunBullet.GRAVITY);
	}

	protected float getGunBulletDamageReduction() {
		return this.entityData.get(GunBullet.DAMAGERED);
	}

	protected int getRange() {
		return this.entityData.get(GunBullet.RANGE);
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(Items.AIR);
	}
	
}
