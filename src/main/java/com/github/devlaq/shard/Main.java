package com.github.devlaq.shard;

import arc.ApplicationListener;
import arc.Core;
import arc.Events;
import arc.util.CommandHandler;
import com.github.devlaq.shard.command.ClientCommandManager;
import com.github.devlaq.shard.command.ServerCommandManager;
import com.github.devlaq.shard.command.client.*;
import com.github.devlaq.shard.command.server.AnnounceServerCommand;
import com.github.devlaq.shard.event.EventHandlerManager;
import com.github.devlaq.shard.event.handler.OnBlockBuildBegin;
import com.github.devlaq.shard.event.handler.OnGameOver;
import com.github.devlaq.shard.event.handler.OnPlayerChat;
import com.github.devlaq.shard.event.handler.OnPlayerJoin;
import mindustry.game.EventType;
import mindustry.mod.Plugin;

public class Main extends Plugin implements ApplicationListener {

    public ClientCommandManager clientCommandManager;
    public ServerCommandManager serverCommandManager;

    public Discord discord;

    @Override
    public void init() {
        Core.app.addListener(this);
        Core.app.addListener(discord = new Discord());
        registerEventHandlers();

        Core.settings.defaults(
                "shard-discord-enabled", false,
                "shard-discord-chat-channel", "00000000",
                "shard-discord-console-channel", "00000000"
        );

    }

    @Override
    public void dispose() {
        Core.app.removeListener(this);
        Core.app.removeListener(discord);
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        clientCommandManager = new ClientCommandManager(handler);

        //clientCommandManager.removeCommand("votekick");
        //clientCommandManager.removeCommand("vote");

        
        //clientCommandManager.registerCommand("vote", "<mode> [params...]", "투표 시작 명령어. 명령어를 입력하여 자세한 도움말을 확인하세요.", new VoteClientCommand());
        clientCommandManager.registerCommand("announce", "<messages...>", "공지 명령어. <message>를 모든 플레이어에게 공지합니다. 관리자 전용 명령어입니다.", new AnnounceClientCommand());
        clientCommandManager.registerCommamd("gameover", "Game Over 명령어. 관리자 전용 명령어입니다.", new GameOverClientCommand());
        clientCommandManager.registerCommamd("discord", "Discord 정보 확인 명령어.", new DiscordClientCommand());
        clientCommandManager.registerCommamd("status", "서버 상태 확인 명령어.", new StatusClientCommand());
        clientCommandManager.registerCommand("team", "<team>", "팀 변경 명령어. 관리자 전용 명령어입니다.", new TeamClientCommand());
        clientCommandManager.registerCommand("tp", "<playerName>", "텔레포트 명령어. 플레이어에게로 순간이동합니다.", new TeleportClientCommand());
        clientCommandManager.registerCommand("js", "<script>", "", new JavaScriptClientCommand());
        clientCommandManager.registerCommand("core", "[coreID]", "코어 이동 명령어. 아무것도 입력하지 않으면 코어 리스트를 확인할 수 있습니다.", new CoreClientCommand());
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        serverCommandManager = new ServerCommandManager(handler);

        serverCommandManager.registerCommand("announce", "<messages...>", "공지 명령어. <message>를 모든 플레이어에게 공지합니다.", new AnnounceServerCommand());
    }

    public void registerEventHandlers() {
        Events.on(EventType.PlayerChatEvent.class, new OnPlayerChat());
        Events.on(EventType.PlayerJoin.class, new OnPlayerJoin());
        Events.on(EventType.BlockBuildBeginEvent.class, new OnBlockBuildBegin());
        Events.on(EventType.GameOverEvent.class, new OnGameOver());
    }


}
