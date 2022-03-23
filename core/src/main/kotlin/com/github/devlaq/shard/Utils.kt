package com.github.devlaq.shard

import arc.Core
import mindustry.server.ServerControl

class Utils {

    companion object {
        fun getServerControl(): ServerControl? {
            return Core.app.listeners.find { l -> l is ServerControl } as ServerControl?
        }
    }

}