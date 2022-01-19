package com.github.devlaq.shard;

import arc.Core;
import arc.util.CommandHandler;
import mindustry.mod.Plugin;

public class PluginEntry extends Plugin {

    private Main main;

    public static CommandHandler clientCommandHandler;
    public static CommandHandler serverCommandHandler;

    @Override
    public void init() {
        Core.app.addListener(main = new Main());
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        clientCommandHandler = handler;
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        serverCommandHandler = handler;
    }
}
