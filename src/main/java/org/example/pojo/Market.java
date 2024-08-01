package org.example.pojo;

import java.util.List;

public class Market extends LionEntity{
    List<Runner> runners;

    public Market(long id, String name, List<Runner> runners) {
        this.id = id;
        this.name = name;
        this.runners = runners;
    }

    public Market() {
    }

    public List<Runner> getRunners() {
        return runners;
    }

    public void setRunners(List<Runner> runners) {
        this.runners = runners;
    }
}
