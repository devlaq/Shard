package com.github.devlaq.shard.core.discord.event;

import com.github.devlaq.shard.core.Me;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.util.function.Consumer;

public class DiscordMessageCreateEventHandler implements Consumer<MessageCreateEvent> {

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        if(message.getAuthor().isPresent() && !message.getAuthor().get().isBot()) {
            Me.discord(message.getAuthor().get().getTag(), message.getContent());
        }
    }

}
