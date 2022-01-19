package com.github.devlaq.shard.command.client;

import arc.Events;
import arc.util.CommandHandler;
import arc.util.Timer;
import com.github.devlaq.shard.core.Me;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Player;

import java.util.HashMap;
import java.util.Map;

public class GameOverClientCommand implements CommandHandler.CommandRunner<Player> {

    private final Map<Player, Boolean> confirmed = new HashMap<>();

    @Override
    public void accept(String[] strings, Player player) {
        if(!player.admin()) {
            Me.error(player, "관리자 전용 명령어입니다.");
            return;
        }

        if(confirmed.getOrDefault(player, false)) {
            Me.info(player, "Game Over 되었습니다.");
            Me.broadcast("관리자 @에 의해 Game Over 되었습니다.", player.name());
            Events.fire(new EventType.GameOverEvent(Team.crux));
            confirmed.put(player, false);
        } else {
            Me.confirm(player, "정말로 게임 오버 하시겠습니까? 진행하려면 10초 이내에 명령어를 한 번 더 입력하세요.");
            confirmed.put(player, true);
            Timer.instance().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                if(!confirmed.getOrDefault(player, false)) return;
                confirmed.put(player, false);
                Me.cancel(player, "게임 오버가 취소되었습니다.");
                }
            }, 10);
        }
    }

}
