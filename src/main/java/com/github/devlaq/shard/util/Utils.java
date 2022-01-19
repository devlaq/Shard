package com.github.devlaq.shard.util;

import arc.Core;
import arc.struct.Seq;
import arc.util.Reflect;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.maps.Map;
import mindustry.server.ServerControl;
import mindustry.world.Block;
import mindustry.world.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    public static class Worlds {

        public static boolean isCore(Block block) {
            return block == Blocks.coreShard || block == Blocks.coreFoundation || block == Blocks.coreNucleus;
        }

        public static List<Tile> getCores() {
            List<Tile> cores = new ArrayList<>();
            for(int x = 0; x < Vars.world.width(); x++) {
                for(int y = 0; y < Vars.world.height(); y++) {
                    Tile tile = Vars.world.tile(x, y);
                    if(tile.isCenter() && isCore(tile.block())) {
                        cores.add(tile);
                    }
                }
            }
            return cores;
        }

        public static List<Tile> getCores(Team team) {
            List<Tile> cores = getCores();
            cores.removeIf(tile -> tile.team() != team);
            return cores;
        }

        public static List<Tile> getEnemyCores(Team team) {
            List<Tile> cores = getCores();
            cores.removeIf(tile -> tile.team() == team);
            return cores;
        }

    }

    public static class Players {

        public static Player findPlayer(String name, int marginOfError) {
            int nearest = marginOfError;
            Player found = null;

            for(Player player : Groups.player) {
                int levenshtein = Strings.levenshtein(player.name(), name);
                if(levenshtein < nearest) {
                    nearest = levenshtein;
                    found = player;
                }
            }

            return found;
        }

        public static Player findPlayer(String name) {
            return findPlayer(name, 3);
        }

        public static void teleportPlayer(Player player, float x, float y) {
            Thread teleportingThread = new Thread("Shard-Teleport-Thread-" + player.id) {
                @Override
                public void run() {
                    int limit = 30;
                    while(!player.within(x, y, 2 * Vars.tilesize) && limit-- > 0) {
                        player.unit().set(x, y);
                        player.set(x, y);
                        Call.setPosition(player.con, x, y);
                        try {
                            Thread.sleep(50);
                        } catch (Exception ignored) {}
                    }
                }
            };
            teleportingThread.setDaemon(true);
            teleportingThread.start();
        }

        public static void teleportPlayer(Player player, Player target) {
            teleportPlayer(player, target.x, target.y);
        }

    }

    public static class Maps {

        public static Seq<Map> searchMaps(String name) {
            Seq<Map> result = new Seq<>();
            for(Map map : Vars.maps.all()) {
                if(name.equalsIgnoreCase(map.name())) result.add(map);
                else if(Strings.levenshtein(name, map.name()) < 3) result.add(map);
                else if(map.name().contains(name)) result.add(map);
            }
            return result;
        }

        public static Map findMapByName(String name) {
            int nearest = 100;
            Map found = null;

            for(Map map : Vars.maps.all()) {
                int levenshtein = Strings.levenshtein(map.name(), name);
                if(levenshtein < nearest) {
                    nearest = levenshtein;
                    found = map;
                }
            }

            return found;
        }

        public static Map findMapByIndex(int index) {
            return Vars.maps.all().get(index);
        }

        public static Map findAuto(String input) {
            Map byName = findMapByName(input);
            if(byName != null) return byName;
            try {
                Map byIndex = findMapByIndex(Integer.parseInt(input));
                if(byIndex != null) return byIndex;
            } catch (Exception ignored) {}
            return null;
        }

        public static void changeMap(Map map) {
            Core.app.getListeners().forEach(l -> {
                if(!(l instanceof ServerControl serverControl)) return;
                Reflect.set(serverControl, "nextMapOverride", map);
            });
        }

    }

    public static class Integers {

        public static int parseIntOrValue(String input, int value) {
            try {
                return Integer.parseInt(input);
            } catch (Exception ignored) {
                return value;
            }
        }

    }

}