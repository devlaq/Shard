package com.github.devlaq.shard.command.client;

import arc.Core;
import arc.math.geom.Position;
import arc.struct.Seq;
import arc.util.CommandHandler;
import com.github.devlaq.shard.core.Me;
import mindustry.Vars;
import mindustry.gen.Player;
import mindustry.net.Administration;

public class BansClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        Seq<Administration.PlayerInfo> bans = Vars.netServer.admins.getBanned();
        Me.info(player, "[blue]ID 차단된 플레이어 목록[]([blue]@[]):", bans.size);
        for(Administration.PlayerInfo info : bans) {
            Me.info(player, "[blue]@[] / [blue]마지막으로 알려진 이름[]: [blue]'@'[]", info.id, info.lastName);
        }
        Seq<String> ipbans = Vars.netServer.admins.getBannedIPs();
        Me.info(player, "[blue]IP 차단된 플레이어 목록[]([blue]@[]):", ipbans.size);
        for(String string : ipbans) {
            Administration.PlayerInfo info = Vars.netServer.admins.findByIP(string);
            if(info != null) {
                Me.info(player, "[blue]@[] / [blue]마지막으로 알려진 이름[]: [blue]'@'[] / ID: [blue]'@'[]", string, info.lastName, info.id);
            } else {
                Me.info(player, "[blue]@[] / [red]플레이어 정보가 없음[]", string);
            }
        }
    }

}
