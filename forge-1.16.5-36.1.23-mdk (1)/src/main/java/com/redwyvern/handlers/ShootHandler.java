package com.redwyvern.handlers;

import com.redwyvern.animation.AnimationManager;
import com.redwyvern.gun.ItemGun;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.network.HandleShootMessage;
import com.redwyvern.util.Util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

public class ShootHandler {

	public ShootHandler() {

	}

	public void tryShoot(ItemStack stack, PlayerEntity player, AnimationManager man) {
		int bullets = stack.getOrCreateTag().getInt("bullets");
		ItemGun gun = (ItemGun) stack.getItem();
		if (bullets > 0 && bullets <= gun.getMaxAmmo() && !player.isSprinting() && !man.start
				&& man.shootticks >= gun.getShootTime()
				|| (player.isCreative() && man.shootticks >= gun.getShootTime() && !man.start
						&& !player.isSprinting())) {
			System.out.println("able to shoot and sending message to server");
			man.shootticks = 0;
			man.shoot = false;
			player.level.playSound(player, player.getX(), player.getY(), player.getZ(), gun.getShootSound(),
					SoundCategory.NEUTRAL, gun.getShootVolume(), 1.0f);
			man.startRecoilTicks(gun.getRecoil(), gun.getRegistryName().toString());
			man.startShootingTicks(gun.getShootTime());
			OldGuns.channel.sendToServer(new HandleShootMessage(player.yRot, player.xRot));
			player.xRot = player.xRot - (Util.numInRange(-gun.getRecoil() * 0.75f, gun.getRecoil()) * 0.75f);
			player.yRot = player.yRot - (Util.numInRange(-gun.getRecoil() * 0.75f, gun.getRecoil()) * 0.75f);
		}
	}

}
