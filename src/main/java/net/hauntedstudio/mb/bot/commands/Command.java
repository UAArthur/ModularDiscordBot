package net.hauntedstudio.mb.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface Command {
    void execute(SlashCommandInteractionEvent event);
    SlashCommandData getCommandData();
}