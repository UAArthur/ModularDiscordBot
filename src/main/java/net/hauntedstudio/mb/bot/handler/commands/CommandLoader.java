package net.hauntedstudio.mb.bot.handler.commands;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.hauntedstudio.mb.Main;
import net.hauntedstudio.mb.bot.BotInstance;
import net.hauntedstudio.mb.bot.commands.Command;
import net.hauntedstudio.mb.bot.commands.HelpCommand;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CommandLoader extends ListenerAdapter {
    private Main main;

    /**
     * Slash commands
     * String ModuleName
     * Command CommandInstance
     */
    public final SetMultimap<String, Command> commands;

    public CommandLoader(Main main) {
        this.main = main;
        this.commands = HashMultimap.create(); // Use SetMultimap to ensure uniqueness

        // Internal commands
        registerSlashCommand("_Internal_", new HelpCommand(this.main));
    }

    public void registerSlashCommand(String moduleName, Command command) {
        // Ensure uniqueness by checking if the command is not already registered for the given module
        if (!commands.containsEntry(moduleName, command)) {
            commands.put(moduleName, command);
        }
    }

    public void loadSlashCommandsForAllBots() {
        this.main.dcBotMain.getBotInstances().forEach(botInstance -> {
            JDA jda = botInstance.getJda();
            Guild guild = jda.getGuildById("958707335541456946");
            this.loadSlashCommands(guild);
        });
    }

    protected void loadSlashCommands(Guild guild){
        System.out.println("Loading slash commands for guild " + guild.getId());
        this.commands.forEach((moduleName, command) -> {
            System.out.println("  - Loading command \"/" + command.getCommandData().getName() + "\" for guild " + guild.getId());
            guild.updateCommands().addCommands(command.getCommandData()).queue();
        });
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        commands.forEach((moduleName, command) -> {
            if (command.getCommandData().getName().equals(commandName)) {
                command.execute(event);
            }
        });
    }
}
