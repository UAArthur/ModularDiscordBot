package net.hauntedstudio.mb.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.hauntedstudio.mb.Main;
import net.hauntedstudio.mb.config.Config;
import net.hauntedstudio.mb.utils.LoggerClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DCBM {
    private Main main;
    private Config config;
    private List<BotInstance> botInstances;
    private final LoggerClass logger = new LoggerClass();

    public DCBM(Main main, Config config) {
        this.main = main;
        this.config = config;
        this.botInstances = new ArrayList<>();
    }

    public void startBots() {
        Map<String, Config.BotConfig> apiTokens = config.getApiTokens();

        if (apiTokens != null && !apiTokens.isEmpty()) {
            for (Map.Entry<String, Config.BotConfig> entry : apiTokens.entrySet()) {
                startBot(entry.getKey(), entry.getValue());
            }
        } else {
            logger.warning("No bot tokens found in the config.");
        }
    }

    private void startBot(String botName, Config.BotConfig botConfig) {
        logger.info("Heap Usage: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "MB/" + Runtime.getRuntime().totalMemory() / (1024 * 1024) + "MB");
        try {
            this.logger.info("Starting bot '" + botName + "' with token '" + botConfig.getToken() + "'.");
            JDABuilder builder = JDABuilder.createDefault(botConfig.getToken());

            JDA botInstance = builder.build();
            botInstances.add(new BotInstance(botName, botConfig.getToken(), botConfig.getUid(), botInstance));

            logger.info("Bot '" + botName + "' started successfully.");
        } catch (Exception e) {
            logger.severe("Error starting bot '" + botName + "': ", e);
        }
    }

    public List<BotInstance> getBotInstances() {
        return botInstances;
    }
}
