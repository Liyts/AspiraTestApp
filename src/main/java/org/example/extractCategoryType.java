package org.example;

import javax.swing.*;

public enum extractCategoryType {
    FOOTBALL("Football"),
    TENNIS("Tennis"),
    HOCKEY("Ice Hockey"),
    BASKETBALL("Basketball");
    private final String category;
    extractCategoryType(final String category){
        this.category = category;
    }
    public String getCategory() {
        return category;
    }

    public static boolean contains(String category) {
        for (extractCategoryType type : values()) {
            if (type.getCategory().equals(category)) {
                return true;
            }
        }
        return false;
    }
}
