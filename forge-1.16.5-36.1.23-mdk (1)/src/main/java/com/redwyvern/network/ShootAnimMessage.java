package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.animation.AnimationManager;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.proxy.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class ShootAnimMessage {

	public ShootAnimMessage() {
		// TODO Auto-generated constructor stub
	}

	public static void encode(ShootAnimMessage msg, PacketBuffer packetBuffer) {

	}

	public static ShootAnimMessage decode(PacketBuffer b) {
		return new ShootAnimMessage();
	}

	public static void handle(ShootAnimMessage message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> run(message)));
		ctx.get().setPacketHandled(true);
	}

	@OnlyIn(Dist.CLIENT)
	public static void run(ShootAnimMessage msg) {
		PlayerEntity player = Minecraft.getInstance().player;

		if (player != null) {
			AnimationManager man = ClientProxy.manager.get(player.getUUID());
			if (man != null) {
				man.shootticks++;
				System.out.println("man shootticks: " + man.shootticks);
				if (man.shootticks == 4) {
					ItemGun gun = (ItemGun) player.getMainHandItem().getItem();
					if (gun.getShootReloadSound() != null) {
						System.out.println("playing sound with clientSide: " + player.level.isClientSide);
						player.level.playSound(player, player.getX(), player.getY(), player.getZ(),
								gun.getShootReloadSound(), SoundCategory.NEUTRAL, 1.0f, 1.0f);
						OldGuns.channel.sendToServer(new PlaySoundOnServerMessage(gun.getShootReloadSound(),
								player.getX(), player.getY(), player.getZ()));
					}
				}
			}
		}
	}
}
