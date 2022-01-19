package com.github.devlaq.shard.command.client;

import arc.struct.ObjectMap;
import arc.util.CommandHandler;
import com.github.devlaq.shard.core.Me;
import com.github.devlaq.shard.util.Utils;
import com.github.devlaq.shard.util.Vote;
import mindustry.gen.Player;
import mindustry.maps.Map;

import java.util.HashMap;

public class VoteClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        if(strings.length < 1) {
            Me.error(player, "1개 이상의 인수가 필요합니다.");
            return;
        }

        if(strings[0].equalsIgnoreCase("kick")) {
            if(Vote.isVoting() && Vote.application.handler.name.equalsIgnoreCase("kick")) {
                Vote.vote(player, true);
                return;
            }
        }

        if(Vote.isVoting()) {
            Me.error(player, "이미 투표가 진행 중입니다! 한 번에 한 개의 투표만 진행될 수 있습니다!");
            return;
        }

        Vote.VoteHandler handler = null;
        ObjectMap<String, Object> data = new ObjectMap<>();

        switch (strings[0].toLowerCase()) {
            case "kick": {
                if(strings.length < 2) {
                    Me.error(player, "추방 투표할 대상 플레이어를 입력하세요.");
                    return;
                }
                Player targetPlayer = Utils.Players.findPlayer(strings[1]);
                if(targetPlayer == null) {
                    Me.error(player, "플레이어를 찾을 수 없습니다.");
                    return;
                }
                handler = Vote.kickVote;
                data.put("targetPlayer", targetPlayer);
                break;
            }
            case "map": {
                if(strings.length < 2) {
                    Me.error(player, "변경할 맵 이름을 입력하세요.");
                    return;
                }
                Map targetMap = Utils.Maps.findAuto(strings[1]);
                if(targetMap == null) {
                    Me.error(player, "맵을 찾을 수 없습니다.");
                    return;
                }
                handler = Vote.mapVote;
                data.put("targetMap", targetMap);
                break;
            }
            case "gameover": {
                handler = Vote.gameoverVote;
                break;
            }
            case "skipwave": {
                if(strings.length < 2) {
                    Me.warn(player, "넘길 웨이브가 설정되지 않아 1 웨이브를 스킵합니다.");
                }
                int waveToSkip = Math.abs(Utils.Integers.parseIntOrValue(strings[1], 1));
                handler = Vote.skipwaveVote;
                data.put("waveToSkip", waveToSkip);
                break;
            }
        }
        if(handler == null) return;

        if(!Vote.startNewVote(handler, player, data)) {
            Me.error(player, "인원이 부족하거나 오류가 발생하여 투표를 시작하지 못했습니다.");
        }
    }

}
