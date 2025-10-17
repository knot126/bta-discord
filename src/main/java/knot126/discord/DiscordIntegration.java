package knot126.discord;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.net.command.CommandManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class DiscordIntegration implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
	public static final String MOD_ID = "discord";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static DiscordBot bot;

	@Override
	public void onInitialize() {
		CommandManager.registerServerCommand(new DiscordCommands());

		if (bot == null) {
			bot = new DiscordBot();
			// HACK: VERY BAD!!!
			bot.start();
		}
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
