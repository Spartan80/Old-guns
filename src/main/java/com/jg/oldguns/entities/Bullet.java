package com.jg.oldguns.entities;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.network.StartHitmarkerMessage;
import com.jg.oldguns.registries.EntityRegistries;
import com.jg.oldguns.registries.SoundRegistries;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkHooks;

public class Bullet extends ThrowableProjectile {
	public static final float BASE_SPEED = 20f;
	public static final int TICKS = 1;
	public LivingEntity shooter;

	protected static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Bullet.class,
			EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Float> DAMAGERED = SynchedEntityData.defineId(Bullet.class,
			EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Bullet.class,
			EntityDataSerializers.FLOAT);
	protected static final EntityDataAccessor<Integer> RANGE = SynchedEntityData.defineId(Bullet.class, EntityDataSerializers.INT);

	public Bullet(LivingEntity entity, Level worldIn) {
		super(EntityRegistries.BULLET.get(), worldIn);
		this.shooter = entity;
	}

	public Bullet(Level worldIn) {
		super(EntityRegistries.BULLET.get(), worldIn);
	}

	public Bullet(EntityType<? extends Bullet> type, Level worldIn) {
		super(type, worldIn);
	}

	public Bullet(EntityType<Bullet> type, LivingEntity livingEntityIn, Level worldIn) {
		super(type, livingEntityIn, worldIn);
		this.shooter = livingEntityIn;
	}

	public Bullet init(Player player) {
		this.shooter = player;
		return this;
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
			if (tickCount >= 50) {
				remove(RemovalReason.KILLED);
			}
			if (tickCount >= getRange()) {
				float damred = getBulletDamage() * getBulletDamageReduction();
				if (damred > 0) {
					setDamage(damred);

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
					entity.hurt(src, entityData.get(Bullet.DAMAGE).floatValue());
					System.out.println(src.isMagic());
					OldGuns.channel.sendTo(new StartHitmarkerMessage(),
							((ServerPlayer) shooter).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
					if (shooter != null) {
						ItemStack stack = ((Player) shooter).getMainHandItem();
						((ItemGun) stack.getItem()).onHit(stack, entity);
						shooter.level.playSound((Player) null, shooter.getX(), shooter.getY(), shooter.getZ(),
								SoundRegistries.HITMARKER.get(), SoundSource.NEUTRAL, 0.1f, 1);
					}
				}
			}

			remove(RemovalReason.KILLED);
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
	public Packet<?> getAddEntityPacket() {
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
