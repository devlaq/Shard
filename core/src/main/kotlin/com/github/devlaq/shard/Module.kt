package com.github.devlaq.shard

import arc.struct.Seq

abstract class Module(val core: ShardCore?) {

    val modules: Seq<Module> = Seq()

    fun module(name: String): Module {
        return modules.find { it.name() == name }
    }

    abstract fun name(): String
    abstract fun enable()
    abstract fun disable()
    abstract fun enabled(): Boolean



}