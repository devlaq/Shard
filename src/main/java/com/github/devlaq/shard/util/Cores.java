package com.github.devlaq.shard.util;

import com.github.devlaq.shard.Utils;
import mindustry.Vars;
import mindustry.world.Tile;

import java.util.HashMap;
import java.util.Map;

public class Cores {

    public static final Map<Integer, Tile> cores = new HashMap<>();

    public static void update() {
        for(int x = 0; x < Vars.world.width(); x++) {
            for(int y = 0; y < Vars.world.height(); y++) {
                Tile tile = Vars.world.tile(x, y);
                if(tile.isCenter() && Utils.Worlds.isCore(tile.block())) cores.put(cores.size()+1, tile);
            }
        }
    }

}
