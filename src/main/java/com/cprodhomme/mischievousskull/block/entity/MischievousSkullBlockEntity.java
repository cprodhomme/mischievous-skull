package com.cprodhomme.mischievousskull.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.util.math.BlockPos;

public class MischievousSkullBlockEntity extends SkullBlockEntity {

  public MischievousSkullBlockEntity(BlockPos pos, BlockState state) {
    // BlockEntityType.SKULL
    super(pos, state);
  }
  
}
