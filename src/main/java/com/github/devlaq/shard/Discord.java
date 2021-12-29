package com.github.devlaq.shard;

import arc.ApplicationListener;
import arc.Core;
import arc.Events;
import com.mewna.catnip.Catnip;
import com.mewna.catnip.entity.channel.Channel;
import com.mewna.catnip.entity.channel.GuildChannel;
import com.mewna.catnip.shard.DiscordEvent;
import io.reactivex.rxjava3.core.Maybe;
import jdk.internal.org.jline.utils.Log;
import mindustry.game.EventType;
import mindustry.gen.Player;


public class Discord implements ApplicationListener {

    public Catnip catnip;

    @Override
    public void init() {
        if(!Core.settings.getBool("shard-discord-enabled")) {
            return;
        }

        String token = Core.settings.getString("shard-discord-token");
        if(token == null) {
            Log.error("Shard: Discord token is null.");
            return;
        }
        catnip = Catnip.catnip(token);
        Events.on(EventType.PlayerChatEvent.class, (event) -> {
            Player player = event.player;
            String message = event.message;
            catnip.cache().channel(Core.settings.getString("shard-discord-chat-guild"), Core.settings.getString("shard-discord-chat-channel")).subscribe(c -> {
                c.asMessageChannel().sendMessage("**" + player.name() + "** > " + message);
            }).dispose();
        });
        catnip.observable(DiscordEvent.MESSAGE_CREATE).subscribe(message -> {
            Me.discord(message.author().asMention(), message.content());
        }).dispose();
    }

    @Override
    public void dispose() {

    }
}
