package com.dummyRecorder.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class AppendStringToFile {
    private static final Logger logger = LogManager.getLogger(AppendStringToFile.class);
    public static void appendToFile(String fileName, String content) {
        try {
            boolean fileExists = fileExists(fileName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            if (fileExists) {
                writer.newLine();
            }
            writer.append(content);
            writer.close();
            logger.info("Contenido añadido correctamente al archivo: " + fileName);
        } catch (IOException e) {
            logger.error("Error al añadir contenido al archivo: " + e.getMessage());
        }
    }

    private static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

}