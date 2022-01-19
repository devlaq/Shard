package com.github.devlaq.shard.event.handler;

import arc.func.Cons;
import arc.util.Log;
import arc.util.Strings;
import arc.util.Time;
import com.github.devlaq.shard.core.Global;
import com.github.devlaq.shard.core.discord.Discord;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import discord4j.common.util.Snowflake;
import discord4j.core.object.Embed;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.spec.EmbedCreateFields;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.net.Administration;

import java.util.Date;

public class OnPlayerBan implements Cons<EventType.PlayerBanEvent> {

    @Override
    public void get(EventType.PlayerBanEvent event) {
        Administration.PlayerInfo playerInfo = event.player != null ? event.player.getInfo() : Vars.netServer.admins.getInfo(event.uuid);
        String playerName = playerInfo != null ? playerInfo.lastName : "Name Unknown";
        String playerId = playerInfo != null ? playerInfo.id : "ID Unknown";

        JsonObject discordConfiguration = Global.configuration.getAsJsonObject("discord");
        Log.info("Reach :A");
        if(discordConfiguration == null) return;
        JsonArray banLogChannels = discordConfiguration.getAsJsonObject("channels").getAsJsonArray("banLogChannels");
        JsonArray allLogChannels = discordConfiguration.getAsJsonObject("channels").getAsJsonArray("allLogChannels");
        JsonArray targetChannels = new JsonArray();
        Log.info("Reach :B");
        if(banLogChannels != null) targetChannels.addAll(banLogChannels);
        Log.info("Reach :C");
        if(allLogChannels != null) targetChannels.addAll(allLogChannels);
        Log.info("Reach :D");
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
        builder.title("User Banned");
        builder.description("User banned by administrator");
        builder.addField(EmbedCreateFields.Field.of("Name", playerName, false));
        builder.addField(EmbedCreateFields.Field.of("ID", playerId, false));
        builder.addField(EmbedCreateFields.Field.of("Date", new Date().toString(), false));
        builder.color(Color.RED);
        EmbedCreateSpec embed = builder.build();
        Log.info(banLogChannels);
        for(JsonElement jsonChannel : targetChannels) {
            Log.info("Reach :E");
            String channelID = jsonChannel.getAsString();
            Channel channel = Discord.gateway.getChannelById(Snowflake.of(channelID)).block();
            if(!(channel instanceof MessageChannel messageChannel)) continue;
            Log.info("Reach :F");
            messageChannel.createMessage(embed).block();
            Log.info("Ban log sent to: @, EMBED: @", messageChannel.getId(), embed.toString());
        }
    }

}
