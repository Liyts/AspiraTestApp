package org.example.pojo;

import java.util.List;

public class League extends LionEntity{
    String nameDefault;
    boolean top;
    int topOrder;
    private List<Match> matches;

    public League(long id, String name, String nameDefault, boolean top, int topOrder, String url) {
        this.id = id;
        this.name = name;
        this.nameDefault = nameDefault;
        this.top = top;
        this.topOrder = topOrder;
    }

    public String getNameDefault() {
        return nameDefault;
    }

    public void setNameDefault(String nameDefault) {
        this.nameDefault = nameDefault;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public int getTopOrder() {
        return topOrder;
    }

    public void setTopOrder(int topOrder) {
        this.topOrder = topOrder;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
