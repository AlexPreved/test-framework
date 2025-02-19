package com.example.teamcity.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static volatile Config instance;
    private final Properties properties = new Properties();

    private Config() {
        loadProperties(CONFIG_PROPERTIES);
    }

    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    private void loadProperties(String fileName) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null) {
                throw new IllegalArgumentException("Configuration file not found: " + fileName);
            }
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + fileName, e);
        }
    }

    public static String getProperty(String key) {
        return getInstance().properties.getProperty(key);
    }
}
