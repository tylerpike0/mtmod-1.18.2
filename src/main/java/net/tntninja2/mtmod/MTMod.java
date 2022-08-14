package net.tntninja2.mtmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.ActionResult;
import net.tntninja2.mtmod.block.ModBlocks;
import net.tntninja2.mtmod.event.LivingEntityDamageCancelCallback;
import net.tntninja2.mtmod.item.ModItems;
import net.tntninja2.mtmod.item.armorSkillsAndNbt.ArmorSkillAbilities;
import net.tntninja2.mtmod.networking.ModMessages;
import net.tntninja2.mtmod.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MTMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

//	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final String MOD_ID = "mtmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.!!!!

//		Load mod items and blocks
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModRegistries.registerModStuffs();
		ModMessages.registerC2SPackets();
		MTModTickEvents.registerServerEndTickEvents();

		ArmorSkillAbilities.register();

		LivingEntityDamageCancelCallback.EVENT.register((livingEntity, damageSource, amount) -> {
			MTMod.LOGGER.info(livingEntity.getClass().descriptorString() + " was avoided " + amount + " from " + damageSource.toString());
			return ActionResult.PASS;
		});

	}

}
