package com.jg.oldguns.network;

import java.util.function.Supplier;

import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.ItemGun.ShootMode;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent.Context;

public class UpdateGunPropertiesMessage {
	
	float dmg;
	float power;
	float inn;
	float hRec;
	float vRec;
	float zRec;
	float rdr;
	float r;
	
	int st;
	int recTime;
	
	float shotGunB;
	float burstShots;
	float burstTargetTime;
	
	boolean isShotgun;
	boolean requiresMag;
	boolean hasScope;
	boolean canChageParts;
	
	String bulletType;
	String gunId;
	String bulletId;
	
	Sound sound;
	
	Item bullet;
	
	ShootMode shootMode;
	
	public UpdateGunPropertiesMessage(float dmg, float power, float inn, 
			float hRec, float vRec, float zRec, float recTime, float rdr) {
		
	}

	public static void encode(UpdateGunPropertiesMessage msg, FriendlyByteBuf buf) {
		
	}

	public static UpdateGunPropertiesMessage decode(FriendlyByteBuf buf) {
		return new UpdateGunPropertiesMessage();
	}

	public static void handle(UpdateGunPropertiesMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			
		});
		context.setPacketHandled(true);
	}
	
}
