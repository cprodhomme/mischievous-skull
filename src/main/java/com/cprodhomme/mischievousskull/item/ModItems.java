package com.cprodhomme.mischievousskull.item;

import com.cprodhomme.mischievousskull.Mischievousskull;
import com.cprodhomme.mischievousskull.block.ModBlocks;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

public class ModItems {
  public static final Item MISCHIEVOUS_SKULL = registerItem("mischievous_skull",
          new VerticallyAttachableBlockItem(ModBlocks.MISCHIEVOUS_SKULL, ModBlocks.MISCHIEVOUS_WALL_SKULL, new Item.Settings().rarity(Rarity.UNCOMMON), Direction.DOWN));

  private static Item registerItem(String name, Item item) {
    return Registry.register(Registries.ITEM, new Identifier(Mischievousskull.MOD_ID, name), item);
  }

  public static void initialize() {  
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(itemGroup -> {
      itemGroup.add(ModItems.MISCHIEVOUS_SKULL);
    });
  }
}
