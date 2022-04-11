package com.jg.oldguns.client.handlers;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.jg.oldguns.utils.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RecoilHandler {

	protected final Map<Item, Cooldown> cooldowns = Maps.newHashMap();
	protected int tickCount;

	public float random;

	public RecoilHandler() {
		random = 1f;
	}

	public boolean isOnCooldown(Item p_185141_1_) {
		return this.getCooldownPercent(p_185141_1_, 0.0F) > 0.0F;
	}

	public float getCooldownPercent(Item p_185143_1_, float p_185143_2_) {
		Cooldown cooldowntracker$cooldown = this.cooldowns.get(p_185143_1_);
		if (cooldowntracker$cooldown != null) {

			float f = (float) (cooldowntracker$cooldown.endTime - cooldowntracker$cooldown.startTime);
			float f1 = (float) cooldowntracker$cooldown.endTime - ((float) this.tickCount + p_185143_2_);
			return Mth.clamp(f1 / f, 0.0F, 1.0F);
		} else {
			return 0.0F;
		}
	}

	public void tick() {
		++this.tickCount;

		if (!this.cooldowns.isEmpty()) {
			Iterator<Entry<Item, Cooldown>> iterator = this.cooldowns.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<Item, Cooldown> entry = iterator.next();
				if ((entry.getValue()).endTime <= this.tickCount) {
					iterator.remove();
					this.onCooldownEnded(entry.getKey());
				}
			}
		}
	}

	public void addCooldown(Item p_185145_1_, int p_185145_2_) {
		this.cooldowns.put(p_185145_1_, new Cooldown(this.tickCount, this.tickCount + p_185145_2_));
		this.onCooldownStarted(p_185145_1_, p_185145_2_);

	}

	@OnlyIn(Dist.CLIENT)
	public void removeCooldown(Item p_185142_1_) {
		this.cooldowns.remove(p_185142_1_);
		this.onCooldownEnded(p_185142_1_);
	}

	protected void onCooldownStarted(Item p_185140_1_, int p_185140_2_) {
	}

	protected void onCooldownEnded(Item p_185146_1_) {
	}

	public void setRandom() {
		random = (float) Util.numInRange(-0.1f, 0.1f);
	}

	public float getRandom() {
		return random;
	}

	public float getRecoil(ItemStack stack) {
		return RecoilHandler.this.getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());
	}
	
	public void reset() {
		tickCount = 0;
	}

	class Cooldown {
		private final int startTime;
		private final int endTime;

		private Cooldown(int p_i47037_2_, int p_i47037_3_) {
			this.startTime = p_i47037_2_;
			this.endTime = p_i47037_3_;
		}
	}
}
