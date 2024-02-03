package net.hauntedstudio.mb;

import net.hauntedstudio.mb.bot.DCBM;
import net.hauntedstudio.mb.config.Config;
import net.hauntedstudio.mb.config.ConfigUpdater;
import net.hauntedstudio.mb.module.Module;
import net.hauntedstudio.mb.module.ModuleLoader;
import net.hauntedstudio.mb.utils.LoggerClass;

import java.util.HashMap;

public class Main {
    // Config
    public ConfigUpdater configUpdater;
    //MySQL
    // Discord
    public DCBM dcBotMain;
    public final LoggerClass logger = new LoggerClass();
    public Config config;

    public boolean debug = true;


    public Main() {
        // Set the main class for the API
        MBApi.setMain(this);

        this.configUpdater = new ConfigUpdater();
        this.config = this.configUpdater.config;
        // Start bots based on the loaded configuration
        this.dcBotMain = new DCBM(this);
        this.dcBotMain.startBots();

        //Loading the modules after starting the bots
        ModuleLoader moduleLoader = new ModuleLoader(this);
        moduleLoader.loadModules();
    }

    public static void main(String[] args) {
        //Loading first the Main class
        new Main();
    }

}