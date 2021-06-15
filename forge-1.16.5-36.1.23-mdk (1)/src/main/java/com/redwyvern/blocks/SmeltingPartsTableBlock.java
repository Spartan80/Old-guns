package com.redwyvern.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.redwyvern.tileentities.SmeltingPartsTE;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SmeltingPartsTableBlock extends Block {

	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public SmeltingPartsTableBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.getStateDefinition().any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
						.setValue(LIT, Boolean.valueOf(false)));
	}

	@Override
	public VoxelShape getOcclusionShape(BlockState p_196247_1_, IBlockReader p_196247_2_, BlockPos p_196247_3_) {
		return VoxelShapes.empty();
	}

	public VoxelShape getVisualShape(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_,
			ISelectionContext p_230322_4_) {
		return VoxelShapes.empty();
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.getValue(LIT) ? 12 : 0;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection())
				.setValue(LIT, Boolean.valueOf(false));
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
		if (p_180655_1_.getValue(LIT)) {
			Direction direction = p_180655_1_.getValue(BlockStateProperties.HORIZONTAL_FACING);
			double d0 = (double) p_180655_3_.getX();
			double d1 = (double) p_180655_3_.getY() + 0.5D;
			double d2 = (double) p_180655_3_.getZ();

			switch (direction) {
			case EAST:
				d0 += -0.212f;
				d2 += 0.488f;
				break;
			case WEST:
				d0 += 1.276f;
				d2 += 0.524f;
				break;
			case NORTH:
				d0 += 0.452f;
				d2 += 1.272f;
				break;
			case SOUTH:
				d0 += 0.468f;
				d2 += -0.24f;
				break;
			}

			if (p_180655_4_.nextDouble() < 0.1D) {
				p_180655_2_.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F,
						1.0F, false);
			}

			Direction.Axis direction$axis = direction.getAxis();
			double d3 = 0.52D;
			double d4 = p_180655_4_.nextDouble() * 0.6D - 0.3D;
			double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
			double d6 = p_180655_4_.nextDouble() * 6.0D / 16.0D;
			double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
			p_180655_2_.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
			p_180655_2_.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
		builder.add(LIT);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		if (!worldIn.isClientSide) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof SmeltingPartsTE)
				NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity,
						(packetBuffer) -> {
						});
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		// TODO Auto-generated method stub
		return new SmeltingPartsTE();
	}

	public void onRemove(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_,
			boolean p_196243_5_) {
		if (!p_196243_1_.is(p_196243_4_.getBlock())) {
			TileEntity tileentity = p_196243_2_.getBlockEntity(p_196243_3_);
			if (tileentity instanceof SmeltingPartsTE) {
				InventoryHelper.dropContents(p_196243_2_, p_196243_3_, (SmeltingPartsTE) tileentity);
				p_196243_2_.updateNeighbourForOutputSignal(p_196243_3_, this);
			}

			super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
		}
	}
}
