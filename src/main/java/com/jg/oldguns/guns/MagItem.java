package com.jg.oldguns.guns;

import com.jg.oldguns.containers.MagContainer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class MagItem extends Item {

	public static final float LIGHT = 1.0f;
	public static final float MEDIUM = 1.5f;
	public static final float HEAVY = 2f;

	int maxAmmo;
	int metal;

	float weight;

	boolean isIron;

	String gunId;
	String acceptedSize;
	String proj;

	public MagItem(Item.Properties prop, String gunid, int maxammo, String acceptedSize, int metal, boolean isIron,
			float weight, String proj) {
		super(prop);
		this.gunId = gunid;
		this.maxAmmo = maxammo;
		this.acceptedSize = acceptedSize;
		this.metal = metal;
		this.isIron = isIron;
		this.weight = weight;
		this.proj = proj;
	}

	// Item methods

	@Override
	public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
		if (!p_77659_1_.isClientSide) {
			NetworkHooks.openScreen((ServerPlayer) p_77659_2_, new SimpleMenuProvider(
					(id, pi, cplayer) -> new MagContainer(id, pi), Component.translatable("gui.oldguns.mag_gui")));
		}
		return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
	}

	// NBT Methods

	public CompoundTag getNBT(ItemStack stack) {
		return stack.getOrCreateTag();
	}

	/*public int getNBTBullets(ItemStack stack) {
		return getNBT(stack).getInt(Constants.BULLETS);
	}

	public void setNBTBullets(ItemStack stack, int bullets) {
		getNBT(stack).putInt(Constants.BULLETS, bullets);
	}*/

	// End

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public String getGunId() {
		return gunId;
	}

	public int getMetal() {
		return metal;
	}

	public void setMetal(int metal) {
		this.metal = metal;
	}

	public boolean isIron() {
		return isIron;
	}

	public void setIron(boolean isIron) {
		this.isIron = isIron;
	}

	public float getWeight() {
		return weight;
	}

	public String getAcceptedSize() {
		return acceptedSize;
	};

	public String getProjectile() {
		return proj;
	}
	
}
