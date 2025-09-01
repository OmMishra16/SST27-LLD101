package com.example.config;

import java.lang.reflect.Constructor;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws Exception {
        String path = args.length > 0 ? args[0] : "app.properties";
        
        // Test singleton behavior
        AppSettings instance1 = AppSettings.getInstance();
        AppSettings instance2 = AppSettings.getInstance();
        
        System.out.println("Same instance: " + (instance1 == instance2));
        System.out.println("Instance 1 hash: " + System.identityHashCode(instance1));
        System.out.println("Instance 2 hash: " + System.identityHashCode(instance2));
        
        // Load properties
        instance1.loadFromFile(Path.of(path));
        System.out.println("app.name=" + instance1.get("app.name"));
        System.out.println("app.version=" + instance2.get("app.version")); // Should work from both instances
        
        // Test SettingsLoader
        SettingsLoader loader = new SettingsLoader();
        AppSettings instance3 = loader.load(Path.of(path));
        System.out.println("Loader returns same instance: " + (instance1 == instance3));
        
        // Test reflection protection
        try {
            Constructor<AppSettings> constructor = AppSettings.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            System.out.println("Reflection attack succeeded - BAD!");
        } catch (Exception e) {
            System.out.println("Reflection attack blocked: " + e.getMessage());
        }
        
        // Demonstrate thread safety with multiple threads
        Thread t1 = new Thread(() -> {
            AppSettings instance = AppSettings.getInstance();
            System.out.println("Thread 1 instance hash: " + System.identityHashCode(instance));
        });
        
        Thread t2 = new Thread(() -> {
            AppSettings instance = AppSettings.getInstance();
            System.out.println("Thread 2 instance hash: " + System.identityHashCode(instance));
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
