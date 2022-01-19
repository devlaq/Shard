package com.github.devlaq.shard.core.discord;

import arc.ApplicationListener;
import arc.util.ArcRuntimeException;
import arc.util.Log;
import com.github.devlaq.shard.core.Global;
import com.github.devlaq.shard.core.discord.event.DiscordMessageCreateEventHandler;
import com.github.devlaq.shard.core.discord.event.DiscordReadyEventHandler;
import com.google.gson.JsonObject;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import reactor.core.publisher.Mono;


public class Discord implements ApplicationListener {

    public static DiscordClient client;
    public static GatewayDiscordClient gateway;

    @Override
    public void init() {
        JsonObject discordConfigurations = Global.configuration.getAsJsonObject("discord");
        if(discordConfigurations == null) throw new ArcRuntimeException("Invalid configuration(config.json)");
        if(!discordConfigurations.get("enabled").getAsBoolean()) return;

        String token = discordConfigurations.get("token").getAsString();
        if(token == null) throw new ArcRuntimeException("Token not provided(config.json)");
        client = DiscordClient.builder(token).build();
        gateway = client.login().block();
    }

    @Override
    public void dispose() {
        ApplicationListener.super.dispose();
    }

    public void registerEvents() {
        gateway.on(MessageCreateEvent.class).subscribe(new DiscordMessageCreateEventHandler());
        gateway.on(ReadyEvent.class).subscribe(new DiscordReadyEventHandler());
    }

}
