package com.github.devlaq.shard.vote;

import arc.ApplicationListener;

public class Vote {

    public VoteType type;
    public VoteCounter counter;

    public Vote(VoteType type) {
        this.type = type;
        this.counter = new VoteCounter(type, 1);
    }

    public class VoteCounter implements ApplicationListener {

        public VoteType type;
        public int limit;

        public int tick = 0;

        public VoteCounter(VoteType type, int limit) {
            this.type = type;
            this.limit = limit;
        }

        @Override
        public void update() {

        }
    }

}
