package com.cprodhomme.mischievousskull.block;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import com.cprodhomme.mischievousskull.Mischievousskull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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

public class MischievousSkullBlock extends Block {
  public static final DirectionProperty FACING = DirectionProperty.of("facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

  private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);

  public static final String IDENTIFIER = "mischievous_skull_block";
  public static final MischievousSkullBlock MISCHIEVOUS_SKULL_BLOCK = new MischievousSkullBlock(
    Block.Settings.create()
                  .instrument(Instrument.SKELETON)
                  .pistonBehavior(PistonBehavior.DESTROY)
                  .strength(5.0f)
                  .sounds(BlockSoundGroup.AMETHYST_BLOCK)
  );


  public MischievousSkullBlock(Settings settings) {
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
    // Obtenir la direction du joueur et la mettre à l'opposé pour que la face front soit orientée vers le joueur

    Direction playerFacing = ctx.getHorizontalPlayerFacing().getOpposite();
    Mischievousskull.LOGGER.info("playerFacing: "+ playerFacing);
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

  // Méthode appelée lorsqu'un bloc est cassé
  @Override
  public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
    if (player.isCreative() == false) {
      world.playSound(player, pos, SoundEvents.ENTITY_WITHER_DEATH, SoundCategory.BLOCKS, 0.5f, 0.5f);
  
      // Appliquer un effet aléatoire au joueur
      applyRandomEffect(player);
    }
  }

  // Méthode pour appliquer un effet aléatoire au joueur
  private void applyRandomEffect(PlayerEntity player) {
    int randomEffectIndex = getRandomIntegerEffect();

    // Créer une nouvelle instance d'effet aléatoire
    RegistryEntry<StatusEffect> randomEffect = getRandomEffect(randomEffectIndex);

    // Appliquer l'effet au joueur
    Integer amplifier = calculLevelEffect(player, randomEffect);

    Integer infiniteDuration = -1;
    StatusEffectInstance statusEffectInstance = new StatusEffectInstance(randomEffect, infiniteDuration, amplifier);
    player.addStatusEffect(statusEffectInstance);
  }

  private Integer getRandomIntegerEffect() {
    Random random = new Random();

    return random.nextInt(9) + 1;
  }

  // assigne effet niveau 1 (amplifier 0) par défaut
  // assigne effet niveau 2 (amplifier 1) si le joueur possède déjà l'effet
  private Integer calculLevelEffect(PlayerEntity player, RegistryEntry<StatusEffect> effect) {
    Integer level = 0;
    Boolean hasStatus = player.hasStatusEffect(effect);
    if (hasStatus) {
      level += 1;
    }
    return level;
  }

  // Méthode pour obtenir un effet aléatoire en fonction de l'index
  private RegistryEntry<StatusEffect> getRandomEffect(int index) {
    switch (index) {
      case 1:
        return StatusEffects.STRENGTH;
      case 2:
        return StatusEffects.HASTE;
      case 3:
        return StatusEffects.JUMP_BOOST;
      case 4:
        return StatusEffects.SPEED;
      case 5:
        return StatusEffects.NIGHT_VISION;
      case 6:
        return StatusEffects.SATURATION;
      case 7:
        return StatusEffects.CONDUIT_POWER;
      case 8:
        return StatusEffects.OOZING;
      case 9:
        return StatusEffects.FIRE_RESISTANCE;
      default:
        return StatusEffects.STRENGTH;
    }
  }
}
