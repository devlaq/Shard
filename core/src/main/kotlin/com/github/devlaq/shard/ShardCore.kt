package com.github.devlaq.shard

abstract class ShardCore : Module(null) {

    companion object {
        var instance: ShardCore? = null
    }

    var commands: Commands? = null
    var events: EventHandlers? = null

}