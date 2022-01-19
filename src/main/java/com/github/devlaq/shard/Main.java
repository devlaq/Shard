package com.github.devlaq.shard;

import arc.ApplicationListener;
import arc.Core;
import arc.Events;
import arc.Files;
import arc.files.Fi;
import com.github.devlaq.shard.command.ClientCommandManager;
import com.github.devlaq.shard.command.ServerCommandManager;
import com.github.devlaq.shard.command.client.*;
import com.github.devlaq.shard.command.server.AnnounceServerCommand;
import com.github.devlaq.shard.core.Global;
import com.github.devlaq.shard.core.ShardFi;
import com.github.devlaq.shard.core.configuration.JsonConfiguration;
import com.github.devlaq.shard.core.discord.Discord;
import com.github.devlaq.shard.event.handler.*;
import mindustry.game.EventType;

public class Main implements ApplicationListener {

    public static Main instance;

    public ClientCommandManager clientCommandManager;
    public ServerCommandManager serverCommandManager;

    public Discord discord;

    @Override
    public void init() {
        instance = this;

        Global.configuration = new JsonConfiguration(data("config.json"), classpath("config.json"));
        reloadConfigurations();

        registerEventHandlers();
        registerClientCommands();
        registerServerCommands();

        Core.app.addListener(discord = new Discord());
    }

    @Override
    public void dispose() {
        saveConfiguration();
    }

    public void reloadConfigurations() {
        Global.configuration.load(true);
    }

    public void saveConfiguration() {
        Global.configuration.save(false);
    }

    public void registerClientCommands() {
        clientCommandManager = new ClientCommandManager(PluginEntry.clientCommandHandler);

        clientCommandManager.removeCommand("votekick");
        clientCommandManager.removeCommand("vote");

        clientCommandManager.registerCommand("votekick", "<playername>", "추방 투표 명령어. Client 지원 전용.", new VoteKickClientCommand());
        clientCommandManager.registerCommand("vote", "<mode> [params...]", "투표 시작 명령어. 명령어를 입력하여 자세한 도움말을 확인하세요.", new VoteClientCommand());
        clientCommandManager.registerCommand("announce", "<messages...>", "공지 명령어. <message>를 모든 플레이어에게 공지합니다. 관리자 전용 명령어입니다.", new AnnounceClientCommand());
        clientCommandManager.registerCommamd("gameover", "Game Over 명령어. 관리자 전용 명령어입니다.", new GameOverClientCommand());
        clientCommandManager.registerCommamd("discord", "Discord 정보 확인 명령어.", new DiscordClientCommand());
        clientCommandManager.registerCommamd("status", "서버 상태 확인 명령어.", new StatusClientCommand());
        clientCommandManager.registerCommand("team", "<team>", "팀 변경 명령어. 관리자 전용 명령어입니다.", new TeamClientCommand());
        clientCommandManager.registerCommand("tp", "<playerName|x> [y]", "텔레포트 명령어. 플레이어 또는 좌표로 순간이동합니다.", new TeleportClientCommand());
        clientCommandManager.registerCommand("js", "<script...>", "", new JavaScriptClientCommand());
        clientCommandManager.registerCommand("core", "[coreID]", "코어 이동 명령어. 아무것도 입력하지 않으면 코어 리스트를 확인할 수 있습니다.", new CoreClientCommand());
        clientCommandManager.registerCommand("maps", "[search...]", "맵 리스트 명령어. 문자열을 입력하면 검색하고 -builtin 또는 -custom이 들어갈 경우 필터가 적용됩니다.", new MapsClientCommand());
        clientCommandManager.registerCommamd("bans", "밴 리스트 명령어.", new BansClientCommand());
    }

    public void registerServerCommands() {
        serverCommandManager = new ServerCommandManager(PluginEntry.serverCommandHandler);

        serverCommandManager.registerCommand("announce", "<messages...>", "공지 명령어. <message>를 모든 플레이어에게 공지합니다.", new AnnounceServerCommand());
    }

    public void registerEventHandlers() {
        Events.on(EventType.PlayerChatEvent.class, new OnPlayerChat());
        Events.on(EventType.PlayerJoin.class, new OnPlayerJoin());
        Events.on(EventType.PlayerLeave.class, new OnPlayerLeave());
        Events.on(EventType.BlockBuildBeginEvent.class, new OnBlockBuildBegin());
        Events.on(EventType.GameOverEvent.class, new OnGameOver());
        Events.on(EventType.PlayerBanEvent.class, new OnPlayerBan());
        Events.on(EventType.PlayerIpBanEvent.class, new OnPlayerIpBan());
        Events.on(EventType.PlayerUnbanEvent.class, new OnPlayerUnban());
        Events.on(EventType.PlayerIpUnbanEvent.class, new OnPlayerIpUnban());
   }

    public static Fi data(String path) {
        return Core.files.local("config/mods/Shard").child(path);
    }

    public static Fi classpath(String path) {
        return new ShardFi(path, Files.FileType.classpath);
    }

}
