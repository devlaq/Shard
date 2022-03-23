package com.github.devlaq.shard.event

import arc.func.Cons
import com.github.devlaq.shard.ShardCore
import mindustry.game.EventType

class PlayerChatEventHandler: Cons<EventType.PlayerChatEvent> {
    override fun get(event: EventType.PlayerChatEvent?) {
        if (event != null) {
            ShardCore.instance?.commands?.handleClientCommand(event)
        }
    }
}