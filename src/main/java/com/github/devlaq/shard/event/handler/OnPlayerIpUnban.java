package com.github.devlaq.shard.event.handler;

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
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.spec.EmbedCreateFields;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.net.Administration;

import java.util.Date;

public class OnPlayerIpUnban implements Cons<EventType.PlayerIpUnbanEvent> {

    @Override
    public void get(EventType.PlayerIpUnbanEvent event) {
        Administration.PlayerInfo playerInfo = Vars.netServer.admins.findByIP(event.ip);
        String playerName = playerInfo != null ? playerInfo.lastName : "Name Unknown";
        String playerId = playerInfo != null ? playerInfo.id : "ID Unknown";

        JsonObject discordConfiguration = Global.configuration.getAsJsonObject("discord");
        if(discordConfiguration == null) return;
        JsonArray banLogChannels = discordConfiguration.getAsJsonObject("channels").getAsJsonArray("banLogChannels");
        JsonArray allLogChannels = discordConfiguration.getAsJsonObject("channels").getAsJsonArray("allLogChannels");
        JsonArray targetChannels = new JsonArray();
        if(banLogChannels != null) targetChannels.addAll(banLogChannels);
        if(allLogChannels != null) targetChannels.addAll(allLogChannels);
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
        builder.title("User IP Unbanned");
        builder.description("User unbanned by administrator");
        builder.addField(EmbedCreateFields.Field.of("Name", playerName, false));
        builder.addField(EmbedCreateFields.Field.of("ID", playerId, false));
        builder.addField(EmbedCreateFields.Field.of("Date", new Date().toString(), false));
        builder.color(Color.RED);
        EmbedCreateSpec embed = builder.build();
        for(JsonElement jsonChannel : targetChannels) {
            String channelID = jsonChannel.getAsString();
            Channel channel = Discord.gateway.getChannelById(Snowflake.of(channelID)).block();
            if(!(channel instanceof MessageChannel messageChannel)) continue;
            messageChannel.createMessage(embed).block();
        }
    }
}

