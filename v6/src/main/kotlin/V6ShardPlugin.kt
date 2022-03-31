import arc.ApplicationListener
import arc.Core
import mindustry.mod.Plugin

class V6ShardPlugin: Plugin() {

    val core = V6ShardCore()

    override fun init() {
        core.enable()
        Core.app.addListener(object : ApplicationListener {
            override fun dispose() {
                core.disable()
            }
        })
    }

}