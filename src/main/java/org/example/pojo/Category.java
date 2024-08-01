package org.example.pojo;

import java.util.List;

public class Category extends LionEntity{
    String family;
    List<Region> regions;

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
