package com.github.devlaq.shard.event.handler;

import arc.func.Cons;
import arc.math.geom.Vec2;
import com.github.devlaq.shard.core.Me;
import com.github.devlaq.shard.util.Utils;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Player;
import mindustry.world.Tile;

public class OnBlockBuildBegin implements Cons<EventType.BlockBuildBeginEvent> {

    @Override
    public void get(EventType.BlockBuildBeginEvent event) {
        if(event.team == Team.sharded && !event.breaking && event.unit.isPlayer() && event.tile.block() == Blocks.thoriumReactor) {
            Player player = event.unit.getPlayer();
            int nearest = Integer.MAX_VALUE;
            Tile nearestCore = null;
            for(int x = 0; x < Vars.world.width(); x++) {
                for(int y = 0; y < Vars.world.height(); y++) {
                    if(Utils.Worlds.isCore(Vars.world.tile(x, y).block()) && Vars.world.tile(x, y).isCenter()) {
                        int distance = (int) new Vec2(x, y).dst2(new Vec2(player.x, player.y));
                        if(nearest > distance) {
                            nearest = distance;
                            nearestCore = Vars.world.tile(x, y);
                        }
                    }
                }
            }
            Me.broadcast("@이(가) @, @에 Thorium Reactor를 설치하고 있습니다!");
        }
    }

}
