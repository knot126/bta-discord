package knot126.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.gateway.intent.Intent;
import discord4j.gateway.intent.IntentSet;
import net.minecraft.server.MinecraftServer;
// import discord4j.rest.entity.RestChannel;
import reactor.core.publisher.Mono;

public class DiscordBot {
	public DiscordClient client;
	public GatewayDiscordClient gateway;
	public long channel;

	public DiscordBot() {
		this.channel = 0;
	}

	public void start() {
		String token = DiscordConfig.get("token");

		if (token == null) {
			DiscordIntegration.LOGGER.error("Discord token is not set! Will not start bot.");
			return;
		}

		try {
			this.channel = Long.valueOf(DiscordConfig.get("channel"));
		}
		catch (NumberFormatException e) {
			DiscordIntegration.LOGGER.error("The channel ID is not convertable to a long value! Will not start bot.", e);
			return;
		}

		DiscordIntegration.LOGGER.info("Starting discord integration on channel id " + String.valueOf(this.channel));
		this.client = DiscordClient.create(token);
		this.gateway = this.client.gateway().setEnabledIntents(IntentSet.nonPrivileged().or(IntentSet.of(Intent.MESSAGE_CONTENT))).login().block();
		this.setupRecieve();
	}

	public void setupRecieve() {
		this.gateway.on(MessageCreateEvent.class, event -> {
			Message message = event.getMessage();
			Member member = message.getAuthorAsMember().block();
			MessageChannel channel = message.getChannel().block();
			DiscordIntegration.LOGGER.info("Discord " + String.valueOf(this.channel) + " " + member.getUsername() + ": " + message.getContent());
			if (channel.getId().asLong() == this.channel && this.gateway.getSelfId().asLong() != member.getId().asLong()) {
				this.recieve(member.getUsername(), message.getContent());
			}
			return Mono.empty();
		}).subscribe();
	}

	public void stop() {
		this.gateway.logout().block();
	}

	public void restart() {
		if (this.gateway != null) stop();
		start();
	}

	public void recieve(String username, String message) {
		MinecraftServer mcServer = MinecraftServer.getInstance();
		mcServer.playerList.sendEncryptedChatToAllPlayers("<" + username + "> " + message);
	}

	public void send(String username, String message) {
		/*if (this.gateway != null) {
			this.gateway.getChannelById(Snowflake.of(this.channel))
				.ofType(MessageChannel.class)
				.doOnSuccess(chan -> {
					chan.createMessage(MessageCreateSpec.builder()
						.content("<" + username + "> " + message)
						.build()
					);
				});
		}*/
		if (this.gateway != null) {
			MessageChannel channel = this.gateway.getChannelById(Snowflake.of(this.channel)).ofType(MessageChannel.class).block();
			channel.createMessage(MessageCreateSpec.builder().content("<" + username + "> " + message).build()).block();
		}
	}
}
