package com.cprodhomme.mischievousskull;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cprodhomme.mischievousskull.block.ModBlocks;
import com.cprodhomme.mischievousskull.item.ModItems;

public class Mischievousskull implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "mischievous-skull";
  public static final Logger LOGGER = LoggerFactory.getLogger("mischievous-skull");

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModItems.initialize();
	}
}
