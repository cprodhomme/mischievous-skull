package com.cprodhomme.mischievousskull.block;

import com.cprodhomme.mischievousskull.Mischievousskull;
import com.cprodhomme.mischievousskull.block.entity.MischievousSkullBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
  public static final Block MISCHIEVOUS_SKULL = registerBlock(
    MischievousSkullBlock.IDENTIFIER,
    MischievousSkullBlock.MISCHIEVOUS_SKULL_BLOCK
  );
  public static final Block MISCHIEVOUS_WALL_SKULL = registerBlock(
    MischievousWallSkullBlock.IDENTIFIER,
    MischievousWallSkullBlock.MISCHIEVOUS_SKULL_BLOCK
  );

  public static final BlockEntityType<MischievousSkullBlockEntity> MISCHIEVOUS_SKULL_BLOCK_ENTITY = Registry.register(
    Registries.BLOCK_ENTITY_TYPE,
    new Identifier(Mischievousskull.MOD_ID, "mischievous_skull_block_entity"),
    BlockEntityType.Builder.create(MischievousSkullBlockEntity::new, MischievousSkullBlock.MISCHIEVOUS_SKULL_BLOCK).build()
  );

  private static Block registerBlock(String name, Block block) {
    return Registry.register(
      Registries.BLOCK,
      new Identifier(Mischievousskull.MOD_ID, name),
      block
    );
  }

  @SuppressWarnings("unused")
  private static Item registerBlockItem(String name, Block block) {
    return Registry.register(
      Registries.ITEM,
      new Identifier(Mischievousskull.MOD_ID, name),
      new BlockItem(block, new Item.Settings())
    );
  }

  public static void initialize() {
    Mischievousskull.LOGGER.debug("Registrering ModBlocks for " + Mischievousskull.MOD_ID);
  }
}
