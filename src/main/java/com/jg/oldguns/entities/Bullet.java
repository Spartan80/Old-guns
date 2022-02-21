package com.jg.oldguns.entities;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.network.StartHitmarkerMessage;
import com.jg.oldguns.registries.EntityRegistries;
import com.jg.oldguns.registries.SoundRegistries;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkHooks;

public class Bullet extends ThrowableEntity {
	public static final float BASE_SPEED = 20f;
	public static final int TICKS = 1;
	public LivingEntity shooter;

	protected static final DataParameter<Float> DAMAGE = EntityDataManager.defineId(Bullet.class,
			DataSerializers.FLOAT);
	protected static final DataParameter<Float> DAMAGERED = EntityDataManager.defineId(Bullet.class,
			DataSerializers.FLOAT);
	protected static final DataParameter<Float> GRAVITY = EntityDataManager.defineId(Bullet.class,
			DataSerializers.FLOAT);
	protected static final DataParameter<Integer> RANGE = EntityDataManager.defineId(Bullet.class, DataSerializers.INT);

	public Bullet(LivingEntity entity, World worldIn) {
		super(EntityRegistries.BULLET.get(), worldIn);
		this.shooter = entity;
	}

	public Bullet(World worldIn) {
		super(EntityRegistries.BULLET.get(), worldIn);
	}

	public Bullet(EntityType<? extends Bullet> type, World worldIn) {
		super(type, worldIn);
	}

	public Bullet(EntityType<Bullet> type, LivingEntity livingEntityIn, World worldIn) {
		super(type, livingEntityIn, worldIn);
		this.shooter = livingEntityIn;
	}

	public Bullet setDamage(float damage) {
		this.entityData.set(Bullet.DAMAGE, damage);
		return this;
	}

	public Bullet setGravity(float gravity) {
		this.entityData.set(Bullet.GRAVITY, gravity);
		return this;
	}

	public Bullet setDamageReduction(float damagered) {
		this.entityData.set(Bullet.DAMAGERED, damagered);
		return this;
	}

	public Bullet setRange(int range) {
		this.entityData.set(Bullet.RANGE, range);
		return this;
	}

	@Override
	public void tick() {
		if (!level.isClientSide) {
			this.setDeltaMovement(this.getDeltaMovement().scale(1F / Bullet.TICKS));
			super.tick();
			this.setDeltaMovement(this.getDeltaMovement().scale(Bullet.TICKS));
			// System.out.println(tickCount);
			if (tickCount >= 50) {
				remove();
			}
			if (tickCount >= getRange()) {
				float damred = getBulletDamage() * getBulletDamageReduction();
				if (damred > 0) {
					// System.out.println("damageReduced");
					setDamage(damred);

				}
			}
		}
	}

	@Override
	protected void onHit(RayTraceResult result) {
		if (result.getType() == Type.ENTITY) {
			EntityRayTraceResult hit = (EntityRayTraceResult) result;

			if (!this.level.isClientSide) {
				if (hit.getEntity() instanceof LivingEntity && this.shooter != null) {
					LivingEntity entity = (LivingEntity) hit.getEntity();
					entity.invulnerableTime = 0;
					DamageSource src = DamageSource.mobAttack(this.shooter);
					entity.hurt(src, entityData.get(Bullet.DAMAGE).floatValue());
					System.out.println(src.isMagic());
					OldGuns.channel.sendTo(new StartHitmarkerMessage(),
							((ServerPlayerEntity) shooter).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
					if (shooter != null) {
						ItemStack stack = ((PlayerEntity) shooter).getMainHandItem();
						((ItemGun) stack.getItem()).onHit(stack, entity);
						shooter.level.playSound((PlayerEntity) null, shooter.getX(), shooter.getY(), shooter.getZ(),
								SoundRegistries.HITMARKER.get(), SoundCategory.NEUTRAL, 0.1f, 1);
					}
				}
			}

			remove();
		}
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(Bullet.DAMAGE, 4F);
		this.entityData.define(Bullet.GRAVITY, 1F);
		this.entityData.define(Bullet.DAMAGERED, 5F);
		this.entityData.define(Bullet.RANGE, 4);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected float getBulletDamage() {
		return this.entityData.get(Bullet.DAMAGE);
	}

	protected float getBulletGravity() {
		return this.entityData.get(Bullet.GRAVITY);
	}

	protected float getBulletDamageReduction() {
		return this.entityData.get(Bullet.DAMAGERED);
	}

	protected int getRange() {
		return this.entityData.get(Bullet.RANGE);
	}

}
