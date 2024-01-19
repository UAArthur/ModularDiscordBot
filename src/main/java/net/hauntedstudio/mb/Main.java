package net.hauntedstudio.mb;

import net.hauntedstudio.mb.bot.DCBM;
import net.hauntedstudio.mb.config.Config;
import net.hauntedstudio.mb.config.ConfigUpdater;
import net.hauntedstudio.mb.module.Module;
import net.hauntedstudio.mb.module.ModuleLoader;

import java.util.HashMap;

public class Main {
    // Config
    public ConfigUpdater configUpdater;
    //MySQL
    // Discord
    public DCBM dcBotMain;

    public Main() {
        this.configUpdater = new ConfigUpdater();

        // Start bots based on the loaded configuration
        this.dcBotMain = new DCBM(this, this.configUpdater.config);
        this.dcBotMain.startBots();


        // Set the main class for the API
        MBApi.setMain(this);

        //Loading the modules after starting the bots
        ModuleLoader moduleLoader = new ModuleLoader();
        moduleLoader.loadModules();
    }
    public static void main(String[] args) {
        //Loading first the Main class
        new Main();
    }

}