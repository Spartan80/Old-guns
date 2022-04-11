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
import com.jg.oldguns.config.Config;
import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.network.HandleShootMessage;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.ServerUtils;
import com.jg.oldguns.utils.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.registries.RegistryObject;

public abstract class ItemGun extends Item {

	protected final Multimap<Attribute, AttributeModifier> attributes;

	protected final UUID SPEED_MOD = UUID.randomUUID();

	protected AttributeModifier SPEED_MODIFIER_SPRINTING;

	protected RegistryObject<Item> mag;
	
	protected final float TOOHEAVY = 7.0f;
	protected final float HEAVY = 5.0f;
	protected final float MEDIUM = 3.5f;
	protected final float LIGHT = 2.0f;
	Random random;
	
	public ItemGun(Item.Properties prop, RegistryObject<Item> mag) {
		super(prop);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
				(double) 20, AttributeModifier.Operation.ADDITION));
		this.attributes = builder.build();
		SPEED_MODIFIER_SPRINTING = new AttributeModifier(SPEED_MOD, "Sprinting speed gun mod", -getBaseSpeedModifier(),
				AttributeModifier.Operation.ADDITION);
		this.mag = mag;
		this.random = new Random();
	}

	// item methods
	
	@Override
	public void appendHoverText(ItemStack stack, Level p_77624_2_, List<Component> tooltip,
			TooltipFlag p_77624_4_) {
		if(Minecraft.getInstance().player != null) {
			tooltip.add(new TranslatableComponent("Damage: " + getGunDamage(stack)));
			tooltip.add(new TranslatableComponent("Power: " + getPower(stack)));
			tooltip.add(new TranslatableComponent("Innacuracy: " + getInaccuracy(stack)));
			tooltip.add(new TranslatableComponent("Has scope: " + ServerUtils.hasScope(stack)));
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot,
			ItemStack stack) {
		return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributes
				: super.getAttributeModifiers(equipmentSlot, stack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return oldStack.getItem() != newStack.getItem();
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack p_77661_1_) {
		return UseAnim.NONE;
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		return true;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level p_77663_2_, Entity p_77663_3_, int p_77663_4_,
			boolean p_77663_5_) {
		if (!p_77663_2_.isClientSide) {
			Player player = (Player) p_77663_3_;
			if(player.getMainHandItem().getItem() instanceof ItemGun) {
				tickServer(stack, p_77663_2_, player);
			}else {
				if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER_SPRINTING)) {
					player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_SPRINTING);
				}
			}
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
		Player player = ClientEventHandler.getPlayer();
		int bullets = getNBTBullets(stack);
		
		if(getShootMode(stack) != ShootMode.BURST) {
			if (bullets > 0 && bullets <= getMaxAmmo(stack) && !player.isSprinting() && handler.isAnimationEmpty()
					&& handler.getGunModel().canShoot(stack)
					|| (player.isCreative() && player.getCooldowns().getCooldownPercent(this, Util.getFrameTime()) == 0
							&& handler.getGunModel().canShoot(stack)) && handler.getGunModel().canShoot(stack)) {
				handler.pickRandomRecoil();
				shoot(player, stack, handler, bullets);
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

	public void shoot(Player player, ItemStack stack, ClientHandler handler, int bullets) {
		
		if(getShootMode(stack) == ShootMode.AUTO || getShootMode(stack) == ShootMode.SEMI) {
			handler.getRecoilHandler().addCooldown(stack.getItem(), getRecoilTime(stack));
			OldGuns.channel.sendToServer(new HandleShootMessage(player.getYRot(), player.getXRot()));
			/*player.setXRot((float) (player.getXRot() +
					(Util.numInRange(-getRHorRecoil(stack)*10, getRHorRecoil(stack)*10)) ));
			player.setYRot((float) (player.getYRot() +
					(Util.numInRange(-getRVertRecoil(stack)*10, getRVertRecoil(stack)*10)) ));
			*/
			Random rnd = new Random();
			player.setXRot(player.getViewXRot(0)+Mth.randomBetween(rnd, -0.5f*(getRVertRecoil(stack)*20), 0.5f*(getRVertRecoil(stack)*20)));
			player.setYRot(player.getViewYRot(0)+Mth.randomBetween(rnd, -0.5f*(getRHorRecoil(stack)*20), 0.5f*(getRHorRecoil(stack)*20)));
			handler.getGunModel().setCanRenderMuzzleFlash(true);
			handler.onShoot(stack);
		}
	}

	public void modifySpeedModifier(ItemStack stack) {
		float weightt = 0.0f;
		if(getNBTHasMag(stack)) {
			ItemMag mag = (ItemMag)Registry.ITEM.get(new ResourceLocation(getNBTMagPath(stack)));
			weightt = mag.getWeight();
		}
		System.out.println(((GunPart)getStock()).getWeight());
		this.SPEED_MODIFIER_SPRINTING = new AttributeModifier(SPEED_MOD, "Sprinting speed gun mod",
				-(getWeight(stack) + weightt) * Config.SERVER.speedMul.get(), Operation.ADDITION);
	}

	public void applySpeedModifier(Player player, ItemStack stack) {
		if (player.isSprinting()) {
			if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER_SPRINTING)) {
				modifySpeedModifier(stack);
				player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(SPEED_MODIFIER_SPRINTING);
			}
		} else {
			if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER_SPRINTING)) {
				player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_SPRINTING);
				System.out.println("Removing");
			}
		}
	}
	
	public void tickServer(ItemStack stack, Level Level, Player player) {
		applySpeedModifier(player, stack);
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
	
	//Getters
	
	public Random getRandom() {
		return random;
	}
	
	public Item getMagPath(ItemStack stack) {
		String mpath = getNBT(stack).getString(Constants.MAGPATH);
		if(!mpath.equals(Constants.EMPTY)) {
			return Registry.ITEM.get(new ResourceLocation(mpath));
		}else {
			return null;
		}
	}

	public int getMaxAmmo(ItemStack stack) {
		return stack.getOrCreateTag().getInt(Constants.MAXAMMO);
	}
	
	private float getWeight(ItemStack stack) {
		float sw = 0.0f;
		float bow = 0.0f;
		float baw = 0.0f;
		
		if(ServerUtils.getStock(stack).equals("")) {
			sw = ((GunPart)getStock()).getWeight();
			//System.out.println("asdasd");
		}else {
			sw = getNBT(stack).getFloat(Constants.STOCKW);
		}
		if(ServerUtils.getBody(stack).equals("")) {
			bow = ((GunPart)getBody()).getWeight();
		}else {
			bow = getNBT(stack).getFloat(Constants.BODYW);
		}
		if(ServerUtils.getBarrel(stack).equals("")) {
			baw = ((GunPart)getBarrel()).getWeight();
		}else {
			baw = getNBT(stack).getFloat(Constants.BARRELW);
		}
		return baw + bow + sw;
	}
	
	// NBT methods

	public CompoundTag getNBT(ItemStack stack) {
		return stack.getOrCreateTag();
	}
	
	public String getNBTMagPath(ItemStack stack) {
		return getNBT(stack).getString(Constants.MAGPATH);
	}
	
	public void setNBTMagPath(ItemStack stack, String magpath) {
		getNBT(stack).putString(Constants.MAGPATH, magpath);
	}

	public int getNBTBullets(ItemStack stack) {
		return getNBT(stack).getInt(Constants.BULLETS);
	}

	public void setNBTBullets(ItemStack stack, int bullets) {
		getNBT(stack).putInt(Constants.BULLETS, bullets);
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

	public void setNBTScopePercent(ItemStack stack, int scope) {
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
	
	public abstract float getRangeDamageReduction(ItemStack stack);
	
	public abstract int getRecoilTime(ItemStack stack);
	
	public abstract int getShootTime(ItemStack stack);
	
	public abstract int getRange(ItemStack stack);
	
	public abstract int getShotgunBullets(ItemStack stack);
	
	public abstract int getBurstShots(ItemStack stack);
	
	public abstract boolean isShotgun(ItemStack stack);
	
	public abstract boolean requiresMag(ItemStack stack);
	
	public abstract String getAcceptedBulletType(ItemStack stack);
	
	public abstract SoundEvent getShootSound(ItemStack stack);
	
	public abstract Item getBulletItem(ItemStack stack);
	
	public abstract String getProjectile(ItemStack stack);
	
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
