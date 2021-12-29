package com.github.devlaq.shard.event;

import arc.Events;
import arc.func.Cons;
import mindustry.content.Blocks;
import mindustry.gen.Call;

import java.util.ArrayList;

public class EventHandlerManager {

    public ArrayList<Cons<?>> handlers;

    public EventHandlerManager() {
        this.handlers = new ArrayList<>();
    }

    public <T> void registerEventHandler(Class<T> type, Cons<T> handler) {
        Events.on(type, handler);
        handlers.add(handler);
    }

}
