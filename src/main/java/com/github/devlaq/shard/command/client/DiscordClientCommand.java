package com.github.devlaq.shard.command.client;

import arc.util.CommandHandler;
import com.github.devlaq.shard.Me;
import mindustry.gen.Player;

public class DiscordClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        Me.info(player, "Discord 서버: https://discord.gg/qHBsWvsDct");
    }

}
