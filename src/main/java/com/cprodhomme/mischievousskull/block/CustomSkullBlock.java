package com.cprodhomme.mischievousskull.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CustomSkullBlock extends Block {
  public static final DirectionProperty FACING = DirectionProperty.of("facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

  private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);

  public CustomSkullBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }
  
  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    Direction playerFacing = ctx.getHorizontalPlayerFacing().getOpposite();
    return this.getDefaultState().with(FACING, playerFacing);
  }

  @Override
  protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
    return VoxelShapes.empty();
  }

  protected BlockState rotate(BlockState state, BlockRotation rotation) {
    return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
  }

  protected BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
  }

  @Override
  public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
    Direction facing = placer.getHorizontalFacing().rotateYClockwise();  // rotation à 45 degrés pour simuler les diagonales
    world.setBlockState(pos, state.with(FACING, facing), 2);
  }
}
