package com.github.devlaq.shard

import arc.func.Func
import arc.func.Func2
import arc.struct.Seq
import arc.util.CommandHandler
import mindustry.gen.Player

class Commands(core: ShardCore?) : Module(core) {

    val clientCommands: Seq<Command<Player>> = Seq()
    val serverCommands: Seq<Command<Any>> = Seq()

    var enabled = false

    var clientCommandHandler: Func2<AbstractUser, String, CommandHandler.CommandResponse> = Func2 { user, message ->
        if(!enabled) return@Func2 CommandHandler.CommandResponse(CommandHandler.ResponseType.noCommand, null, null)
        val args = Seq(message.substring(0, 1).split(" ").toTypedArray())

        val command = findClientCommand(args[0])
        if(command != null) {
            command.run(null, args)
            return@Func2 CommandHandler.CommandResponse(CommandHandler.ResponseType.valid, command.asCommand(), args[0])
        } else {
            return@Func2 CommandHandler.CommandResponse(CommandHandler.ResponseType.unknownCommand, null, args[0])
        }
    }

    var serverCommandHandler: Func<String, CommandHandler.CommandResponse> = Func { string ->
        if(!enabled) return@Func CommandHandler.CommandResponse(CommandHandler.ResponseType.noCommand, null, null)
        val args = Seq(string.substring(0, 1).split(" ").toTypedArray())

        val command = findServerCommand(args[0])
        if(command != null) {
            command.run(null, args)
            return@Func CommandHandler.CommandResponse(CommandHandler.ResponseType.valid, command.asCommand(), args[0])
        } else {
            return@Func CommandHandler.CommandResponse(CommandHandler.ResponseType.unknownCommand, null, args[0])
        }
    }

    fun handleClientCommand(user: AbstractUser, message: String): CommandHandler.CommandResponse {
        return clientCommandHandler.get(user, message)
    }

    fun handleServerCommand(message: String): CommandHandler.CommandResponse {
        return serverCommandHandler.get(message)
    }

    fun findClientCommand(name: String): Command<Player>? {
        return clientCommands.find { it.name == name || it.aliases.toList().contains(name) }
    }

    fun findServerCommand(name: String): Command<Any>? {
        return serverCommands.find { it.name == name || it.aliases.toList().contains(name) }
    }

    override fun name(): String {
        return "commands"
    }

    override fun enable() {
        this.enabled = true
    }

    override fun disable() {
        this.enabled = false
    }

    override fun enabled(): Boolean {
        return this.enabled
    }

    interface Command<T> {
        val name: String
        val aliases: Array<String>

        fun run(t: T?, args: Seq<String>)

        fun asCommand(): CommandHandler.Command {
            return CommandHandler.Command(name, "...", name) { args, _ ->
                run(null, Seq(args))
            }
        }
    }
}
