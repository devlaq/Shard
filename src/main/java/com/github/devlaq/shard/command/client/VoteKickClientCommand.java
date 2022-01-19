package com.github.devlaq.shard.command.client;

import arc.util.CommandHandler;
import com.github.devlaq.shard.Main;
import mindustry.gen.Player;

public class VoteKickClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        Main.instance.clientCommandManager.handler.handleMessage("/vote kick", strings);
    }

}
