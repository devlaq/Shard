package com.github.devlaq.shard;

import arc.util.Strings;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.world.Block;
import mindustry.world.Tile;

import java.util.ArrayList;
import java.util.List;

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

        public static Player findPlayer(String name) {
            int nearest = 100;
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

    }

}