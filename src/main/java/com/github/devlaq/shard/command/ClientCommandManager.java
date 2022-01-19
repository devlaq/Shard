package com.github.devlaq.shard.command;

import arc.func.Cons;
import arc.util.CommandHandler;

public class ClientCommandManager {

    public CommandHandler handler;

    public ClientCommandManager(CommandHandler handler) {
        this.handler = handler;
    }

    public <T> CommandHandler.Command registerCommamd(String text, String description, CommandHandler.CommandRunner<T> runner) {
        return this.handler.register(text, description, runner);
    }

    public <T> CommandHandler.Command registerCommand(String text, String params, String description, CommandHandler.CommandRunner<T> runner) {
        return this.handler.register(text, params, description, runner);
    }

    public CommandHandler.Command registerCommand(String text, String description, Cons<String[]> runner) {
        return this.handler.register(text, description, runner);
    }

    public CommandHandler.Command registerCommand(String text, String params, String description, Cons<String[]> runner) {
        return this.handler.register(text, params, description, runner);
    }

    public void removeCommand(String text) {
        this.handler.removeCommand(text);
    }

    public void setPrefix(String prefix) {
        this.handler.setPrefix(prefix);
    }

    public String getPrefix() {
        return this.handler.getPrefix();
    }

}
