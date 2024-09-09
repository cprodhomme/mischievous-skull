package com.cprodhomme.mischievousskull.block;

import com.cprodhomme.mischievousskull.Mischievousskull;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
  public static final Block MISCHIEVOUS_SKULL = register(
    MischievousSkullBlock.MISCHIEVOUS_SKULL_BLOCK,
    MischievousSkullBlock.IDENTIFIER,
    true
  );

  private static Block register(Block block, String name, boolean shouldRegisterItem) {
		// Register the block and its item.
		Identifier id = Identifier.tryParse(Mischievousskull.MOD_ID, name);

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			BlockItem blockItem = new BlockItem(block, new Item.Settings());
			Registry.register(Registries.ITEM, id, blockItem);
		}

		return Registry.register(Registries.BLOCK, id, block);
	}

  public static void initialize() {
  }
}
