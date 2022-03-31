package com.github.devlaq.shard

import arc.Events
import arc.func.Cons
import arc.func.Cons2
import arc.struct.Seq

class EventHandlers : Module(null) {

    private var enabled = false

    private val handlers: Seq<Cons<Any>> = Seq()

    var eventRegistrar: Cons2<Class<Any>, Cons<Any>>? = null
    var eventRemover: Cons2<Class<Any>, Cons<Any>>? = null

    override fun name(): String {
        return "EventHandlers"
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
        eventRegistrar?.get(clazz, handler)
    }

    fun remove(clazz: Class<Any>, handler: Cons<Any>) {
        handlers.remove(handler)
        eventRemover?.get(clazz, handler)
    }

}