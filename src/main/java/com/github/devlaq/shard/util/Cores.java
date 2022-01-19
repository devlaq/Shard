package com.github.devlaq.shard.util;

import arc.struct.IntMap;
import arc.struct.ObjectMap;
import mindustry.Vars;
import mindustry.world.Tile;


public class Cores {

    public static final IntMap<Tile> cores = new IntMap<>();

    public static void update() {
        for(int x = 0; x < Vars.world.width(); x++) {
            for(int y = 0; y < Vars.world.height(); y++) {
                Tile tile = Vars.world.tile(x, y);
                if(tile.isCenter() && Utils.Worlds.isCore(tile.block())) cores.put(cores.size+1, tile);
            }
        }
    }

}
