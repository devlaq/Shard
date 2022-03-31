package com.github.devlaq.shard.command

interface ShardClientCommand {

    val name: String
    val aliases: Array<String>

    fun execute()

}