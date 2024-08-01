package org.example.pojo;

import java.util.List;

public class Match extends LionEntity{
    long kickoff;
    List<Market> markets;

    public long getKickoff() {
        return kickoff;
    }

    public void setKickoff(long kickoff) {
        this.kickoff = kickoff;
    }

    public List<Market> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }
}
