package com.jg.oldguns.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

public class PlaySoundMessage {
	private final String sound, category;
	private final double x, y, z;
	private final float volume, pitch;

	public PlaySoundMessage(SoundEvent sound, double x, double y, double z) {
		this(sound, x, y, z, 1.0F, 1.0F);
	}

	public PlaySoundMessage(SoundEvent sound, double x, double y, double z, float volume, float pitch) {
		this(sound.getRegistryName().toString(), x, y, z, volume, pitch);
	}

	public PlaySoundMessage(String sound, double x, double y, double z, float volume, float pitch) {
		this(sound, SoundSource.NEUTRAL, x, y, z, volume, pitch);
	}

	public PlaySoundMessage(SoundEvent sound, SoundSource cat, double x, double y, double z, float volume,
			float pitch) {
		this(sound.getRegistryName().toString(), cat, x, y, z, volume, pitch);
	}

	public PlaySoundMessage(String sound, SoundSource category, double x, double y, double z, float volume,
			float pitch) {
		this(sound, category.toString(), x, y, z, volume, pitch);
	}

	public PlaySoundMessage(String sound, String category, double x, double y, double z, float volume, float pitch) {
		this.sound = sound;
		this.category = category;
		this.x = x;
		this.y = y;
		this.z = z;
		this.volume = volume;
		this.pitch = pitch;
	}

	public static void encode(PlaySoundMessage msg, FriendlyByteBuf packetBuffer) {
		packetBuffer.writeUtf(msg.sound).writeUtf(msg.category).writeDouble(msg.x).writeDouble(msg.y).writeDouble(msg.z)
				.writeFloat(msg.volume).writeFloat(msg.pitch);
	}

	public static PlaySoundMessage decode(FriendlyByteBuf b) {
		return new PlaySoundMessage(b.readUtf(32767), b.readUtf(32767), b.readDouble(), b.readDouble(), b.readDouble(),
				b.readFloat(), b.readFloat());
	}

	public static void handle(PlaySoundMessage msg, Supplier<Context> ctx) {
		Context context = ctx.get();

		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();

			SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(msg.sound));

			if (player != null && sound != null) {
				System.out.println(sound.getRegistryName().toString());
				SoundSource category = SoundSource.valueOf(msg.category);
				player.level.playSound((Player) null, msg.x, msg.y, msg.z, sound, category, msg.volume,
						msg.pitch);

			}
		});

		context.setPacketHandled(true);
	}
}
