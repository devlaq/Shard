package com.github.devlaq.shard.event.handler;

import arc.Core;
import arc.func.Cons;
import mindustry.Vars;
import mindustry.game.EventType;

public class OnPlayerJoin implements Cons<EventType.PlayerJoin> {

    @Override
    public void get(EventType.PlayerJoin event) {
        if(event.player.admin()) Vars.netServer.admins.adminPlayer(event.player.uuid(), event.player.usid());
    }

}
