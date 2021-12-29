package com.github.devlaq.shard.command.client;

import arc.util.CommandHandler;
import com.github.devlaq.shard.Me;
import com.github.devlaq.shard.Utils;
import mindustry.gen.Groups;
import mindustry.gen.Player;

public class TeleportClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        if(strings.length < 1) {
            Me.error(player, "1개의 인수가 필요힙니다");
            return;
        }

        Player targetPlayer = Utils.Players.findPlayer(strings[0]);
        if(targetPlayer == null) {
            Me.error(player, "대상 플레이어를 찾을 수 없습니다.");
            return;
        }
        if(targetPlayer.team() != player.team()) {
            Me.error(player, "아군 플레이어에게만 텔레포트할 수 있습니다.");
            return;
        }

        player.set(targetPlayer);
        player.unit().set(targetPlayer.unit());
        player.update();
        player.updateUnit();
        Me.info(player, "텔레포트 되었습니다.");
        //Me.info(targetPlayer, "@이(가) 당신에게로 텔레포트했습니다.", player.name());
    }

}
