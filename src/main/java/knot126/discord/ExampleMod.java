package knot126.discord;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import knot126.discord.bot;

public class ExampleMod implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
	public static final String MOD_ID = "discord";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final DiscordBot BOT;

	@Override
	public void onInitialize() {
		if (BOT == null) {
			BOT = new DiscordBot();
		}
		LOGGER.info("Discord initialized.");
	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}
}
