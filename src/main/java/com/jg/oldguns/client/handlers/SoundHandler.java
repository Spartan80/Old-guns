package com.jg.oldguns.client.handlers;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.network.PlaySoundMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class SoundHandler {
	public static void playSoundOnClient(SoundEvent e, SoundSource s) {
		LocalPlayer player = Minecraft.getInstance().player;
		player.clientLevel.playSound((Player) null, player.getX(), player.getY(), player.getZ(), e, s, 1.0f,
				1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F));
	}

	public static void playSoundOnClientWithVolume(SoundEvent e, SoundSource s, float volume, float pitch) {
		LocalPlayer player = Minecraft.getInstance().player;
		player.clientLevel.playSound((Player) null, player.getX(), player.getY(), player.getZ(), e, s, volume,
				pitch);
	}

	public static void playSoundOnClientAt(SoundEvent e, SoundSource s, double x, double y, double z, float volume,
			float pitch) {
		LocalPlayer player = Minecraft.getInstance().player;
		player.clientLevel.playSound((Player) null, x, y, z, e, s, volume, pitch);
	}

	public static void playSoundOnServer(SoundEvent e, SoundSource cat) {
		Player player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, cat, player.getX(), player.getY(), player.getZ(), 0.4f,
				1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F)));
	}

	public static void playSoundOnServer(SoundEvent e) {
		Player player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, SoundSource.NEUTRAL, player.getX(), player.getY(),
				player.getZ(), 0.4f, 1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F)));
	}

	public static void playSoundOnServerWithVolume(SoundEvent e, SoundSource cat, float volume, float pitch) {
		Player player = Minecraft.getInstance().player;
		OldGuns.channel
				.sendToServer(new PlaySoundMessage(e, cat, player.getX(), player.getY(), player.getZ(), volume, pitch));
	}

	public static void playSoundOnServerAt(SoundEvent e, SoundSource cat, double x, double y, double z, float volume,
			float pitch) {
		Player player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, cat, x, y, z, volume, pitch));
	}

	public static void playSoundOnBoth(SoundEvent e, SoundSource cat) {
		Player player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, cat, player.getX(), player.getY(), player.getZ(), 1.0f,
				1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F)));
		Minecraft.getInstance().player.playSound(e, 1.0f, 1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F));
	}

	public static void playSoundOnBothWithVolume(SoundEvent e, SoundSource cat, float volume, float pitch) {
		Player player = Minecraft.getInstance().player;
		OldGuns.channel
				.sendToServer(new PlaySoundMessage(e, cat, player.getX(), player.getY(), player.getZ(), volume, pitch));
		Minecraft.getInstance().player.clientLevel.playSound((Player) null, player.getX(), player.getY(),
				player.getZ(), e, cat, volume, pitch);
	}

	public static void playSoundOnBothAt(SoundEvent e, SoundSource cat, double x, double y, double z, float volume,
			float pitch) {
		Player player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, cat, x, y, z, volume, pitch));
		Minecraft.getInstance().player.clientLevel.playSound((Player) null, x, y, z, e, cat, volume, pitch);
	}
}
