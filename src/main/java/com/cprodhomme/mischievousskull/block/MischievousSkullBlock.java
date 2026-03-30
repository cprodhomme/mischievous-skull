package com.cprodhomme.mischievousskull.block;

import com.cprodhomme.mischievousskull.Mischievousskull;
import com.cprodhomme.mischievousskull.SkullEffects;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
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
                  .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Mischievousskull.MOD_ID, IDENTIFIER)))
                  .instrument(NoteBlockInstrument.SKELETON)
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
    for (SkullEffects.Entry entry : SkullEffects.shuffledEntries()) {
      RegistryEntry<StatusEffect> effect = entry.effect();
      if (!player.hasStatusEffect(effect)) {
        int amplifier = calculLevelEffect(player, effect);
        StatusEffectInstance effectInstance = new StatusEffectInstance(effect, entry.instanceDuration(), amplifier);
        player.addStatusEffect(effectInstance);
        return;
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

  @Override
  public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    super.randomDisplayTick(state, world, pos, random);

    if (world.isClient()) {
      // Génération aléatoire des coordonnées autour du bloc
      double x = (double)pos.getX() + random.nextDouble();
      double y = (double)pos.getY() + random.nextDouble();
      double z = (double)pos.getZ() + random.nextDouble();

      // Ajout d'une particule (par exemple des particules de flamme)
      world.addParticleClient(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
    }
  }
}
