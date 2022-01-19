package com.github.devlaq.shard.core.discord.event;

import arc.util.Log;
import discord4j.core.event.domain.lifecycle.ReadyEvent;

import java.util.function.Consumer;

public class DiscordReadyEventHandler implements Consumer<ReadyEvent> {

    @Override
    public void accept(ReadyEvent event) {
        Log.info("ready");
    }

}
