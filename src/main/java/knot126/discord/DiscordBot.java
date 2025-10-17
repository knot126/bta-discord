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
	public String token;

	public DiscordBot() {
		this.channel = 0;
		this.token = "";
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setChannel(long channel) {
		this.channel = channel;
	}

	public void start() {
		DiscordIntegration.LOGGER.info("Starting discord integration on channel id " + String.valueOf(this.channel));
		this.client = DiscordClient.create(this.token);
		this.gateway = this.client.gateway().setEnabledIntents(IntentSet.nonPrivileged().or(IntentSet.of(Intent.MESSAGE_CONTENT))).login().block();
		this.setupRecieve();
	}

	public void setupRecieve() {
		this.gateway.on(MessageCreateEvent.class, event -> {
			Message message = event.getMessage();
			Member member = message.getAuthorAsMember().block();
			MessageChannel channel = message.getChannel().block();
			DiscordIntegration.LOGGER.info("Discord " + String.valueOf(this.channel) + " " + member.getUsername() + ": " + message.getContent());
			if (channel.getId().asLong() == this.channel) {
				this.recieve(member.getUsername(), message.getContent());
			}
			return Mono.empty();
		}).subscribe();
	}

	public void stop() {

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
