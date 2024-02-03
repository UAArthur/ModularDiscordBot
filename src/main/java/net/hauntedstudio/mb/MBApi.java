package net.hauntedstudio.mb;

import net.hauntedstudio.mb.bot.BotInstance;
import net.hauntedstudio.mb.bot.commands.Command;

import java.util.List;

public class MBApi {

    private static Main main;

    public static void setMain(Main main) {
        MBApi.main = main;
    }

    public static Main getMain() {
        return main;
    }

    public static List<BotInstance> getBotInstances() {
        return main.dcBotMain.getBotInstances();
    }


    //Commands
    public static void registerCommand(String moduleName, Command command){
        main.dcBotMain.getCommandLoader().registerSlashCommand(moduleName, command);
    }
}
