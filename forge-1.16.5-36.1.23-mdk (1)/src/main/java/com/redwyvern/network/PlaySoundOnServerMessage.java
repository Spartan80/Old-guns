package com.redwyvern.network;

import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class PlaySoundOnServerMessage {
	private final String sound, category;
	private final double x, y, z;
	private final float volume, pitch;

	public PlaySoundOnServerMessage(SoundEvent sound, double x, double y, double z) {
		this(sound, x, y, z, 1.0F, 1.0F);
	}

	public PlaySoundOnServerMessage(SoundEvent sound, double x, double y, double z, float volume, float pitch) {
		this(sound.getRegistryName().toString(), x, y, z, volume, pitch);
	}

	public PlaySoundOnServerMessage(String sound, double x, double y, double z, float volume, float pitch) {
		this(sound, SoundCategory.NEUTRAL, x, y, z, volume, pitch);
	}

	public PlaySoundOnServerMessage(String sound, SoundCategory category, double x, double y, double z, float volume,
			float pitch) {
		this(sound, category.toString(), x, y, z, volume, pitch);
	}

	public PlaySoundOnServerMessage(String sound, String category, double x, double y, double z, float volume,
			float pitch) {
		this.sound = sound;
		this.category = category;
		this.x = x;
		this.y = y;
		this.z = z;
		this.volume = volume;
		this.pitch = pitch;
	}

	public static void encode(PlaySoundOnServerMessage msg, PacketBuffer packetBuffer) {
		packetBuffer.writeUtf(msg.sound).writeUtf(msg.category).writeDouble(msg.x).writeDouble(msg.y).writeDouble(msg.z)
				.writeFloat(msg.volume).writeFloat(msg.pitch);
	}

	public static PlaySoundOnServerMessage decode(PacketBuffer b) {
		return new PlaySoundOnServerMessage(b.readUtf(32767), b.readUtf(32767), b.readDouble(), b.readDouble(),
				b.readDouble(), b.readFloat(), b.readFloat());
	}

	public static void handle(PlaySoundOnServerMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayerEntity player = context.getSender();

			SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(msg.sound));

			if (player != null && sound != null) {
				SoundCategory category = SoundCategory.valueOf(msg.category);
				player.level.playSound(player, msg.x, msg.y, msg.z, sound, category, msg.volume, msg.pitch);

			}
		});

		context.setPacketHandled(true);
	}
}
