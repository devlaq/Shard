package com.github.devlaq.shard.command.client;

import arc.Core;
import arc.util.CommandHandler;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.gen.Groups;
import mindustry.gen.Player;

public class StatusClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        player.sendMessage(Strings.format("Playing on map [blue]@[] / Wave [blue]@[]", Strings.capitalize(Strings.stripColors(Vars.state.map.name())), Vars.state.wave));
        if(Vars.state.rules.waves) {
            player.sendMessage(Strings.format("[blue]@[] enemies.", Vars.state.enemies));
        } else {
            player.sendMessage(Strings.format("[blue]@[] seconds until next wave.", (int)(Vars.state.wavetime / 60)));
        }
        player.sendMessage(Strings.format("[blue]@[] FPS, [blue]@[] MS used.", Core.graphics.getFramesPerSecond(), Core.app.getJavaHeap() / 1024 / 1024));
        if(Groups.player.size() > 0){
            player.sendMessage(Strings.format("Players: [blue]@[]", Groups.player.size()));
        }else{
            player.sendMessage("Players: [blue]No players connected.[]");
        }
    }

}
