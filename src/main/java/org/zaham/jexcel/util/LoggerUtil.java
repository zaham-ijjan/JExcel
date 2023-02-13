/*
package org.zaham.jexcel.util;

import lombok.experimental.UtilityClass;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class LoggerUtil {

    private static PropertiesConfiguration config;

    public static boolean getConfigBoolean(String key, boolean defaultValue) {
        return config.getBoolean(key, defaultValue);
    }
    public static void configureLogger() {
        LogManager.shutdown();
        String logFile = getConfigBoolean("log.save", false) ? "log4j.file.properties" : "log4j.properties";
        try (InputStream stream = Utils.class.getClassLoader().getResourceAsStream(logFile)) {
            if (stream == null) {
                PropertyConfigurator.configure("src/main/resources/" + logFile);
            } else {
                PropertyConfigurator.configure(stream);
            }

            LOGGER.info("Loaded " + logFile);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }
}
*/
