package com.github.devlaq.shard.core.discord.event;

import arc.util.Strings;
import com.github.devlaq.shard.core.Global;
import com.github.devlaq.shard.core.Me;
import com.github.devlaq.shard.core.discord.Discord;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;

import java.util.function.Consumer;

public class DiscordMessageCreateEventHandler implements Consumer<MessageCreateEvent> {

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        JsonObject discordConfiguration = Global.configuration.getAsJsonObject("discord");
        if(discordConfiguration == null) return;
        if(!discordConfiguration.get("enabled").getAsBoolean()) return;
        JsonArray chatSyncChannels = discordConfiguration.getAsJsonObject("channels").getAsJsonArray("chatSyncChannels");
        if(chatSyncChannels == null) return;
        for(JsonElement jsonChannel : chatSyncChannels) {
            String channelID = jsonChannel.getAsString();
            Channel channel = Discord.gateway.getChannelById(Snowflake.of(channelID)).block();
            if(!(channel instanceof MessageChannel messageChannel)) continue;
            if(message.getChannel().block() == messageChannel) {
                if(message.getAuthor().isPresent() && !message.getAuthor().get().isBot()) {
                    Me.discord(message.getAuthor().get().getTag(), message.getContent());
                }
            }
        }

    }

}
