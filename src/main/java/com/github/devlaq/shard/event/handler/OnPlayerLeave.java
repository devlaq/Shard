package com.github.devlaq.shard.event.handler;

import arc.func.Cons;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Groups;

public class OnPlayerLeave implements Cons<EventType.PlayerLeave> {

    @Override
    public void get(EventType.PlayerLeave event) {
        if(Groups.player.size() <= 1) Vars.state.serverPaused = true;
    }

}
