package com.cprodhomme.mischievousskull.item;

import com.cprodhomme.mischievousskull.block.ModBlocks;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class ModItems {
  public static void initialize() {  
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(itemGroup -> {
      itemGroup.add(ModBlocks.MISCHIEVOUS_SKULL.asItem());
    });
  }
}
