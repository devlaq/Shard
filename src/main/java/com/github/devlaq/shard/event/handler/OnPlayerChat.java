package com.github.devlaq.shard.event.handler;

import arc.Core;
import arc.func.Cons;
import arc.util.Strings;
import com.github.devlaq.shard.core.Global;
import com.github.devlaq.shard.core.discord.Discord;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateFields;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.net.Administration;

import java.util.Date;

public class OnPlayerChat implements Cons<EventType.PlayerChatEvent> {

    @Override
    public void get(EventType.PlayerChatEvent event) {
        JsonObject discordConfiguration = Global.configuration.getAsJsonObject("discord");
        if(discordConfiguration == null) return;
        JsonArray chatSyncChannels = discordConfiguration.getAsJsonObject("channels").getAsJsonArray("chatSyncChannels");
        if(chatSyncChannels == null) return;
        for(JsonElement jsonChannel : chatSyncChannels) {
            String channelID = jsonChannel.getAsString();
            Channel channel = Discord.gateway.getChannelById(Snowflake.of(channelID)).block();
            if(!(channel instanceof MessageChannel messageChannel)) continue;
            messageChannel.createMessage(Strings.format("**@** > @", event.player.name, event.message));
        }
    }

}
