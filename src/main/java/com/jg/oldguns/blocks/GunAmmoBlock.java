package com.jg.oldguns.blocks;

import javax.annotation.Nullable;

import com.jg.oldguns.containers.AmmoCraftingTableContainer;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class GunAmmoBlock extends Block {
	
	private static final Component CONTAINER_TITLE = new TranslatableComponent("com.jg.container.gun_ammo_table");
	
	public GunAmmoBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		this.registerDefaultState(
				this.getStateDefinition().any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
	}
	
	public VoxelShape getOcclusionShape(BlockState p_196247_1_, BlockGetter p_196247_2_, BlockPos p_196247_3_) {
		return Shapes.empty();
	}
	
	public VoxelShape getVisualShape(BlockState p_230322_1_, BlockGetter p_230322_2_, BlockPos p_230322_3_,
			CollisionContext p_230322_4_) {
		return Shapes.empty();
	}

	public boolean propagatesSkylightDown(BlockState p_200123_1_, BlockGetter p_200123_2_, BlockPos p_200123_3_) {
		return true;
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.HORIZONTAL_FACING);

	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
			BlockHitResult hit) {
		if (!worldIn.isClientSide) {
			NetworkHooks.openGui((ServerPlayer) player, this.getMenuProvider(state, worldIn, pos));
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState p_60563_, Level p_60564_, BlockPos p_60565_) {
		return new SimpleMenuProvider((p_52229_, p_52230_, p_52231_) -> {
	         return new AmmoCraftingTableContainer(p_52229_, p_52230_);
	      }, CONTAINER_TITLE);
	}

}
