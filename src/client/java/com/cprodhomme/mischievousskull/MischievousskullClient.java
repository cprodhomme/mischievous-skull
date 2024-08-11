package com.cprodhomme.mischievousskull;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class MischievousskullClient implements ClientModInitializer {

	public static final EntityModelLayer MISCHIEVOUS_SKULL = new EntityModelLayer(new Identifier(Mischievousskull.MOD_ID, "mischievous_skull"), "main");

	@Override
	public void onInitializeClient() {
		
	}
}
