package net.hauntedstudio.mb.config;

import com.google.gson.Gson;
import net.hauntedstudio.mb.Main;
import net.hauntedstudio.mb.utils.LoggerClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUpdater {
    public Config config;
    private final LoggerClass logger = new LoggerClass();

    public ConfigUpdater() {
        // Load existing config if the file exists
        config = loadConfig("config.json");

        // If the config file doesn't exist, create a new config
        if (config == null) {
            config = new Config();
            // Set default or initial values if needed

            // Save the new config
            saveConfig();
        }
    }

    private Config loadConfig(String fileName) {
        File configFile = new File(fileName);

        if (!configFile.exists()) {
            logger.warning("Config file does not exist: " + configFile.getAbsolutePath());
            return null;
        }

        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(configFile.toPath()))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Config.class);
        } catch (IOException ex) {
            // An error occurred while reading the file
            logger.severe("Failed to load config: ", ex);
            return null;
        }
    }

    private void saveConfig() {
        Gson gson = new Gson();
        String json = gson.toJson(config);

        logger.debug("JSON content: " + json);  // Print the JSON content for debugging

        // Specify the path where you want to save the new config
        Path path = Path.of("config.json");

        try {
            Files.write(path, json.getBytes());
            logger.info("New Config saved successfully.");
        } catch (IOException e) {
            logger.severe("Failed to save new config: ", e);
        }
    }
}
