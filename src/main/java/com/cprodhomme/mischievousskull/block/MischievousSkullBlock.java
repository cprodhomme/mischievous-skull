package com.cprodhomme.mischievousskull.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MischievousSkullBlock extends CustomSkullBlock {
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
    // Création de la liste des effets
    List<RegistryEntry<StatusEffect>> effects = getRandomEffects();

    // Boucler sur la liste randomisée pour trouver un effet que le joueur n'a pas
    for (RegistryEntry<StatusEffect> effect : effects) {
      if (!player.hasStatusEffect(effect)) {
        // Calculer le niveau de l'effet (0 ou 1)
        int amplifier = calculLevelEffect(player, effect);

        // Appliquer l'effet avec une durée infinie
        StatusEffectInstance effectInstance = new StatusEffectInstance(effect, StatusEffectInstance.INFINITE, amplifier);
        player.addStatusEffect(effectInstance);
        return; // Sortir de la méthode une fois que l'effet est appliqué
      }
    }
  }

  // assigne effet niveau 1 (amplifier 0) par défaut
  // assigne effet niveau 2 (amplifier 1) si le joueur possède déjà l'effet
  private Integer calculLevelEffect(PlayerEntity player, RegistryEntry<StatusEffect> effect) {
    Integer level = 0;
    Boolean hasStatus = player.hasStatusEffect(effect);
    if (hasStatus) {
      level = 1;
    }
    return level;
  }

  // Méthode pour obtenir un effet aléatoire en fonction de l'index
  private List<RegistryEntry<StatusEffect>> getRandomEffects() {
    // Création de la liste des effets
    List<RegistryEntry<StatusEffect>> effects = new ArrayList<>();
    effects.add(StatusEffects.STRENGTH);
    effects.add(StatusEffects.HASTE);
    effects.add(StatusEffects.JUMP_BOOST);
    effects.add(StatusEffects.SPEED);
    effects.add(StatusEffects.NIGHT_VISION);
    effects.add(StatusEffects.SATURATION);
    effects.add(StatusEffects.CONDUIT_POWER);
    effects.add(StatusEffects.OOZING);
    effects.add(StatusEffects.FIRE_RESISTANCE);

    // Mélanger la liste pour randomiser l'ordre
    Collections.shuffle(effects);

    return effects;
  }

  @Override
  public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    super.randomDisplayTick(state, world, pos, random);

    if (world.isClient()) {
      // Génération aléatoire des coordonnées autour du bloc
      double x = (double)pos.getX() + random.nextDouble();
      double y = (double)pos.getY() + random.nextDouble();
      double z = (double)pos.getZ() + random.nextDouble();

      // Ajout d'une particule (par exemple des particules de flamme)
      world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
    }
  }
}
