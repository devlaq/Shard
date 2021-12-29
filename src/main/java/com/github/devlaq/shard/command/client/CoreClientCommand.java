package com.github.devlaq.shard.command.client;

import arc.util.CommandHandler;
import com.github.devlaq.shard.Me;
import com.github.devlaq.shard.Utils;
import com.github.devlaq.shard.util.Cores;
import mindustry.gen.Player;
import mindustry.world.Tile;

import java.util.List;
import java.util.Map;

public class CoreClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        Cores.update();
        Map<Integer, Tile> cores = Cores.cores;
        Me.info(player, "아군 코어 [blue]@[]개:");
        for(int id : cores.keySet()) {
            Tile tile = cores.get(id);
            Me.info(player, "@. [yellow]종류[]: [blue]@[] / [green]위치[]: [blue]@[], [blue]@ / [red]체력[]: [blue]@[]", id, tile.block().localizedName, tile.x, tile.y, tile.build.health() / tile.build.maxHealth());
        }
    }

}