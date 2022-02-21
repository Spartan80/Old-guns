package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.ItemMag;
import com.jg.oldguns.utils.Constants;

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

				player.xRot = msg.pitch;
				player.yRot = msg.yaw;

				if (!gun.isShotgun(stack)) {
					
					float rotationPitch = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy(stack);
					float rotationYaw = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy(stack);
					rotationPitch = player.xRot + rotationPitch;
					rotationYaw = player.yRot + rotationYaw;

					Bullet bulletEntity = stack.getOrCreateTag().getBoolean(Constants.HASMAG)
							? ((ItemMag) gun.getMagPath(stack)).getProjectile(player, world)
							: gun.getProjectile(stack).create(player, world);

					bulletEntity.setGravity(2).setDamage((float) gun.getGunDamage(stack))
							.setDamageReduction(gun.getRangeDamageReduction(stack)).setRange(gun.getRange(stack));// 0.8f

					bulletEntity.setPos(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
					bulletEntity.shootFromRotation(player, player.xRot, player.yRot, 0, (float) gun.getPower(stack),
							gun.getInaccuracy(stack));

					world.addFreshEntity(bulletEntity);

					world.playSound((ServerPlayerEntity) null, player.getX(), player.getY(), player.getZ(),
							gun.getShootSound(stack), SoundCategory.NEUTRAL, 0.1f, 1.0f);

					player.getCooldowns().addCooldown(gun, gun.getShootTime(stack));

					gun.setNBTBullets(stack, Math.max(0, gun.getNBTBullets(stack) - 1));
				
					/*player.xRot = (float) (player.xRot - (Util.numInRange(-(gun.getRHorRecoil(stack) * 10f) * 0.75f,
							(gun.getRHorRecoil(stack) * 10f)) * 0.75f));
					player.yRot = (float) (player.yRot - (Util.numInRange(-(gun.getRVertRecoil(stack) * 10f) * 0.75f,
							(gun.getRVertRecoil(stack) * 10f)) * 0.75f));
					*/
					player.xRot = msg.pitch;
					player.yRot = msg.yaw;
				} else {
					gun.setNBTBullets(stack, Math.max(0, gun.getNBTBullets(stack) - 1));
					
					for(int i = 0; i < gun.getShotgunBullets(stack); i++) {
						float rotationPitch = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy(stack);
						float rotationYaw = (gun.getRandom().nextFloat() * 2 - 1) * gun.getInaccuracy(stack);
						rotationPitch = player.xRot + rotationPitch;
						rotationYaw = player.yRot + rotationYaw;

						Bullet bulletEntity = stack.getOrCreateTag().getBoolean(Constants.HASMAG)
								? ((ItemMag) gun.getMagPath(stack)).getProjectile(player, world)
								: gun.getProjectile(stack).create(player, world);

						bulletEntity.setGravity(2).setDamage((float) gun.getGunDamage(stack))
								.setDamageReduction(gun.getRangeDamageReduction(stack)).setRange(gun.getRange(stack));// 0.8f

						bulletEntity.setPos(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
						bulletEntity.shootFromRotation(player, player.xRot, player.yRot, 0, (float) gun.getPower(stack),
								gun.getInaccuracy(stack));

						world.addFreshEntity(bulletEntity);

						world.playSound((ServerPlayerEntity) null, player.getX(), player.getY(), player.getZ(),
								gun.getShootSound(stack), SoundCategory.NEUTRAL, 0.1f, 1.0f);

						player.getCooldowns().addCooldown(gun, gun.getShootTime(stack));

						player.xRot = msg.pitch;
						player.yRot = msg.yaw;
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
