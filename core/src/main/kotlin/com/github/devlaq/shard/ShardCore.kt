package com.github.devlaq.shard

abstract class ShardCore(val plugin: ShardPlugin): Module(null) {

    companion object {
        var instance: ShardCore? = null
    }

    var commands: Commands? = null

}