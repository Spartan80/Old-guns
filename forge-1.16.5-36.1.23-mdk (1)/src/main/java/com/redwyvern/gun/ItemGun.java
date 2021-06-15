package com.redwyvern.gun;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.redwyvern.enchantments.GunEnchantment;
import com.redwyvern.mod.OldGuns;
import com.redwyvern.registries.ItemRegistries;
import com.redwyvern.util.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public abstract class ItemGun extends TieredItem {

	protected final Multimap<Attribute, AttributeModifier> attributes;
	public IBakedModel model, hammer;

	public ItemGun(IItemTier tierIn) {
		super(tierIn, new Item.Properties().tab(OldGuns.modtab).stacksTo(1));
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
				(double) 20, AttributeModifier.Operation.ADDITION));
		this.attributes = builder.build();
	}
	///////////////////////////////////////////// Gun methods

	public Random getRandom() {
		return Item.random;
	}

	/*
	 * @Override public void inventoryTick(ItemStack stack, World worldIn, Entity
	 * entity, int itemSlot, boolean isSelected) { super.inventoryTick(stack,
	 * worldIn, entity, itemSlot, isSelected); if(!worldIn.isRemote) {
	 * ServerPlayerEntity player = (ServerPlayerEntity)entity; if(isSelected) {
	 * Vector3d pp = player.getPositionVec(); Vector3d from = new Vector3d(pp.x-5,
	 * pp.y+2, pp.z-5); Vector3d to = new Vector3d(pp.x+5, pp.y-2, pp.z+5);
	 * for(Entity ent : player.world.getEntitiesWithinAABB(LivingEntity.class, new
	 * AxisAlignedBB(from, to))) { if(ent instanceof LivingEntity && ent != entity)
	 * { LivingEntity living = (LivingEntity)ent; System.out.println("pos " +
	 * living.getPositionVec().toString() + "health " + living.getHealth() +
	 * " type " + living.getType().toString()); Vector3d entPos =
	 * living.getPositionVec(); Vector3d dir = entPos.subtract(pp); dir =
	 * dir.normalize(); Vector3d newpos = living.getPositionVec().add(-dir.x *
	 * 0.3000000596046448D, -dir.y * 0.3000000596046448D, -dir.z *
	 * 0.3000000596046448D); living.setPosition(newpos.x, newpos.y, newpos.z);
	 * System.out.println("newpos " + living.getPositionVec().toString() + "health "
	 * + living.getHealth() + " type " + living.getType().toString()); } } } } }
	 */

	public void spawnParticles(PlayerEntity player) {
		Vector3d pos = player.position();
		Vector3d front = player.getLookAngle();
		front.multiply(5, 1, 5);
		for (int i = 0; i < 10; ++i) {
			Minecraft.getInstance().level.addParticle(ParticleTypes.CLOUD,
					(pos.x + (front.x * Util.numInRange(-0.5f, 0.5f))), (pos.y + player.getEyeHeight()),
					(pos.z + (front.z * Util.numInRange(1, 2))), (front.x * 0.06f), front.y, (front.z * 0.06f));
		}
	}

	public boolean getMoreShotsProbability(int level) {
		switch (level) {
		case 1:
			System.out.println("1");
			return prob(20);
		case 2:
			System.out.println("2");
			return prob(40);
		case 3:
			System.out.println("3");
			return prob(60);
		}
		return false;
	}

	public static boolean prob(float prob) {
		float p = prob * 0.01f;

		if (p >= Math.random()) {
			return true;
		}
		return false;
	}

	///////////////////////////////////////////// Gun methods

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment instanceof GunEnchantment;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return this.getItemStackLimit(stack) == 1;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		// TODO Auto-generated method stub
		return 5;
	}

	///////////////////////////////////////////// Gun info

	@Override
	public void appendHoverText(ItemStack p_77624_1_, World p_77624_2_, List<ITextComponent> tooltip,
			ITooltipFlag p_77624_4_) {
		tooltip.add(new TranslationTextComponent("Damage: " + getDamage()));
		tooltip.add(new TranslationTextComponent("ReloadTime: " + getReloadTime()));
		tooltip.add(new TranslationTextComponent("Power: " + getPower()));
		tooltip.add(new TranslationTextComponent("Innacuracy: " + getInaccuracy()));
		super.appendHoverText(p_77624_1_, p_77624_2_, tooltip, p_77624_4_);
	}

	///////////////////////////////////////////// Gun info

	///////////////////////////////////////////// Disables Item Reequip anim

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot,
			ItemStack stack) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributes
				: super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return true;// oldStack.getItem() != newStack.getItem();
	}

	@Override
	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		// TODO Auto-generated method stub
		return UseAction.NONE;
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		return true;
	}

	public void setupModel(IBakedModel model) {
		this.model = model;
		this.hammer = Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(
				ItemRegistries.pirate_pistol.get().getRegistryName().toString() + "_hammer_down", "inventory"));
	};

	public boolean hasHammer() {
		return true;
	};

	///////////////////////////////////////////// Disables Item Reequip anim

	///////////////////////////////////////////// NBT

	public CompoundNBT getNBT(ItemStack stack) {
		return stack.getOrCreateTag();
	}

	public int getNBTBullets(ItemStack stack) {
		return stack.getOrCreateTag().getInt("bullets");
	}

	public void setNBTBullets(ItemStack stack, int bullets) {
		stack.getOrCreateTag().putInt("bullets", bullets);
	}

	public void setNBTScope(ItemStack stack, boolean has) {
		getNBT(stack).putBoolean("scope", has);
	}

	public boolean getNBTScope(ItemStack stack) {
		return getNBT(stack).getBoolean("scope");
	}

	public void setNBTFired(ItemStack stack, boolean fired) {
		getNBT(stack).putBoolean("fired", fired);
	}

	public boolean getNBTFired(ItemStack stack) {
		return getNBT(stack).getBoolean("fired");
	}

	public void setNBTShooticks(ItemStack stack, int shootticks) {
		getNBT(stack).putInt("shoot_ticks", shootticks);
	}

	public int getNBTShooticks(ItemStack stack) {
		return getNBT(stack).getInt("shoot_ticks");
	}

	///////////////////////////////////////////// NBT

	///////////////////////////////////////////// Gun abstract methods

	public abstract float getRecoil();

	public float getShootVolume() {
		return 1.0f;
	}

	public abstract float getDamage();

	public abstract int getReloadTime();

	public abstract int getShootTime();

	public abstract float getPower();

	public abstract float getInaccuracy();

	public abstract boolean isBoth();

	public abstract Item getBodyWood();

	public abstract Item getBodyMetal();

	public abstract Item getBarrel();

	public abstract void configMatrixGun(MatrixStack matrix);

	public abstract void configMatrixGunWhileAiming(MatrixStack matrix, float aimticks);

	public abstract void configMatrixLeftHand(MatrixStack matrix);

	public abstract void configMatrixRightHand(MatrixStack matrix);

	public abstract void configMatrixHammer(MatrixStack matrix, ItemStack stack);

	public abstract void configMatrixScope(MatrixStack matrix);

	public abstract boolean hasScope();

	public abstract int getMaxAmmo();

	public abstract SoundEvent getShootSound();

	public SoundEvent getShootReloadSound() {
		return null;
	};

	public boolean isShotgun() {
		return false;
	}

	public boolean isPrimitive() {
		return true;
	}

	///////////////////////////////////////////// Gun abstract methods
}
