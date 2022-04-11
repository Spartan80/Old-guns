package com.jg.oldguns.utils;

import com.mojang.math.Vector3d;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RayTraceHelper {
	
	public static <T extends Entity> T rayTraceEntities(Level w, Player player, Vec3 pos, Class<T> entityClazz) {
		Vec3 dir = player.getLookAngle().scale(2);
		Vec3 end = pos.add(dir);
		AABB aabb = new AABB(pos.x, pos.y, pos.z, end.x, end.y, end.z).expandTowards(dir.x, dir.y, dir.z);
		Vec3 checkVec = pos.add(dir);
		for (T t : w.getEntitiesOfClass(entityClazz, aabb)) {
			AABB entityBB = t.getBoundingBox();
			if (entityBB == null) {
				continue;
			}
			
			if (entityBB.intersects(Math.min(pos.x, checkVec.x), Math.min(pos.y, checkVec.y), Math.min(pos.z, checkVec.z), Math.max(pos.x, checkVec.x), Math.max(pos.y, checkVec.y), Math.max(pos.z, checkVec.z))){
				if(t != player) {
					return t;
				}
			}
		}
		
		return null;
	}
}
