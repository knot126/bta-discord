package knot126.discord;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateSpec;
// import discord4j.rest.entity.RestChannel;

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
		this.gateway = this.client.login().block();
	}

	public void stop() {

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
