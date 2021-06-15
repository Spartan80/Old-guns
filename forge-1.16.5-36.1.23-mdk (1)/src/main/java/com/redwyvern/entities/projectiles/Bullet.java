package com.redwyvern.entities.projectiles;

import com.redwyvern.mod.OldGuns;
import com.redwyvern.network.PlaySoundMessage;
import com.redwyvern.registries.EntityRegistries;
import com.redwyvern.registries.SoundRegistries;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class Bullet extends ThrowableEntity {
	public static final float BASE_SPEED = 20f;
	public static final int TICKS = 1;
	public static LivingEntity shooter;

	protected static final DataParameter<Float> DAMAGE = EntityDataManager.defineId(Bullet.class,
			DataSerializers.FLOAT);
	protected static final DataParameter<Float> DAMAGERED = EntityDataManager.defineId(Bullet.class,
			DataSerializers.FLOAT);
	protected static final DataParameter<Float> GRAVITY = EntityDataManager.defineId(Bullet.class,
			DataSerializers.FLOAT);

	public Bullet(EntityType<? extends Bullet> type, World worldIn) {
		super(type, worldIn);
	}

	public Bullet(World worldIn) {
		super(EntityRegistries.BULLET.get(), worldIn);
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

	@Override
	public void tick() {
		this.setDeltaMovement(this.getDeltaMovement().scale(1F / Bullet.TICKS));
		super.tick();
		this.setDeltaMovement(this.getDeltaMovement().scale(Bullet.TICKS));
		if (tickCount >= 50) {
			remove();
		}
		if (tickCount >= 10) {
			setDamage(getBulletDamage() - (tickCount / getBulletDamageReduction()));
		}
	}

	@Override
	protected void onHit(RayTraceResult result) {
		if (result.getType() == Type.ENTITY) {
			EntityRayTraceResult hit = (EntityRayTraceResult) result;

			if (!this.level.isClientSide && hit.getEntity() instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) hit.getEntity();
				entity.invulnerableTime = 0;
				entity.hurt(DamageSource.thrown(this.shooter, hit.getEntity()),
						entityData.get(Bullet.DAMAGE).floatValue());
				System.out.println("bullet damage: " + entityData.get(Bullet.DAMAGE).floatValue() + " entity life: "
						+ entity.getHealth());

			}
			if (!this.level.isClientSide) {
				OldGuns.channel.sendToServer(new PlaySoundMessage(SoundRegistries.HIT_MARKER_SOUND.get(),
						Minecraft.getInstance().player.getX(), Minecraft.getInstance().player.getY(),
						Minecraft.getInstance().player.getZ(), true));
			}
			remove();
		}
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(Bullet.DAMAGE, 4F);
		this.entityData.define(Bullet.GRAVITY, 1F);
		this.entityData.define(Bullet.DAMAGERED, 5F);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		// TODO Auto-generated method stub
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

}
