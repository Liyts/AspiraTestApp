package org.example.pojo;

import java.util.List;

public class Region extends LionEntity{
    List<League> leagues;

    public Region() {
    }

    public Region(long id, String name, String nameDefault, String family, List<League> leagues) {
        this.id = id;
        this.name = name;
        this.leagues = leagues;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }
}
