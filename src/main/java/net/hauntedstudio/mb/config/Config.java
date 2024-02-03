package net.hauntedstudio.mb.config;

import com.google.gson.Gson;
import net.hauntedstudio.mb.utils.LoggerClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private Map<String, BotConfig> apiTokens;
    private DatabaseConfig mysqlDatabase;

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

    public DatabaseConfig getMysqlDatabase() {
        return mysqlDatabase;
    }

    public void setMysqlDatabase(DatabaseConfig mysqlDatabase) {
        this.mysqlDatabase = mysqlDatabase;
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

    // Additional class for MySQL database configuration
    public static class DatabaseConfig {
        private String database;
        private String host;
        private String password;
        private String user;
        private int port;

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}

