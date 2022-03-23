package com.github.devlaq.shard

import arc.func.Func
import arc.struct.Seq
import arc.util.CommandHandler
import mindustry.game.EventType
import mindustry.gen.Player

class Commands(core: ShardCore?) : Module(core) {

    val clientCommands: Seq<Command<Player>> = Seq()
    val serverCommands: Seq<Command<Any>> = Seq()

    var enabled = false

    var clientCommandHandler: Func<EventType.PlayerChatEvent, CommandHandler.CommandResponse> = Func { event ->
        if(!enabled) return@Func CommandHandler.CommandResponse(CommandHandler.ResponseType.noCommand, null, null)
        val args = Seq(event.message.substring(0, 1).split(" ").toTypedArray())

        val command = findClientCommand(args[0])
        if(command != null) {
            command.run(null, args)
            return@Func CommandHandler.CommandResponse(CommandHandler.ResponseType.valid, command.asCommand(), args[0])
        } else {
            return@Func CommandHandler.CommandResponse(CommandHandler.ResponseType.unknownCommand, null, args[0])
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

    fun handleClientCommand(event: EventType.PlayerChatEvent): CommandHandler.CommandResponse {
        return clientCommandHandler.get(event)
    }

    fun handleServerCommand(string: String): CommandHandler.CommandResponse {
        return serverCommandHandler.get(string)
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
