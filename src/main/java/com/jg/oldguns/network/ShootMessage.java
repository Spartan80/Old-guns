package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.entities.GunBullet;
import com.jg.oldguns.guns.GunItem;
import com.jg.oldguns.guns.GunPart;
import com.jg.oldguns.utils.NBTUtils;
import com.mojang.logging.LogUtils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class ShootMessage {

	private final String sound;
	float yaw;
	float pitch;

	public ShootMessage(float yaw, float pitch, String sound) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.sound = sound;
	}

	public static void encode(ShootMessage msg, FriendlyByteBuf buf) {
		buf.writeFloat(msg.yaw);
		buf.writeFloat(msg.pitch);
		buf.writeUtf(msg.sound);
	}

	public static ShootMessage decode(FriendlyByteBuf buf) {
		return new ShootMessage(buf.readFloat(), buf.readFloat(), buf.readUtf(32767));
	}

	public static void handle(ShootMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			ItemStack stack = player.getMainHandItem();
			LogUtils.getLogger().info("Bulletsxd: ");
			if (player != null) {
				player.setXRot(msg.pitch);
				player.setYRot(msg.yaw);
				SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(msg.sound));
				if (sound != null) {
					System.out.println(sound.toString());
					player.level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), sound,
							SoundSource.NEUTRAL, 1.0f, 1.0f);
				}
				GunItem gun = (GunItem) player.getMainHandItem().getItem();
				float f = -Mth.sin(msg.yaw * ((float) Math.PI / 180F)) * Mth.cos(msg.pitch * ((float) Math.PI / 180F));
				float f1 = -Mth.sin((msg.pitch) * ((float) Math.PI / 180F));
				float f2 = Mth.cos(msg.yaw * ((float) Math.PI / 180F)) * Mth.cos(msg.pitch * ((float) Math.PI / 180F));
				LogUtils.getLogger().info("Bullets: " + gun.getBulletsPerShoot());
				
				/*String barrel = NBTUtils.getBarrel(stack);
				Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(barrel));
				int bullets = 0;
				if(item != null) {
					bullets = ((GunPart)item).getGunPartProperties().getBulletsPerShoot();
					LogUtils.getLogger().info("bullets: " + bullets);
				}*/
				
				for (int i = 0; i < gun.getBulletsPerShoot(); i++) {
					GunBullet bullet = new GunBullet(player, player.level);
					bullet.setPos(player.position().add(0, player.getEyeHeight(), 0));
					bullet.setDamage(gun.getDamage()).setRange(gun.getRange()).setGravity(0.9f)
							.setDamageReduction(gun.getRangeDamageReduction());
					bullet.shoot((double) f, (double) f1, (double) f2, gun.getPower(), gun.getInnacuracy());
					Vec3 vec3 = player.getDeltaMovement();
					bullet.setDeltaMovement(
							bullet.getDeltaMovement().add(vec3.x, player.isOnGround() ? 0.0D : vec3.y, vec3.z));
					player.level.addFreshEntity(bullet);
				}
				int ammo = NBTUtils.getAmmo(player.getMainHandItem()) - 1;
				NBTUtils.setAmmo(player.getMainHandItem(), ammo > 0 ? ammo : 0);
				LogUtils.getLogger().info("Shooting Server");
			}
		});
		context.setPacketHandled(true);
	}
}
