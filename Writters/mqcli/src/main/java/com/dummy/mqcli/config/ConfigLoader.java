package com.dummy.mqcli.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadConfig(String configFile) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(configFile)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando el archivo de configuración: " + e.getMessage(), e);
        }
        return properties;
    }

    public static void validateConfigFile(String configFile) {
        if (configFile == null || configFile.isEmpty()) {
            throw new IllegalArgumentException("El path del archivo de configuración no puede ser nulo o vacío.");
        }

        if (!configFile.endsWith(".properties")) {
            throw new IllegalArgumentException("El archivo de configuración debe tener extensión .properties");
        }

        if (!fileExists(configFile)) {
            throw new RuntimeException("El archivo de configuración no existe: " + configFile);
        }
    }

    private static boolean fileExists(String filePath) {
        return new java.io.File(filePath).exists();
    }
}