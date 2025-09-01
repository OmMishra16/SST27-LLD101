package com.example.config;

import java.nio.file.Path;

/** Loads settings into the singleton instance. */
public class SettingsLoader {
    public AppSettings load(Path file) {
        AppSettings instance = AppSettings.getInstance();
        instance.loadFromFile(file);
        return instance;
    }
}
