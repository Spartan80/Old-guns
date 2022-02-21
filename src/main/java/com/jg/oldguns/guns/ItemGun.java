package com.jg.oldguns.guns;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.guns.properties.BodyProperties.BulletProvider;
import com.jg.oldguns.guns.properties.GunPropertiesHandler;
import com.jg.oldguns.network.HandleShootMessage;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public abstract class ItemGun extends Item {

	protected final Multimap<Attribute, AttributeModifier> attributes;

	protected final UUID SPEED_MOD = UUID.randomUUID();

	protected AttributeModifier SPEED_MODIFIER_SPRINTING;

	protected RegistryObject<Item> mag;

	public ItemGun(Item.Properties prop, RegistryObject<Item> mag) {
		super(prop);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
				(double) 20, AttributeModifier.Operation.ADDITION));
		this.attributes = builder.build();
		SPEED_MODIFIER_SPRINTING = new AttributeModifier(SPEED_MOD, "Sprinting speed gun mod", -getBaseSpeedModifier(),
				AttributeModifier.Operation.ADDITION);
		this.mag = mag;
	}

	// item methods

	@Override
	public void appendHoverText(ItemStack stack, World p_77624_2_, List<ITextComponent> tooltip,
			ITooltipFlag p_77624_4_) {
		if(GunPropertiesHandler.INSTANCE.isAble() && Minecraft.getInstance().player != null) {
			tooltip.add(new TranslationTextComponent("Damage: " + getGunDamage(stack)));
			tooltip.add(new TranslationTextComponent("Power: " + getPower(stack)));
			tooltip.add(new TranslationTextComponent("Innacuracy: " + getInaccuracy(stack)));
			tooltip.add(new TranslationTextComponent("Has scope: " + ServerUtils.hasScope(stack)));
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot,
			ItemStack stack) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributes
				: super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return oldStack.getItem() != newStack.getItem();
	}

	@Override
	public UseAction getUseAnimation(ItemStack p_77661_1_) {
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

	public Random getRandom() {
		return random;
	}

	@Override
	public void inventoryTick(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_,
			boolean p_77663_5_) {
		if (!p_77663_2_.isClientSide) {
			PlayerEntity player = (PlayerEntity) p_77663_3_;
			tickServer(stack, p_77663_2_, player);
		}
		super.inventoryTick(stack, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
	}

	// gun methods
	
	public Item getDefaultMag() {
		return mag.get();
	}

	public boolean isMagEmpty(ItemStack stack) {
		return stack.getOrCreateTag().getInt(Constants.BULLETS) == 0;
	}

	public void tryShoot(ItemStack stack, ClientHandler handler) {
		PlayerEntity player = ClientEventHandler.getPlayer();
		int bullets = getNBTBullets(stack);
		
		if(getShootMode(stack) != ShootMode.BURST) {
			//System.out.println(bullets > 0);
			if (bullets > 0 && bullets <= getMaxAmmo(stack) && !player.isSprinting() && handler.isAnimationEmpty()
					&& handler.getGunModel().canShoot(stack)
					|| (player.isCreative() && player.getCooldowns().getCooldownPercent(this, Util.getFrameTime()) == 0
							&& handler.getGunModel().canShoot(stack)) && handler.getGunModel().canShoot(stack)) {
				handler.pickRandomRecoil();
				shoot(player, stack, handler, bullets);
				//System.out.println("Shooting");
			}
		}else {
			if (bullets > 0 && bullets <= getMaxAmmo(stack) && !player.isSprinting() && handler.isAnimationEmpty()
					&& handler.getGunModel().canShoot(stack)
					|| (player.isCreative()
							&& handler.getGunModel().canShoot(stack)) && handler.getGunModel().canShoot(stack)) {
				
				handler.pickRandomRecoil();
				shoot(player, stack, handler, bullets);
			}
		}
	}

	public void shoot(PlayerEntity player, ItemStack stack, ClientHandler handler, int bullets) {
		
		if(getShootMode(stack) == ShootMode.AUTO || getShootMode(stack) == ShootMode.SEMI) {
			handler.getRecoilHandler().addCooldown(stack.getItem(), getRecoilTime(stack));
			//System.out.println("another shoot");
			OldGuns.channel.sendToServer(new HandleShootMessage(player.yRot, player.xRot));
			/*
			 player.xRot = (float) (player.xRot
					- (Util.numInRange(-(getRHorRecoil(stack) * 10f) * 0.75D, (getRHorRecoil(stack) * 10f)) * 0.75D));
			player.yRot = (float) (player.yRot
					- (Util.numInRange(-(getRVertRecoil(stack) * 10f) * 0.75D, (getRVertRecoil(stack) * 10f)) * 0.75D));
			 */
			player.xRot = (float) (player.xRot +
					(Util.numInRange(-getRHorRecoil(stack)*10, getRHorRecoil(stack)*10)) );
			player.yRot = (float) (player.yRot +
					(Util.numInRange(-getRVertRecoil(stack)*10, getRVertRecoil(stack)*10)) );
			handler.onShoot(stack);
		}
	}

	public void modifySpeedModifier(double mod) {
		this.SPEED_MODIFIER_SPRINTING = new AttributeModifier(SPEED_MOD, "Sprinting speed gun mod", mod,
				Operation.ADDITION);
	}

	public void modifySpeedModifier(ItemStack stack) {
		System.out.println("Speed Modifier: " + getSpeedModifier(stack));
		this.SPEED_MODIFIER_SPRINTING = new AttributeModifier(SPEED_MOD, "Sprinting speed gun mod",
				getSpeedModifier(stack) * 0.01f, Operation.ADDITION);
	}

	public void applySpeedModifier(PlayerEntity player, ItemStack stack) {
		if (player.isSprinting()) {
			modifySpeedModifier(stack);
			if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER_SPRINTING)) {
				player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(SPEED_MODIFIER_SPRINTING);
			}
		} else {
			if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER_SPRINTING)) {
				player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_SPRINTING);
			}
		}
	}

	public boolean canModifyBarrelMouth() {
		return true;
	}

	public boolean canModifyBarrel() {
		return true;
	}

	public boolean canModifyBody() {
		return true;
	}

	public boolean canModifyStock() {
		return true;
	}
	
	public boolean hasDefBarrel(ItemStack stack) {
		return ServerUtils.getBarrel(stack).equals(getBarrel().getRegistryName().toString()) || ServerUtils.getBarrel(stack).equals("");
	}

	public boolean hasDefBody(ItemStack stack) {
		return ServerUtils.getBody(stack).equals(getBody().getRegistryName().toString()) || ServerUtils.getBody(stack).equals("");
	}
	
	public boolean hasDefStock(ItemStack stack) {
		return ServerUtils.getStock(stack).equals(getStock().getRegistryName().toString()) || ServerUtils.getStock(stack).equals("");
	}
	
	public boolean hasDefBarrelMouth(ItemStack stack) {
		return ServerUtils.getBarrelMouth(stack).equals(getBarrelMouth().getRegistryName().toString()) || ServerUtils.getBarrelMouth(stack).equals("");
	}
	
	public boolean barrelEqTo(ItemStack stack, RegistryObject<Item> obj) {
		return ServerUtils.getBarrel(stack).equals(obj.get().getRegistryName().toString());
	}
	
	public boolean bodyEqTo(ItemStack stack, RegistryObject<Item> obj) {
		return ServerUtils.getBody(stack).equals(obj.get().getRegistryName().toString());
	}
	
	public boolean stockEqTo(ItemStack stack, RegistryObject<Item> obj) {
		return ServerUtils.getStock(stack).equals(obj.get().getRegistryName().toString());
	}
	
	public boolean barrelMouthEqTo(ItemStack stack, RegistryObject<Item> obj) {
		return ServerUtils.getBarrelMouth(stack).equals(obj.get().getRegistryName().toString());
	}
	
	public float handleZRecoil(float rp, ItemStack stack) {
		return rp * 0.1F * getZRecoil(stack);
	}

	public float handleRHorRecoil(float rp, ItemStack stack) {
		return rp * (getRHorRecoil(stack) * 0.1f );
	}

	public float handleRVertRecoil(float rp, ItemStack stack) {
		return rp * (getRVertRecoil(stack) * 0.1f );
	}
	
	// Abstract working methods

	public abstract void tickServer(ItemStack stack, World world, PlayerEntity player);

	// NBT methods

	public CompoundNBT getNBT(ItemStack stack) {
		return stack.getOrCreateTag();
	}

	public int getNBTBullets(ItemStack stack) {
		return getNBT(stack).getInt(Constants.BULLETS);
	}

	public void setNBTBullets(ItemStack stack, int bullets) {
		getNBT(stack).putInt(Constants.BULLETS, bullets);
	}

	public int getNBTMagType(ItemStack stack) {
		return getNBT(stack).getInt(Constants.MAGTYPE);
	}

	public void setNBTMagType(ItemStack stack, int magtype) {
		getNBT(stack).putInt(Constants.MAGTYPE, magtype);
	}

	public boolean getNBTHasBulletOnRoom(ItemStack stack) {
		return getNBT(stack).getBoolean(Constants.HASBULLETONROOM);
	}

	public void setNBTHasBulletOnRoom(ItemStack stack, boolean hasbulletonroom) {
		getNBT(stack).putBoolean(Constants.HASBULLETONROOM, hasbulletonroom);
	}

	public boolean getNBTHasMag(ItemStack stack) {
		return getNBT(stack).getBoolean(Constants.HASMAG);
	}

	public void getNBTHasMag(ItemStack stack, boolean hasmag) {
		getNBT(stack).putBoolean(Constants.HASMAG, hasmag);
	}

	public String getNBTStock(ItemStack stack) {
		return getNBT(stack).getString(Constants.STOCK);
	}

	public void setNBTStock(ItemStack stack, String stock) {
		getNBT(stack).putString(Constants.STOCK, stock);
	}

	public String getNBTBarrel(ItemStack stack) {
		return getNBT(stack).getString(Constants.BARREL);
	}

	public void setNBTBarrel(ItemStack stack, String barrel) {
		getNBT(stack).putString(Constants.BARREL, barrel);
	}

	public String getNBTBarrelMouth(ItemStack stack) {
		return getNBT(stack).getString(Constants.BARRELMOUTH);
	}

	public void setNBTBarrelMouth(ItemStack stack, String barrel) {
		getNBT(stack).putString(Constants.BARRELMOUTH, barrel);
	}

	public String getNBTBody(ItemStack stack) {
		return getNBT(stack).getString(Constants.BODY);
	}

	public void setNBTBody(ItemStack stack, String body) {
		getNBT(stack).putString(Constants.BODY, body);
	}
	
	public int getNBTScopePercent(ItemStack stack) {
		return getNBT(stack).getInt(Constants.SCOPE);
	}

	public void setScopePercent(ItemStack stack, int scope) {
		getNBT(stack).putInt(Constants.SCOPE, scope);
	}

	// abstract gun methods
	
	//Gun Properties
	
	public abstract ShootMode getShootMode(ItemStack stack);
	
	public abstract double getGunDamage(ItemStack stack);
	
	public abstract double getPower(ItemStack stack);
	
	public abstract float getInaccuracy(ItemStack stack);
	
	public abstract float getRHorRecoil(ItemStack stack);
	
	public abstract float getRVertRecoil(ItemStack stack);
	
	public abstract float getZRecoil(ItemStack stack);
	
	public abstract float getBurstTargetTime(ItemStack stack);
	
	public abstract int getRecoilTime(ItemStack stack);
	
	public abstract int getShootTime(ItemStack stack);
	
	public abstract int getRange(ItemStack stack);
	
	public abstract int getMaxAmmo(ItemStack stack);
	
	public abstract int getShotgunBullets(ItemStack stack);
	
	public abstract int getBurstShots(ItemStack stack);
	
	public abstract float getSpeedModifier(ItemStack stack);
	
	public abstract float getRangeDamageReduction(ItemStack stack);
	
	public abstract boolean isShotgun(ItemStack stack);
	
	public abstract boolean requiresMag(ItemStack stack);
	
	public abstract String getAcceptedBulletType(ItemStack stack);
	
	public abstract SoundEvent getShootSound(ItemStack stack);
	
	public abstract Item getMagPath(ItemStack stack);
	
	public abstract Item getBulletItem(ItemStack stack);
	
	public abstract BulletProvider<? extends Bullet> getProjectile(ItemStack stack);
	
	/////////////////////////////////////////////////////
	
	public abstract void onHit(ItemStack stack, LivingEntity target);

	public abstract float getBaseSpeedModifier();
	
	public abstract boolean isBoth();

	public abstract boolean hasScope();

	public abstract boolean shouldRenderCrosshair();

	public abstract boolean canChangeParts();

	public abstract String getGunId();

	public abstract Item getBarrel();

	public abstract Item getBody();

	public abstract Item getStock();

	public abstract Item getBarrelMouth();

	public abstract ResourceLocation getScopeResLoc();

	public abstract ResourceLocation getNullPartsResLoc();

	public enum ShootMode {
		SEMI, AUTO, BURST, NONE;
		
		private ShootMode() {
			
		}
		
	}
}
