package knot126.discord;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentTypeString;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.builder.ArgumentBuilderRequired;

import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;

public class DiscordCommands implements CommandManager.CommandRegistry {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register(
            (ArgumentBuilderLiteral) ArgumentBuilderLiteral
                .literal("discord").requires(source -> ((CommandSource)source).hasAdmin())
                // Channel ID
                .then(
                    ArgumentBuilderLiteral.literal("channel").then(
                        ArgumentBuilderRequired.argument("id", ArgumentTypeString.string())
                            .executes(context -> {
                                this.setChannelOption(context.getArgument("id", String.class));
                                ((CommandSource) context.getSource()).sendMessage("Channel ID has been set!");
                                return 1;
                            })
                    )
                )
                // Second argument
                .then(
                    ArgumentBuilderLiteral.literal("token").then(
                        ArgumentBuilderRequired.argument("token", ArgumentTypeString.string())
                            .executes(context -> {
                                this.setTokenOption(context.getArgument("token", String.class));
                                ((CommandSource) context.getSource()).sendMessage("Bot token has been set!");
                                return 1;
                            })
                    )
                )
        );
    }

    public void setChannelOption(String id) {
        DiscordConfig.set("channel", id);
        DiscordIntegration.bot.restart();
    }

    public void setTokenOption(String token) {
        DiscordConfig.set("token", token);
        DiscordIntegration.bot.restart();
    }
}
