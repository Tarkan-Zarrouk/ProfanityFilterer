package com.filter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.filter.command.ToggleFilterCommand;

public class ProfanityFilterer implements ModInitializer {
	private static final String MOD_ID = "profanityfilterer";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ToggleFilterCommand.init();
		LOGGER.info("Profanity Filterer Mod Initialization Event");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
