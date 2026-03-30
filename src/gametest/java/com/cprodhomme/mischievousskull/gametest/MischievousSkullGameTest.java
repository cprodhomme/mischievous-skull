package com.cprodhomme.mischievousskull.gametest;

import java.util.List;

import com.cprodhomme.mischievousskull.block.ModBlocks;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;

public class MischievousSkullGameTest {
	private static final List<RegistryEntry<StatusEffect>> EXPECTED_EFFECTS = List.of(
		StatusEffects.STRENGTH,
		StatusEffects.HASTE,
		StatusEffects.JUMP_BOOST,
		StatusEffects.SPEED,
		StatusEffects.NIGHT_VISION,
		StatusEffects.SATURATION,
		StatusEffects.CONDUIT_POWER,
		StatusEffects.OOZING,
		StatusEffects.FIRE_RESISTANCE
	);

	@GameTest(structure = "fabric-gametest-api-v1:empty")
	public void breakingSkullInSurvivalAppliesRandomGoodEffect(TestContext context) {
		BlockPos pos = new BlockPos(2, 1, 2);
		context.setBlockState(pos, ModBlocks.MISCHIEVOUS_SKULL);

		PlayerEntity player = context.createMockPlayer(GameMode.SURVIVAL);
		context.assertFalse(player.isCreative(), "Le joueur doit être en survie (pas créatif) pour que l’effet s’applique");

		BlockPos absolutePos = context.getAbsolutePos(pos);
		BlockState state = context.getBlockState(pos);
		BlockEntity blockEntity = context.getWorld().getBlockEntity(absolutePos);
		state.getBlock().afterBreak(context.getWorld(), player, absolutePos, state, blockEntity, new ItemStack(Items.NETHERITE_PICKAXE));

		boolean hasExpected = EXPECTED_EFFECTS.stream().anyMatch(player::hasStatusEffect);
		context.assertTrue(hasExpected, "Le joueur doit recevoir l’un des effets positifs après destruction du crâne");
		context.complete();
	}
}
