package net.hauntedstudio.mb.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.hauntedstudio.mb.Main;
import net.hauntedstudio.mb.bot.handler.commands.CommandLoader;
import net.hauntedstudio.mb.config.Config;
import net.hauntedstudio.mb.utils.LoggerClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DCBM {
    private Main main;
    private List<BotInstance> botInstances;
    private final CommandLoader commandLoader;

    public DCBM(Main main) {
        this.main = main;
        this.botInstances = new ArrayList<>();
        this.commandLoader = new CommandLoader(main);
    }

    public void startBots() {
        Map<String, Config.BotConfig> apiTokens = this.main.config.getApiTokens();

        if (apiTokens != null && !apiTokens.isEmpty()) {
            for (Map.Entry<String, Config.BotConfig> entry : apiTokens.entrySet()) {
                startBot(entry.getKey(), entry.getValue());
            }
        } else {
            this.main.logger.warning("No bot tokens found in the config.");
        }
    }

    private void startBot(String botName, Config.BotConfig botConfig) {
        this.main.logger.info("Heap Usage: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "MB/" + Runtime.getRuntime().totalMemory() / (1024 * 1024) + "MB");
        try {
            this.main.logger.info("Starting bot '" + botName + "' with token '" + botConfig.getToken() + "'.");
            JDABuilder builder = JDABuilder.createDefault(botConfig.getToken());
            JDA botInstance = builder.build().awaitReady();
            botInstance.addEventListener(commandLoader);
            botInstances.add(new BotInstance(botName, botConfig.getToken(), botConfig.getUid(), botInstance));


            this.main.logger.info("Bot '" + botName + "' started successfully.");
        } catch (Exception e) {
            this.main.logger.severe("Error starting bot '" + botName + "': ", e.getMessage());
        }
    }

    public List<BotInstance> getBotInstances() {
        return botInstances;
    }

    public CommandLoader getCommandLoader() {
        return commandLoader;
    }

}

