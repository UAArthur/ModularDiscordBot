package net.hauntedstudio.mb.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.hauntedstudio.mb.Main;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelpCommand implements Command {
    private Main main;
    public HelpCommand(Main main) {
        this.main = main;
    }
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        this.main.logger.debug("Executing help command");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Help Menu");

        // Add static commands to the help message
        eb.setDescription("Here is a list of all available commands:" +
                "\n\n/help - Shows a list of all commands" +
                "\n/ping - Pong!\n\n");

        // Iterate through each module name (key) in the commands list
        for (String moduleName : this.main.dcBotMain.getCommandLoader().commands.keySet()) {
            // Skip modules with the name "_Internal_"
            if ("_Internal_".equals(moduleName)) {
                continue;
            }

            // Get the list of commands associated with the current module name
            List<Command> moduleCommands = this.main.dcBotMain.getCommandLoader().commands.entries().stream()
                    .filter(entry -> entry.getKey().equals(moduleName))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            // Append the module name to the help message
            eb.appendDescription("\n\n**" + moduleName + "**\n");

            // Iterate through the commands and append their information to the help message
            for (Command command : moduleCommands) {
                eb.appendDescription("`/" + command.getCommandData().getName() + "` - " + command.getCommandData().getDescription() + "\n");
            }
        }

        eb.setColor(0x00FFFF);
        event.replyEmbeds(eb.build()).queue();
    }

    @Override
    public SlashCommandData getCommandData() {
        return Commands.slash("help", "Shows a list of all commands and their descriptions.");
    }
}