package com.heart.mod;

import com.heart.mod.player.PlayerHealthHandler;
import com.heart.mod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartMod implements ModInitializer {
	public static final String MOD_ID = "heart-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		PlayerHealthHandler.registerPlayerHealthModifier();
	}
}