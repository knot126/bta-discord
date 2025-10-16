package knot126.discord;

import java.util.function.Consumer;
import reactor.core.publisher.Mono;
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
		this.client = DiscordClient.create(this.token);
		this.client.login().doOnSuccess(e -> { this.gateway = e; });
	}

	public void stop() {

	}

	public void send(String username, String message) {
		if (this.gateway != null) {
			this.gateway.getChannelById(Snowflake.of(this.channel))
				.ofType(MessageChannel.class)
				.doOnSuccess(chan -> {
					chan.createMessage(MessageCreateSpec.builder()
						.content("<" + username + "> " + message)
						.build()
					);
				});
		}
	}
}
