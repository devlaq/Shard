package com.github.devlaq.shard.command.client;

import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Log;
import arc.util.Strings;
import com.github.devlaq.shard.core.Me;
import com.github.devlaq.shard.util.Utils;
import mindustry.Vars;
import mindustry.gen.Player;
import mindustry.maps.Map;

import java.util.List;

public class MapsClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        Seq<Map> maps;
        if(strings.length < 1) {
            maps = Vars.maps.all();
            Me.info(player, "[blue]All maps[]([blue]@[]): ", maps.size);

        } else {
            maps = Utils.Maps.searchMaps(strings[0]);
            Me.info(player, "[blue]Search result for \"@\"[]([blue]@[]):");
        }

        if(Strings.join(" ", strings).contains(" -builtin")) {
            maps = maps.filter(m -> Vars.maps.defaultMaps().contains(m));
            Me.info(player, "[blue]Filter[]: [blue]builtin[]");
        } else if(Strings.join(" ", strings).contains(" -custom")) {
            maps = maps.filter(m -> Vars.maps.customMaps().contains(m));
            Me.info(player, "[blue]Filter[]: [blue]custom[]");
        }

        for(int i = 0; i < maps.size; i++) {
            Map map = maps.get(i);
            Me.info(player, "[blue]@[]. [blue]@[] / [blue]Custom[]: [blue]@[] / [blue]Tags[]: [blue]@[]", i, map.name(), map.custom, map.tags.toString(", ", true));
        }
    }

}
