package com.cprodhomme.mischievousskull.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CustomSkullBlock extends Block {
  public static final int MAX_ROTATION_INDEX = RotationPropertyHelper.getMax();
	private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
	public static final IntProperty ROTATION = Properties.ROTATION;

  private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);

  public CustomSkullBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState().with(ROTATION, Integer.valueOf(0)));
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  @Override
  protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
    return VoxelShapes.empty();
  }

  @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(ROTATION, Integer.valueOf(RotationPropertyHelper.fromYaw(ctx.getPlayerYaw())));
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(ROTATION, Integer.valueOf(rotation.rotate((Integer)state.get(ROTATION), MAX_ROTATIONS)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.with(ROTATION, Integer.valueOf(mirror.mirror((Integer)state.get(ROTATION), MAX_ROTATIONS)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ROTATION);
	}
}
