package com.cprodhomme.mischievousskull.block;

import net.minecraft.block.Block;
import net.minecraft.block.SkullBlock.SkullType;
import net.minecraft.block.SkullBlock.Type;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.piston.PistonBehavior;

public class MischievousWallSkullBlock extends WallSkullBlock {
  public static final String IDENTIFIER = "mischievous_wall_skull_block";
  public static final MischievousWallSkullBlock MISCHIEVOUS_SKULL_BLOCK = new MischievousWallSkullBlock(
    Type.SKELETON,
    Block.Settings.create()
                  .strength(5.0f)
                  .dropsLike(ModBlocks.MISCHIEVOUS_SKULL)
                  .pistonBehavior(PistonBehavior.DESTROY)
  );

  public MischievousWallSkullBlock(SkullType skullType, Settings settings) {
    super(skullType, settings);
  }
  
}
