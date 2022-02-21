package com.jg.oldguns.client.handlers;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.network.PlaySoundMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundHandler {
	public static void playSoundOnClient(SoundEvent e, SoundCategory s) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		player.clientLevel.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), e, s, 1.0f,
				1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F));
	}

	public static void playSoundOnClientWithVolume(SoundEvent e, SoundCategory s, float volume, float pitch) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		player.clientLevel.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), e, s, volume,
				pitch);
	}

	public static void playSoundOnClientAt(SoundEvent e, SoundCategory s, double x, double y, double z, float volume,
			float pitch) {
		ClientPlayerEntity player = Minecraft.getInstance().player;
		player.clientLevel.playSound((PlayerEntity) null, x, y, z, e, s, volume, pitch);
	}

	public static void playSoundOnServer(SoundEvent e, SoundCategory cat) {
		PlayerEntity player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, cat, player.getX(), player.getY(), player.getZ(), 0.4f,
				1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F)));
	}

	public static void playSoundOnServer(SoundEvent e) {
		PlayerEntity player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, SoundCategory.NEUTRAL, player.getX(), player.getY(),
				player.getZ(), 0.4f, 1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F)));
	}

	public static void playSoundOnServerWithVolume(SoundEvent e, SoundCategory cat, float volume, float pitch) {
		PlayerEntity player = Minecraft.getInstance().player;
		OldGuns.channel
				.sendToServer(new PlaySoundMessage(e, cat, player.getX(), player.getY(), player.getZ(), volume, pitch));
	}

	public static void playSoundOnServerAt(SoundEvent e, SoundCategory cat, double x, double y, double z, float volume,
			float pitch) {
		PlayerEntity player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, cat, x, y, z, volume, pitch));
	}

	public static void playSoundOnBoth(SoundEvent e, SoundCategory cat) {
		PlayerEntity player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, cat, player.getX(), player.getY(), player.getZ(), 1.0f,
				1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F)));
		Minecraft.getInstance().player.playSound(e, 1.0f, 1.2F / (player.getRandom().nextFloat() * 0.2F + 0.9F));
	}

	public static void playSoundOnBothWithVolume(SoundEvent e, SoundCategory cat, float volume, float pitch) {
		PlayerEntity player = Minecraft.getInstance().player;
		OldGuns.channel
				.sendToServer(new PlaySoundMessage(e, cat, player.getX(), player.getY(), player.getZ(), volume, pitch));
		Minecraft.getInstance().player.clientLevel.playSound((PlayerEntity) null, player.getX(), player.getY(),
				player.getZ(), e, cat, volume, pitch);
	}

	public static void playSoundOnBothAt(SoundEvent e, SoundCategory cat, double x, double y, double z, float volume,
			float pitch) {
		PlayerEntity player = Minecraft.getInstance().player;
		OldGuns.channel.sendToServer(new PlaySoundMessage(e, cat, x, y, z, volume, pitch));
		Minecraft.getInstance().player.clientLevel.playSound((PlayerEntity) null, x, y, z, e, cat, volume, pitch);
	}
}
