package com.github.devlaq.shard;

import arc.Core;
import arc.util.Strings;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.gen.Playerc;

public class Me {

    public static void message(Player player, String prefix, String message, Object... args) {
        player.sendMessage(Strings.format(prefix + message, args));
    }

    public static void info(Player player, String message, Object... args) {
        message(player, "[green]Info[]: ", message, args);
    }

    public static void warn(Player player, String message, Object... args) {
        message(player, "[yellow]Warn[]: ", message, args);
    }

    public static void error(Player player, String message, Object... args) {
        message(player, "[red]Error[]: ", message, args);
    }

    public static void confirm(Player player, String message, Object... args) {
        message(player, "[cyan]Confirm[]: ", message, args);
    }

    public static void cancel(Player player, String message, Object... args) {
        message(player, "[purple]Cancel[]: ", message, args);
    }

    public static void broadcast(String message, Object... args) {
        Groups.player.forEach(p -> message(p, "[sky]Broadcast[]: ", message, args));
    }

    public static void discord(String user, String message, Object... args) {
        Groups.player.forEach(p -> message(p, "[blue]Discord[] [yellow]@[]: @", user, message, args));
    }

    public static void infoPopup(String message, float duration, int align, int top, int left, int bottom, int right) {
        Core.app.post(() -> {
            Call.infoPopup(message != null ? message : "NULL", duration, align, top, left, bottom, right);
        });
    }

    public static void infoPopup(Playerc player, String message, float duration, int align, int top, int left, int bottom, int right) {
        Core.app.post(() -> {
            Call.infoPopup(player.con(), message != null ? message : "NULL", duration, align, top, left, bottom, right);
        });
    }

}
