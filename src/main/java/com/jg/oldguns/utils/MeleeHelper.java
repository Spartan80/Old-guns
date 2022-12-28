package com.jg.oldguns.utils;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.ClientsHandler;
import com.jg.oldguns.network.BodyHitMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class MeleeHelper {
	public static void hit(float damage) {
		Player player = Minecraft.getInstance().player;
		Entity entity = RayTraceHelper.rayTraceEntities(player.level, player,
				new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ()), Entity.class);
		if (entity instanceof LivingEntity) {
			ClientsHandler.getClient(Minecraft.getInstance().getUser()).getHitmarker().reset();
			OldGuns.channel.sendToServer(new BodyHitMessage(entity.getId(), damage));
		}
	}
}
