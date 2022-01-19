package com.github.devlaq.shard.command.client;

import arc.util.CommandHandler;
import com.github.devlaq.shard.core.Me;
import com.github.devlaq.shard.util.Utils;
import mindustry.Vars;
import mindustry.gen.Call;
import mindustry.gen.Player;

public class TeleportClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        if(strings.length < 1) {
            Me.error(player, "1개 또는 2개의 인수가 필요힙니다");
            return;
        }

        if(strings.length < 2) {
            Player targetPlayer = Utils.Players.findPlayer(strings[0]);
            if(targetPlayer == null) {
                Me.error(player, "대상 플레이어를 찾을 수 없습니다. 비슷한 플레이어: @", Utils.Players.findPlayer(strings[0], 1000).name());
                return;
            }
            if(targetPlayer.team() != player.team()) {
                Me.error(player, "아군 플레이어에게만 텔레포트할 수 있습니다.");
                return;
            }
            Utils.Players.teleportPlayer(player, targetPlayer);
            Me.info(player, "@에게로 텔레포트 되었습니다.", targetPlayer.name());
            //Me.info(targetPlayer, "@이(가) 당신에게로 텔레포트했습니다.", player.name());
        } else {
            int targetX, targetY;
            try {
                targetX = Integer.parseInt(strings[0]);
                targetY = Integer.parseInt(strings[1]);
            } catch (Exception ignored) {
                Me.error(player, "모든 인수가 정수여야합니다.");
                return;
            }
            Utils.Players.teleportPlayer(player, targetX, targetY);
            Me.info(player, "@, @으로 텔레포트 되었습니다.", targetX, targetY);
        }
    }

}
