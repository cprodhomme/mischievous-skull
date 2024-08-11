package com.cprodhomme.mischievousskull.block;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import com.cprodhomme.mischievousskull.block.entity.MischievousSkullBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MischievousSkullBlock extends SkullBlock {
  public static final String IDENTIFIER = "mischievous_skull_block";
  public static final MischievousSkullBlock MISCHIEVOUS_SKULL_BLOCK = new MischievousSkullBlock(
    Type.SKELETON,
    Block.Settings.create()
                  .instrument(Instrument.SKELETON)
                  .pistonBehavior(PistonBehavior.DESTROY)
                  .strength(5.0f)
                  .sounds(BlockSoundGroup.AMETHYST_BLOCK)
  );

  public MischievousSkullBlock(SkullType skullType, Settings settings) {
    super(skullType, settings);
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

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new MischievousSkullBlockEntity(pos, state);
  }

  // Méthode pour appliquer un effet aléatoire au joueur
  private void applyRandomEffect(PlayerEntity player) {
    int randomEffectIndex = getRandomIntegerEffect();

    // Créer une nouvelle instance d'effet aléatoire
    RegistryEntry<StatusEffect> randomEffect = getRandomEffect(randomEffectIndex);

    // Appliquer l'effet au joueur
    Integer amplifier = calculLevelEffect(player, randomEffect);

    StatusEffectInstance statusEffectInstance = new StatusEffectInstance(randomEffect, Integer.MAX_VALUE, amplifier);
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
