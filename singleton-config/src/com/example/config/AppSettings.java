package com.example.config;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Thread-safe Singleton with protection against reflection and serialization attacks.
 */
public class AppSettings implements Serializable {
    private static volatile AppSettings INSTANCE;
    private final Properties props = new Properties();
    private volatile boolean initialized = false;

    private AppSettings() {
        // Protect against reflection attacks
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to create instance. Cannot create multiple instances via reflection.");
        }
    }

    public static AppSettings getInstance() {
        if (INSTANCE == null) {
            synchronized (AppSettings.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppSettings();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void loadFromFile(Path file) {
        if (initialized) {
            return; // Prevent re-loading to maintain singleton state consistency
        }
        try (InputStream in = Files.newInputStream(file)) {
            props.load(in);
            initialized = true;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String get(String key) {
        return props.getProperty(key);
    }

    // Preserve singleton property during serialization
    private Object readResolve() {
        return INSTANCE;
    }

    // Additional protection against serialization attacks
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("Singleton cannot be deserialized");
    }
}
