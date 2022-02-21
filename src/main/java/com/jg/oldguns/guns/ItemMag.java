package com.jg.oldguns.guns;

import com.jg.oldguns.containers.MagContainer;
import com.jg.oldguns.entities.Bullet;
import com.jg.oldguns.utils.Constants;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemMag extends Item {

	int magtype;
	int maxAmmo;
	int metal;
	
	boolean isIron;
	
	String gunId;
	String acceptedSize;
	
	IFactory proj;

	public interface IFactory {
		Bullet create(LivingEntity entity, World p_create_2_);
	}

	public ItemMag(Item.Properties prop, String gunid, int magtype, int maxammo, String acceptedSize, int metal, boolean isIron, IFactory test) {
		super(prop);
		this.gunId = gunid;
		this.magtype = magtype;
		this.maxAmmo = maxammo;
		this.acceptedSize = acceptedSize;
		this.metal = metal;
		this.isIron = isIron;
		this.proj = test;
	}

	// Item methods

	@Override
	public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
		if (!p_77659_1_.isClientSide) {
			NetworkHooks.openGui((ServerPlayerEntity) p_77659_2_, new SimpleNamedContainerProvider(
					(id, pi, cplayer) -> new MagContainer(id, pi), new TranslationTextComponent("gui.oldguns.mag_gui")));
		}
		return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
	}

	// NBT Methods

	public CompoundNBT getNBT(ItemStack stack) {
		return stack.getOrCreateTag();
	}

	public int getNBTBullets(ItemStack stack) {
		return getNBT(stack).getInt(Constants.BULLETS);
	}

	public void setNBTBullets(ItemStack stack, int bullets) {
		getNBT(stack).putInt(Constants.BULLETS, bullets);
	}

	// End

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public String getGunId() {
		return gunId;
	}

	public int getMagType() {
		return magtype;
	};

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

	public int getMagtype() {
		return magtype;
	}

	public IFactory getProj() {
		return proj;
	}

	public String getAcceptedSize() {
		return acceptedSize;
	};

	public Bullet getProjectile(PlayerEntity player, World world) {
		System.out.println(proj.create(player, world) == null);
		return proj.create(player, world);
	}

}
