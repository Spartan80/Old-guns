package com.jg.oldguns.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.jg.oldguns.client.animations.Animation;
import com.jg.oldguns.client.animations.Keyframe;
import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class Utils {

	public static final List<BakedQuad> EMPTY = new ArrayList<>();

	public static ItemStack getStack() {
		return Minecraft.getInstance().player.getMainHandItem();
	}
	
	public static Player getPlayer() {
		return Minecraft.getInstance().player;
	}
	
	public static void spawnParticlesOnPlayerView(Player player, int amount, float ox, float oy, float oz) {
		Vec3 view = player.getViewVector(1.0f);
		if (amount <= 0) {
			amount = 50;
		}
		for (int i = 0; i < amount; i++) {
			player.level.addParticle(ParticleTypes.SMOKE, (player.getX() + view.x) + ox, (player.getEyeY()) + oy,
					(player.getZ() + view.z) + oz, view.x * 0.1f, 0, 0);
		}
	}

	public static GunModelPart getGunPartByName(GunModel model, String name) {
		for (GunModelPart part : model.getGunParts()) {
			if (part.getName().equals(name)) {
				return part;
			}
		}
		return null;
	}

	public static <T> void insertInto(int index, List<T> list, T object) {
		if (index < list.size() - 1) {
			T next = null;
			T temp = null;
			LogUtils.getLogger().info("index: " + index + " list size-1: " + (list.size() - 1));
			for (int i = index; i < list.size() - 1; i++) {
				LogUtils.getLogger().info("i: " + i);
				if (temp == null) {
					temp = list.get(i + 1);
					list.set(i + 1, list.get(i));
				} else {
					next = list.get(i + 1);
					list.set(i + 1, temp);
					temp = next;
				}
			}
			list.add(temp);
			list.set(index, object);
			LogUtils.getLogger().info("temp == null: " + (temp == null) + " next == null: " + (next == null));
		} else if (list.size() - 1 == 1) {
			list.add(list.get(1));
			list.set(1, object);
			LogUtils.getLogger().info("2");
		} else if (list.size() - 1 == 0) {
			list.add(list.get(0));
			list.set(0, object);
			LogUtils.getLogger().info("3");
		} else if (list.size() - 1 == index) {
			list.add(list.get(list.size() - 1));
			list.set(list.size() - 2, object);
			LogUtils.getLogger().info("index: " + index + " list size-1: " + (list.size() - 1));
		} else {
			LogUtils.getLogger().info("index: " + index + " list size-1: " + (list.size() - 1));
		}
		// list.add(temp);
		// list.set(index, object);
	}

	public static void updateKeyframesFromAnimation(Animation animation) {
		int dur = 0;
		for (int i = 0; i < animation.getKeyframes().size(); i++) {
			Keyframe kf = animation.getKeyframes().get(i);
			if (kf == null)
				continue;
			kf.startTick = dur;
			kf.startVisualTick = kf.startTick + kf.dur;
			dur += kf.dur;
		}
		animation.setDuration(dur);
	}

	public static List<Item> getItemsFromTag(TagKey<Item> tag) {
		List<Item> list = Lists.newArrayList();

		for (Holder<Item> holder : Registry.ITEM.getTagOrEmpty(tag)) {
			list.add(new ItemStack(holder).getItem());
		}

		if (list.size() == 0) {
			list.add(new ItemStack(net.minecraft.world.level.block.Blocks.BARRIER)
					.setHoverName(Component.translatable("Empty Tag: " + tag.location())).getItem());
		}
		return list;
	}
	
	public static List<Keyframe> removeExtraCycle(List<Keyframe> list){
		List<Keyframe> finalList = new ArrayList<>();
		boolean oneTime = false;
		for(int i = 0; i < list.size()-1; i++) {
			Keyframe kf = list.get(i);
			if(kf.type == 0 && !oneTime) {
				finalList.add(kf);
			} else if(kf.type == 1 && !oneTime) {
				oneTime = true;
				finalList.add(kf);
			} else if(kf.type == 2 && oneTime && list.get(i+1).type == 0) {
				oneTime = false;
				finalList.add(kf);
			}
		}
		finalList.add(list.get(list.size()-1));
		
		int dur = 0;
		for (int i = 0; i < finalList.size(); i++) {
			Keyframe kf = finalList.get(i);
			if (kf == null)
				continue;
			kf.startVisualTick = dur + kf.dur;
			dur += kf.dur;
		}
		
		return finalList;
	}
	
	public static List<Keyframe> copyKeyframes(List<Keyframe> frames){
		List<Keyframe> keyframes = new ArrayList<>();
		for(Keyframe kf : frames) {
			keyframes.add(kf.copy());
		}
		return frames;
	}
	
	public static ResourceLocation getR(Item item) {
		return ForgeRegistries.ITEMS.getKey(item);
	}
	
	public static ResourceLocation getR(ItemStack stack) {
		return ForgeRegistries.ITEMS.getKey(stack.getItem());
	}

	public static ModelResourceLocation getMR(Item item) {
		return new ModelResourceLocation(ForgeRegistries.ITEMS.getKey(item).toString(), "inventory");
	}

}
