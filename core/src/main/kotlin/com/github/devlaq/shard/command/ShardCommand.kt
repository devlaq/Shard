package com.github.devlaq.shard.command

interface ShardCommand {

    val name: String
    val aliases: Array<String>

    fun execute()

}