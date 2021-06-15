package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.entities.projectiles.Bullet;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.registries.EntityRegistries;
import com.redwyvern.util.EnchUtil;
import com.redwyvern.util.Util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class HandleShootMessage {

	public float yaw, pitch;

	public HandleShootMessage(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public static void encode(HandleShootMessage msg, PacketBuffer buf) {
		buf.writeFloat(msg.yaw);
		buf.writeFloat(msg.pitch);
	}

	public static HandleShootMessage decode(PacketBuffer buf) {
		return new HandleShootMessage(buf.readFloat(), buf.readFloat());
	}

	public static void handle(HandleShootMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();

			if (player != null) {
				World world = player.level;
				ItemStack stack = player.getMainHandItem();
				ItemGun gun = (ItemGun) stack.getItem();

				float rotationPitch = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy();
				float rotationYaw = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy();
				rotationPitch = player.xRot + rotationPitch;
				rotationYaw = player.yRot + rotationYaw;

				Bullet bulletEntity = new Bullet(EntityRegistries.BULLET.get(), player, world);
				bulletEntity.setGravity(2).setDamage(gun.getDamage());
				bulletEntity.setPos(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
				bulletEntity.shootFromRotation(player, player.xRot, player.yRot, 0, gun.getPower(),
						gun.getInaccuracy());
				world.addFreshEntity(bulletEntity);

				gun.spawnParticles(player);

				if (!player.isCreative()) {
					gun.setNBTBullets(stack, Math.max(0, gun.getNBTBullets(stack) - 1));
				}

				world.playSound(player, player.getX(), player.getY(), player.getZ(), gun.getShootSound(),
						SoundCategory.NEUTRAL, gun.getShootVolume(), 1.0f);
				OldGuns.channel.sendToServer(
						new PlaySoundOnServerMessage(gun.getShootSound(), player.getX(), player.getY(), player.getZ()));

				int level = EnchUtil.getMoreShots(stack);

				if (level > 0) {
					if (gun.getMoreShotsProbability(level)) {
						Bullet bulletEntity2 = new Bullet(EntityRegistries.BULLET.get(), player, player.level);
						bulletEntity2.setGravity(2).setDamage(gun.getDamage());
						bulletEntity2.setPos(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
						bulletEntity2.shootFromRotation(player, rotationPitch, rotationYaw, 0, gun.getPower(),
								gun.getInaccuracy());
						player.level.addFreshEntity(bulletEntity2);
						gun.spawnParticles(player);
						System.out.println("adding bullet");
					}
				}

				if (gun.isShotgun()) {
					int bullets = (int) Util.numInRange(4, 7);
					for (int bullet = 0; bullet < bullets; bullet++) {
						if (!player.level.isClientSide) {
							Bullet bulletShotgun = new Bullet(EntityRegistries.BULLET.get(), player, player.level);
							bulletShotgun.setGravity(2).setDamage(gun.getDamage());
							bulletShotgun.setPos(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
							bulletShotgun.shootFromRotation(player, player.xRot, player.yRot, 0, gun.getPower(),
									gun.getInaccuracy());
							player.level.addFreshEntity(bulletShotgun);
						}
					}
				}

				player.xRot = player.xRot - (Util.numInRange(-gun.getRecoil() * 0.75f, gun.getRecoil()) * 0.75f);
				player.yRot = player.yRot - (Util.numInRange(-gun.getRecoil() * 0.75f, gun.getRecoil()) * 0.75f);

			}
		});

		context.setPacketHandled(true);
	}
}
