package net.hauntedstudio.mb.bot;

import net.dv8tion.jda.api.JDA;

import java.util.List;

public class BotInstance {
    private String botName;
    private String token;
    private String uid;
    private JDA jdaInstance;

    public BotInstance(String botName, String token, String uid, JDA jdaInstance) {
        this.botName = botName;
        this.token = token;
        this.uid = uid;
        this.jdaInstance = jdaInstance;
    }

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }

    public String getUid() {
        return uid;
    }

    public JDA getJda() {
        return jdaInstance;
    }

    // Method to get BotInstance by bot ID
    public static BotInstance getBotInstanceById(List<BotInstance> botInstances, long botId) {
        for (BotInstance botInstance : botInstances) {
            if (botInstance.getUid().equals(botId)) {
                return botInstance;
            }
        }
        return null; // Bot instance not found with the given ID
    }
}
