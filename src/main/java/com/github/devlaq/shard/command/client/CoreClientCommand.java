package com.github.devlaq.shard.command.client;

import arc.struct.IntMap;
import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import com.github.devlaq.shard.core.Me;
import com.github.devlaq.shard.util.Cores;
import com.github.devlaq.shard.util.Utils;
import mindustry.gen.Player;
import mindustry.world.Tile;

import java.util.Map;

public class CoreClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        Cores.update();
        IntMap<Tile> cores = Cores.cores;
        if(strings.length < 1) {
            Me.info(player, "아군 코어 [blue]@[]개:");
            for(int id : cores.keys().toArray().items) {
                Tile tile = cores.get(id);
                Me.info(player, "@. [yellow]종류[]: [blue]@[] / [green]위치[]: [blue]@[], [blue]@ / [red]체력[]: [blue]@[]", id, tile.block().localizedName, tile.x, tile.y, tile.build.health() / tile.build.maxHealth());
            }
        } else {
            int targetCoreID;
            try {
                targetCoreID = Integer.parseInt(strings[0]);
            } catch (Exception ignored) {
                Me.error(player, "@는 잘못된 숫자입니다.", strings[0]);
                return;
            }
            Tile core = cores.get(targetCoreID);
            if(core == null) {
                Me.error(player, "Core @을(를) 찾을 수 없습니다.", targetCoreID);
                return;
            }
            Utils.Players.teleportPlayer(player, core.x, core.y);
        }
    }

}