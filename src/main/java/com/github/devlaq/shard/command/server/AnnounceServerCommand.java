package com.github.devlaq.shard.command.server;

import arc.util.CommandHandler;
import mindustry.gen.Call;

public class AnnounceServerCommand implements CommandHandler.CommandRunner<Object> {

    @Override
    public void accept(String[] strings, Object o) {
        String joined = String.join(" ", strings).replace("\\n", "\n");
        Call.warningToast(0, joined);
        Call.sendMessage("[Server의 공지] " + joined);
    }

}
