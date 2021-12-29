package com.github.devlaq.shard.command.client;

import arc.util.CommandHandler;
import com.github.devlaq.shard.Me;
import mindustry.game.Team;
import mindustry.gen.Player;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class TeamClientCommand implements CommandHandler.CommandRunner<Player> {

    @Override
    public void accept(String[] strings, Player player) {
        if(player.admin()) {
            if(strings.length > 0) {
                Team team;
                try {
                    team = Arrays.stream(Team.baseTeams).filter(t -> Objects.equals(t.name, strings[0])).findFirst().get();
                } catch (Exception ignored) {
                    Me.error(player, "@는 알 수 없는 팀입니다.", strings[0]);
                    return;
                }

                player.team(team);
                Me.info(player, "플레이어의 팀을 @로 바꿨습니다.", team.name);
            } else {
                StringJoiner joiner = new StringJoiner(", ");
                Arrays.stream(Team.baseTeams).forEach(t -> joiner.add(t.name));
                Me.info(player, "이용 가능한 팀: @", joiner.toString());
            }
        }
    }

}
