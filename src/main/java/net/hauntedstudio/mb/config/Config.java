package net.hauntedstudio.mb.config;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private Map<String, BotConfig> apiTokens;

    // Default constructor or setup method
    public Config() {
        this.apiTokens = new HashMap<>();
        // Set default values if needed
    }

    public Map<String, BotConfig> getApiTokens() {
        return apiTokens;
    }

    public void setApiTokens(Map<String, BotConfig> apiTokens) {
        this.apiTokens = apiTokens;
    }

    // Additional class for bot configuration
    public static class BotConfig {
        private String token;
        private String uid;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
