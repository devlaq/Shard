package com.github.devlaq.shard

import arc.Events
import arc.func.Cons
import arc.struct.Seq

class EventHandlers : Module(null) {

    private var enabled = false

    private val handlers: Seq<Cons<Any>> = Seq()

    override fun name(): String {
        return "event_handler"
    }

    override fun enable() {
        if(enabled()) return
        this.enabled = true
    }

    override fun disable() {
        if(!enabled()) return
        this.enabled = false
    }

    override fun enabled(): Boolean {
        return this.enabled
    }

    fun register(clazz: Class<Any>, handler: Cons<Any>) {
        handlers.add(handler)
        Events.on(clazz, handler)
    }

    fun unregister(clazz: Class<Any>, handler: Cons<Any>) {
        handlers.remove(handler)
        Events.remove(clazz, handler)
    }

}