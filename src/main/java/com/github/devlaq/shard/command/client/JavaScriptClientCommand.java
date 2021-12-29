package com.github.devlaq.shard.command.client;

import arc.util.CommandHandler;
import arc.util.Strings;
import com.github.devlaq.shard.Me;
import mindustry.Vars;
import mindustry.gen.Player;

public class JavaScriptClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        if(!player.admin()) {
            Me.error(player, "관리자 전용 명령어입니다.");
            return;
        }

        Me.info(player, "[orange]Script[] [blue]eval[] [cyan]results[]: \n@", Vars.mods.getScripts().runConsole(Strings.join(" ", strings)));
    }

}