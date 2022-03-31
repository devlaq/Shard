import arc.Events
import arc.func.Cons2
import com.github.devlaq.shard.Commands
import com.github.devlaq.shard.EventHandlers
import com.github.devlaq.shard.ShardCore
import mindustry.game.EventType

class V6ShardCore : ShardCore() {

    private var enabled = false

    override fun name(): String {
        return "V6ShareCore"
    }

    override fun enable() {
        if(this.enabled) return
        commands = Commands(this)
        events = EventHandlers()
        events?.eventRegistrar = Cons2 { clazz, handler ->
            if(V6Registry.eventMapping[clazz::class.java] != null) {
                //Events.on(V6Registry.eventMapping[clazz::class.java], handler)
            }
        }
    }

    override fun disable() {
        if(!this.enabled) return
    }

    override fun enabled(): Boolean {
        return this.enabled
    }
}