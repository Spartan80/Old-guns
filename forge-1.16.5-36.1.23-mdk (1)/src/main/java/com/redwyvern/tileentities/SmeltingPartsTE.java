package com.redwyvern.tileentities;

import javax.annotation.Nullable;

import com.redwyvern.blocks.SmeltingPartsTableBlock;
import com.redwyvern.container.SmeltingPartsContainer;
import com.redwyvern.registries.EntityRegistries;
import com.redwyvern.tileentities.data.SmeltingPartsData;
import com.redwyvern.tileentities.data.SmeltingPartsSlotHandler;
import com.redwyvern.util.Util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class SmeltingPartsTE extends TileEntity implements INamedContainerProvider, ITickableTileEntity, IInventory {

	SmeltingPartsData data;
	private SmeltingPartsSlotHandler mh;
	private int MAXSTEPTIMER = 80;
	private int MAXCOOLINGSTEPTIME = 800;

	public SmeltingPartsTE() {
		super(EntityRegistries.SPTE.get());
		mh = SmeltingPartsSlotHandler.createForTileEntity(5, this::canPlayerAccessInventory, this::setChanged);
		data = new SmeltingPartsData();

	}

	public SmeltingPartsTE(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		data = new SmeltingPartsData();
	}

	public boolean canPlayerAccessInventory(PlayerEntity player) {
		if (this.level.getBlockEntity(worldPosition) != this)
			return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.distanceToSqr(worldPosition.getX() + X_CENTRE_OFFSET, worldPosition.getY() + Y_CENTRE_OFFSET,
				worldPosition.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		data.readFromNBT(nbt);
		mh.deserializeNBT(nbt.getCompound("inputslot"));
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		data.putIntoNBT(compound);
		compound.put("inputslot", mh.serializeNBT());
		return compound;
	}

	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT updateTagDescribingTileEntityState = getUpdateTag();
		final int METADATA = 42; // arbitrary.
		return new SUpdateTileEntityPacket(this.worldPosition, METADATA, updateTagDescribingTileEntityState);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT updateTagDescribingTileEntityState = pkt.getTag();
		BlockState blockState = level.getBlockState(worldPosition);
		handleUpdateTag(blockState, updateTagDescribingTileEntityState);
		this.load(blockState, updateTagDescribingTileEntityState);
		level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition).getBlock().defaultBlockState(),
				level.getBlockState(worldPosition), 2);

	}

	/*
	 * Creates a tag containing the TileEntity information, used by vanilla to
	 * transmit from server to client Warning - although our getUpdatePacket() uses
	 * this method, vanilla also calls it directly, so don't remove it.
	 */
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT nbtTagCompound = new CompoundNBT();
		save(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(BlockState blockState, CompoundNBT tag) {
		deserializeNBT(blockState, tag);
	}

	public void dropAllContents(World world, BlockPos blockPos) {
		InventoryHelper.dropContents(world, blockPos, mh);

	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return this.level == null ? null
				: new SmeltingPartsContainer(p_createMenu_1_, p_createMenu_2_,
						IWorldPosCallable.create(this.level, this.worldPosition), data, mh);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("oldguns.container.smelting_parts_container");
	}

	public boolean canSmelt() {
		return data.get(4) >= 23 && (Util.isMoldValid(mh.getItem(2)) ? ingotsValid() : false);
	}

	public boolean ingotsValid() {
		ItemStack stack = mh.getItem(1);
		if (!stack.isEmpty()) {
			if (stack.getCount() >= Util.getIngotsFor(mh.getItem(2).getOrCreateTag().getString("res_loc"))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void tick() {
		if (!level.isClientSide) {

			if (canSmelt()) {
				if (data.get(0) < 20) {
					data.set(0, data.get(0) + 1);
				} else {
					if (data.get(1) < 24) {
						data.set(1, data.get(1) + 1);
						data.set(0, 0);
					} else {
						if (!mh.getItem(0).isEmpty()) {
							if (mh.getItem(0).getItem() == ForgeRegistries.ITEMS.getValue(
									new ResourceLocation(mh.getItem(2).getOrCreateTag().getString("res_loc")))) {
								mh.getItem(0).grow(1);
								mh.getItem(1)
										.shrink(Util.getIngotsFor(mh.getItem(2).getOrCreateTag().getString("res_loc")));
								data.set(1, 0);
							}
						} else {
							mh.setItem(0, new ItemStack(ForgeRegistries.ITEMS.getValue(
									new ResourceLocation(mh.getItem(2).getOrCreateTag().getString("res_loc")))));
							mh.getItem(1)
									.shrink(Util.getIngotsFor(mh.getItem(2).getOrCreateTag().getString("res_loc")));
							data.set(1, 0);
						}
					}
				}
			} else {
				if (data.get(0) > 0) {
					if (mh.getItem(1).isEmpty() || mh.getItem(2).isEmpty()) {
						data.set(1, 0);
					}
				}
			}
			if (!mh.getItem(3).isEmpty() && data.get(3) == 0 && data.get(4) < 23) {
				if (mh.getItem(3).getItem().getItem() == Items.LAVA_BUCKET) {
					System.out.println(ForgeHooks.getBurnTime(mh.getItem(3)));
					data.set(3, data.get(3) + ForgeHooks.getBurnTime(mh.getItem(3)));
					mh.setItem(3, new ItemStack(Items.BUCKET));
				} else {
					System.out.println(ForgeHooks.getBurnTime(mh.getItem(3)));
					data.set(3, data.get(3) + ForgeHooks.getBurnTime(mh.getItem(3)));
					mh.getItem(3).shrink(1);
				}

			}
			data.set(2, data.get(5));
			if (data.get(3) > 0) {
				data.set(3, data.get(3) - 1);
				if (data.get(5) <= MAXSTEPTIMER) {
					data.set(5, data.get(5) + 1);
				}
			}
			if (data.get(5) >= MAXSTEPTIMER) {
				if (data.get(4) < 23) {
					data.set(4, data.get(4) + 1);
					data.set(5, 0);
				}
			}

			if (data.get(2) == data.get(5)) {
				if (data.get(6) < MAXCOOLINGSTEPTIME) {
					data.set(6, data.get(6) + 1);
				} else {
					if (data.get(4) > 0) {
						data.set(4, data.get(4) - 1);
						data.set(6, 0);
					}
				}
			}
			if (data.get(4) > 8) {
				level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition)
						.setValue(SmeltingPartsTableBlock.LIT, Boolean.valueOf(true)), 3);
			} else {
				level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition)
						.setValue(SmeltingPartsTableBlock.LIT, Boolean.valueOf(false)), 3);
			}
		}
	}

	@Override
	public void clearContent() {
		mh.clearContent();

	}

	@Override
	public int getContainerSize() {
		return mh.getContainerSize();
	}

	@Override
	public boolean isEmpty() {
		return mh.isEmpty();
	}

	@Override
	public ItemStack getItem(int p_70301_1_) {
		return mh.getItem(p_70301_1_);
	}

	@Override
	public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
		return mh.removeItem(p_70298_1_, p_70298_2_);
	}

	@Override
	public ItemStack removeItemNoUpdate(int p_70304_1_) {
		return mh.removeItemNoUpdate(p_70304_1_);
	}

	@Override
	public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
		mh.setItem(p_70299_1_, p_70299_2_);

	}

	@Override
	public boolean stillValid(PlayerEntity p_70300_1_) {
		return mh.stillValid(p_70300_1_);
	}

}
