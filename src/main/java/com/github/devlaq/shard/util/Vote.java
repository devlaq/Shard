package com.github.devlaq.shard.util;

import arc.ApplicationListener;
import arc.Core;
import arc.Events;
import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.util.Align;
import com.github.devlaq.shard.core.Global;
import com.github.devlaq.shard.core.Me;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Player;

import java.util.Arrays;

public class Vote {

    public static VoteApplication application = null;

    public static VoteHandler kickVote = new VoteHandler("kick", "추방 투표", 3, 0.5F, 60, new int[] {45, 30, 15, 5, 4, 3, 2, 1}, (data) -> {
        Player player = (Player) data.get("targetPlayer");
        player.con().kick("다른 플레이어들에 의한 추방 투표 (20분)", 1000 * 60 * 20);
        Me.broadcast("@이(가) 투표에 의해 20분간 차단되었습니다.");
    });
    public static VoteHandler mapVote = new VoteHandler("map", "맵 투표", 1, 0.75F, 60, new int[] {45, 30, 15, 5, 4, 3, 2, 1}, (data) -> {
        mindustry.maps.Map map = (mindustry.maps.Map) data.get("targetMap");
        Utils.Maps.changeMap(map);
        Events.fire(new EventType.GameOverEvent(Team.derelict));
    });
    public static VoteHandler gameoverVote = new VoteHandler("gameover", "게임오버 투표", 1, 0.75F, 60, new int[] {45, 30, 15, 5, 4, 3, 2, 1}, (data) -> {
        Events.fire(new EventType.GameOverEvent(Team.derelict));
    });
    public static VoteHandler skipwaveVote = new VoteHandler("skipwave", "웨이브 스킵 투표", 1, 0.25F, 60, new int[] {45, 30, 15, 5, 4, 3, 2, 1}, (data) -> {
        int waveToSkip = (int) data.get("waveToSkip", 1);
        Global.waveReason = "Vote_" + waveToSkip;
        for(int i = 1; i <= waveToSkip; i++) Vars.logic.runWave();
    });

    public static boolean isVoting() {
        return application != null;
    }

    public static boolean startNewVote(VoteHandler handler, Player startedPlayer, ObjectMap<String, Object> data) {
        if(data == null) data = new ObjectMap<>();
        if(!isVoting()) {
            if(Groups.player.size() < handler.minimumPlayers) {
                return false;
            }
            Core.app.addListener(application = new VoteApplication(startedPlayer, handler, 60, data));
            Me.broadcast("@이(가) @ 투표를 시작했습니다.", startedPlayer.name, handler.displayName);
            return true;
        }
        return false;
    }

    public static boolean vote(Player player, boolean yes) {
        if(!isVoting()) return false;
        if(application.votedPlayers.containsKey(player)) return false;
        application.votedPlayers.put(player, yes);
        return true;
    }

    public static class VoteApplication implements ApplicationListener {

        public final VoteHandler handler;
        public final Player startedPlayer;
        public final ObjectMap<Player, Boolean> votedPlayers;
        public final ObjectMap<String, Object> data;

        public int votingTimeLeft;

        //Update interval as tick(60 ticks = 1 second)
        private final int updateInterval;
        //Alerting times as second
        private final int[] alertTimes;

        private int tick = 60;


        public VoteApplication(Player startedPlayer, VoteHandler type, int updateInterval, ObjectMap<String, Object> data) {
            this.startedPlayer = startedPlayer;
            this.handler = type;
            this.updateInterval = updateInterval;
            this.alertTimes = type.alertTimes;

            this.votingTimeLeft = type.votingTime;
            this.votedPlayers = new ObjectMap<>();
            this.data = data;
        }

        @Override
        public void update() {
            if(this.tick >= this.updateInterval) {
                this.tick = 0;
                this.displayPopup();
                this.votingTimeLeft--;
            } else {
                this.tick++;
            }

            if(Arrays.stream(this.alertTimes).anyMatch(a -> this.votingTimeLeft==a && this.tick == 0)) {
                infoAlert();
            }

            if(this.votingTimeLeft == 0) {
                endVote();
            }
        }

        public int agreeCount() {
            return votedPlayers.values().toSeq().filter(b -> b).size;
        }

        public int disagreeCount() {
            return votedPlayers.values().toSeq().filter(b -> !b).size;
        }

        public void displayPopup() {
            Me.infoPopup(Me.format("@ 투표 진행 중...\n\n@/@ 투표\n@ 동의/@ 반대\n\n@에 의해 시작됨", handler.displayName, votedPlayers.size, Groups.player.size(), agreeCount(), disagreeCount(), startedPlayer), 1.001f, Align.left, 0, 0, 0, 0);
        }

        public void infoAlert() {
            Me.broadcast("투표가 [blue]@[]초 남았습니다.([blue]@명 투표함[])", votingTimeLeft, votedPlayers.size);
        }

        public void endVote() {
            Me.broadcast("투표가 끝났습니다.");
            if(this.votedPlayers.values().toSeq().filter(b -> b).size >= getRequiredPlayer()) {
                Me.broadcast("[blue]@[]명 이상 동의로 [green]승인[]되었습니다.", getRequiredPlayer());
                this.handler.onSuccess.get(this.data);
            } else {
                Me.broadcast("[blue]@[]명 이상이 동의하지 않아 [red]거부[]되었습니다.", getRequiredPlayer());
            }
            Core.app.removeListener(Vote.application);
            Vote.application.dispose();
            Vote.application = null;
        }

        public int getRequiredPlayer() {
            int base = (int) (handler.requirePlayers * Groups.player.size());
            if(base < 1) base = 1;
            return base;
        }

    }

    public static class VoteHandler {

        public String name;
        public String displayName;
        public int minimumPlayers;
        public float requirePlayers;
        public int votingTime;
        public int[] alertTimes;
        public Cons<ObjectMap<String, Object>> onSuccess;

        public VoteHandler(String name, String displayName, int minimumPlayers, float requirePlayers, int votingTime, int[] alertTimes, Cons<ObjectMap<String, Object>> onSuccess) {
            this.name = name;
            this.displayName = displayName;
            this.votingTime = votingTime;
            this.alertTimes = alertTimes;
            this.onSuccess = onSuccess;
            this.minimumPlayers = minimumPlayers;
            this.requirePlayers = requirePlayers;

        }

    }

}
