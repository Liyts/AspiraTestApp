package org.example;

import java.util.ResourceBundle;

public class ConfigBundle {
    private static ConfigBundle INSTANCE;
    private final ResourceBundle resourceBundle;

    private ConfigBundle() {
        resourceBundle = ResourceBundle.getBundle("resources");
    }

    public static synchronized ConfigBundle getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigBundle();
        }
        return INSTANCE;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public String getProperty(String key) {
        return getInstance().resourceBundle.getString(key);
    }
}
