package com.github.devlaq.shard.util;

import arc.ApplicationListener;
import arc.func.Cons;
import arc.util.Align;
import com.github.devlaq.shard.Me;
import mindustry.gen.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Vote {

    private static VoteApplication application;

    public static VoteHandler kickVote = new VoteHandler("kick", "추방 투표", 60, new int[] {30, 15, 5, 4, 3, 2, 1}, (data) -> {
        Player player = (Player) data.get("targetPlayer");
        Me.broadcast("@이(가) 투표에 의해 추방되었습니다.");
    });

    public static class VoteApplication implements ApplicationListener {

        public  final VoteHandler type;
        public final Player startedPlayer;

        public int votingTimeLeft;


        //Update interval as tick(60 ticks = 1 second)
        private final int updateInterval;
        //Total voting time as seconds
        private final int votingTime;
        //Alerting times as second
        private final int[] alertTimes;

        private int tick = 60;


        public VoteApplication(Player startedPlayer, VoteHandler type, int updateInterval, int votingTime, int[] alertTimes) {
            this.startedPlayer = startedPlayer;
            this.type = type;
            this.updateInterval = updateInterval;
            this.votingTime = votingTime;
            this.votingTimeLeft = votingTime;
            this.alertTimes = alertTimes;
        }

        @Override
        public void update() {
            if(tick >= 60) {
                tick = 0;
                displayPopup();
                votingTimeLeft--;
            } else {
                tick++;
            }

            if(Arrays.stream(alertTimes).anyMatch(a -> votingTimeLeft==a)) {
                infoAlert();
            }

            if(votingTimeLeft == 0) {
                endVote();
            }
        }

        public void displayPopup() {
            Me.infoPopup(" [red]투표[] [green]진행[] 중\n\n[red]투표 발의자[]: [blue]" + startedPlayer.name() + "[]\n", 1f, Align.left, 0, 0, 0, 0);
        }

        public void infoAlert() {
            Me.broadcast("투표가 [blue]@[]초 남았습니다.([blue]@명 투표함[])", votingTimeLeft);
        }

        public void endVote() {
            Me.broadcast("투표가 끝났습니다.");

            type.onSuccess.get(type.data);
        }
    }

    public static class VoteHandler {

        public String name;
        public String displayName;
        public int votingTime;
        public int[] alertTimes;
        public Cons<Map<String, Object>> onSuccess;

        public Map<String, Object> data;

        public VoteHandler(String name, String displayName, int votingTime, int[] alertTimes, Cons<Map<String, Object>> onSuccess) {
            this.name = name;
            this.displayName = displayName;
            this.votingTime = votingTime;
            this.alertTimes = alertTimes;
            this.onSuccess = onSuccess;

            data = new HashMap<>();
        }

    }

}
