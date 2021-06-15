package com.redwyvern.network;

import java.util.function.Supplier;

import com.redwyvern.animation.AnimationManager;
import com.redwyvern.proxy.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class PlaySoundMessage {
	private final String sound, category;
	private final double x, y, z;
	private final float volume, pitch;
	private final boolean hit;

	public PlaySoundMessage(SoundEvent sound, double x, double y, double z, boolean hit) {
		this(sound, x, y, z, 1.0F, 1.0F, hit);
	}

	public PlaySoundMessage(SoundEvent sound, double x, double y, double z, float volume, float pitch, boolean hit) {
		this(sound.getRegistryName().toString(), x, y, z, volume, pitch, hit);
	}

	public PlaySoundMessage(String sound, double x, double y, double z, float volume, float pitch, boolean hit) {
		this(sound, SoundCategory.NEUTRAL, x, y, z, volume, pitch, hit);
	}

	public PlaySoundMessage(String sound, SoundCategory category, double x, double y, double z, float volume,
			float pitch, boolean hit) {
		this(sound, category.toString(), x, y, z, volume, pitch, hit);
	}

	public PlaySoundMessage(String sound, String category, double x, double y, double z, float volume, float pitch,
			boolean hit) {
		this.sound = sound;
		this.category = category;
		this.x = x;
		this.y = y;
		this.z = z;
		this.volume = volume;
		this.pitch = pitch;
		this.hit = hit;
	}

	public static void encode(PlaySoundMessage msg, PacketBuffer packetBuffer) {
		packetBuffer.writeUtf(msg.sound).writeUtf(msg.category).writeDouble(msg.x).writeDouble(msg.y).writeDouble(msg.z)
				.writeFloat(msg.volume).writeFloat(msg.pitch).writeBoolean(msg.hit);
	}

	public static PlaySoundMessage decode(PacketBuffer b) {
		return new PlaySoundMessage(b.readUtf(32767), b.readUtf(32767), b.readDouble(), b.readDouble(), b.readDouble(),
				b.readFloat(), b.readFloat(), b.readBoolean());
	}

	public static void handle(PlaySoundMessage message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> run(message)));
		ctx.get().setPacketHandled(true);
	}

	@OnlyIn(Dist.CLIENT)
	public static void run(PlaySoundMessage msg) {
		PlayerEntity player = Minecraft.getInstance().player;
		SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(msg.sound));

		if (player != null && sound != null) {
			SoundCategory category = SoundCategory.valueOf(msg.category);
			player.level.playSound(player, msg.x, msg.y, msg.z, sound, category, msg.volume, msg.pitch);

		}

		if (msg.hit) {
			AnimationManager man = ClientProxy.manager.get(player.getUUID());
			if (man == null)
				return;

			man.renderCrosshair = true;
		}
	}
}
