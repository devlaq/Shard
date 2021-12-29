package com.github.devlaq.shard.command.client;

import arc.Core;
import arc.util.CommandHandler;
import arc.util.Strings;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;

public class AnnounceClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        String joined = String.join(" ", strings).replace("\\n", "\n");
        Call.warningToast(0, joined);
        Call.sendMessage("[" + player.name() + "] " + joined);
    }

}
