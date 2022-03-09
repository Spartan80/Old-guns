package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.utils.Constants;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent.Context;

public class HandleShootMessage {

	public float yaw, pitch;

	public HandleShootMessage(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public static void encode(HandleShootMessage msg, FriendlyByteBuf buf) {
		buf.writeFloat(msg.yaw);
		buf.writeFloat(msg.pitch);
	}

	public static HandleShootMessage decode(FriendlyByteBuf buf) {
		return new HandleShootMessage(buf.readFloat(), buf.readFloat());
	}

	public static void handle(HandleShootMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			
			if (player != null) {
				
				Level world = player.level;
				ItemStack stack = player.getMainHandItem();
				ItemGun gun = (ItemGun) stack.getItem();
				player.setXRot(msg.pitch);
				player.setYRot(msg.pitch);

				if (!gun.isShotgun(stack)) {
					
					float rotationPitch = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy(stack);
					float rotationYaw = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy(stack);
					rotationPitch = player.getXRot() + rotationPitch;
					rotationYaw = player.getYRot() + rotationYaw;

					Bullet bulletEntity = stack.getOrCreateTag().getBoolean(Constants.HASMAG)
							? ((ItemMag) gun.getMagPath(stack)).getProjectile(player, world)
							: gun.getProjectile(stack).create(player, world);

					bulletEntity.setGravity(2).setDamage((float) gun.getGunDamage(stack))
							.setDamageReduction(gun.getRangeDamageReduction(stack)).setRange(gun.getRange(stack));// 0.8f

					bulletEntity.setPos(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
					bulletEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, (float) gun.getPower(stack),
							gun.getInaccuracy(stack));

					world.addFreshEntity(bulletEntity);

					world.playSound((ServerPlayer) null, player.getX(), player.getY(), player.getZ(),
							gun.getShootSound(stack), SoundSource.NEUTRAL, 0.1f, 1.0f);

					player.getCooldowns().addCooldown(gun, gun.getShootTime(stack));

					gun.setNBTBullets(stack, Math.max(0, gun.getNBTBullets(stack) - 1));
				
					/*player.xRot = (float) (player.xRot - (Util.numInRange(-(gun.getRHorRecoil(stack) * 10f) * 0.75f,
							(gun.getRHorRecoil(stack) * 10f)) * 0.75f));
					player.yRot = (float) (player.yRot - (Util.numInRange(-(gun.getRVertRecoil(stack) * 10f) * 0.75f,
							(gun.getRVertRecoil(stack) * 10f)) * 0.75f));
					*/
					player.setXRot(msg.pitch);
					player.setYRot(msg.yaw);
				} else {
					gun.setNBTBullets(stack, Math.max(0, gun.getNBTBullets(stack) - 1));
					
					for(int i = 0; i < gun.getShotgunBullets(stack); i++) {
						float rotationPitch = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy(stack);
						float rotationYaw = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy(stack);
						rotationPitch = player.getXRot() + rotationPitch;
						rotationYaw = player.getYRot() + rotationYaw;

						Bullet bulletEntity = stack.getOrCreateTag().getBoolean(Constants.HASMAG)
								? ((ItemMag) gun.getMagPath(stack)).getProjectile(player, world)
								: gun.getProjectile(stack).create(player, world);

						bulletEntity.setGravity(2).setDamage((float) gun.getGunDamage(stack))
								.setDamageReduction(gun.getRangeDamageReduction(stack)).setRange(gun.getRange(stack));// 0.8f

						bulletEntity.setPos(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
						bulletEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, (float) gun.getPower(stack),
								gun.getInaccuracy(stack));

						world.addFreshEntity(bulletEntity);

						world.playSound((ServerPlayer) null, player.getX(), player.getY(), player.getZ(),
								gun.getShootSound(stack), SoundSource.NEUTRAL, 0.1f, 1.0f);

						player.getCooldowns().addCooldown(gun, gun.getShootTime(stack));

						player.setXRot(msg.pitch);
						player.setYRot(msg.yaw);
						/*player.xRot = (float) (player.xRot - (Util.numInRange(-(gun.getRHorRecoil(stack) * 10f) * 0.75f,
								(gun.getRHorRecoil(stack) * 10f)) * 0.75f));
						player.yRot = (float) (player.yRot - (Util.numInRange(-(gun.getRVertRecoil(stack) * 10f) * 0.75f,
								(gun.getRVertRecoil(stack) * 10f)) * 0.75f));
						*/
					}
					
				}
				
			}
		});

		context.setPacketHandled(true);
	}
}
